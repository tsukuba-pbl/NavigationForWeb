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
		logger.info("eventId = " + eventId);
		logger.info("---------------------------");
		logger.info("sourceName = " + sourceName);
		logger.info("---------------------------");
		logger.info("destinationName = " + destinationName);
		
		//エリアオブジェクトを抽出
		for(Area area: areas) {
			int routeId = (int)area.getRouteId();
			int isStart = (int)area.getIsStart();
			int isGoal = (int)area.getIsGoal();
			int isCrossroad = (int)area.getIsCrossroad();
			String navigationText = area.getNavigation();
			int rotateDegree = (int)area.getRotateDegree();
			
			logger.info("===============================");
			logger.info("routeId = " + routeId);
			logger.info("isStart = " + isStart);
			logger.info("isGoal = " + isGoal);
			logger.info("isCrossroad = " + isCrossroad);
			logger.info("navigationText = " + navigationText);
			logger.info("rotateDegree = " + rotateDegree);
			
			logger.info("---------------------------");
			
			Beacon trainDataList[][] = area.getBeacons();
			String beaconJsonString = getTrainDataAsJson(trainDataList);
			logger.info("===============================");
			logger.info(beaconJsonString);
			
		}

		
		return ResponseEntity.builder()
				.status(200)
				.message("success to upload navigation data")
				.data(null)
				.build();
	}
	
	
	//電波強度のトレーニングデータの部分をJSON形式に変換する
	private String getTrainDataAsJson(Beacon[][] trainDataList) {
		String jsonString = "";
		
		jsonString = "[";
		for(int i = 0; i < trainDataList.length; i++) {
			jsonString += "[";
			for(int j = 0; j < trainDataList[i].length; j++) {
				int minor = (int)trainDataList[i][j].getMinorId();
				int rssi = (int)trainDataList[i][j].getRssi();
				logger.info("[" + minor + "," + rssi + "],");
				jsonString += "{" + "minor : " + minor + "," + "rssi" + ":" + rssi + "}";
				if(j < trainDataList[i].length - 1) {
					jsonString += ",";
				}
			}
			jsonString += "]";
			if(i < trainDataList.length - 1) {
				jsonString += ",";
			}
		}
		jsonString += "]";
		
		return jsonString;
	}
	
	public int sum(int a, int b) {
		return a+b;
	}
}
