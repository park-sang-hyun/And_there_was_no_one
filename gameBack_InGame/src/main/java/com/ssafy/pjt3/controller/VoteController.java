package com.ssafy.pjt3.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pjt3.model.BasicResponse;
import com.ssafy.pjt3.model.VoteData;
import com.ssafy.pjt3.service.VoteService;

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
@RequestMapping("/vote")
public class VoteController {
	@Autowired
	private VoteService voteService;

	@PostMapping("/vote/{room_id}")
	@ApiOperation(value = "투표시작", notes = "투표시작")
	public void vote(@PathVariable int room_id, @RequestParam String who) {
		try {
			System.out.println("insert 중~~~");
			voteService.insertVote(room_id, who);
			System.out.println("insert 끝!!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static class point{
		String who;
		int cnt;
		public point(String who, int cnt) {
			this.who = who;
			this.cnt = cnt;
		}
	}
	
	@PostMapping("/voteResult/{room_id}")
	@ApiOperation(value = "투표시작", notes = "투표시작")
	public Object voteResult(@PathVariable int room_id) {
		
		List<VoteData> list = new ArrayList<>();
		List<point> res = new ArrayList<>();
		List<point> res1 = new ArrayList<>();
		
		try {
			System.out.println("selct 중~~~");
			list = voteService.seletVote(room_id);
			
			for(int i = 0; i < list.size(); i++){
				int f = 0;
				end: for(int k = 0; k < res.size(); k++) {
					if(res.get(k).who.equals(list.get(i).getWho())) {
						f = 1;
						res.get(k).cnt++;
						break end;
					}
				}
				if(f == 0) {
					res.add(new point(list.get(i).getWho(), 1));
				}
			}
			
			Collections.sort(res, new Comparator<point>() {

				@Override
				public int compare(point o1, point o2) {
					// TODO Auto-generated method stub
					return o2.cnt - o1.cnt;
				}
			});
			
			res1.add(res.get(0));
			
			int max = res.get(0).cnt;
			if(res1.size() != 1) {
				for(int i = 1; i < res.size(); i++) {
					if(res.get(i).cnt == max) {
						res1.add(res.get(i));
					} else break;
				}
			}
			
			
			System.out.println("selct 끝!!");
			voteService.deleteVote(room_id);
			System.out.println("delete 끝!!");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return res1;
	}
}
