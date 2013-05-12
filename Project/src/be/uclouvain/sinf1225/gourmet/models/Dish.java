package be.uclouvain.sinf1225.gourmet.models;

public class Dish
{
	// TODO implement.
	private int dishId;
	private String name;
	private int restoId;
	private String description;
	private double price;
	private int spicy;
	private int vegan;
	private int available;
	private String allergens;
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
	public Dish(int dishId, String name, int restoId, String description, double price, int spicy, int vegan, int available, String allergens, String category, Restaurant restaurant, Image img)
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
		this.allergens = allergens;
		this.category = category;
		this.restaurant = restaurant;
		this.img = img;
	}
	
	public Dish()
	{
		//TODO Delete this. This is an awful technique to economize lines in AddDishView. Bad.
	}
	
	/**
	 * Delete dish in database
	 */
	
	public void addDish()
	{
		GourmetDatabase db = new GourmetDatabase();
		db.addDish(this);
		db.close();
	}
	public void deleteDish()
	{
		GourmetDatabase db = new GourmetDatabase();
		db.deleteDish(this);
		db.close();
	}

	/**
	 * update dish in database
	 */
	public void updateDish()
	{
		GourmetDatabase db = new GourmetDatabase();
		db.updateDish(this);
		db.close();
	}

	/**
	 * get dish in database
	 * 
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
	public static Dish getDish(String name,int restoId)
	{
		GourmetDatabase db = new GourmetDatabase();
		Dish dish = db.getDish(name, restoId);
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

	public String getAllergensText()
	{
		return allergens;
	}

	public String[] getAllergens()
	{
		return this.allergens.split(",");
	}
	
	public void setAllergen(String allergens)
	{
		this.allergens = allergens;
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

	public void setRestoId(int restoId)
	{
		this.restoId = restoId;
		this.restaurant = Restaurant.getRestaurant(restoId);
	}
}
