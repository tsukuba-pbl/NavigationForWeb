package com.example.web.entity;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import lombok.Data;

@Data
public class RegistBeaconEntity implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String eventId;
	private String uuid;
	private int minorId;
	
	public class BeaconRowMapper implements RowMapper<RegistBeaconEntity> {

		@Override
		public RegistBeaconEntity mapRow(ResultSet arg0, int arg1) throws SQLException {
			return null;
		}
		
	}
}
