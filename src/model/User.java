package model;

/**
 * Class for the different profiles the player can have.
 */
public class User {

	private int id;
	private String name;
	private String password;
	private String email;
	private String lastAccess;
	private boolean rememberSession;

	public User(int id, String name, String password, String email, String lastAccess, boolean rememberSession) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.lastAccess = lastAccess;
		this.rememberSession = rememberSession;
	}

	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public User(String name, String password, String email, boolean rememberSession) {
		this.name = name;
		this.password = password;
		this.email = email;
		this.rememberSession = rememberSession;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public String getLastAccess() {
		return lastAccess;
	}

	public boolean isRememberSession() {
		return rememberSession;
	}

	public int getId() {
		return id;
	}

}