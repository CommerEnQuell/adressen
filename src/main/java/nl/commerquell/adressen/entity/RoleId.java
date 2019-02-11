package nl.commerquell.adressen.entity;

import java.io.Serializable;

public class RoleId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8778588382487737126L;
	
	String username;
	String role;
	
	public RoleId() {
		this(null, null);
	}
	
	public RoleId(String username, String role) {
		this.username = username;
		setRole(role);
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		String prefix = "";
		if (role != null && !role.startsWith("ROLE_")) {
			prefix = "ROLE_";
		}
			
		this.role = prefix + role;
	}
	
	@Override
	public String toString() {
		return "RoleId [username=" + username + ", role=" + role + "]";
	}
	
}
