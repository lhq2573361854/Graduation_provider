package com.tianling.config;

import com.tianling.websocket.MyWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tianling
 * @email 859073143@qq.com
 * @since 2021/2/20 21:21
 */
@Configuration
public class WebSocketConfig {

    @Resource
    MyWebSocketHandler myWebSocketHandler;

    @Bean
    public HandlerMapping handlerMapping() {
        // 对相应的URL进行添加处理器
        Map<String, WebSocketHandler> map = new HashMap<>();
        map.put("/websocket", myWebSocketHandler);
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setUrlMap(map);
        mapping.setOrder(-1);
        return mapping;
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

}
