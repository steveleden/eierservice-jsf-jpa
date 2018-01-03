package web;

import java.io.Serializable;
import java.util.TimeZone;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class GlobalBean implements Serializable {

	private static final long serialVersionUID = -7430312506269577249L;

	TimeZone hostTimeZone = TimeZone.getDefault();
	TimeZone localTimeZone = TimeZone.getTimeZone("GMT+2");

	public TimeZone getHostTimeZone() {
		return hostTimeZone;
	}
	public void setHostTimeZone(TimeZone hostTimeZone) {
		this.hostTimeZone = hostTimeZone;
	}
	public TimeZone getLocalTimeZone() {
		return localTimeZone;
	}
	public void setLocalTimeZone(TimeZone localTimeZone) {
		this.localTimeZone = localTimeZone;
	}
	
	
}
