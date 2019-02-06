package nl.commerquell.adressen.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="persoon_adres")
public class PersoonAdres {
	
	@Id
	@Column(name="pern_id")
	private int persoonId;
	
	@Column(name="adrs_id")
	private int adresId;
	
	@OneToOne(mappedBy="persoonAdres")
	@JoinColumn(name="pern_id")
	private Persoon persoon;
	
	@ManyToOne
	@JoinColumn(name="adrs_id", insertable=false, updatable=false)
	private Adres adres;

	public int getPersoonId() {
		return persoonId;
	}

	public void setPersoonId(int persoonId) {
		this.persoonId = persoonId;
	}

	public int getAdresId() {
		return adresId;
	}

	public void setAdresId(int adresId) {
		this.adresId = adresId;
	}

	public Persoon getPersoon() {
		return persoon;
	}

	public void setPersoon(Persoon persoon) {
		this.persoon = persoon;
	}

	public Adres getAdres() {
		return adres;
	}

	public void setAdres(Adres adres) {
		this.adres = adres;
	}

	@Override
	public String toString() {
		return "PersoonAdres [persoonId=" + persoonId + ", adresId=" + adresId + "]";
	}
}
