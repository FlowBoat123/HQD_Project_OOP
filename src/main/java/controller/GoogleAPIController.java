package controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.Book;
import org.example.javafxtutorial.GoogleAPI;
import org.example.javafxtutorial.LibraryService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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


    public void setMainView(AnchorPane mainView) {
        this.mainView = mainView;
    }

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
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/javafxtutorial/lib-view.fxml"));
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

    public void setLibraryService(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchMethodGroup = new ToggleGroup();
        searchMethodISBN.setToggleGroup(searchMethodGroup);
        searchMethodTitle.setToggleGroup(searchMethodGroup);
    }

    private void launchBookView(Book book) throws Exception {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/javafxtutorial/book-view.fxml"));
        Node content = loader.load();



        BookController bookController = loader.getController();

        bookController.initializeBookViewForAdmin(book);

        bookController.setLibraryService(libraryService);
//        Scene scene = new Scene((AnchorPane) content);
//        scene.getStylesheets().add(getClass().getResource("/org/example/javafxtutorial/bookview.css").toExternalForm());
//        Stage bookStage = new Stage();
//        bookStage.setScene(scene);
//        bookStage.setTitle("Book View");
//        bookStage.setResizable(false);
//        bookStage.initModality(Modality.APPLICATION_MODAL);  // window can't resize
//        bookStage.show();
        if (mainView != null) {
            bookController.setMainView(mainView, mainView.getChildren().getFirst());
            mainView.getChildren().setAll(content);
        }
    }
}
