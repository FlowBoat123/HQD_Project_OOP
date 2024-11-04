package org.example.javafxtutorial;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Scanner;


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
    private ComboBox<String> bookStatus;

    @FXML
    private Button updateBtn;
    int initStatus = -1;
    int currStatus = -1;
    Book book;

    public void initializeBookView(Book book) {
        this.book = book;
        bookStatus.getItems().addAll("None", "Reading", "Plan to Read", "On Hold", "Completed");
        bookTitle.setText(book.getTitle());
        bookAuthor.setText(book.getAuthorsAsString());
        bookDescription.setText(book.getDescription());
        genreLabel.setText(book.getGenresAsString());
        bookCover.setFitWidth(200);
        bookCover.setFitHeight(300);
        bookCover.setPreserveRatio(true);
        bookCover.setSmooth(true);
        initStatus = LibraryService.getInstance().checkIfBookExists(book);
        if(book.getCoverImgUrl() != null) {
            bookCover.setImage(new Image(book.getCoverImgUrl(), true));
        }
        setInitStatus();
    }
    @FXML
    void changeCurrentBookStatus(ActionEvent event) {
        String selectedStatus = bookStatus.getValue();
        switch (selectedStatus) {
            case "Reading":
                currStatus = Book.READING;
                break;
            case "Plan to Read":
                currStatus = Book.PLAN_TO_READ;
                break;
            case "On Hold":
                currStatus = Book.ON_HOLD;
                break;
            case "Completed":
                currStatus = Book.COMPLETED;
                break;
            default:
                currStatus = -1;
                break;
        }
        System.out.println(currStatus);
    }
    @FXML
    void updateBookStatus(ActionEvent event) {
        if (currStatus == initStatus) return;
        if (initStatus == -1) {
            book.setStatus(currStatus);
            LibraryService.getInstance().addBook(book);
            updateBtn.setText("Update");
            showNotification("Add book to " + bookStatus.getValue());
        } else {
            book.setStatus(currStatus);
            LibraryService.getInstance().updateBookStatus(book.getTitle(), currStatus);
            showNotification("Book status updated to " + bookStatus.getValue());
        }
        initStatus = currStatus;
    }

    private void setInitStatus() {
        switch (initStatus) {
            case Book.READING:
                bookStatus.setValue("Reading");
                break;
            case Book.PLAN_TO_READ:
                bookStatus.setValue("Plan to Read");
                break;
            case Book.ON_HOLD:
                bookStatus.setValue("On Hold");
                break;
            case Book.COMPLETED:
                bookStatus.setValue("Completed");
                break;
            default:
                bookStatus.setValue("None");
                updateBtn.setText("Add to Library");
                break;
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

}
