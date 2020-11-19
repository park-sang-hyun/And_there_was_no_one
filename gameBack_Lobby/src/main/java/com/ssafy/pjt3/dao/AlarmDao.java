package com.ssafy.pjt3.dao;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.pjt3.dto.Alarm;

public interface AlarmDao {
	public void send(Alarm alarm) throws SQLException;
	public List<Alarm> receive(int user_id) throws SQLException;
	public void delete(int alarm_id) throws SQLException;
	
	public Alarm findOne(int alarm_id) throws SQLException;
}
