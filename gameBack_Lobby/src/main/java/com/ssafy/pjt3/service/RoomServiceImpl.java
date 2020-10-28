package com.ssafy.pjt3.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.pjt3.dao.RoomDao;
import com.ssafy.pjt3.dto.Room;

@Service
public class RoomServiceImpl implements RoomService {
	
	@Autowired
	RoomDao roomDao;

	@Override
	public List<Room> findAll() throws SQLException {
		return roomDao.findAll();
	}
}
