package be.uclouvain.sinf1225.gourmet.models;

import android.location.Location;
import be.uclouvain.sinf1225.gourmet.enums.PriceCategory;

public class Restaurant
{
	//TODO Complete.
	private String address;
	private PriceCategory priceCategory;
	private Location location;
	private String phone;
	private int seats;
	private String website;
	private String name;
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
	public Location getLocation() { return location;}
	public String getName() {return this.name;}
}
