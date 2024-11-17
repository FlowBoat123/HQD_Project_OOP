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

  private StackPane mainView;
  private Node previousContent;
  private Consumer<Void> refreshLibraryViewCallback;
  Book book;

  private LibraryService libraryService;

  public void initializeBookViewForUser(Book book) {
    this.book = book;
    book.setStatus("Unread");//khi init check status
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
    if(1+1 == 2){
      showNotification("This item is currently out of stock.");
    } else {
      readButton.setText("On Hold");
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
}