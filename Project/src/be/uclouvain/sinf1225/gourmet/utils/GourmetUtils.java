package be.uclouvain.sinf1225.gourmet.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import be.uclouvain.sinf1225.gourmet.CityListView;
import be.uclouvain.sinf1225.gourmet.Gourmet;
import be.uclouvain.sinf1225.gourmet.LoginView;
import be.uclouvain.sinf1225.gourmet.PreferenceManagerView;
import be.uclouvain.sinf1225.gourmet.R;
import be.uclouvain.sinf1225.gourmet.ReservationManagerView;
import be.uclouvain.sinf1225.gourmet.TestView;
import be.uclouvain.sinf1225.gourmet.models.User;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Utilities
 * @author Guillaume Derval
 */
public class GourmetUtils
{
	/**
	 * Return true if the Google Play Services lib is loaded and available on this device
	 * @return true if available, false if not.
	 */
	public static boolean hasGooglePlayServicesLib()
	{
		try
		{
			Class.forName("com.google.android.gms.common.GooglePlayServicesUtil", true, GourmetUtils.class.getClassLoader());
			return GooglePlayServicesUtil.isGooglePlayServicesAvailable(Gourmet.getAppContext()) == ConnectionResult.SUCCESS;
		}
		catch (ClassNotFoundException e)
		{
			return false;
		}
	}
	
	/**
	 * Create a standard menu for this activity.
	 * Activity should redirect onOptionsItemSelected to GourmetUtils.onMenuItemSelected to let this work.
	 * @param menu
	 * @param view 
	 * @param nowId Ressource of the menu this activity is currently in.
	 */
	public static void createMenu(Menu menu, Activity view, int nowId)
	{
		view.getActionBar().setDisplayShowTitleEnabled(false);
		
		MenuInflater inflater = view.getMenuInflater();
	    inflater.inflate(R.menu.tablayoutmenu, menu);
	    
	    MenuItem item = menu.findItem(nowId);
	    item.setVisible(false);
	}
	
	/**
	 * Apply clic event on the menu created by createMenu.
	 * @param item
	 * @param view
	 * @return true if the event was consumed.
	 */
	public static boolean onMenuItemSelected(MenuItem item, Activity view)
	{
		if(item.getItemId() == R.id.search)
		{
			Intent intent = new Intent(view, CityListView.class);
		    view.startActivity(intent);
		}
		else if(item.getItemId() == R.id.reservations)
		{
			Intent intent = new Intent(view, ReservationManagerView.class);
		    view.startActivity(intent);
		}
		else if(item.getItemId() == R.id.preferences)
		{
			Intent intent = new Intent(view, PreferenceManagerView.class);
		    view.startActivity(intent);
		}
		else if(item.getItemId() == R.id.logout)
		{
			//Update last login values
        	SharedPreferences sp = view.getSharedPreferences("gourmetLogin",Context.MODE_PRIVATE);
        	sp.edit().putString("email", "").putString("passwordHash", "").commit();

        	User.logoutUser();
        	Intent intent = new Intent(view, LoginView.class);
			view.startActivity(intent);
		}
		else if(item.getItemId() == R.id.test)
		{
			Intent intent = new Intent(view, TestView.class);
		    view.startActivity(intent);
		}
		else
			return false;
		return true;
	}
	
	/**
	 * Read raw ressource
	 * @param context Context from which function is called
	 * @param ressourceId Raw ressource id to read
	 * @return string containing all text inside the ressource
	 */
	public static String readRawTextFile(Context context, int ressourceId)
    {
		InputStream inputStream = context.getResources().openRawResource(ressourceId);
		InputStreamReader inputreader = new InputStreamReader(inputStream);
		BufferedReader buffreader = new BufferedReader(inputreader);
		
		String line;
		StringBuilder text = new StringBuilder();

		try
		{
			while (( line = buffreader.readLine()) != null)
			{
				text.append(line);
				text.append('\n');
			}
		}
		catch (IOException e)
		{
			return null;
		}
		
		return text.toString();
    }
	
	private static String bytesToHex(byte[] b)
	{
		char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
				'8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
		StringBuffer buf = new StringBuffer();
		for (int j=0; j<b.length; j++)
		{
			buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
			buf.append(hexDigit[b[j] & 0x0f]);
		}
		return buf.toString();
	}
	
	/**
	 * Return SHA1 Hash of a string.
	 * @param input String to hash
	 * @return An hexadecimal string, representing the SHA1 hash
	 */
	public static String sha1(String input)
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance("SHA1");
			md.update(input.getBytes());
			return bytesToHex(md.digest());
		}
		catch (Exception e)
		{
			System.out.println("Exception: "+e);
		}
		return "";
	}
}
