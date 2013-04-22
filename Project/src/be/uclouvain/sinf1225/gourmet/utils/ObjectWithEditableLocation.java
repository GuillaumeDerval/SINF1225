package be.uclouvain.sinf1225.gourmet.utils;

public interface ObjectWithEditableLocation extends ObjectWithLocation
{
	public void setLocation();
	public void setLongitude();
	public void setLatitude();
}
