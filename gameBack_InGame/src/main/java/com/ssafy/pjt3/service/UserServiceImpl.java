package com.ssafy.pjt3.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.pjt3.dao.UserDao;
import com.ssafy.pjt3.dto.User;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UserDao userDao;
	
	@Override
	public int findPkId(String username) throws SQLException {
		return userDao.findPkId(username);
	}

	@Override
	public boolean isLeader(int user_id) throws SQLException {
		return userDao.isLeader(user_id);
	}

	@Override
	public User getLeader(int room_id) throws SQLException {
		return userDao.getLeader(room_id);
	}

	@Override
	public User getUserwithUserId(int user_id) throws SQLException {
		return userDao.getUserwithUserId(user_id);
	}

	@Override
	public void changeScore(User user) throws SQLException {
		userDao.changeScore(user);
	}

	@Override
	public String getRank(int user_id) throws SQLException{
		String rank="a";
		
		try {
			List<User> rankList = userDao.findAllUser();
			
			int index = 0;
			
			for(int i=0; i<rankList.size(); i++) {
				index++;
				if(rankList.get(i).getId() == user_id) break;
			}
			
			double per = (double)index / (double)rankList.size() * 100;

			if(per <= 1) rank = "S";
			else if(per <= 5) rank = "A";
			else if(per <= 10) rank = "B";
			else if(per <= 20) rank = "C";
			else if(per <= 50) rank = "D";
			else if(per <= 80) rank = "E";
			else if(per <= 100) rank = "F";
			
		}catch(SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rank;
	}
}
