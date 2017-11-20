package com.example.web.controller;

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
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private DataSource dataSource;
    
    private static final Logger logger = LoggerFactory.getLogger(EventController.class);
    
    @Autowired
    private RedisTemplate<String, EventEntity> redisTemplate;
  
    @ResponseBody
	@RequestMapping(value = "", method = RequestMethod.GET)
    public List<EventEntity> index() {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		String sql = "select * from events";
		logger.info("will fetch event list from databases");
		List<EventEntity> eventList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(EventEntity.class));
		logger.info("have fetched event list from databases");
		return eventList;
    }
    
    @ResponseBody
   	@RequestMapping(value = "/{event_id}", method = RequestMethod.GET)
    public EventEntity getEvent(@PathVariable("event_id") String id) {
    		EventEntity resultEvent = new EventEntity();
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		String sql = "select * from events where id = :event_id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("event_id", id);
		logger.info("will fetch event from databases");
		List<EventEntity> event = jdbcTemplate.query(sql, param, new BeanPropertyRowMapper<>(EventEntity.class));
		logger.info("have fetched event from databases");
		if (!event.isEmpty()) {
			resultEvent = event.get(0);
		}
		return resultEvent;
    }
    
	@ResponseBody
	@RequestMapping(value = "/new", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public int add(@RequestBody EventEntity event) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		String eventId = "";
		int tryCount = 0;
		while(true){
			tryCount = tryCount + 1;
			eventId = RandomStringUtils.randomAlphanumeric(5);
			int checkCount = jdbcTemplate.queryForObject("select count(id) from events where id = :id",
					new MapSqlParameterSource().addValue("id", eventId), Integer.class);
			if(checkCount < 1) {
				break;
			}
			if(tryCount > 3) {
				return -1;
			}
		}
		
		event.setId(eventId);
		String sql = "insert into events (id, name, description, location, start_date, end_date, user_id) "
					+ "values (:id, :name, :description, :location, :start_date, :end_date, :user_id)";
		SqlParameterSource param = new MapSqlParameterSource()
	            .addValue("id", event.getId())
	            .addValue("name", event.getName())
	            .addValue("description", event.getDescription())
	            .addValue("location", event.getLocation())
	            .addValue("start_date", event.getStartDate())
	            .addValue("end_date", event.getEndDate())
	            .addValue("user_id", event.getUserId());
	    return jdbcTemplate.update(sql, param);
	}
    
    @ResponseBody
   	@RequestMapping(value = "/{event_id}/locations", method = RequestMethod.GET)
    public Object getLocation(@PathVariable("event_id") String event_id) {
    		Map<String, List<LocationEntity>> data = new HashMap<>();
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		String sql = "select name, detail from locations where event_id = :event_id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("event_id", event_id);
		logger.info("will fetch locations from databases");
		List<LocationEntity> locations = jdbcTemplate.query(sql, param, new BeanPropertyRowMapper<>(LocationEntity.class));
		logger.info("have fetched locations from databases");
		data.put("locations", locations);
		return data;
    }

}