package nl.commerquell.adressen.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.commerquell.adressen.entity.Adres;

public interface AdresRepository extends JpaRepository<Adres, Integer> {
	
	public List<Adres> findAllByOrderByPostcodeAscHuisnrAsc();

}
