package nl.commerquell.adressen.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import nl.commerquell.adressen.ApplicationConstants;
import nl.commerquell.adressen.entity.Adres;
import nl.commerquell.adressen.entity.Persoon;
import nl.commerquell.adressen.entity.PersoonAdres;
import nl.commerquell.adressen.pojo.ZoekPersoon;
import nl.commerquell.adressen.service.AdresService;
import nl.commerquell.adressen.service.PersoonAdresService;
import nl.commerquell.adressen.service.PersoonService;
import nl.commerquell.adressen.utils.Utils;

@RequestMapping("/adressenboek/query")
@Controller
public class QueryController {
	private static final Logger logger = Logger.getLogger(QueryController.class.getName());
	
	private PersoonService persoonService;
	private AdresService adresService;
	private PersoonAdresService persoonAdresService;
	
	@Autowired
	private ApplicationConstants applicationConstants;
	
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
	public String personenEnAdressenlijst(@ModelAttribute("page") int pageNo, Model theModel) {
//		List<Persoon> personen = persoonService.findAll();
		Sort theSort = Sort.by("achternaam", "voorvoegsel", "voornaam", "id");
		PageRequest p = PageRequest.of(pageNo - 1, applicationConstants.getDefaultPageSize(), theSort);
		Page<Persoon> personen = persoonService.findAll(p);
		System.err.println("Er zijn " + personen.getTotalElements() + " personen, verdeeld over " + personen.getTotalPages() + " pagina's");
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
		Utils.addPageingAttributes(theModel, personen, pageNo);
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
	
	@GetMapping("/zoekPersonen")
	public String zoekPersonen(Model theModel) {
		ZoekPersoon zoekPersoon = new ZoekPersoon();
		theModel.addAttribute("zoekPersoon", zoekPersoon);
		return "personen-query";
	}
	
	@PostMapping("/zoekPersonen")
	public String vindPersonen(@ModelAttribute("zoekPersoon") ZoekPersoon zoekPersoon, Model theModel) {
		logger.info("Zoeken op " + zoekPersoon);
		String voornaam =(zoekPersoon.getVoornaam() == null ? "" : zoekPersoon.getVoornaam());
		String achternaam =(zoekPersoon.getAchternaam() == null ? "" : zoekPersoon.getAchternaam());
		theModel.addAttribute("page", 1);
		theModel.addAttribute("voornaam", zoekPersoon.getVoornaam());
		theModel.addAttribute("achternaam", zoekPersoon.getAchternaam());
		StringBuffer buf = new StringBuffer("redirect:/adressenboek/query/gevondenPersonen?page=1");
		if (!Utils.isBlankOrEmpty(achternaam)) {
			buf.append("&achternaam=").append(Utils.toAddressBarParam(achternaam));
		}
		if (!Utils.isBlankOrEmpty(voornaam)) {
			buf.append("&voornaam=").append(Utils.toAddressBarParam(voornaam));
		}
		return buf.toString();
			
	}

	@GetMapping("/gevondenPersonen") 
	public String gevondenPersonen(@ModelAttribute("voornaam") String voornaam, @ModelAttribute("achternaam") String achternaam, @ModelAttribute("page") int pageNo, Model theModel) {
		logger.info("Zoeken op voornaam \"" + voornaam + "%\" en achternaam \"" + achternaam + "%\" (redirected) - pagina " + pageNo);
		Sort theSort = Sort.by("achternaam", "voorvoegsel", "voornaam", "id");
		PageRequest p = PageRequest.of(pageNo - 1, applicationConstants.getDefaultPageSize(), theSort);
		Page<Persoon> personen = persoonService.search(voornaam, achternaam, p);
		theModel.addAttribute("personen", personen);
		Boolean anyoneFound = Boolean.valueOf(personen.getTotalElements() > 0L);
		theModel.addAttribute("anyoneFound", anyoneFound);
		Utils.addPageingAttributes(theModel, personen, pageNo);
		return "personen-query-resultaat";		
	}

}
