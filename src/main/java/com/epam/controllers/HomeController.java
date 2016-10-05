package com.epam.controllers;

import com.epam.entities.User;
import com.epam.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class HomeController {

	private final UserValidator validator;

	@Autowired
	public HomeController(UserValidator validator) { this.validator = validator; }

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
	public String register(@Valid @ModelAttribute User user, Errors errors){
		if (!errors.hasErrors()) {
			System.out.println(user);
			Authentication auth = new UsernamePasswordAuthenticationToken(user,
					user.getPassword(), user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(auth);
			return "redirect:/";
		} else {
			return "register";
		}
	}

	@ModelAttribute("user")
	public User getUser(){
		return new User();
	}

	@InitBinder
	public void addBinder(WebDataBinder binder) {
		binder.addValidators(validator);
	}
	
}
