package com.ssafy.pjt3.service;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ssafy.pjt3.exception.UnauthorizedException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtServiceImpl implements JwtService {
	private static final String SALT = "ssafy1ban5jo";
	private static final Logger log  = LoggerFactory.getLogger(JwtService.class);
	
	@Override
	public <T> String create(String email, String nickname) {
		String jwt = Jwts.builder()
							.setHeaderParam("typ", "JWT")
							.setHeaderParam("alg", "HS256")
							.setHeaderParam("regDate", System.currentTimeMillis())
							.claim("email", email)
							.claim("nickname", nickname)
							.signWith(SignatureAlgorithm.HS256, this.generateKey())
							.compact();
		return jwt;
	}
	
	private byte[] generateKey() {
		byte[] key = null;
		try {
			key = SALT.getBytes("UTF-8");
		}catch(UnsupportedEncodingException e) {
			if(log.isInfoEnabled()) {
				e.printStackTrace();
			}else {
				log.error("Making JWT Key Error ::: {}", e.getMessage());
			}
		}
		
		return key;
	}
	
	@Override
	public boolean isUsable(String jwt) throws UnauthorizedException {
		try {
			Jws<Claims> claims = Jwts.parser()
								.setSigningKey(this.generateKey())
								.parseClaimsJws(jwt);
			return true;
		}catch(Exception e) {
			throw new UnauthorizedException();
		}
	}

	@Override
	public Map<String, Object> get(String key) throws UnauthorizedException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String jwt = request.getHeader("jwt-auth-token");
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser()
						 .setSigningKey(SALT.getBytes("UTF-8"))
						 .parseClaimsJws(jwt);
		} catch (Exception e) {
			throw new UnauthorizedException();
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> value = (LinkedHashMap<String, Object>)claims.getBody().get(key);
		return value;
	}
}
