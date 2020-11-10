package com.ssafy.pjt3.service;

import java.sql.SQLException;

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
	
}
