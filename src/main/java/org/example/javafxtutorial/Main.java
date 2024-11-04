package org.example.javafxtutorial;

public class Main {
    public static void main(String[] args) {
        // Create a new user
        User user = new User("john_doe", "securePassword123".toCharArray());

        // Authenticate user
        if (user.authenticate("john_doe", "securePassword123".toCharArray())) {
            System.out.println("Authentication successful!");
        } else {
            System.out.println("Authentication failed.");
        }

        // Clear password after use for security
        user.clearPassword();
    }
}

