package nl.commerquell.adressen.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpServerErrorException;

import nl.commerquell.adressen.entity.Role;
import nl.commerquell.adressen.entity.RoleId;
import nl.commerquell.adressen.entity.User;
import nl.commerquell.adressen.service.RoleService;
import nl.commerquell.adressen.service.UserService;

@RequestMapping("/adressenboek/users")
@Controller
public class UsersController {
	
	private UserService userService;
	
	private RoleService roleService;
	
	@Autowired
	public UsersController(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	@GetMapping("/")
	public String listUsers(Model theModel) {
		List<User> users = userService.findAll();
		theModel.addAttribute("users", users);
		return "gebruikerslijst";
	}
	
	@GetMapping("/modifyUser/{username}")
	public String modifyUser(@PathVariable("username") String username, Model theModel) {
		if ("admin".equals(username)) {
			throw new HttpServerErrorException(HttpStatus.FORBIDDEN, "No modifications on admin allowed");
		}
		User theUser = userService.findByUsername(username);
		theModel.addAttribute("user", theUser);
		List<Role> roles = roleService.findByUsername(username);
		theModel.addAttribute("roles", roles);
		
		theModel.addAttribute("existing", Boolean.TRUE);
		return "gebruiker-form";
	}
	
	@GetMapping("/addUser")
	public String addUser(Model theModel) {
		User theUser = new User();
		theUser.setEnabled(Boolean.TRUE);
		theModel.addAttribute("user", theUser);
		
		List<Role> theRoles = new ArrayList<Role>();
		theModel.addAttribute("roles", theRoles);

		theModel.addAttribute("existing", Boolean.FALSE);
		return "gebruiker-form";
	}
	
	@PostMapping("/saveUser")
	public String doSave(@ModelAttribute("user") User theUser) {
		if ("admin".equals(theUser.getUsername())) {
			throw new HttpServerErrorException(HttpStatus.FORBIDDEN, "Sneaking in on admin is not allowed");
		}
		
		
		userService.save(theUser);
		return "redirect:/adressenboek/users/";
	}
	
	@GetMapping("/addRole/{username}")
	public String addRole(@PathVariable("username") String username, Model theModel) {
		Role theRole = new Role();
		theRole.setUsername(username);
		theRole.setRole("");
		theModel.addAttribute("role", theRole);
		
		return "rol-toewijzen";
	}

	@PostMapping("/saveRole")
	public String saveRole(@ModelAttribute("role") Role theRole) {
		roleService.save(theRole);
		return "redirect:/adressenboek/users/modifyUser/" + theRole.getUsername();
	}
	
	@GetMapping("/deleteRole/{username}/{role}")
	public String deleteRole(@PathVariable("username") String username, @PathVariable("role") String role, Model theModel) {
		RoleId theId = new RoleId();
		theId.setUsername(username);
		theId.setRole(role);
		
		Role theRole = roleService.findById(theId);
		theModel.addAttribute("rol", theRole);
		
		System.err.println("Role added to model: " + theRole);
		
		return "rol-verwijderen";
	}
	
	@PostMapping("/deleteRole")
	public String deleteRole(@ModelAttribute("rol") Role theRole, Model theModel) {
		System.err.println("Method deleteRole entered with role " + theRole);
		String username = theRole.getUsername();
		String roleId = theRole.getRole();
		System.err.println("Role: " + theRole);
		RoleId theId = new RoleId(username, roleId);
		roleService.deleteById(theId);
		return "redirect:/adressenboek/users/modifyUser/" + username;

	}
}
