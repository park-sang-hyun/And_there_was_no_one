package com.ssafy.pjt3.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.pjt3.dto.Room;
import com.ssafy.pjt3.dto.User;

public interface RoomService {
	public List<User> findUserInRoom(int room_id) throws SQLException;
	public Room findRoomWithRoomid(int room_id) throws SQLException;
}
