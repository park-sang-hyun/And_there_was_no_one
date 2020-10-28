package com.ssafy.pjt3.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.pjt3.dto.Room;

public interface RoomService  {
	List<Room> findAll() throws SQLException;
}
