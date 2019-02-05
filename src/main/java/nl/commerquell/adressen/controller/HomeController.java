package nl.commerquell.adressen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/adressenboek")
@Controller
public class HomeController {
	
	@GetMapping("/")
	public String goHome() {
		return "home";
	}

}
