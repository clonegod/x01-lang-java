package com.asynclife.clonegod.template.thymeleaf.gtvg.web.controller;

import java.util.Calendar;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asynclife.clonegod.template.thymeleaf.gtvg.business.entities.User;

@Controller
@RequestMapping("/gtvg")
public class HomeController {

	@RequestMapping("/")
	String home(Model model, HttpSession session) {
		session.setAttribute("user", new User("John", "Apricot", "Antarctica", null));
		model.addAttribute("today", Calendar.getInstance());
		return "gtvg/home";
	}
	
}
