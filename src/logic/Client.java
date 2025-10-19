package logic;

/**
 * Class for casino clients (customers).
 * @author David Forero
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
	 * Minimum constructor for playing, only the name, balance, and id will be needed.
	 * @param id Client ID
	 * @param name Client name
	 * @param balance Client balance
	 * @since 2.0
	 */
	public Client(int id, String name, double balance) {
		this.id = id;
		this.name = name;
		this.balance = balance;
	}

	
	/**
	 * Constructor for clients.
	 * @param name Client's full name (first and last name)
	 * @param age Client's age (with established valid age range)
	 * @param gender Client's gender
	 * @param balance Client's balance
	 * @since 2.0
	 */
	public Client(String name, int age, char gender, double balance) {
		this.name = name;
		this.age = age;
		this.gender = gender;
		active = true;
		this.balance = balance;
	}
	
	/**
	 * Complete constructor (to be edited) for clients.
	 * @param name Client's full name (first and last name)
	 * @param age Client's age (with established valid age range)
	 * @param gender Client's gender
	 * @param balance Client's balance
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


	public int getAge() {
		return age;
	}


	public char getGender() {
		return gender;
	}


	public int getId() {
		return id;
	}


	public String getName() {
		return name;
	}


	public double getBalance() {
		return balance;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public void setGender(char gender) {
		this.gender = gender;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setBalance(double balance) {
		this.balance = balance;
	}
}