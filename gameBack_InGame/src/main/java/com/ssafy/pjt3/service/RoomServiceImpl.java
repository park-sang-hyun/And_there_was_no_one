package com.ssafy.pjt3.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.pjt3.dao.RoomDao;
import com.ssafy.pjt3.dto.Room;
import com.ssafy.pjt3.dto.User;

@Service
public class RoomServiceImpl implements RoomService{
	@Autowired
	RoomDao roomDao;

	@Override
	public List<User> findUserInRoom(int room_id) throws SQLException {
		return roomDao.findUserInRoom(room_id);
	}

	@Override
	public Room findRoomWithRoomid(int room_id) throws SQLException {
		// TODO Auto-generated method stub
		return roomDao.findRoomWithRoomid(room_id);
	}
}
