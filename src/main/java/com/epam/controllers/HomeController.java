package com.epam.controllers;

import com.epam.dao.UserDAO;
import com.epam.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	@Autowired
	UserDAO dao;

	@RequestMapping("/")
	public String goHome(){
		return "home";
	}

	@RequestMapping(value="/login", method= RequestMethod.GET)
	public String goLogin(){
		return "login";
	}

	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String goRegister(){
		return "register";
	}

	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String register(@ModelAttribute User user){
		user.setRole("ROLE_USER");
		System.out.println(user);
		dao.createUser(user);
		Authentication auth = new UsernamePasswordAuthenticationToken(user,
				user.getPassword(), user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
		return "redirect:/";
	}
	
}
