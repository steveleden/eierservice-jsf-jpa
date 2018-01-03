package web;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class BenutzerViewBean implements Serializable {


	private static final long serialVersionUID = -3550749768363783831L;
	
	BenutzerView benutzerView = new BenutzerView();

	public BenutzerView getBenutzerView() {
		return benutzerView;
	}

	public void setBenutzerView(BenutzerView benutzerView) {
		this.benutzerView = benutzerView;
	}


}
