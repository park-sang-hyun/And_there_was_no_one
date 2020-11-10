package com.ssafy.pjt3.dao;

import java.sql.SQLException;

import com.ssafy.pjt3.dto.Alarm;

public interface AlarmDao {
	public void send(Alarm alarm) throws SQLException;
}
