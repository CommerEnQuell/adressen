package nl.commerquell.adressen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/adressenboek/login")
@Controller
public class LoginController {
	
	@GetMapping("/")
	public String doLogin() {
		return "login-form";
	}


	@GetMapping("/access-denied")
	public String showAccessDenied() {
		return "access-denied";
	}
}
