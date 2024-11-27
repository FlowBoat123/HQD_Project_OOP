package controller;

import javafx.scene.layout.StackPane;
import logic.LibraryService;
import logic.Book;

/**
 * Abstract class for managing user view controllers in a JavaFX application.
 * This class provides common functionality and abstract methods for initializing
 * the view and launching the book view.
 */
public abstract class UserViewController {

  protected LibraryService libraryService;
  protected StackPane mainView;

  /**
   * Initializes the user view. This method must be implemented by subclasses.
   */
  public abstract void init();

  /**
   * Launches the book view for a selected book. This method must be implemented by subclasses.
   *
   * @param book The book to be displayed in the book view.
   */
  public abstract void launchBookView(Book book);

  /**
   * Sets the library service for this controller.
   *
   * @param libraryService The library service instance.
   */
  public void setLibraryService(LibraryService libraryService) {
    this.libraryService = libraryService;
  }

  /**
   * Sets the main view for this controller.
   *
   * @param mainView The main view where the user view will be displayed.
   */
  public void setMainView(StackPane mainView) {
    this.mainView = mainView;
  }
}