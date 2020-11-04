package com.ssafy.pjt3.service;

import java.util.Map;

import com.ssafy.pjt3.exception.UnauthorizedException;

public interface JwtService {

	public <T> String create(String email, String nickname);
	public boolean isUsable(String jwt) throws UnauthorizedException;
	public Map<String, Object> get(String key) throws UnauthorizedException;
}
