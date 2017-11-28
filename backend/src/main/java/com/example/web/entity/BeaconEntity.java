package com.example.web.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class BeaconEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int rssi;
	private int minorId;
}
