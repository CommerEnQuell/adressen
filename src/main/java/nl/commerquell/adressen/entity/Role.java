package nl.commerquell.adressen.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(RoleId.class)
@Table(name="authorities")
public class Role {
	
	@Column(name="username")
	@Id
	private String username;
	
	@Column(name="authority")
	@Id
	private String role;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		String prefix = "ROLE_";
		String theRole = role;
		if (theRole.startsWith(prefix)) {
			theRole = theRole.substring(prefix.length());
		}
			
		return theRole;
	}

	public void setRole(String theRole) {
		String prefix = "";
		if (theRole != null && !theRole.startsWith("ROLE_")) {
			prefix = "ROLE_";
		}
			
		this.role = prefix + theRole;
	}

	@Override
	public String toString() {
		return "Role [username=" + username + ", role=" + role + "]";
	}
}
