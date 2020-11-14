package com.ssafy.pjt3.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pjt3.dto.Vote;
import com.ssafy.pjt3.model.BasicResponse;
import com.ssafy.pjt3.model.point;
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
			if(!who.equals(null)) {
				Vote v = new Vote(room_id, who);
				voteService.insertVote(v);
				System.out.println(" 끝!!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@PostMapping("/voteDelete/{room_id}")
	@ApiOperation(value = "투표끝", notes = "투표끝")
	public void vote(@PathVariable int room_id) {
		try {
			voteService.deleteVote(room_id);
			System.out.println(" 끝!!");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@GetMapping("/voteResult/{room_id}")
	@ApiOperation(value = "투표시작", notes = "투표시작")
	public List<point> voteResult(@PathVariable int room_id) {
		
		List<String> list = new ArrayList<>();
		List<point> res = new ArrayList<>();
		List<point> res1 = new ArrayList<>();
		
		try {
			System.out.println("selct 중~~~");
			list = voteService.seletVote(room_id);
			System.out.println("ddd~~~");
			for(int i = 0; i < list.size(); i++){
				int f = 0;
				end: for(int k = 0; k < res.size(); k++) {
					if(res.get(k).getWho().equals(list.get(i))) {
						f = 1;
						res.get(k).setCnt(res.get(k).getCnt()+1);
						break end;
					}
				}
				if(f == 0) {
					res.add(new point(list.get(i), 1));
				}
			}
			
			Collections.sort(res, new Comparator<point>() {

				@Override
				public int compare(point o1, point o2) {
					// TODO Auto-generated method stub
					return o2.getCnt() - o1.getCnt();
				}
			});
			
			res1.add(res.get(0));
	         
	         int max = res.get(0).getCnt();
	         
	         if(res.size() != 1) {
	            for(int i = 1; i < res.size(); i++) {
	               System.out.println(res.get(i).getCnt());
	               if(res.get(i).getCnt() == max) {
	                  res1.add(res.get(i));
	               }
	            }
	         }
			
			System.out.println("selct 끝!!");
			//voteService.deleteVote(room_id);
			System.out.println("delete 끝!!");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res1;
	}
}
