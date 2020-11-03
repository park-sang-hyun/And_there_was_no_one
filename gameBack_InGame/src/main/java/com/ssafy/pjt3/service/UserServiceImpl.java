package com.ssafy.pjt3.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.pjt3.dao.UserDao;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UserDao userDao;
	
	@Override
	public int findPkId(String username) throws SQLException {
		return userDao.findPkId(username);
	}

	@Override
	public boolean isLeader(int user_id) throws SQLException {
		return userDao.isLeader(user_id);
	}

}
