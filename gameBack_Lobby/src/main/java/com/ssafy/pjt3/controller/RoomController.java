package com.ssafy.pjt3.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pjt3.dto.Room;
import com.ssafy.pjt3.dto.User;
import com.ssafy.pjt3.dto.UserRoom;
import com.ssafy.pjt3.service.RoomService;
import com.ssafy.pjt3.service.UserService;

@RestController
@RequestMapping("/room")
public class RoomController {
	@Autowired
	private RoomService roomService;
	@Autowired
	private UserService userService;

	@GetMapping("/list")
	public List<Room> showList() {
		List<Room> list = new ArrayList<Room>();

		try {
			list = roomService.findAll();
			for (int i = 0; i < list.size(); i++) {
				System.out.println("title: " + list.get(i).getTitle());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
	
	@PostMapping("/create")
	public void roomCreate(Room room, @RequestParam String username) {
		try {
			roomService.createRoom(room);
			int room_id = room.getId();
			int user_id = userService.findPkId(username);
			System.out.println("roompk : " + room_id + "  userpk : " + user_id);
			
			UserRoom userroom = new UserRoom();
			
			userroom.setLeader(true);
			userroom.setUser_id(user_id);
			userroom.setRoom_id(room_id);
			
			roomService.connectUserToRoom(userroom);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@DeleteMapping("leave/{username}")
	public void roomLeave(@PathVariable String username) {
		try {
			int user_id = userService.findPkId(username);
			int room_id = roomService.findRoomPkId(user_id);
			System.out.println("userpkid : " + user_id + "  roompkid : " + room_id);
			
			List<User> userList = roomService.findUserInRoom(room_id);
			
			//게임방에 유저가 2명이상이면 방장을 위임하고 나가고, 본인 한명 뿐이면 그냥 나가게 된다
			if(userList.size() > 1) {
				//방장을 위임할 유저 탐색(점수 기준)
				User mandateUser = new User();
				mandateUser.setScore(-1);
				
				for(int i=0; i<userList.size(); i++) {
					if(userList.get(i).getId() != user_id && userList.get(i).getScore() > mandateUser.getScore()) mandateUser = userList.get(i);
				}
				
				//방장이 게임방 안의 가장 점수가 높은사람에게 방장 위임
				roomService.mandateLeader(user_id, mandateUser.getId());
			}else {
				
			}
			
		}catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
