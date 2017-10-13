package com.example.web.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/api")
public class IndexController {

	@RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
		model.addAttribute("msg","サンプルメッセージ！");
		return "index/index";
    }
}
