package com.devracoon.react;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.devracoon.react.exception.InvaildRefreshTokenException;
import com.devracoon.react.exception.InvaildTokenException;

@RestControllerAdvice  
public class GlobalExceptionHandler {  
  
    @ResponseStatus(HttpStatus.UNAUTHORIZED)  
    @ExceptionHandler(value = InvaildTokenException.class)  
    public Map<String , Object> handleTokenException(InvaildTokenException e){  
        Map<String,  Object> data = new HashMap<String ,Object>();
        data.put("code", 4401);
        data.put("msg","invalid token");
    	return data;  
    }
    
    @ResponseStatus(HttpStatus.UNAUTHORIZED)  
    @ExceptionHandler(value = InvaildRefreshTokenException.class)  
    public Map<String , Object> handleRefreshTokenException(InvaildRefreshTokenException e){  
        Map<String,  Object> data = new HashMap<String ,Object>();
        data.put("code", 4402);
        data.put("msg","invalid token");
    	return data;  
    }
    
  
}
