package web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import entity.Kunde;

public class KundeView {
	
	protected Boolean		     checked = false;
	protected Kunde			     kunde = new Kunde();
	protected BigDecimal         einzahlung = new BigDecimal(0.0);
	protected List<BenutzerView> benutzerListe = new ArrayList<BenutzerView>();

	public KundeView() {
		this.kunde.setGuthaben(new BigDecimal(0.0));
	}
	
	/* Setter & Getter */
	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Kunde getKunde() {
		
		return kunde;
	}

	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}

	public BigDecimal getEinzahlung() {
		return einzahlung;
	}

	public void setEinzahlung(BigDecimal einzahlung) {
		this.einzahlung = einzahlung;
	}
	
	public List<BenutzerView> getBenutzerListe() {	
		return benutzerListe;
	}

	public void setBenutzerListe(List<BenutzerView> benutzerListe) {
		this.benutzerListe = benutzerListe;
	}
}
