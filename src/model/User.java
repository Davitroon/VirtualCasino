package model;

/**
 * Represents a user profile in the system.
 * <p>
 * Each user has an ID, username, password, email, last access timestamp, and
 * a flag indicating whether the session should be remembered.
 * </p>
 * 
 * @author Davitroon
 * @since 3.0
 */
public class User {

    /** User ID in the database. */
    private int id;

    /** Username chosen by the user. */
    private String name;

    /** User's password. */
    private String password;

    /** User's email address. */
    private String email;

    /** Timestamp of the last access to the system. */
    private String lastAccess;

    /** Flag indicating if the user chose to remember the login session. */
    private boolean rememberSession;

    /**
     * Full constructor for creating a user with all fields.
     * 
     * @param id              User ID
     * @param name            Username
     * @param password        User password
     * @param email           User email
     * @param lastAccess      Last access timestamp
     * @param rememberSession True if the session should be remembered
     */
    public User(int id, String name, String password, String email, String lastAccess, boolean rememberSession) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.lastAccess = lastAccess;
        this.rememberSession = rememberSession;
    }

    /**
     * Constructor for creating a user with only a username and password.
     * 
     * @param name     Username
     * @param password Password
     */
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    /**
     * Constructor for creating a user with username, password, email, and
     * remember session flag.
     * 
     * @param name            Username
     * @param password        Password
     * @param email           Email address
     * @param rememberSession True if the session should be remembered
     */
    public User(String name, String password, String email, boolean rememberSession) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.rememberSession = rememberSession;
    }

    /** @return Username of the user. */
    public String getName() {
        return name;
    }

    /** @return Password of the user. */
    public String getPassword() {
        return password;
    }

    /** @return Email of the user. */
    public String getEmail() {
        return email;
    }

    /** @return Timestamp of the last access. */
    public String getLastAccess() {
        return lastAccess;
    }

    /** @return True if the session is set to be remembered. */
    public boolean isRememberSession() {
        return rememberSession;
    }

    /** @return User ID. */
    public int getId() {
        return id;
    }
}
