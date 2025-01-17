package com.ssafy.pjt3.service;

import java.sql.SQLException;

import com.ssafy.pjt3.dto.User;

public interface UserService {
	public int findPkId(String username) throws SQLException;
	public boolean isLeader(int user_id) throws SQLException;
	public User getLeader(int room_id) throws SQLException;
	public User getUserwithUserId(int user_id) throws SQLException;
	public void changeScore(User user) throws SQLException;
	public String getRank(int user_id) throws SQLException;
}
