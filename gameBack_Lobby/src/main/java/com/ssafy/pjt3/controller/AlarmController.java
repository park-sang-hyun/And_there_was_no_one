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
import com.ssafy.pjt3.model.AlarmData;
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
@RequestMapping("/alarm")
public class AlarmController {
	@Autowired
	private AlarmService alarmService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/send")
	@ApiOperation(value = "알림 보내기", notes = "알림 보내기 기능 구현")
	public Object send(@RequestParam int from_id, String to_ninckname, String content) {
		final BasicResponse result = new BasicResponse();
		Alarm alarm = new Alarm();
		
		try {
			int to_id = userService.findPkId(to_ninckname);
			alarm.setFrom_id(from_id);
			alarm.setTo_id(to_id);
			alarm.setContent(content);
			
			alarmService.send(alarm);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.status = true;
        result.data = "알림 보내기 완료";
        return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/receive/{user_id}")
	@ApiOperation(value = "알림 받기", notes = "알림 받기 기능 구현")
	public Object receive(@PathVariable int user_id) {
		List<Alarm> alarmList = new ArrayList<>();
		List<AlarmData> contents = new ArrayList<>();
		
		try {
			alarmList = alarmService.receive(user_id);
			for(int i=0; i<alarmList.size(); i++) {
				User u = userService.findUser(alarmList.get(i).getTo_id());
				contents.add(new AlarmData(alarmList.get(i).getId(), u.getId(), u.getNickname(), alarmList.get(i).getContent()));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return contents;
	}
	
	@DeleteMapping("/delete")
	@ApiOperation(value = "알림 삭제", notes = "알림 삭제 기능 구현")
	public void delete(@RequestParam int alarm_id) {
		try {
			alarmService.delete(alarm_id);
			System.out.println("알람 삭제");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
