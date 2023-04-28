package com.epam.controllers;

import com.epam.entities.Journal;
import com.epam.services.PeriodicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import jakarta.validation.Valid;

@Controller
@RequestMapping(value="/editjournal")
@SessionAttributes("journal")
public class EditJournalController {

	private final PeriodicalService periodicalService;

	@Autowired
	public EditJournalController(PeriodicalService periodicalService) { this.periodicalService = periodicalService; }

	@RequestMapping(method = RequestMethod.GET)
	public String review(Model model, @ModelAttribute("journalToEdit") Journal journal){
		model.addAttribute("journal", journal);
		return "/editjournal";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String editJournal(@Valid @ModelAttribute Journal journal, Errors errors, SessionStatus status){
		if (!errors.hasErrors()) {
			periodicalService.createOrUpdate(journal);
			status.setComplete();
			return "redirect:/journals";
		} else {
			return "/editjournal";
		}
	}

}
