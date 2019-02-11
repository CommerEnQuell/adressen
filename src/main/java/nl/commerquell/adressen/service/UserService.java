package nl.commerquell.adressen.service;

import java.util.List;

import nl.commerquell.adressen.entity.User;

public interface UserService {
	
	public User findByUsername(String username);
	
	public List<User> findAll();

	public void save(User user);
	
	public void deleteByUsername(String username);
}
