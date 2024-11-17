package org.example.javafxtutorial;

import controller.BookUserView;
import controller.UserViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import logic.Book;

import java.io.IOException;
import java.util.List;

public class BrowseUserViewController extends UserViewController {

  @FXML
  private TextField searchField;

  @FXML
  private Button searchButton;

  @FXML
  private GridPane bookGridPane;

  @Override
  public void init() {
    try {
      List<Book> books = libraryService.getBooks();

      int row = 1;
      int col = 0;
      for (Book book : books) {
        FXMLLoader cardLoader = new FXMLLoader(
            getClass().getResource("/org/example/javafxtutorial/book-card.fxml"));
        VBox bookCard = cardLoader.load();
        BookCardController bookCardController = cardLoader.getController();
        bookCardController.setBook(book, this);

        if (col == 5) {
          col = 0;
          ++row;
        }

        bookGridPane.add(bookCard, col++, row);
        GridPane.setMargin(bookCard, new Insets(10));
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void launchBookView(Book book) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/javafxtutorial/BookUserview.fxml"));
      Node content = loader.load();
      BookUserView bookUserView = loader.getController();
      bookUserView.setMainView(mainView, mainView.getChildren().get(0));
      mainView.getChildren().add(content);
      bookUserView.initializeBookViewForUser(book);
      bookUserView.setLibraryService(libraryService);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void initialize() {
    searchButton.setOnAction(event -> {
      String searchText = searchField.getText();
      try {
        filterBooks(searchText);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  private void filterBooks(String searchText) throws IOException {
    bookGridPane.getChildren().clear();

    List<Book> filteredBooks = libraryService.getBooks().stream()
        .filter(book ->
            book.getTitle().toLowerCase().contains(searchText.toLowerCase()) ||
                book.getAuthorsAsString().toLowerCase().contains(searchText.toLowerCase()) ||
                book.getGenresAsString().toLowerCase().contains(searchText.toLowerCase()) ||
                book.getIsbn_10().toLowerCase().contains(searchText.toLowerCase()) ||
                book.getIsbn_13().toLowerCase().contains(searchText.toLowerCase()) ||
                String.valueOf(book.getQuantity()).contains(searchText.toLowerCase()) ||
                String.valueOf(book.getBorrowedCopies()).contains(searchText.toLowerCase())
        )
        .toList();


    int row = 1;
    int col = 0;
    for (Book book : filteredBooks) {
      FXMLLoader cardLoader = new FXMLLoader(
          getClass().getResource("/org/example/javafxtutorial/book-card.fxml"));
      VBox bookCard = cardLoader.load();
      BookCardController bookCardController = cardLoader.getController();
      bookCardController.setBook(book, this);

      if (col == 5) {
        col = 0;
        ++row;
      }

      bookGridPane.add(bookCard, col++, row);
      GridPane.setMargin(bookCard, new Insets(10));
    }
  }
}