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
 * Controller responsible for managing the application's user interfaces (UIs).
 * <p>
 * The {@code ViewController} initializes all UI classes, controls window
 * transitions, and manages the launching of game interfaces depending on the
 * selected game.
 * </p>
 *
 * <p>
 * It also provides methods for switching between windows, displaying UIs, and
 * loading user data into the profile.
 * </p>
 *
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

	/**
	 * Initializes all user interface (UI) classes and links them with the main
	 * controller.
	 * <p>
	 * This method creates instances of every UI window, passing the main controller
	 * to each, and initializes the home interface as the default screen.
	 * </p>
	 *
	 * @param controller Reference to the application's main controller.
	 * @param dbManager  Reference to the {@link DatabaseManager} used for database
	 *                   operations.
	 * @since 3.0
	 */
	public void initializeClasses(MainController controller, DatabaseManager dbManager) {
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
		blackjackUI = new BlackjackUI(controller);
		slotmachineUI = new SlotmachineUI(controller);

		homeUI.initializeClassesUI();
	}

	// -------------------------- WINDOW CONTROL METHODS --------------------------

	/**
	 * Displays a specific window (JFrame) on screen.
	 *
	 * @param window The window to make visible.
	 * @since 3.3
	 */
	public void openWindow(JFrame window) {
		window.setVisible(true);
	}

	/**
	 * Switches from one window to another by hiding the current one and displaying
	 * the new one.
	 *
	 * @param currentWindow The currently visible window to hide.
	 * @param newWindow     The new window to display.
	 * @since 3.3
	 */
	public void switchWindow(JFrame currentWindow, JFrame newWindow) {
		currentWindow.setVisible(false);
		newWindow.setVisible(true);
	}

	/**
	 * Opens the appropriate game window based on the type of {@link Game} provided.
	 * <p>
	 * Depending on whether the instance is a {@link Blackjack} or
	 * {@link Slotmachine}, this method initializes the corresponding game UI with
	 * the provided client and bet data, switches the view, and starts the game.
	 * </p>
	 *
	 * @param playWindow The Play UI window from which this method is called.
	 * @param game       The game instance to be played.
	 * @param client     The client who will play the game.
	 * @param bet        The amount of money bet.
	 * @since 3.0
	 */
	public void openGameWindow(PlayUI playWindow, Game game, Client client, double bet) {

		if (game instanceof Blackjack) {
			blackjackUI.initializeData(client, (Blackjack) game, bet);
			switchWindow(playWindow, blackjackUI);
			blackjackUI.startGame();
		}

		if (game instanceof Slotmachine) {
			slotmachineUI.initializeData(client, (Slotmachine) game, bet);
			switchWindow(playWindow, slotmachineUI);
			slotmachineUI.startGame();
		}
	}

	// ---------------------------- DATA METHODS ----------------------------

	/**
	 * Loads the current user's data and updates the Profile UI with that
	 * information.
	 *
	 * @since 3.3
	 */
	public void loadUserData() {
		User user = controller.getCurrentUser();
		profileUI.upateUserData(user);
	}

	// --------------------------------- GETTERS ----------------------------------


	/**
	 * @return The application's home UI.
	 */
	public HomeUI getHomeUI() {
		return homeUI;
	}

	/**
	 * @return The connection UI.
	 */
	public ConnectUI getConnectUI() {
		return connectUI;
	}

	/**
	 * @return The user profile UI.
	 */
	public ProfileUI getProfileUI() {
		return profileUI;
	}

	/**
	 * @return The play (game selection) UI.
	 */
	public PlayUI getPlayUI() {
		return playUI;
	}

	/**
	 * @return The management UI.
	 */
	public ManagementUI getManagementUI() {
		return managementUI;
	}

	/**
	 * @return The statistics UI.
	 */
	public StatsUI getStatsUI() {
		return statsUI;
	}

	/**
	 * @return The login UI.
	 */
	public LogInUI getLogInUI() {
		return logInUI;
	}

	/**
	 * @return The sign-in (registration) UI.
	 */
	public SignInUI getSignInUI() {
		return SignInUI;
	}

	/**
	 * @return The blackjack game UI.
	 */
	public BlackjackUI getBlackjackUI() {
		return blackjackUI;
	}

	/**
	 * @return The slot machine game UI.
	 */
	public SlotmachineUI getSlotmachineUI() {
		return slotmachineUI;
	}

	/**
	 * @return The client management UI.
	 */
	public ClientUI getClientUI() {
		return clientUI;
	}

	/**
	 * @return The client editing UI.
	 */
	public ClientEditUI getClientEditUI() {
		return clientEditUI;
	}

	/**
	 * @return The game editing UI.
	 */
	public GameEditUI getGameEditUI() {
		return gameEditUI;
	}

	/**
	 * @return The game list UI.
	 */
	public GameUI getGameUI() {
		return gameUI;
	}

	/**
	 * @return Reference to the main controller.
	 */
	public MainController getController() {
		return controller;
	}
}