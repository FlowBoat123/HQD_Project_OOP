package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.example.javafxtutorial.Shelf;
import org.example.javafxtutorial.ShelfController;

import java.io.IOException;

public class UserDashboardController {
    @FXML
    private AnchorPane mainView;

    @FXML
    void launchShelfView(ActionEvent event) {
        Object source = event.getSource();
        Button clickedButton = (Button) source;
        String shelfName = clickedButton.getText();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/javafxtutorial/shelf-view.fxml"));
            Node content = loader.load();
            ShelfController shelfController = loader.getController();
            shelfController.initializeShelfView(new Shelf(shelfName));
            mainView.getChildren().setAll(content);
            clickedButton.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
