package com.epam.controllers;

import com.epam.entities.Journal;
import com.epam.entities.User;
import com.epam.services.PeriodicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping(value="/myjournals")
public class MyJournalController {

	private final PeriodicalService periodicalService;

	@Autowired
	public MyJournalController(PeriodicalService periodicalService) {
		this.periodicalService = periodicalService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getMyJournals(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User)auth.getPrincipal();
		List<List<Journal>> userJournals = periodicalService.getUserJournals(currentUser.getId_user());
		BigDecimal sum = periodicalService.sumToPay(currentUser.getId_user());
		model.addAttribute("myChoiceJournals", userJournals.get(0));
		model.addAttribute("mySubscriptionJournals", userJournals.get(1));
		model.addAttribute("sumToPay", sum);
		return "/myjournals";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteMyChoice (@RequestParam Long choiceId) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User currentUser = (User)auth.getPrincipal();
			periodicalService.deleteMyChoice(choiceId, currentUser.getId_user());
		return "redirect:/myjournals";
	}

	@RequestMapping(value = "/pay", method = RequestMethod.POST)
	public String pay(@RequestParam BigDecimal sum) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User)auth.getPrincipal();
		periodicalService.pay(sum, currentUser.getId_user());
		return "redirect:/myjournals";
	}
	
}
