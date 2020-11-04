package com.ssafy.pjt3.dto;

import java.sql.Date;

public class AuthToken {
	String key;
	Date created;
	int user_id;
	
	public AuthToken() {
		
	}

	public AuthToken(String key, Date created, int user_id) {
		this.key = key;
		this.created = created;
		this.user_id = user_id;
	}

	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}
	
	public int getUser_id() {
		return user_id;
	}
	
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
}
