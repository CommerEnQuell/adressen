package nl.commerquell.adressen.service;

import java.util.List;

import org.springframework.data.domain.Example;

import nl.commerquell.adressen.entity.Persoon;

public interface PersoonService {

	public Persoon findById(int id);
	
	public List<Persoon> findAll();
	
	public List<Persoon> search(Example<Persoon> qbeFilter);
	
	public void save(Persoon Persoon);
	
	public void deleteById(int id);

}
