package nl.commerquell.adressen.service;

import java.util.List;

import nl.commerquell.adressen.entity.Role;
import nl.commerquell.adressen.entity.RoleId;

public interface RoleService {
	
	public Role findById(RoleId roleId);
	
	public List<Role> findAll();
	
	public List<Role> findByUsername(String username);
	
	public void save(Role role);

	public void deleteById(RoleId roleId);
}
