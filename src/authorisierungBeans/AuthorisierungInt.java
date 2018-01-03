package authorisierungBeans;

public interface AuthorisierungInt {

	/* Falls authorisiert: Gibt die Rolle (1 = Benutzer, 2 = Administrator) zurueck. */
	/* Falls nicht authorisiert: 0                                                  */
	public Integer authorisiert(String benutzerName, String pw);
	
	
}
