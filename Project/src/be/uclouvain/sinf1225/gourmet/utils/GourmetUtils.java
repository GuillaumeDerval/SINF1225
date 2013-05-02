package be.uclouvain.sinf1225.gourmet.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;

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
