package com.ssafy.pjt3.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.pjt3.dto.Room;
import com.ssafy.pjt3.dto.User;

@Repository
public class RoomDaoImpl implements RoomDao{
	@Autowired
	private SqlSession session;

	@Override
	public List<User> findUserInRoom(int room_id) throws SQLException {
		return session.selectList("room_mapper.findUserInRoom", room_id);
	}

	@Override
	public Room findRoomWithRoomid(int room_id) throws SQLException {
		return session.selectOne("room_mapper.findRoomWithRoomId", room_id);
	}

}
