package nl.commerquell.adressen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import nl.commerquell.adressen.entity.Adres;
import nl.commerquell.adressen.entity.Persoon;
import nl.commerquell.adressen.entity.PersoonAdres;
import nl.commerquell.adressen.service.AdresService;
import nl.commerquell.adressen.service.PersoonAdresService;
import nl.commerquell.adressen.service.PersoonService;

@RequestMapping("/adressenboek/query")
@Controller
public class QueryController {
	
	private PersoonService persoonService;
	private AdresService adresService;
	private PersoonAdresService persoonAdresService;
	
	@Autowired
	public QueryController(PersoonService persoonService) {
		this.persoonService = persoonService;
	}

	@Autowired
	public void setAdresService(AdresService adresService) {
		this.adresService = adresService;
	}

	@Autowired
	public void setPersoonAdresService(PersoonAdresService persoonAdresService) {
		this.persoonAdresService = persoonAdresService;
	}

	@GetMapping("/")
	public String personenEnAdressenlijst(Model theModel) {
		List<Persoon> personen = persoonService.findAll();
		for (Persoon persoon : personen) {
			int pernId = persoon.getId();
			PersoonAdres persoonAdres = persoonAdresService.findByPersoonId(pernId);
			Adres adres = null;
			if (persoonAdres == null) {
				persoonAdres = new PersoonAdres();
				persoonAdres.setPersoonId(pernId);
				persoonAdres.setAdresId(0);
				persoonAdres.setAdresId(0);
				adres = new Adres();
				adres.setId(0);
				adres.setHuisnr(" ");
				adres.setHuisnrToev(" ");
			} else {
				int adrsId = persoonAdres.getAdresId();
				adres = adresService.findById(adrsId);
			}
			persoonAdres.setAdres(adres);
			persoon.setPersoonAdres(persoonAdres);
		}
		theModel.addAttribute("personen", personen);
		return "personen-adressenlijst";
	}
	
	@GetMapping("/details/{pernId}")
	public String getDetails(@PathVariable("pernId") int pernId, Model theModel) {
		Persoon persoon = persoonService.findById(pernId);
		PersoonAdres persoonAdres = persoonAdresService.findByPersoonId(pernId);
		Adres adres = null;
		if (persoonAdres == null) {
			persoonAdres = new PersoonAdres();
			persoonAdres.setPersoonId(pernId);
			persoonAdres.setAdresId(0);
			adres = new Adres();
			adres.setId(0);
		} else {
			adres = adresService.findById(persoonAdres.getAdresId());
		}
		theModel.addAttribute("persoon", persoon);
		theModel.addAttribute("adres", adres);
		return "persoon-details";
	}
}
