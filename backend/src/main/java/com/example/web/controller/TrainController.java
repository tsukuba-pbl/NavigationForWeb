package com.example.web.controller;

import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/train")
public class TrainController {

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Integer> index(Model model) {
		Map<String, Integer> map = new HashMap<>();
		map.put("ume", 25);
		map.put("turu", 23);
		return map;
	}
}
