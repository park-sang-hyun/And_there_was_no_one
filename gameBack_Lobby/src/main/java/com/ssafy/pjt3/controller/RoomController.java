package com.ssafy.pjt3.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pjt3.dto.Room;
import com.ssafy.pjt3.dto.User;
import com.ssafy.pjt3.dto.UserRoom;
import com.ssafy.pjt3.model.BasicResponse;
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
			//for (int i = 0; i < list.size(); i++) {
			//	System.out.println("title: " + list.get(i).getTitle());
			//}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
	
	@PostMapping("/create")
	public Object create(Room room, @RequestParam String username) {
		final BasicResponse result = new BasicResponse();
		
		try {
			roomService.createRoom(room);
			int room_id = room.getId();
			int user_id = userService.findPkId(username);
			//System.out.println("roompk : " + room_id + "  userpk : " + user_id);
			
			UserRoom userroom = new UserRoom();
			
			userroom.setLeader(true);
			userroom.setUser_id(user_id);
			userroom.setRoom_id(room_id);
			
			// 게임방에 들어가고, 방의 현재 인원수 1증가
			roomService.enterRoom(userroom);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.status = true;
        result.data = "방 생성 완료";
        return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("leave/{username}")
	public void leave(@PathVariable String username) {
		try {
			int user_id = userService.findPkId(username);
			boolean isLeader = userService.isLeader(user_id);
			int room_id = roomService.findRoomPkId(user_id);
			
			//System.out.println("userpkid : " + user_id);
			//System.out.println("isLeader : " + isLeader);
			//System.out.println("roompkid : " + room_id);
			
			List<User> userList = roomService.findUserInRoom(room_id);
			
			if(isLeader == true) {
				//게임방에 유저가 2명 이상이면 방장을 위임하고 나가고, 본인 한명 뿐이면 그냥 나가게 된다
				if(userList.size() > 1) {
					//방장을 위임할 유저 탐색(점수 기준)
					User mandateUser = new User();
					mandateUser.setScore(-1);
					
					for(int i=0; i<userList.size(); i++) {
						if(userList.get(i).getId() != user_id && userList.get(i).getScore() > mandateUser.getScore()) mandateUser = userList.get(i);
					}
					
					//방장이 게임방 안의 가장 점수가 높은사람에게 방장 위임
					roomService.mandateLeader(user_id, mandateUser.getId());
					
					//게임방 퇴장하기
					roomService.leaveRoom(user_id);
				}else {
					//게임방 퇴장하기
					roomService.leaveRoom(user_id);
					
					//게임방 삭제하기
					roomService.deleteRoom(room_id);
				}		
			}else {
				//게임방 퇴장하기
				roomService.leaveRoom(user_id);
			}
			
		}catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@GetMapping("/enter/{username}/{leader_username}")
	public Object enter(@PathVariable String username, @PathVariable String leader_username) {
		final BasicResponse result = new BasicResponse();
		
		try {
			int leader_id = userService.findPkId(leader_username);
			Room leader_room = roomService.findRoomWithUserid(leader_id);
			int user_id = userService.findPkId(username);
			
			if(leader_room.getMax_count() == leader_room.getCur_count()) {
				result.status = false;
                result.data = "방에 인원이 가득 찼습니다.";
                return new ResponseEntity<>(result, HttpStatus.OK);
			}
			else if(leader_room.isStart()==true) {
				result.status = false;
                result.data = "이미 게임이 시작된 방입니다.";
                return new ResponseEntity<>(result, HttpStatus.OK);
			}
			
			UserRoom userroom = new UserRoom();
			
			userroom.setLeader(false);
			userroom.setUser_id(user_id);
			userroom.setRoom_id(leader_room.getId());
			
			// 게임방에 들어가고, 방의 현재 인원수 1증가
			roomService.enterRoom(userroom);
		}catch(SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.status = true;
        result.data = "방 입장 완료";
        return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PutMapping("modify/{username}")
	public Object modify(@PathVariable String username, @RequestParam String title, int max_count, int mode, int difficulty) {
		final BasicResponse result = new BasicResponse();
		
		try {
			int user_id = userService.findPkId(username);
			Room room = roomService.findRoomWithUserid(user_id);

			room.setTitle(title);
			room.setMax_count(max_count);
			room.setMode(mode);
			room.setDifficulty(difficulty);
			
			roomService.modifyRoom(room);
		}catch(SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.status = true;
        result.data = "방 수정 완료";
        return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PutMapping("mandate/{username}/{leader_username}")
	public Object mandate(@PathVariable String username, @PathVariable String leader_username) {
		final BasicResponse result = new BasicResponse();
		
		try {
			int user_id = userService.findPkId(username);
			int leader_user_id = userService.findPkId(leader_username);
			
			roomService.mandateLeader(leader_user_id, user_id);
		}catch(SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.status = true;
        result.data = "방장 위임 완료";
        return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("kickout/{username}")
	public Object kickout(@PathVariable String username) {
		final BasicResponse result = new BasicResponse();
		
		try {
			int user_id = userService.findPkId(username);
			
			roomService.kickoutUser(user_id);
		}catch(SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.status = true;
        result.data = "강제 퇴장 완료";
        return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PutMapping("start/{room_id}")
	public void start(@PathVariable int room_id) {
		try {
			roomService.startGame(room_id);
		}catch(SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
