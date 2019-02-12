package nl.commerquell.adressen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import nl.commerquell.adressen.dao.PersoonRepository;
import nl.commerquell.adressen.entity.Persoon;
import nl.commerquell.adressen.utils.Utils;

@Service
public class PersoonServiceImpl implements PersoonService {
	
	private PersoonRepository persoonRepository;
	
	@Autowired
	public PersoonServiceImpl(PersoonRepository thePersoonRepository) {
		this.persoonRepository = thePersoonRepository;
	}

	@Override
	public Persoon findById(int id) {
		Optional<Persoon> result = persoonRepository.findById(id);
		Persoon retval = null;
		if (!result.isPresent()) {
			throw new RuntimeException("Persoon #" + id + " ontbreekt");
		}
		retval = result.get();
		return retval;
	}

	@Override
	public List<Persoon> findAll() {
		return persoonRepository.findAllByOrderByAchternaamAscVoorvoegselAscVoornaamAsc();
	}
	
	@Override
	public Page<Persoon> findAll(Pageable p) {
		return persoonRepository.findAll(p);
	}

	@Override
	public List<Persoon> search(Example<Persoon> qbeFilter) {
		return persoonRepository.findAll(qbeFilter);
	}


	@Override
	public List<Persoon> search(String voornaam, String achternaam) {
		String deVoornaam = Utils.like(voornaam);
		String deAchternaam = Utils.like(achternaam);
		return persoonRepository.findAllByVoornaamAndAchternaam(deVoornaam, deAchternaam);
		
	}
	
	@Override
	public void save(Persoon persoon) {
		persoonRepository.save(persoon);
	}

	@Override
	public void deleteById(int id) {
		persoonRepository.deleteById(id);
	}

}
