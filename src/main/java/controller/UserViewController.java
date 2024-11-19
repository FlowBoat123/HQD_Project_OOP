package controller;

import javafx.scene.layout.StackPane;
import org.example.javafxtutorial.LibraryService;
import logic.Book;

public abstract class UserViewController {
  protected LibraryService libraryService;
  protected StackPane mainView;
  public abstract void init();
  public abstract void launchBookView(Book book);
  public void setLibraryService(LibraryService libraryService) {
    this.libraryService = libraryService;
  }
  public void setMainView(StackPane mainView) {
    this.mainView = mainView;
  }
}
