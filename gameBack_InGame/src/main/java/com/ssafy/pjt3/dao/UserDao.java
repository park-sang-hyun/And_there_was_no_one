package com.ssafy.pjt3.dao;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.pjt3.dto.User;

public interface UserDao {
	public int findPkId(String username) throws SQLException;
	public boolean isLeader(int user_id) throws SQLException;
	public User getLeader(int room_id) throws SQLException;
	public User getUserwithUserId(int user_id) throws SQLException;
	public void changeScore(User user) throws SQLException;
	public List<User> findAllUser() throws SQLException;
}
