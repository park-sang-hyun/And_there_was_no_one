package com.ssafy.pjt3.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.pjt3.dao.VoteDao;
import com.ssafy.pjt3.dto.Vote;

@Service
public class VoteServiceImpl implements VoteService{
	@Autowired
	VoteDao voteDao;
	
	@Override
	public void insertVote(Vote v) throws SQLException {
		// TODO Auto-generated method stub
		voteDao.insertVote(v);
	}

	@Override
	public List<String> seletVote(int room_id) throws SQLException {
		// TODO Auto-generated method stub
		return voteDao.selectVote(room_id);
	}

	@Override
	public void deleteVote(int room_id) throws SQLException {
		// TODO Auto-generated method stub
		voteDao.deleteVote(room_id);
	}

}
