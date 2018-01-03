package web;

import web.BenutzerBean.BenutzerRolle;
import entity.Benutzer;

public class BenutzerView {
	protected Boolean 	checked = false;
	protected Benutzer	benutzer = new Benutzer();
	protected BenutzerRolle rolle = BenutzerRolle.KUNDE;
	protected String	pw;
	
	
	public BenutzerRolle getRolle() {
		return rolle;
	}

	public void setRolle(BenutzerRolle rolle) {
		this.rolle = rolle;
	}
	
	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	/* Setter & Getter */
	public Benutzer getBenutzer() {
		return benutzer;
	}

	public void setBenutzer(Benutzer benutzer) {
		this.benutzer = benutzer;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
}
