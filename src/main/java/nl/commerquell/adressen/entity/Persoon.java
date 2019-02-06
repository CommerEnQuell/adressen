package nl.commerquell.adressen.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import nl.commerquell.adressen.utils.DateToStringConverter;

@Entity
@Table(name="persoon")
public class Persoon {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="voornaam")
	private String voornaam;

	@Column(name="voorvoegsel")
	private String voorvoegsel;
	
	@Column(name="achternaam")
	private String achternaam;
	
	@Column(name="gender")	
	private String gender;
	
	@Column(name="geboortedatum")
	@Convert(converter=DateToStringConverter.class)
	private String gebdat;
	
	@Column(name="email")
	private String email;
	
	@Column(name="telnr_mobiel")
	private String mobielTelnr;
	
	@OneToOne
	@JoinColumn(name="id", unique=true, nullable=false, updatable=false)
	private PersoonAdres persoonAdres;
	
//	private String gebdatAlfa;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVoornaam() {
		return voornaam;
	}

	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}

	public String getVoorvoegsel() {
		return voorvoegsel;
	}

	public void setVoorvoegsel(String voorvoegsel) {
		this.voorvoegsel = voorvoegsel;
	}

	public String getAchternaam() {
		return achternaam;
	}

	public void setAchternaam(String achternaam) {
		this.achternaam = achternaam;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGebdat() {
		return gebdat;
	}

	public void setGebdat(String gebdat) {
		this.gebdat = gebdat;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobielTelnr() {
		return mobielTelnr;
	}

	public void setMobielTelnr(String mobielTelnr) {
		this.mobielTelnr = mobielTelnr;
	}
	
	public PersoonAdres getPersoonAdres() {
		return persoonAdres;
	}

	public void setPersoonAdres(PersoonAdres persoonAdres) {
		this.persoonAdres = persoonAdres;
	}

	/*
	public String getGebdatAlfa() {
		if (this.gebdat == null) {
			this.gebdatAlfa = "";
		} else {
			this.gebdatAlfa = Utils.dateToString(gebdat, "DD.MM.YYYY");
		}
		return gebdatAlfa;
	}

	public void setGebdatAlfa(String gebdatAlfa) {
		if (gebdatAlfa == null || gebdatAlfa.trim().equals("")) {
			this.gebdat = null;
			this.gebdatAlfa = "";
			return;
		}
		this.gebdat = Utils.formatDate(gebdatAlfa, "DD.MM.YYYY");
		if (this.gebdat == null) {
			this.gebdatAlfa = "";
		} else {
			this.gebdatAlfa = gebdatAlfa;
		}
	}
*/
	@Override
	public String toString() {
		return "Persoon [id=" + id + ", voornaam=" + voornaam + ", voorvoegsel=" + voorvoegsel + ", achternaam="
				+ achternaam + ", gender=" + gender + ", gebdat=" + gebdat + ", email=" + email + ", mobielTelnr="
				+ mobielTelnr + "]";
	}
}
