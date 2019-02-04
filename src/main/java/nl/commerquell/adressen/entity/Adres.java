package nl.commerquell.adressen.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="adres")
public class Adres {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="straat")
	private String straat;
	
	@Column(name="huisnr")
	private String huisnr;

	@Column(name="huisnr_toev")
	private String huisnrToev;
	
	@Column(name="postcode")
	private String postcode;
	
	@Column(name="plaats")
	private String plaats;
	
	@Column(name="landafk")
	private String landafk;
	
	@Column(name="telnr_vast")
	private String telnrVast;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStraat() {
		return straat;
	}

	public void setStraat(String straat) {
		this.straat = straat;
	}

	public String getHuisnr() {
		return huisnr;
	}

	public void setHuisnr(String huisnr) {
		this.huisnr = huisnr;
	}

	public String getHuisnrToev() {
		return huisnrToev;
	}

	public void setHuisnrToev(String huisnrToev) {
		this.huisnrToev = huisnrToev;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getPlaats() {
		return plaats;
	}

	public void setPlaats(String plaats) {
		this.plaats = plaats;
	}

	public String getLandafk() {
		return landafk;
	}

	public void setLandafk(String landafk) {
		this.landafk = landafk;
	}

	public String getTelnrVast() {
		return telnrVast;
	}

	public void setTelnrVast(String telnrVast) {
		this.telnrVast = telnrVast;
	}

	@Override
	public String toString() {
		return "Adres [id=" + id + ", straat=" + straat + ", huisnr=" + huisnr + ", huisnrToev=" + huisnrToev
				+ ", postcode=" + postcode + ", plaats=" + plaats + ", landafk=" + landafk + ", telnrVast=" + telnrVast
				+ "]";
	}
}
