package com.example.web.entity;

import java.util.HashMap;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseEntity {
	private int status;
	private String message;
	private Object data;
	
	public Object response() {
		Map<String, Object> response = new HashMap<>();
		response.put("status", this.status);
		response.put("message", this.message);
		response.put("data", this.data);
		return response;
	}
}
