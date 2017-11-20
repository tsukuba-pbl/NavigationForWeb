package com.example.web.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class LocationEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String detail;
	private String eventId;
}
