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

/**
 * Controller class for managing the book view in a JavaFX application.
 * This class handles the display of book details, updating book quantities,
 * and navigating back to the main view.
 */
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
    private Book book;
    private boolean isShowingCover = true;

    private LibraryService libraryService;

    /**
     * Initializes the book view for an admin user. This method sets up the book details,
     * including title, author, description, genre, quantity, and borrowed copies.
     * It also configures the number picker for adding copies and displays the book cover
     * and QR code for the book preview.
     *
     * @param book The book object containing the details to be displayed.
     */
    public void initializeBookViewForAdmin(Book book) {
        // Set up the number picker for adding copies
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

        // Set up the book cover image
        bookCover.setFitWidth(200);
        bookCover.setFitHeight(300);
        bookCover.setPreserveRatio(true);
        bookCover.setSmooth(true);
        if (book.getCoverImgUrl() != null) {
            bookCover.setImage(new Image(book.getCoverImgUrl(), true));
        }

        // Generate and display the QR code for the book preview
        QRGenerator.displayQRCode(book.getPreviewUrl(), bookPreviewQR);
        bookPreviewQR.setVisible(false);

        // Set up the click event to toggle between book cover and QR code
        imageStackPane.setOnMouseClicked(event -> toggleImage());
    }

    /**
     * Handles the action of updating the book's quantity in the library.
     * This method adds the selected number of copies to the library and updates
     * the displayed quantity.
     *
     * @param event The action event triggered by the user.
     */
    @FXML
    void handleUpdateBook(ActionEvent event) {
        libraryService.addCopiesToLibrary(book, numberPicker.getValue());
        System.out.println("current quantity: " + book.getQuantity());
        quantityLabel.setText("Number of copies in library: " + book.getQuantity());
        showNotificationAlert("Added successfully!", false);
    }

    /**
     * Sets the main view and the previous content for navigation purposes.
     *
     * @param mainView The main view where the book details are displayed.
     * @param previousContent The previous content to be restored when navigating back.
     */
    public void setMainView(AnchorPane mainView, Node previousContent) {
        this.mainView = mainView;
        this.previousContent = previousContent;
    }

    /**
     * Handles the action of going back to the previous content.
     * This method restores the previous content in the main view and refreshes
     * the library view if a callback is provided.
     */
    @FXML
    private void handleGoBack() {
        if (mainView != null && previousContent != null) {
            mainView.getChildren().setAll(previousContent);
            if (refreshLibraryViewCallback != null) {
                refreshLibraryViewCallback.accept(null);
            }
        }
    }

    /**
     * Handles the action of removing all copies of the book from the library.
     * This method checks if the book is currently borrowed and prevents removal
     * if any copies are borrowed. Otherwise, it removes all copies and updates
     * the displayed quantity.
     *
     * @param event The action event triggered by the user.
     */
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

    /**
     * Sets the library service for managing book operations.
     *
     * @param libraryService The library service instance.
     */
    public void setLibraryService(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    /**
     * Sets the callback for refreshing the library view after an action.
     *
     * @param refreshLibraryViewCallback The callback function to refresh the library view.
     */
    public void setRefreshLibraryViewCallback(Consumer<Void> refreshLibraryViewCallback) {
        this.refreshLibraryViewCallback = refreshLibraryViewCallback;
    }

    /**
     * Displays a notification alert to the user.
     *
     * @param message The message to be displayed in the alert.
     * @param warning Indicates whether the alert is a warning or an information alert.
     */
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

    /**
     * Toggles between displaying the book cover and the book preview QR code.
     */
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