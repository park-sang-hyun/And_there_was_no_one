package com.ssafy.pjt3.dao;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.pjt3.dto.Vote;
import com.ssafy.pjt3.model.VoteData;

@Repository
public class VoteDaoImpl implements VoteDao{
	
	@Autowired
	private SqlSession session;
	
	@Override
	public void insertVote(Vote v) throws SQLException {
		// TODO Auto-generated method stub
		session.insert("vote_mapper.insertVote", v);
	}

	@Override
	public List<String> selectVote(int room_id) throws SQLException {
		// TODO Auto-generated method stub
		return session.selectList("vote_mapper.selectVote", room_id);
	}

	@Override
	public void deleteVote(int room_id) throws SQLException {
		// TODO Auto-generated method stub
		session.delete("vote_mapper.deleteVote", room_id);
	}

}
