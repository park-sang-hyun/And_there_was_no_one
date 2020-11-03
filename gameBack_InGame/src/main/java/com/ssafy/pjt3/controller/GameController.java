package com.ssafy.pjt3.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pjt3.dto.Room;
import com.ssafy.pjt3.service.RoomService;
import com.ssafy.pjt3.service.UserService;

@RestController
@RequestMapping("/game")
public class GameController {
	@Autowired
	private RoomService roomService;
	@Autowired
	private UserService userService;

	@GetMapping("/list")
	public List<Room> showList() {
		List<Room> list = new ArrayList<Room>();

		try {
			list = roomService.findAll();
			//for (int i = 0; i < list.size(); i++) {
			//	System.out.println("title: " + list.get(i).getTitle());
			//}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
}
