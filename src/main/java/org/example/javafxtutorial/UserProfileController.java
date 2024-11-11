package org.example.javafxtutorial;

import controller.AvatarSelectionController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserProfileController {

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

    private boolean isEditing = false;

    private User user;

    private ExecutorService executorService = Executors.newFixedThreadPool(1);

    public void setUser(User user) {
        this.user = user;
        updateUIWithUserData();
    }

    private void updateUIWithUserData() {
        if (user != null) {
            // Set the profile image, assuming you have logic to retrieve the user's image
            Image image = new Image(Objects.<InputStream>requireNonNull(getClass().getResourceAsStream("/Avatar/icon.png")));
            profileImage.setImage(image);

            // Clip the ImageView to a circle
            double radius = Math.min(profileImage.getFitWidth(), profileImage.getFitHeight()) / 2;
            Circle clip = new Circle(radius, radius, radius);
            profileImage.setClip(clip);

            // Ensure the clip is centered correctly
            profileImage.setX((profileImage.getFitWidth() - radius * 2) / 2);
            profileImage.setY((profileImage.getFitHeight() - radius * 2) / 2);

            // Set user details
            usernameLabel.setText(user.getUsername());
            bioLabel.setText(user.getBio());
            emailLabel.setText(user.getEmail());
            websiteLabel.setText(user.getWebsite());

            // Set stats
            repoCountLabel.setText(String.valueOf(user.getRepoCount()));
            followerCountLabel.setText(String.valueOf(user.getFollowerCount()));
            followingCountLabel.setText(String.valueOf(user.getFollowingCount()));

            toggleEditing(false);
        }
    }

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
    private void handleChangeProfileImage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("avatar-selection.fxml"));
            Parent root = loader.load();
            AvatarSelectionController controller = loader.getController();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());
            dialogStage.setScene(new Scene(root));
            controller.setStageAndController(dialogStage, this);
            dialogStage.showAndWait();
            Image selectedImage = controller.getSelectedImage();
            if (selectedImage != null) {
                profileImage.setImage(selectedImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleEditProfile(ActionEvent event) {
        if (isEditing) {
            // Save changes and switch back to label view
            System.out.println("Profile saved");

            // Set text from TextFields to Labels
            user.setUsername(usernameField.getText());
            user.setBio(bioField.getText());
            user.setEmail(emailField.getText());
            user.setWebsite(websiteField.getText());

            // Run the database update on a different thread
            executorService.submit(() -> updateUserDatabase(user));

            // Update UI with saved user data
            updateUIWithUserData();

            // Hide TextFields, show Labels
            toggleEditing(false);
            editProfileButton.setText("Edit Profile");
        } else {
            // Switch to edit mode
            System.out.println("Editing profile");

            // Set text from Labels to TextFields
            usernameField.setText(user.getUsername());
            bioField.setText(user.getBio());
            emailField.setText(user.getEmail());
            websiteField.setText(user.getWebsite());

            // Hide Labels, show TextFields
            toggleEditing(true);
            editProfileButton.setText("Save");
        }
        isEditing = !isEditing;
    }

    private void updateUserDatabase(User user) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                String updateQuery = "UPDATE users SET username = ?, bio = ?, email = ?, website = ? WHERE id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                    preparedStatement.setString(1, user.getUsername());
                    preparedStatement.setString(2, user.getBio());
                    preparedStatement.setString(3, user.getEmail());
                    preparedStatement.setString(4, user.getWebsite());
                    java.sql.Timestamp creationTime = java.sql.Timestamp.valueOf(user.getCreationTime());
                    preparedStatement.setInt(5, user.getID());
                    System.out.println(user.getUsername());

                    preparedStatement.executeUpdate();
                    System.out.println("User data updated successfully");
                }
            } else {
                System.out.println("Failed to establish database connection.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update user data");
        }
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
        // Ensure that editing is initially disabled
        toggleEditing(false);
        updateUIWithUserData();
    }

    @FXML
    public void handleImageClick(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminDashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAvatar(Image avatar) {
        profileImage.setImage(avatar);
    }
    @FXML public void stop() { executorService.shutdown(); }
}
