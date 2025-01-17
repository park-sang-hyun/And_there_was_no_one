package com.ssafy.pjt3.model;

import java.util.List;

import com.ssafy.pjt3.dto.User;

public class GameData {
	int id;
	String title;
	int max_count;
	int cur_count;
	int mode;
	int difficulty;
	boolean start;
	
	String topic;
	String word;
	
	User shadow;
	List<User> userList;

	public GameData() {}

	public GameData(int id, String title, int max_count, int cur_count, int mode, int difficulty, boolean start,
			String topic, String word, User shadow, List<User> userList) {
		this.id = id;
		this.title = title;
		this.max_count = max_count;
		this.cur_count = cur_count;
		this.mode = mode;
		this.difficulty = difficulty;
		this.start = start;
		this.topic = topic;
		this.word = word;
		this.shadow = shadow;
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

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public User getShadow() {
		return shadow;
	}

	public void setShadow(User shadow) {
		this.shadow = shadow;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
}
