package controller;

import javax.swing.JFrame;

import dao.DatabaseManager;
import model.User;
import ui.ConnectUI;
import ui.HomeUI;
import ui.PlayUI;
import ui.ProfileUI;

/**
 * @author Davitroon
 * @since 3.3
 */
public class ViewController {
	
	private HomeUI homeUI;
	private ConnectUI connectUI;
	private ProfileUI profileUI;
	private MainController controller;
	private PlayUI playUI;
	
	public ViewController(MainController controller, DatabaseManager dbManager) {
		this.controller = controller;
		homeUI = new HomeUI(controller, dbManager);
		connectUI = new ConnectUI(controller, dbManager);
		profileUI = new ProfileUI(controller, dbManager);
		playUI = new PlayUI(controller);
	}
	
	public void openWindow(JFrame window) {
		window.setVisible(true);
	}

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
	
	/**
	 * 
	 * @param userId
	 * @since 3.3
	 */
	public void loadUserData(int userId) {
	    User user = controller.getCurrentUser();
	    profileUI.displayUserData(user);
	}
	
	/**
	 * Method to hide one window and show a new one.
	 * 
	 * @param currentWindow Window to close/hide
	 * @param newWindow     Window to open/show
	 * @since 3.0
	 */
	public void switchWindow(JFrame currentWindow, JFrame newWindow) {
		currentWindow.setVisible(false);
		newWindow.setVisible(true);

	}
	
	
}
