package nl.commerquell.adressen.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import nl.commerquell.adressen.utils.Utils;

@RequestMapping("/adressenboek")
@Controller
public class HomeController {
	@Autowired
	private Environment env;
	
	@GetMapping({"", "/"})
	public String goHome(HttpSession session, Model theModel) {
		User theUser = Utils.getUserFromSession(session);
		theModel.addAttribute("loginUser", theUser.getUsername());
		boolean isAdmin = false;
		boolean isReader = false;
		boolean isUser = false;
		for (GrantedAuthority auth : theUser.getAuthorities()) {
			if ("ROLE_ADMIN".equals(auth.getAuthority())) {
				isAdmin = true;
			} else if ("ROLE_USER".equals(auth.getAuthority())) {
				isUser = true;
			} else if ("ROLE_READER".equals(auth.getAuthority())) {
				isReader = true;
			}
		}
		theModel.addAttribute("appVersion", env.getProperty("info.app.version", "x.y.z"));
		theModel.addAttribute("isAdmin", Boolean.valueOf(isAdmin));
		theModel.addAttribute("isReader", Boolean.valueOf(isReader));
		theModel.addAttribute("isUser", Boolean.valueOf(isUser));
		
		return "home";
	}
}
