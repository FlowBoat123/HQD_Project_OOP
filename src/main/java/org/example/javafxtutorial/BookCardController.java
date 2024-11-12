package org.example.javafxtutorial;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.Book;

public class BookCardController {
    @FXML
    private ImageView coverImg;
    @FXML
    private Label bookTitle;
    @FXML
    private Label bookAuthor;

    public void setBook(Book book) {
        if (book.getCoverImgUrl() != null && !book.getCoverImgUrl().isEmpty()) {
            coverImg.setImage(new Image(book.getCoverImgUrl()));
        }
        bookTitle.setText(book.getTitle());
        bookAuthor.setText(book.getAuthorsAsString());
    }
}