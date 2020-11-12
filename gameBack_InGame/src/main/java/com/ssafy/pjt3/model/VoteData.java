package com.ssafy.pjt3.model;

public class VoteData {
	int room_id;
	String who;
	public VoteData(int room_id, String who) {
		this.room_id = room_id;
		this.who = who;
	}
	public int getRoom_id() {
		return room_id;
	}
	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}
	public String getWho() {
		return who;
	}
	public void setWho(String who) {
		this.who = who;
	}
	
	
}
