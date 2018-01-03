package web;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import authorisierungBeans.AuthorisierungInt;
import datenVerwaltungInterface.BenutzerVerwaltungInt;
import datenVerwaltungInterface.KundenVerwaltungInt;
import web.BenutzerBean.BenutzerRolle;
import entity.Kunde;

@ManagedBean
@SessionScoped
public class AuthController implements Serializable {

	private static final long serialVersionUID = 8800130583675966556L;

	/* Dependency Injections */
	@ManagedProperty(value="#{benutzerBean}")
	private BenutzerBean benutzer;
	
	
	@ManagedProperty(value="#{authorisierung}")
	private	AuthorisierungInt auth;
	
	
	@ManagedProperty(value="#{benutzerVerwaltung}")
	private BenutzerVerwaltungInt benutzerVerwaltung;
	
	
	@ManagedProperty(value="#{kundenVerwaltung}")
	private KundenVerwaltungInt kundenVerwaltung;
		
		
	private String password = null;
	
	
	public AuthController () {
		this.setPassword("testpwd");
	}
	
	
	/* Setters & Getters */
	public BenutzerBean getBenutzer() {
		return benutzer;
	}

	public void setBenutzer(BenutzerBean benutzer) {
		this.benutzer = benutzer;
	}


	public void setAuth(AuthorisierungInt auth) {
		this.auth = auth;
	}

	
	public void setBenutzerVerwaltung(BenutzerVerwaltungInt benutzerVerwaltung) {
		this.benutzerVerwaltung = benutzerVerwaltung;
	}


	public void setKundenVerwaltung(KundenVerwaltungInt kundenVerwaltung) {
		this.kundenVerwaltung = kundenVerwaltung;
	}
	
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	/* Controller */
	
	/* Check password method */
	public String login() {

		Integer rolle = auth.authorisiert(benutzer.getName(), this.password);
		
		if (rolle > 0) {
			benutzer.setAuthorisiert(true);
			if (rolle == 2) {
				benutzer.setRolle(BenutzerRolle.ADMIN);
				benutzer.setIstAdministrator(true);
			} else {
				benutzer.setRolle(BenutzerRolle.KUNDE);	
			}
			benutzerVerwaltung.updateLoginInfo(benutzer.getName());
			if (rolle == 1) {
				Kunde kunde = kundenVerwaltung.leseKundeByBenutzerName(benutzer.getName());
				if (kunde == null) {
					return "loginFailure";
				}
				benutzer.setKundenId(kunde.getId());
				benutzer.setGuthaben(kunde.getGuthaben());
			}
			return "loggedIn";
		}
		System.out.println("auth fehler");
		benutzer.setAuthorisiert(false);
		FacesContext.getCurrentInstance().addMessage("frm1:the_password", new FacesMessage("Passwort falsch"));
		FacesContext.getCurrentInstance().addMessage("frm2:the_password", new FacesMessage("Passwort falsch"));
		return "loginFailure";

	}
	
	/* Benutzer Logout */
	public String logout() throws IOException {
		benutzer.setAuthorisiert(false);
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();		
		return "loggedOut";
	}


	
}
