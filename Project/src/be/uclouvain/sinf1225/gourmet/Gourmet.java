package be.uclouvain.sinf1225.gourmet;

import android.app.Application;
import android.content.Context;

/*
 * Application class, used to have a static context
 */
public class Gourmet extends Application
{
	private static Context context;

    public void onCreate()
    {
        super.onCreate();
        Gourmet.context = getApplicationContext();
    }

    public static Context getAppContext()
    {
        return Gourmet.context;
    }
}
