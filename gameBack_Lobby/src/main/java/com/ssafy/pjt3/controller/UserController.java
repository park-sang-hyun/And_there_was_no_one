package com.ssafy.pjt3.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pjt3.dto.User;
import com.ssafy.pjt3.dto.Userconnect;
import com.ssafy.pjt3.model.BasicResponse;
import com.ssafy.pjt3.service.AlarmService;
import com.ssafy.pjt3.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

//@CrossOrigin(origins = { "http://localhost:3000" })
@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AlarmService alarmService;
	
	@PostMapping("/friend/make")
	@ApiOperation(value = "친구 만들기", notes = "친구만들기 기능 구현.")
	public void makeFriend(@RequestParam int user_id, int friend_id, int alarm_id) {
		try {
			System.out.println("친구수락!!");
			userService.makeFriend(new Userconnect(user_id, friend_id));
			System.out.println("친구수락");
			alarmService.delete(alarm_id);
			System.out.println("알람 삭제");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@DeleteMapping("/friend/delete")
	@ApiOperation(value = "친구 삭제", notes = "친구삭제 기능 구현.")
	public void deleteFriend(@RequestParam int user_id, int friend_id) {
		try {
			System.out.println("친구삭제!!");
			userService.deleteFriend(new Userconnect(user_id, friend_id));
			System.out.println("친구삭제");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@GetMapping("/friend/list")
	@ApiOperation(value = "친구 목록", notes = "친구 목록")
	public List<User> showFriend(@RequestParam int user_id) {
		List<Userconnect> list = new ArrayList<Userconnect>();
		List<User> list1 = new ArrayList<User>();
		
		try {
			System.out.println("친구리스트!!");
			list = userService.listFriend(user_id);
			for (int i = 0; i < list.size(); i++) {
				User u = userService.findUser(user_id);
				list1.add(u);
			}
			System.out.println("친구리스트");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list1;
	}
	
}
