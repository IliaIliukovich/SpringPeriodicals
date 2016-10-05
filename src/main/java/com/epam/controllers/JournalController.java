package com.epam.controllers;

import com.epam.entities.Journal;
import com.epam.entities.User;
import com.epam.services.PeriodicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping(value="/journals")
public class JournalController {

	private final PeriodicalService periodicalService;

	@Autowired
	public JournalController(PeriodicalService periodicalService) { this.periodicalService = periodicalService;	}

	@RequestMapping(method = RequestMethod.GET)
	public String getJournals(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String role = auth.getAuthorities().toArray()[0].toString();
		List<Journal> journals;
		if (role.equals("ROLE_ADMIN") || role.equals("ROLE_USER")) {
			User currentUser = (User) auth.getPrincipal();
			journals = periodicalService.getJournals(currentUser);
		} else {
			journals = periodicalService.getJournals();
		}
		model.addAttribute("journals", journals);
		return "journals";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	public String addMyJournal (@RequestParam Long currentId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User)auth.getPrincipal();
		periodicalService.addMyChoice(currentId, currentUser);
		return "redirect:/journals";
	}

}
