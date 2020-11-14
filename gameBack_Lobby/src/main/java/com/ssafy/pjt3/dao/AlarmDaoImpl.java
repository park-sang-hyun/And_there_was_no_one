package com.ssafy.pjt3.dao;

import java.sql.SQLException;
import java.util.List;

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
		session.insert("alarm_mapper.send", alarm);
	}

	@Override
	public List<Alarm> receive(int user_id) throws SQLException {
		return session.selectList("alarm_mapper.receive", user_id);
	}

	@Override
	public void delete(int alarm_id) throws SQLException {
		// TODO Auto-generated method stub
		session.delete("alarm_mapper.alarmDelete",alarm_id);
	}

	@Override
	public Alarm findOne(int alarm_id) throws SQLException {
		// TODO Auto-generated method stub
		return session.selectOne("alarm_mapper.findOne", alarm_id);
	}
}
