package controller;

import java.util.function.Consumer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import logic.Book;
import logic.BookLoan;
import org.example.javafxtutorial.LibraryService;

public class BookUserView {

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
    private Button readButton;

    private int loanStatus;
    private StackPane mainView;
    private Node previousContent;
    private Consumer<Void> refreshLibraryViewCallback;
    Book book;

    private LibraryService libraryService;

    public void initializeBookViewForUser(Book book) {
        this.book = book;
        loanStatus = libraryService.getLoanStatus(book); // check loan status
        book.setStatus("Unread");//khi init check status
        updateLoanStatus();
        bookTitle.setText(book.getTitle());
        bookAuthor.setText(book.getAuthorsAsString());
        bookDescription.setText(book.getDescription());
        genreLabel.setText(book.getGenresAsString());
        bookCover.setFitWidth(200);
        bookCover.setFitHeight(300);
        readButton.setText(book.getStatus());
        bookCover.setPreserveRatio(true);
        bookCover.setSmooth(true);
        if (book.getCoverImgUrl() != null) {
            bookCover.setImage(new Image(book.getCoverImgUrl(), true));
        }
    }

    @FXML
    private void handleGoBack() {
        if (mainView != null && previousContent != null) {
            mainView.getChildren().remove(mainView.getChildren().size() - 1);
        }
    }

    @FXML
    void handleUpdateBook(ActionEvent event) {
        if (loanStatus == BookLoan.NOT_BORROWED) {
            borrowBook();
            showNotification("Book borrowed successfully");
        } else if (loanStatus == BookLoan.READING) {

        }
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

    public void setMainView(StackPane mainView, Node previousContent) {
        this.mainView = mainView;
        this.previousContent = previousContent;
    }

    public void updateLoanStatus() {
        switch (loanStatus) {
            case BookLoan.NOT_BORROWED:
                readButton.setText("Borrow");
                break;
            case BookLoan.READING:
                readButton.setText("Return");
                break;
            case BookLoan.WAITING:
                readButton.setText("Waiting");
                break;
            case BookLoan.COMPLETED:
                readButton.setText("Completed");
                break;
            default:
                readButton.setText("Unavailable");
                break;
        }
    }

    //Borrow book
    public void borrowBook() {
        if (libraryService.borrowBook(book)) {
            loanStatus = BookLoan.READING;
            book.setStatus("Reading");
            showNotification("Book borrowed successfully");
            updateLoanStatus();
        } else {
            loanStatus = BookLoan.WAITING;
            showNotification("Book is not available");
        }
    }

    //Return book
    public void returnBook() {
        libraryService.returnBook(book);
        loanStatus = BookLoan.NOT_BORROWED;
        book.setStatus("Unread");
        showNotification("Book returned successfully");
        updateLoanStatus();
    }
}