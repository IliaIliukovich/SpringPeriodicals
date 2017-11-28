package com.epam.controllers;

import com.epam.entities.Journal;
import com.epam.services.PeriodicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import javax.validation.Valid;

@Controller
@RequestMapping(value="/addjournal")
@SessionAttributes("journal")
public class AddJournalController {

	private final PeriodicalService periodicalService;

	@Autowired
	public AddJournalController(PeriodicalService periodicalService) { this.periodicalService = periodicalService; }

	@RequestMapping(method = RequestMethod.GET)
	public String review(){
		return "/addjournal";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String addNewJournal(@Valid @ModelAttribute Journal journal, Errors errors, SessionStatus status){
		if (!errors.hasErrors()) {
			periodicalService.createOrUpdate(journal);
			status.setComplete();
			return "redirect:/addjournal";
		} else {
			return "/addjournal";
		}
	}

	@ModelAttribute("journal")
	public Journal getJournal(){
		return new Journal();
	}

}
