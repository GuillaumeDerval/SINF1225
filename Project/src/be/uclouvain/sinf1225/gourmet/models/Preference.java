package be.uclouvain.sinf1225.gourmet.models;

public class Preference {
	private String userEmail;
	private int budget; // 1 is the cheapest, 6 the most expensive
	private boolean allergen;
	private boolean spicy;
	private boolean vegeterian;
	
	public Preference(String userEmail, int budget, boolean allergen, boolean spicy, boolean vegeterian){
		this.userEmail = userEmail;
		this.budget = budget;
		this.allergen = allergen;
		this.spicy = spicy;
		this.vegeterian = vegeterian;
	}
	
	public static void addPreference(Preference preference){
			GourmetDatabase db = new GourmetDatabase();
			db.addPreference(preference);
			db.close();
			System.out.println("You added a preference");
	}
	
	public static void updatePreference(Preference pref){
		GourmetDatabase db = new GourmetDatabase();
		db.updatePreference(pref);
		db.close();
		System.out.println("You updated a preference");
	}
	
	public static boolean isTherePref(String email){
		GourmetDatabase db = new GourmetDatabase();
		boolean isIt = db.isTherePref(email);
		db.close();
		return isIt;
	}
	
	public static Preference getPrefByUserEmail(String userEmail){
		GourmetDatabase db = new GourmetDatabase();
		Preference pref = db.getPrefByUserEmail(userEmail);
		db.close();
		return pref;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public int getBudget() {
		return budget;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	public boolean isAllergen() {
		return allergen;
	}

	public void setAllergen(boolean allergen) {
		this.allergen = allergen;
	}

	public boolean isSpicy() {
		return spicy;
	}

	public void setSpicy(boolean spicy) {
		this.spicy = spicy;
	}

	public boolean isVegeterian() {
		return vegeterian;
	}

	public void setVegeterian(boolean vegeterian) {
		this.vegeterian = vegeterian;
	}
	
	public String toString(){
		return "Budget = "+budget+", allergen = "+allergen+", vegeterian = "+vegeterian+", spicy = "+spicy;
	}
}
