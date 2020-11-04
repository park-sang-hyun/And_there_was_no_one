package com.ssafy.pjt3.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pjt3.dto.Room;
import com.ssafy.pjt3.dto.User;
import com.ssafy.pjt3.model.BasicResponse;
import com.ssafy.pjt3.model.GameData;
import com.ssafy.pjt3.service.GameService;
import com.ssafy.pjt3.service.UserService;

@RestController
@RequestMapping("/game")
public class GameController {
	@Autowired
	private GameService gameService;
	@Autowired
	private UserService userService;
	
	@GetMapping("ingame/{room_id}")
	public Object ingame(@PathVariable int room_id) {
		Room room = new Room();
		List<User> userList = new ArrayList<>();
		List<User> userListSuffle = new ArrayList<>();
		List<Integer> suffleNumber = new ArrayList<Integer>();
		User shadow = new User();
		GameData gamedata = new GameData();
		
		try {
			// 게임상태를 true로 변경
			gameService.startGame(room_id);
			
			room = gameService.findRoomWithRoomid(room_id);
			userList = gameService.findUserInRoom(room_id);
			
			// 그림자 뽑기
			Random rand = new Random();
			int shadowIndex = rand.nextInt(userList.size());
			shadow = userList.get(shadowIndex);
			
			// 그리기 순서 정하기
			for(int i = 0; i < userList.size(); i++) {
				suffleNumber.add(i);
			}
			
			Collections.shuffle(suffleNumber);
			
			for(int i = 0; i < userList.size(); i++) {
				userListSuffle.add(userList.get(suffleNumber.get(i)));
			}
			
			// 넘겨줄 model 채우기
			gamedata.setId(room.getId());
			gamedata.setTitle(room.getTitle());
			gamedata.setCur_count(room.getCur_count());
			gamedata.setMax_count(room.getMax_count());
			gamedata.setDifficulty(room.getDifficulty());
			gamedata.setMode(room.getMode());
			gamedata.setStart(room.isStart());
			
			gamedata.setShadow(shadow);
			gamedata.setUserList(userListSuffle);
			
		}catch(SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return gamedata;
	}
	
	@GetMapping("exitgame/{room_id}")
	public Object exitgame(@PathVariable int room_id) {
		final BasicResponse result = new BasicResponse();
		
		try {
			gameService.endGame(room_id);
		}catch(SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.status = true;
        result.data = "게임 종료";
        return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("leave/{username}")
	public void leave(@PathVariable String username) {
		try {
			int user_id = userService.findPkId(username);
			boolean isLeader = userService.isLeader(user_id);
			int room_id = gameService.findRoomPkId(user_id);
			
			List<User> userList = gameService.findUserInRoom(room_id);
			
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
					gameService.mandateLeader(user_id, mandateUser.getId());
					
					//게임방 퇴장하기
					gameService.leaveRoom(user_id);
				}else {
					//게임방 퇴장하기
					gameService.leaveRoom(user_id);
					
					//게임방 삭제하기
					gameService.deleteRoom(room_id);
				}		
			}else {
				//게임방 퇴장하기
				gameService.leaveRoom(user_id);
			}
			
		}catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@PutMapping("modify/{username}")
	public Object modify(@PathVariable String username, @RequestParam String title, int max_count, int mode, int difficulty) {
		final BasicResponse result = new BasicResponse();
		
		try {
			int user_id = userService.findPkId(username);
			Room room = gameService.findRoomWithUserid(user_id);

			room.setTitle(title);
			room.setMax_count(max_count);
			room.setMode(mode);
			room.setDifficulty(difficulty);
			
			gameService.modifyRoom(room);
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
			
			gameService.mandateLeader(leader_user_id, user_id);
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
			
			gameService.kickoutUser(user_id);
		}catch(SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.status = true;
        result.data = "강제 퇴장 완료";
        return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
