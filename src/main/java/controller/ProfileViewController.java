package controller;

import database.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.User;

import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * This class controls the profile view and handles interactions with the user profile.
 */
public class ProfileViewController {

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

    private boolean isEditing = false;

    private User user;

    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    /**
     * Sets the user for the profile.
     *
     * @param user The User object to be set.
     */
    public void setUser(User user) {
        this.user = user;
        updateUIWithUserData();
    }

    /**
     * Updates the UI with the user's data.
     */
    private void updateUIWithUserData() {
        if (user != null) {
            // Set the profile image, assuming you have logic to retrieve the user's image
            String Image_path = user.getAvatar();
            System.out.println(Image_path);
            if (Image_path == null) Image_path = "/Avatar/icon_2.png";
            Image image = new Image(Objects.<InputStream>requireNonNull(getClass().getResourceAsStream(Image_path)));
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

    /**
     * Handles the change profile image button click event.
     *
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    private void handleChangeProfileImage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/avatar-selection.fxml"));
            Parent root = loader.load();
            AvatarSelectionController controller = loader.getController();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);        // set the pop up window for choosing avatar
            dialogStage.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());
            dialogStage.setScene(new Scene(root));                  // set scene
            controller.setStageAndController(dialogStage, this);
            dialogStage.showAndWait();
            String selectedImagePath = controller.getSelectedImagePath();
            Image selectedImage = controller.getSelectedImage();
            if (selectedImagePath != null) {
                profileImage.setImage(selectedImage);
                user.setAvatar(selectedImagePath);
                System.out.println(selectedImagePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the edit profile button click event.
     *
     * @param event The ActionEvent that triggered this method.
     */
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

    /**
     * Updates the user's data in the database.
     *
     * @param user The User object to be updated.
     */
    private void updateUserDatabase(User user) {
        Runnable task = () -> {
            try {
                System.out.println(user.getAvatar());
                UserDAO userDAO = new UserDAO();
                userDAO.update(user);
                javafx.application.Platform.runLater(() -> {
                    // Code to update the UI after successful update (if needed)
                    System.out.println("User updated successfully!");
                });
            } catch (Exception e) {
                e.printStackTrace();
                javafx.application.Platform.runLater(() -> {
                    // Code to handle the error in the UI (if needed)
                    System.out.println("Failed to update user: " + e.getMessage());
                });
            }
        };
        executorService.submit(task);
    }

    /**
     * Toggles the editing mode.
     *
     * @param enable True to enable editing mode, false to disable.
     */
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

    /**
     * Initializes the controller.
     */
    public void initialize() {
        // Ensure that editing is initially disabled
        toggleEditing(false);
        updateUIWithUserData();
    }

    /**
     * Sets the avatar image for the user.
     *
     * @param avatar The Image object to be set as the avatar.
     */
    public void setAvatar(Image avatar) {
        profileImage.setImage(avatar);
    }

    /**
     * Stops the executor service.
     */
    @FXML
    public void stop() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

    @FXML
    private TextArea bioTextArea;

    @FXML
    private Button editBioButton;

    boolean isEditingDetail = false;

    /**
     * Handles the edit bio button click event.
     */
    @FXML
    private void handleEditBio() {
        if (isEditingDetail) {
            // Save changes
            bioTextArea.setEditable(false);
            editBioButton.setText("Edit");
            // Additional logic to save the changes can be added here
        } else {
            // Enable editing
            bioTextArea.setEditable(true);
            editBioButton.setText("Save");
        }
        isEditingDetail = !isEditingDetail;
    }
}