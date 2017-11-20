package com.example.web.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.Serializable;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.example.web.CustomDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;

@Data
public class LocationEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String detail;
	private String event_id;
}
