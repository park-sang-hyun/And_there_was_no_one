package com.ssafy.pjt3.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.pjt3.dto.User;

@Repository
public class UserDaoImpl implements UserDao{
	@Autowired
	private SqlSession session;	
	
	@Override
	public int findPkId(String username) throws SQLException {
		return session.selectOne("user_mapper.selectPkId", username);
	}

	@Override
	public boolean isLeader(int user_id) throws SQLException {
		return session.selectOne("user_mapper.isLeader", user_id);
	}

	@Override
	public User getLeader(int room_id) throws SQLException {
		return session.selectOne("user_mapper.getLeader", room_id);
	}

	@Override
	public User getUserwithUserId(int user_id) throws SQLException {
		return session.selectOne("user_mapper.getUserwithUserId", user_id);
	}

	@Override
	public void changeScore(User user) throws SQLException {
		session.update("user_mapper.changeScore", user);
		
	}

	@Override
	public List<User> findAllUser() throws SQLException {
		return session.selectList("user_mapper.findAllUser");
	}
}
