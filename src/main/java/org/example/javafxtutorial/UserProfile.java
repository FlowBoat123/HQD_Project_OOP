package org.example.javafxtutorial;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class UserProfile {

    @FXML
    private ImageView profileImage;

    @FXML
    private Label usernameLabel;
    @FXML
    private TextField usernameField;

    @FXML
    private Label bioLabel;
    @FXML
    private TextField bioField;

    @FXML
    private Label emailLabel;
    @FXML
    private TextField emailField;

    @FXML
    private Label websiteLabel;
    @FXML
    private TextField websiteField;

    @FXML
    private Label repoCountLabel;

    @FXML
    private Label followerCountLabel;

    @FXML
    private Label followingCountLabel;

    @FXML
    private Button editProfileButton;

    private boolean isEditing = false;

    @FXML
    public void handleEditProfile(ActionEvent event) {
        if (isEditing) {
            // Save changes and switch back to label view
            System.out.println("Profile saved");

            // Set text from TextFields to Labels
            usernameLabel.setText(usernameField.getText());
            bioLabel.setText(bioField.getText());
            emailLabel.setText(emailField.getText());
            websiteLabel.setText(websiteField.getText());

            // Hide TextFields, show Labels
            toggleEditing(false);
            editProfileButton.setText("Edit Profile");
        } else {
            // Switch to edit mode
            System.out.println("Editing profile");

            // Set text from Labels to TextFields
            usernameField.setText(usernameLabel.getText());
            bioField.setText(bioLabel.getText());
            emailField.setText(emailLabel.getText());
            websiteField.setText(websiteLabel.getText());

            // Hide Labels, show TextFields
            toggleEditing(true);
            editProfileButton.setText("Save");
        }
        isEditing = !isEditing;
    }

    private void toggleEditing(boolean enable) {
        // Show or hide TextFields based on editing mode
        usernameField.setVisible(enable);
        bioField.setVisible(enable);
        emailField.setVisible(enable);
        websiteField.setVisible(enable);

        // Show or hide Labels based on editing mode
        usernameLabel.setVisible(!enable);
        bioLabel.setVisible(!enable);
        emailLabel.setVisible(!enable);
        websiteLabel.setVisible(!enable);
    }

    public void initialize() {
        // Set profile picture
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/6.jpg")));
        profileImage.setImage(image);

        // Set user details (for example, hardcoded or loaded from a database)
        usernameField.setText("Hien dep trai");
        bioField.setText("Dep trai, nhieu ban gai");

        // Set stats
        repoCountLabel.setText("0");
        followerCountLabel.setText("0");
        followingCountLabel.setText("0");

        // Set contact info
        emailField.setText("Email: hienctcom@gmail.com");
        websiteField.setText("Website: https://skdibi.net");

        toggleEditing(false);
    }
}
