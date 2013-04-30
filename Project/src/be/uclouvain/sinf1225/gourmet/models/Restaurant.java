package be.uclouvain.sinf1225.gourmet.models;

import java.util.List;

import android.location.Location;
import be.uclouvain.sinf1225.gourmet.enums.PriceCategory;

//TODO Documentation.
public class Restaurant
{
	private int id;
	private City city;
	private String name;
	private String address;
	private PriceCategory priceCategory;
	private Location location;
	private String phone;
	private int seats;
	private String website; 
	private String description;
	private int stars;
	private String email;
	private List<Reservation> reservations;
	private List<Dish> dishes;
	
	
	/**
	 * Constructor for Restaurant. Should only be called by GourmetDatabase
	 * @param id
	 * @param city
	 * @param name
	 * @param address
	 * @param priceCategory
	 * @param location
	 * @param phone
	 * @param seats
	 * @param website
	 * @param description
	 * @param stars
	 * @param email
	 * @param reservations
	 * @param dishes
	 */
	
	public Restaurant(){}
	public Restaurant(int id, City city, String name, String address, PriceCategory priceCategory, 
			Location location, String phone, int seats, String website, String description,
			int stars, String email)
	{
		this.id = id;
		this.city = city;
		this.name = name;
		this.address = address;
		this.priceCategory = priceCategory;
		this.location = location;
		this.phone = phone;
		this.seats = seats;
		this.website = website;
		this.description = description;
		this.stars = stars;
		this.email = email;
		this.reservations = null;
		this.dishes = null;
	}
	/**
	 * update restaurant in DB
	 * @param restaurant
	 */
	public void updateRestaurant( Restaurant restaurant){
		GourmetDatabase db = new GourmetDatabase();
		db.updateRestaurant(restaurant);
		db.close();
		
	}
	/**
	 * getRestaurant in DB
	 * @param restoId
	 * @return
	 */
	public static Restaurant getRestaurant(int restoId)
	{
		GourmetDatabase db = new GourmetDatabase();
		Restaurant resto = db.getRestaurant(restoId);
		db.close();
		return resto;
	}
	
	public int getId()
	{
		return id;
	}

	public City getCity()
	{
		return city;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public PriceCategory getPriceCategory()
	{
		return priceCategory;
	}

	public void setPriceCategory(PriceCategory priceCategory)
	{
		this.priceCategory = priceCategory;
	}

	public Location getLocation()
	{
		return location;
	}

	public void setLocation(Location location)
	{
		this.location = location;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public int getSeats()
	{
		return seats;
	}

	public void setSeats(int seats)
	{
		this.seats = seats;
	}

	public String getWebsite()
	{
		return website;
	}

	public void setWebsite(String website)
	{
		this.website = website;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public int getStars()
	{
		return stars;
	}

	public void setStars(int stars)
	{
		this.stars = stars;
	}

	public List<Reservation> getReservations()
	{
		if(reservations == null)
		{
			GourmetDatabase db = new GourmetDatabase();
			reservations = db.getReservationInRestaurant(this);
			db.close();
		}
		return reservations;
	}

	public List<Dish> getDishes()
	{
		if(dishes == null) //only query db when it is necessary
		{
			GourmetDatabase db = new GourmetDatabase();
			dishes = db.getDishInRestaurant(this);
			db.close();
		}
		return dishes;
	}
	
	//...
	
	//TODO Implement
	/*
	 * + getXXX(): ...
	 * + setXXX(): ...
	 * + getCity(): City[1]
	 * + getVotes(): Vote[*]
	 * + getDishes(): Dish[*]
	 * + getCotation(): Integer[1]{0..5}
	 * + getRemainingSeats(): Integer[1]
	 * + newVote(user: User, note: Integer): void
	 * + hasUserPreference(user: User): Integer[1]{0..5}
	 * + newReservation(user: User, date: Date, beginHour: Hour, nbPeople: Integer): Reservation[0..1]
	 * + newDish(user: User, allergens, ...): Dish[0..1]
	 * + getPhotos(): Photo[*]
	 * + getRestaurator(): Restaurator[0..1]
     */
	
}
