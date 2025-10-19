package logic;

/**
 * Abstract class that provides the format for games.
 * Each game will have its own logic, methods, and attributes.
 * @author David Forero
 * @since 2.0
 */
public abstract class Game {
	
	private int id;
	private String type;
	private boolean active = true;
	private double money;
	
	
	/**
	 * Constructor for the games.
	 * @param money Money that the game will count for bets
	 * @param type Game type
	 * @since 2.0
	 */
	public Game(double money, String type) {
		this.money = money;
		this.type = type;
	}
	
	
	/**
	 * Minimum constructor for playing, only the money and the id will be needed.
	 * @param id Game ID
	 * @param money Game money
	 * @param type Game type
	 */
	public Game(int id, double money, String type) {
		this.id = id;
		this.money = money;
		this.type = type;
	}

	
	/**
	 * Complete constructor (to be edited) for games.
	 * @param id Game ID
	 * @param type Game type
	 * @param active Game status
	 * @param money Money that the game will count for bets
	 * @since 2.0
	 */
	public Game(int id, String type, boolean active, double money) {
		this.id = id;
		this.money = money;
		this.active = active;
		this.type = type;
	}


	public double getMoney() {
		return money;
	}
	
	
	public int getId() {
		return id;
	}


	public String getType() {
		return type;
	}


	public boolean isActive() {
		return active;
	}


	/**
	 * Method for playing the game. Each game type calculates the bet modification based on its own rules, depending on whether the client wins or loses.
	 * @param bet Client's bet.
	 * @return Modified bet (positive for winnings, negative for losses, zero/same for push).
	 * @since 2.0
	 */
	public abstract double play(double bet);
	
	
	public void setActive(boolean active) {
		this.active = active;
	}


	public void setMoney(double money) {
		this.money = money;
	}
	
}