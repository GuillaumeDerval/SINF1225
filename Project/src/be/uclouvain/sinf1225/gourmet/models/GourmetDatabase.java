package be.uclouvain.sinf1225.gourmet.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import be.uclouvain.sinf1225.gourmet.Gourmet;
import be.uclouvain.sinf1225.gourmet.R;
import be.uclouvain.sinf1225.gourmet.utils.GourmetUtils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;

/**
 * Layer between the models classes and the database.
 * ONLY models should access to this class!
 * NO verification will be done on rights!
 * @author guillaumederval
 */
class GourmetDatabase extends SQLiteOpenHelper
{
	private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "gourmet";
    private Context context;
    
    /**
     * Initialize database
     * @param context Context which call the database
     */
	public GourmetDatabase(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}
	
	/**
	 * Initialize database
	 */
	public GourmetDatabase()
	{
		super(Gourmet.getAppContext(), DATABASE_NAME, null, DATABASE_VERSION);
		this.context = Gourmet.getAppContext();
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
		if(db.isReadOnly())
		{
			return;
		}
		else
		{
			String sql = GourmetUtils.readRawTextFile(this.context, R.raw.dbv1);
			StringTokenizer st = new StringTokenizer(sql, ";");
			while(st.hasMoreElements())
			{
				String t = st.nextToken();
				db.execSQL(t);
			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		onCreate(db);
	}

	/* User */
	//TODO implement. Commented lines are not essential
	//Must handle the case if the User is a Restaurator!
	public void addUser(User user) {}
	public User getUser(String username) { return null; }
	//public List<User> getAllUsers() { return null; }
	public void updateUser(User user) {}
	//public void deleteUser(User user) {}

	
	/* Dish */
	//TODO implement. Commented lines are not essential
	public Dish getDish(int dishId) { return null;}
	//public List<Dish> getAllDishes() { return null; }
	public List<Dish> getDishInRestaurant(Restaurant restaurant) { return null; }

	
	/* Restaurant */
	//TODO implement. Commented lines are not essential
	//public void addRestaurant(Restaurant restaurant) {}
	//public List<Restaurant> getAllRestaurants() { return null; }
	public List<Restaurant> getRestaurantsInCity(City city) { return null; }
	//public void deleteRestaurant(Restaurant restaurant) {}
	
	/* Cities */
	
	/**
	 * Add a city to the database
	 * @param city
	 */
	public Restaurant getRestaurant(int restoId){
		SQLiteDatabase db = this.getReadableDatabase();
		Restaurant resto = new Restaurant();
		Cursor cursor = db.query("restaurant", //table to select on
				null,//column to get
				"`restoId` = ?", //where clause, ?s will be replaced by...
				new String[]{""+restoId}, //... these values
				null, //group by
				null, //having
				null); //orderby
		if(cursor == null)
			return null;
		
		cursor.moveToFirst();
		Location loc = new Location("Database");
		loc.setLongitude(cursor.getDouble(5));
		loc.setLatitude(cursor.getDouble(6));
		resto.setLocation(loc);
		
		resto.setId(cursor.getInt(0));
		resto.setName(cursor.getString(1));
		resto.setCity(cursor.getString(2));
		resto.setAddress(cursor.getString(4));
		resto.setDescription( cursor.getString(7));
		resto.setEmail(cursor.getString(8));
		resto.setStars(cursor.getInt(9));
		resto.setPhone(cursor.getString(10));
		resto.setWebsite(cursor.getString(11));
		resto.setSeats(cursor.getInt(12));
		resto.setPriceCate(cursor.getInt(13));
		
		return resto;
		/*
		0"restoId" integer NOT NULL PRIMARY KEY AUTOINCREMENT,
		1"name" text NOT NULL,
		2"cityName" text NOT NULL,
		3"cityCountry" text NOT NULL,
		4"address" text NOT NULL,
		5"longitude" real NOT NULL,
		6"latitude" real NOT NULL,
		7"description" text,
		8"email" text,
		9"stars" integer,
		10"phone" integer,
		11"website" integer,
		12"seats" integer NOT NULL,
		13"priceCat" integer NOT NULL,*/

	}
	public void addDish(Dish dish) {
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put("name", dish.getName());
	    values.put("category", dish.getCategory());
	    values.put("description", dish.getDescription());
	    values.put("price", dish.getPrice());
	    values.put("spicy", dish.getSpicy());
	    values.put("vegan", dish.getVegan());
	    values.put("available", dish.getAvailable());
	    values.put("allergen", dish.getAllergen());
	    db.insert("city", null, values);
	    
	    db.close(); // Closing database connection
		
	}
	public void updateDish(Dish dish) {
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put("name", dish.getName());
	    values.put("category", dish.getCategory());
	    values.put("description", dish.getDescription());
	    values.put("price", dish.getPrice());
	    values.put("spicy", dish.getSpicy());
	    values.put("vegan", dish.getVegan());
	    values.put("available", dish.getAvailable());
	    values.put("allergen", dish.getAllergen());
	    db.insert("city", null, values);
	    
	    db.close(); // Closing database connection
		
	}

	public void addCity(City city)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put("name", city.getName());
	    values.put("country", city.getCountry());
	    values.put("longitude", city.getLocation().getLongitude());
	    values.put("latitude", city.getLocation().getLatitude());
	    db.insert("city", null, values);
	    
	    db.close(); // Closing database connection
	}
	
	/**
	 * Get a city from the database
	 * @param name Name of the city
	 * @param country Country of the city
	 * @return a City object if object exists, null else
	 */

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
	
	/**
	 * Return all cities available in database
	 * @return all cities available in database
	 */
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
	
	/**
	 * Return number of cities available in the database
	 * @return number of cities available in the database
	 */
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
	
	
	public void updateRestaurant(Restaurant restaurant) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", restaurant.getName());
		values.put("cityName", restaurant.getCity());
		values.put("adress", restaurant.getAddress());
		values.put("longitude", restaurant.getLocation().getLongitude());
		values.put("latitude", restaurant.getLocation().getLatitude());
		values.put("description", restaurant.getDescription());
		values.put("email", restaurant.getEmail());
		values.put("stars", restaurant.getStars());
		values.put("phone", restaurant.getPhone());
		values.put("website", restaurant.getName());
		values.put("seats", restaurant.getSeats());
		values.put("priceCat", restaurant.getPriceCate());
		db.insert("restaurant", null, values);
	}
	/**
	 * Update city 
	 * @param city
	 * @return number of row updated
	 */
	public int updateCity(City city)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put("name", city.getName());
	    values.put("country", city.getCountry());
	    values.put("longitude", city.getLocation().getLongitude());
	    values.put("latitude", city.getLocation().getLatitude());
	    db.insert("city", null, values);
	    
