package com.ssafy.pjt3.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.pjt3.dao.UserDao;
import com.ssafy.pjt3.dto.User;
import com.ssafy.pjt3.dto.Userconnect;

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
	public void makeFriend(Userconnect uc) throws SQLException {
		// TODO Auto-generated method stub
		userDao.makeFriend(uc);
	}

	@Override
	public void deleteFriend(Userconnect uc) throws SQLException {
		// TODO Auto-generated method stub
		userDao.deleteFriend(uc);
	}

	@Override
	public List<Userconnect> listFriend(int user_id) throws SQLException {
		// TODO Auto-generated method stub
		return userDao.listFriend(user_id);
	}

	@Override
	public User findUser(int user_id) throws SQLException {
		// TODO Auto-generated method stub
		return userDao.findUser(user_id);
	}

	@Override
	public int loginCheck(int user_id) throws SQLException {
		// TODO Auto-generated method stub
		return userDao.loginCheck(user_id);		
	}

	@Override
	public String getRank(int user_id) throws SQLException{
		String rank="a";
		
		try {
			List<User> rankList = userDao.findAllUser();
			
			int index = 0, playcount = 0;
			
			for(int i=0; i<rankList.size(); i++) {
				index++;
				if(rankList.get(i).getId() == user_id) {
					playcount = rankList.get(i).getPlaycount();
					break;
				}
			}
			
			double per = (double)index / (double)rankList.size() * 100;
			
			if(playcount == 0) rank= "UnRank";
			else if(per <= 1) rank = "S";
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

	@Override
	public List<User> findAllUser() throws SQLException {
		return userDao.findAllUser();
	}
}
