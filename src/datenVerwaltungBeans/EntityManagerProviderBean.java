package datenVerwaltungBeans;

import java.io.Serializable;

import javax.annotation.PreDestroy;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.Persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;

@ManagedBean(eager=true)
@ApplicationScoped
public class EntityManagerProviderBean implements Serializable {

	private static final long serialVersionUID = -7579135305917707190L;

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("eggtestjpa");
	private EntityManager em;

	public EntityManagerProviderBean() {
		System.out.println("Creating EntityManagerProviderBean");
	}
	
	public EntityManager getEntityManager () {
		em = emf.createEntityManager();
		return em;
	}
	
    @PreDestroy
    public void closeEm() {
    	if (emf.isOpen()) {
			System.out.println("EntityManagerProvider: Closing EntitManagerFactory...");
			emf.close();
    	}
    }
	
}