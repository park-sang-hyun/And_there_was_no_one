package com.ssafy.pjt3.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pjt3.dto.Room;
import com.ssafy.pjt3.dto.Topic;
import com.ssafy.pjt3.dto.User;
import com.ssafy.pjt3.model.BasicResponse;
import com.ssafy.pjt3.model.GameData;
import com.ssafy.pjt3.model.UserData;
import com.ssafy.pjt3.model.WaitRoomData;
import com.ssafy.pjt3.service.GameService;
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
@RequestMapping("/game")
public class GameController {
	@Autowired
	private GameService gameService;
	@Autowired
	private UserService userService;
	
	@GetMapping("waitroom/{room_id}")
	@ApiOperation(value = "게임방 내용 조회", notes = "게임이 시작했을 때 필요한 정보들을 넘겨줌")
	public WaitRoomData wait(@PathVariable int room_id) {
		Room room = new Room();
		List<User> userList = new ArrayList<>();
		List<UserData> userListWithRank = new ArrayList<>();
		User leader = new User();
		WaitRoomData waitroomdata = new WaitRoomData();
		
		try {
			room = gameService.findRoomWithRoomid(room_id);
			userList = gameService.findUserInRoom(room_id);
			leader = userService.getLeader(room_id);
			
			waitroomdata.setId(room.getId());
			waitroomdata.setTitle(room.getTitle());
			waitroomdata.setCur_count(room.getCur_count());
			waitroomdata.setMax_count(room.getMax_count());
			waitroomdata.setDifficulty(room.getDifficulty());
			waitroomdata.setMode(room.getMode());
			waitroomdata.setStart(room.isStart());
			waitroomdata.setLeader(leader);
			
			for(int i=0; i< userList.size(); i++) {
				UserData ud = new UserData();
				
				ud.setId(userList.get(i).getId());
				ud.setUsername(userList.get(i).getUsername());
				ud.setNickname(userList.get(i).getNickname());
				ud.setPlaycount(userList.get(i).getPlaycount());
				ud.setScore(userList.get(i).getScore());
				ud.setWincount(userList.get(i).getWincount());
				ud.setRank(userService.getRank(userList.get(i).getId()));
				
				userListWithRank.add(ud);
			}
			
			waitroomdata.setUserList(userListWithRank);
		}catch(SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return waitroomdata;
	}
	
	@GetMapping("ingame/{room_id}")
	@ApiOperation(value = "게임 시작", notes = "게임이 시작했을 때 필요한 정보들을 넘겨줌")
	public Object ingame(@PathVariable int room_id) {
		Room room = new Room();
		List<User> userList = new ArrayList<>();
		List<User> userListSuffle = new ArrayList<>();
		List<Integer> suffleNumber = new ArrayList<Integer>();
		List<Topic> topicList = new ArrayList<>();
		List<String> wordList = new ArrayList<>();
		Topic topic = new Topic();
		String word;
		User shadow = new User();
		GameData gamedata = new GameData();
		
		try {
			// 게임상태를 true로 변경
			gameService.startGame(room_id);
			
			room = gameService.findRoomWithRoomid(room_id);
			userList = gameService.findUserInRoom(room_id);
			
			// 주제 뽑기
			topicList = gameService.getTopic();
			Random rand = new Random();
			
			int topicIndex = 0;
			if(topicList.size() > 0) topicIndex = rand.nextInt(topicList.size());
			topic = topicList.get(topicIndex);
			
			// 제시어 뽑기
			wordList = gameService.getWord(topic.getId());
			rand = new Random();
			
			int wordIndex = 0;
			if(wordList.size() > 0)  wordIndex = rand.nextInt(wordList.size());
			word = wordList.get(wordIndex);
			
			// 그림자 뽑기
			rand = new Random();
			
			int shadowIndex = 0;
			if(userList.size() > 0) shadowIndex = rand.nextInt(userList.size());
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
			
			gamedata.setTopic(topic.getName());
			gamedata.setWord(word);
			
			gamedata.setShadow(shadow);
			gamedata.setUserList(userListSuffle);
			
		}catch(SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return gamedata;
	}
	
	@PutMapping("/exitgame/{room_id}")
	@ApiOperation(value = "게임 끝", notes = "게임이 끝났을 때 필요한 정보를 변경")
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
	
	@DeleteMapping("/leave/{user_id}")
	@ApiOperation(value = "방 나가기", notes = "방 나가기 기능을 구현(방장일 때 1. 유저가 있으면 방장 위임하고 나가기 2. 없으면 방 제거 / 일반유저일 때 나가기)")
	public Object leave(@PathVariable int user_id) {
		final BasicResponse result = new BasicResponse();
		
		try {
			boolean isLeader = userService.isLeader(user_id);
			System.out.println("isLeader: " + isLeader);
			int room_id = gameService.findRoomPkId(user_id);
			
			List<User> userList = gameService.findUserInRoom(room_id);
			System.out.println("userlist: "+ userList);
			
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
					
					result.status = true;
			        result.data = "방장이 방장권한을 위임하고 나가기 완료";
			        result.object = mandateUser;
				}else {
					//게임방 퇴장하기
					gameService.leaveRoom(user_id);
					
					//게임방 삭제하기
					gameService.deleteRoom(room_id);
					
					result.status = true;
			        result.data = "방이 없어지고 방장이 나가기 완료";
				}		
			}else {
				//게임방 퇴장하기
				gameService.leaveRoom(user_id);
				
				result.status = true;
		        result.data = "일반유저 방 나가기 완료";
			}
			
		}catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PutMapping("/modify/{room_id}")
	@ApiOperation(value = "방 수정", notes = "방 정보를 수정하는 기능 구현(방 제목, 최대 인원 수, 게임 모드, 게임 난이도)")
	public Object modify(@PathVariable int room_id, @RequestParam String title, int max_count, int mode, int difficulty) {
		final BasicResponse result = new BasicResponse();
		
		try {
			Room room = gameService.findRoomWithRoomid(room_id);

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
	
	@PutMapping("/mandate/{user_id}/{leader_user_id}")
	@ApiOperation(value = "방장 위임", notes = "방장 위임 기능 구현")
	public Object mandate(@PathVariable int user_id, @PathVariable int leader_user_id) {
		final BasicResponse result = new BasicResponse();
		
		try {
			
			gameService.mandateLeader(leader_user_id, user_id);
		}catch(SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.status = true;
        result.data = "방장 위임 완료";
        return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("/kickout/{user_id}")
	@ApiOperation(value = "유저 강제 퇴장", notes = "방장이 유저를 강제 퇴장 시키는 기능을 구현")
	public Object kickout(@PathVariable int user_id) {
		final BasicResponse result = new BasicResponse();
		
		try {
			gameService.kickoutUser(user_id);
		}catch(SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.status = true;
        result.data = "강제 퇴장 완료";
        return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/score/{user_id}/{score}/{win}")
	@ApiOperation(value = "게임이 끝난 후 점수 변경", notes = "게임이 끝난 후 점수 변경하는 기능을 구현")
	public Object score(@PathVariable int user_id, int score, boolean win) {
		final BasicResponse result = new BasicResponse();
		User user = new User();
		
		try {
			user = userService.getUserwithUserId(user_id);
			user.setPlaycount(user.getPlaycount() + 1);
			user.setScore(score);
			if(win == true) user.setWincount(user.getWincount() + 1);
			
			userService.changeScore(user);
		}catch(SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.status = true;
        result.data = "점수 변경 완료";
        return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
