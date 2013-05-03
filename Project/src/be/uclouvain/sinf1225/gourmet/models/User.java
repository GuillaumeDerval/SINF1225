package be.uclouvain.sinf1225.gourmet.models;

import be.uclouvain.sinf1225.gourmet.utils.GourmetUtils;

public class User
{
	/*
	 * Static part
	 */
	
	/**
	 * Return values of function User.loginUser and User.registerUser
	 */
	public enum UserManagerReturn
	{
		LOGIN_OK,
		LOGIN_ERR,
		REGISTER_MAIL_ALREADY_TAKEN,
		NOT_FULL
	}
	
	private static User loggedUser = null;
	
	/**
	 * Return if a user is connected or not
	 * @return true if user is connected, else false.
	 */
	public static boolean isUserConnected()
	{
		return loggedUser != null;
	}
	
	/**
	 * Return current user
	 * @return current user, or null if there is not user connected
	 */
	public static User getUserConnected()
	{
		return loggedUser;
	}
	
	/**
	 * Log a user by checking if an email with this password exists in database.
	 * @param email
	 * @param password
	 * @return LOGIN_OK if success, other values of User.UserManagerReturn else.
	 */
	public static UserManagerReturn loginUser(String email, String password)
	{
		return loginUser(email, password, false);
	}
	
	/**
	 * Log a user by checking if an email with this password exists in database.
	 * @param email
	 * @param password
	 * @param isHash is password a hash?
	 * @return LOGIN_OK if success, other values of User.UserManagerReturn else.
	 */
	public static UserManagerReturn loginUser(String email, String password, boolean isHash)
	{
		if(email.equals("") || password.equals(""))
			return UserManagerReturn.NOT_FULL;
		
		GourmetDatabase db = new GourmetDatabase();
		loggedUser = db.getUser(email,password, isHash);
		db.close();
		
		return loggedUser == null ? UserManagerReturn.LOGIN_ERR : UserManagerReturn.LOGIN_OK;
	}
	
	/**
	 * Register a new user and log him on.
	 * @param name
	 * @param surname
	 * @param email
	 * @param password
	 * @return LOGIN_OK if success, other values of User.UserManagerReturn else.
	 */
	public static UserManagerReturn registerUser(String name, String surname, String email, String password)
	{
		if(name.equals("") || surname.equals("") || email.equals("") || password.equals(""))
			return UserManagerReturn.NOT_FULL;
		
		GourmetDatabase db = new GourmetDatabase();
		if(db.haveUserWithEmail(email))
		{
			db.close();
			return UserManagerReturn.REGISTER_MAIL_ALREADY_TAKEN;
		}
		
		loggedUser = new User(name, surname, email, GourmetUtils.sha1(password));
		db.addUser(loggedUser);
		db.close();
		return UserManagerReturn.LOGIN_OK;
	}
	
	/**
	 * Logout a user
	 */
	public static void logoutUser()
	{
		loggedUser = null;
	}
	
	/*
	 * Object part
	 */
	
	String name;
	String surname;
	String email;
	String passwordHash;
	
	/**
	 * Create a new user
	 * @param name
	 * @param surname
	 * @param email
	 * @param passwordHash
	 */
	public User(String name, String surname, String email, String passwordHash)
	{
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.passwordHash = passwordHash;
	}
	
	/**
	 * Return name of the current user
	 * @return name of the current user
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Return surname of the current user
	 * @return surname of the current user
	 */
	public String getSurname()
	{
		return surname;
	}
	
	/**
	 * Return complete name of the current user. Equals to getName() + " " + getSurname()
	 * @return complete name of the current user.
	 */
	public String getCompleteName()
	{
		return name + " " + surname;
	}
	
	/**
	 * Return SHA1 hash of the user's password
	 * @return SHA1 hash of the user's password
	 */
	public String getPasswordHash()
	{
		return passwordHash;
	}
	
	/**
	 * Return email of the user
	 * @return email of the user
	 */
	public String getEmail()
	{
		return email;
	}
	
	/**
	 * Change email of the user.
	 * @param email
	 * @return true if the change was successful, else false(new email is already in database).
	 */
	public boolean setEmail(String email)
	{
		GourmetDatabase db = new GourmetDatabase();
		if(db.haveUserWithEmail(email))
			return false;
		db.close();
		this.email = email;
		return true;
	}
	
	/**
	 * Change password of the user
	 * @param password
	 */
	public void setPassword(String password)
	{
		this.passwordHash = GourmetUtils.sha1(password);
	}
	
	/**
	 * Change name and surname of the user
	 * @param name
	 * @param surname
	 */
	public void changeNameAndSurname(String name, String surname)
	{
		this.name = name;
		this.surname = surname;
	}
	
	/**
	 * Commit changes to the database
	 */
	public void saveToDB()
	{
		GourmetDatabase db = new GourmetDatabase();
		db.updateUser(this);
		db.close();
	}
}
