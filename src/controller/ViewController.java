package controller;

import javax.swing.JFrame;

import dao.DatabaseManager;
import model.Blackjack;
import model.Client;
import model.Game;
import model.Slotmachine;
import model.User;
import ui.BlackjackUI;
import ui.ClientEditUI;
import ui.ClientUI;
import ui.ConnectUI;
import ui.GameEditUI;
import ui.GameUI;
import ui.HomeUI;
import ui.LogInUI;
import ui.ManagementUI;
import ui.PlayUI;
import ui.ProfileUI;
import ui.SignInUI;
import ui.SlotmachineUI;
import ui.StatsUI;

/**
 * @author Davitroon
 * @since 3.3
 */
public class ViewController {

	// ----------------------------- UI INSTANCES -----------------------------
	private HomeUI homeUI;
	private ConnectUI connectUI;
	private ProfileUI profileUI;
	private MainController controller;
	private PlayUI playUI;
	private ManagementUI managementUI;
	private BlackjackUI blackjackUI;
	private SlotmachineUI slotmachineUI;
	private StatsUI statsUI;
	private LogInUI logInUI;	
	private SignInUI SignInUI;
	private ClientUI clientUI;
	private ClientEditUI clientEditUI;
	private GameEditUI gameEditUI;
	private GameUI gameUI;

	public ViewController(MainController controller, DatabaseManager dbManager) {
		this.controller = controller;
		homeUI = new HomeUI(controller);
		connectUI = new ConnectUI(controller);
		profileUI = new ProfileUI(controller);
		playUI = new PlayUI(controller);
		managementUI = new ManagementUI(controller);
		statsUI = new StatsUI(controller);
		logInUI = new LogInUI(controller);
		SignInUI = new SignInUI(controller);
		clientUI = new ClientUI(controller);
		clientEditUI = new ClientEditUI(controller);
		gameEditUI = new GameEditUI(controller);
		gameUI = new GameUI(controller);
	}

	// -------------------------- WINDOW CONTROL METHODS --------------------------

	/**
	 * Method to display a JFrame window.
	 * * @param window Window to open/show.
	 */
	public void openWindow(JFrame window) {
		window.setVisible(true);
	}

	/**
	 * Method to hide one window and show a new one.
	 * 
	 * @param currentWindow Window to close/hide
	 * @param newWindow     Window to open/show
	 * @since 3.0
	 */
	public void switchWindow(JFrame currentWindow, JFrame newWindow) {
		currentWindow.setVisible(false);
		newWindow.setVisible(true);

	}

	/**
	 * Method to open a game window. Depending on the instance of the received game,
	 * it will open a different type of window.
	 * 
	 * @param playWindow "Play" window from which this method should be called.
	 * @param game       Game class to play.
	 * @param client     Client who will play.
	 * @param bet        Amount of the bet.
	 * @since 3.0
	 */
	public void openGameWindow(PlayUI playWindow, Game game, Client client, double bet) {

		if (game instanceof Blackjack) {
			// Initialize the Blackjack UI with game data and start the game
			blackjackUI.initializeData(client, (Blackjack) game, bet);
			switchWindow(playWindow, blackjackUI);
			blackjackUI.startGame();
		}

		if (game instanceof Slotmachine) {
			// Initialize the Slotmachine UI with game data and start the game
			slotmachineUI.initializeData(client, (Slotmachine) game, bet);
			switchWindow(playWindow, slotmachineUI);
			slotmachineUI.startGame();
		}
	}

	// ---------------------------- DATA LOADING METHODS ----------------------------

	/**
	 * Loads the current user's data and updates the Profile UI.
	 * * @since 3.3
	 */
	public void loadUserData() {
		User user = controller.getCurrentUser();
		profileUI.upateUserData(user);
	}

	// --------------------------------- GETTERS ----------------------------------

	public HomeUI getHomeUI() {
		return homeUI;
	}

	public ConnectUI getConnectUI() {
		return connectUI;
	}

	public ProfileUI getProfileUI() {
		return profileUI;
	}

	public PlayUI getPlayUI() {
		return playUI;
	}

	public ManagementUI getManagementUI() {
		return managementUI;
	}

	public StatsUI getStatsUI() {
		return statsUI;
	}

	public LogInUI getLogInUI() {
		return logInUI;
	}

	public SignInUI getSignInUI() {
		return SignInUI;
	}

	public BlackjackUI getBlackjackUI() {
		return blackjackUI;
	}

	public SlotmachineUI getSlotmachineUI() {
		return slotmachineUI;
	}

	public ClientUI getClientUI() {
		return clientUI;
	}

	public ClientEditUI getClientEditUI() {
		return clientEditUI;
	}

	public GameEditUI getGameEditUI() {
		return gameEditUI;
	}

	public GameUI getGameUI() {
		return gameUI;
	}
	
	public MainController getController() {
		return controller;
	}
}