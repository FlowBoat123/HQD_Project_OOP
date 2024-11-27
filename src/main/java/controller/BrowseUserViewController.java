package controller;

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

/**
 * Controller class for browsing books in a user view.
 * This class handles the initialization of the book grid, filtering books based on search criteria,
 * and launching the book view for a selected book.
 */
public class BrowseUserViewController extends UserViewController {

  @FXML
  private TextField searchField;

  @FXML
  private Button searchButton;

  @FXML
  private GridPane bookGridPane;

  /**
   * Initializes the book grid with the list of books retrieved from the library service.
   * This method populates the grid with book cards, each representing a book.
   */
  @Override
  public void init() {
    try {
      List<Book> books = libraryService.getBooks();

      int row = 1;
      int col = 0;
      for (Book book : books) {
        FXMLLoader cardLoader = new FXMLLoader(
                getClass().getResource("/application/book-card.fxml"));
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

  /**
   * Launches the book view for a selected book.
   * This method loads the book user view FXML, sets up the main view, and initializes the book view.
   *
   * @param book The book to be displayed in the book view.
   */
  @Override
  public void launchBookView(Book book) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/BookUserview.fxml"));
      Node content = loader.load();
      BookUserView bookUserView = loader.getController();
      bookUserView.setMainView(mainView, mainView.getChildren().get(0));
      mainView.getChildren().add(content);
      bookUserView.setLibraryService(libraryService);
      bookUserView.initializeBookViewForUser(book);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Initializes the controller class. This method sets up the search button action
   * to filter books based on the search text entered by the user.
   */
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

  /**
   * Filters the books based on the search text entered by the user.
   * This method clears the current book grid and repopulates it with the filtered books.
   *
   * @param searchText The search text entered by the user.
   * @throws IOException If there is an error loading the book card FXML.
   */
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
              getClass().getResource("/application/book-card.fxml"));
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