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
import web.BenutzerBean.BenutzerRolle;
import entity.Benutzer;

@ManagedBean
@SessionScoped
public class BenutzerVerwaltungController implements Serializable {


	private static final long serialVersionUID = -1918300628648028374L;


	/* CDI - Dependency BenutzerVerwaltung*/
	@ManagedProperty(value="#{benutzerVerwaltung}")
	BenutzerVerwaltungInt benutzerVerwaltung;

	public void setBenutzerVerwaltung(BenutzerVerwaltungInt benutzerVerwaltung) {
		this.benutzerVerwaltung = benutzerVerwaltung;
	}

	/* CDI - Dependency BenutzerViewBean*/
	@ManagedProperty(value="#{benutzerViewBean}")
	BenutzerViewBean benutzerViewBean;

	/* CDI - Dependency BenutzerListViewBean */
	@ManagedProperty(value="#{benutzerListeViewBean}")
	BenutzerListeViewBean benutzerListViewBean;



	private Benutzer 					benutzer = new Benutzer();
	private ArrayList<BenutzerRolle>	selectItems = new ArrayList<BenutzerRolle>();
	private DialogModus 				dialogmodus = DialogModus.BENUTZER_LISTE;

	
	/* BENUTZER_KUNDE_AUSWAHL: Aufruf durch KundenVerwaltungController -> Benutzer, welche noch keinen Kunden haben, auflisten zum Auswaehlen */
	enum DialogModus {BENUTZER_LISTE, BENUTZER_ERFASSEN, BENUTZER_MUTIEREN, BENUTZER_KUNDE_AUSWAHL};
	
	/* Constructor */
	public BenutzerVerwaltungController() {
		selectItems.add(BenutzerRolle.KUNDE);
		selectItems.add(BenutzerRolle.ADMIN);
	}

	/* Setter & Getter */
	
	public Benutzer getBenutzer() {
		return benutzer;
	}

	public void setBenutzer(Benutzer benutzer) {
		this.benutzer = benutzer;
	}
	
	
	public BenutzerViewBean getBenutzerViewBean() {
		return benutzerViewBean;
	}

	public void setBenutzerViewBean(BenutzerViewBean benutzerViewBean) {
		this.benutzerViewBean = benutzerViewBean;
	}
	

	public BenutzerListeViewBean getBenutzerListViewBean() {

		/*
		List<Benutzer> benutzerListe;
		List<BenutzerView> benutzerListView = new ArrayList<BenutzerView>();
		
		if (this.dialogmodus == DialogModus.BENUTZER_KUNDE_AUSWAHL) {
			benutzerListe = benutzerVerwaltung.leseBenutzerOhneKunde();
		} else {
			benutzerListe = benutzerVerwaltung.leseAlleBenutzer();			
		}
		
		if (benutzerListe == null) {
			this.benutzerListViewBean.getBenutzerListView().clear();
			return benutzerListViewBean;
		}
		
		for (Benutzer benutzer : benutzerListe) {
			
			BenutzerView benutzerItem = new BenutzerView();
			benutzerItem.setChecked(false);
			benutzerItem.setBenutzer(benutzer);
			String pw = benutzerVerwaltung.lesePwByBenutzerId(benutzer.getId());
			benutzerItem.setPw(pw);
			if (benutzer.getRolle() == 2)
				benutzerItem.setRolle(BenutzerRolle.ADMIN);
			else
				benutzerItem.setRolle(BenutzerRolle.KUNDE);
			
			benutzerListView.add(benutzerItem);
			
		}
		
		this.benutzerListViewBean.setBenutzerListView(benutzerListView);
		*/
		return benutzerListViewBean;

	}

	public void setBenutzerListViewBean(BenutzerListeViewBean benutzerListViewBean) {
		this.benutzerListViewBean = benutzerListViewBean;
	}	
	

	public ArrayList<BenutzerRolle> getSelectItems() {
		return selectItems;
	}

	public void setSelectItems(ArrayList<BenutzerRolle> selectItems) {
		this.selectItems = selectItems;
	}


	public DialogModus getDialogmodus() {
		return dialogmodus;
	}

	public void setDialogmodus(DialogModus dialogmodus) {
		this.dialogmodus = dialogmodus;
	}
	


	
	/* Kontroller-Methoden */

	public String startBenutzerVerwaltung () {
		
		fuelleBenutzerListeViewBeanAlleBenutzer();
		benutzerListViewBean.setDialogModus(web.BenutzerListeViewBean.DialogModus.BENUTZER_VERWALTUNG);
		return ("show-benutzerliste" + "?faces-redirect=true");
	}
	
	public String abbrechenBenutzerMutieren() {
		
		fuelleBenutzerListeViewBeanAlleBenutzer();
		this.dialogmodus = DialogModus.BENUTZER_LISTE;
		return "goto-benutzerliste";
	}
	
