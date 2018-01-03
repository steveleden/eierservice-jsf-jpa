package web;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class KundeViewBean implements Serializable {

	private static final long serialVersionUID = -1600860940932807061L;
	
	KundeView kundeView = new KundeView();

	
	public KundeView getKundeView() {
		return kundeView;
	}

	public void setKundeView(KundeView kundeView) {
		this.kundeView = kundeView;
	}

}
