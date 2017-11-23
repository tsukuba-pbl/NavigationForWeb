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
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.web.entity.BeaconEntity;
import com.example.web.entity.NavigationEntity;
import com.example.web.entity.ResponseEntity;
import com.example.web.entity.RouteEntity;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mypack.parse.navigationdata.*;


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
	
	@ResponseBody
	@RequestMapping(value = "/{eventId}", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public Object ReceiveRouteInformation(@PathVariable("eventId") String eventId, @RequestBody String receiveRouteJson) throws IOException {
		logger.info("ルートデータがpostされてきた");
		//jsonのパース
		GettingStarted data = Converter.fromJsonString(receiveRouteJson);
		
		Area areas[] = data.getAreas();

		//データベースへの格納
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		logger.info("データベースへの格納");
		//source_idをlocationsテーブルから取得
		String sql_sourceid = "select id from locations where name = :source_id";
		SqlParameterSource param_sourceid = new MapSqlParameterSource()
				.addValue("source_id", data.getSourceName());
		int sourceId = jdbcTemplate.queryForObject(sql_sourceid, param_sourceid, Integer.class);
		logger.info("sourceId="+sourceId);
		
		//destination_idをlocationsテーブルから取得
		String sql_destinationid = "select id from locations where name = :destination_id";
		SqlParameterSource param_destinationid = new MapSqlParameterSource()
				.addValue("destination_id", data.getDestinationName());
		int destinationId = jdbcTemplate.queryForObject(sql_destinationid, param_destinationid, Integer.class);
		logger.info("destinationId="+destinationId);
		logger.info("EventId="+data.getEventId());
		
		//routesテーブルへ格納
		String sql_routes = "insert into routes (source_id, destination_id, event_id) "
				+ "values (:source_id, :destination_id, :event_id)";
		SqlParameterSource param_routes = new MapSqlParameterSource()
				.addValue("source_id", sourceId)
				.addValue("destination_id", destinationId)
				.addValue("event_id", data.getEventId());
		logger.info("source_id="+sourceId);
		logger.info("destination_id="+destinationId);
		logger.info("event_id="+data.getEventId());
		int result_routes = jdbcTemplate.update(sql_routes, param_routes);
		logger.info("routesテーブルへ挿入成功");
		
		//route_idの取得
		String sql_routeId = "select id from routes where source_id = :source_id and destination_id = :destination_id";
		SqlParameterSource params_id = new MapSqlParameterSource()
				.addValue("source_id", sourceId)
				.addValue("destination_id", destinationId);
		int routeId = jdbcTemplate.queryForObject(sql_routeId, params_id, Integer.class);
		
		//areas挿入
		int result_areas = 1;
		for (Area area: areas) {
			String sql_areas = "insert into areas (route_id, path_id, degree, is_start, is_goal, is_crossroad, is_road, train_data, around_info, navigation_text) "
					+ "values (:route_id, :path_id, :degree, :is_start, :is_goal, :is_crossroad, :is_road, :train_data, :around_info, :navigation_text)";
			SqlParameterSource param_areas = new MapSqlParameterSource()
					.addValue("route_id", routeId)
					.addValue("path_id", (int)area.getRouteId())
					.addValue("degree", (int)area.getRotateDegree())
					.addValue("is_start", myIntToBoolean((int)area.getIsStart()))
					.addValue("is_goal", myIntToBoolean((int)area.getIsGoal()))
					.addValue("is_crossroad", myIntToBoolean((int)area.getIsCrossroad()))
					.addValue("train_data", getTrainDataAsJson(area.getBeacons()))
					.addValue("around_info", null)
					.addValue("navigation_text", area.getNavigation());
			result_areas = jdbcTemplate.update(sql_areas, param_areas);
		}
		if (result_routes != 1 || result_areas != 1) {
			return ResponseEntity.builder()
					.status(400)
					.message("failed to insert route information")
					.data(null)
					.build();
	    }

		return ResponseEntity.builder()
				.status(200)
				.message("success to insert route information")
				.data(null)
				.build();
    }
	
	private Boolean myIntToBoolean(int n) {
		// 0: false
		// 1: true
		if (n == 0) {
			return false;
		}
		return true;
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
}