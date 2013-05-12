package be.uclouvain.sinf1225.gourmet.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Manage GPS information
 * 
 * @author Guillaume Derval
 */
public class GourmetLocationListener implements LocationListener
{
	private LocationManager locationManager = null;
	private static Location lastLocation = null;
	private GourmetLocationReceiver callbackObjet = null;

	/**
	 * Create GourmetLocationListener. You have to call init/close on the object to begin/stop updates.
	 * 
	 * @param c
	 *            context from which GourmetLocationReceiver is called
	 * @param r
	 *            GourmetLocationReceiver object callback
	 */
	public GourmetLocationListener(Context c, GourmetLocationReceiver r)
	{
		locationManager = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
		callbackObjet = r;
	}

	/**
	 * Init location updates.
	 * 
	 * @return this object
	 */
	public GourmetLocationListener init()
	{
		if (locationManager.getProvider(LocationManager.NETWORK_PROVIDER) != null)
		{
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
			lastLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}
		if (locationManager.getProvider(LocationManager.GPS_PROVIDER) != null)
		{
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
			Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			lastLocation = isBetterLocation(loc, lastLocation) ? loc : lastLocation;
		}
		return this;
	}

	/**
	 * Stop location updates
	 */
	public void close()
	{
		locationManager.removeUpdates(this);
	}

	/**
	 * Return the more precise location available
	 * 
	 * @return more precise location available
	 */
	public static Location getLastLocation()
	{
		return lastLocation;
	}

	@Override
	public void onLocationChanged(Location location)
	{
		if (isBetterLocation(location, lastLocation))
		{
			lastLocation = location;
			if (callbackObjet != null)
				callbackObjet.onLocationUpdate(location);
		}
	}

	@Override
	public void onProviderDisabled(String provider)
	{
	}

	@Override
	public void onProviderEnabled(String provider)
	{
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{
	}

	// Thanks google... http://developer.android.com/guide/topics/location/strategies.html
	protected boolean isBetterLocation(Location location, Location currentBestLocation)
	{
		if (currentBestLocation == null)
			return true;

		if (location == null)
			return false;

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > 2 * 60 * 1000;
		boolean isSignificantlyOlder = timeDelta < -2 * 60 * 1000;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use the new location
		// because the user has likely moved
		if (isSignificantlyNewer)
		{
			return true;
		}
		else if (isSignificantlyOlder) // If the new location is more than two minutes older, it must be worse
		{
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(location.getProvider(), currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and accuracy
		if (isMoreAccurate)
		{
			return true;
		}
		else if (isNewer && !isLessAccurate)
		{
			return true;
		}
		else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider)
		{
			return true;
		}
		return false;
	}

	private boolean isSameProvider(String provider1, String provider2)
	{
		if (provider1 == null)
		{
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}
}