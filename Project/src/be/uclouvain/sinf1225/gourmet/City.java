package be.uclouvain.sinf1225.gourmet;

import java.util.List;

import android.location.Location;
import be.uclouvain.sinf1225.gourmet.utils.ObjectWithLocation;

public class City implements ObjectWithLocation
{
	private String name;
	private String country;
	private Location location;
	private List<Integer> restaurantsID;
	//TODO implements this when Restaurants will be done
	//private List<Restaurant> restaurants;
	
	public City(String name, String country, Location loc, List<Integer> restaurantsID)
	{
		this.name = name;
		this.country = country;
		this.location = loc;
		this.restaurantsID = restaurantsID;
	}

	public String getName() { return name; }
	public String getCountry() { return country; }
	public Location getLocation() { return location; }
	public double getLongitude() { return location.getLongitude(); }
	public double getLatitude() { return location.getLatitude(); }
	
	public int getRestaurantCount() { return restaurantsID.size(); }
	
	//TODO implement.
	//public List<Restaurant> getRestaurants() {}
	//public List<Restaurant> getClosestRestaurantsInCity(Location location, int howmuch) {}
	
}
