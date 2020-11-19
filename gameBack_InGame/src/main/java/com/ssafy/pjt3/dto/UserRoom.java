package com.ssafy.pjt3.dto;

public class UserRoom {
	int id;
	boolean leader;
	int user_id;
	int room_id;
	
	public UserRoom() {}
	
	public UserRoom(int id, boolean leader, int user_id, int room_id) {
		this.id = id;
		this.leader = leader;
		this.user_id = user_id;
		this.room_id = room_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isLeader() {
		return leader;
	}

	public void setLeader(boolean leader) {
		this.leader = leader;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getRoom_id() {
		return room_id;
	}

	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}
}
