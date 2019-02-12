package nl.commerquell.adressen.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import nl.commerquell.adressen.entity.Persoon;

public interface PersoonRepository extends JpaRepository<Persoon, Integer> {
	
	public List<Persoon> findAllByOrderByAchternaamAscVoorvoegselAscVoornaamAsc();
	
	@Query("SELECT p FROM Persoon p WHERE p.voornaam LIKE :voornaam AND p.achternaam LIKE :achternaam")
	public List<Persoon> findAllByVoornaamAndAchternaam(@Param("voornaam") String voornaam, @Param("achternaam") String achternaam);

}
