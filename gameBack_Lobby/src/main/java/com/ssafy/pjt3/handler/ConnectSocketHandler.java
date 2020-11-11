package com.ssafy.pjt3.handler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class ConnectSocketHandler extends TextWebSocketHandler {
	private Map<String, WebSocketSession> sessions = new HashMap<String, WebSocketSession>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("세션아이디[" + session.getId() + "] 입장.");

		Map<String, Object> map = session.getAttributes();
		String id = (String) map.get("userId");
		System.out.println(id);
		
//		Iterator<String> sessionIds = sessions.keySet().iterator();
//
//		String sessionId = "";

//		while (sessionIds.hasNext()) {
//			sessionId = sessionIds.next();
//			sessions.get(sessionId).sendMessage(new TextMessage(session.getId() + ", login"));
//		}

		sessions.put(session.getId(), session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("세션아이디[" + session.getId() + "] 퇴장.");

		sessions.remove(session.getId());

//		Iterator<String> sessionIds = sessions.keySet().iterator();

//		String sessionId = "";
//
//		while (sessionIds.hasNext()) {
//			sessionId = sessionIds.next();
//			sessions.get(sessionId).sendMessage(new TextMessage(session.getId() + ", logout"));
//		}
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

		System.out.println(session.getId() + "이(가) 메세지전송.");

		Iterator<String> sessionIds = sessions.keySet().iterator();
		
		
		String msg = message.getPayload();
		String[] msgs = msg.split(",");

		String sessionId = "";
		
		while (sessionIds.hasNext()) {
			sessionId = sessionIds.next();
			sessions.get(sessionId).sendMessage(new TextMessage(msgs[0] +"," + msgs[1]));
		}
	}
}