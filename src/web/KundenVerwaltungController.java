package web;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import datenVerwaltungInterface.BenutzerVerwaltungInt;
import datenVerwaltungInterface.KundenVerwaltungInt;
import entity.Benutzer;
import entity.Kunde;

@ManagedBean
@SessionScoped
public class KundenVerwaltungController implements Serializable {

	private static final long serialVersionUID = 8497164851405405578L;

	private DialogModus	dialogModus = DialogModus.KUNDE_LISTE;
	
	enum DialogModus {KUNDE_LISTE,KUNDE_ERFASSEN, KUNDE_MUTIEREN, KUNDE_ANZEIGEN,
		KUNDE_EINZAHLUNG};

	

	/* CDI - Dependency KundeViewBean */
	@ManagedProperty(value="#{kundeViewBean}")
	KundeViewBean kundeViewBean;

	/* CDI - Dependency BenutzerViewBean */
	@ManagedProperty(value="#{benutzerViewBean}")
	BenutzerViewBean benutzerViewBean;

	/* CDI - Dependency BenutzerBean */
	@ManagedProperty(value="#{benutzerBean}")
	BenutzerBean benutzerBean;


	/* CDI - Dependency KundeListeViewBean */
	@ManagedProperty(value="#{kundeListeViewBean}")
	KundeListeViewBean kundeListeViewBean;

	/* CDI - Dependency BenutzerListeViewBean */
	@ManagedProperty(value="#{benutzerListeViewBean}")
	BenutzerListeViewBean benutzerListeViewBean;

	/* CDI - Dependency KundenVerwaltung */
	@ManagedProperty(value="#{kundenVerwaltung}")
	KundenVerwaltungInt kundenVerwaltung;
	
	
	/* CDI - Dependency BenutzerVerwaltung */
	@ManagedProperty(value="#{benutzerVerwaltung}")
	BenutzerVerwaltungInt benutzerVerwaltung;
	
	
	/* Constructor */
	public KundenVerwaltungController() {
	}

	
	/* Getter und Setter */
	
	public void setKundenVerwaltung(KundenVerwaltungInt kundenVerwaltung) {
		this.kundenVerwaltung = kundenVerwaltung;
	}
		
	public void setBenutzerVerwaltung(BenutzerVerwaltungInt benutzerVerwaltung) {
		this.benutzerVerwaltung = benutzerVerwaltung;
	}
		

	public KundeViewBean getKundeViewBean() {

		return kundeViewBean;
	}

	public void setKundeViewBean(KundeViewBean kundeViewBean) {
		this.kundeViewBean = kundeViewBean;
	}

	public BenutzerViewBean getBenutzerViewBean() {
		return benutzerViewBean;
	}


	public void setBenutzerViewBean(BenutzerViewBean benutzerViewBean) {
		this.benutzerViewBean = benutzerViewBean;
	}

	public BenutzerBean getBenutzerBean() {
		return benutzerBean;
	}

	public void setBenutzerBean(BenutzerBean benutzerBean) {
		this.benutzerBean = benutzerBean;
	}	

	public KundeListeViewBean getKundeListeViewBean() {
		return kundeListeViewBean;			
	}

	public void setKundeListeViewBean(KundeListeViewBean kundeListeViewBean) {
		this.kundeListeViewBean = kundeListeViewBean;
	}	
	

	public BenutzerListeViewBean getBenutzerListeViewBean() {
		return benutzerListeViewBean;
	}


	public void setBenutzerListeViewBean(BenutzerListeViewBean benutzerListeViewBean) {
		this.benutzerListeViewBean = benutzerListeViewBean;
	}	
	
	public DialogModus getDialogModus() {
		return dialogModus;
	}

	public void setDialogModus(DialogModus dialogModus) {
		this.dialogModus = dialogModus;
	}


	/* Kontroller-Methoden */

	public String startKundenVerwaltung() {
		
		fuelleKundeListeViewBeanAlleKunden();	
		this.benutzerListeViewBean.getBenutzerListView().clear();		
		return ("show-kundenliste" + "?faces-redirect=true");
	}
	
