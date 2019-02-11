package nl.commerquell.adressen.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.commerquell.adressen.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	public List<User> findAllByOrderByUsernameAsc();
}
