package controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.Book;
import logic.BookLoan;
import logic.UserSession;

import java.io.IOException;
import java.util.List;

/**
 * Controller class for the Shelf View.
 * Handles the initialization and event handling for the shelf view in the user interface.
 * Manages the display of books on the shelf based on loan status and user interactions.
 */
public class ShelfController extends UserViewController {

    /**
     * Flag to indicate if the view is in user profile mode.
     */
    private boolean userProfileView;

    /**
     * GridPane to display the books.
     */
    @FXML
    private GridPane bookGridPane;

    /**
     * HBox for the shelf header.
     */
    @FXML
    private HBox shelfHeader;

    /**
     * Label to display the shelf title.
     */
    @FXML
    private Label shelfTitle;

    /**
     * User ID for the current user.
     */
    @FXML
    private int userID;

    /**
     * Initializes the shelf view.
     * Loads books to the shelf based on the current user and shelf title.
     */
    @Override
    public void init() {
        try {
            loadBooksToShelf();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the controller.
     */
    @FXML
    private void initialize() {

    }

    /**
     * Launches the book view for the selected book.
     * Loads the book view FXML and initializes it with the selected book's details.
     *
     * @param book The Book object to be displayed.
     */
    @Override
    public void launchBookView(Book book) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/BookUserview.fxml"));
            Node content = loader.load();
            BookUserView bookUserView = loader.getController();
            bookUserView.setMainView(mainView, mainView.getChildren().get(0));
            mainView.getChildren().add(content);
            bookUserView.setLibraryService(libraryService);
            bookUserView.setShelfController(this);
            if (userProfileView) {
                bookUserView.setUpBookViewForUserProfile();
            }
            bookUserView.initializeBookViewForUser(book);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the shelf title.
     *
     * @param title The title to be set.
     */
    public void setShelfTitle(String title) {
        shelfTitle.setText(title);
    }

    /**
     * Refreshes the shelf view.
     * Clears the current books and reloads them based on the current user and shelf title.
     */
    public void refreshView() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                System.out.println("Refreshing shelf view...");
                loadBooksToShelf();
                return null;
            }
        };

        task.setOnSucceeded(event -> {
            Platform.runLater(() -> {
                bookGridPane.getChildren().clear();
                try {
                    loadBooksToShelf();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });

        new Thread(task).start();
    }

    /**
     * Loads books to the shelf based on the current user and shelf title.
     *
     * @throws IOException If there is an error loading the book card FXML.
     */
    private void loadBooksToShelf() throws IOException {
        List<Book> books;
        String shelfName = shelfTitle.getText();
        if (userID == 0 && UserSession.getInstance().getUserID() != 0) {
            userID = UserSession.getInstance().getUserID();
        }
        switch (shelfName) {
            case "Currently Reading":
                books = libraryService.getBooksByLoanStatus(BookLoan.READING, userID);
                break;
            case "Waiting":
                books = libraryService.getBooksByLoanStatus(BookLoan.WAITING, userID);
                break;
            case "Completed":
                books = libraryService.getBooksByLoanStatus(BookLoan.COMPLETED, userID);
                break;
            case "All":
                books = libraryService.getAllBooksHasLoan();
                break;
            default:
                books = null;
                break;
        }
        int row = 1;
        int col = 0;
        for (Book book : books) {
            FXMLLoader cardLoader = new FXMLLoader(
                    getClass().getResource("/application/book-card.fxml"));
            VBox bookCard = cardLoader.load();
            BookCardController bookCardController = cardLoader.getController();
            bookCardController.setBook(book, this);

            if (col == 5) {
                col = 0;
                ++row;
            }

            bookGridPane.add(bookCard, col++, row);
            GridPane.setMargin(bookCard, new Insets(10));
        }
    }

    /**
     * Hides the shelf header.
     */
    public void hideShelfHeader() {
        shelfHeader.setVisible(false);
        shelfHeader.setManaged(false);
    }

    /**
     * Sets up the shelf view for the user profile.
     * Hides the shelf header and sets the user ID.
     *
     * @param userID The user ID to be set.
     */
    public void setUpShelfViewForUserProfile(int userID) {
        this.userProfileView = true;
        this.userID = userID;
        hideShelfHeader();
    }
}