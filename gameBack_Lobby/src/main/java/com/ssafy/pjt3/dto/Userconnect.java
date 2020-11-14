package com.ssafy.pjt3.dto;

public class Userconnect {
	int id;
	int user_id;
	int friend_id;
	
	public Userconnect() {
		
	}
	
	public Userconnect(int id, int user_id, int friend_id) {
		this.id = id;
		this.user_id = user_id;
		this.friend_id = friend_id;
	}
	public Userconnect(int user_id, int friend_id) {
		this.user_id = user_id;
		this.friend_id = friend_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getFriend_id() {
		return friend_id;
	}
	public void setFriend_id(int friend_id) {
		this.friend_id = friend_id;
	}
	
	
}
