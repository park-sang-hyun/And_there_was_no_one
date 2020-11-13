package com.ssafy.pjt3.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.ssafy.pjt3.handler.ChatSocketHandler;
import com.ssafy.pjt3.handler.RoomSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Autowired
	ChatSocketHandler chatSocketHandler;
	
	@Autowired
	RoomSocketHandler roomSocketHandler;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(chatSocketHandler, "/chatting/{room_id}").setAllowedOrigins("*");
		registry.addHandler(roomSocketHandler, "/renewing/{room_id}").setAllowedOrigins("*");
	}
	
}
