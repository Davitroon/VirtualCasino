package model;

/**
 * Slot machine game, child of the Game class. Simulates the real operation of a
 * slot machine.
 * 
 * @author David Forero
 * @since 2.0
 */
public class Slotmachine extends Game {

	int[] numbers = new int[3];

	/**
	 * Constructor for the slot machine, sends its type to the Game constructor.
	 * 
	 * @param money Money that the game will count for bets.
	 * @since 2.0
	 */
	public Slotmachine(double money) {
		super(money, "SlotMachine");
	}

	/**
	 * Slot machine constructor for necessary data (to save in the game).
	 * 
	 * @param id    Game ID
	 * @param money Game money
	 * @since 3.0
	 */
	public Slotmachine(int id, double money) {
		super(id, money, "SlotMachine");
	}

	/**
	 * Complete constructor (to be edited) for the slot machine, sends parameters to
	 * the Game constructor.
	 * 
	 * @param id     Game ID
	 * @param type   Game type
	 * @param active Game status
	 * @param money  Game money
	 * @since 2.0
	 */
	public Slotmachine(int id, String type, boolean active, double money) {
		super(id, type, active, money);
	}

	/**
	 * Method that fills the 3 slots with a random number from 1 to 9.
	 * 
	 * @since 2.0
	 */
	public void generateNumbers() {

		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = (int) Math.round(Math.random() * 8) + 1;
		}
	}

	public int[] getNumbers() {
		return numbers;
	}

	/**
	 * Checks the number of repeated slots (numbers).<br>
	 * - If there are none, the bet is lost.<br>
	 * - If there are 2, the bet is multiplied by 1.9.<br>
	 * - If there are 3, the bet is multiplied by 3.5.<br>
	 * - If there are three sevens, the bet is multiplied by 6.5.<br>
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