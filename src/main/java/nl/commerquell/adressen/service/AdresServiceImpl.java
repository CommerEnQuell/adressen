package nl.commerquell.adressen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import nl.commerquell.adressen.dao.AdresRepository;
import nl.commerquell.adressen.entity.Adres;

@Service
public class AdresServiceImpl implements AdresService {
	
	private AdresRepository adresRepository;
	
	@Autowired
	public AdresServiceImpl(AdresRepository theAdresRepository) {
		this.adresRepository = theAdresRepository;
	}

	@Override
	public Adres findById(int id) {
		Optional<Adres> result = adresRepository.findById(id);
		Adres retval = null;
		if (!result.isPresent()) {
			throw new RuntimeException("Adres #" + id + " ontbreekt");
		}
		retval = result.get();
		return retval;
	}

	@Override
	public List<Adres> find(String postcode, String huisnr) {
		Adres qbeAdres = new Adres();
		qbeAdres.setPostcode(postcode);
		qbeAdres.setHuisnr(huisnr);
		Example<Adres> qbeFilter = Example.of(qbeAdres);
		return search(qbeFilter);
	}

	@Override
	public List<Adres> findAll() {
		return adresRepository.findAll();
	}

	@Override
	public List<Adres> search(Example<Adres> qbeFilter) {
		return adresRepository.findAll(qbeFilter);
	}

	@Override
	public void save(Adres adres) {
		adresRepository.save(adres);
	}

	@Override
	public void deleteById(int id) {
		adresRepository.deleteById(id);
	}

}
