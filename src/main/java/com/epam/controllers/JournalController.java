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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping(value="/journals")
@SessionAttributes("journals")
public class JournalController {

	private final PeriodicalService periodicalService;

	@Autowired
	public JournalController(PeriodicalService periodicalService) { this.periodicalService = periodicalService;	}

	@RequestMapping(method = RequestMethod.GET)
	public String getJournals(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String role = auth.getAuthorities().toArray()[0].toString();
		List<Journal> journals;
		if (role.equals("ROLE_USER")) {
			User currentUser = (User) auth.getPrincipal();
			journals = periodicalService.getJournals(currentUser.getId_user());
		} else {
			journals = periodicalService.getJournals(null);
		}
		model.addAttribute("journals", journals);
		return "/journals";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_USER')")
	public String addMyJournal (@RequestParam Long currentId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User)auth.getPrincipal();
		periodicalService.addMyChoice(currentId, currentUser.getId_user());
		return "redirect:/journals";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String editJournal (@ModelAttribute("journals") List<Journal> journals,
							   @RequestParam Long currentId, RedirectAttributes attributes) {
		Optional<Journal> journalOptional = journals.stream().filter(j -> Objects.equals(j.getId_journal(), currentId)).findAny();
		if (journalOptional.isPresent()) {
			attributes.addFlashAttribute("journalToEdit", journalOptional.get());
			return "redirect:/editjournal";
		} else {
			return "/journals";
		}
	}

}
