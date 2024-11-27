package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.*;

/**
 * Controller class for the Book User View.
 * Handles the initialization and event handling for the book view in the user interface.
 * Manages the display of book details, loan status, comments, and interactions with the book.
 */
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
    private VBox commentsContainer;

    @FXML
    private ImageView bookPreviewQR;

    @FXML
    private StackPane imageStackPane;

    private int loanStatus;
    private StackPane mainView;
    private Node previousContent;
    private Book book;
    private ShelfController shelfController;
    private LibraryService libraryService;
    private List<Comment> comments = new ArrayList<>();
    private boolean inProfileView = false;
    private boolean isShowingCover = true;

    /**
     * Initializes the book view for the user.
     * Sets up the UI components with the book's details and handles the loan status.
     *
     * @param book The Book object to be displayed.
     */
    public void initializeBookViewForUser(Book book) {
        this.book = book;
        this.updateLoanStatus();
        bookTitle.setText(book.getTitle());
        bookAuthor.setText(book.getAuthorsAsString());
        bookDescription.setText(book.getDescription());
        genreLabel.setText(book.getGenresAsString());
        bookCover.setFitWidth(200);
        bookCover.setFitHeight(300);
        readButton.setText(book.getStatus());
        bookCover.setPreserveRatio(true);
        bookCover.setSmooth(true);
        QRGenerator.displayQRCode(book.getPreviewUrl(), bookPreviewQR);
        if (book.getCoverImgUrl() != null) {
            bookCover.setImage(new Image(book.getCoverImgUrl(), true));
        }
        bookPreviewQR.setVisible(false);
        if (loanStatus == BookLoan.WAITING && book.getBorrowedCopies() < book.getQuantity() && !inProfileView) {
            notifyReadyBookDialog();
        }
        comments = libraryService.getBookComments(book);
        imageStackPane.setOnMouseClicked(event -> toggleImage());
        displayComments();
    }

    /**
     * Handles the go back action.
     * Restores the previous content in the main view.
     */
    @FXML
    private void handleGoBack() {
        if (mainView != null && previousContent != null) {
            mainView.getChildren().remove(mainView.getChildren().size() - 1);
        }
    }

    /**
     * Handles the update book action.
     * Borrows or returns the book based on the current loan status.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    void handleUpdateBook(ActionEvent event) {
        if (loanStatus == BookLoan.NOT_BORROWED) {
            this.borrowBook();
        } else if (loanStatus == BookLoan.READING) {
            this.returnBook();
            showNotification("Book returned successfully");
        } else {
            showNotification("Book is not available");
        }
    }

    /**
     * Displays a notification message.
     *
     * @param message The message to be displayed.
     */
    private void showNotification(String message) {
        notificationLabel.setText(message);
        notificationLabel.setVisible(true);
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(2000),
                ae -> notificationLabel.setVisible(false)));
        timeline.play();
    }

    /**
     * Handles the comment action.
     * Opens a dialog to add a new comment and updates the comments display.
     */
    @FXML
    private void handleComment() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/userComment.fxml"));
            Parent commentView = loader.load();
            Scene commentScene = new Scene(commentView);
            Stage commentStage = new Stage();
            commentStage.setTitle("Comments");
            commentStage.setScene(commentScene);
            commentStage.showAndWait();
            CommentController commentController = loader.getController();
            int userId = commentController.getUserID();
            String comment = commentController.getComment();
            String username = commentController.getUsername();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String date = sdf.format(System.currentTimeMillis());
            ImageView userImage = commentController.getUserImage();
            if (comment != null && !comment.isEmpty()) {
                Comment newComment = new Comment(userId, username, userImage, comment, date, book.getIsbn_13());
                comments.add(newComment);
                libraryService.addComment(newComment);
                displayComments();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the comments for the book.
     */
    private void displayComments() {
        commentsContainer.getChildren().clear();
        for (Comment comment : comments) {
            HBox commentBox = new HBox();
            commentBox.setSpacing(12);
            commentBox.getChildren().add(comment.getUserImage());
            VBox commentDetails = new VBox();
            Label usernameLabel = new Label(comment.getUsername());
            usernameLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-font-family: Calibri");
            Label commentLabel = new Label(comment.getComment());
            commentLabel.setStyle("-fx-font-size: 18px; -fx-font-family: Calibri; -fx-margin-top: 10px");
            commentLabel.setWrapText(true);
            commentLabel.setMinHeight(Region.USE_PREF_SIZE);
            commentLabel.setMaxHeight(Double.MAX_VALUE);
            VBox.setVgrow(commentLabel, Priority.ALWAYS);
            Label dateLabel = new Label(comment.getDate());
            dateLabel.setStyle("-fx-font-size: 13px; -fx-font-family: Calibri");
            commentDetails.getChildren().addAll(usernameLabel, dateLabel, commentLabel);
            commentBox.getChildren().add(commentDetails);
            commentsContainer.getChildren().add(commentBox);
        }
        VBox.setVgrow(commentsContainer, Priority.ALWAYS);
    }

    /**
     * Sets the LibraryService instance.
     *
     * @param libraryService The LibraryService instance.
     */
    public void setLibraryService(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    /**
     * Sets the main view and previous content.
     *
     * @param mainView The main view container.
     * @param previousContent The previous content in the main view.
     */
    public void setMainView(StackPane mainView, Node previousContent) {
        this.mainView = mainView;
        this.previousContent = previousContent;
    }

    /**
     * Updates the loan status of the book.
     */
    public void updateLoanStatus() {
        loanStatus = libraryService.getLoanStatus(book);
        Platform.runLater(() -> {
            switch (loanStatus) {
                case BookLoan.NOT_BORROWED:
                    System.out.println("Not borrowed");
                    readButton.setText("Borrow");
                    break;
                case BookLoan.READING:
                    System.out.println("Reading");
                    readButton.setText("Return");
                    break;
                case BookLoan.WAITING:
                    System.out.println("Waiting");
                    readButton.setText("Waiting");
                    break;
                case BookLoan.COMPLETED:
                    System.out.println("Completed");
                    readButton.setText("Completed");
                    break;
                default:
                    System.out.println("Unavailable");
                    readButton.setText("Unavailable");
                    break;
            }
        });
    }

    /**
     * Borrows the book.
     * Updates the loan status and refreshes the view if necessary.
     */
    public void borrowBook() {
        if (libraryService.borrowBook(book)) {
            loanStatus = BookLoan.READING;
            showLoanConditionAlert("Book borrowed successfully");
        } else {
            loanStatus = BookLoan.WAITING;
            showLoanConditionAlert("The book is currently unavailable. You are added to the waiting list.");
        }
        this.updateLoanStatus();
        if (shelfController != null) {
            shelfController.refreshView();
        }
    }

    /**
     * Returns the book.
     * Updates the loan status and refreshes the view if necessary.
     */
    public void returnBook() {
        libraryService.returnBook(book);
        loanStatus = BookLoan.NOT_BORROWED;
        this.updateLoanStatus();
        if (shelfController != null) {
            shelfController.refreshView();
        }
    }

    /**
     * Displays an alert with the loan condition message.
     *
     * @param message The message to be displayed.
     */
    private void showLoanConditionAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Book status notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays a dialog to notify the user that the book is ready to read.
     */
    private void notifyReadyBookDialog() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Notification");
        dialog.setHeaderText(null);

        ButtonType readNowBtn = new ButtonType("Read Now", ButtonBar.ButtonData.OK_DONE);
        ButtonType laterBtn = new ButtonType("Later", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(readNowBtn, laterBtn);

        Label label = new Label("The book is now available for you to read. Would you like to read it now?");
        dialog.getDialogPane().setContent(label);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == readNowBtn) {
                libraryService.updateWaitingBookToLoan(book);
                this.updateLoanStatus();
                return "Read Now";
            } else if (dialogButton == laterBtn) {
                showNotification("You can read the book later.");
                return "Later";
            }
            return null;
        });

        dialog.showAndWait();
    }

    /**
     * Sets the ShelfController instance.
     *
     * @param shelfController The ShelfController instance.
     */
    public void setShelfController(ShelfController shelfController) {
        this.shelfController = shelfController;
    }

    /**
     * Sets up the book view for the user profile.
     * Hides the read button and disables the comment icon.
     */
    public void setUpBookViewForUserProfile() {
        this.inProfileView = true;
        readButton.setVisible(false);
        readButton.setManaged(false);
        commentIcon.setDisable(true);
    }

    /**
     * Toggles the display between the book cover and the preview QR code.
     */
    private void toggleImage() {
        if (isShowingCover) {
            bookCover.setVisible(false);
            bookPreviewQR.setVisible(true);
        } else {
            bookCover.setVisible(true);
            bookPreviewQR.setVisible(false);
        }
        isShowingCover = !isShowingCover;
    }
}