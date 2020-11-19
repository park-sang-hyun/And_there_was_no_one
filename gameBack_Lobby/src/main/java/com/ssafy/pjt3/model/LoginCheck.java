package com.ssafy.pjt3.model;

import java.util.List;

public class LoginCheck {
	List<UserData> login;	
	List<UserData> logout;
	
	public LoginCheck() {}

	public LoginCheck(List<UserData> login, List<UserData> logout) {
		this.login = login;
		this.logout = logout;
	}

	public List<UserData> getLogin() {
		return login;
	}

	public void setLogin(List<UserData> login) {
		this.login = login;
	}

	public List<UserData> getLogout() {
		return logout;
	}

	public void setLogout(List<UserData> logout) {
		this.logout = logout;
	}
}
