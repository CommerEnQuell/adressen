package nl.commerquell.adressen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import nl.commerquell.adressen.dao.PersoonAdresRepository;
import nl.commerquell.adressen.entity.PersoonAdres;

@Service
public class PersoonAdresServiceImpl implements PersoonAdresService {

	private PersoonAdresRepository persoonAdresRepository;
	
	@Autowired
	public PersoonAdresServiceImpl(PersoonAdresRepository theRepository) {
		this.persoonAdresRepository = theRepository;
	}
	
	@Override
	public PersoonAdres findByPersoonId(int pernId) {
		Optional<PersoonAdres> o = persoonAdresRepository.findById(pernId);
		PersoonAdres retval = null;
		if (o.isPresent()) {
			retval = o.get();
		}

		return retval;
	}

	@Override
	public List<PersoonAdres> findByAdresId(int adrsId) {
		PersoonAdres qbePernAdrs = new PersoonAdres();
		qbePernAdrs.setAdresId(adrsId);
		Example<PersoonAdres> x = Example.of(qbePernAdrs);
		List<PersoonAdres> retval = persoonAdresRepository.findAll(x);
		
		return retval;
	}

	@Override
	public void save(PersoonAdres persoonAdres) {
		persoonAdresRepository.save(persoonAdres);
	}

	@Override
	public void deleteByPersoonId(int pernId) {
		persoonAdresRepository.deleteById(pernId);
	}

	@Override
	public void deleteByAdresId(int adrsId) {
		List<PersoonAdres> deletables = findByAdresId(adrsId);
		for (PersoonAdres pa : deletables) {
			deleteByPersoonId(pa.getPersoonId());
		}
	}

}
