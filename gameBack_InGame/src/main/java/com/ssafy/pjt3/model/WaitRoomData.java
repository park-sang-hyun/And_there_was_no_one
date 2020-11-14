package com.ssafy.pjt3.model;

import java.util.List;

import com.ssafy.pjt3.dto.User;

public class WaitRoomData {
	int id;
	String title;
	int max_count;
	int cur_count;
	int mode;
	int difficulty;
	boolean start;
	User leader;
	
	List<UserData> userList;

	public WaitRoomData(){}

	public WaitRoomData(int id, String title, int max_count, int cur_count, int mode, int difficulty, boolean start,
			User leader, List<UserData> userList) {
		this.id = id;
		this.title = title;
		this.max_count = max_count;
		this.cur_count = cur_count;
		this.mode = mode;
		this.difficulty = difficulty;
		this.start = start;
		this.leader = leader;
		this.userList = userList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getMax_count() {
		return max_count;
	}

	public void setMax_count(int max_count) {
		this.max_count = max_count;
	}

	public int getCur_count() {
		return cur_count;
	}

	public void setCur_count(int cur_count) {
		this.cur_count = cur_count;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public User getLeader() {
		return leader;
	}

	public void setLeader(User leader) {
		this.leader = leader;
	}

	public List<UserData> getUserList() {
		return userList;
	}

	public void setUserList(List<UserData> userList) {
		this.userList = userList;
	}
}
