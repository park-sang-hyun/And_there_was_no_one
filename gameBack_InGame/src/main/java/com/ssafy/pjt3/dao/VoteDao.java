package com.ssafy.pjt3.dao;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.pjt3.model.VoteData;

public interface VoteDao {
	public void deleteVote(int room_id) throws SQLException;
	public void insertVote(int room_id, String who) throws SQLException;
	public List<VoteData> selectVote(int room_id) throws SQLException;
}
