package datenVerwaltungBeans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import datenVerwaltungInterface.KundenVerwaltungInt;
import entity.Benutzer;
import entity.Kunde;

@ManagedBean(name="kundenVerwaltung", eager=true)
@SessionScoped
public class KundenVerwaltungBean implements KundenVerwaltungInt, Serializable {

	private static final long serialVersionUID = -7922492662448527369L;

	/* Dependency Injection */
	@ManagedProperty(value="#{entityManagerProviderBean}")
	private EntityManagerProviderBean emProvider;
	
	public KundenVerwaltungBean() {
		//System.out.println("Creating KundenVerwaltungBean...");
	}

	
	/* Setter and Getter Methoden */
	public void setEmProvider(EntityManagerProviderBean emProvider) {
		this.emProvider = emProvider;
	}
	
	
	@Override
	public Kunde leseKundeById(Integer kundenId) {
		
		try {
			EntityManager em = emProvider.getEntityManager();
			Kunde kunde = em.find(Kunde.class, kundenId);
			em.close();
			return kunde;
		} catch (Exception e) {
			System.out.println("KundenVerwaltungEjb: Fehler beim Lesen Kunde mit Id = " + kundenId);
		}
		return null;
	}

	@Override
	public Kunde leseKundeByBenutzerName(String benutzerName) {
		
		try {
			EntityManager em = emProvider.getEntityManager();
			
			Benutzer benutzer = em.createQuery("SELECT b FROM Benutzer b where b.name=?1", Benutzer.class)
									.setParameter(1, benutzerName.trim())
									.getSingleResult();
			
			em.close();;
			Kunde kunde = benutzer.getKunde();
			if (kunde == null) 
				System.out.println("KundenVerwaltungEjb: Benutzer gefunden, Kunde nicht gefunden. Benutzername: " + benutzer.getName());				
			return kunde;
		} catch (Exception e) {
			System.out.println("KundenVerwaltungEjb: Benutzer/Kunde nicht gefunden fuer Benutzername: " + benutzerName);
			return null;
		}

	}
	
	
	@Override
	public List<Benutzer> leseBenutzerZuKundeById(Integer kundeId) {
		
		try {
			EntityManager em = emProvider.getEntityManager();
			
			Kunde k = em.find(Kunde.class, kundeId);

			List<Benutzer> benutzer = k.getBenutzers();
			
			em.close();;
			return benutzer;
		} catch (Exception e) {
			
			System.out.println("KundenVerwaltungEjb, leseBenutzerZuKundeById: Benutzer kann nicht gelesen werden");
			System.out.println(e.getMessage());
			return null;

		}

	}		
	

	@Override
	public List<Kunde> leseAlleKunden() {

		try {
			EntityManager em = emProvider.getEntityManager();
			
			List<Kunde> kundenListe = em.createQuery("SELECT k from Kunde k order by k.nachname", Kunde.class).getResultList();
			
			em.close();;
			return kundenListe;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Kunde kreiereKunde(Kunde kunde) {

		try {
			EntityManager em = emProvider.getEntityManager();
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			
			em.persist(kunde);
			em.flush();
			
			transaction.commit();
			em.close();
			return kunde;
		} catch (Exception e) {

			System.out.println("KundenVerwaltungEjb, kreiereKunde: Kunde kann nicht kreiert werden. Vorname/Nachname = " 
							   + kunde.getVorname() + " " + kunde.getNachname());			
			return null;
		}

	}

	@Override
	public Integer mutiereKunde(Kunde kunde) {
		
		try {
			EntityManager em = emProvider.getEntityManager();
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			
			em.merge(kunde);
			
			transaction.commit();
			em.close();
			return 0;
		} catch (Exception e) {
			
			System.out.println("KundenVerwaltungEjb: Kunde kann nicht gemerged werden. Id = " + kunde.getId());		
			return -1;
		}

	}

	@Override
	public Integer loescheKunde(Kunde kunde) {

		try {
			EntityManager em = emProvider.getEntityManager();
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			
			Kunde zuLoeschen = em.getReference(Kunde.class, kunde.getId());
			em.remove(zuLoeschen);
			
			transaction.commit();
			em.close();
			return 0;
		} catch (Exception e) {
			
			System.out.println("KundenVerwaltungEjb: Kunde kann nicht geloescht werden. Id = " + kunde.getId());		
			return -1;
		}
	}

	@Override
	public Integer verknuepfeBenutzerMitKunde(String benutzerName, Kunde kunde) {
		
		try {
			EntityManager em = emProvider.getEntityManager();
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			Benutzer benutzer = em.createQuery("SELECT b FROM Benutzer b where b.name=?1", Benutzer.class)
								.setParameter(1, benutzerName.trim())
								.getSingleResult();

			Kunde kundeBisher = benutzer.getKunde();

			if (kunde == null) {				
				//Keine Kunde abgefüllt: alle Benutzer des Kunden löschen
				if (kundeBisher != null) {
					kundeBisher.setBenutzers(null);
					em.merge(kundeBisher);
				}
			}
			else {
				kunde.addBenutzer(benutzer);
				em.merge(kunde);
			}
						
			benutzer.setKunde(kunde);
			em.merge(benutzer);
						
			em.getTransaction().commit();
			em.close();
			return 0;
			
		} catch (Exception e) {
			
			System.out.println("KundenVerwaltungEjb: Benutzer kann nicht mit Kunde verknuepft werden. Kunde.Id = " + kunde.getId());		
			System.out.println(e.getMessage());
			return -1;

		}
		
		
	}


}
