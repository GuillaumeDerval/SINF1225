package be.uclouvain.sinf1225.gourmet.models;

public class TimeTable {
	private String morningOpening;
	private String morningClosing;
	private String eveningOpening;
	private String eveningClosing;
	private String day;
	
	
	
	public TimeTable(String morningOpening, String morningClosing,
			String eveningOpening, String eveningClosing, String day) {
		super();
		this.morningOpening = morningOpening;
		this.morningClosing = morningClosing;
		this.eveningOpening = eveningOpening;
		this.eveningClosing = eveningClosing;
		this.day = day;
	}
	public String getMorningOpening() {
		return morningOpening;
	}
	public void setMorningOpening(String morningOpening) {
		this.morningOpening = morningOpening;
	}
	public String getMorningClosing() {
		return morningClosing;
	}
	public void setMorningClosing(String morningClosing) {
		this.morningClosing = morningClosing;
	}
	public String getEveningOpening() {
		return eveningOpening;
	}
	public void setEveningOpening(String eveningOpening) {
		this.eveningOpening = eveningOpening;
	}
	public String getEveningClosing() {
		return eveningClosing;
	}
	public void setEveningClosing(String eveningClosing) {
		this.eveningClosing = eveningClosing;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}

}
