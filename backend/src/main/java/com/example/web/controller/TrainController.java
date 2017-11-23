package com.example.web.controller;

import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.web.entity.ResponseEntity;

import mypack.parse.navigationdata.*;

@Controller
@RequestMapping("/api/train")
public class TrainController {
	
	private static final Logger logger = LoggerFactory.getLogger(EventController.class);

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Integer> index() {
		Map<String, Integer> map = new HashMap<>();
		map.put("ume", 25);
		map.put("turu", 23);
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "/test", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public Object test(@RequestBody String jsonString) throws IOException {
		logger.info(jsonString);
		
		//パース
		GettingStarted data = Converter.fromJsonString(jsonString);
		
		int eventId = (int) data.getEventId();
		String sourceName = data.getSourceName();
		String destinationName = data.getDestinationName();
		Area areas[] = data.getAreas();
		
		logger.info("---------------------------");
		logger.info("eventId = " + eventId + "¥n");
		logger.info("---------------------------");
		logger.info("sourceName = " + sourceName + "¥n");
		logger.info("---------------------------");
		logger.info("destinationName = " + destinationName + "¥n");
		
		return ResponseEntity.builder()
				.status(200)
				.message("success to upload navigation data")
				.data(null)
				.build();
	}
	
	public int sum(int a, int b) {
		return a+b;
	}
}
