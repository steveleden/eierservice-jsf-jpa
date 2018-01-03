package web;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean
@SessionScoped
public class BestellungViewBean implements Serializable{
	
	private static final long serialVersionUID = 4755835092462398511L;

	private BestellungView bestellungView = new BestellungView();

	
	public BestellungViewBean() {
		//System.out.println("Init BestellungViewBean... ");
	}
	
	public BestellungView getBestellungView() {
		return bestellungView;
	}

	public void setBestellungView(BestellungView bestellungView) {
		this.bestellungView = bestellungView;
	}
	
}
