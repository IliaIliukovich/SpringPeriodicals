package com.epam.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String goHome(){
		return "home";
	}

	@RequestMapping(value="/login", method= RequestMethod.GET)
	public String goLogin(){
		return "login";
	}
	
}
