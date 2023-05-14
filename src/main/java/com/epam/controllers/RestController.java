//package com.epam.controllers;
//
//import com.epam.entities.Journal;
//import com.epam.entities.User;
//import com.epam.services.PeriodicalService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@Controller
//@RequestMapping(value="/rest")
//public class RestController {
//
//	private final PeriodicalService periodicalService;
//
//	@Autowired
//	public RestController(PeriodicalService periodicalService) {
//		this.periodicalService = periodicalService;
//	}
//
//	@RequestMapping(value = "/mychoices", method = RequestMethod.GET)
//	public @ResponseBody List<Journal> myChoices(){
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		User currentUser = (User)auth.getPrincipal();
//		List<List<Journal>> userJournals = periodicalService.getUserJournals(currentUser.getId_user());
//		return userJournals.get(0);
//	}
//
//	@RequestMapping(value = "/mysubscriptions", method = RequestMethod.GET)
//	public @ResponseBody List<Journal> mySubscriptions(){
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		User currentUser = (User)auth.getPrincipal();
//		List<List<Journal>> userJournals = periodicalService.getUserJournals(currentUser.getId_user());
//		return userJournals.get(1);
//	}
//
//	@RequestMapping(value = "/sumtopay", method = RequestMethod.GET)
//	public @ResponseBody BigDecimal sumToPay(){
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		User currentUser = (User)auth.getPrincipal();
//		return periodicalService.sumToPay(currentUser.getId_user());
//	}
//
//	@RequestMapping(value = "/delete", method = RequestMethod.POST)
//	public @ResponseBody void deleteMyChoice (@RequestBody Long choiceId) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		User currentUser = (User)auth.getPrincipal();
//		periodicalService.deleteMyChoice(choiceId, currentUser.getId_user());
//	}
//
//	@RequestMapping(value = "/pay", method = RequestMethod.POST)
//	public @ResponseBody void pay(@RequestBody BigDecimal sum) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		User currentUser = (User)auth.getPrincipal();
//		periodicalService.pay(sum, currentUser.getId_user());
//	}
//
//}
