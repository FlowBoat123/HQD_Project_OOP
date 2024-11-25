package org.example.javafxtutorial;

import controller.BookUserView;
import controller.UserViewController;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.Book;
import logic.BookLoan;

import java.io.IOException;
import java.util.List;

public class ShelfController extends UserViewController {

    private boolean userProfileView;

    @FXML
    private GridPane bookGridPane;

    @FXML
    private HBox shelfHeader;

    @FXML
    private Label shelfTitle;

    @FXML
    private int userID;

    @Override
    public void init() {
        try {
            loadBooksToShelf();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {

    }

    @Override
    public void launchBookView(Book book) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/javafxtutorial/BookUserview.fxml"));
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

    public void setShelfTitle(String title) {
        shelfTitle.setText(title);
    }

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
                    getClass().getResource("/org/example/javafxtutorial/book-card.fxml"));
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

    public void hideShelfHeader() {
        shelfHeader.setVisible(false);
        shelfHeader.setManaged(false);
    }

    public void setUpShelfViewForUserProfile(int userID) {
        this.userProfileView = true;
        this.userID = userID;
        hideShelfHeader();

    }
}
