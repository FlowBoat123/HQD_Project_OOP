package controller;

import javafx.concurrent.Task;
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

        bookTitleButton.setText(book.getTitle());
        bookAuthor.setText(book.getAuthorsAsString());

        if (book.getCoverImgUrl() != null && !book.getCoverImgUrl().isEmpty()) {
            loadImageAsync(book.getCoverImgUrl());
        }
    }

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

    @FXML
    private void handleBookTitleButtonClick() {
        userViewController.launchBookView(book);
    }
}