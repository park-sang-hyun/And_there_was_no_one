package com.ssafy.pjt3.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pjt3.dto.User;
import com.ssafy.pjt3.model.LoginRequest;
import com.ssafy.pjt3.service.JwtService;
import com.ssafy.pjt3.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	@GetMapping("/friend/make")
	public void makeFriend() {
		
	}
	
	@PostMapping("/login")
	@ApiOperation(value = "로그인 ", notes = "로그인 기능을 구현")
	public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request,
			HttpServletResponse res) {

		System.out.println("username: " + request.getUsername());
		System.out.println("password: " + request.getPassword());

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		
		User u1 = null;
		
		try {
			u1 = userService.login(request);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (u1 != null) {
			String token = jwtService.create(u1.getUsername(), u1.getNickname());
			res.setHeader("jwt-auth-token", token);

			resultMap.put("token", token);
			resultMap.put("status", true);
			resultMap.put("username", u1.getUsername());
			resultMap.put("nickname", u1.getNickname());
			status = HttpStatus.ACCEPTED;

			System.out.println("로그인 성공");
		} else {
			resultMap.put("message", "로그인 실패");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
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
