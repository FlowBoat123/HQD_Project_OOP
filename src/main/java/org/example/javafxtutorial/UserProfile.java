package org.example.javafxtutorial;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
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

    @FXML
    private Button changeProfileImageButton;

    @FXML
    private AnchorPane scene1;

    @FXML
    private AnchorPane scene2;

    @FXML
    private AnchorPane scene3;

    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;

    @FXML
    public void showScene1(ActionEvent event) {
        scene1.setVisible(true);
        scene2.setVisible(false);
        scene3.setVisible(false);
        button1.getStyleClass().add("button-active");
        button2.getStyleClass().remove("button-active");
        button3.getStyleClass().remove("button-active");
    }

    @FXML
    public void showScene2(ActionEvent event) {
        scene1.setVisible(false);
        scene2.setVisible(true);
        scene3.setVisible(false);
        button1.getStyleClass().remove("button-active");
        button2.getStyleClass().add("button-active");
        button3.getStyleClass().remove("button-active");
    }

    @FXML
    public void showScene3(ActionEvent event) {
        scene1.setVisible(false);
        scene2.setVisible(false);
        scene3.setVisible(true);
        button1.getStyleClass().remove("button-active");
        button2.getStyleClass().remove("button-active");
        button3.getStyleClass().add("button-active");
    }

    @FXML
    public void handleChangeProfileImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(changeProfileImageButton.getScene().getWindow());

        if (selectedFile != null) {
            Image newImage = new Image(selectedFile.toURI().toString());
            profileImage.setImage(newImage);
        }
    }

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

        // Show or hide the Change Image button based on editing mode
        changeProfileImageButton.setVisible(enable);
    }

    public void initialize() {
        // Set profile picture
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/bg3.jpg")));
        profileImage.setImage(image);

        // Clip the ImageView to a circle
        double radius = Math.min(profileImage.getFitWidth(), profileImage.getFitHeight()) / 2;
        Circle clip = new Circle(radius, radius, radius);
        profileImage.setClip(clip);

        // Ensure the clip is centered correctly
        profileImage.setX((profileImage.getFitWidth() - radius * 2) / 2);
        profileImage.setY((profileImage.getFitHeight() - radius * 2) / 2);

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