	public String kundeMutierenAbbrechen() {
		
		if (this.dialogModus == DialogModus.KUNDE_ERFASSEN &&
			this.kundeViewBean.getKundeView().getKunde().getId() != 0) {
			
			//Kunde loeschen, falls schon kreiert
			this.kundenVerwaltung.loescheKunde(kundeViewBean.getKundeView().getKunde());
		}
		
		/* Benutzerliste zuruecksetzen auf saveKundeView */
		for (BenutzerView bv : this.kundeViewBean.getKundeView().getBenutzerListe()) {
			this.kundenVerwaltung.verknuepfeBenutzerMitKunde(bv.getBenutzer().getName(), null);
		}
		
		for (Benutzer b : saveKundeBenutzerList) {
			this.kundenVerwaltung.verknuepfeBenutzerMitKunde(b.getName(), this.kundeViewBean.getKundeView().getKunde());
		}
		
		fuelleKundeListeViewBeanAlleKunden();
		this.dialogModus = DialogModus.KUNDE_LISTE;
		return "back-to-kundenliste";
	}
	
	public String kundeSpeichern() {
		
		if (dialogModus == DialogModus.KUNDE_ERFASSEN) {
			/* Erfassungsmodus */
			if (this.kundeViewBean.getKundeView().getKunde().getId() == 0) {
				Kunde k =  kundenVerwaltung.kreiereKunde(this.kundeViewBean.getKundeView().getKunde());
				if (k == null) {
					return "show-kunde";
				}

			} else {
				Integer resultat = kundenVerwaltung.mutiereKunde(this.kundeViewBean.getKundeView().getKunde());
				if (resultat != 0) {
					return "show-kunde";
				}							
			}
			/* View leeren */
			this.kundeViewBean.setKundeView(new KundeView());
			fuelleKundeListeViewBeanAlleKunden();
			this.dialogModus = DialogModus.KUNDE_LISTE;
			return "back-to-kundenliste";

		} else {
			/* Mutiermodus */
			Integer resultat = kundenVerwaltung.mutiereKunde(this.kundeViewBean.getKundeView().getKunde());
			if (resultat == 0) {
				
				this.kundeViewBean.setKundeView(new KundeView());
				fuelleKundeListeViewBeanAlleKunden();
				this.dialogModus = DialogModus.KUNDE_LISTE;
				return "back-to-kundenliste";

			}		
			return "show-kunde";
		}
			
	}

	public String einzahlungErfassenAbbrechen() {		
		this.dialogModus = DialogModus.KUNDE_LISTE;
		fuelleKundeListeViewBeanAlleKunden();
		return "back-to-kundenliste";
	}
	
	
	public String einzahlungKundeSpeichern() {	
		
		Kunde k = this.kundeViewBean.getKundeView().getKunde();
		BigDecimal einzahlungsBetrag = this.kundeViewBean.getKundeView().getEinzahlung();
		k.setGuthaben(k.getGuthaben().add(einzahlungsBetrag));
		
		Integer resultat = kundenVerwaltung.mutiereKunde(k);
		if (resultat != 0) {
			return "show-einzahlung";
		}							
		/* View leeren */
		this.kundeViewBean.setKundeView(new KundeView());
		fuelleKundeListeViewBeanAlleKunden();
		this.dialogModus = DialogModus.KUNDE_LISTE;
		return "back-to-kundenliste";
	}
	

