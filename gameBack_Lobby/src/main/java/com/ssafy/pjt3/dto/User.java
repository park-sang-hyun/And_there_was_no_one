package com.ssafy.pjt3.dto;

public class User {
	int id;
	String username;
	String nickname;
	String password;
	int playcount;
	int wincount;
	int score;
	
	public User() {}

	public User(int id, String username, String nickname, String password, int playcount, int wincount, int score) {
		this.id = id;
		this.username = username;
		this.nickname = nickname;
		this.password = password;
		this.playcount = playcount;
		this.wincount = wincount;
		this.score = score;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPlaycount() {
		return playcount;
	}

	public void setPlaycount(int playcount) {
		this.playcount = playcount;
	}

	public int getWincount() {
		return wincount;
	}

	public void setWincount(int wincount) {
		this.wincount = wincount;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
