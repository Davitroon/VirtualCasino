package controller;

import javax.swing.JLabel;

import exceptions.BetException;

/**
 * Class responsible for validating input and output data in the application.
 * <p>
 * This class handles validations for:
 * <ul>
 *   <li>Bets and minimum balances</li>
 *   <li>Client balances and game money pools</li>
 *   <li>Client information (name, age)</li>
 * </ul>
 * </p>
 * 
 * @author Davitroon
 * @since 3.0
 */
public class Validator {

	// ----------------------------- CONSTANTS -----------------------------
	private static final double MIN_BALANCE = 100;
	private static final double MAX_BALANCE = 999999;
	private static final int MAX_AGE = 95;
	private static final int MAX_NAME_LENGTH = 30;
	private static final double MIN_MONEY = 1000;
	private static final double MAX_MONEY = 999999;
	private static final double MIN_BET = 2;
	private static final double MAX_BET = 50000;

	// -------------------------- BET / MINIMUM VALIDATIONS --------------------------

	/**
	 * Validates a bet amount.
	 * <p>
	 * Throws a {@link BetException} if the bet is not valid due to format,
	 * minimum/maximum limits, or exceeding client/game balances.
	 * </p>
	 *
	 * @param betTxt        Bet amount as a string.
	 * @param clientBalance The client's current balance.
	 * @param gameMoney     The money available in the game.
	 * @throws BetException if the bet is invalid.
	 * @since 3.0
	 */
	public void validateBet(String betTxt, double clientBalance, double gameMoney) throws BetException {
		double bet;

		try {
			bet = Double.parseDouble(betTxt);
		} catch (NumberFormatException e) {
			throw new BetException("Enter a valid bet");
		}

		if (bet > clientBalance) {
			throw new BetException("The bet is greater than the client's balance");
		}
		if (bet > gameMoney) {
			throw new BetException("The bet is greater than the money in the game");
		}
		if (bet < MIN_BET) {
			throw new BetException("The minimum bet is " + MIN_BET);
		}
		if (bet > MAX_BET) {
			throw new BetException("The maximum bet is " + MAX_BET);
		}
	}

	/**
	 * Validates that both the client and the game have sufficient funds to play.
	 *
	 * @param clientBalance The client's balance.
	 * @param gameMoney     The money available in the game.
	 * @throws BetException if either balance is below the minimum bet.
	 * @since 3.0
	 */
	public void validateMinimumBalances(double clientBalance, double gameMoney) throws BetException {
		if (clientBalance < MIN_BET) {
			throw new BetException(
					"This client cannot play, they have less balance than the minimum bet (" + MIN_BET + "$).");
		}

		if (gameMoney < MIN_BET) {
			throw new BetException(
					"This game cannot be played, it has less money than the minimum bet (" + MIN_BET + "$).");
		}
	}

	// -------------------------- BALANCE / MONEY VALIDATIONS --------------------------

	/**
	 * Validates a client's balance input.
	 *
	 * @param text         Text input representing the client's balance.
	 * @param errorMessage JLabel where any validation error message will be displayed.
	 * @return True if the balance is valid, false otherwise.
	 * @since 3.0
	 */
	public boolean validateClientBalance(String text, JLabel errorMessage) {
		double balance;

		if (text.isBlank()) {
			errorMessage.setText("");
			return false;
		}

		try {
			balance = Double.parseDouble(text);
		} catch (NumberFormatException ee) {
			errorMessage.setText("Enter numbers only");
			return false;
		}

		if (balance < MIN_BALANCE) {
			errorMessage.setText("Balance too small (" + MIN_BALANCE + " min.)");
			return false;
		}

		if (balance > MAX_BALANCE) {
			errorMessage.setText("Balance too large (" + MAX_BALANCE + " max.)");
			return false;
		}

		errorMessage.setText("");
		return true;
	}

	/**
	 * Validates a game's money pool input.
	 *
	 * @param text         Text input representing the game's money pool.
	 * @param errorMessage JLabel where any validation error message will be displayed.
	 * @return True if the money pool is valid, false otherwise.
	 * @since 3.0
	 */
	public boolean validateGameMoney(String text, JLabel errorMessage) {
		double balance;

		if (text.isBlank()) {
			errorMessage.setText("");
			return false;
		}

		try {
			balance = Double.parseDouble(text);
		} catch (NumberFormatException ee) {
			errorMessage.setText("Enter numbers only");
			return false;
		}

		if (balance < MIN_MONEY) {
			errorMessage.setText("Amount too small (" + MIN_MONEY + " min.)");
			return false;
		}

		if (balance > MAX_MONEY) {
			errorMessage.setText("Amount too large (" + MAX_MONEY + " max.)");
			return false;
		}

		errorMessage.setText("");
		return true;
	}

	// -------------------------- CLIENT DATA VALIDATIONS --------------------------

	/**
	 * Validates a client's name.
	 *
	 * @param text         Client's name input.
	 * @param errorMessage JLabel where any validation error message will be displayed.
	 * @return True if the name is valid (no numbers or special characters, max length), false otherwise.
	 * @since 3.0
	 */
	public boolean validateName(String text, JLabel errorMessage) {

		if (text.isBlank()) {
			errorMessage.setText("");
			return false;
		}

		if (!text.matches("[a-zA-Z ]+")) {
			errorMessage.setText("Special characters or numbers are not allowed");
			return false;
		}

		if (text.length() > MAX_NAME_LENGTH) {
			errorMessage.setText("Name is too long (" + MAX_NAME_LENGTH + " max.)");
			return false;
		}

		errorMessage.setText("");
		return true;
	}

	/**
	 * Validates a client's age.
	 *
	 * @param text         Client's age input.
	 * @param errorMessage JLabel where any validation error message will be displayed.
	 * @return True if the age is valid (between 18 and MAX_AGE), false otherwise.
	 * @since 3.0
	 */
	public boolean validateClientAge(String text, JLabel errorMessage) {

		if (text.isBlank()) {
			errorMessage.setText("");
			return false;
		}

		if (!text.matches("[0-9]+")) {
			errorMessage.setText("Enter numbers only");
			return false;
		}

		int age = Integer.parseInt(text);

		if (age < 18) {
			errorMessage.setText("Client is underage.");
			return false;
		}

		if (age > MAX_AGE) {
			errorMessage.setText("Client is too old (" + MAX_AGE + " max.)");
			return false;
		}

		errorMessage.setText("");
		return true;
	}
}
