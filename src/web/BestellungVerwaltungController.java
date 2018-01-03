package web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import datenVerwaltungInterface.BenutzerVerwaltungInt;
import datenVerwaltungInterface.BestellungVerwaltungInt;
import datenVerwaltungInterface.KundenVerwaltungInt;
import entity.Bestellung;
import entity.Kunde;

@ManagedBean
@SessionScoped
public class BestellungVerwaltungController implements Serializable {
	

	private static final long serialVersionUID = -8444231268804940575L;
	
	
	/* CDI - Dependency BestellungVerwaltung*/
	@ManagedProperty(value="#{bestellungVerwaltung}")
	BestellungVerwaltungInt bestellungVerwaltung;	

	public void setBestellungVerwaltung(BestellungVerwaltungInt bestellungVerwaltung) {
		this.bestellungVerwaltung = bestellungVerwaltung;
	}
	
	
	/* CDI - Dependency BenutzerVerwaltung*/
	@ManagedProperty(value="#{benutzerVerwaltung}")
	BenutzerVerwaltungInt benutzerVerwaltung;

	public void setBenutzerVerwaltung(BenutzerVerwaltungInt benutzerVerwaltung) {
		this.benutzerVerwaltung = benutzerVerwaltung;
	}
	
	
	/* CDI - Dependency KundenVerwaltung*/
	@ManagedProperty(value="#{kundenVerwaltung}")
	KundenVerwaltungInt kundenVerwaltung;

	public void setKundenVerwaltung(KundenVerwaltungInt kundenVerwaltung) {
		this.kundenVerwaltung = kundenVerwaltung;
	}

	

	/* CDI - Dependency BestellungViewBean*/
	@ManagedProperty(value="#{bestellungViewBean}")
	BestellungViewBean bestellungViewBean;

	/* CDI - Dependency BestellungListeViewBean*/
	@ManagedProperty(value="#{bestellungListeViewBean}")
	BestellungListeViewBean bestellungListeViewBean;

	/* CDI - Dependency KundeListeViewBean*/
	@ManagedProperty(value="#{kundeListeViewBean}")
	KundeListeViewBean kundeListeViewBean;


	/* CDI - Dependency BenutzerBean*/
	@ManagedProperty(value="#{benutzerBean}")
	BenutzerBean benutzerBean;


	private DialogModus 		dialogModus = DialogModus.NEUTRAL;
		
	enum DialogModus {NEUTRAL, BESTELLUNG_ERFASSEN, BESTELLUNG_BESTELLEN, BESTELLUNG_MUTIEREN, 
		              BESTAETIGTE_EINBLENDEN};

			
	
	/* Constructor */
	public BestellungVerwaltungController() {
	}

	
	/* Getter und Setter */

	public BestellungViewBean getBestellungViewBean() {
		return bestellungViewBean;
	}

	public void setBestellungViewBean(BestellungViewBean bestellungViewBean) {
		this.bestellungViewBean = bestellungViewBean;
	}	

	public BenutzerBean getBenutzerBean() {
		return benutzerBean;
	}

	public BestellungListeViewBean getBestellungListeViewBean() {		
		return bestellungListeViewBean;
	}

	public void setBestellungListeViewBean(
			BestellungListeViewBean bestellungListeViewBean) {

		this.bestellungListeViewBean = bestellungListeViewBean;
	}

	public KundeListeViewBean getKundeListeViewBean() {
		return kundeListeViewBean;
	}

	public void setKundeListeViewBean(KundeListeViewBean kundeListeViewBean) {
		this.kundeListeViewBean = kundeListeViewBean;
	}	
	
	
	public void setBenutzerBean(BenutzerBean benutzerBean) {
		this.benutzerBean = benutzerBean;
	}

	public DialogModus getDialogModus() {
		return dialogModus;
	}

	public void setDialogModus(DialogModus dialogModus) {
		this.dialogModus = dialogModus;
	}
	

