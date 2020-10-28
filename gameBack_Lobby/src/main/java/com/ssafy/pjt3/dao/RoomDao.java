package com.ssafy.pjt3.dao;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.pjt3.dto.Room;
import com.ssafy.pjt3.dto.UserRoom;

public interface RoomDao {
	public List<Room> findAll() throws SQLException;
	public void createRoom(Room room) throws SQLException;
	public void connectUserToRoom(UserRoom userroom) throws SQLException;
}
