package com.ssafy.pjt3.service;

import java.sql.SQLException;

import com.ssafy.pjt3.dto.Alarm;

public interface AlarmService {
	public void send(Alarm alarm) throws SQLException;
}
