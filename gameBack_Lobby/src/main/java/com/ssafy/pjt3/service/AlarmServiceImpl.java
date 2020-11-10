package com.ssafy.pjt3.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.pjt3.dao.AlarmDao;
import com.ssafy.pjt3.dto.Alarm;

@Service
public class AlarmServiceImpl implements AlarmService{
	@Autowired
	AlarmDao alarmDao;

	@Override
	public void send(Alarm alarm) throws SQLException {
		alarmDao.send(alarm);
	}

	@Override
	public List<Alarm> receive(int user_id) throws SQLException {
		return alarmDao.receive(user_id);
	}
}
