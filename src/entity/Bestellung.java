package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the bestellung database table.
 * 
 */
@Entity
@Table(name="bestellung")
@NamedQuery(name="Bestellung.findAll", query="SELECT b FROM Bestellung b")
public class Bestellung implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false)
	private int anzahl;

	@Column(name="belastung_guthaben", nullable=false)
	private int belastungGuthaben;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="bestaetigt_am")
	private Date bestaetigtAm;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="bestellt_am", nullable=false)
	private Date bestelltAm;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="bezahlt_am")
	private Date bezahltAm;

	@Column(name="fk_benutzerid", nullable=false)
	private int fkBenutzerid;

	@Temporal(TemporalType.DATE)
	@Column(name="geliefert_am")
	private Date geliefertAm;

	@Column(length=255)
	private String kommentar;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date liefertermin;

	@Temporal(TemporalType.DATE)
	@Column(name="liefertermin_bestaetigt")
	private Date lieferterminBestaetigt;

	@Column(precision=10, scale=2)
	private BigDecimal preistotal;

	@Column(nullable=false)
	private int status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="storniert_am")
	private Date storniertAm;

	@Column(nullable=false, precision=10, scale=2)
	private BigDecimal stueckpreis;

	//bi-directional many-to-one association to Kunde
	@ManyToOne
	@JoinColumn(name="fk_kundeid", nullable=false)
	private Kunde kunde;

	public Bestellung() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAnzahl() {
		return this.anzahl;
	}

	public void setAnzahl(int anzahl) {
		this.anzahl = anzahl;
	}

	public int getBelastungGuthaben() {
		return this.belastungGuthaben;
	}

	public void setBelastungGuthaben(int belastungGuthaben) {
		this.belastungGuthaben = belastungGuthaben;
	}

	public Date getBestaetigtAm() {
		return this.bestaetigtAm;
	}

	public void setBestaetigtAm(Date bestaetigtAm) {
		this.bestaetigtAm = bestaetigtAm;
	}

	public Date getBestelltAm() {
		return this.bestelltAm;
	}

	public void setBestelltAm(Date bestelltAm) {
		this.bestelltAm = bestelltAm;
	}

	public Date getBezahltAm() {
		return this.bezahltAm;
	}

	public void setBezahltAm(Date bezahltAm) {
		this.bezahltAm = bezahltAm;
	}

	public int getFkBenutzerid() {
		return this.fkBenutzerid;
	}

	public void setFkBenutzerid(int fkBenutzerid) {
		this.fkBenutzerid = fkBenutzerid;
	}

	public Date getGeliefertAm() {
		return this.geliefertAm;
	}

	public void setGeliefertAm(Date geliefertAm) {
		this.geliefertAm = geliefertAm;
	}

	public String getKommentar() {
		return this.kommentar;
	}

	public void setKommentar(String kommentar) {
		this.kommentar = kommentar;
	}

	public Date getLiefertermin() {
		return this.liefertermin;
	}

	public void setLiefertermin(Date liefertermin) {
		this.liefertermin = liefertermin;
	}

	public Date getLieferterminBestaetigt() {
		return this.lieferterminBestaetigt;
	}

	public void setLieferterminBestaetigt(Date lieferterminBestaetigt) {
		this.lieferterminBestaetigt = lieferterminBestaetigt;
	}

	public BigDecimal getPreistotal() {
		return this.preistotal;
	}

	public void setPreistotal(BigDecimal preistotal) {
		this.preistotal = preistotal;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getStorniertAm() {
		return this.storniertAm;
	}

	public void setStorniertAm(Date storniertAm) {
		this.storniertAm = storniertAm;
	}

	public BigDecimal getStueckpreis() {
		return this.stueckpreis;
	}

	public void setStueckpreis(BigDecimal stueckpreis) {
		this.stueckpreis = stueckpreis;
	}

	public Kunde getKunde() {
		return this.kunde;
	}

	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}

}