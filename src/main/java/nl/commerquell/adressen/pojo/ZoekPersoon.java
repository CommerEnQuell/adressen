package nl.commerquell.adressen.pojo;

public class ZoekPersoon {
	
	private String voornaam;
	private String achternaam;

	public String getVoornaam() {
		return voornaam;
	}

	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}

	public String getAchternaam() {
		return achternaam;
	}

	public void setAchternaam(String achternaam) {
		this.achternaam = achternaam;
	}

	@Override
	public String toString() {
		return "ZoekPersoon [voornaam=" + voornaam + ", achternaam=" + achternaam + "]";
	}
}