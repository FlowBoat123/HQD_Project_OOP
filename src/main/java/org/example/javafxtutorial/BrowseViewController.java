package org.example.javafxtutorial;

import database.BookDAO;
import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import logic.Book;

public class BrowseViewController {

  private LibraryService libraryService;

  private AnchorPane mainView;

  @FXML
  private TextField searchField;

  @FXML
  private GridPane bookGridPane;
  private List<Book> allBooks;

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
        bookCardController.setBook(book);

        if (col == 5) {
          col = 0;
          ++row;
        }

        bookGridPane.add(bookCard, col++, row);
        GridPane.setMargin(bookCard,new Insets(10));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  public void setLibraryService(LibraryService libraryService){
    this.libraryService = libraryService;
  }
}