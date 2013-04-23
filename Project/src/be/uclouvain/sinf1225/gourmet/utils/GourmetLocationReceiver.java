package be.uclouvain.sinf1225.gourmet.utils;

import android.location.Location;

/**
 * Interface for object which will receive events from GourmetLocationListener.
 * @author Guillaume Derval
 * @see GourmetLocationListener
 */
public interface GourmetLocationReceiver
{
	/**
	 * Receive a new position
	 * @param loc new position (always more accurate than the location send before)
	 */
	public void onLocationUpdate(Location loc);
}
