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
  private HBox cardLayout;

  public void init() {
    try {
      List<Book> books = libraryService.getBooks();
      for (Book book : books) {
        FXMLLoader cardLoader = new FXMLLoader(
            getClass().getResource("/org/example/javafxtutorial/recom-card.fxml"));
        HBox cardBox = cardLoader.load();
        BookCardController bookCardController = cardLoader.getController();
        bookCardController.setBook(book,this);
        cardLayout.getChildren().add(cardBox);
      }
    } catch(IOException e){
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
}
