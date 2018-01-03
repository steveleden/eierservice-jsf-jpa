package datenVerwaltungInterface;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import entity.Bestellung;
import entity.Kunde;

public interface BestellungVerwaltungInt {

	BigDecimal 			getStueckPreis();
	void 				setStueckPreis(BigDecimal stueckPreis);
	BigDecimal 			berechnePreisBestellung(Integer anzahl);
	//Bestimme naechsten Liefertermin (Dienstag):
	Date 				bestimmeNaechsterLiefertermin();
	
	// Gibt null zurueck, falls fehlerhaft
	Bestellung 			kreiereBestellung(Integer kundeId, Integer benutzerId, Integer anzahl, Date lieferTermin, String kommentar);

	Integer				mutiereBestellung(Bestellung bestellung);
	
	Integer				storniereBestellung(Bestellung bestellung);
	
	// Lesen von Bestellungen, die noch bestaetigt werden muessen
	List<Bestellung>	leseBestellungenZumBestaetigen(Boolean inklBestaetigte);
	
	// Lesen Bestellungen eines Kunden, welche noch nicht geliefert oder noch nicht bezahlt sind */
	List<Bestellung> 	leseBestellungenOffenByKundeId(Integer kundeId);

	// Lesen aller Bestellungen eines Kunden (Anzahl: letzte 'limit', ueberspring die erste 'skip')
	List<Bestellung>	leseBestellungenAlleByKundeId(Integer kundeId, Integer limit, Integer skip);	
	
	// Lesen aller Bestellungen, welche noch zu liefern sind, d.h. status=2 (bestaetigt)
	List<Bestellung>	leseLieferungOffen();
	
	// Lesen aller Bestellungen, welche geliefert sind, aber noch nicht bezahlt
	List<Bestellung>	leseBezahlungOffen();
		
	// Kunde zu einer gegebenen Bestellung lesen
	Kunde				leseKundeZuBestellung(Integer bestellungId);

	// Bestellung.status = 2 (bestaetigt) setzen, bestaetigten Liefertermin speichern 
	Integer lieferterminBestaetigen(Integer bestellungId, Date lieferTerminBestaetigt);
	
	// Setzt Status=3 (=geliefert), setzt geliefert_am, und falls bezahlt bezahlt_am
	Integer				lieferungAbschliessen(Integer bestellungId, Boolean bezahlt);

	// Setzt Status=3 (=geliefert), setzt geliefert_am, und bezahlt_am
	// Das Kundenguthaben wird belastet
	Integer lieferungAbschliessenBelastungGuthaben(Integer bestellungId);	
	
	// Setzt Status=4 (abgeschlossen) und setzt bezahlt_am
	Integer bestellungBezahltSetzen(Integer bestellungId);
	
	// Setzt Status=5 (storniert) und storniert_am
	Integer bestellungStorniertSetzen(Integer bestellungId);
	
}
