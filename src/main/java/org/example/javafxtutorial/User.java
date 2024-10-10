package org.example.javafxtutorial;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Arrays;


public class User {
    private String username;
    private char[] password;

    public User(String username, char []password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    // Method for clear password -> Change password from the user
    public void clearPassword() {
        // fill the array with null characters for security
        Arrays.fill(password, '\0');
    }

    // Example method for authentication logic
    public boolean authenticate(String inputUsername, char[] inputPassword) {
        return this.username.equals(inputUsername) && Arrays.equals(this.password, inputPassword);
    }

    // Optional: Override toString() to hide password when printing User objects
    @Override
    public String toString() {
        return "User{username='" + username + "'}";
    }
}
