package com.example.web.controller;

import spock.lang.Specification

public class TrainControllerSpec extends Specification {
	private TrainController instance;
	
	def setup() {
		instance = new TrainController()
	}
	
	def "index"() {
		expect:
			instance.sum(a, b) == result
			
		where:
		a | b || result
		1 | 1 || 2
		2 | 3 || 3
	}
}
