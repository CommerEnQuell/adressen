package nl.commerquell.adressen.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nl.commerquell.adressen.utils.IntToBooleanConverter;

@Entity
@Table(name="users")
public class User {

	@Id
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="enabled")
	@Convert(converter=IntToBooleanConverter.class)
	private Boolean enabled;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		String thePassword = password;
		if (password != null) {
			if (password.startsWith("{noop}")) {
				thePassword = password.substring("{noop}".length());
			} else if (password.startsWith("{") && password.indexOf("}") > 0) {
				String encryptionMethod = password.substring(1, password.indexOf("}"));
				thePassword = "<< encrypted with " + encryptionMethod + " >>";
			}
		}
		return thePassword;
	}

	public void setPassword(String password) {
		if (password.startsWith("<< encrypted with " )) {
			return;
		}
		String prefix = "";
		String thePassword = password;
		if (!password.startsWith("{")) {
			prefix="{noop}";
		}
		this.password = prefix + password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", enabled=" + enabled + "]";
	}
}
