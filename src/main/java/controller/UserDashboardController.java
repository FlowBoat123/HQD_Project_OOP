package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Label loadingLabel;

    @FXML
    void launchShelfView(ActionEvent event) {
        Object source = event.getSource();
        Button clickedButton = (Button) source;
        String shelfName = clickedButton.getText();
        loadView("/org/example/javafxtutorial/shelf-view.fxml", (loader) -> {
            ShelfController shelfController = loader.getController();
            shelfController.initializeShelfView(new Shelf(shelfName));
        });
    }

    @FXML
    void launchBrowse(ActionEvent event) {
        loadView("/org/example/javafxtutorial/browse-view.fxml", (loader) -> {
            BrowseUserViewController browseViewController = loader.getController();
            browseViewController.setLibraryService(libraryService);
            browseViewController.setMainView(mainView);
            browseViewController.init();
        });
    }

    @FXML
    void launchRecommendation(ActionEvent event) {
        loadView("/org/example/javafxtutorial/recom-view.fxml", (loader) -> {
            RecomUserViewController recomUserViewController = loader.getController();
            recomUserViewController.setLibraryService(libraryService);
            recomUserViewController.setMainView(mainView);
            recomUserViewController.init();
        });
    }

    private void loadView(String fxmlPath, LoaderCallback callback) {
        showLoading();
        new Thread(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Node content = loader.load();
                Platform.runLater(() -> {
                    mainView.getChildren().setAll(content);
                    callback.call(loader);
                    hideLoading();
                });
            } catch (IOException e) {
                e.printStackTrace();
                Platform.runLater(this::hideLoading);
            }
        }).start();
    }

    private void showLoading() {
        Platform.runLater(() -> loadingLabel.setVisible(true));
    }

    private void hideLoading() {
        Platform.runLater(() -> loadingLabel.setVisible(false));
    }

    private interface LoaderCallback {
        void call(FXMLLoader loader);
    }
}