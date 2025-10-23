package controller;

import java.sql.ResultSet;

import dao.DatabaseManager;
import model.Client;

/**
 * @author Davitroon
 * @since 3.0
 */
public class DataBaseController {

	private MainController mainController;
	private DatabaseManager dbManager;

	public DataBaseController(MainController mainController, DatabaseManager dbManager) {
		this.mainController = mainController;
		this.dbManager = dbManager;
	}

	public void updateLastAccess(String name) {
		dbManager.updateLastAccess(name);
	}

	public void deleteClient(int clientId) {
		dbManager.deleteClient(clientId);
	}

	public void toggleRememberLogin(String name, Boolean active) {
		dbManager.toggleRememberLogin(name, active);
	}

	public void deleteUser(int userId) {
		dbManager.deleteUser(userId);
	}

	public ResultSet queryClients(boolean onlyActive) {
		return dbManager.queryClients(onlyActive);
	}
}
