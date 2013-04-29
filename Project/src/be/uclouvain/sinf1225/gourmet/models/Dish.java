package be.uclouvain.sinf1225.gourmet.models;

public class Dish
{
	//TODO implement.
	
	private String name;
	private String category;
	private String description;
	private double price;
	private int spicy;
	private int vegan;
	private int available;
	private int allergen;
	private Restaurant restaurant;
	private int dishId;
	
	public static Dish getDish(int dishId)
	{
		GourmetDatabase db = new GourmetDatabase();
		Dish dish = db.getDish(dishId);
		db.close();
		return dish;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getSpicy() {
		return spicy;
	}
	public void setSpicy(int spicy) {
		this.spicy = spicy;
	}
	public int getVegan() {
		return vegan;
	}
	public void setVegan(int vegan) {
		this.vegan = vegan;
	}
	public int getAvailable() {
		return available;
	}
	public void setAvailable(int available) {
		this.available = available;
	}
	public int getAllergen() {
		return allergen;
	}
	public void setAllergen(int allergen) {
		this.allergen = allergen;
	}
	public String getName() 
	{
		return this.name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	public int getDishId() {
		return dishId;
	}
	public void setDishId(int dishId) {
		this.dishId = dishId;
	}
	
	
	
}
