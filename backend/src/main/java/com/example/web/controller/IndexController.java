package com.example.web.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class IndexController {

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		return "index";
	}
}
