package be.uclouvain.sinf1225.gourmet.models;

import java.util.List;

import android.location.Location;
import be.uclouvain.sinf1225.gourmet.enums.PriceCategory;

public class Restaurant
{
	//TODO Complete.
	private String name;
	private String address;
	private PriceCategory priceCategory;
	private Location location;
	private String phone;
	private int seats;
	private String website; 
	private String description;
	private int stars;
	private List<Reservation> reservations;
	private List<Dish> dishes;
	private int id;
	private String City;
	private String email;
	private int priceCate;
	
	
	public static Restaurant getRestaurant(int restoId)
	{
		GourmetDatabase db = new GourmetDatabase();
		Restaurant resto = db.getRestaurant(restoId);
		db.close();
		return resto;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public PriceCategory getPriceCategory() {
		return priceCategory;
	}
	public void setPriceCategory(PriceCategory priceCategory) {
		this.priceCategory = priceCategory;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getSeats() {
		return seats;
	}
	public void setSeats(int seats) {
		this.seats = seats;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getStars() {
		return stars;
	}
	public void setStars(int stars) {
		this.stars = stars;
	}
	public List<Reservation> getReservations() {
		return reservations;
	}
	public int getPriceCate() {
		return priceCate;
	}
	public void setPriceCate(int priceCate) {
		this.priceCate = priceCate;
	}
	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
	public List<Dish> getDishes() {
		return dishes;
	}
	public void setDishes(List<Dish> dishes) {
		this.dishes = dishes;
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
