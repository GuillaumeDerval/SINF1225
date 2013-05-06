package be.uclouvain.sinf1225.gourmet.models;
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
	private String userEmail;
	private Restaurant restaurant;
	private int nbrReservation;
	private List<DishNode> dishes;
	private Date date;
	
	public Reservation(String userEmail, Restaurant restaurant, int nbrReservation, List<DishNode> dishes, Date date)
	{
		this.userEmail = userEmail;
		this.restaurant = restaurant;
		this.nbrReservation = nbrReservation;
		this.dishes = dishes;
		this.date = date;
	}
	
	/* Lecture des variables d'instances*/
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
		{
			if(node.dish == dish) node.nbrDishes = nbr;
		}
	}
	
	/**
	 * Ajout d'un plat à la réservation
	 * @param dish plat à ajouter
	 * @param nbr nombre d'exemplaire souhaité
	 */
	public void addDishNode (Dish dish, int nbr) {this.dishes.add(new DishNode(dish, nbr));}
	
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
	
	public static int addReservation(int restoID, String email, int nbr, Date date)
	{
		GourmetDatabase db = new GourmetDatabase();
		int i = db.addReservation(restoID, email, nbr, date);
		db.close();
		return i;
	}
	public static int deleteReservation(int resvID)
	{
		GourmetDatabase db = new GourmetDatabase();
		int i = db.deleteReservation(resvID);
		db.close();
		return i;
	}
	public static int getResvID(String email, int restoID, Date date)
	{
		GourmetDatabase db = new GourmetDatabase();
		int i = db.getResvID(email, restoID, date);
		db.close();
		return i;
	}
	public static int addReservationDish(int resvID, String dish, int nbr)
	{
		GourmetDatabase db = new GourmetDatabase();
		int i = db.addReservationDish(resvID, dish, nbr);
		db.close();
		return i;
	}
	public static int deleteResvervationDish (int resvID, String dish, int nbr)
	{
		GourmetDatabase db = new GourmetDatabase();
		int i = db.deleteResvervationDish(resvID, dish, nbr);
		db.close();
		return i;
	}
}
