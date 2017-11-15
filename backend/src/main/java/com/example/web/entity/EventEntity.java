package com.example.web.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.example.web.CustomDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;

@Data
public class EventEntity {
	private String id;
	private String name;
	private String description;
	private String location;
	@JsonDeserialize(using = CustomDeserializer.DateDeserializer.class)
	private Date startDate;
	@JsonDeserialize(using = CustomDeserializer.DateDeserializer.class)
	private Date endDate;
	private String userId;
	private Date createdAt;
	private Date updatedAt;
	
	public class EventRowMapper implements RowMapper<EventEntity> {

		@Override
		public EventEntity mapRow(ResultSet arg0, int arg1) throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
