package com.ssafy.pjt3.model;

public class AlarmData {
	int alarm_id;
	int to_id;
	String to_name;
	String content;
	public AlarmData(int alarm_id, int to_id, String to_name, String content) {
		super();
		this.alarm_id = alarm_id;
		this.to_id = to_id;
		this.to_name = to_name;
		this.content = content;
	}
	public int getAlarm_id() {
		return alarm_id;
	}
	public void setAlarm_id(int alarm_id) {
		this.alarm_id = alarm_id;
	}
	public int getTo_id() {
		return to_id;
	}
	public void setTo_id(int to_id) {
		this.to_id = to_id;
	}
	public String getTo_name() {
		return to_name;
	}
	public void setTo_name(String to_name) {
		this.to_name = to_name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
