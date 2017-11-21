package com.example.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.web.entity.EventEntity;
import com.example.web.entity.LocationEntity;
import com.example.web.entity.ResponseEntity;
import com.example.web.entity.RouteEntity;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

@Controller
@RequestMapping("api/routes/")
public class RouteController {
	
	private static final Logger logger = LoggerFactory.getLogger(EventController.class);
	
	@Autowired
    private DataSource dataSource;
	
	@ResponseBody
	@RequestMapping(value = "/{eventId}", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public Object postRoute(@PathVariable("eventId") String eventId, @RequestBody String json) {
		
		logger.info(json);

		return ResponseEntity.builder()
				.status(200)
				.message("success to insert route information")
				.data(null)
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
	
}
