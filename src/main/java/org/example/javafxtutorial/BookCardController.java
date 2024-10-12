package org.example.javafxtutorial;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BookCardController {
    @FXML
    private Label bookAuthor;

    @FXML
    private Label bookTitle;

    @FXML
    private ImageView coverImg;

    private Book book;

    private void initializeBookCard(Book book) {
        this.book = book;
        bookAuthor.setText(book.getAuthorsAsString());
        bookTitle.setText(book.getTitle());
        if (coverImg != null) {
            coverImg.setImage(new Image(book.getCoverImgUrl(), true));
        }
    }
}
