package com.ssafy.pjt3.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pjt3.dto.Alarm;
import com.ssafy.pjt3.model.BasicResponse;
import com.ssafy.pjt3.service.AlarmService;

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
	
	@PostMapping("/send")
	@ApiOperation(value = "알림 보내기", notes = "알림 보내기 기능 구현")
	public Object send(@RequestParam int from_id, int to_id, String content) {
		final BasicResponse result = new BasicResponse();
		Alarm alarm = new Alarm();
		
		try {
			alarm.setFrom_id(from_id);
			alarm.setTo_id(to_id);
			alarm.setContent(content);
			
			System.out.println("from_id : " + from_id);
			System.out.println("to_id : " + to_id);
			System.out.println("content : " + content);
			
			alarmService.send(alarm);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.status = true;
        result.data = "알림 보내기 완료";
        return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	
}
