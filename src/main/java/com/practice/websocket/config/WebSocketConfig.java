package com.practice.websocket.config;

import com.practice.websocket.interceptor.WebSocketInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Client 에서 websocket 연결할 때 사용할 API 경로를 설정해주는 메서드.
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }


    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 보낼 때와 받을 때의 prefix 지정

        // Enables a simple in-memory broker
        // server -> client prefix
        registry.enableSimpleBroker("/sub");
        // client -> server prefix
        registry.setApplicationDestinationPrefixes("/pub");
        /*
         * setApplicationDestinationPrefixes는 @MessageMapping이 붙은 메서드를 호출한다.
         * 메세지를 발행하게 되면, /pub/{roomId} 로 라우팅되고,
         * /sub/user/{roomId}를 구독하는 클라이언트로 메세지 객체가 전달된다.
         */
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new WebSocketInterceptor());
    }

}
