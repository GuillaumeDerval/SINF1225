package be.uclouvain.sinf1225.gourmet.models;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Reservation
 * @author Alexandre Laterre
 */
public class Reservation
{	
	private int id;
	private String userEmail;
	private Restaurant restaurant;
	private int nbrReservation;
	private List<Integer> dishes;
	private Date date;
	private String dateText;
	
	/**
	 * Create a new reservation, to be saved to database
	 * @param userEmail
	 * @param restaurant
	 * @param nbrReservation
	 * @param date
	 */
	public Reservation(String userEmail, Restaurant restaurant, int nbrReservation, Date date)
	{
		this.id = -1;
		this.userEmail = userEmail;
		this.restaurant = restaurant;
		this.nbrReservation = nbrReservation;
		this.dishes = new ArrayList<Integer>();
		this.date = date;
	}
	
	/**
	 * Create a new reservation, from database.
	 * @param id
	 * @param userEmail
	 * @param restaurant
	 * @param nbrReservation
	 * @param dishes
	 * @param date
	 */
	public Reservation(int id, String userEmail, Restaurant restaurant, int nbrReservation, List<Integer> dishes, Date date)
	{
		this.id = id;
		this.userEmail = userEmail;
		this.restaurant = restaurant;
		this.nbrReservation = nbrReservation;
		this.dishes = dishes;
		this.date = date;
	}
	
	/**
	 * Create a new reservation, from database.
	 * @param id
	 * @param userEmail
	 * @param restaurant
	 * @param nbrReservation
	 * @param dishes
	 * @param dateText
	 */
	public Reservation(int id, String userEmail, Restaurant restaurant, int nbrReservation, List<Integer> dishes, String dateText)
	{
		this.id = id;
		this.userEmail = userEmail;
		this.restaurant = restaurant;
		this.nbrReservation = nbrReservation;
		this.dishes = dishes;
		this.dateText = dateText;
	}
	
	/* Lecture des variables d'instances*/
	public int getId() { return this.id; }
	public String getUserEmail() {return this.userEmail;}
	public Restaurant getRestaurant () {return this.restaurant;}
	public int getnbrReservation() {return this.nbrReservation;}
	public List<Integer> getDish() {return this.dishes;}
	public Date getDate() {return this.date;}
	public String getDateText() {return this.dateText;}
	
	/* Modification des variables d'instances */
	public void setRestaurant (Restaurant resto) {this.restaurant = resto;}
	public void setNbrReservation (int nbr) {this.nbrReservation = nbr;}
	public void setDate (Date date) {this.date = date;}
	public void setId (int id) {this.id = id;}
	public void setDishes (List<Integer> dishesId) {this.dishes = dishesId;}
	
	/**
	 * Ajout d'un plat à la réservation
	 * @param dish plat à ajouter
	 * @param nbr nombre d'exemplaire souhaité
	 */
	public void addDish (int dishId)
	{
		dishes.add(dishId);
	}

	/**
	 * Suppression d'un plat commandé
	 * @param dish plat à supprimer de la réservation
	 */
	public void delete (int dishId)
	{
		for(int id : this.dishes)
		{ 
			if (id == dishId) this.dishes.remove(id);
		}
	}

	/********************* DATABASE *********************/
	
	
	public static int addReservation(Reservation reservation)
	{
		GourmetDatabase db = new GourmetDatabase();
		int ans = db.addReservation(reservation);
		db.close();
	    return ans;
	}
	
	/**
	 * Get reservation from database
	 * @param userEmail
	 * @param restaurantId
	 * @param date
	 * @return Reservation object if it exists, null else
	 */
	public static Reservation getReservation(String userEmail, int restaurantId, Date date)
	{
		GourmetDatabase db = new GourmetDatabase();
		Reservation resv = db.getReservation(userEmail, restaurantId, date);
		db.close();
		return resv;
	}
	
	public static List<Reservation> getReservationByUser(User user){
		GourmetDatabase db = new GourmetDatabase();
		List<Reservation> reservations = db.getReservationByUser(user);
		db.close();
		return reservations;
	}
	
	/**
	 * Update this reservation in database
	 */
	public void updateReservation()
	{
		GourmetDatabase db = new GourmetDatabase();
		if(id == -1) //add
		{
			id = db.addReservation(this);
		}
		else //update
		{
			db.updateReservation(this);
		}
		db.close();
	}
	
	/**
	 * Delete reservation from database
	 */
	public void deleteReservation()
	{
		GourmetDatabase db = new GourmetDatabase();
		db.deleteReservation(this);
		db.close();
	}
	
	public static Reservation getReservation(int resvId)
	{
		GourmetDatabase db = new GourmetDatabase();
		Reservation resv = db.getReservation(resvId);
		db.close();
		return resv;
	}
	
	public String toString(){
		String date = this.date != null ? this.date.toString() : this.dateText;
		return "DATE = "+date+", DISHES = toStringMissing, ID ="+id+", NBRRESERVATION = "+nbrReservation+", Restaurant =" + restaurant.getName() + ", USERMAIL = "+userEmail;
	}
}
