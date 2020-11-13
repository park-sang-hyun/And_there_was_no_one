package com.ssafy.pjt3.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pjt3.dto.Alarm;
import com.ssafy.pjt3.dto.User;
import com.ssafy.pjt3.dto.Userconnect;
import com.ssafy.pjt3.model.BasicResponse;
import com.ssafy.pjt3.model.LoginCheck;
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
	public Object makeFriend(@RequestParam int user_id, int friend_id, int alarm_id) {
		final BasicResponse result = new BasicResponse();

		try {
			System.out.println("친구수락!!" + friend_id);
			System.out.println("친구수락!!" + user_id);
			userService.makeFriend(new Userconnect(user_id, friend_id));
			userService.makeFriend(new Userconnect(friend_id, user_id));
			result.status = true;

			System.out.println("친구수락");
			alarmService.delete(alarm_id);
			System.out.println("알람 삭제");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		result.data = "알림 보내기 완료";
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@DeleteMapping("/friend/delete")
	@ApiOperation(value = "친구 삭제", notes = "친구삭제 기능 구현.")
	public void deleteFriend(@RequestParam int user_id, int friend_id) {
		try {
			System.out.println("친구삭제!!");
			userService.deleteFriend(new Userconnect(user_id, friend_id));
			userService.deleteFriend(new Userconnect(friend_id, user_id));
			System.out.println("친구삭제");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@GetMapping("/friend/list/{user_id}")
	@ApiOperation(value = "친구 목록", notes = "친구 목록")
	public List<LoginCheck> showFriend(@PathVariable int user_id) {
		List<Userconnect> list = new ArrayList<Userconnect>();
		List<User> login = new ArrayList<User>();
		List<User> logout = new ArrayList<User>();
		List<LoginCheck> list1 = new ArrayList<LoginCheck>();

		try {
			System.out.println("친구리스트!!");
			list = userService.listFriend(user_id);
			for (int i = 0; i < list.size(); i++) {

				int a = userService.loginCheck(list.get(i).getFriend_id());
				System.out.println("login check: " + a);

				if (a == 0) {
					User u = userService.findUser(list.get(i).getFriend_id());
					logout.add(u);
				} else {
					User u = userService.findUser(list.get(i).getFriend_id());
					login.add(u);
				}
			}
			System.out.println("친구리스트");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		list1.add(new LoginCheck(login, logout));
		return list1;
	}
	
	@GetMapping("/loginFriend/list/{user_id}")
	@ApiOperation(value = "친구 목록", notes = "친구 목록")
	public List<User> loginFriend(@PathVariable int user_id) {
		List<Userconnect> list = new ArrayList<Userconnect>();
		List<User> login = new ArrayList<User>();

		try {
			System.out.println("친구리스트!!");
			list = userService.listFriend(user_id);
			for (int i = 0; i < list.size(); i++) {

				int a = userService.loginCheck(list.get(i).getFriend_id());
				System.out.println("login check: " + a);

				if (a != 0) {
					User u = userService.findUser(list.get(i).getFriend_id());
					login.add(u);
				}
			}
			System.out.println("친구리스트");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return login;
	}

}
