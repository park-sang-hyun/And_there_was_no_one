package com.ssafy.pjt3.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.pjt3.dto.Room;
import com.ssafy.pjt3.dto.UserRoom;

@Repository
public class RoomDaoImpl implements RoomDao {
	@Autowired
	private SqlSession session;

	@Override
	public List<Room> findAll() throws SQLException {
		return session.selectList("room_mapper.selectAll");
	}

	@Override
	public void createRoom(Room room) throws SQLException {
		session.insert("room_mapper.create", room);
	}

	@Override
	public void connectUserToRoom(UserRoom userroom) throws SQLException {
		session.insert("room_mapper.connectUser", userroom);
	}
}
