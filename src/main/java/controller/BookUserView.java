package controller;

import java.util.function.Consumer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import logic.Book;
import logic.BookLoan;
import org.example.javafxtutorial.LibraryService;
import org.example.javafxtutorial.ShelfController;

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
    Book book;
    private ShelfController shelfController;
    private LibraryService libraryService;

    public void initializeBookViewForUser(Book book) {
        this.book = book;
        // check loan status
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
        if (book.getCoverImgUrl() != null) {
            bookCover.setImage(new Image(book.getCoverImgUrl(), true));
        }
        if (loanStatus == BookLoan.WAITING && book.getBorrowedCopies() < book.getQuantity()) {
            notifyReadyBookDialog();
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
            this.borrowBook();
        } else if (loanStatus == BookLoan.READING) {
            this.returnBook();
            showNotification("Book returned successfully");
        } else {
            showNotification("Book is not available");
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

    public void setMainView(StackPane mainView, Node previousContent) {
        this.mainView = mainView;
        this.previousContent = previousContent;
    }

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

    //Borrow book
    public void borrowBook() {
        if (libraryService.borrowBook(book)) {
            loanStatus = BookLoan.READING;
            showLoanConditionDialog("Reading", "Book borrowed successfully");
        } else {
            loanStatus = BookLoan.WAITING;
            showLoanConditionDialog("Waiting", "The book is currently unavailable. You are added to the waiting list.");
        }
        this.updateLoanStatus();
        if (shelfController != null) {
            shelfController.refreshView();
        }
    }

    //Return book
    public void returnBook() {
        libraryService.returnBook(book);
        loanStatus = BookLoan.NOT_BORROWED;
        this.updateLoanStatus();
        if (shelfController != null) {
            shelfController.refreshView();
        }
    }

    private void showLoanConditionDialog(String status, String message) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Notification");
        dialog.setHeaderText("Current Loan Status: " + status);

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType);

        Label label = new Label(message);
        dialog.getDialogPane().setContent(label);

        dialog.showAndWait();
    }

    private void notifyReadyBookDialog() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Notification");
        dialog.setHeaderText("Book is ready for you");

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

    public void setShelfController(ShelfController shelfController) {
        this.shelfController = shelfController;
    }
}