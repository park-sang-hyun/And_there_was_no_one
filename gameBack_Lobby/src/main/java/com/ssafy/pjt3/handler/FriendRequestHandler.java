//package com.ssafy.pjt3.handler;
//
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import com.ssafy.pjt3.service.AuthTokenService;
//
//public class FriendRequestHandler extends TextWebSocketHandler {
//	@Autowired
//	AuthTokenService authTokenService;
//	
//	// 로그인중인 개별유저(key값: user_id)
//	Map<String, WebSocketSession> sessions = new HashMap<String, WebSocketSession>();
//
//	
//
//	@Override
//	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//		System.out.println("세션아이디[" + session.getId() + "] 알림 요청 시작됨.");
//		
//	}
//
//	@Override
//	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//		// 메세징 규칙
//		// 1. 발신자.
//		// 2. 수신자.
//		// 3. 알림 내용.
//		
//		String msg = message.getPayload();
//		
//		if(msg != null) {
//			String[] strs = msg.split(","); //메세지를 분해해서 발신자 user_id, 수신자user_id, 알림내용을 분리함.
//			
//			if(strs != null && strs.length ==3) {
//				String sender = strs[0];
//				String receiver = strs[1];
//				String content = strs[2];
//				
//				sessions.put(sender, session);
//				sessions.put(receiver, session);
//				
//				
//				//receiver가 로그인 되어 있다면
//				int receiver_id = Integer.parseInt(receiver);
//				int state = 0;
//				
//				try {
//					state = authTokenService.isLogin(receiver_id);
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//				
//			
//				// receiver가 로그인 되있는지 확인
//				// 1. 로그인 되있으면, 바로 알람보내기.
//				if(state == 1) {
//					
//				}
//				
//				// 2. 로그인 되있지않으면, 알람테이블에 저장하기. 
//				else {
//					
//				}
//				
//			}
//		}
//		
//				
//		for(int i=0; i<strs.length; i++) {
//			System.out.println(strs[i]);
//		}
//				
//	}
//
//	@Override
//	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
//		// TODO Auto-generated method stub
//		super.handleTransportError(session, exception);
//	}
//
//	@Override
//	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//		System.out.println("세션아이디[" + session.getId() + "] 알림 요청 종료됨");
//	}
//	
//	private String getMemberId(WebSocketSession session) {
//		Map<String, Object> httpSession = session.getAttributes();
//		String m_id = (String) httpSession.get("m_id");
//		return m_id==null?null:m_id;
//	}
//	
//}
