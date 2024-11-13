package org.example.javafxtutorial;

import controller.BookUserView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import logic.Book;

import java.io.IOException;
import java.util.List;

public class BrowseViewController {

  private LibraryService libraryService;

  @FXML
  private TextField searchField;

  @FXML
  private GridPane bookGridPane;

  private StackPane mainView;

  public TextField getSearchField() {
    return searchField;
  }

  public GridPane getBookGridPane() {
    return bookGridPane;
  }

  public void initializeBrowseView() throws IOException {
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

  public void setLibraryService(LibraryService libraryService) {
    this.libraryService = libraryService;
  }

  public void setMainView(StackPane mainView) {
    this.mainView = mainView;
  }
}