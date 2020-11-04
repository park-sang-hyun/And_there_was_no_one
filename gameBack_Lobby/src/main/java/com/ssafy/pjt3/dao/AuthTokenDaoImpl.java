package com.ssafy.pjt3.dao;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AuthTokenDaoImpl implements AuthTokenDao {
	@Autowired
	private SqlSession session;
	
	public int isLogin(int user_id) throws SQLException{
		return session.selectOne("authtoken_mapper.isLogin",user_id);
	}
}
