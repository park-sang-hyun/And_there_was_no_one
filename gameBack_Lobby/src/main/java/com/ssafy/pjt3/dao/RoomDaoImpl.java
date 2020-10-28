package com.ssafy.pjt3.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.pjt3.dto.Room;

@Repository
public class RoomDaoImpl implements RoomDao {
	@Autowired
	private SqlSession session;

	@Override
	public List<Room> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return session.selectList("room_mapper.selectAll");
	}

}
