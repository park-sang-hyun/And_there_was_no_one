//package com.ssafy.pjt3.controller;
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.ssafy.pjt3.dto.Room;
//import com.ssafy.pjt3.dto.User;
//import com.ssafy.pjt3.model.BasicResponse;
//import com.ssafy.pjt3.service.UserService;
//
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
//
//@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
//        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
//        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
//        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })
//
////@CrossOrigin(origins = { "http://localhost:3000" })
//@CrossOrigin(origins = "*")
//
//@RestController
//@RequestMapping("/user")
//public class UserController {
//	
//	@Autowired
//	private UserService userService;
//	
//	@GetMapping("/friend/make")
//	@ApiOperation(value = "친구 만들기", notes = "친구만들기 기능 구현.")
//	public void makeFriend() {
//		
//	}
//	
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
//	
//}
