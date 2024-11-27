package controller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * Controller class for handling avatar selection in a JavaFX application.
 * This class manages the initialization of avatar images, their display,
 * and the selection of an avatar by the user.
 */
public class AvatarSelectionController {

    @FXML
    private HBox avatarContainer;

    @FXML
    private ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8;

    private Image selectedImage;
    private String selectedImagePath;
    private Stage stage;
    private ProfileViewController profileViewController;

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded. It sets up the avatar images,
     * their display properties, and click event handlers.
     */
    @FXML
    public void initialize() {
        // Array of ImageViews and their corresponding image paths
        ImageView[] imageViews = {imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8};
        String[] imagePaths = {
                "/Avatar/icon_2.png",
                "/Avatar/img_7.png",
                "/Avatar/img_8.png",
                "/Avatar/img.png",
                "/Avatar/img_1.png",
                "/Avatar/img_2.png",
                "/Avatar/img_3.png",
                "/Avatar/img_4.png"
        };

        for (int i = 0; i < imageViews.length; i++) {
            final String path = imagePaths[i]; // Declare as final inside the loop
            try {
                final ImageView imageView = imageViews[i]; // Declare as final inside the loop

                // Load the image and set it to the ImageView
                imageView.setImage(new Image(getClass().getResourceAsStream(path)));
                imageView.setFitHeight(130);
                imageView.setFitWidth(130);
                imageView.setPickOnBounds(true);
                imageView.setPreserveRatio(true);

                // Create a Circle clip and adjust the center and radius
                Circle clip = new Circle(65, 65, 65); // centerX, centerY, radius
                imageView.setClip(clip);

                // Set the click event handler for selecting the avatar
                imageView.setOnMouseClicked(event -> selectAvatar(imageView, path));

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Could not load image: " + path);
            }
        }
    }

    /**
     * Sets the stage and the ProfileViewController for this instance.
     * This method is used to pass the stage and the controller that will
     * handle the selected avatar.
     *
     * @param stage The stage where the avatar selection window is displayed.
     * @param profileViewController The controller that will handle the selected avatar.
     */
    public void setStageAndController(Stage stage, ProfileViewController profileViewController) {
        this.stage = stage;
        this.profileViewController = profileViewController;
    }

    /**
     * Handles the selection of an avatar. This method sets the selected image
     * and its path, updates the profile view controller with the selected avatar,
     * and closes the avatar selection window.
     *
     * @param imageView The ImageView containing the selected avatar.
     * @param path The path of the selected avatar image.
     */
    private void selectAvatar(ImageView imageView, String path) {
        selectedImage = imageView.getImage();
        selectedImagePath = path; // Store the path of the selected image
        System.out.println("Avatar selected: " + selectedImage.getUrl());

        // Update the profile view controller with the selected avatar
        if (profileViewController != null) {
            profileViewController.setAvatar(selectedImage);
        }

        // Close the avatar selection window
        if (stage != null) {
            stage.close();
        }
    }

    /**
     * Gets the selected avatar image.
     *
     * @return The selected avatar image.
     */
    public Image getSelectedImage() {
        return selectedImage;
    }

    /**
     * Gets the path of the selected avatar image.
     *
     * @return The path of the selected avatar image.
     */
    public String getSelectedImagePath() {
        return selectedImagePath;
    }
}