package com.example.web.entity;

import java.io.Serializable;
import java.util.Date;

import com.example.web.CustomDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;

@Data
public class EventEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String description;
	private String location;
	@JsonDeserialize(using = CustomDeserializer.DateDeserializer.class)
	private Date startDate;
	@JsonDeserialize(using = CustomDeserializer.DateDeserializer.class)
	private Date endDate;
	private String userId;
	private Date created_at;
	private Date updated_at;
}
