package logic;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;

import logic.Controller;
import logic.Model;
import logic.User;
import ui.ConnectUI;
import ui.HomeUI;

/**
 * Class that handles user login sessions.
 */
public class Session {
	
	private HomeUI menu;
	private ConnectUI connect;
	private Controller controller;
	private Model model;

	public Session(HomeUI menu, ConnectUI connect, Controller controller, Model model) {
		this.menu = menu;
		this.connect = connect;
		this.controller = controller;
		this.model = model;
	}


	/**
	 * Checks if any user has been flagged to remember the login session. If so, it will take them to the menu with their session activated.
	 */
	public void checkStartup () {
		ResultSet rset = model.specificQuery("SELECT * FROM users WHERE remember_login = 1");
		
		try {
			if (rset.next()) {
				startSession(new User(rset.getInt("id") ,rset.getString("username"), rset.getString("user_password"), rset.getString("email"), 
						rset.getString("last_access"), true), menu);
				
			} else {
				connect.setVisible(true);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	
	
	/**
	 * Method that starts a session with a new user and takes the user to the main menu.
	 * @param user New user.
	 * @param currentWindow Window from which the session is being started.
	 */
	public void startSession (User user, JFrame currentWindow) {
		menu.setUser(user);
		model.setCurrentUser(user.getId());
		controller.changeWindow(currentWindow, menu);
	}


	public void setConnect(ConnectUI connect) {
		this.connect = connect;
	}
}