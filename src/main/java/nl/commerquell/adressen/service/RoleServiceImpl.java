package nl.commerquell.adressen.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.commerquell.adressen.dao.RoleRepository;
import nl.commerquell.adressen.entity.Role;
import nl.commerquell.adressen.entity.RoleId;

@Service
public class RoleServiceImpl implements RoleService {
	
	private RoleRepository roleRepository;
	
	@Autowired
	public RoleServiceImpl(RoleRepository repository) {
		this.roleRepository = repository;
	}

	@Override
	public Role findById(RoleId roleId) {
		Optional<Role> optRole =  roleRepository.findById(roleId);
		if (!optRole.isPresent()) {
			throw new RuntimeException("Role not found: " + roleId);
		}
		return optRole.get();
	}

	@Override
	public List<Role> findAll() {
		return roleRepository.findAllByOrderByUsernameAscRoleAsc();
	}

	@Override
	public List<Role> findByUsername(String username) {
/*
		Role qbeRole = new Role();
		qbeRole.setUsername(username);
		qbeRole.setRole("READER");
		Example<Role> x = Example.of(qbeRole);
		List<Role> retval =  roleRepository.findAll(x);
*/
		List<Role> roles = findAll();
		List<Role> retval = new ArrayList<>();
		for (Role aRole : roles) {
			if (aRole.getUsername().equals(username)) {
				retval.add(aRole);
			}
		}
		return retval;
	}

	@Override
	public void save(Role role) {
		roleRepository.save(role);
	}

	@Override
	public void deleteById(RoleId roleId) {
		roleRepository.deleteById(roleId);
	}

}
