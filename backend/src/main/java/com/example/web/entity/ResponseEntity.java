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
}
