package org.example.javafxtutorial;

import controller.BookUserView;
import controller.UserViewController;
import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.Book;

public class RecomUserViewController extends UserViewController {
  @FXML
  private HBox cardLayout1;
  @FXML
  private HBox cardLayout2;
  @FXML
  private HBox cardLayout3;
  @FXML
  private HBox cardLayout4;

  public void init() {
    try {
      List<Book> books = libraryService.getBooks();
      initializeCardLayout(cardLayout1, books);
      initializeCardLayout(cardLayout2, books.subList(books.size() / 4, books.size() / 2));
      initializeCardLayout(cardLayout3, books.subList(books.size() / 2, 3 * books.size() / 4));
      initializeCardLayout(cardLayout4, books.subList(3 * books.size() / 4, books.size()));
    } catch(IOException e){
      e.printStackTrace();
    }
  }

  private void initializeCardLayout(HBox cardLayout, List<Book> books) throws IOException {
    for (Book book : books) {
      FXMLLoader cardLoader = new FXMLLoader(
          getClass().getResource("/org/example/javafxtutorial/recom-card.fxml"));
      HBox cardBox = cardLoader.load();
      BookCardController bookCardController = cardLoader.getController();
      bookCardController.setBook(book, this);
      cardLayout.getChildren().add(cardBox);
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
}