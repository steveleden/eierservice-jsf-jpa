package web;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import authorisierungBeans.AuthorisierungInt;
import datenVerwaltungInterface.BenutzerVerwaltungInt;

@ManagedBean
@RequestScoped
public class PasswortAendernController implements Serializable{

	private static final long serialVersionUID = -2010633952066987624L;

	
	/* CDI - Dependency Authorisierung */
	@ManagedProperty(value="#{authorisierung}")
	private	AuthorisierungInt auth;

	public void setAuth(AuthorisierungInt auth) {
		this.auth = auth;
	}
	
	
	/* CDI - Dependency BenutzerVerwaltung */
	@ManagedProperty(value="#{benutzerVerwaltung}")
	BenutzerVerwaltungInt benutzerVerwaltung;
	
	public void setBenutzerVerwaltung(BenutzerVerwaltungInt benutzerVerwaltung) {
		this.benutzerVerwaltung = benutzerVerwaltung;
	}

	
	/* CDI - Dependency BenutzerBean */
	@ManagedProperty(value="#{benutzerBean}")
	BenutzerBean benutzerBean;


	private String pw;
	private String pwNeu1;
	private String pwNeu2;

	
	/* Getter & Setters */
	/*------------------*/
	
	public BenutzerBean getBenutzerBean() {
		return benutzerBean;
	}

	public void setBenutzerBean(BenutzerBean benutzerBean) {
		this.benutzerBean = benutzerBean;
	}	
	
	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getPwNeu1() {
		return pwNeu1;
	}

	public void setPwNeu1(String newPw1) {
		this.pwNeu1 = newPw1;
	}

	public String getPwNeu2() {
		return pwNeu2;
	}

	public void setPwNeu2(String pwNeu2) {
		this.pwNeu2 = pwNeu2;
	}
	
	
	/* Controller-Methoden */
	/*--------------------*/
	
	/* Passwort aendern   */
	/*---------------------*/
	
	public String startPasswortAendern() {

		return "passwort-aendern";
	}
	
	public String passwortSpeichern() {

		// Pw pruefen 
		if (auth.authorisiert(this.benutzerBean.getName(), this.pw) == 0) {
			FacesContext.getCurrentInstance().addMessage("frm:pw_alt", new FacesMessage("Passwort falsch"));
			return "";
		}
		
		// Uebereinstimmung neues Pw pruefen
		if (!pwNeu1.trim().matches(pwNeu2.trim())) {			
			FacesContext.getCurrentInstance().addMessage("frm:pw_neu2",
	                new FacesMessage("Passwort stimmt mit obigem nicht ueberein."));
			return "";
		}
		
		// neues Pw speichern
		Integer resultat = benutzerVerwaltung.mutierePasswortZuBenutzer(benutzerBean.getName(), this.pwNeu1);
		if (resultat<0) {
			FacesContext.getCurrentInstance().addMessage("frm:pw_neu2",
	                new FacesMessage("Fehler beim Speichern des Passworts. Passwort nicht gespeichert."));
			return "";			
		}

		this.pw = "";
		return "benutzer-konto";
	}
	
	public String passwortAbbrechen() {
		return "benutzer-konto";
	}
	
}
