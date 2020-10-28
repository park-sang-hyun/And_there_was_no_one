package com.ssafy.pjt3.dao;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao{
	@Autowired
	private SqlSession session;	
	
	@Override
	public int findPkId(String username) throws SQLException {
		return session.selectOne("user_mapper.selectPkId", username);
	}

}
