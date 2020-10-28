package com.ssafy.pjt3.dao;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.pjt3.dto.Room;

public interface RoomDao {
 public List<Room> findAll() throws SQLException;
}
