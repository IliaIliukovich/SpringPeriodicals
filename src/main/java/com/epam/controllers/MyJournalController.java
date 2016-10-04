package com.epam.controllers;

import com.epam.entities.Journal;
import com.epam.services.PeriodicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping(value="/myjournals")
public class MyJournalController {

	@Autowired
	private PeriodicalService periodicalService;

	@RequestMapping(method = RequestMethod.GET)
	public String getMyJournals(Model model){
		List<Journal> myChoiceJournals = periodicalService.getChoiceJournals();
		List<Journal> mySubscriptionJournals = periodicalService.getSubscriptionJournals();
		BigDecimal sum = periodicalService.sumToPay();
		model.addAttribute("myChoiceJournals", myChoiceJournals);
		model.addAttribute("mySubscriptionJournals", mySubscriptionJournals);
		model.addAttribute("sumToPay", sum);
		return "myjournals";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteMyChoice (@RequestParam Long currentId) {
			periodicalService.deleteMyChoice(currentId);
		return "redirect:/myjournals";
	}

	@RequestMapping(value = "/pay", method = RequestMethod.POST)
	public String pay(@RequestParam BigDecimal sum){
		periodicalService.pay(sum);
		return "redirect:/myjournals";
	}
	
}
