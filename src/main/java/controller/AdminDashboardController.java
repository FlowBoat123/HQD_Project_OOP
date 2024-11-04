package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.example.javafxtutorial.Shelf;
import org.example.javafxtutorial.ShelfController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboardController {

    @FXML
    private AnchorPane mainView;

    @FXML
    void launchAPI(ActionEvent event) {
        Object source = event.getSource();
        Button clickedButton = (Button) source;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/javafxtutorial/api.fxml"));
            Node content = loader.load();
            GoogleAPIController apiController = loader.getController();
            mainView.getChildren().setAll(content);
            clickedButton.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void launchAddUser(ActionEvent event) {

    }

    @FXML
    void launchLibView(ActionEvent event) {
        Object source = event.getSource();
        Button clickedButton = (Button) source;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/javafxtutorial/lib-view.fxml"));
            Node content = loader.load();
            LibraryViewController libraryViewController = loader.getController();
            mainView.getChildren().setAll(content);
            clickedButton.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void launchUserInfo(ActionEvent event) {

    }

}
