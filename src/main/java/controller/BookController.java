package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import logic.Book;
import org.example.javafxtutorial.LibraryService;

import java.util.function.Consumer;


public class BookController {

    @FXML
    private Label bookAuthor;

    @FXML
    private ImageView bookCover;

    @FXML
    private Label bookDescription;

    @FXML
    private Label bookTitle;

    @FXML
    private Label genreLabel;

    @FXML
    private Label notificationLabel;

    @FXML
    private Spinner<Integer> numberPicker;

    @FXML
    private ImageView returnBtn;

    private AnchorPane mainView;
    private Node previousContent;
    private Consumer<Void> refreshLibraryViewCallback;
    Book book;

    private LibraryService libraryService;

    public void initializeBookViewForAdmin(Book book) {
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        numberPicker.setValueFactory(valueFactory);
        this.book = book;
        bookTitle.setText(book.getTitle());
        bookAuthor.setText(book.getAuthorsAsString());
        bookDescription.setText(book.getDescription());
        genreLabel.setText(book.getGenresAsString());
        bookCover.setFitWidth(200);
        bookCover.setFitHeight(300);
        bookCover.setPreserveRatio(true);
        bookCover.setSmooth(true);
        if(book.getCoverImgUrl() != null) {
            bookCover.setImage(new Image(book.getCoverImgUrl(), true));
        }
    }

    @FXML
    void handleUpdateBook(ActionEvent event) {
        libraryService.addCopiesToLibrary(book, numberPicker.getValue());
        showNotification("Added successfully!");
    }

    public void setMainView(AnchorPane mainView, Node previousContent) {
        this.mainView = mainView;
        this.previousContent = previousContent;
    }

    @FXML
    private void handleGoBack() {
        if (mainView != null && previousContent != null) {
            mainView.getChildren().setAll(previousContent);
            if (refreshLibraryViewCallback != null) {
                refreshLibraryViewCallback.accept(null);
            }
        }
    }

    @FXML
    void handleRemoveAll(ActionEvent event) {
        libraryService.removeCopiesFromLibrary(book);
        showNotification("Removed all copies successfully!");
    }

    private void showNotification(String message) {
        notificationLabel.setText(message);
        notificationLabel.setVisible(true);
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(2000),
                ae -> notificationLabel.setVisible(false)));
        timeline.play();
    }

    public void setLibraryService(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    public void setRefreshLibraryViewCallback(Consumer<Void> refreshLibraryViewCallback) {
        this.refreshLibraryViewCallback = refreshLibraryViewCallback;
    }
}
