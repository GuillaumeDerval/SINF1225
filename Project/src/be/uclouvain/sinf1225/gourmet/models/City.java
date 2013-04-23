package be.uclouvain.sinf1225.gourmet.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import be.uclouvain.sinf1225.gourmet.utils.GourmetDatabase;

import android.location.Location;

/**
 * Represents a city
 * @author Guillaume Derval
 */
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
	
	/**
	 * Create a new City. Will not be saved to database.
	 * @param name Name of the city
	 * @param country Country(entire name, in french, like Belgique)
	 * @param loc Location of the city
	 * @param restaurantsID restaurantIDs which are in this city.
	 */
	public City(String name, String country, Location loc, List<Integer> restaurantsID)
	{
		this.name = name;
		this.country = country;
		this.location = loc;
		this.restaurantsID = restaurantsID;
		this.restaurants = null;
	}

	/**
	 * Get the name of this city
	 * @return name
	 */
	public String getName() { return name; }
	
	/**
	 * Get the country of this city
	 * @return country
	 */
	public String getCountry() { return country; }
	
	/**
	 * Get the location of this city
	 * @return location
	 */
	public Location getLocation() { return location; }
	
	/**
	 * Return the number of restaurant contained in this city
	 * @return restaurants count
	 */
	public int getRestaurantCount() { return restaurantsID.size(); }
	
	/**
	 * Return restaurants contained in this city
	 * @return List of all restaurants contained in this city
	 */
	public List<Restaurant> getRestaurants()
	{
		if(restaurants == null)
		{
			GourmetDatabase db = new GourmetDatabase();
			restaurants = db.getRestaurantsInCity(this);
		}
		return restaurants;
	}
	
	/**
	 * Return restaurants contained in this city, ordered by distance from parameter "location"
	 * @param location Location from which calculate distance
	 * @return List of all restaurants contained in this city, ordered by distance from parameter "location"
	 */
	public List<Restaurant> getClosestRestaurantsInCity(Location location)
	{
		return getClosestRestaurantsInCity(location, -1);
	}
	
	/**
	 * Return restaurants contained in this city, ordered by distance from parameter "location", limited to "howmuch" restaurants
	 * @param location Location from which calculate distance
	 * @param howmuch max number of restaurant to get 
	 * @return List of "howmuch" restaurants contained in this city, ordered by distance from parameter "location"
	 */
	public List<Restaurant> getClosestRestaurantsInCity(final Location location, int howmuch)
	{
		List<Restaurant> restaurants = new ArrayList<Restaurant>(getRestaurants());
		Collections.sort(restaurants, new Comparator<Restaurant>()
		{
			@Override
			public int compare(Restaurant arg0, Restaurant arg1)
			{
				return Double.valueOf(arg0.getLocation().distanceTo(location)).compareTo((double) arg1.getLocation().distanceTo(location));
			}
		});
		return howmuch >= 0 ? restaurants.subList(0, howmuch) : restaurants;
	}
	
	/*
	 * Class
	 */
	
	/**
	 * Return an object City found in database
	 * @param name Name of the city
	 * @param country Country of the city
	 * @return City object if city exists, else null
	 */
	public static City getCity(String name, String country)
	{
		GourmetDatabase db = new GourmetDatabase();
		City city = db.getCity(name, country);
		db.close();
		return city;
	}
	
	/**
	 * Return all cities available in database
	 * @return All cities availables
	 */
	public static List<City> getAllCities()
	{
		GourmetDatabase db = new GourmetDatabase();
		List<City> cities = db.getAllCities();
		db.close();
		return cities;
	}
}
