package web;

import entity.Bestellung;
import entity.Kunde;

public class BestellungView {

	protected Boolean 		checked = false;
	protected Bestellung	bestellung = new Bestellung();
	protected String 		statusBestellung;
	protected Kunde			kunde = new Kunde();

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	
	public Bestellung getBestellung() {
		return bestellung;
	}

	public void setBestellung(Bestellung bestellung) {
		this.bestellung = bestellung;
	}	

	public Kunde getKunde() {
		return kunde;
	}

	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}	

	public String getStatusBestellung() {
		
		switch (bestellung.getStatus()) {
		case 1:
			return "bestellt";
		case 2:
			return "bestaetigt";
		case 3:
			return "geliefert";
		case 4:
			return "abgeschlossen";
		case 5:
			return "storniert";
		default:
			return "keine Status";
		}
		
	}

	public void setStatusBestellung(String statusBestellung) {
		this.statusBestellung = statusBestellung;
	}

}
