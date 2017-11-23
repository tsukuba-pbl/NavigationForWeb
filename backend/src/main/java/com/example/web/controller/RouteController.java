package com.example.web.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.web.entity.BeaconEntity;
import com.example.web.entity.NavigationEntity;
import com.example.web.entity.ParsedRouteAreaEntity;
import com.example.web.entity.ResponseEntity;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
@RequestMapping("/api/routes")
public class RouteController {

    @Autowired
    private DataSource dataSource;
    
    private static final Logger logger = LoggerFactory.getLogger(RouteController.class);
    
    @Autowired
    private RedisTemplate<String, NavigationEntity> redisTemplate;
    

    @ResponseBody
	@RequestMapping(value = "/{eventId}", method = RequestMethod.GET)
    public Object index(@PathVariable("eventId") String eventId, @RequestParam("departure") String departure, @RequestParam("destination") String destination) {
    		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		String sql = "select area.path_id as area_id, area.degree as rotate_degree, area.is_start, area.is_goal, area.is_road, area.is_crossroad, area.train_data as beacons, area.around_info, area.navigation_text"
				+ " from area join (\n" + 
				"    select routes.id from routes join (\n" + 
				"        select sourcelocation.id as source_id, sourcelocation.name as source_name, destinationlocation.id as destination_id, destinationlocation.name as destination_name from \n" + 
				"            (select * from locations where event_id=:eventId and name=:departure) as sourcelocation,\n" + 
				"            (select * from locations where event_id=:eventId and name=:destination) as destinationlocation\n" + 
				"    ) as locations on routes.source_id = locations.source_id and routes.destination_id = locations.destination_id\n" + 
				") as routes on routes.id = area.route_id order by area.id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("eventId", eventId)
															.addValue("departure", departure)
															.addValue("destination", destination);
		logger.info("will fetch navigation from databases");
		List<NavigationEntity> navigation = jdbcTemplate.query(sql, param, new BeanPropertyRowMapper<>(NavigationEntity.class));
		if(navigation.isEmpty()) {
			// 対象のルートがない場合は逆パターンも試す．
			sql = "select area.path_id as area_id, area.degree as rotate_degree, area.is_start, area.is_goal, area.is_road, area.is_crossroad, area.train_data as beacons, area.around_info, area.navigation_text"
					+ " from area join (\n" + 
					"    select routes.id from routes join (\n" + 
					"        select sourcelocation.id as source_id, sourcelocation.name as source_name, destinationlocation.id as destination_id, destinationlocation.name as destination_name from \n" + 
					"            (select * from locations where event_id=:eventId and name=:departure) as sourcelocation,\n" + 
					"            (select * from locations where event_id=:eventId and name=:destination) as destinationlocation\n" + 
					"    ) as locations on routes.source_id = locations.source_id and routes.destination_id = locations.destination_id\n" + 
					") as routes on routes.id = area.route_id order by area.id desc";
			param = new MapSqlParameterSource().addValue("eventId", eventId)
											.addValue("departure", destination)
											.addValue("destination", departure);
			logger.info("will fetch navigation from databases");
			navigation = jdbcTemplate.query(sql, param, new BeanPropertyRowMapper<>(NavigationEntity.class));
			if(navigation.isEmpty()) {
				// 逆パターンもない場合は，残念
				return ResponseEntity.builder()
						.status(400)
						.message("failed to fetch navigation")
						.data(null)
						.build();
			}
			
			// データの修正
			NavigationEntity first = navigation.get(0);
			first.setIsGoal(0);
			first.setIsStart(1);
			navigation.set(0, first);
			NavigationEntity end = navigation.get(navigation.size()-1);
			end.setIsGoal(1);
			end.setIsStart(0);
			navigation.set(navigation.size()-1, end);
		}
		logger.info("have fetched event list from databases");
		List<Object> list = new ArrayList<>();
		navigation.forEach(data -> {
			Map<String, Object> entity = new HashMap<>();
			entity.put("areaId", data.getAreaId());
			entity.put("rotateDegree", data.getRotateDegree());
			entity.put("isStart", data.getIsStart());
			entity.put("isGoal", data.getIsGoal());
			entity.put("isRoad", data.getIsRoad());
			entity.put("isCrossroad", data.getIsCrossroad());
			entity.put("navigationText", data.getNavigationText());
			entity.put("aroundInfo", data.getAroundInfo());
			ObjectMapper mapper = new ObjectMapper();
			try {
				List<BeaconEntity[]> beaconList = mapper.readValue(data.getBeacons(), new TypeReference<List<BeaconEntity[]>>() {});
				entity.put("beacons", beaconList);
			} catch (JsonParseException e) {
				e.printStackTrace();
				logger.error(e.toString());
			} catch (JsonMappingException e) {
				e.printStackTrace();
				logger.error(e.toString());
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.toString());
			}
			list.add(entity);
		});
		Map<String, List<Object>> response = new HashMap<>();
		response.put("routes", list);
		
    		return ResponseEntity.builder()
					.status(200)
					.message("success to fetch navigation")
					.data(response)
					.build();
    }
	
//	@ResponseBody
//	@RequestMapping(value = "/{eventId}", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
//	public Object postRoute(@PathVariable("eventId") String eventId, @RequestBody RouteEntity route) {
//		//イベントID
//		route.setEventId(eventId);
//		//データベースへの格納
//		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
//		String sql = "insert into routes (source_id, destination_id, event_id) "
//				+ "values (:source_id, :destination_id, :event_id))";
//		SqlParameterSource param = new MapSqlParameterSource()
//				.addValue("source_id", route.getSourceId())
//				.addValue("destination_id", route.getDestinationId())
//				.addValue("event_id", route.getEventId());
//		int result = jdbcTemplate.update(sql, param);
//		
//		if (result != 1) {
//			return ResponseEntity.builder()
//					.status(400)
//					.message("failed to insert route information")
//					.data(null)
//					.build();
//	    }
//
//		return ResponseEntity.builder()
//				.status(200)
//				.message("success to insert route information")
//				.data(null)
//				.build();
//    }
    
    //計測アプリから送られてきたJSONデータをパースし，そのデータを返す
    private ParsedRouteAreaEntity getParsedRouteAreaEntity(String strJson) {
    		
		return null;
    	
    }
	
}