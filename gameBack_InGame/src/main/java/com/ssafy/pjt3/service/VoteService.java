package com.ssafy.pjt3.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.pjt3.model.VoteData;

public interface VoteService {
	public void insertVote(int room_id, String who) throws SQLException;
	public void deleteVote(int room_id) throws SQLException;
	public List<VoteData> seletVote(int room_id) throws SQLException;
}
