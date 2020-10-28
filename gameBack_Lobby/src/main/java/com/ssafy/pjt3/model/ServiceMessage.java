package com.ssafy.pjt3.model;

public class ServiceMessage {
    private MessageType type;
    private ChatMessage chat;

    public enum MessageType {
        CHAT
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public ChatMessage getChat() {
        return chat;
    }

    public void setChat(ChatMessage chat) {
        this.chat = chat;
    }

}
