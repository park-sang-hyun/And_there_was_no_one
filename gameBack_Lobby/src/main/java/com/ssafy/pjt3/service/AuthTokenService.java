package com.ssafy.pjt3.service;

import java.sql.SQLException;

public interface AuthTokenService {
	public int isLogin(int user_id) throws SQLException;
}
