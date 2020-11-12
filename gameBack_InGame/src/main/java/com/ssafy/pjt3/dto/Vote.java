package com.ssafy.pjt3.dto;

public class Vote {
	int id;
	int room_id;
	String who;
	
	public Vote() {
	}
	
	public Vote(int id, int room_id, String who) {
		this.id = id;
		this.room_id = room_id;
		this.who = who;
	}
	
	public Vote(int room_id, String who) {
		this.room_id = room_id;
		this.who = who;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
