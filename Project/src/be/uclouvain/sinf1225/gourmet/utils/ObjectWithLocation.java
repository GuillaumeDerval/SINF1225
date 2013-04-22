package be.uclouvain.sinf1225.gourmet.utils;

import android.location.Location;

public interface ObjectWithLocation
{
	public Location getLocation();
	public double getLongitude();
	public double getLatitude();
}
