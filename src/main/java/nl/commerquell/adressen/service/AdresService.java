package nl.commerquell.adressen.service;

import java.util.List;

import org.springframework.data.domain.Example;

import nl.commerquell.adressen.entity.Adres;

public interface AdresService {
	
	public Adres findById(int id);
	
	public List<Adres> find(String postcode, String huisnr);
	
	public List<Adres> findAll();
	
	public List<Adres> search(Example<Adres> qbeFilter);
	
	public void save(Adres adres);
	
	public void deleteById(int id);

}
