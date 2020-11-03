package com.ssafy.pjt3.dao;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.pjt3.dto.Room;
import com.ssafy.pjt3.dto.User;

public interface GameDao {
	public int findRoomPkId(int user_id) throws SQLException;
	public Room findRoomWithUserid(int user_id) throws SQLException;
	public Room findRoomWithRoomid(int room_id) throws SQLException;
	public List<User> findUserInRoom(int room_id) throws SQLException;
	public void mandateLeader(int giveUser, int takeUser) throws SQLException;
	public void leaveRoom(int user_id) throws SQLException;
	public void deleteRoom(int room_id) throws SQLException;
	public void modifyRoom(Room room) throws SQLException;
	public void kickoutUser(int user_id) throws SQLException;
	public void startGame(int room_id) throws SQLException;
	public void endGame(int room_id) throws SQLException;
}
