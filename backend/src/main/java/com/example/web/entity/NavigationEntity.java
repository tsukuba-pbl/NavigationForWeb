package com.example.web.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class NavigationEntity implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int areaId;
	private int isStart;
	private int isGoal;
	private int isRoad;
	private int isCrossroad;
	private int rotateDegree;
	private String navigationText;
	private String aroundInfo;
	private String beacons;
}
