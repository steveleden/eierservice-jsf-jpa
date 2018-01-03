package web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class BestellungListeViewBean implements Serializable {

	
	private static final long serialVersionUID = -4588659697912554514L;

	private List<BestellungView> 	bestellungListView = new ArrayList<BestellungView>();
	private Boolean					statusBestelltVorhanden = false;  //true falls es in der Liste Bestellungen mit Status 1 (=bestellt) gibt.
	private Integer					anzahlStatusBestellt = 0; //Anzahl der Bestellungen in der Liste mit Status 1 (=bestellt).

	public List<BestellungView> getBestellungListView() {
		return bestellungListView;
	}

	public void setBestellungListView(List<BestellungView> bestellungListView) {
		this.bestellungListView = bestellungListView;
	}

	public Boolean getStatusBestelltVorhanden() {
		return statusBestelltVorhanden;
	}

	public void setStatusBestelltVorhanden(Boolean statusBestelltVorhanden) {
		this.statusBestelltVorhanden = statusBestelltVorhanden;
	}

	public Integer getAnzahlStatusBestellt() {
		return anzahlStatusBestellt;
	}

	public void setAnzahlStatusBestellt(Integer anzahlStatusBestellt) {
		this.anzahlStatusBestellt = anzahlStatusBestellt;
	}
	
	public void initList() {
		this.bestellungListView.clear();
		this.statusBestelltVorhanden = false;
		this.anzahlStatusBestellt = 0;
	}
}
