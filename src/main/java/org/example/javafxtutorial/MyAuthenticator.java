package org.example.javafxtutorial;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class MyAuthenticator extends Authenticator {
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        // Supply the username and password for the protected resource
        return new PasswordAuthentication("myUsername", "myPassword".toCharArray());
    }
}
