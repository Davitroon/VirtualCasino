package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Blackjack game, child of {@link Game}. Simulates the functionality of real
 * Blackjack, including card shuffling, dealing, hand evaluation, and game
 * logic.
 * 
 * @author David
 * @since 2.0
 */
public class Blackjack extends Game {

	private LinkedList<Integer> totalCards = new LinkedList<>();
	private ArrayList<Integer> playerCards = new ArrayList<>();
	private ArrayList<Integer> dealerCards = new ArrayList<>();

	/**
	 * Constructs a Blackjack game with the specified initial money for bets.
	 * 
	 * @param money The initial money for bets in this game.
	 * @since 2.0
	 */
	public Blackjack(double money) {
		super(money, "BlackJack");
	}

	/**
	 * Constructs a Blackjack game with an ID and initial money. Useful when loading
	 * a saved game.
	 * 
	 * @param id    Game ID
	 * @param money Initial money for bets
	 * @since 3.0
	 */
	public Blackjack(int id, double money) {
		super(id, money, "Blackjack");
	}

	/**
	 * Full constructor specifying ID, type, active status, and money.
	 * 
	 * @param id     Game ID
	 * @param type   Game type
	 * @param active Whether the game is active
	 * @param money  Initial money for bets
	 * @since 3.0
	 */
	public Blackjack(int id, String type, boolean active, double money) {
		super(id, type, active, money);
	}

	/**
	 * Shuffles the card deck.
	 * <p>
	 * Clears all player, dealer, and total card lists, fills the total deck with 52
	 * cards, and shuffles them randomly.
	 * </p>
	 * 
	 * @since 2.0
	 */
	public void shuffleCards() {
		totalCards.clear();
		playerCards.clear();
		dealerCards.clear();

		for (int i = 1; i < 14; i++) {
			for (int j = 0; j < 4; j++) {
				totalCards.add(i);
			}
		}

		Collections.shuffle(totalCards);
	}

	/**
	 * Determines whether the dealer should hit (draw another card) based on
	 * Blackjack rules.
	 * 
	 * @return true if the dealer should hit, false otherwise
	 * @since 2.0
	 */
	public boolean dealerShouldHit() {
		int dealerSum = sumCards(dealerCards);
		int playerSum = sumCards(playerCards);

		if (dealerSum < 16) {
			return true;
		} else if (dealerSum > playerSum && dealerSum < 22) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Gets the player's cards.
	 * 
	 * @return List of integers representing the player's cards
	 */
	public ArrayList<Integer> getPlayerCards() {
		return playerCards;
	}

	/**
	 * Gets the dealer's cards.
	 * 
	 * @return List of integers representing the dealer's cards
	 */
	public ArrayList<Integer> getDealerCards() {
		return dealerCards;
	}

	/**
	 * Checks if the specified player has exceeded 21 (busted).
	 * 
	 * @param player "player" or "dealer" to indicate which hand to check
	 * @return true if the player's total exceeds 21, false otherwise
	 * @since 2.0
	 */
	public boolean playerLoses(String player) {
		ArrayList<Integer> cardList = player.equalsIgnoreCase("player") ? playerCards : dealerCards;
		return sumCards(cardList) > 21;
	}

	/**
	 * Plays a round of Blackjack and calculates the resulting payout.
	 * <p>
	 * - Player busts: loses the bet.<br>
	 * - Dealer busts: player wins 1.75x the bet.<br>
	 * - Tie with blackjack: player wins 1.5x the bet.<br>
	 * - Tie without blackjack: player gets original bet (push).<br>
	 * - Player wins normally: bet multiplied by 1.5 or 2.5 if blackjack.<br>
	 * - Player loses normally: loses the bet.
	 * </p>
	 * 
	 * @param bet Amount bet by the player
	 * @return Resulting amount after the round
	 */
	@Override
	public double play(double bet) {
		int dealerSum = sumCards(dealerCards);
		int playerSum = sumCards(playerCards);

		if (playerSum > 21) {
			return -bet;
		}

		if (dealerSum > 21) {
			return bet * 1.75;
		}

		if (playerSum == dealerSum) {
			if (playerSum == 21) {
				return bet * 1.5;
			} else {
				return bet;
			}
		}

		if (playerSum > dealerSum) {
			return (playerSum == 21 && playerCards.size() == 2) ? bet * 2.5 : bet * 1.5;
		}

		return -bet;
	}

	/**
	 * Translates a card value to its face symbol (French deck).
	 * 
	 * @param card Card number (1-13)
	 * @return Symbolic representation ("A", "J", "Q", "K", or numeric string)
	 * @since 2.0
	 */
	private String readCard(Integer card) {
		switch (card) {
		case 1:
			return "A";
		case 11:
			return "J";
		case 12:
			return "Q";
		case 13:
			return "K";
		default:
			return String.valueOf(card);
		}
	}

	/**
	 * Shows the cards of the specified player.
	 * 
	 * @param hideCard Whether to hide the dealer's second card
	 * @param player   "player" or "dealer"
	 * @return String representation of the cards
	 * @since 2.0
	 */
	public String showCards(boolean hideCard, String player) {
		String cards = "";

		if (player.equals("player")) {
			for (Integer card : playerCards) {
				cards += "[" + readCard(card) + "] ";
			}
		}

		if (player.equals("dealer")) {
			for (int i = 0; i < dealerCards.size(); i++) {
				if (hideCard && i == 1) {
					cards += "[?] ";
				} else {
					cards += "[" + readCard(dealerCards.get(i)) + "] ";
				}
			}
		}

		return cards;
	}

	/**
	 * Deals a specified number of cards to a player or dealer.
	 * 
	 * @param numCards Number of cards to deal
	 * @param player   "player" or "dealer"
	 * @since 2.0
	 */
	public void dealCards(int numCards, String player) {
		if (player.equalsIgnoreCase("player")) {
			for (int i = 0; i < numCards; i++) {
				playerCards.add(totalCards.getFirst());
				totalCards.removeFirst();
			}
		} else if (player.equalsIgnoreCase("dealer")) {
			for (int i = 0; i < numCards; i++) {
				dealerCards.add(totalCards.getFirst());
				totalCards.removeFirst();
			}
		}
	}

	/**
	 * Sums the value of a list of cards, treating Aces as 1 or 11 as appropriate.
	 * 
	 * @param cardList List of cards
	 * @return Total sum of cards
	 * @since 2.0
	 */
	public int sumCards(ArrayList<Integer> cardList) {
		int sum = 0;
		int numAces = 0;

		for (int card : cardList) {
			if (card == 1) {
				numAces++;
				sum += 11;
			} else if (card > 10) {
				sum += 10;
			} else {
				sum += card;
			}
		}

		while (sum > 21 && numAces > 0) {
			sum -= 10;
			numAces--;
		}

		return sum;
	}
}
