package com.ssafy.pjt3.model;

public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;
    private String roomName;
    
    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
    
    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
    
}

