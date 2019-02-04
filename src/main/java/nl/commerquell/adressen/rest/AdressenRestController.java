package nl.commerquell.adressen.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.commerquell.adressen.service.AdresService;

@RequestMapping("/adressenboek")
@RestController
public class AdressenRestController {

	private AdresService adresService;
	
	@Autowired
	public AdressenRestController(AdresService theAdresService) {
		this.adresService = theAdresService;
	}
}
