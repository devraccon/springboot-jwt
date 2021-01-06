package com.devracoon.react.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.devracoon.react.vo.SampleDataVO;

@RestController
@RequestMapping("/sample")
public class SampleDataController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="/sampleData" , method=RequestMethod.GET)
	public List<SampleDataVO> sampleData(HttpServletRequest request , HttpServletResponse response) {
		List<SampleDataVO> sampleDatas = new ArrayList<SampleDataVO>();
		for(int i = 0 ; i < 20 ; i++) {
			SampleDataVO data = new SampleDataVO();
			data.setId("ID_"+ i);
			data.setName("Name!!!!"+ i);
			data.setPhone("010_000_"+ i);
			data.setAddr("ADDR_"+ i);
			sampleDatas.add(data);
		}
		  
		return sampleDatas;
	}
}
