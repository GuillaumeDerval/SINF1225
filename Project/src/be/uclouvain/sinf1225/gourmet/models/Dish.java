package be.uclouvain.sinf1225.gourmet.models;
public class Dish
{
	//TODO implement.
	private int dishId;
	private String name;
	private int restoId;
	private String description;
	private double price;
	private int spicy;
	private int vegan;
	private int available;
	private int allergen;
	private String category;
	private Restaurant restaurant;
	private Image img; // 1 dish = 1 picture
	/**
	 * 
	 * @param dishId
	 * @param name
	 * @param restoId
	 * @param description
	 * @param price
	 * @param spicy
	 * @param vegan
	 * @param available
	 * @param allergen
	 * @param category
	 * @param restaurant
	 * @param img
	 */
	public Dish(int dishId, String name, int restoId, String description,
			double price, int spicy, int vegan, int available, int allergen,
			String category, Restaurant restaurant, Image img) 
	{
		super();
		this.dishId = dishId;
		this.name = name;
		this.restoId = restoId;
		this.description = description;
		this.price = price;
		this.spicy = spicy;
		this.vegan = vegan;
		this.available = available;
		this.allergen = allergen;
		this.category = category;
		this.restaurant = restaurant;
		this.img = img;
	}
	public Dish() 
	{
		super();
	}
	/**
	 * Delete dish in database
	 * @param dish
	 */
	public void deleteDish( Dish dish)
	{
		GourmetDatabase db = new GourmetDatabase();
		db.deleteDish(dish);
		db.close();
	}
	/**
	 * update dish in data base
	 * @param dish
	 */
	public void updateDish( Dish dish)
	{
		GourmetDatabase db = new GourmetDatabase();
		db.updateDish(dish);
		db.close();
		
	}
	/**
	 * get dish in database
	 * @param dishId
	 * @return dish object if it exists.
	 */
	public static Dish getDish(int dishId)
	{
		GourmetDatabase db = new GourmetDatabase();
		Dish dish = db.getDish(dishId);
		db.close();
		return dish;
	}
	public String getCategory() 
	{
		return category;
	}
	public void setCategory(String category) 
	{
		this.category = category;
	}
	public String getDescription() 
	{
		return description;
	}
	public void setDescription(String description) 
	{
		this.description = description;
	}
	public double getPrice() 
	{
		return price;
	}
	public void setPrice(double price) 
	{
		this.price = price;
	}
	public int getSpicy() 
	{
		return spicy;
	}
	public void setSpicy(int spicy) 
	{
		this.spicy = spicy;
	}
	public int getVegan() 
	{
		return vegan;
	}
	public void setVegan(int vegan) 
	{
		this.vegan = vegan;
	}
	public int getAvailable() 
	{
		return available;
	}
	public void setAvailable(int available) 
	{
		this.available = available;
	}
	public int getAllergen() 
	{
		return allergen;
	}
	public void setAllergen(int allergen) 
	{
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
	public Restaurant getRestaurant() 
	{
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) 
	{
		this.restaurant = restaurant;
	}
	public int getDishId() 
	{
		return dishId;
	}
	public void setDishId(int dishId) 
	{
		this.dishId = dishId;
	}
	public Image getImg() 
	{
		return img;
	}
	public void setImg(Image img) 
	{
		this.img = img;
	}
	public int getRestoId()
	{
		return restoId;
	}
}
