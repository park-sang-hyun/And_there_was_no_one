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
import com.ssafy.pjt3.dto.Room;
import com.ssafy.pjt3.dto.User;
import com.ssafy.pjt3.dto.UserRoom;
import com.ssafy.pjt3.dto.Userconnect;
import com.ssafy.pjt3.model.BasicResponse;
import com.ssafy.pjt3.model.LoginCheck;
import com.ssafy.pjt3.model.UserData;
import com.ssafy.pjt3.service.AlarmService;
import com.ssafy.pjt3.service.RoomService;
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

	@Autowired
	private RoomService roomService;

	@PostMapping("/friend/make")
	@ApiOperation(value = "친구 만들기", notes = "친구만들기 기능 구현.")
	public Object makeFriend(@RequestParam int user_id, int friend_id, int alarm_id) {
		final BasicResponse result = new BasicResponse();

		try {

			Alarm al = alarmService.findOne(alarm_id);

			String str = al.getContent();

			String[] str1 = str.split(" ");

			if (str1[0].equals("GameRoom")) {
				int room_id = Integer.parseInt(str1[1]);

				try {

					Room room = roomService.findRoomWithRoomid(room_id);
					if (room.getMax_count() == room.getCur_count()) {
						result.status = false;
						result.data = "방에 인원이 가득 찼습니다.";
						return new ResponseEntity<>(result, HttpStatus.OK);
					} else if (room.isStart() == true) {
						result.status = false;
						result.data = "이미 게임이 시작된 방입니다.";
						return new ResponseEntity<>(result, HttpStatus.OK);
					}

					UserRoom userroom = new UserRoom();

					userroom.setLeader(false);
					userroom.setUser_id(user_id);
					userroom.setRoom_id(room_id);

					// 게임방에 들어가고, 방의 현재 인원수 1증가
					roomService.enterRoom(userroom);

				} catch (SQLException e) {
					result.status = false;
					result.data = "이미 없어진 방입니다.";
					return new ResponseEntity<>(result, HttpStatus.OK);
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
				System.out.println("나는 게임 초대다" + room_id);
				result.object = room_id;
				result.status = false;
			} else {
				System.out.println("친구수락!!" + friend_id);
				System.out.println("친구수락!!" + user_id);
				userService.makeFriend(new Userconnect(user_id, friend_id));
				userService.makeFriend(new Userconnect(friend_id, user_id));
				result.status = true;
				System.out.println("친구수락");
			}

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
		List<UserData> login = new ArrayList<>();
		List<UserData> logout = new ArrayList<>();
		List<LoginCheck> list1 = new ArrayList<LoginCheck>();

		try {
			System.out.println("친구리스트!!");
			list = userService.listFriend(user_id);
			for (int i = 0; i < list.size(); i++) {

				int a = userService.loginCheck(list.get(i).getFriend_id());
				System.out.println("login check: " + a);

				User u = userService.findUser(list.get(i).getFriend_id());

				int flag = 0;
				
				for (int k = 0; k < login.size(); k++) {
					if (login.get(k).getNickname().equals(u.getNickname())) {
						flag = 1;
						break;
					}
				}
				for (int k = 0; k < logout.size(); k++) {
					if (logout.get(k).getNickname().equals(u.getNickname())) {
						flag = 1;
						break;
					}
				}

				if (flag == 0) {
					UserData ud = new UserData();

					ud.setId(u.getId());
					ud.setUsername(u.getUsername());
					ud.setNickname(u.getNickname());
					ud.setPlaycount(u.getPlaycount());
					ud.setScore(u.getScore());
					ud.setWincount(u.getWincount());
					ud.setRank(userService.getRank(u.getId()));

					if (a == 0) {

						logout.add(ud);
					} else {
						login.add(ud);
					}
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
		System.out.println(user_id);
		try {
			System.out.println("친구리스트!!");
			list = userService.listFriend(user_id);
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).getFriend_id());
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
	
	@GetMapping("/rank/listcount")
	@ApiOperation(value = "전체 유저 수 세기", notes = "전체 유저 수 세기 기능 구현")
	public int usercount() {
		List<User> userList = new ArrayList<>();
		int listcount = 0;
		try {
			userList = userService.findAllUser();
			
			listcount = userList.size();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listcount;
	}
	
	@GetMapping("/rank/list/{page}")
	@ApiOperation(value = "랭킹 목록", notes = "친구 목록 보여주기 기능 구현")
	public List<UserData> rank(@PathVariable int page) {
		List<User> userList = new ArrayList<>();
		List<UserData> rank = new ArrayList<>();
		List<UserData> pagenavigation = new ArrayList<>();

		try {
			userList = userService.findAllUser();
			
			for (int i = 0; i < userList.size(); i++) {
				UserData ud = new UserData();
				
				ud.setId(userList.get(i).getId());
				ud.setUsername(userList.get(i).getUsername());
				ud.setNickname(userList.get(i).getNickname());
				ud.setPlaycount(userList.get(i).getPlaycount());
				ud.setScore(userList.get(i).getScore());
				ud.setWincount(userList.get(i).getWincount());
				ud.setRank(userService.getRank(userList.get(i).getId()));
				
				rank.add(ud);
			}
			
			int start = page * 10 - 10;
			int end = page * 10;
			
			if(end > rank.size()) end = rank.size();
			
			for(int i = start; i < end; i++) {
				pagenavigation.add(rank.get(i));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("rank 길이 : " + rank.size());

		return pagenavigation;
	}

}
