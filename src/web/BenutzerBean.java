package web;


import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class BenutzerBean implements Serializable {
	
	public static enum BenutzerRolle {ADMIN, KUNDE};
	private static final long serialVersionUID = 8403511019835098481L;

	private Integer				  id;
	private String                name;
	private BenutzerRolle         rolle = BenutzerRolle.KUNDE;
	private Boolean               istAdministrator = false;
	private Boolean               authorisiert = false;
	private Integer               kundenId = 0;
	private BigDecimal            guthaben = new BigDecimal(0.0);


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String username) {
		this.name = username;
	}

	public BenutzerRolle getRolle() {
		return rolle;
	}

	public Boolean getIstAdministrator() {
		return istAdministrator;
	}

	public void setIstAdministrator(Boolean istAdministrator) {
		this.istAdministrator = istAdministrator;
	}
	
	public void setRolle(BenutzerRolle rolle) {
		this.rolle = rolle;
	}
	
	public Boolean getAuthorisiert() {
		return authorisiert;
	}

	public void setAuthorisiert(Boolean loggedIn) {
		this.authorisiert = loggedIn;
	}

	public Integer getKundenId() {
		return kundenId;
	}

	public void setKundenId(Integer kundenId) {
		this.kundenId = kundenId;
	}
	
	public BigDecimal getGuthaben() {
		return guthaben;
	}

	public void setGuthaben(BigDecimal guthaben) {
		this.guthaben = guthaben;
	}
	
	/* Eventhandler: Pruefen ob Benutzer authorisiert. Wenn nicht -> login.xhtml */
	public void checkAuthorisiert () throws IOException {
		String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
		if (!viewId.equalsIgnoreCase("/login.xhtml") && !this.authorisiert) {
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
			
		}
	}



	
}
