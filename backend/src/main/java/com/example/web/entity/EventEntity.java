package com.example.web.entity;

import java.util.Date;

import lombok.Data;

@Data
public class EventEntity {
	private String id;
	private String name;
	private String description;
	private String location;
	private String startDate;
	private String endDate;
	private String userId;
}
