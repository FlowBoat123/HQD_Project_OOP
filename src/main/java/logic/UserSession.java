package logic;

/**
 * Singleton class to manage the current user session.
 * This class provides methods to get and set the current user's ID and user object.
 */
public class UserSession {
    private static UserSession instance;
    private int userID;
    private User user;

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private UserSession() {
    }

    /**
     * Retrieves the singleton instance of UserSession.
     *
     * @return The singleton instance of UserSession.
     */
    public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    /**
     * Gets the current user's ID.
     *
     * @return The current user's ID.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Checks if the current user is an admin.
     *
     * @return True if the current user is an admin, false otherwise.
     */
    public boolean isAdmin() {
        return userID == 1;
    }

    /**
     * Sets the current user's ID.
     *
     * @param userID The user ID to set.
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Gets the current user object.
     *
     * @return The current user object.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the current user object.
     *
     * @param user The user object to set.
     */
    public void setUser(User user) {
        this.user = user;
    }
}