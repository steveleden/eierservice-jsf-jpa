package web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class BenutzerListeViewBean implements Serializable {

	private static final long serialVersionUID = -2113727262288407949L;
	
	private List<BenutzerView> 	benutzerListView = new ArrayList<BenutzerView>();
	private DialogModus			dialogModus = DialogModus.BENUTZER_VERWALTUNG;
	
	enum DialogModus {BENUTZER_VERWALTUNG, BENUTZERWAHL_FUER_KUNDE}

	public BenutzerListeViewBean() {
	}
	
	public List<BenutzerView> getBenutzerListView() {
		return benutzerListView;
	}

	public void setBenutzerListView(List<BenutzerView> benutzerListView) {
		this.benutzerListView = benutzerListView;
	}

	public DialogModus getDialogModus() {
		return dialogModus;
	}

	public void setDialogModus(DialogModus dialogModus) {
		this.dialogModus = dialogModus;
	}


	

}
