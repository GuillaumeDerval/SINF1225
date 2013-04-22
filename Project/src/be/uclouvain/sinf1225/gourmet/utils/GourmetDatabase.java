package be.uclouvain.sinf1225.gourmet.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import be.uclouvain.sinf1225.gourmet.City;
import be.uclouvain.sinf1225.gourmet.R;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;

public class GourmetDatabase extends SQLiteOpenHelper
{
	private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "gourmet";
    private Context context;
    
	public GourmetDatabase(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	//TODO delete this
	@Override
	public void onOpen(SQLiteDatabase db)
	{
		onCreate(db);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		String sql = GourmetUtils.readRawTextFile(this.context, R.raw.dbv1);
		StringTokenizer st = new StringTokenizer(sql, ";");
		while(st.hasMoreElements())
		{
			String t = st.nextToken();
			db.execSQL(t);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		onCreate(db);
	}

	/* Cities */
	public void addCity(City city)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put("name", city.getName());
	    values.put("country", city.getCountry());
	    values.put("longitude", city.getLongitude());
	    values.put("latitude", city.getLatitude());
	    db.insert("city", null, values);
	    
	    db.close(); // Closing database connection
	}
	
	public City getCity(String name, String country)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query("city", //table to select on
				new String[]{"name","country","longitude","latitude"}, //column to get
				"`name` = ? AND `country` = ?", //where clause, ?s will be replaced by...
				new String[]{name,country}, //... these values
				null, //group by
				null, //having
				null); //orderby
		if(cursor == null)
			return null;
		cursor.moveToFirst();
		
		Location loc = new Location("Database");
		loc.setLongitude(cursor.getDouble(2));
		loc.setLatitude(cursor.getDouble(3));
		
		List<Integer> restaurantsID = new ArrayList<Integer>();
		cursor = db.query(true, "restaurant", new String[]{"restoId"}, "`cityName` = ? AND `cityCountry` = ?", new String[]{name, country}, null, null, null,null);
		if(cursor != null)
		{
			cursor.moveToFirst();
			for(int i = 0; i < cursor.getCount(); i++)
			{
				restaurantsID.add(cursor.getInt(0));
				cursor.moveToNext();
			}
		}
		
		return new City(name,country,loc, restaurantsID);
	}
	
	public List<City> getAllCities()
	{
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query("city", //table to select on
				new String[]{"name","country","longitude","latitude"}, //column to get
				null, //where
				null, //where string
				null, //group by
				null, //having
				null); //orderby
		if(cursor == null)
			return null;
		cursor.moveToFirst();
		
		List<City> cities = new ArrayList<City>();
		for(int j = 0; j < cursor.getCount(); j++)
		{
			Location loc = new Location("Database");
			loc.setLongitude(cursor.getDouble(2));
			loc.setLatitude(cursor.getDouble(3));
		
			List<Integer> restaurantsID = new ArrayList<Integer>();
			Cursor restos = db.query(true, "restaurant", new String[]{"restoId"}, "`cityName` = ? AND `cityCountry` = ?", new String[]{cursor.getString(0),cursor.getString(1)}, null, null, null,null);
			if(restos != null)
			{
				restos.moveToFirst();
				for(int i = 0; i < restos.getCount(); i++)
				{
					restaurantsID.add(restos.getInt(0));
					restos.moveToNext();
				}
			}
		
			cities.add(new City(cursor.getString(0),cursor.getString(1),loc, restaurantsID));
			cursor.moveToNext();
		}
		return cities;
	}
	
	public int getCitiesCount()
	{
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query("city", //table to select on
				new String[]{"name","country"}, //column to get
				null, //where
				null, //where string
				null, //group by
				null, //having
				null); //orderby
		if(cursor == null)
			return 0;
		return cursor.getCount();
	}
	
	public int updateCity(City city)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put("name", city.getName());
	    values.put("country", city.getCountry());
	    values.put("longitude", city.getLongitude());
	    values.put("latitude", city.getLatitude());
	    db.insert("city", null, values);
	    
	    return db.update("city", values, "`name` = ? AND `country` = ?", new String[] {city.getName(), city.getCountry()});
	}
	
	public void deleteCity(City city)
	{
		SQLiteDatabase db = this.getWritableDatabase();
	    db.delete("city", "`name` = ? AND `country` = ?", new String[] {city.getName(), city.getCountry()});
	    db.close();
	}
}
