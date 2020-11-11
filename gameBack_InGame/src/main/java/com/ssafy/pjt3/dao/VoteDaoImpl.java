package com.ssafy.pjt3.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.pjt3.model.VoteData;

@Repository
public class VoteDaoImpl implements VoteDao{
	
	@Autowired
	private SqlSession session;
	
	@Override
	public void insertVote(int room_id, String who) throws SQLException {
		// TODO Auto-generated method stub
		VoteData vd = new VoteData(room_id, who);
		session.insert("vote_mapper.insertVote", vd);
	}

	@Override
	public List<VoteData> selectVote(int room_id) throws SQLException {
		// TODO Auto-generated method stub
		return session.selectList("vote_mapper.selectVote", room_id);
	}

	@Override
	public void deleteVote(int room_id) throws SQLException {
		// TODO Auto-generated method stub
		
	}

}
