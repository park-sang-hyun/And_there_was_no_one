package com.ssafy.pjt3.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.pjt3.exception.UnauthorizedException;
import com.ssafy.pjt3.service.JwtService;

public class JwtInterceptor implements HandlerInterceptor{
	private static final String HEADER_AUTH = "jwt-auth-token";
	@Autowired
	private JwtService jwtservice;
	
	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler) throws Exception{
		final String token = request.getHeader(HEADER_AUTH);
		
		if(token != null && jwtservice.isUsable(token)) {
			return true;
		}else {
			throw new UnauthorizedException();
		}
	}	
}
