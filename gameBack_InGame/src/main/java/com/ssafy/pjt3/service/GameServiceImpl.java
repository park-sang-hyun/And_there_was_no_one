package com.ssafy.pjt3.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.pjt3.dao.GameDao;
import com.ssafy.pjt3.dto.Room;
import com.ssafy.pjt3.dto.User;
import com.ssafy.pjt3.dto.UserRoom;

@Service
public class GameServiceImpl implements GameService {
	@Autowired
	GameDao roomDao;

	@Override
	public int findRoomPkId(int user_id) throws SQLException {
		return roomDao.findRoomPkId(user_id);
	}
	
	@Override
	public Room findRoomWithUserid(int user_id) throws SQLException {
		return roomDao.findRoomWithUserid(user_id);
	}
	
	@Override
	public Room findRoomWithRoomid(int room_id) throws SQLException {
		return roomDao.findRoomWithRoomid(room_id);
	}

	@Override
	public List<User> findUserInRoom(int room_id) throws SQLException {
		return roomDao.findUserInRoom(room_id);
	}

	@Override
	public void mandateLeader(int giveUser, int takeUser) throws SQLException {
		roomDao.mandateLeader(giveUser, takeUser);
	}

	@Override
	public void leaveRoom(int user_id) throws SQLException {
		roomDao.leaveRoom(user_id);
	}

	@Override
	public void deleteRoom(int room_id) throws SQLException {
		roomDao.deleteRoom(room_id);
	}

	@Override
	public void modifyRoom(Room room) throws SQLException {
		roomDao.modifyRoom(room);
	}

	@Override
	public void kickoutUser(int user_id) throws SQLException {
		roomDao.leaveRoom(user_id);
	}

	@Override
	public void startGame(int room_id) throws SQLException {
		roomDao.startGame(room_id);
	}
}
