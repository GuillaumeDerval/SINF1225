package be.uclouvain.sinf1225.gourmet;

import java.util.List;

import be.uclouvain.sinf1225.gourmet.utils.GourmetDatabase;

import android.location.Location;

public class City
{
	private String name;
	private String country;
	private Location location;
	private List<Integer> restaurantsID;
	private List<Restaurant> restaurants;
	
	/*
	 * Object
	 */
	public City(String name, String country, Location loc, List<Integer> restaurantsID)
	{
		this.name = name;
		this.country = country;
		this.location = loc;
		this.restaurantsID = restaurantsID;
		this.restaurants = null;
	}

	public String getName() { return name; }
	public String getCountry() { return country; }
	public Location getLocation() { return location; }
	public double getLongitude() { return location.getLongitude(); }
	public double getLatitude() { return location.getLatitude(); }
	
	public int getRestaurantCount() { return restaurantsID.size(); }
	
	public List<Restaurant> getRestaurants()
	{
		if(restaurants == null)
		{
			GourmetDatabase db = new GourmetDatabase();
			restaurants = db.getRestaurantsInCity(this);
		}
		return restaurants;
	}
	
	public List<Restaurant> getClosestRestaurantsInCity(Location location, int howmuch)
	{
		//TODO implement.
		return null;
	}
	
	/*
	 * Class
	 */
	public static City getCity(String name, String country)
	{
		GourmetDatabase db = new GourmetDatabase();
		City city = db.getCity(name, country);
		db.close();
		return city;
	}
	
	public static List<City> getAllCities()
	{
		GourmetDatabase db = new GourmetDatabase();
		List<City> cities = db.getAllCities();
		db.close();
		return cities;
	}
}
