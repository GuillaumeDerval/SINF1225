package be.uclouvain.sinf1225.gourmet.models;


/**
 * Structure associant � chaque plat r�serv�, le nombre "d'exemplaires" souhait�
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