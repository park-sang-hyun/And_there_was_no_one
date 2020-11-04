package com.ssafy.pjt3.service;

import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ssafy.pjt3.dao.AuthTokenDao;

@Service
public class AuthTokenServiceImpl implements AuthTokenService {
	@Autowired
	AuthTokenDao authTokenDao;
	
	@Override
	public int isLogin(int user_id) throws SQLException {
		return authTokenDao.isLogin(user_id);
	}
}
