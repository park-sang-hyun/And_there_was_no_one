package com.ssafy.pjt3.dao;

import java.sql.SQLException;

public interface AuthTokenDao {
	public int isLogin(int user_id) throws SQLException;
}
