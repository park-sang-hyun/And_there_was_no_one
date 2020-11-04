package com.ssafy.pjt3.service;

import java.sql.SQLException;

import com.ssafy.pjt3.dto.User;
import com.ssafy.pjt3.model.LoginRequest;

public interface UserService {
    public User login(LoginRequest request) throws SQLException;
	public void signUp(User user) throws SQLException;
	public int findPkId(String username) throws SQLException;
	public boolean isLeader(int user_id) throws SQLException;
}
