package org.example.javafxtutorial;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class BookController {

    public void initializeBookView(Book book) {
        bookTitle.setText(book.getTitle());
        bookAuthor.setText(book.getAuthor());
        bookDescription.setText(book.getDescription());
        bookCover.setFitWidth(200);
        bookCover.setFitHeight(300);
        bookCover.setPreserveRatio(true);
        bookCover.setSmooth(true);
        if(book.getCoverImgUrl() != null) {
            bookCover.setImage(new Image(book.getCoverImgUrl(), true));
        }
    }

    @FXML
    private Text bookAuthor;

    @FXML
    private Text bookDescription;

    @FXML
    private Text bookTitle;

    @FXML
    private ImageView bookCover;


}
