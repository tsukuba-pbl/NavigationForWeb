package com.example.web.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class RegistBeaconEntity implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String eventId;
	private String uuid;
	private int minorId;
}
