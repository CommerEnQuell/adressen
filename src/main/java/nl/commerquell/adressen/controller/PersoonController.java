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
import nl.commerquell.adressen.entity.Persoon;
import nl.commerquell.adressen.entity.PersoonAdres;
import nl.commerquell.adressen.service.AdresService;
import nl.commerquell.adressen.service.PersoonAdresService;
import nl.commerquell.adressen.service.PersoonService;
import nl.commerquell.adressen.utils.Utils;

@RequestMapping("/adressenboek/personen")
@Controller
public class PersoonController {
	private static final Logger logger = Logger.getLogger(PersoonController.class.getName());

	private PersoonService persoonService;
	private PersoonAdresService persoonAdresService;
	private AdresService adresService;
	
	@Autowired
	public PersoonController(PersoonService thePersoonService) {
		this.persoonService = thePersoonService;
	}
	
	@Autowired
	public void setPersoonAdresService(PersoonAdresService persoonAdresService) {
		this.persoonAdresService = persoonAdresService;
		logger.info("PersoonAdresService " + (persoonAdresService != null ? "set" : "is null"));
	}

	@Autowired
	public void setAdresService(AdresService adresService) {
		this.adresService = adresService;
		logger.info("AdresService " + (adresService != null ? "set" : "is null"));
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
	
	@GetMapping("/wijsAdresToe/{id}")
	public String wijsAdresToe(@PathVariable("id") int pernId, Model theModel) {
		Persoon persoon = persoonService.findById(pernId);
		theModel.addAttribute("persoon", persoon);
		List<Adres> adressen = adresService.findAll();
		theModel.addAttribute("adressen", adressen);
		return "toewijzen-adres";
	}
	
	@GetMapping("/toewijzenAdres/{pernId}/{adrsId}")
	public String toewijzenAdres(@PathVariable("pernId") int pernId, @PathVariable("adrsId") int adrsId, Model theModel) {
		logger.fine("Adres #" + adrsId + " toewijzen aan persoon #" + pernId);
		PersoonAdres persoonAdres = persoonAdresService.findByPersoonId(pernId);
		if (persoonAdres == null) {
			persoonAdres = new PersoonAdres();
			persoonAdres.setPersoonId(pernId);
		}
		persoonAdres.setAdresId(adrsId);
		persoonAdresService.save(persoonAdres);
		logger.info("Adres #" + adrsId + " toegewezen aan persoon #" + pernId);
		return "redirect:/adressenboek/personen/";
	}
	
	@GetMapping("/wijsNieuwAdresToe/{pernId}")
	public String nieuwAdresToewijzen(@PathVariable("pernId") int pernId, Model theModel) {
		Persoon persoon = persoonService.findById(pernId);
		theModel.addAttribute("persoon", persoon);
		Adres hetAdres = new Adres();
		hetAdres.setId(0);
		theModel.addAttribute("adres", hetAdres);
		return "persoon-adres-form";
	}
	
	@PostMapping("/nieuwAdresToewijzen/{pernId}")
	public String savePersoonAdres(@PathVariable("pernId") int pernId, @ModelAttribute("adres") Adres hetAdres) {
		List<Adres> andereAdressen = adresService.find(hetAdres.getPostcode(), hetAdres.getHuisnr());
		boolean found = false;
		Adres nieuwAdres = hetAdres;
		if (andereAdressen != null && !andereAdressen.isEmpty()) {
			for (Adres anderAdres : andereAdressen) {
				if (Utils.nvl(hetAdres.getHuisnrToev(), "*").equals(Utils.nvl(anderAdres.getHuisnrToev(), "*"))) {
					nieuwAdres = anderAdres;
					found = true;
					break;
				}
				if (hetAdres.getHuisnrToev() != null && anderAdres.getHuisnrToev() != null) {
					if (hetAdres.getHuisnrToev().toUpperCase().equals(anderAdres.getHuisnrToev().toUpperCase())) {
						nieuwAdres = anderAdres;
						found = true;
						break;
					}
				}
			}
		}
		if (!found) {
			adresService.save(nieuwAdres);
		}
		found = false;
		PersoonAdres persoonAdres = persoonAdresService.findByPersoonId(pernId);
		if (persoonAdres == null) {
			persoonAdres = new PersoonAdres();
			persoonAdres.setPersoonId(pernId);
		}
		persoonAdres.setAdresId(nieuwAdres.getId());
		persoonAdresService.save(persoonAdres);
		logger.info ("Adres #" + nieuwAdres.getId() + " toegewezen aan persoon #" + pernId);
		return "redirect:/adressenboek/personen/";
	}
}
