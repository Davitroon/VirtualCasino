package model;

/**
 * Represents a Slot Machine game, child of the {@link Game} class.
 * <p>
 * Simulates the behavior of a real slot machine, with three slots that generate
 * random numbers between 1 and 9. The payout depends on the combination of the
 * numbers obtained.
 * </p>
 * 
 * @author Davitroon
 * @since 2.0
 */
public class Slotmachine extends Game {

	/** Array representing the three slots of the machine. */
	private int[] numbers = new int[3];

	/**
	 * Constructor for creating a Slot Machine with a specific money pool.
	 * 
	 * @param money Money available in the game for bets.
	 * @since 2.0
	 */
	public Slotmachine(double money) {
		super(money, "SlotMachine");
	}

	/**
	 * Constructor for creating a Slot Machine with an ID and money pool, typically
	 * used when loading saved games.
	 * 
	 * @param id    Game ID
	 * @param money Money available in the game for bets
	 * @since 3.0
	 */
	public Slotmachine(int id, double money) {
		super(id, money, "SlotMachine");
	}

	/**
	 * Complete constructor for the Slot Machine, allowing full initialization.
	 * 
	 * @param id     Game ID
	 * @param type   Game type
	 * @param active Game status (active or inactive)
	 * @param money  Money available in the game for bets
	 * @since 2.0
	 */
	public Slotmachine(int id, String type, boolean active, double money) {
		super(id, type, active, money);
	}

	/**
	 * Fills the three slots with random numbers between 1 and 9 (inclusive).
	 * <p>
	 * This simulates a spin of the slot machine.
	 * </p>
	 * 
	 * @since 2.0
	 */
	public void generateNumbers() {
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = (int) Math.round(Math.random() * 8) + 1;
		}
	}

	/**
	 * Returns the array of numbers currently in the slots.
	 * 
	 * @return An array of 3 integers representing the slot results.
	 */
	public int[] getNumbers() {
		return numbers;
	}

	/**
	 * Calculates the result of a bet based on the numbers in the slots.
	 * <p>
	 * Payout rules:
	 * <ul>
	 * <li>No matching numbers: player loses the bet.</li>
	 * <li>Two matching numbers: bet multiplied by 1.9.</li>
	 * <li>Three matching numbers: bet multiplied by 3.5.</li>
	 * <li>Three sevens: bet multiplied by 6.5 (jackpot).</li>
	 * </ul>
	 * </p>
	 * 
	 * @param bet The amount of money bet by the player.
	 * @return The payout based on the slot result. Negative if the player loses.
	 */
	@Override
	public double play(double bet) {
		int n1 = numbers[0], n2 = numbers[1], n3 = numbers[2];

		if (n1 == 7 && n2 == 7 && n3 == 7) {
			return bet * 6.50;
		}

		if (n1 == n2 && n1 == n3) {
			return bet * 3.5;
		}

		if (n1 == n2 || n1 == n3 || n2 == n3) {
			return bet * 1.9;
		}

		return -bet;
	}

}