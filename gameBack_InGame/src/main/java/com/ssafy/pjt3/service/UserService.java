package com.ssafy.pjt3.service;

import java.sql.SQLException;

public interface UserService {
	public int findPkId(String username) throws SQLException;
	public boolean isLeader(int user_id) throws SQLException;
}
