package controller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.example.javafxtutorial.UserProfileController;

public class AvatarSelectionController {

    @FXML
    private HBox avatarContainer;

    @FXML
    private ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8;

    private Image selectedImage;
    private Stage stage;
    private UserProfileController userProfileController;

    @FXML
    public void initialize() {
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
            String path = null;
            try {
                ImageView imageView = imageViews[i];
                path = imagePaths[i];

                imageView.setImage(new Image(getClass().getResourceAsStream(path)));
                imageView.setFitHeight(130);
                imageView.setFitWidth(130);
                imageView.setPickOnBounds(true);
                imageView.setPreserveRatio(true);

                // Create a Circle clip and adjust the center and radius
                Circle clip = new Circle(65, 65, 65); // centerX, centerY, radius
                imageView.setClip(clip);

                imageView.setOnMouseClicked(event -> selectAvatar(imageView));

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Could not load image: " + path);
            }
        }
    }

    public void setStageAndController(Stage stage, UserProfileController userProfileController) {
        this.stage = stage;
        this.userProfileController = userProfileController;
    }

    private void selectAvatar(ImageView imageView) {
        selectedImage = imageView.getImage();
        System.out.println("Avatar selected: " + selectedImage.getUrl());

        if (userProfileController != null) {
            userProfileController.setAvatar(selectedImage);
        }

        // Close the avatar selection window
        if (stage != null) {
            stage.close();
        }
    }

    public Image getSelectedImage() {
        return selectedImage;
    }
}
