package org.example.javafxtutorial;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField searchField;
    @FXML
    private void launchSearch() throws Exception {
        welcomeText.setText("Welcome to JavaFX Application!");
        String search = searchField.getText();
        System.out.println("Search: " + search);
        ArrayList<Book> searchResult = GoogleAPI.searchBook(search);
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("book-view.fxml"));
        AnchorPane root = loader.load();

        // Check if the FXML loaded correctly
        if (root == null) {
            System.out.println("Error loading book-view.fxml");
            return;
        }

        // Get the controller for the book view
        BookController bookController = loader.getController();

        // Pass the book data to the controller to initialize the view
        bookController.initializeBookView(book);

        // Setup and show the new scene
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("bookview.css").toExternalForm());
        Stage bookStage = new Stage();
        bookStage.setScene(scene);
        bookStage.setTitle("Book View");
        bookStage.setResizable(false);
        bookStage.initModality(Modality.APPLICATION_MODAL);  // window can't resize
        bookStage.show();
    }
}