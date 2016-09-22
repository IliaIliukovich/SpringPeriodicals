package com.epam.controllers;

import com.epam.entities.Journal;
import com.epam.services.PeriodicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

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
	public String addNewJournal(@ModelAttribute Journal newJournal){
		periodicalService.addNewJournal(newJournal);
		return "/addjournal";
	}

	@ModelAttribute("newJournal")
	public Journal getJournal(){
		return new Journal();
	}

}