	public String kundenLoeschen () {
		
		Integer count = 0;
		
		for (KundeView kundeView : kundeListeViewBean.getKundenListView()) {
			if (kundeView.checked) {
				kundenVerwaltung.loescheKunde(kundeView.kunde);
				count ++;
			}
		}
		
		if (count == 0) {
			FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage("Zu loeschende(n) Kunden auswaehlen."));
		}
		fuelleKundeListeViewBeanAlleKunden();
		return "show-kundenliste";
	}
	
	public String gotoKundeErfassen() {
		dialogModus = DialogModus.KUNDE_ERFASSEN; /* Erfassungsmodus */
		this.saveKundeBenutzerList.clear();
		/* leeren Benutzer anzeigen */
		this.kundeViewBean.setKundeView(new KundeView());
		return "show-kunde";
	}

	private List<Benutzer> saveKundeBenutzerList = new ArrayList<Benutzer>();

	public String gotoKundeMutieren() {	
		
		Integer count = 0;
		dialogModus = DialogModus.KUNDE_MUTIEREN; /* Mutiermodus */
		for (KundeView kundenViewItem : kundeListeViewBean.getKundenListView()) {
			if (kundenViewItem.checked) {
				count ++;
			}
		}	
		if (count > 1) {
			FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage("Zum Mutieren nur 1 Kunden auswaehlen."));
			this.dialogModus = DialogModus.KUNDE_LISTE;
			return "back-to-kundenliste";			
		}
		
		
		for (KundeView kundenViewItem : kundeListeViewBean.getKundenListView()) {
			if (kundenViewItem.checked) {
				this.kundeViewBean.setKundeView(kundenViewItem);
				saveKundeBenutzerList.clear();
				for (BenutzerView kv : this.kundeViewBean.getKundeView().getBenutzerListe()) {
					saveKundeBenutzerList.add(kv.getBenutzer());
				}
								return "show-kunde";
			}
		}		
		FacesContext.getCurrentInstance()
				    .addMessage(null, new FacesMessage("Zu mutierenden Kunden auswaehlen."));
		this.dialogModus = DialogModus.KUNDE_LISTE;
		return "back-to-kundenliste";
	}

	public String gotoEinzahlungErfassen() {
		
		Integer count = 0;
		dialogModus = DialogModus.KUNDE_EINZAHLUNG; /* Einzahlungsmodus */
		for (KundeView kundenViewItem : kundeListeViewBean.getKundenListView()) {
			if (kundenViewItem.checked) {
				count ++;
			}
		}	
		if (count > 1) {
			FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage("Zum Mutieren nur 1 Kunden auswaehlen."));
			this.dialogModus = DialogModus.KUNDE_LISTE;
			return "back-to-kundenliste";			
		}
		
		for (KundeView kundenViewItem : kundeListeViewBean.getKundenListView()) {
			if (kundenViewItem.checked) {
				this.kundeViewBean.setKundeView(kundenViewItem);
				saveKundeBenutzerList.clear();
				for (BenutzerView kv : this.kundeViewBean.getKundeView().getBenutzerListe()) {
					saveKundeBenutzerList.add(kv.getBenutzer());
				}
				return "show-einzahlung";
			}
		}		

		FacesContext.getCurrentInstance()
	    .addMessage(null, new FacesMessage("Zu mutierenden Kunden auswaehlen."));
		this.dialogModus = DialogModus.KUNDE_LISTE;
		return "back-to-kundenliste";
	}
	
	public String gotoBenutzerAuswaehlen() {
		
		List<Benutzer> benutzerListe = benutzerVerwaltung.leseBenutzerOhneKunde();		
				
		List<BenutzerView> benutzerListView = new ArrayList<BenutzerView>();
		for (Benutzer benutzer : benutzerListe) {
			
			BenutzerView benutzerItem = new BenutzerView();
			benutzerItem.setChecked(false);
			benutzerItem.setBenutzer(benutzer);
			benutzerListView.add(benutzerItem);
			
		}
		
		benutzerListeViewBean.setBenutzerListView(benutzerListView);
		
		if (this.benutzerListeViewBean.getBenutzerListView().isEmpty()) {
			FacesContext.getCurrentInstance()
		    .addMessage(null, new FacesMessage("Keine Benutzer vorhanden. Bitte zuerst Benutzer fuer Kunden erfassen."));
			return " ";
		}		
		
		
		// Kunde speichern, falls noch nicht gespeichert
		
		if (this.kundeViewBean.getKundeView().getKunde().getId() == 0) {

			//Kunde ist noch nicht gespeichert
			Kunde k = this.kundenVerwaltung.kreiereKunde(this.kundeViewBean.getKundeView().getKunde());
			if (k == null) {
				return "show-kunde";
			}

			this.kundeViewBean.getKundeView().setKunde(k);
			this.kundeViewBean.getKundeView().setBenutzerListe(new ArrayList<BenutzerView>());

		}
		
		benutzerListeViewBean.setDialogModus(web.BenutzerListeViewBean.DialogModus.BENUTZERWAHL_FUER_KUNDE);
		return "show-benutzerliste";
	}

	// Rueckkehr von Auswahl Benutzer mit OK
	public String benutzerAusgewaehlt() {
		
		for (BenutzerView benutzerView : this.benutzerListeViewBean.getBenutzerListView()) {
			if (benutzerView.getChecked()) {
				this.kundeViewBean.getKundeView().getBenutzerListe().add(benutzerView);
				this.kundenVerwaltung.verknuepfeBenutzerMitKunde(benutzerView.getBenutzer().getName(), this.kundeViewBean.getKundeView().getKunde());
			}
		}
		

		return "show-kunde";
	}

	public String benutzerEntfernen() {
		
		// View fuer Benutzerliste leeren
		this.benutzerListeViewBean.getBenutzerListView().clear();
		
		// Benutzer abhaengen
		for (BenutzerView bv : this.kundeViewBean.getKundeView().getBenutzerListe()) {
			Integer result = this.kundenVerwaltung.verknuepfeBenutzerMitKunde(bv.getBenutzer().getName(), null);
			if (result < 0)
				return "back-to-kundenliste";
		}

		this.kundeViewBean.getKundeView().getBenutzerListe().clear();
		return "show-kunde";
	}
	
	
	/* Benutzerkonto mutieren */
	/*------------------------*/
	
	public String startBenutzerKontoMutieren() {
		
		Kunde k = kundenVerwaltung.leseKundeByBenutzerName(benutzerBean.getName());
		
		if (k==null) {
			FacesContext.getCurrentInstance()
		    .addMessage(null, new FacesMessage("Fehler beim Lesen des Kunden."));
			return " ";
		}
		this.kundeViewBean.getKundeView().setKunde(k);
		
		Benutzer b = benutzerVerwaltung.leseBenutzerByName(benutzerBean.getName());
		
		if (b==null) {
			FacesContext.getCurrentInstance()
		    .addMessage(null, new FacesMessage("Fehler beim Lesen des Benutzers."));
			return " ";
		}
		this.benutzerViewBean.getBenutzerView().setBenutzer(b);
		
		this.dialogModus = DialogModus.KUNDE_ANZEIGEN;
		return ("benutzer-konto" + "?faces-redirect=true");

	}
	
	public String gotoKundenDatenMutieren() {
		this.dialogModus = DialogModus.KUNDE_MUTIEREN;
		return "";
	}
	
	public String kontoDatenSpeichern() {
		
		Integer result = kundenVerwaltung.mutiereKunde(this.kundeViewBean.getKundeView().getKunde());
		if (result<0) {
			FacesContext.getCurrentInstance()
		    .addMessage(null, new FacesMessage("Fehler: Geaenderte Kundendaten konnten nicht gespeichert werden."));
			return " ";
		}
		
		result = benutzerVerwaltung.mutiereBenutzer(this.benutzerViewBean.getBenutzerView().getBenutzer());
		if (result<0) {
			FacesContext.getCurrentInstance()
		    .addMessage(null, new FacesMessage("Fehler: Geaenderte Benutzerdaten konnten nicht gespeichert werden."));
			return " ";
		}
		
		return startBenutzerKontoMutieren();
	}
	
	public String kontoDatenAbbrechen() {
		this.dialogModus = DialogModus.KUNDE_ANZEIGEN;
		return " ";
	}

	
	/* Helper-Methoden */
	
	private void fuelleKundeListeViewBeanAlleKunden () {

		List<KundeView> kundenListView = new ArrayList<KundeView>(); 
		
		List<Kunde> kundenListe = kundenVerwaltung.leseAlleKunden();
		
		for (Kunde kunde : kundenListe) {			
			
			/* KundenView erzeugen */
			KundeView kundeView = new KundeView(); 
			kundeView.setKunde(kunde);
			//System.out.print("Kunde = " + kunde.getNachname());
			
			List<Benutzer> benutzerListe = kundenVerwaltung.leseBenutzerZuKundeById(kunde.getId());	
			if (benutzerListe == null)
				benutzerListe = new ArrayList<Benutzer>();		
			List<BenutzerView> benutzerListView = new ArrayList<BenutzerView>();
			for (Benutzer benutzer : benutzerListe) {
				BenutzerView bView = new BenutzerView();
				bView.setBenutzer(benutzer);				
				benutzerListView.add(bView);
			}
			kundeView.setBenutzerListe(benutzerListView); 
				
			/* kundenView zur KundenListView hinzufuegen */
			kundenListView.add(kundeView);
		}
		
		this.kundeListeViewBean.setKundenListView(kundenListView);		
		
	}

}	
	
