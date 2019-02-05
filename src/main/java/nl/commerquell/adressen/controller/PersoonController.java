package nl.commerquell.adressen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import nl.commerquell.adressen.entity.Adres;
import nl.commerquell.adressen.entity.Persoon;
import nl.commerquell.adressen.service.PersoonService;

@RequestMapping("/adressenboek/personen")
@Controller
public class PersoonController {

	private PersoonService persoonService;
	
	@Autowired
	public PersoonController(PersoonService thePersoonService) {
		this.persoonService = thePersoonService;
	}
	
	@GetMapping("/")
	public String findAll(Model theModel) {
		List<Persoon> personen = persoonService.findAll();
		theModel.addAttribute("personen", personen);
		return "personenlijst";
		
	}
	
	@GetMapping("/voegPersoonToe")
	public String showFormForAdd(Model theModel) {
		Persoon dePersoon = new Persoon();
		dePersoon.setId(0);
		
		theModel.addAttribute("persoon", dePersoon);
		
		return "persoon-form";
	}
	
	@GetMapping("/wijzigPersoon/{id}")
	public String showFormForUpdate(@PathVariable("id") int theId, Model theModel) {
		Persoon persoon = persoonService.findById(theId);
		theModel.addAttribute("persoon", persoon);
		return "persoon-form";
	}
	
	@GetMapping("/verwijderPersoon/{id}")
	public String showFormForDelete(@PathVariable("id") int theId, Model theModel) {
		Persoon persoon = persoonService.findById(theId);
		theModel.addAttribute("persoon", persoon);
		return "persoon-exit-form";
	}
	
	@PostMapping("/savePersoon")
	public String savePersoon(@ModelAttribute("persoon") Persoon dePersoon) {
		System.out.println("Opslaan: " + dePersoon);
		persoonService.save(dePersoon);
		
		return "redirect:/adressenboek/personen/";
	}
	
	@PostMapping("/deletePersoon")
	public String deletePersoon(@ModelAttribute("persoon") Persoon dePersoon) {
		System.out.println("Verwijderen: " + dePersoon);
		persoonService.deleteById(dePersoon.getId());
		
		return "redirect:/adressenboek/personen/";
	}
}
