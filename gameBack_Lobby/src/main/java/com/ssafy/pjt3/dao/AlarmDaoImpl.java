package com.ssafy.pjt3.dao;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.pjt3.dto.Alarm;

@Repository
public class AlarmDaoImpl implements AlarmDao{
	@Autowired
	private SqlSession session;

	@Override
	public void send(Alarm alarm) throws SQLException {
		System.out.println("!!!!");
		System.out.println(alarm);
		session.insert("alarm_mapper.send", alarm);
	}
}
