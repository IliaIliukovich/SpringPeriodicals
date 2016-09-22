package com.epam.controllers;

import com.epam.entities.Choice;
import com.epam.entities.Journal;
import com.epam.entities.Subscription;
import com.epam.services.PeriodicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping(value="/journals")
public class JournalController {

	@Autowired
	private PeriodicalService periodicalService;

	@RequestMapping(method = RequestMethod.GET)
	public String getJournals(Model model){
		List<Journal> journals = periodicalService.getJournals();
		model.addAttribute("journals", journals);
		return "journals";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addMyJournal (@RequestParam Long currentId) {
		periodicalService.addMyChoice(currentId);
		return "redirect:/journals";
	}

}
