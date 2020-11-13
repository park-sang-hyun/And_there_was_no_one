package com.ssafy.pjt3.model;

import java.util.List;

import com.ssafy.pjt3.dto.User;

public class LoginCheck {
	List<User> login;
	List<User> logout;
	public LoginCheck(List<User> login, List<User> logout) {
		this.login = login;
		this.logout = logout;
	}
	public List<User> getLogin() {
		return login;
	}
	public void setLogin(List<User> login) {
		this.login = login;
	}
	public List<User> getLogout() {
		return logout;
	}
	public void setLogout(List<User> logout) {
		this.logout = logout;
	}
	
	
}
