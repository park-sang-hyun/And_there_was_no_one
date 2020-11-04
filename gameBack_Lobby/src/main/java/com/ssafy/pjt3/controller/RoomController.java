package com.ssafy.pjt3.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pjt3.dto.Room;
import com.ssafy.pjt3.dto.UserRoom;
import com.ssafy.pjt3.model.BasicResponse;
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
@RequestMapping("/room")
public class RoomController {
	@Autowired
	private RoomService roomService;
	@Autowired
	private UserService userService;
	
	@GetMapping("/listcount")
	@ApiOperation(value = "room 개수 세기", notes = "Lobby의 room의 개수를 세어줌.")
	public int listCount() {
		List<Room> list = new ArrayList<Room>();
		int listcount = 0;
		
		try {
			list = roomService.findAll();
			
			listcount = list.size();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listcount;
	}
	
	@GetMapping("/list/{page}")
	@ApiOperation(value = "room 분할 해서 리스트 보내기", notes = "room을 8개씩 분할 해서 리스트 보내기")
	public List<Room> showList(@PathVariable int page) {
		List<Room> list = new ArrayList<Room>();
		List<Room> pagenavigation = new ArrayList<Room>();

		try {
			list = roomService.findAll();
			
			int start = page * 8 - 8;
			int end = page * 8;
			
			if(end > list.size()) end = list.size();
			
			for(int i = start; i < end; i++) {
				pagenavigation.add(list.get(i));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pagenavigation;
	}
	
	@PostMapping("/create")
	@ApiOperation(value = "방 생성", notes = "방 생성 기능을 구현")
	public Object create(Room room, @RequestParam String username) {
		final BasicResponse result = new BasicResponse();
		
		try {
			roomService.createRoom(room);
			int room_id = room.getId();
			int user_id = userService.findPkId(username);
			
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
	
	@GetMapping("/enter/{username}/{leader_username}")
	@ApiOperation(value = "방 입장", notes = "방 입장 기능을 구현(유저 가득 찼을 때, 시작됬을 때, 방이 없을 때 고려)")
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
			result.status = false;
	        result.data = "이미 없어진 방입니다.";
	        return new ResponseEntity<>(result, HttpStatus.OK);
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		result.status = true;
        result.data = "방 입장 완료";
        return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
