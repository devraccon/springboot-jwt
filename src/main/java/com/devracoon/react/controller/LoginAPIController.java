package com.devracoon.react.controller;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.devracoon.react.exception.InvaildRefreshTokenException;
import com.devracoon.react.vo.TokenVO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/auth")
public class LoginAPIController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Value("${security.jwt.token.secret-key}") 
	private String secretKey;
	
	@RequestMapping(value="/login" , method=RequestMethod.POST)
	public String login(@RequestBody TokenVO userData , HttpServletRequest request , HttpServletResponse response) {
		String accessToken = "";
		String refreshToken = "";
		
		accessToken = getToken(userData.getEmail() , 1);
		refreshToken = getToken(userData.getEmail() , 3);
		Cookie refreshCookie = new Cookie("refreshToken" , refreshToken);
		refreshCookie.setMaxAge(3 * 60);
		response.addCookie(refreshCookie);
		  
		return accessToken;
	}
	
	@RequestMapping(value="/refreshToken" , method=RequestMethod.POST)
	public String refreshToken(@RequestBody TokenVO userData , HttpServletRequest request , HttpServletResponse response) throws Exception{
		
		
		String accessToken = "";
		String refreshToken = "";
		
		Cookie [] cookies = request.getCookies();
		if(cookies != null && cookies.length > 0 ) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals("refreshToken")) {
					refreshToken = cookie.getValue();
					if(checkClaim(refreshToken)) {
						accessToken = getToken(userData.getEmail() , 1);
					}else {
						throw new InvaildRefreshTokenException();
					}
				}
			}
		}
		
		if(refreshToken == null || "".equals(refreshToken)) {
			throw new InvaildRefreshTokenException();
		}
				
		  
		return accessToken;
	}
	
	public boolean checkClaim(String jwt) {
	    try {
	    	Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
	                .parseClaimsJws(jwt).getBody();
	        return true;
	    
	    }catch(ExpiredJwtException e) {   
	        log.error("Token Expired");
	        return false;
	    
	    }catch(JwtException e) {        
	        log.error("Token Error" , e);
	        return false;
	    }
	}
	
	
	
	private String getToken(String subject , long expire) {
		String accessToken = "";
		
		Claims claims = Jwts.claims().setSubject(subject);
		accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
//                .setExpiration(Date.from(LocalDateTime.now().plusMinutes(expire).toInstant(ZoneOffset.ofHours(9))))
                .setExpiration(new Date(System.currentTimeMillis() + (expire * (1000 * 60 ))))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();


		  
		return accessToken;
	}
	
}