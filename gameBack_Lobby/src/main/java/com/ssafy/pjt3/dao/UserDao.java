package com.ssafy.pjt3.dao;

import java.sql.SQLException;

public interface UserDao {
	public int findPkId(String username) throws SQLException;
}
