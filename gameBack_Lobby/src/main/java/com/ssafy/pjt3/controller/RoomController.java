package com.ssafy.pjt3.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pjt3.dto.Room;
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
			int roompkid = room.getId();
			int userpkid = userService.findPkId(username);
			System.out.println("roompk : " + roompkid + "  userpk : " + userpkid);
			
			UserRoom userroom = new UserRoom();
			
			userroom.setLeader(true);
			userroom.setUser_id(userpkid);
			userroom.setRoom_id(roompkid);
			
			roomService.connectUserToRoom(userroom);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
