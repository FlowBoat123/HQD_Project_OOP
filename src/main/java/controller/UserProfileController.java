package controller;

import database.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import logic.User;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class UserProfileController {
    @FXML
    private StackPane contentPane;  // Ensure this matches the fx:id in FXML

    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;

    @FXML
    private boolean isAdmin;

    private User user;

    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    public void setUser(User user) {
//        System.out.println(user.getUsername());
        this.user = user;
        isAdmin = false;
        showScene1(null);
    }

    public void setAdmin() {
        isAdmin = true;
    }

    @FXML
    public void showScene1(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/javafxtutorial/profile-view.fxml"));
            AnchorPane newContent = loader.load();
            ProfileViewController profileViewController = loader.getController();
            profileViewController.setUser(this.user);
            contentPane.getChildren().setAll(newContent);  // Ensure contentPane is not null
        } catch (IOException e) {
            e.printStackTrace();
        }
        button1.getStyleClass().add("button-active");
        button2.getStyleClass().remove("button-active");
        button3.getStyleClass().remove("button-active");
    }

    @FXML
    public void showScene2(ActionEvent event) {
//        loadFXMLContent("/org/example/javafxtutorial/shelf-view.fxml");
        button1.getStyleClass().remove("button-active");
        button2.getStyleClass().add("button-active");
        button3.getStyleClass().remove("button-active");
    }

    @FXML
    public void showScene3(ActionEvent event) {
//        loadFXMLContent("/scene3.fxml");
        button1.getStyleClass().remove("button-active");
        button2.getStyleClass().remove("button-active");
        button3.getStyleClass().add("button-active");
    }

//    private void loadFXMLContent(String fxmlFile) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
//            AnchorPane newContent = loader.load();
//            contentPane.getChildren().setAll(newContent);  // Ensure contentPane is not null
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @FXML
    public void initialize() {
//        showScene1(null);  // This should be called after contentPane is initialized
    }

    @FXML
    public void handleImageClick(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            FXMLLoader loader;
            if (user.getID() == 2 || this.isAdmin) {
                loader = new FXMLLoader(getClass().getResource("/org/example/javafxtutorial/AdminDashboard.fxml"));
            } else {
                loader = new FXMLLoader(getClass().getResource("/org/example/javafxtutorial/UserDashboard.fxml"));
            }

            Parent root = loader.load();
            Stage stage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

            if (user.getID() != 2 && !this.isAdmin) {
                UserDashboardController controller = loader.getController();
                controller.setUser(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAvatar(Image avatar) {
        // Implementation for setting avatar
    }

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

    @FXML
    private void handleEditBio() {
        if (isEditingDetail) {
            bioTextArea.setEditable(false);
            editBioButton.setText("Edit");
        } else {
            bioTextArea.setEditable(true);
            editBioButton.setText("Save");
        }
        isEditingDetail = !isEditingDetail;
    }
}
