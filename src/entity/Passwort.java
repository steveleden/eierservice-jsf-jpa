package entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the passwort database table.
 * 
 */
@Entity
@Table(name="passwort")
@NamedQuery(name="Passwort.findAll", query="SELECT p FROM Passwort p")
public class Passwort implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="fk_benutzerid", unique=true, nullable=false)
	private int fkBenutzerid;

	@Column(nullable=false, length=10)
	private String wort;

	//bi-directional one-to-one association to Benutzer
	@OneToOne
	@JoinColumn(name="fk_benutzerid", nullable=false, insertable=false, updatable=false)
	private Benutzer benutzer;

	public Passwort() {
	}

	public int getFkBenutzerid() {
		return this.fkBenutzerid;
	}

	public void setFkBenutzerid(int fkBenutzerid) {
		this.fkBenutzerid = fkBenutzerid;
	}

	public String getWort() {
		return this.wort;
	}

	public void setWort(String wort) {
		this.wort = wort;
	}

	public Benutzer getBenutzer() {
		return this.benutzer;
	}

	public void setBenutzer(Benutzer benutzer) {
		this.benutzer = benutzer;
	}

}