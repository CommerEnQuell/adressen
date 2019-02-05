package nl.commerquell.adressen.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.commerquell.adressen.entity.Persoon;

public interface PersoonRepository extends JpaRepository<Persoon, Integer> {

}
