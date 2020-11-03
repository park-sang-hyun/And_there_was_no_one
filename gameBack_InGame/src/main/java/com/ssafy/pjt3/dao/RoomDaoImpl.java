package com.ssafy.pjt3.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.pjt3.dto.Room;
import com.ssafy.pjt3.dto.User;
import com.ssafy.pjt3.dto.UserRoom;

@Repository
public class RoomDaoImpl implements RoomDao {
	@Autowired
	private SqlSession session;

	@Override
	public List<Room> findAll() throws SQLException {
		return session.selectList("room_mapper.selectAll");
	}

	@Override
	public void createRoom(Room room) throws SQLException {
		session.insert("room_mapper.create", room);
	}

	@Override
	public void enterRoom(UserRoom userroom) throws SQLException {
		session.insert("room_mapper.enterRoom", userroom);
		session.update("room_mapper.curCountPlus", userroom.getRoom_id());
	}

	@Override
	public int findRoomPkId(int user_id) throws SQLException {
		return session.selectOne("room_mapper.findRoomPkId", user_id);
	}
	
	@Override
	public Room findRoomWithUserid(int user_id) throws SQLException {
		return session.selectOne("room_mapper.findRoomWithUserid", user_id);
	}

	@Override
	public List<User> findUserInRoom(int room_id) throws SQLException {
		return session.selectList("room_mapper.findUserInRoom", room_id);
	}

	@Override
	public void mandateLeader(int giveUser, int takeUser) throws SQLException {
		session.update("room_mapper.takeLeader", takeUser);
		session.update("room_mapper.giveLeader", giveUser);
	}

	@Override
	public void leaveRoom(int user_id) throws SQLException {
		session.update("room_mapper.curCountMinus",user_id);
		session.delete("room_mapper.leaveRoom", user_id);
	}

	@Override
	public void deleteRoom(int room_id) throws SQLException {
		session.delete("room_mapper.deleteRoom", room_id);
	}

	@Override
	public void modifyRoom(Room room) throws SQLException {
		session.update("room_mapper.modifyRoom", room);
	}

	@Override
	public void kickoutUser(int user_id) throws SQLException {
		session.update("room_mapper.curCountMinus",user_id);
		session.delete("room_mapper.leaveRoom", user_id);
	}

	@Override
	public void startGame(int room_id) throws SQLException {
		session.update("room_mapper.startGame", room_id);	
	}
}
