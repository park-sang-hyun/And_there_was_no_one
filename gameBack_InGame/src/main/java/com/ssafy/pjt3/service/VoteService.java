package com.ssafy.pjt3.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.pjt3.dto.Vote;

public interface VoteService {
	public void insertVote(Vote v) throws SQLException;
	public void deleteVote(int room_id) throws SQLException;
	public List<String> seletVote(int room_id) throws SQLException;
}
