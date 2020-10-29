package com.ssafy.pjt3.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.pjt3.dto.Room;
import com.ssafy.pjt3.dto.User;
import com.ssafy.pjt3.dto.UserRoom;

public interface RoomService  {
	public List<Room> findAll() throws SQLException;
	public void createRoom(Room room) throws SQLException;
	public void connectUserToRoom(UserRoom userroom) throws SQLException;
	public int findRoomPkId(int user_id) throws SQLException;
	public List<User> findUserInRoom(int room_id) throws SQLException;
	public void mandateLeader(int giveUser, int takeUser) throws SQLException;
}
