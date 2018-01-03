package datenVerwaltungInterface;

import java.util.List;

import entity.Benutzer;
import entity.Kunde;

public interface KundenVerwaltungInt {

	Kunde       leseKundeById(Integer kundenId);
	Kunde       leseKundeByBenutzerName(String benutzerName);
	List<Kunde> leseAlleKunden();
	List<Benutzer>	leseBenutzerZuKundeById(Integer kundeId);
	
	Kunde       kreiereKunde(Kunde kunde);
	Integer     mutiereKunde(Kunde kunde);
	Integer     loescheKunde(Kunde kunde);
	Integer     verknuepfeBenutzerMitKunde(String benutzerName, Kunde kunde);
	
}
