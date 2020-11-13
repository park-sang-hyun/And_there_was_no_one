package com.ssafy.pjt3.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.pjt3.dao.UserDao;
import com.ssafy.pjt3.dto.User;
import com.ssafy.pjt3.dto.Userconnect;

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

	@Override
	public void makeFriend(Userconnect uc) throws SQLException {
		// TODO Auto-generated method stub
		userDao.makeFriend(uc);
	}

	@Override
	public void deleteFriend(Userconnect uc) throws SQLException {
		// TODO Auto-generated method stub
		userDao.deleteFriend(uc);
	}

	@Override
	public List<Userconnect> listFriend(int user_id) throws SQLException {
		// TODO Auto-generated method stub
		return userDao.listFriend(user_id);
	}

	@Override
	public User findUser(int user_id) throws SQLException {
		// TODO Auto-generated method stub
		return userDao.findUser(user_id);
	}

	@Override
	public int loginCheck(int user_id) throws SQLException {
		// TODO Auto-generated method stub
		return userDao.loginCheck(user_id);		
	}

}