	/* Kontroller-Methoden */

	
	/* -------------------------------------------------------------------------------------------------- /*
	 * Bestellungen erfassen 
	 */
	
	
	
	public String startBestellungErfassen() {
		
		this.bestellungViewBean.setBestellungView(new BestellungView());
		this.dialogModus = DialogModus.NEUTRAL;
		return ("welcome" + "?faces-redirect=true");
			
	}
	
	public String bestellung6Eier() {
		
		this.bestellungViewBean.setBestellungView(new BestellungView());
		this.bestellungViewBean.getBestellungView().getBestellung().setAnzahl(6);
		this.bestellungViewBean.getBestellungView().getBestellung().setLiefertermin(bestellungVerwaltung.bestimmeNaechsterLiefertermin());
		this.setDialogModus(DialogModus.BESTELLUNG_ERFASSEN);

		if (this.benutzerBean.getIstAdministrator()) {
			this.kundeListeViewBean.getKundenListView().clear();
			this.kundeListeViewBean.setKundenListView(erstelleKundenViewListe());
			return "bestellung-auswahl-kunde";
		} else {
			this.bestellungViewBean.getBestellungView().setKunde(kundenVerwaltung.leseKundeById(benutzerBean.getKundenId()));
			return "show-bestellung";	
		}
	}

	public String bestellung10Eier() {
		
		this.bestellungViewBean.setBestellungView(new BestellungView());
		this.bestellungViewBean.getBestellungView().getBestellung().setAnzahl(10);
		this.bestellungViewBean.getBestellungView().getBestellung().setLiefertermin(bestellungVerwaltung.bestimmeNaechsterLiefertermin());
		this.setDialogModus(DialogModus.BESTELLUNG_ERFASSEN);

		if (this.benutzerBean.getIstAdministrator()) {
			this.kundeListeViewBean.getKundenListView().clear();
			this.kundeListeViewBean.setKundenListView(erstelleKundenViewListe());
			return "bestellung-auswahl-kunde";
		} else {
			this.bestellungViewBean.getBestellungView().setKunde(kundenVerwaltung.leseKundeById(benutzerBean.getKundenId()));
			return "show-bestellung";	
		}
	}

	public String bestellung20Eier() {
		
		this.bestellungViewBean.setBestellungView(new BestellungView());
		this.bestellungViewBean.getBestellungView().getBestellung().setAnzahl(20);
		this.bestellungViewBean.getBestellungView().getBestellung().setLiefertermin(bestellungVerwaltung.bestimmeNaechsterLiefertermin());
		this.setDialogModus(DialogModus.BESTELLUNG_ERFASSEN);

		if (this.benutzerBean.getIstAdministrator()) {
			this.kundeListeViewBean.getKundenListView().clear();
			this.kundeListeViewBean.setKundenListView(erstelleKundenViewListe());
			return "bestellung-auswahl-kunde";
		} else {
			this.bestellungViewBean.getBestellungView().setKunde(kundenVerwaltung.leseKundeById(benutzerBean.getKundenId()));
			return "show-bestellung";	
		}
	}

	public String bestellungIndividuell() {
		
		this.bestellungViewBean.setBestellungView(new BestellungView());
		this.bestellungViewBean.getBestellungView().getBestellung().setLiefertermin(bestellungVerwaltung.bestimmeNaechsterLiefertermin());
		this.setDialogModus(DialogModus.BESTELLUNG_ERFASSEN);
		
		if (this.benutzerBean.getIstAdministrator()) {
			this.kundeListeViewBean.getKundenListView().clear();
			this.kundeListeViewBean.setKundenListView(erstelleKundenViewListe());
			return "bestellung-auswahl-kunde";
		} else {
			this.bestellungViewBean.getBestellungView().setKunde(kundenVerwaltung.leseKundeById(benutzerBean.getKundenId()));
			return "show-bestellung";	
		}
	}

