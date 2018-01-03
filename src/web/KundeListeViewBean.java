package web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class KundeListeViewBean implements Serializable {

	private static final long serialVersionUID = 8128240094268938259L;
	
	private List<KundeView> 	kundenListView = new ArrayList<KundeView>();

	public List<KundeView> getKundenListView() {
		return kundenListView;
	}

	public void setKundenListView(List<KundeView> kundenListView) {
		this.kundenListView = kundenListView;
	}
	

}
