package com.example.web.entity;

import java.sql.ResultSet;
import java.sql.SQLException;


import org.springframework.jdbc.core.RowMapper;

public class AreaEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int routeId;
	private int areaId;
	private double rotateDegree;
	private boolean isStart;
	private boolean isGoal;
	private boolean isCrossroad;
	private boolean isRoad;
	private String trainData;
	private String aroundInfo;
	
	public class AreaRowMapper implements RowMapper<AreaEntity> {

		@Override
		public AreaEntity mapRow(ResultSet arg0, int arg1) throws SQLException {
			return null;
		}
		
	}
}
