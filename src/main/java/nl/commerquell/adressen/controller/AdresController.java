package nl.commerquell.adressen.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import nl.commerquell.adressen.entity.Adres;
import nl.commerquell.adressen.service.AdresService;

@RequestMapping("/adressenboek/adressen")
@Controller
public class AdresController {
	private static final Logger logger = Logger.getLogger(AdresController.class.getName());

	private AdresService adresService;
	
	@Autowired
	public AdresController(AdresService theAdresService) {
		this.adresService = theAdresService;
	}
	
	@GetMapping("/")
	public String findAll(Model theModel) {
		List<Adres> adressen = adresService.findAll();
		theModel.addAttribute("adressen", adressen);
		return "adressenlijst";
		
	}
	
	@GetMapping("/voegAdresToe")
	public String showFormForAdd(Model theModel) {
		Adres hetAdres = new Adres();
		hetAdres.setId(0);
		
		theModel.addAttribute("adres", hetAdres);
		
		return "adres-form";
	}
	
	@GetMapping("/wijzigAdres/{id}")
	public String showFormForUpdate(@PathVariable("id") int theId, Model theModel) {
		Adres adres = adresService.findById(theId);
		theModel.addAttribute("adres", adres);
		return "adres-form";
	}
	
	@GetMapping("/verwijderAdres/{id}")
	public String showFormForDelete(@PathVariable("id") int theId, Model theModel) {
		Adres adres = adresService.findById(theId);
		theModel.addAttribute("adres", adres);
		return "adres-exit-form";
	}
	
	@PostMapping("/saveAdres")
	public String saveAdres(@ModelAttribute("adres") Adres hetAdres) {
		logger.info("Opslaan: " + hetAdres);
		adresService.save(hetAdres);
		
		return "redirect:/adressenboek/adressen/";
	}
	
	@PostMapping("/deleteAdres")
	public String deleteAdres(@ModelAttribute("adres") Adres hetAdres) {
		logger.info("Verwijderen: " + hetAdres);
		adresService.deleteById(hetAdres.getId());
		
		return "redirect:/adressenboek/adressen/";
	}
}
