package com.ssafy.pjt3.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.ssafy.pjt3.handler.ChatSocketHandler;
import com.ssafy.pjt3.interceptor.HandshakeInterceptor;


@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Autowired
	ChatSocketHandler chatSocketHandler;
	

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(chatSocketHandler, "/chatting").addInterceptors(new HandshakeInterceptor()).setAllowedOrigins("*");
		
	}
	
	@Bean
	public HandshakeInterceptor createHandshakeInterceptor() {
		return new HandshakeInterceptor();
	}
}
