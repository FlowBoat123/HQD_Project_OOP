package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.Book;
import org.example.javafxtutorial.CommentController;
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

  @FXML
  private ImageView commentIcon;

  @FXML
  private VBox commentsContainer; // Add this line

  private StackPane mainView;
  private Node previousContent;
  private Consumer<Void> refreshLibraryViewCallback;
  Book book;

  private LibraryService libraryService;
  private List<Comment> comments = new ArrayList<>(); // Add this line

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

  @FXML
  private void handleComment() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/javafxtutorial/userComment.fxml"));
      Parent commentView = loader.load();
      Scene commentScene = new Scene(commentView);
      Stage commentStage = new Stage();
      commentStage.setTitle("Comments");
      commentStage.setScene(commentScene);
      commentStage.showAndWait(); // Use showAndWait to wait for the window to close

      // Get the comment from the CommentController
      CommentController commentController = loader.getController();
      String comment = commentController.getComment();
      String username = commentController.getUsername();
      ImageView userImage = commentController.getUserImage();
      if (comment != null && !comment.isEmpty()) {
        comments.add(new Comment(username, userImage, comment));
        displayComments();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void displayComments() {
    commentsContainer.getChildren().clear();
    for (Comment comment : comments) {
      HBox commentBox = new HBox();
      commentBox.getChildren().add(comment.getUserImage());
      VBox commentContent = new VBox();
      Label usernameLabel = new Label(comment.getUsername());
      usernameLabel.setFont(new javafx.scene.text.Font(18.0));
      Label commentLabel = new Label(comment.getComment());
      commentLabel.setWrapText(true);
      commentLabel.setMaxWidth(624.0); // Set the maximum width to match the TextArea width
      commentContent.getChildren().addAll(usernameLabel, commentLabel);
      commentBox.getChildren().add(commentContent);
      commentsContainer.getChildren().add(commentBox);
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

  private static class Comment {
    private final String username;
    private final ImageView userImage;
    private final String comment;

    public Comment(String username, ImageView userImage, String comment) {
      this.username = username;
      this.userImage = userImage;
      this.comment = comment;
    }

    public String getUsername() {
      return username;
    }

    public ImageView getUserImage() {
      return userImage;
    }

    public String getComment() {
      return comment;
    }
  }
}