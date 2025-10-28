package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Blackjack Game, child of Game. Simulates the functionality of real BlackJack.
 * 
 * @author David Forero
 * @since 2.0
 */
public class Blackjack extends Game {

	private LinkedList<Integer> totalCards = new LinkedList<>();
	private ArrayList<Integer> playerCards = new ArrayList<>();
	private ArrayList<Integer> dealerCards = new ArrayList<>();

	/**
	 * Constructor for Blackjack, sends its type to the Game constructor.
	 * 
	 * @param money Money that the game will count for bets.
	 * @since 2.0
	 */
	public Blackjack(double money) {
		super(money, "BlackJack");
	}

	/**
	 * Blackjack constructor for necessary data (to save in the game).
	 * 
	 * @param id    Game ID
	 * @param money Game money
	 * @since 3.0
	 */
	public Blackjack(int id, double money) {
		super(id, money, "Blackjack");
	}

	/**
	 * Complete constructor (to be edited) for Blackjack, sends parameters to the
	 * Game constructor.
	 * 
	 * @param id     Game ID
	 * @param type   Game type
	 * @param active Game status
	 * @param money  Game money
	 * @since 3.0
	 */
	public Blackjack(int id, String type, boolean active, double money) {
		super(id, type, active, money);
	}

	/**
	 * Shuffles the cards. First, it empties all card decks, then it fills the total
	 * card deck, and finally shuffles them.
	 */
	public void shuffleCards() {

		totalCards.clear();
		playerCards.clear();
		dealerCards.clear(); // NOTE: 'cartasTotales' translated to 'totalCards', etc., in the previous
								// response

		for (int i = 1; i < 14; i++) {
			for (int j = 0; j < 4; j++) {
				totalCards.add(i);
			}
		}

		Collections.shuffle(totalCards);
	}

	/**
	 * Indicates whether the dealer should ask for a card in different situations.
	 * 
	 * @return Whether or not to ask for a card.
	 * @since 2.0
	 */
	public boolean dealerShouldHit() {

		int dealerSum = sumCards(dealerCards);
		int playerSum = sumCards(playerCards);

		// If their cards sum less than 16, they must hit (ask for a card).
		if (dealerSum < 16) {
			return true;

			// If they have more points than the player and don't exceed 21, they stand.
		} else if (dealerSum > playerSum && dealerSum < 22) {
			return false;

			// They have fewer points than the player and more than 16, so they must hit to
			// win.
		} else {
			return true;
		}
	}

	public ArrayList<Integer> getPlayerCards() {
		return playerCards;
	}

	public ArrayList<Integer> getDealerCards() {
		return dealerCards;
	}

	/**
	 * Checks if the player has exceeded 21 (busted).
	 * 
	 * @param player Player to check.
	 * @return True if the player has exceeded 21, otherwise false.
	 * @since 2.0
	 */
	public boolean playerLoses(String player) {

		ArrayList<Integer> cardList = player.equalsIgnoreCase("player") ? playerCards : dealerCards;

		// Assumes 'sumCards' is a method accessible in this class, corresponding to
		// 'sumarCartas'
		return sumCards(cardList) > 21;
	}

	/**
	 * Compares the sum of the player's and the dealer's cards.<br>
	 * - If the player wins with a blackjack, the bet is multiplied by 2.5.<br>
	 * - If there is a tie with a blackjack or the player wins with a normal hand,
	 * the bet is multiplied by 1.5.<br>
	 * - If there is a tie without a blackjack, the original bet is recovered.<br>
	 * - If the player loses, they lose their bet.<br>
	 */
	@Override
	public double play(double bet) {

		// Assumes 'sumCards' is a method accessible in this class, corresponding to
		// 'sumarCartas'
		int dealerSum = sumCards(dealerCards);
		int playerSum = sumCards(playerCards);

		// Player busts/loses
		if (playerSum > 21) {
			return -bet;
		}

		// Dealer busts and the player does not
		if (dealerSum > 21) {
			// NOTE: The original code uses 1.75 here, which is not standard BlackJack
			// payout (usually 2.0).
			// We maintain the logic.
			return bet * 1.75;
		}

		// Tie (Push)
		if (playerSum == dealerSum) {
			if (playerSum == 21) {
				return bet * 1.5; // Tie with a blackjack (often just a push/1.0 in real games, but maintaining
									// the code's logic)

			} else {
				return bet; // Normal tie (push)
			}
		}

		// Player wins (Blackjack or normal win)
		if (playerSum > dealerSum) {
			// Check for natural Blackjack (21 on first 2 cards)
			return (playerSum == 21 && playerCards.size() == 2) ? bet * 2.5 : bet * 1.5;
		}

		// Player loses (Dealer score > Player score, and neither busted)
		return -bet;

	}

	/**
	 * Reads a card to translate it to the French deck (face card symbols).
	 * 
	 * @param card Card to read.
	 * @return Translated card symbol.
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
	 * Shows the cards of each player.
	 * 
	 * @param hideCard Whether or not to hide the dealer's 2nd card.
	 * @param player   The player whose cards are to be displayed ("player" or
	 *                 "dealer").
	 * @return The cards of the specified player.
	 * @since 2.0
	 */
	public String showCards(boolean hideCard, String player) {

		String cards = "";

		if (player.equals("player")) {
			// Assumes 'playerCards' is used for the client/player
			for (Integer card : playerCards) {
				cards += "[" + readCard(card) + "] ";
			}
		}

		if (player.equals("dealer")) {
			// Assumes 'dealerCards' is used for the dealer/crupier
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
	 * Deals a number of cards to the indicated player, removing the cards from the
	 * total deck.
	 * 
	 * @param numCards Number of cards to deal.
	 * @param player   Player to whom the cards will be dealt.
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
	 * Sums the value of the player's cards.
	 * 
	 * @param cardList Player's cards.
	 * @return The total sum, taking Aces into account.
	 * @since 2.0
	 */
	public int sumCards(ArrayList<Integer> cardList) {

		int sum = 0;
		int numAces = 0;

		for (int card : cardList) {
			// Counts if there are any Aces.
			if (card == 1) {
				numAces++;
				sum += 11;

				// Considers J, Q, and K as 10.
			} else if (card > 10) {
				sum += 10;

			} else {
				sum += card;
			}
		}

		// Converts an Ace from 11 to 1 if the player busts (exceeds 21).
		while (sum > 21 && numAces > 0) {
			sum -= 10;
			numAces--;
		}

		return sum;
	}
}
