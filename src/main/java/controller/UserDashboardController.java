package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import org.example.javafxtutorial.BrowseUserViewController;
import org.example.javafxtutorial.LibraryService;
import org.example.javafxtutorial.RecomUserViewController;
import org.example.javafxtutorial.Shelf;
import org.example.javafxtutorial.ShelfController;

import java.io.IOException;

public class UserDashboardController implements Initializable {

    private LibraryService libraryService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        libraryService = new LibraryService();
    }

    @FXML
    private StackPane mainView;

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

    @FXML
    void launchBrowse(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/javafxtutorial/browse-view.fxml"));
            Node content = loader.load();
            BrowseUserViewController browseViewController = loader.getController();
            browseViewController.setLibraryService(libraryService);
            browseViewController.setMainView(mainView);
            browseViewController.init();
            mainView.getChildren().setAll(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void launchRecommendation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/javafxtutorial/recom-view.fxml"));
            Node content = loader.load();
            RecomUserViewController recomUserViewController = loader.getController();
            recomUserViewController.setLibraryService(libraryService);
            recomUserViewController.setMainView(mainView);
            recomUserViewController.init();
            mainView.getChildren().setAll(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}