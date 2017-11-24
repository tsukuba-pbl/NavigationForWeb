package com.example.web.entity;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import lombok.Data;

@Data
public class RouteEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int sourceId;
	private int destinationId;
	private String eventId;
	
	public class RouteRowMapper implements RowMapper<RouteEntity> {

		@Override
		public RouteEntity mapRow(ResultSet arg0, int arg1) throws SQLException {
			return null;
		}
		
	}
}
