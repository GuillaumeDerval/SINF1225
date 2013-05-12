package be.uclouvain.sinf1225.gourmet.models;

import java.util.List;

public class Restaurator extends User
{
	private List<Integer> restoIds;

	/**
	 * Init a restaurator
	 * 
	 * @param name
	 * @param surname
	 * @param email
	 * @param passwordHash
	 * @param restoIds
	 */
	public Restaurator(String name, String surname, String email, String passwordHash, List<Integer> restoIds)
	{
		super(name, surname, email, passwordHash);
		this.restoIds = restoIds;
	}

	/**
	 * Return if a user has or not the rights to manage a restaurant
	 * 
	 * @param resto
	 * @return true if the user has the rights.
	 */
	public boolean hasRightsForRestaurant(Restaurant resto)
	{
		return restoIds.contains(resto.getId());
	}

	/**
	 * Return list of restoIds
	 */
	public List<Integer> getRestoIds()
	{
		return restoIds;
	}
}
