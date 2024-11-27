package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logic.LibraryService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable{

    private LibraryService libraryService;

    private Button currentlyFocusedButton;

    @FXML
    private AnchorPane mainView;

    @FXML
    void launchAPI(ActionEvent event) {
        Object source = event.getSource();
        Button clickedButton = (Button) source;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/api.fxml"));
            Node content = loader.load();
            GoogleAPIController apiController = loader.getController();
            apiController.setMainView(mainView);
            mainView.getChildren().setAll(content);
            apiController.setLibraryService(libraryService);
            updateFocus(clickedButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void launchAddUser(ActionEvent event) {
        Object source = event.getSource();
        Button clickedButton = (Button) source;
        updateFocus(clickedButton);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/signup.fxml"));
            Node content = loader.load();
            SignupController signUpController = loader.getController();
            signUpController.setFromLogin(false);
            mainView.getChildren().setAll(content);
            clickedButton.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void launchLibView(ActionEvent event) {
        Object source = event.getSource();
        Button clickedButton = (Button) source;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/lib-view.fxml"));
            Node content = loader.load();
            LibraryViewController libraryViewController = loader.getController();
            libraryViewController.setLibraryService(libraryService);
            libraryViewController.setMainView(mainView);
            libraryService.updateBooksRequestNumber();
            libraryViewController.initializeLibraryView(libraryService.getBooks());
            mainView.getChildren().setAll(content);
            updateFocus(clickedButton);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void launchUserInfo(ActionEvent event) {
        Object source = event.getSource();
        Button clickedButton = (Button) source;
        updateFocus(clickedButton);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/user-form.fxml"));
            Node content = loader.load();
            UserFormController userInfoController = loader.getController();
            userInfoController.setLibraryService(libraryService);
            mainView.getChildren().setAll(content);
            clickedButton.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        libraryService = new LibraryService();
    }

    private void updateFocus(Button clickedButton) {
        if (currentlyFocusedButton != null) {
            currentlyFocusedButton.getStyleClass().remove("focused-button");
            currentlyFocusedButton.getStyleClass().add("transparent-button");
        }
        clickedButton.requestFocus();
        clickedButton.getStyleClass().remove("transparent-button");
        clickedButton.getStyleClass().add("focused-button");
        currentlyFocusedButton = clickedButton;
    }

    public void backToLogin(ActionEvent actionEvent) {
        try {
            // Load the login FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/login.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
