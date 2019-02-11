package nl.commerquell.adressen.pojo;

public class PasswordHolder {
	
	private String username;
	private String oldPassword = null;
	private String newPassword = null;
	private String newPasswordAgain = null;
	private String errorMessage = null;
	
	public PasswordHolder(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPasswordAgain() {
		return newPasswordAgain;
	}

	public void setNewPasswordAgain(String newPasswordAgain) {
		this.newPasswordAgain = newPasswordAgain;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public void reset() {
		this.oldPassword = null;
		this.newPassword = null;
		this.newPasswordAgain = null;
	}

	@Override
	public String toString() {
		return "PasswordHolder [username=" + username + ", passwords are protected]";
	}
	
	

}
