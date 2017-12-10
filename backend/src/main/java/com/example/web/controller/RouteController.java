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
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.web.entity.EventEntity;

import mypack.parse.navigationdata.*;


@Controller
@RequestMapping("/api/routes")
public class RouteController {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private RedisTemplate<String, Map<String, List<Object>>> redisTemplate;
    
    private static final Logger logger = LoggerFactory.getLogger(RouteController.class);
    
    @ResponseBody
	@RequestMapping(value = "/{eventId}", method = RequestMethod.GET)
    public Object index(@PathVariable("eventId") String eventId, @RequestParam("departure") String departure, @RequestParam("destination") String destination) {
    		Boolean isReverse = false;
    		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    		if (departure == null || destination == null) {
			return ResponseEntity.builder()
					.status(400)
					.message("something wrong")
					.data(null)
					.build();
    		}
    		
    		String redisKey = "route_"+eventId+"_"+departure+"_"+destination;
    		
    		// Redisにキャッシュされていたらその情報を取得して返す
    		if(redisTemplate.opsForValue().get(redisKey) != null) {
    			logger.info("return redis value");
    			return ResponseEntity.builder()
					.status(200)
					.message("success to fetch navigation from redis")
					.data(redisTemplate.opsForValue().get(redisKey))
					.build();
    		}
    		
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
			isReverse = true;
			// データの修正
			NavigationEntity firstNavigation = navigation.get(0);
			NavigationEntity endNavigation = navigation.get(navigation.size()-1);
			
			String tmp = firstNavigation.getNavigationText();
			firstNavigation.setIsGoal(0);
			firstNavigation.setIsStart(1);
			firstNavigation.setNavigationText(endNavigation.getNavigationText());
			endNavigation.setIsGoal(1);
			endNavigation.setIsStart(0);
			endNavigation.setNavigationText(tmp);
			
			navigation.set(0, firstNavigation);
			navigation.set(navigation.size()-1, endNavigation);
		}
		logger.info("have fetched event list from databases");
		
		List<Object> list = new ArrayList<>();
		for(int i = 0; i < navigation.size(); i++) {
			NavigationEntity data = navigation.get(i);
			Map<String, Object> entity = new HashMap<String, Object>();
			entity.put("areaId", i + 1);
			//反転ルートの処理を行う
			//なお，StartとGoalに対しては反転処理を行わない
			if (isReverse && data.getIsStart() == 0 && data.getIsGoal() == 0) {	
				entity.put("rotateDegree", - data.getRotateDegree());
			} else {
				entity.put("rotateDegree", data.getRotateDegree());
			}
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
		}
		
		Map<String, List<Object>> response = new HashMap<>();
		response.put("routes", list);
		
		List<Object> detailsList = new ArrayList<>();
		Map<String, Object> detailsEntity = new HashMap<>();
		detailsEntity.put("isReverse", isReverse ? 1 : 0);
		detailsList.add(detailsEntity);
		response.put("details", detailsList);
		
		redisTemplate.opsForValue().set(redisKey, response);
		logger.info("set value in redis");
		
    		return ResponseEntity.builder()
					.status(200)
					.message("success to fetch navigation")
					.data(response)
					.build();
    }
	
	@ResponseBody
	@RequestMapping(value = "/{eventId}", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public Object receiveRouteInformation(@PathVariable("eventId") String eventId, @RequestBody String receiveRouteJson) throws IOException {
		//jsonのパース
		RouteData data = Converter.fromJsonString(receiveRouteJson);
		
		//データベースへの格納
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

		//source_idの取得
		//source_idをlocationsテーブルから取得
		String sqlSourceid = "select id from locations where event_id = :event_id and name = :source_name";
		SqlParameterSource paramSourceid = new MapSqlParameterSource()
				.addValue("event_id", data.getEventId())
				.addValue("source_name", data.getSourceName());
		int sourceId = jdbcTemplate.queryForObject(sqlSourceid, paramSourceid, Integer.class);
		
		//destination_idをlocationsテーブルから取得
		String sqlDestinationid = "select id from locations where event_id = :event_id and name = :destination_name";
		SqlParameterSource paramDestinationid = new MapSqlParameterSource()
				.addValue("event_id", data.getEventId())
				.addValue("destination_name", data.getDestinationName());
		int destinationId = jdbcTemplate.queryForObject(sqlDestinationid, paramDestinationid, Integer.class);
		
		//routesテーブルへ格納
		String sqlRoutes = "insert into routes (source_id, destination_id, event_id) "
				+ "values (:source_id, :destination_id, :event_id)";
		SqlParameterSource paramRoutes = new MapSqlParameterSource()
				.addValue("source_id", sourceId)
				.addValue("destination_id", destinationId)
				.addValue("event_id", data.getEventId());
		int resultRoutes = jdbcTemplate.update(sqlRoutes, paramRoutes);
		logger.info("routesテーブルへ格納完了");
		logger.info("------------------------------");
		
		if (resultRoutes != 1) {
			return ResponseEntity.builder()
					.status(400)
					.message("failed to insert route information")
					.data(null)
					.build();
	    }
		
		//areaテーブルで必要となる外部キーroute_idの取得
		String sqlRouteId = "select id from routes where source_id = :source_id and destination_id = :destination_id";
		SqlParameterSource paramsId = new MapSqlParameterSource()
				.addValue("source_id", sourceId)
				.addValue("destination_id", destinationId);
		int routeId = jdbcTemplate.queryForObject(sqlRouteId, paramsId, Integer.class);
		
		//areaテーブルへの格納
		int resultAreas = -1; //エラコード用でとりあえず変数を用意している
		for (Area area: data.getAreas()) {
			String sqlAreas = "insert into area (route_id, path_id, degree, is_start, is_goal, is_road, is_crossroad, train_data, around_info, navigation_text) "
					+ "values (:route_id, :path_id, :degree, :is_start, :is_goal, :is_road, :is_crossroad, :train_data, :around_info, :navigation_text)";
			SqlParameterSource paramAreas = new MapSqlParameterSource()
					.addValue("route_id", routeId)
					.addValue("path_id", (int)area.getRouteId())
					.addValue("degree", (int)area.getRotateDegree())
					.addValue("is_start", myIntToBoolean((int)area.getIsStart()))
					.addValue("is_goal", myIntToBoolean((int)area.getIsGoal()))
					.addValue("is_crossroad", myIntToBoolean((int)area.getIsCrossroad()))
					.addValue("is_road", myIntToBoolean((int)area.getIsRoad()))
					.addValue("train_data", getTrainDataAsJson(area.getBeacons()))
					.addValue("around_info", null)
					.addValue("navigation_text", area.getNavigation());
			resultAreas = jdbcTemplate.update(sqlAreas, paramAreas);
		}
		if (resultAreas != 1) {
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
	
	//Int型をBoolean型に変換する
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
				jsonString += "{" + "\"minorId\" : " + minor + "," + "\"rssi\"" + ":" + rssi + "}";
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
