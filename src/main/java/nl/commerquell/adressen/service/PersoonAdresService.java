package nl.commerquell.adressen.service;

import java.util.List;

import nl.commerquell.adressen.entity.PersoonAdres;

public interface PersoonAdresService {

	public PersoonAdres findByPersoonId(int pernId);
	
	public List<PersoonAdres> findByAdresId(int adrsId);
	
	public void save(PersoonAdres persoonAdres);
	
	public void deleteByPersoonId(int pernId);
	
	public void deleteByAdresId(int adrsId);

}
