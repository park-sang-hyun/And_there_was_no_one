package com.ssafy.pjt3.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.pjt3.dto.Alarm;

public interface AlarmService {
	public void send(Alarm alarm) throws SQLException;
	public List<Alarm> receive(int user_id) throws SQLException;
}
