package controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import logic.Book;
import logic.GoogleAPI;
import logic.LibraryService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller class for handling Google API searches in a JavaFX application.
 * This class manages the search functionality, displaying search results,
 * and launching the book view or library view based on the search method.
 */
public class GoogleAPIController implements Initializable {

    @FXML
    private ProgressIndicator loadingIndicator;

    @FXML
    private TextField searchField;

    @FXML
    private AnchorPane mainView;

    @FXML
    private RadioButton searchMethodISBN;

    @FXML
    private RadioButton searchMethodTitle;

    private ToggleGroup searchMethodGroup;

    private LibraryService libraryService;

    /**
     * Sets the main view for this controller.
     *
     * @param mainView The main view where the search results will be displayed.
     */
    public void setMainView(AnchorPane mainView) {
        this.mainView = mainView;
    }

    /**
     * Handles the search action triggered by a mouse event.
     * This method retrieves the search text, determines the search method,
     * and initiates the search using the Google API.
     *
     * @param mouseEvent The mouse event that triggered the search.
     * @throws Exception If there is an error during the search process.
     */
    public void handleSearch(MouseEvent mouseEvent) throws Exception {
        String search = searchField.getText();
        System.out.println("Search: " + search);
        String searchMethod = searchMethodTitle.isSelected() ? "title" : "isbn";

        loadingIndicator.setVisible(true); // Show the loading indicator

        Task<ArrayList<Book>> task = new Task<>() {
            @Override
            protected ArrayList<Book> call() throws Exception {
                return GoogleAPI.searchBook(search, searchMethod);
            }
        };

        task.setOnSucceeded(event -> {
            ArrayList<Book> searchResult = task.getValue();
            Platform.runLater(() -> {
                loadingIndicator.setVisible(false); // Hide the loading indicator
                if (searchResult == null || searchResult.isEmpty()) {
                    System.out.println("No results found!");
                } else if (searchMethod.equals("isbn")) {
                    try {
                        launchBookView(searchResult.get(0));  // Display the first book result
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/lib-view.fxml"));
                        Node content = loader.load();
                        LibraryViewController libraryViewController = loader.getController();
                        libraryViewController.setMainView(mainView);
                        libraryViewController.setLibraryService(libraryService);
                        libraryViewController.initializeLibraryView(searchResult);
                        mainView.getChildren().setAll(content);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        });

        new Thread(task).start();
    }

    /**
     * Sets the library service for this controller.
     *
     * @param libraryService The library service instance.
     */
    public void setLibraryService(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    /**
     * Initializes the controller class. This method sets up the toggle group
     * for the search method radio buttons.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchMethodGroup = new ToggleGroup();
        searchMethodISBN.setToggleGroup(searchMethodGroup);
        searchMethodTitle.setToggleGroup(searchMethodGroup);
    }

    /**
     * Launches the book view for a given book.
     * This method loads the book view FXML, initializes the book view controller,
     * and sets the main view to display the book details.
     *
     * @param book The book to be displayed in the book view.
     * @throws Exception If there is an error loading the book view FXML.
     */
    private void launchBookView(Book book) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/book-view.fxml"));
        Node content = loader.load();

        BookController bookController = loader.getController();
        bookController.initializeBookViewForAdmin(book);
        bookController.setLibraryService(libraryService);

        if (mainView != null) {
            bookController.setMainView(mainView, mainView.getChildren().getFirst());
            mainView.getChildren().setAll(content);
        }
    }
}