package nl.commerquell.adressen.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.commerquell.adressen.entity.Role;
import nl.commerquell.adressen.entity.RoleId;

public interface RoleRepository extends JpaRepository<Role, RoleId> {

	public List<Role> findAllByOrderByUsernameAscRoleAsc();
}
