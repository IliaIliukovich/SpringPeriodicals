package com.epam.controllers;

import com.epam.entities.Journal;
import com.epam.entities.User;
import com.epam.services.PeriodicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value="/api")
public class ApiController {

	private final PeriodicalService periodicalService;

	@Autowired
	public ApiController(PeriodicalService periodicalService) {
		this.periodicalService = periodicalService;
	}

	@RequestMapping(value = "/mysubscriptions", method = RequestMethod.GET)
	public @ResponseBody List<Journal> mySubscriptions(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User)auth.getPrincipal();
		List<List<Journal>> userJournals = periodicalService.getUserJournals(currentUser.getId_user());
		return userJournals.get(1);
	}

}