	public String weiterNachKundenAuswahl() {
		
		Integer count = 0;
		for (KundeView kv : this.kundeListeViewBean.getKundenListView()) {
			if (kv.getChecked()) {
				this.bestellungViewBean.getBestellungView().setKunde(kv.getKunde());
				count ++;
			}
		}
		if (count==0) {
			FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage("Bitte einen Kunden auswaehlen."));
			return "";
		}
		if (count>1) {
			FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage("Bitte nur 1 Kunden auswaehlen"));	
			return "";
		}
		return "show-bestellung";
	}
	
	public String bestellenWeiter() {
		Integer anz = this.bestellungViewBean.getBestellungView().getBestellung().getAnzahl();
		this.bestellungViewBean.getBestellungView().getBestellung().setPreistotal(bestellungVerwaltung.berechnePreisBestellung(anz));
		this.dialogModus = DialogModus.BESTELLUNG_BESTELLEN;
		return "";
	}
	
	public String bestellenSpeichern() {
		
		Bestellung b = bestellungVerwaltung.kreiereBestellung(
				this.bestellungViewBean.getBestellungView().getKunde().getId(),
							benutzerVerwaltung.leseBenutzerByName(this.benutzerBean.getName()).getId(),
							this.bestellungViewBean.getBestellungView().getBestellung().getAnzahl(),
							this.bestellungViewBean.getBestellungView().getBestellung().getLiefertermin(),
							this.bestellungViewBean.getBestellungView().getBestellung().getKommentar());
		if (b==null) {
			FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage("Fehler beim Speichern der Bestellung. Keine Bestellung erfasst."));

			return "";
		}
		this.dialogModus = DialogModus.NEUTRAL;
		if (benutzerBean.getIstAdministrator()) {
			return "welcome";
		} else {
			return startBestellungenKundeOffen();			
		}
	}

	public String bestellenAbbruch() {
		
		if (dialogModus==DialogModus.BESTELLUNG_MUTIEREN) {
			this.dialogModus = DialogModus.NEUTRAL;
			return startBestellungenKundeOffen();
		}
			
		this.dialogModus = DialogModus.NEUTRAL;
		return "bestellungAbbruch";
	}
	

	/* -------------------------------------------------------------------------------------------------- /*
	 * Bestellung zu Kunde (zu eingeloggtem Benutzer)
	 */

	
	public String startBestellungenKundeOffen() {
		

		if (benutzerBean.getKundenId()==0) {
			FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage("Es ist keine KundenId gesetzt."));
			return "";	
		}
		List<Bestellung> bl = bestellungVerwaltung.leseBestellungenOffenByKundeId(benutzerBean.getKundenId());
		
		this.bestellungListeViewBean.initList();
		if (bl != null) {

			for (Bestellung bestellung : bl) {
				BestellungView bv = new BestellungView();
				bv.setBestellung(bestellung);
				this.bestellungListeViewBean.getBestellungListView().add(bv);
				if (bestellung.getStatus()==1) {
					this.bestellungListeViewBean.setStatusBestelltVorhanden(true);
					this.bestellungListeViewBean.setAnzahlStatusBestellt(this.bestellungListeViewBean.getAnzahlStatusBestellt()+1);
				}
			}
		}
		Kunde k = kundenVerwaltung.leseKundeById(benutzerBean.getKundenId());
		if (k==null) {
			FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage("Fehler beim Lesen des Kunden."));
			return "";				
		}
		this.bestellungViewBean.getBestellungView().setKunde(k);
		
		this.dialogModus = DialogModus.NEUTRAL;
		return ("bestellungen-kunde-offen" + "?faces-redirect=true");
			
	}

	public String bestellungGewaehltMutieren() {
		
		Integer count = 0;
		for (BestellungView bv : this.bestellungListeViewBean.getBestellungListView()) {
			if (bv.getChecked()) {
				this.bestellungViewBean.setBestellungView(bv);
				count++;
			}
		}
		if (count==0) {
			FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage("Zum Mutieren muss mindestens 1 Bestellung ausgewaehlt sein."));
			return "";	
		}
		if (count>1) {
			FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage("Zum Mutieren nur 1 Bestellung auswaehlen."));
			return "";	
		}
		// bestaetigte Bestellungen koennen nicht mehr mutiert werden.
		if (this.bestellungViewBean.getBestellungView().getBestellung().getStatus()>1) {
			FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage("Es koennen nur Bestellungen mit Status 'bestellt' mutiert werden."));
			return "";				
		}
		this.setDialogModus(DialogModus.BESTELLUNG_MUTIEREN);
		this.bestellungViewBean.getBestellungView().setKunde(kundenVerwaltung.leseKundeById(benutzerBean.getKundenId()));
		return "show-bestellung";	
		
	}
	
	
	public String bestellungMutierenSpeichern() {
		
		Bestellung b = this.bestellungViewBean.getBestellungView().getBestellung();
		b.setPreistotal(bestellungVerwaltung.berechnePreisBestellung(b.getAnzahl()));
		
		Integer result = bestellungVerwaltung.mutiereBestellung(b);
		if (result<0) {
			FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage("Fehler beim Speichern der Mutation. Mutation wurde nicht gespeichert."));
			return "";
		}
		
		return startBestellungenKundeOffen();
	}
	
	public String bestellungStorieren() {

		List<BestellungView> bvl = new ArrayList<BestellungView>();
		Integer count = 0;
		for (BestellungView bv : this.bestellungListeViewBean.getBestellungListView()) {
			if (bv.getChecked()) {
				if (bv.getBestellung().getStatus()>1) {
					FacesContext.getCurrentInstance().addMessage(null,
			                new FacesMessage("Es koennen nur Bestellungen mit Status 'bestellt' storniert werden. Es wurde keine Bestellung storniert."));
					return "";									
				}
				bvl.add(bv);
				count++;
			}
		}
		
		if (count==0) {
			FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage("Zum Stornieren mindestens 1 Bestellung auswaehlen."));
			return "";																
		}
		
		for (BestellungView bestellungView : bvl) {
			Integer result = bestellungVerwaltung.bestellungStorniertSetzen(bestellungView.getBestellung().getId());
			if (result<0) {
				FacesContext.getCurrentInstance().addMessage(null,
		                new FacesMessage("Fehler beim Stornieren."));
				return "";													
			}
		}
		
		// bestaetigte Bestellungen koennen nicht mehr storniert werden.
		if (this.bestellungViewBean.getBestellungView().getBestellung().getStatus()>1) {
			FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage("Es koennen nur Bestellungen mit Status 'bestellt' mutiert werden."));
			return "";				
		}		
		return startBestellungenKundeOffen();
	}
	
	/* -------------------------------------------------------------------------------------------------- /*
	 * Bestellungen bestaetigen 
	 */
	
	public String startBestellungBestaetigen() {

		//zu bestaetigende Bestellungen lesen
		List<Bestellung> bList = bestellungVerwaltung.leseBestellungenZumBestaetigen(this.dialogModus==DialogModus.BESTAETIGTE_EINBLENDEN);
		
		this.bestellungListeViewBean.getBestellungListView().clear();
		if (bList==null) {
			this.dialogModus = DialogModus.NEUTRAL;
			return ("bestellungen-bestaetigen" + "?faces-redirect=true");			
		}
		for (Bestellung b : bList) {
			BestellungView bv = new BestellungView();
			bv.setBestellung(b);
			//Kunde zu Bestellung lesen
			Kunde k = this.bestellungVerwaltung.leseKundeZuBestellung(b.getId());
			if (k==null) {
				FacesContext.getCurrentInstance().addMessage(null,
		                new FacesMessage("Fehler beim Lesen Bestellungen."));

				this.bestellungListeViewBean.getBestellungListView().clear();
				break;
			}
			bv.setKunde(k);
			this.bestellungListeViewBean.getBestellungListView().add(bv);
		}
		return ("bestellungen-bestaetigen" + "?faces-redirect=true");
	}

	public String bestaetigteBestellungenEinblenden() {
		this.dialogModus = DialogModus.BESTAETIGTE_EINBLENDEN;
		return startBestellungBestaetigen();
	}
	
	public String bestaetigteBestellungenAusblenden() {
		this.dialogModus = DialogModus.NEUTRAL;
		return startBestellungBestaetigen();
	}
	
	public String gotoBestaetigen() {
		
		List<BestellungView> bestellungListView = new ArrayList<BestellungView>();
		for (BestellungView bv : this.bestellungListeViewBean.getBestellungListView()) {
			if (bv.getChecked()) {
				bestellungListView.add(bv);
				
			}
		}
		
		if (bestellungListView.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage("Zum Bestaetigen muss mindestens 1 Bestellung ausgewaehlt sein."));

			return "";
		}
		
		Bestellung b = new Bestellung();
		b.setLieferterminBestaetigt(
					bestellungListView.get(0).getBestellung().getLiefertermin()
		);

		this.bestellungListeViewBean.setBestellungListView(bestellungListView);
		this.bestellungViewBean.getBestellungView().setBestellung(b);
		this.dialogModus = DialogModus.NEUTRAL;
		return "bestellungen-liefertermin-bestaetigen";
	}
	
	
	public String bestaetigenLiefertermin() {
		
		for (BestellungView bv : this.bestellungListeViewBean.getBestellungListView()) {
			
			Integer resultat = bestellungVerwaltung.lieferterminBestaetigen(
					bv.getBestellung().getId(),
					this.bestellungViewBean.getBestellungView().getBestellung().getLieferterminBestaetigt());
			
			if (resultat < 0) {
				FacesContext.getCurrentInstance().addMessage(null,
		                new FacesMessage("Fehler beim Speichern des Liefertermins. Termin nicht gespeichert."));
				return "";
			}
		}
		return startBestellungBestaetigen();
	}


	/* -------------------------------------------------------------------------------------------------- /*
	 * Bestellungen Auswahl liefern 
	 */
	
	public String startBestellungLiefern() {

		//zu bestaetigende Bestellungen lesen
		List<Bestellung> bList = bestellungVerwaltung.leseLieferungOffen();
		
		this.bestellungListeViewBean.getBestellungListView().clear();
		if (bList==null) {
			this.dialogModus = DialogModus.NEUTRAL;
			return ("bestellung-auswahl-liefern" + "?faces-redirect=true");			
		}
		
		for (Bestellung b : bList) {
			BestellungView bv = new BestellungView();
			bv.setBestellung(b);
			//Kunde zu Bestellung lesen
			Kunde k = this.bestellungVerwaltung.leseKundeZuBestellung(b.getId());
			if (k==null) {
				FacesContext.getCurrentInstance().addMessage(null,
		                new FacesMessage("Fehler beim Lesen Bestellungen."));

				this.bestellungListeViewBean.getBestellungListView().clear();
				break;
			}
			bv.setKunde(k);
			this.bestellungListeViewBean.getBestellungListView().add(bv);
		}
		
		this.dialogModus = DialogModus.NEUTRAL;
		return ("bestellung-auswahl-liefern" + "?faces-redirect=true");
	}	
	

	/* -------------------------------------------------------------------------------------------------- /*
	 * Lieferung bestaetigen
	 */

	public String gotoLieferungBestaetigen() {
		
		BestellungView bestellungView = new BestellungView();
		Integer count = 0;
		for (BestellungView bv : this.bestellungListeViewBean.getBestellungListView()) {
			if (bv.getChecked()) {
				bestellungView = bv;
				count++;
			}
		}
		
		if (count==0) {
			FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage("Es muss eine Bestellung ausgewaehlt werden."));

			return "";
		}
		
		if (count>1) {
			FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage("Es darf nur 1 Bestellung ausgewaehlt werden."));

			return "";
		}
		
		this.bestellungViewBean.setBestellungView(bestellungView);
		this.dialogModus = DialogModus.NEUTRAL;
		return "lieferung-abschliessen";
	}

	public String liefernAbbruch() {
		
		return "bestellung-auswahl-liefern";
	}
	
	public String lieferungAbschliessenBezahlt() {
		
		Integer resultat = bestellungVerwaltung.lieferungAbschliessen(this.bestellungViewBean.getBestellungView().getBestellung().getId(), true);
		if (resultat<0) {
			FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage("Fehler: Lieferung konnte nicht gespeichert werden."));
			return "";	
		}
		return startBestellungLiefern();
	}
	
	public String lieferungAbschliessenNichtBezahlt() {
		
		Integer resultat = bestellungVerwaltung.lieferungAbschliessen(this.bestellungViewBean.getBestellungView().getBestellung().getId(), false);
		if (resultat<0) {
			FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage("Fehler: Lieferung konnte nicht gespeichert werden."));
			return "";	
		}
		return startBestellungLiefern();
	}
	
	public String lieferungAbschliessenBelastungGuthaben() {
		
		Integer resultat = bestellungVerwaltung.lieferungAbschliessenBelastungGuthaben(this.bestellungViewBean.getBestellungView().getBestellung().getId());
		if (resultat<0) {
			FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage("Fehler: Lieferung konnte nicht gespeichert werden."));
			return "";	
		}
		return startBestellungLiefern();
	}
	
	public void doNothing() {
		System.out.println("key...");
	}
	

	/* -------------------------------------------------------------------------------------------------- /*
	 * offene Zahlungen erledigen
	 */
	public String startBezahlungOffen() {

		//zu bezahlende Bestellungen lesen
		List<Bestellung> bList =  bestellungVerwaltung.leseBezahlungOffen();

		this.bestellungListeViewBean.getBestellungListView().clear();
		if (bList==null) {
			this.dialogModus = DialogModus.NEUTRAL;
			return ("bestellungen-zahlung-offen" + "?faces-redirect=true");			
		}
		for (Bestellung b : bList) {
			BestellungView bv = new BestellungView();
			bv.setBestellung(b);
			//Kunde zu Bestellung lesen
			Kunde k = this.bestellungVerwaltung.leseKundeZuBestellung(b.getId());
			if (k==null) {
				FacesContext.getCurrentInstance().addMessage(null,
		                new FacesMessage("Fehler beim Lesen der Bestellungen."));

				this.bestellungListeViewBean.getBestellungListView().clear();
				break;
			}
			bv.setKunde(k);
			this.bestellungListeViewBean.getBestellungListView().add(bv);
		}
		
		this.dialogModus = DialogModus.NEUTRAL;
		return ("bestellungen-zahlung-offen" + "?faces-redirect=true");
	}	

	public String bezahlungErledigtSetzen() {
		
		for (BestellungView bv : this.bestellungListeViewBean.getBestellungListView()) {
			if (bv.getChecked()) {
				Integer result = bestellungVerwaltung.bestellungBezahltSetzen(bv.getBestellung().getId());
				if (result<0) {
					FacesContext.getCurrentInstance().addMessage(null,
			                new FacesMessage("Fehler beim Abschliessen der Bestellungen."));
				}
			}
		}
		return startBezahlungOffen();
	}
	
	
	/* Helper-Methoden */
	List<KundeView> erstelleKundenViewListe() {

		List<KundeView> kvListe = new ArrayList<KundeView>();
		List<Kunde> kl =  kundenVerwaltung.leseAlleKunden();
		
		if (kl!=null) {
			for (Kunde k : kl) {
				KundeView kv = new KundeView();
				kv.setKunde(k);
				kv.setChecked(false);
				kvListe.add(kv);
			}
		}
		
		return kvListe;
	}	
}	
	
