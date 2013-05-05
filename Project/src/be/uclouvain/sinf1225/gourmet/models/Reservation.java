package be.uclouvain.sinf1225.gourmet.models;
import java.util.Date;
import java.util.List;

//REMARQUES : 
/* faire une table reservationDish avec les plats (+ le nombre souhait�) et le resvId de la r�servation */


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
	 * Modification du nombre de plats souhait�s
	 * @param dish plat � modifier
	 * @param nbr nombre souhait�
	 */
	public void setDishNodeNbr (Dish dish, int nbr)
	{
		for(DishNode node : this.dishes)
		{
			if(node.dish == dish) node.nbrDishes = nbr;
		}
	}
	
	/**
	 * Ajout d'un plat � la r�servation
	 * @param dish plat � ajouter
	 * @param nbr nombre d'exemplaire souhait�
	 */
	public void addDishNode (Dish dish, int nbr) {this.dishes.add(new DishNode(dish, nbr));}
	
	/**
	 * Suppression d'un plat command�
	 * @param dish plat � supprimer de la r�servation
	 */
	public void deleteDishNode (Dish dish)
	{
		for(DishNode node : this.dishes)
		{
			if (node.dish == dish) this.dishes.remove(node);			
		}
	}
}
