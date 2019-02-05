package nl.commerquell.adressen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import nl.commerquell.adressen.dao.PersoonRepository;
import nl.commerquell.adressen.entity.Persoon;

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
		return persoonRepository.findAll();
	}

	@Override
	public List<Persoon> search(Example<Persoon> qbeFilter) {
		return persoonRepository.findAll(qbeFilter);
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
