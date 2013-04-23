package be.uclouvain.sinf1225.gourmet.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;

/**
 * Utilities
 * @author Guillaume Derval
 */
public class GourmetUtils
{
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
}
