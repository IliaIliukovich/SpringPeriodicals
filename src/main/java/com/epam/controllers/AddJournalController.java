package com.epam.controllers;

import com.epam.entities.Journal;
import com.epam.services.PeriodicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@RequestMapping(value="/addjournal")
public class AddJournalController {

	@Autowired
	private PeriodicalService periodicalService;

	@RequestMapping(method = RequestMethod.GET)
	public String review(){
		return "/addjournal";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String addNewJournal(@Valid @ModelAttribute Journal journal, Errors errors){
		if (!errors.hasErrors()) {
			System.out.println("validated!");
			periodicalService.addNewJournal(journal);
			return "redirect:/addjournal";
		} else {
			System.out.println("not validated!");
			return "addjournal";
		}
	}

	@ModelAttribute("journal")
	public Journal getJournal(){
		return new Journal();
	}

}
