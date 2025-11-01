package model;

/**
 * Abstract class representing a casino game. Provides a general structure for
 * all games, including common attributes and behavior. Each concrete game
 * (e.g., Blackjack, Slotmachine) will extend this class and implement its own
 * logic.
 * 
 * @author Davitroon
 * @since 2.0
 */
public abstract class Game {

	private int id;
	private String type;
	private boolean active = true;
	private double money;

	/**
	 * Constructor for creating a new game with a specific type and starting money.
	 * The game is active by default.
	 * 
	 * @param money Initial amount of money associated with the game.
	 * @param type  Type of the game (e.g., "Blackjack", "Slotmachine").
	 * @since 2.0
	 */
	public Game(double money, String type) {
		this.money = money;
		this.type = type;
	}

	/**
	 * Constructor for creating a game with a specific ID, type, and money. Useful
	 * for loading existing games from a database.
	 * 
	 * @param id    Unique identifier for the game.
	 * @param money Amount of money associated with the game.
	 * @param type  Type of the game.
	 * @since 2.0
	 */
	public Game(int id, double money, String type) {
		this.id = id;
		this.money = money;
		this.type = type;
	}

	/**
	 * Complete constructor for a game with all attributes specified.
	 * 
	 * @param id     Unique identifier for the game.
	 * @param type   Type of the game.
	 * @param active Whether the game is active or not.
	 * @param money  Amount of money associated with the game.
	 * @since 2.0
	 */
	public Game(int id, String type, boolean active, double money) {
		this.id = id;
		this.money = money;
		this.active = active;
		this.type = type;
	}

	/**
	 * Gets the amount of money currently associated with the game.
	 * 
	 * @return Current game money.
	 */
	public double getMoney() {
		return money;
	}

	/**
	 * Gets the unique identifier of the game.
	 * 
	 * @return Game ID.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the type of the game.
	 * 
	 * @return Game type (e.g., "Blackjack").
	 */
	public String getType() {
		return type;
	}

	/**
	 * Checks if the game is currently active.
	 * 
	 * @return true if active, false otherwise.
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Abstract method to play the game. Each concrete game must implement this
	 * method to define its own rules for calculating winnings or losses based on
	 * the player's bet.
	 * 
	 * @param bet Amount of the player's bet.
	 * @return Modified bet: positive for winnings, negative for losses, or
	 *         same/zero for a push.
	 * @since 2.0
	 */
	public abstract double play(double bet);

	/**
	 * Sets the active status of the game.
	 * 
	 * @param active true to mark the game as active, false to mark it as inactive.
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Sets the amount of money associated with the game.
	 * 
	 * @param money New money amount.
	 */
	public void setMoney(double money) {
		this.money = money;
	}

}
