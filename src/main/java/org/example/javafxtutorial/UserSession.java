package org.example.javafxtutorial;

import logic.User;

public class UserSession {
    private static UserSession instance;
    private int userID;
    private User user;

    private UserSession() {
    }

    public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public int getUserID() {
        return userID;
    }

    boolean isAdmin(){
        return userID == 1;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
