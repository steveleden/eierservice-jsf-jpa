package authorisierungBeans;

import entity.Benutzer;

import java.io.Serializable;

import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import datenVerwaltungInterface.BenutzerVerwaltungInt;

/* Falls authorisiert: Gibt die Rolle (1 = Benutzer, 2 = Administrator) zurueck. */
/* Falls nicht authorisiert: 0                                                  */
@ManagedBean(name="authorisierung", eager=true)
@SessionScoped
public class AuthorisierungBean implements AuthorisierungInt, Serializable{
	
	private static final long serialVersionUID = 8126270038550476552L;
	
	/* Dependency Injection */
	@ManagedProperty(value="#{benutzerVerwaltung}")
	private	BenutzerVerwaltungInt benutzerVerwaltung;

	public void setBenutzerVerwaltung(BenutzerVerwaltungInt benutzerVerwaltung) {
		this.benutzerVerwaltung = benutzerVerwaltung;
	}
	
	public AuthorisierungBean() {
		//System.out.println("Creating AuthorisierungBean...");
	}
		
	@Override
	public Integer authorisiert(String benutzerName, String pw)  {
	
		Benutzer benutzer = benutzerVerwaltung.leseBenutzerByName(benutzerName);

		if (benutzer != null) {
			/* Passwort lesen */
		 	String wort = benutzerVerwaltung.lesePwByBenutzerId(benutzer.getId());

			if (pw.trim().matches(wort.trim())) {
				/* Passwort OK */
				return benutzer.getRolle();
			}
			else {
				/* Passwort falsch */
				return 0;
			}
		}		
		return 0;
	}


}
