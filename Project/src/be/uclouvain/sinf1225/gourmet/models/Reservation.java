package be.uclouvain.sinf1225.gourmet.models;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//REMARQUES : 
/* faire une table reservationDish avec les plats (+ le nombre souhaité) et le resvId de la réservation */


//TODO Documentation (in english, of course).
/**
 * Represents a Reservation
 * @author Alexandre Laterre
 */
public class Reservation
{
	public class DishNode
	{
		public Dish dish;
		public int nbrDishes;
		
		public DishNode (Dish dish, int nbrDishes)
		{
			this.dish = dish;
			this.nbrDishes = nbrDishes;
		}
	}
	
	private int id;
	private String userEmail;
	private Restaurant restaurant;
	private int nbrReservation;
	private List<DishNode> dishes;
	private Date date;
	
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
		this.dishes = new ArrayList<DishNode>();
		this.date = date;
		this.updateReservation(); //will set id.
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
	public Reservation(int id, String userEmail, Restaurant restaurant, int nbrReservation, List<DishNode> dishes, Date date)
	{
		this.id = id;
		this.userEmail = userEmail;
		this.restaurant = restaurant;
		this.nbrReservation = nbrReservation;
		this.dishes = dishes;
		this.date = date;
	}
	
	/* Lecture des variables d'instances*/
	public int getId() { return this.id; }
	public String getUserEmail() {return this.userEmail;}
	public Restaurant getRestaurant () {return this.restaurant;}
	public int getnbrReservation() {return this.nbrReservation;}
	public List<DishNode> getDish() {return this.dishes;}
	public Date getDate() {return this.date;}
	
	/* Modification des variables d'instances */
	public void setRestaurant (Restaurant resto) {this.restaurant = resto;}
	public void setNbrReservation (int nbr) {this.nbrReservation = nbr;}
	public void setDate (Date date) {this.date = date;}
	
	/**
	 * Modification du nombre de plats souhaités
	 * @param dish plat à modifier
	 * @param nbr nombre souhaité
	 */
	public void setDishNodeNbr (Dish dish, int nbr)
	{
		for(DishNode node : this.dishes)
			if(node.dish == dish) 
				node.nbrDishes = nbr;
	}
	
	/**
	 * Ajout d'un plat à la réservation
	 * @param dish plat à ajouter
	 * @param nbr nombre d'exemplaire souhaité
	 */
	public void addDishNode (Dish dish, int nbr)
	{
		dishes.add(new DishNode(dish, nbr));
	}
	
	/**
	 * Suppression d'un plat commandé
	 * @param dish plat à supprimer de la réservation
	 */
	public void deleteDishNode (Dish dish)
	{
		for(DishNode node : this.dishes)
		{
			if (node.dish == dish) this.dishes.remove(node);			
		}
	}
	
	/**
	 * Get reservation from database
	 * @param userEmail
	 * @param restaurantId
	 * @param date
	 * @return
	 */
	public static Reservation getReservation(String userEmail, int restaurantId, Date date)
	{
		GourmetDatabase db = new GourmetDatabase();
		Reservation resv = db.getReservation(userEmail, restaurantId, date);
		db.close();
		return resv;
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
}
