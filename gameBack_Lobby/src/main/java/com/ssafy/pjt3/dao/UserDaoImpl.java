package com.ssafy.pjt3.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.pjt3.dto.User;
import com.ssafy.pjt3.dto.Userconnect;

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
	public void makeFriend(Userconnect uc) throws SQLException {
		// TODO Auto-generated method stub
		session.insert("user_mapper.friendAccecpt",uc);
	}

	@Override
	public void deleteFriend(Userconnect uc) throws SQLException {
		// TODO Auto-generated method stub
		session.insert("user_mapper.friendDelete",uc);
	}

	@Override
	public List<Userconnect> listFriend(int user_id) throws SQLException {
		// TODO Auto-generated method stub
		return session.selectList("user_mapper.friendList",user_id);
	}

	@Override
	public User findUser(int user_id) throws SQLException {
		// TODO Auto-generated method stub
		return session.selectOne("user_mapper.selectUserWithId", user_id);
	}

	@Override
	public int loginCheck(int user_id) throws SQLException {
		// TODO Auto-generated method stub
		if(session.selectOne("user_mapper.loginCheck", user_id) == null)
			return 0;
		else return session.selectOne("user_mapper.loginCheck", user_id);
	}
}
