package model;

/**
 * Represents a client (customer) in the casino system. Stores personal
 * information and account balance.
 * 
 * @author Davitroon
 * @since 2.0
 */
public class Client {

	private int id;
	private String name;
	private int age;
	private char gender;
	private boolean active;
	private double balance;

	/**
	 * Minimum constructor for creating a client for playing purposes. Only the ID,
	 * name, and balance are required.
	 * 
	 * @param id      Unique client ID
	 * @param name    Client's name
	 * @param balance Client's current balance
	 * @since 2.0
	 */
	public Client(int id, String name, double balance) {
		this.id = id;
		this.name = name;
		this.balance = balance;
	}

	/**
	 * Constructor for creating a new client with full information. The client is
	 * marked as active by default.
	 * 
	 * @param name    Client's full name
	 * @param age     Client's age
	 * @param gender  Client's gender (e.g., 'M', 'F', etc.)
	 * @param balance Client's initial balance
	 * @since 2.0
	 */
	public Client(String name, int age, char gender, double balance) {
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.active = true;
		this.balance = balance;
	}

	/**
	 * Complete constructor for a client with all fields specified.
	 * 
	 * @param name    Client's full name
	 * @param age     Client's age
	 * @param gender  Client's gender
	 * @param balance Client's balance
	 * @param id      Client's unique ID
	 * @param active  Whether the client is active or inactive
	 * @since 2.0
	 */
	public Client(String name, int age, char gender, double balance, int id, boolean active) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.active = active;
		this.balance = balance;
	}

	/**
	 * Gets the client's age.
	 * 
	 * @return Age of the client
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Gets the client's gender.
	 * 
	 * @return Gender character of the client
	 */
	public char getGender() {
		return gender;
	}

	/**
	 * Gets the client's unique ID.
	 * 
	 * @return Client ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the client's name.
	 * 
	 * @return Client's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the client's current balance.
	 * 
	 * @return Client's balance
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * Checks if the client is active.
	 * 
	 * @return true if active, false otherwise
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets the client's active status.
	 * 
	 * @param active true to mark active, false to mark inactive
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Sets the client's age.
	 * 
	 * @param age Client's age
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * Sets the client's gender.
	 * 
	 * @param gender Client's gender character
	 */
	public void setGender(char gender) {
		this.gender = gender;
	}

	/**
	 * Sets the client's name.
	 * 
	 * @param name Client's full name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the client's balance.
	 * 
	 * @param balance New balance of the client
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}
}
