package org.example.javafxtutorial;

import controller.UserViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.Book;

public class BookCardController {
    @FXML
    private ImageView coverImg;
    @FXML
    private Button bookTitleButton; // Changed from Label to Button
    @FXML
    private Label bookAuthor;

    private Book book;
    private UserViewController userViewController;

    public void setBook(Book book, UserViewController userViewController) {
        this.book = book;
        this.userViewController = userViewController;

        if (book.getCoverImgUrl() != null && !book.getCoverImgUrl().isEmpty()) {
            coverImg.setImage(new Image(book.getCoverImgUrl()));
        }

        bookTitleButton.setText(book.getTitle());
        bookAuthor.setText(book.getAuthorsAsString());
    }

    @FXML
    private void handleBookTitleButtonClick() {
        userViewController.launchBookView(book);
    }
}