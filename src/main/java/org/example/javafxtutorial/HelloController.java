package org.example.javafxtutorial;

import controller.AdminDashboardController;
import controller.BookController;
import controller.UserDashboardController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.Book;

import java.io.IOException;
import java.util.ArrayList;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField searchField;
    @FXML
    void launchDashboard() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserDashboard.fxml"));
        UserDashboardController userDashboardController = loader.getController();
        BorderPane root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("My Library");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    @FXML
    private void launchSearch() throws Exception {
        welcomeText.setText("Welcome to JavaFX Application!");
        String search = searchField.getText();
        System.out.println("Search: " + search);
        ArrayList<Book> searchResult = GoogleAPI.searchBook(search, "isbn");
        if (searchResult == null || searchResult.isEmpty()) {
            welcomeText.setText("No results found!");
        } else {
            // Open the new window with book details
            openBookView(searchResult.get(0));  // Display the first book result
        }

//        AnchorPane root = loader.load();

//        Scene scene = new Scene(root);
//        scene.getStylesheets().add(getClass().getResource("bookview.css").toExternalForm());
//        Stage transtage = new Stage();
//        transtage.setScene(scene);
//
//        transtage.setTitle("Book View");
//        transtage.setResizable(false);
//        transtage.initModality(Modality.APPLICATION_MODAL);
//        transtage.show();

        //bookController.initializeBookView(searchResult.getFirst());
    }
    private void openBookView(Book book) throws Exception {

    }
}