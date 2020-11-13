package com.ssafy.pjt3.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.pjt3.dto.User;
import com.ssafy.pjt3.dto.Userconnect;

public interface UserService {
	public int findPkId(String username) throws SQLException;
	public boolean isLeader(int user_id) throws SQLException;
	public User findUser(int user_id) throws SQLException;

	public void makeFriend(Userconnect uc) throws SQLException;
	public void deleteFriend(Userconnect uc) throws SQLException;
	public List<Userconnect> listFriend(int user_id) throws SQLException;

	public int loginCheck(int user_id) throws SQLException;
}
