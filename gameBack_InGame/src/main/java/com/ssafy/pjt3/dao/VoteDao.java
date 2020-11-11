package com.ssafy.pjt3.dao;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.pjt3.dto.Vote;

public interface VoteDao {
	public void deleteVote(int room_id) throws SQLException;
	public void insertVote(Vote v) throws SQLException;
	public List<String> selectVote(int room_id) throws SQLException;
}
