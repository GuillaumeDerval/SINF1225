package be.uclouvain.sinf1225.gourmet.models;

public class Preference
{
	private String userEmail;
	private int budget; // 1 is the cheapest, 6 the most expensive
	private String allergens;
	private boolean vegeterian;

	public Preference(String userEmail, int budget, String allergen, boolean vegeterian)
	{
		this.userEmail = userEmail;
		this.budget = budget;
		this.allergens = allergen;
		this.vegeterian = vegeterian;
	}

	public static void addPreference(Preference preference)
	{
		GourmetDatabase db = new GourmetDatabase();
		db.addPreference(preference);
		db.close();
	}

	public static void updatePreference(Preference pref)
	{
		GourmetDatabase db = new GourmetDatabase();
		db.updatePreference(pref);
		db.close();
	}

	public static boolean isTherePref(String email)
	{
		GourmetDatabase db = new GourmetDatabase();
		boolean isIt = db.isTherePref(email);
		db.close();
		return isIt;
	}

	public static Preference getPrefByUserEmail(String userEmail)
	{
		GourmetDatabase db = new GourmetDatabase();
		Preference pref = db.getPrefByUserEmail(userEmail);
		db.close();
		return pref;
	}

	public String getUserEmail()
	{
		return userEmail;
	}

	public void setUserEmail(String userEmail)
	{
		this.userEmail = userEmail;
	}

	public int getBudget()
	{
		return budget;
	}

	public void setBudget(int budget)
	{
		this.budget = budget;
	}

	public String getAllergensText()
	{
		return this.allergens;
	}

	public String[] getAllergens()
	{
		if (this.allergens.equals(""))
			return new String[] {};
		return this.allergens.split(",");
	}

	public void setAllergens(String allergens)
	{
		this.allergens = allergens;
	}

	public boolean isVegeterian()
	{
		return vegeterian;
	}

	public void setVegeterian(boolean vegeterian)
	{
		this.vegeterian = vegeterian;
	}
}
