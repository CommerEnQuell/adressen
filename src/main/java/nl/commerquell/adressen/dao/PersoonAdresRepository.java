package nl.commerquell.adressen.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.commerquell.adressen.entity.PersoonAdres;

public interface PersoonAdresRepository extends JpaRepository<PersoonAdres, Integer> {

}
