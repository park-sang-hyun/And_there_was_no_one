 //package com.ssafy.pjt3.controller;
 //
 //import org.slf4j.Logger;
 //import org.slf4j.LoggerFactory;
 //import org.springframework.beans.factory.annotation.Autowired;
 //import org.springframework.messaging.handler.annotation.DestinationVariable;
 //import org.springframework.messaging.handler.annotation.MessageMapping;
 //import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
 //import org.springframework.messaging.simp.SimpMessagingTemplate;
 //import org.springframework.web.bind.annotation.RestController;
 //
 //import com.ssafy.pjt3.model.ChatMessage;
 //
 //
 //@RestController
 //public class DrawController {
 //	private static final Logger logger = LoggerFactory.getLogger(DrawController.class);
 //	
 //	@Autowired
 //	private SimpMessagingTemplate simpMessagingTemplate;
 //	/*
 //	// 내부로 봤을때 일반서버와 스톰프 서버가 있고 요청을 일반 서버가 받았을때 스톰프 서버 메시지 관련이면 스톰프 서버로
 //	// 보내 주는 것으로 구현되어 있는데 main.js 에서 /topic/public 로 바로 연결해서 해결 할수도 있음  
 //    @MessageMapping("/chat.sendMessage") // main.js 와 연결 
 //    @SendTo("/topic/public") // 이 부분을 해당되는 채팅방 주소로 맵핑 시켜줘야 함!!!! 여러 채팅방을 만들려면   
 //    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
 //    	
 //        return chatMessage;
 //    }
 //	*/
 //	@MessageMapping("/topic/{RoomId}")
 //	public void handledraw(ChatMessage message, @DestinationVariable("RoomId") String RoomId,
 //			SimpMessageHeaderAccessor headerAccessor) {
 //		System.out.println("///////////////RoomId : "+RoomId);
 //		//System.out.println(headerAccessor.getSessionId()+" "+headerAccessor.getSessionAttributes().get("username"));
 //		
 //		System.out.println("ox: " + message.getX1());
 //		System.out.println("oy: " + message.getY1());
 //		System.out.println("nx: " + message.getX2());
 //		System.out.println("ny: " + message.getY2());
 //		System.out.println("color: " + message.getColor());
 //		System.out.println("width: " + message.getWidth());
 //		System.out.println("mode: " + message.getMode());
 //		
 //		this.simpMessagingTemplate.convertAndSend("/topic/"+RoomId, message);
 //	}
 //
 //	/*
 //    @MessageMapping("/chat.addUser")
 //    @SendTo("/topic/public")
 //    public ChatMessage addUser(@Payload ChatMessage chatMessage,
 //                               SimpMessageHeaderAccessor headerAccessor) {
 //        // Add username in web socket session
 //    	System.out.println("chatMessage : "+chatMessage.getSender());
 //        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
 //        return chatMessage;
 //    }
 //    */
 //    // 더 추가 할것 ! 
 //}
