package controller;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.Book;

/**
 * Controller class for the Book Card view.
 * This class handles the initialization and event handling for individual book cards displayed in the user view.
 * It manages the display of book details and handles asynchronous loading of book cover images.
 */
public class BookCardController {

    /**
     * ImageView component to display the book cover image.
     */
    @FXML
    private ImageView coverImg;

    /**
     * Button component to display the book title.
     * This button also serves as a clickable element to launch the book view.
     */
    @FXML
    private Button bookTitleButton; // Changed from Label to Button

    /**
     * Label component to display the book author.
     */
    @FXML
    private Label bookAuthor;

    /**
     * The Book object associated with this card.
     */
    private Book book;

    /**
     * Reference to the UserViewController to handle interactions with the user view.
     */
    private UserViewController userViewController;

    /**
     * Sets the book details and the associated UserViewController for this book card.
     * Initializes the UI components with the book's title, author, and cover image.
     *
     * @param book The Book object to be displayed.
     * @param userViewController The UserViewController to handle interactions.
     */
    public void setBook(Book book, UserViewController userViewController) {
        this.book = book;
        this.userViewController = userViewController;

        bookTitleButton.setText(book.getTitle());
        bookAuthor.setText(book.getAuthorsAsString());

        if (book.getCoverImgUrl() != null && !book.getCoverImgUrl().isEmpty()) {
            loadImageAsync(book.getCoverImgUrl());
        }
    }

    /**
     * Loads the book cover image asynchronously.
     * This method ensures that the UI remains responsive while the image is being loaded.
     *
     * @param imageUrl The URL of the book cover image to be loaded.
     */
    private void loadImageAsync(String imageUrl) {
        Task<Image> loadImageTask = new Task<>() {
            @Override
            protected Image call() throws Exception {
                return new Image(imageUrl);
            }
        };

        loadImageTask.setOnSucceeded(event -> {
            coverImg.setImage(loadImageTask.getValue());
        });

        loadImageTask.setOnFailed(event -> {
            loadImageTask.getException().printStackTrace();
        });

        new Thread(loadImageTask).start();
    }

    /**
     * Event handler for the book title button click.
     * Launches the book view for the selected book.
     */
    @FXML
    private void handleBookTitleButtonClick() {
        userViewController.launchBookView(book);
    }
}