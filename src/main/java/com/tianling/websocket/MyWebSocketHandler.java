package com.tianling.websocket;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tianling.common.WebSocketOperation;
import com.tianling.entities.Article;
import com.tianling.entities.WebsocketMessage;
import com.tianling.entities.ResponseInfo;
import com.tianling.service.IArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.HandshakeInfo;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author Tianling
 * @email 859073143@qq.com
 * @since 2021/2/20 21:21
 */
@Component
@Slf4j
public class MyWebSocketHandler implements WebSocketHandler {
    
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    IArticleService iArticleService;

    @Resource
    ObjectMapper objectMapper;

    private ConcurrentHashMap<String,WebSocketClient> map = new ConcurrentHashMap<>(16);

    private void sendOthers(String message,String id) {
            map.forEach((uuid,webSocketClient) ->{
                if(!ObjectUtil.equal(uuid,id)){
                    log.info("发送给 id = {}",uuid);
                    webSocketClient.sendData(message);
                }
            }
        );
    }

    private void sendCurrent(String message,String id) {
       map.get(id).sendData(message);
    }

    private void updateRedisStar(WebsocketMessage finalWebsocketMessage){
        ResponseInfo responseInfo = (ResponseInfo) redisTemplate.opsForValue().get("article_cache_article__all");
        ArrayList<Article> list = (ArrayList)(responseInfo.getData());

        ArrayList<Article> collect = (ArrayList<Article>) list.stream().map(article1 -> {
            if (ObjectUtil.equal(article1.getId(), finalWebsocketMessage.getId())) {
                if(ObjectUtil.equal(finalWebsocketMessage.getCode(), WebSocketOperation.ADD.getValue())){
                    article1.setArticleStars(article1.getArticleStars() + finalWebsocketMessage.getStarNumber());
                } else if(ObjectUtil.equal(finalWebsocketMessage.getCode(), WebSocketOperation.SUP.getValue())){
                    article1.setArticleStars(article1.getArticleStars() - finalWebsocketMessage.getStarNumber());
                }
            }
            return article1;
        }).collect(Collectors.toList());

        responseInfo.setData(collect);

        redisTemplate.opsForValue().set("article_cache_article__all",responseInfo);
    }
    private Article messageToArticle(WebsocketMessage websocketMessage){
        Assert.notNull(websocketMessage);
        Assert.notNull(websocketMessage.getId());
        Assert.notNull(websocketMessage.getCode());

        Article article = new Article();

        article.setId(websocketMessage.getId());

        if(ObjectUtil.equal(websocketMessage.getCode() , WebSocketOperation.SUP.getValue())){
            article.setArticleStars(-1);
        }else if(ObjectUtil.equal(websocketMessage.getCode() , WebSocketOperation.ADD.getValue())){
            article.setArticleStars(+1);
        }else{
            article.setArticleStars(0);
        }
        return article;
    }


    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        HandshakeInfo handshakeInfo = webSocketSession.getHandshakeInfo();

        InetSocketAddress remoteAddress = handshakeInfo.getRemoteAddress();

        String query = handshakeInfo.getUri().getQuery();

        Map<String, String> queryMap = HttpUtil.decodeParamMap(query, Charset.defaultCharset());

        String id = queryMap.get("id");
        
        Mono<Void> output = webSocketSession.send(Flux.create(sink -> map.put(id, new WebSocketClient(sink,webSocketSession))));

        Mono<Void> input = webSocketSession.receive()
                .map(mapper-> {
                    WebsocketMessage websocketMessage = null;
                    try {
                        websocketMessage = objectMapper.readValue(mapper.getPayloadAsText(), WebsocketMessage.class);
                        log.info("获取到的数据是：websocketMessage {} ", websocketMessage);
                    } catch (JsonProcessingException e) {
                        log.info("json 格式化异常: {}",e.getMessage());
                        websocketMessage.setCode(WebSocketOperation.ERROR.getValue());
                        return websocketMessage;
                    }

                    return websocketMessage;
                })
                .doOnNext(msg-> {
                    try {
                        updateRedisStar(msg);
                        sendOthers(objectMapper.writeValueAsString(msg),id);
                        iArticleService.updateArticle(messageToArticle(msg)).then().subscribe();
                    } catch (JsonProcessingException e) {
                        log.info("json转化错误，cause {}", e.getMessage());
                    }
                })
                .doOnSubscribe(conn -> {
                    log.info("建立连接：{}，用户ip：{}， uuid= {} ", webSocketSession.getId(), remoteAddress.getHostName(), id);
                })
                .doOnCancel(() ->{
                    log.info("用户：{}，关闭", webSocketSession.getId());
                    map.remove(id);
                }).doOnComplete(() -> {
                    log.info("用户：{}，完成", webSocketSession.getId());
                    map.remove(id);
                })
                .doOnError(sink->{
                    log.info("用户：{}，错误", sink.getMessage());
                    map.remove(id);
                })
                .then();
        return Mono.zip(input,output).then();
    }
}
