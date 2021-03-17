package com.tianling.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.FluxSink;

/**
 * @author Tianling
 * @email 859073143@qq.com
 * @since 2021/2/25 19:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class WebSocketClient {
    private FluxSink<WebSocketMessage> sink;
    private WebSocketSession session;

    public void sendData(String data) {
        sink.next(session.textMessage(data));
    }
}
