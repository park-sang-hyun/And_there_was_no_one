package com.ssafy.pjt3.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pjt3.dto.Room;
import com.ssafy.pjt3.dto.User;
import com.ssafy.pjt3.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/friend/make")
	public void makeFriend() {
		
	}
	
//	@GetMapping("/friend/list") //친구 목록 보여주기(
//	public List<User> showFriend() {
//		List<User> list = new ArrayList<User>();
//
//		try {
//			list = userService.findAll();
//			for (int i = 0; i < list.size(); i++) {
//				System.out.println("title: " + list.get(i).getTitle());
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return list;
//	}
	
}