	    return db.update("city", values, "`name` = ? AND `country` = ?", new String[] {city.getName(), city.getCountry()});
	}
	
	/**
	 * Delete a city
	 * @param city
	 */
	public void deleteDish(Dish dish) {
		SQLiteDatabase db = this.getWritableDatabase();
	    db.delete("dish", "`name` = ?", new String[] {""+dish.getDishId()});
	    db.close();
	}
	
	public void deleteCity(City city)
	{
		SQLiteDatabase db = this.getWritableDatabase();
	    db.delete("city", "`name` = ? AND `country` = ?", new String[] {city.getName(), city.getCountry()});
	    db.close();
	}
	
	/** 
	 * Add a reservation in the database
	 * @param reservation
	 */
	public int addReservation(Reservation reservation)
	{
		// ouverture de la base de donnŽes
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values1 = new ContentValues();
	    
	    // valeur des diffŽrents champs
	    values1.put("user", reservation.getUser().getName());
	    values1.put("resto", reservation.getRestaurant().getName());
	    values1.put("nbrReservation", Integer.toString(reservation.getnbrReservation()));
	    values1.put("date", reservation.getDate().toString());
	    
	    // insertion dans la base de donnŽes
	    long resvId = db.insert("reservation", null, values1);
	    
	    // insertion des diffŽrents plats commandŽs dans la base de donnŽes
	    ContentValues values2 = new ContentValues();
	    for(Reservation.DishNode node : reservation.getDish())
	    {
	    	values2.put("nameDish", node.dish.getName());
	    	values2.put("nbrDish", node.nbrDishes);
	    	values2.put("resvId", resvId);
	    	db.insert("reservationDish", null, values2);
	    }
	    db.close();
	    return 1;
	}
	
	/**
	 * Supprime la rŽservation correspondant au triplet (user,resto,date) dans la table rŽservation
	 * et surpprime tt les plats commandŽs dans la table reservationDish
	 */
	public int deleteReservation(User user, Restaurant resto, Date date)
	{
	    // resvId de la rŽservation
	    SQLiteDatabase db = this.getReadableDatabase();
	    String str[] = new String[] {user.getName(), resto.getName(), date.toString()};
	    Cursor cursor = db.rawQuery("select resvId from reservation where user = ? AND resto = ? AND date = ?", str);
	    if(cursor == null || cursor.getCount() != 1) return 0;
	    cursor.moveToFirst();
	    int RESVID = cursor.getInt(0);
	    db.close();
	    
	    // suppression des plats rŽservŽs dans la table reservationDish
	    SQLiteDatabase dbw = this.getWritableDatabase();
	    dbw.delete("reservationDish", "`resvId` = ?", new String[] {"" + RESVID});
	    dbw.close();
	    return 1;
	}
	// retourner ttes lesrŽservtaion d'un meme user => database
	// retourner ttes les users ayant effectuŽs une rŽservation dans un restaurant => database
	// retourner toutes les rŽservation d'un m�me plat => database
	
	/* Reservation */
	//TODO implement. Commented lines are not essential
	public Reservation getReservation(User user, Restaurant restaurant, Date date) {return null;}
	//public List<Reservation> getAllReservations() { return null; }
	public List<Reservation> getReservationInRestaurant(Restaurant restaurant) { return null; }
	public List<Reservation> getReservationByUser(User user) { return null; }
	public void updateReservation(Reservation reservation) {}	
}
