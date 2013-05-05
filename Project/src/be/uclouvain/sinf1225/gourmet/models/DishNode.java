package be.uclouvain.sinf1225.gourmet.models;


/**
 * Structure associant à chaque plat réservé, le nombre "d'exemplaires" souhaité
 */

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