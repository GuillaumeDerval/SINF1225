package be.uclouvain.sinf1225.gourmet.models;

public class User
{
	public enum UserManagerReturn
	{
		LOGIN_OK,
		LOGIN_ERR,
		REGISTER_MAIL_ALREADY_TAKEN,
		NOT_FULL
	}
	
	private static User loggedUser = null;
	
	public static boolean isUserConnected()
	{
		return loggedUser != null;
	}
	
	public static User getUserConnected()
	{
		return loggedUser;
	}
	
	public static UserManagerReturn loginUser(String email, String password)
	{
		//TODO implement.
		return UserManagerReturn.LOGIN_ERR;
	}
	
	public static UserManagerReturn registerUser(String name, String surname, String email, String password)
	{
		//TODO implement.
		return UserManagerReturn.REGISTER_MAIL_ALREADY_TAKEN;
	}
	
	public static void logoutUser()
	{
		loggedUser = null;
	}
	
	public String getName() { return null; } //TODO implement.
}
