package datenVerwaltungBeans;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import datenVerwaltungInterface.BenutzerVerwaltungInt;
import datenVerwaltungBeans.EntityManagerProviderBean;
import entity.Benutzer;
import entity.Passwort;

@ManagedBean(name="benutzerVerwaltung", eager=true)
@SessionScoped
public class BenutzerVerwaltungBean implements BenutzerVerwaltungInt, Serializable{

	private static final long serialVersionUID = -4328885610598354178L;	
	
	/* Dependency Injection */
	@ManagedProperty(value="#{entityManagerProviderBean}")
	private EntityManagerProviderBean emProvider;	
	

	public BenutzerVerwaltungBean() {
		//System.out.println("Creating BenutzerVerwaltungBean...");
	}
		
	/* Setter and Getter Methods */
	public void setEmProvider(EntityManagerProviderBean emProvider) {
		this.emProvider = emProvider;
	}

	
	@Override
	public List<Benutzer> leseAlleBenutzer() {
		try {
			EntityManager em = emProvider.getEntityManager();

			List<Benutzer> list = (List<Benutzer>) em.createQuery("SELECT b from Benutzer b order by b.name", Benutzer.class).getResultList();
			
			em.close();
			return list;
		} catch (Exception e) {
			System.out.println("BenutzerVerwaltungEjb, leseAlleBenutzer: Fehler beim Lesen der Benutzer");
			return null;
		}
	}
	
	
	@Override
	public Benutzer leseBenutzerByName(String benutzerName) {
		
		try {
			EntityManager em = emProvider.getEntityManager();
			
			Benutzer benutzer = (Benutzer) em.createQuery("SELECT b from Benutzer b where b.name =?1")
			        						 .setParameter(1, benutzerName.trim())
			        						 .getSingleResult();
			em.close();
			return benutzer;
		} catch (Exception e) {			
			/* Falls Query kein Resultat findet ... */
			System.out.println("BenutzerVerwaltungEjb, leseBenutzerByName: Fehler beim Lesen der Benutzer");
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public List<Benutzer> leseBenutzerOhneKunde() {
		
		try {
			EntityManager em = emProvider.getEntityManager();

			List<Benutzer> benutzer = em.createQuery("SELECT b from Benutzer b where b.kunde is null and b.rolle=1", Benutzer.class).getResultList();			
			
			em.close();
			return benutzer;
		} catch (Exception e) {
			System.out.println("BenutzerVerwaltungEjb.leseBenutzerOhneKunde(): Benutzer konnten nicht gelesen werden.");
			System.out.println(e.getMessage());
			return null;
		}
		

	}	
	
	

	@Override
	public Integer benutzerAnlegen(Benutzer benutzer) {
		
		try {
			EntityManager em = emProvider.getEntityManager();
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			em.persist(benutzer);
			transaction.commit();
			em.close();
			return 0;
		} catch (Exception e) {
			System.out.println("Fehler beim Speichern Enitaet Benutzer: " + benutzer.getName());
			return -1;
		}
		
	}

	@Override
	public Integer loescheBenutzer(Benutzer benutzer) {
		try {
			EntityManager em = emProvider.getEntityManager();
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			Benutzer zuLoeschen = em.getReference(Benutzer.class, benutzer.getId());
			em.remove(zuLoeschen);
			transaction.commit();
			em.close();
			return 0;
		} catch (Exception e) {
			System.out.println("Fehler beim Loeschen Enitaet Benutzer:" + benutzer.getName());
			return -1;
		}
	}

	@Override
	public Integer mutiereBenutzer(Benutzer benutzer) {
		try {
			if (benutzer != null) {
				EntityManager em = emProvider.getEntityManager();
				EntityTransaction transaction = em.getTransaction();
				transaction.begin();
				em.merge(benutzer);
				transaction.commit();
				em.close();
				return 0;
			} else {
				return -1;
			}
		} catch (Exception e) {
			System.out.println("Fehler beim Update Enitaet Benutzer:" + benutzer.getName());
			return -1;
		}
	}

	
	@Override
	public String lesePwByBenutzerId(Integer id) {
		try {
			EntityManager em = emProvider.getEntityManager();
			Passwort pw = em.find(Passwort.class, id);
			em.close();
			if (pw != null)
				return pw.getWort();
			else 
				return " ";
		} catch (Exception e) {
			e.printStackTrace();
			return " ";
		}
	}
	
	@Override
	public Integer mutierePasswortZuBenutzer(String benutzerName, String pw) {

		try {
			if (pw == null || pw.isEmpty())
				return -1;
			
			Benutzer benutzer = this.leseBenutzerByName(benutzerName);						
			
			if (benutzer == null) {
				return -1;
			}
			
			EntityManager em = emProvider.getEntityManager();
			Passwort passWort = (Passwort) em.find(Passwort.class, benutzer.getId());		
			
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			if (passWort != null) {
				/* update passwort */
				passWort.setWort(pw);
				em.merge(passWort);
				transaction.commit();
				em.close();
				return 0;
			} else {
				/* Passwort an Benutzer haengen */
				passWort = new Passwort();
				passWort.setFkBenutzerid(benutzer.getId());
				//passWort.setBenutzer(benutzer);
				passWort.setWort(pw);
				em.persist(passWort);
				transaction.commit();
				em.close();
				return 0;
			}
								
		}
		
		catch (Exception e) {
		System.out.println("BenutzerVerwaltungEJB: Passwort mutieren fehlerhaft.");
		e.printStackTrace();
		return -1;
		}		
	}

	
	@Override
	public void updateLoginInfo(String benutzerName) {
		
		Benutzer benutzer = this.leseBenutzerByName(benutzerName);
		
		if (benutzer != null) {
			
			try {
				benutzer.setLoginCounter(benutzer.getLoginCounter() + 1);				
				// Login-Zeit speichern 				
				benutzer.setLoginZuletzt(getLokaleZeit());
				EntityManager em = emProvider.getEntityManager();
				EntityTransaction transaction = em.getTransaction();
				transaction.begin();
				em.merge(benutzer);
				transaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	/* Helper-Methoden */
	
	// aktuelle lokale Zeit in UTC liefern
	private Date getLokaleZeit() {		
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		sdf.setTimeZone(TimeZone.getDefault());
		Date localDate = new Date();				
		sdf.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));	
		String dateUTCStr = sdf.format(localDate);
		sdf.setTimeZone(TimeZone.getDefault());				
		Date dateUTC;
		try {
			dateUTC = sdf.parse(dateUTCStr);
			return dateUTC;
		} catch (ParseException e) {
			System.out.println("BenutzerVerwaltungEjb.datumZeitUTC(): Datum kann nicht konvertiert werden.");
			return null;
		}				
		
	}	


}