	public String benutzerSpeichern() {

		if (this.benutzerViewBean.getBenutzerView().getRolle() == BenutzerRolle.ADMIN)
			this.benutzerViewBean.getBenutzerView().getBenutzer().setRolle(2);
		else
			this.benutzerViewBean.getBenutzerView().getBenutzer().setRolle(1);
		
		if (this.dialogmodus == DialogModus.BENUTZER_ERFASSEN) {
			/* Erfassungsmodus */
			Integer resultat =  benutzerVerwaltung.benutzerAnlegen(this.benutzerViewBean.getBenutzerView().getBenutzer());
						
			if (resultat == 0) {
				resultat = benutzerVerwaltung.mutierePasswortZuBenutzer(this.benutzerViewBean.getBenutzerView().getBenutzer().getName(),
																		this.benutzerViewBean.getBenutzerView().getPw());
				if (resultat == 0) {
					/* View leeren */
					this.benutzerViewBean.setBenutzerView(new BenutzerView());
					fuelleBenutzerListeViewBeanAlleBenutzer();
					this.dialogmodus = DialogModus.BENUTZER_LISTE;
					return "goto-benutzerliste";
				}
			}
			return "show-benutzer";

		} else {
			/* Mutiermodus */
			Integer resultat = benutzerVerwaltung.mutiereBenutzer(this.benutzerViewBean.getBenutzerView().getBenutzer());
			if (resultat == 0) {
				resultat = benutzerVerwaltung.mutierePasswortZuBenutzer(this.benutzerViewBean.getBenutzerView().getBenutzer().getName(),
																		this.benutzerViewBean.getBenutzerView().getPw());
				if (resultat == 0) {
					this.benutzerViewBean.setBenutzerView(new BenutzerView());
					fuelleBenutzerListeViewBeanAlleBenutzer();
					this.dialogmodus = DialogModus.BENUTZER_LISTE;
					return "show-benutzerliste";
				}
			}		
			return "show-benutzer";
		}
			
	}

	public String benutzerLoeschen () {
		
		Integer count = 0;
		
		for (BenutzerView benutzerView : this.benutzerListViewBean.getBenutzerListView()) {
			if (benutzerView.checked) {
				benutzerVerwaltung.loescheBenutzer(benutzerView.benutzer);
				count ++;
				
			}
		}
		
		if (count == 0) {
			FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage("Zu loeschende(n) Benutzer auswaehlen."));
		}
		fuelleBenutzerListeViewBeanAlleBenutzer();
		return "show-benutzerliste";
	}
	
	public String gotoBenutzerErfassen() {
		this.dialogmodus = DialogModus.BENUTZER_ERFASSEN;
		/* leeren Benutzer anzeigen */
		this.benutzerViewBean.setBenutzerView(new BenutzerView());
		return "show-benutzer";
	}

	public String gotoBenutzerMutieren() {	
		
		Integer count = 0;
		
		this.dialogmodus = DialogModus.BENUTZER_MUTIEREN;
		for (BenutzerView benutzerViewItem : this.benutzerListViewBean.getBenutzerListView()) {
			if (benutzerViewItem.checked) {
				count ++;
			}
		}	
		if (count > 1) {
			FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage("Zum Mutieren nur 1 Benutzer auswaehlen."));
			return "show-benutzerliste";			
		}
		
		
		for (BenutzerView benutzerViewItem : this.benutzerListViewBean.getBenutzerListView()) {
			if (benutzerViewItem.checked) {
				this.benutzerViewBean.setBenutzerView(benutzerViewItem);
				return "show-benutzer";
			}
		}		
		FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Zu mutierenden Benutzer auswaehlen."));
		return "show-benutzerliste";
	}

	
	/* Kontroller-Methoden fuer Benutzer-Auswahl, um zum Kunden zuzuordnen */
	public String benutzerFuerKundeAuswaehlen () {
		
		this.setDialogmodus(DialogModus.BENUTZER_LISTE);
		return "back-to-kunde";
	}	
	
	public String abbrechenBenutzerFuerKundeAuswaehlen () {
		
		this.setDialogmodus(DialogModus.BENUTZER_LISTE);
		
		/*
		KundenView kv = kundenVerwaltungController.getKundeView();
		List<BenutzerView> 	benutzerLV = new ArrayList<BenutzerView>();
		// Auswgewaehlte Benutzer in benutzerListView der KundenView im kundenVerwaltungsController uebergeben
		for (BenutzerView benutzerView : benutzerListView) {
			if (benutzerView.getChecked()) {
				benutzerLV.add(benutzerView);
			}
				
		}
		
		kv.setBenutzerListe(benutzerLV);
		*/
		
		return "back-to-kunde";
	}
		
	
	/* Helper-Methoden */
	private void fuelleBenutzerListeViewBeanAlleBenutzer() {

		/* Benutzerliste lesen und in benutzerListeViewBean speichern */
		List<Benutzer> benutzerListe;
		List<BenutzerView> benutzerListView = new ArrayList<BenutzerView>();

		benutzerListe = benutzerVerwaltung.leseAlleBenutzer();			

		if (benutzerListe == null) {
			this.benutzerListViewBean.getBenutzerListView().clear();
		}
		
		for (Benutzer benutzer : benutzerListe) {
			
			BenutzerView benutzerItem = new BenutzerView();
			benutzerItem.setChecked(false);
			benutzerItem.setBenutzer(benutzer);
			String pw = benutzerVerwaltung.lesePwByBenutzerId(benutzer.getId());
			benutzerItem.setPw(pw);
			if (benutzer.getRolle() == 2)
				benutzerItem.setRolle(BenutzerRolle.ADMIN);
			else
				benutzerItem.setRolle(BenutzerRolle.KUNDE);
			
			benutzerListView.add(benutzerItem);
			
		}
		
		this.benutzerListViewBean.setBenutzerListView(benutzerListView);
		
	}
	
}
