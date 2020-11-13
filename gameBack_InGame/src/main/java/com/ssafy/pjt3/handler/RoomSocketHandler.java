package com.ssafy.pjt3.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.pjt3.dto.Room;
import com.ssafy.pjt3.service.RoomService;

public class RoomSocketHandler extends TextWebSocketHandler {

	// HashMap<String, WebSocketSession> sessionMap = new HashMap<>(); //웹소켓 세션을 담아둘
	// 맵
	List<HashMap<String, Object>> rls = new ArrayList<>(); // 웹소켓 세션을 담아둘 리스트 ---roomListSessions

	@Autowired
	RoomService roomService;
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("session ID : " + session);
		// 소켓 연결
		super.afterConnectionEstablished(session);
		boolean flag = false;
		String url = session.getUri().toString();
		System.out.println(url);
		String room_id = url.split("/chatting/")[1];

		System.out.println(room_id);

		int idx = rls.size(); // 방의 사이즈를 조사한다.
		if (rls.size() > 0) {
			for (int i = 0; i < rls.size(); i++) {
				String rN = (String) rls.get(i).get("room_id");
				if (room_id.equals(rN)) {
					flag = true;
					idx = i;
					break;
				}
			}
		}

		if (flag) { // 존재하는 방이라면 세션만 추가한다.
			HashMap<String, Object> map = rls.get(idx);
			map.put(session.getId(), session);
		} else { // 최초 생성하는 방이라면 방번호와 세션을 추가한다.
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("room_id", room_id);
			map.put(session.getId(), session);
			rls.add(map);
		}

//		// 세션등록이 끝나면 발급받은 세션ID값의 메시지를 발송한다.
//		JSONObject obj = new JSONObject();
//		obj.put("type", "getId");
//		obj.put("sessionId", session.getId());
//		session.sendMessage(new TextMessage(obj.toJSONString()));
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// 소켓 종료
		if (rls.size() > 0) { // 소켓이 종료되면 해당 세션값들을 찾아서 지운다.
			for (int i = 0; i < rls.size(); i++) {
				rls.get(i).remove(session.getId());
			}
		}
		super.afterConnectionClosed(session, status);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// 메시지 발송
		String msg = message.getPayload();
		JSONObject obj = jsonToObjectParser(msg);
		
		int temp_rN = (int) obj.get("room_id");

		String rN = String.valueOf(obj.get("room_id")); // 어느 방에 보낼 것 인지.
		System.out.println("rN: " + rN);
		
// 		해당하는 번호의 방 정보 갱신되면 그 방에 들어있는 친구들에게 방 정보 브로드캐스팅.

		HashMap<String, Object> temp = new HashMap<String, Object>();

		if (rls.size() > 0) {
			
			for (int i = 0; i < rls.size(); i++) {
				String roomNumber = (String) rls.get(i).get("room_id"); // 세션리스트의 저장된 방번호를 가져와서

				if (roomNumber.equals(rN)) { // 같은값의 방이 존재한다면
					temp = rls.get(i); // 해당 방번호의 세션리스트의 존재하는 모든 object값을 가져온다.(방에 해당하는 MAP 얻어오는 작업.)
					break;
				}
			}

			// db에 접근해서 해당 방에 있는 유저정보를 들고옴.
			Room room = null;
			ObjectMapper objectMapper = new ObjectMapper();
			
			
			try {
				room = roomService.findRoomWithRoomid(temp_rN);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			String massage = objectMapper.writeValueAsString(room);
			
			// 해당 방의 세션들만 찾아서 메시지를 발송해준다.
			for (String k : temp.keySet()) {
				if (k.equals("room_id")) { // 다만 방번호일 경우에는 건너뛴다.
					continue;
				}

				WebSocketSession wss = (WebSocketSession) temp.get(k);
				if (wss != null) {
					try {
						wss.sendMessage(new TextMessage(massage));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	
	private static JSONObject jsonToObjectParser(String jsonStr) {
		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		try {
			obj = (JSONObject) parser.parse(jsonStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return obj;
	}

}
