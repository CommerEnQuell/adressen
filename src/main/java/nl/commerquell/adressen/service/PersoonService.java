package nl.commerquell.adressen.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import nl.commerquell.adressen.entity.Persoon;

public interface PersoonService {

	public Persoon findById(int id);
	
	public List<Persoon> findAll();
	
	public Page<Persoon> findAll(Pageable p);
	
	public List<Persoon> search(Example<Persoon> qbeFilter);
	
	public List<Persoon> search(String voornaam, String achternaam);
	
	public void save(Persoon Persoon);
	
	public void deleteById(int id);

}
