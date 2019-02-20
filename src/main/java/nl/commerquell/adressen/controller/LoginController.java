package nl.commerquell.adressen.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpServerErrorException;

import nl.commerquell.adressen.pojo.PasswordHolder;
import nl.commerquell.adressen.service.UserService;
import nl.commerquell.adressen.utils.Utils;

@RequestMapping("/adressenboek/login")
@Controller
public class LoginController {
	
	private UserService userService;
	
	@Autowired
	public LoginController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping({"", "/"})
	public String doLogin() {
		return "login-form";
	}


	@GetMapping("/access-denied")
	public String showAccessDenied() {
		return "access-denied";
	}
	
	@GetMapping("/password/{username}")
	public String showPasswordForm(HttpSession session, @PathVariable("username") String username, Model theModel) {
		User sessionUser = Utils.getUserFromSession(session);
		if (!sessionUser.getUsername().equals(username)) {
			throw new HttpServerErrorException(HttpStatus.FORBIDDEN, "Een gebruiker kan alleen zijn eigen wachtwoord via deze pagina wijzigen");
		}
		PasswordHolder theHolder = new PasswordHolder(username);
		theModel.addAttribute("passwordHolder", theHolder);
		return "password-form";
	}
	
	@PostMapping("/password/change")
	public String changePassword(HttpSession session, @ModelAttribute("passwordHolder") PasswordHolder theHolder) {
/*
		if (Utils.isBlankOrEmpty(theHolder.getOldPassword())) {
			theHolder.reset();
			theHolder.setErrorMessage("Oud wachtwoord niet ingevuld");
			return "password-form";
		}
*/
		if (Utils.isBlankOrEmpty(theHolder.getNewPassword()) || Utils.isBlankOrEmpty(theHolder.getNewPasswordAgain())) {
			theHolder.reset();
			theHolder.setErrorMessage("Oud wachtwoord niet ingevuld");
			return "password-form";
		}
		if (!theHolder.getNewPassword().equals(theHolder.getNewPasswordAgain())) {
			theHolder.setNewPassword(null);
			theHolder.setNewPasswordAgain(null);
			theHolder.setErrorMessage("De twee ingaven van het nieuwe wachtwoord verschillen");
			return "password-form";
		}
		
		PasswordEncoder encoder = new BCryptPasswordEncoder(9);
		String encPassword = encoder.encode(theHolder.getNewPassword());
		nl.commerquell.adressen.entity.User theUser = userService.findByUsername(theHolder.getUsername());
		theUser.setPassword("{bcrypt}" + encPassword);
		userService.save(theUser);
		return "redirect:/adressenboek/";
	}
}
