package org.example.javafxtutorial;

public class UserSession {
    private static UserSession instance;
    private int userID;

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
}
