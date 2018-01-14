package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the benutzer database table.
 * 
 */
@Entity
@Table(name="benutzer", schema="eggstoreprod")
@NamedQuery(name="Benutzer.findAll", query="SELECT b FROM Benutzer b")
public class Benutzer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(length=254)
	private String email;

	@Column(nullable=false)
	private int loginCounter;

	@Temporal(TemporalType.TIMESTAMP)
	private Date loginZuletzt;

	@Column(nullable=false, length=15)
	private String name;

	@Column(nullable=false)
	private int rolle;

	//bi-directional many-to-one association to Kunde
	@ManyToOne
	@JoinColumn(name="fk_kundeid")
	private Kunde kunde;

	//bi-directional one-to-one association to Passwort
	@OneToOne(mappedBy="benutzer")
	private Passwort passwort;

	public Benutzer() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getLoginCounter() {
		return this.loginCounter;
	}

	public void setLoginCounter(int loginCounter) {
		this.loginCounter = loginCounter;
	}

	public Date getLoginZuletzt() {
		return this.loginZuletzt;
	}

	public void setLoginZuletzt(Date loginZuletzt) {
		this.loginZuletzt = loginZuletzt;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRolle() {
		return this.rolle;
	}

	public void setRolle(int rolle) {
		this.rolle = rolle;
	}

	public Kunde getKunde() {
		return this.kunde;
	}

	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}

	public Passwort getPasswort() {
		return this.passwort;
	}

	public void setPasswort(Passwort passwort) {
		this.passwort = passwort;
	}

}