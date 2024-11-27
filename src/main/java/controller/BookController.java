package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import logic.Book;
import logic.LibraryService;
import logic.QRGenerator;

import java.util.function.Consumer;


public class BookController {

    @FXML
    private Label bookAuthor;

    @FXML
    private ImageView bookCover;

    @FXML
    private ImageView bookPreviewQR;

    @FXML
    private Label bookDescription;

    @FXML
    private Label bookTitle;

    @FXML
    private Label borrowedNumLabel;

    @FXML
    private Label genreLabel;

    @FXML
    private Spinner<Integer> numberPicker;

    @FXML
    private Label quantityLabel;

    @FXML
    private StackPane imageStackPane;

    private AnchorPane mainView;
    private Node previousContent;
    private Consumer<Void> refreshLibraryViewCallback;
    Book book;
    private boolean isShowingCover = true;

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
        quantityLabel.setText("Number of copies in library: " + book.getQuantity());
        borrowedNumLabel.setText("Number of people currently borrowed: " + book.getBorrowedCopies());
        bookCover.setFitWidth(200);
        bookCover.setFitHeight(300);
        bookCover.setPreserveRatio(true);
        bookCover.setSmooth(true);
        QRGenerator.displayQRCode(book.getPreviewUrl(), bookPreviewQR);
        bookPreviewQR.setVisible(false);
        if(book.getCoverImgUrl() != null) {
            bookCover.setImage(new Image(book.getCoverImgUrl(), true));
        }
        imageStackPane.setOnMouseClicked(event -> toggleImage());
    }

    @FXML
    void handleUpdateBook(ActionEvent event) {
        libraryService.addCopiesToLibrary(book, numberPicker.getValue());
        System.out.println("current quantity: " + book.getQuantity());
        quantityLabel.setText("Number of copies in library: " + book.getQuantity());
        showNotificationAlert("Added successfully!", false);
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
        if (book == null) {
            showNotificationAlert("Book not found!", true);
        } else if (book.getBorrowedCopies() > 0) {
            showNotificationAlert("Cannot remove all copies, some are borrowed!", true);
        } else {
            libraryService.removeCopiesFromLibrary(book);
            System.out.println("current quantity: " + book.getQuantity());
            quantityLabel.setText("Number of copies in library: " + book.getQuantity());
            showNotificationAlert("Removed all copies successfully!", false);
        }
    }

    public void setLibraryService(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    public void setRefreshLibraryViewCallback(Consumer<Void> refreshLibraryViewCallback) {
        this.refreshLibraryViewCallback = refreshLibraryViewCallback;
    }

    void showNotificationAlert(String message, boolean warning) {
        Alert alert;
        if (warning) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
        } else {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notification");
            alert.setHeaderText(null);
        }
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void toggleImage() {
        if (isShowingCover) {
            bookCover.setVisible(false);
            bookPreviewQR.setVisible(true);
        } else {
            bookCover.setVisible(true);
            bookPreviewQR.setVisible(false);
        }
        isShowingCover = !isShowingCover;
    }
}
