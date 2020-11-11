package com.ssafy.pjt3.dto;

public class Alarm {
	int id;
	int from_id;
	int to_id;
	String content;
	
	public Alarm() {}

	public Alarm(int id, int from_id, int to_id, String content) {
		this.id = id;
		this.from_id = from_id;
		this.to_id = to_id;
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFrom_id() {
		return from_id;
	}

	public void setFrom_id(int from_id) {
		this.from_id = from_id;
	}

	public int getTo_id() {
		return to_id;
	}

	public void setTo_id(int to_id) {
		this.to_id = to_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
