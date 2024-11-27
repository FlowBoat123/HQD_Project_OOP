package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import logic.Book;
import logic.LibraryService;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Controller class for the Library View.
 * Handles the initialization and event handling for the library view in the admin interface.
 * Manages the display of books in a table, search functionality, and interactions with individual books.
 */
public class LibraryViewController {

    /**
     * Service class that provides library-related functionalities.
     */
    private LibraryService libraryService;

    /**
     * The main view container.
     */
    private AnchorPane mainView;

    /**
     * TableView to display the list of books.
     */
    @FXML
    private TableView<Book> library;

    /**
     * TableColumn to display the book titles.
     */
    @FXML
    private TableColumn<Book, String> bookTitleCol;

    /**
     * TableColumn to display the book authors.
     */
    @FXML
    private TableColumn<Book, String> bookAuthorsCol;

    /**
     * TableColumn to display the book genres.
     */
    @FXML
    private TableColumn<Book, String> bookGenresCol;

    /**
     * TableColumn to display the book ISBN-13.
     */
    @FXML
    private TableColumn<Book, String> bookIsbn13Col;

    /**
     * TableColumn to display the book quantity.
     */
    @FXML
    private TableColumn<Book, Integer> quantityCol;

    /**
     * TableColumn to display the number of borrowed copies.
     */
    @FXML
    private TableColumn<Book, Integer> borrowedCopiesCol;

    /**
     * TableColumn to display the number of requested copies.
     */
    @FXML
    private TableColumn<Book, Integer> requestedCopiesCol;

    /**
     * TextField for searching books.
     */
    @FXML
    private TextField searchField;

    /**
     * ObservableList to hold the list of books.
     */
    ObservableList<Book> books = FXCollections.observableArrayList();

    /**
     * ArrayList to hold the search results.
     */
    ArrayList<Book> result = new ArrayList<>();

    /**
     * Initializes the library view with the given list of books.
     * Sets up the table columns, row factory for double-click events, and search functionality.
     *
     * @param result The list of books to be displayed.
     */
    public void initializeLibraryView(ArrayList<Book> result) {
        this.result = result;
        books = FXCollections.observableArrayList(result);
        bookTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        bookAuthorsCol.setCellValueFactory(new PropertyValueFactory<>("authorString"));
        bookGenresCol.setCellValueFactory(new PropertyValueFactory<>("genreString"));
        bookIsbn13Col.setCellValueFactory(new PropertyValueFactory<>("isbn_13"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        borrowedCopiesCol.setCellValueFactory(new PropertyValueFactory<>("borrowedCopies"));
        requestedCopiesCol.setCellValueFactory(new PropertyValueFactory<>("requestedCopies"));

        library.setRowFactory(bookTableView -> {
            TableRow<Book> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Book rowData = row.getItem();
                    launchBookView(rowData);
                }
            });
            return row;
        });

        FilteredList<Book> filteredData = new FilteredList<>(books, b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(book -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (book.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (book.getAuthorsAsString() != null && book.getAuthorsAsString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (book.getGenresAsString() != null && book.getGenresAsString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (book.getIsbn_13() != null && book.getIsbn_13().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(book.getQuantity()).contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(book.getBorrowedCopies()).contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(book.getRequestedCopies()).contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Book> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(library.comparatorProperty());
        library.setItems(sortedData);
    }

    /**
     * Launches the book view for the selected book.
     * Loads the book view FXML and initializes it with the selected book's details.
     *
     * @param book The Book object to be displayed.
     */
    public void launchBookView(Book book) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/book-view.fxml"));
            Node content = loader.load();
            BookController bookController = loader.getController();
            bookController.setMainView(mainView, mainView.getChildren().getFirst());
            bookController.setRefreshLibraryViewCallback(v -> initializeLibraryView(result));
            mainView.getChildren().setAll(content);
            bookController.initializeBookViewForAdmin(book);
            bookController.setLibraryService(libraryService);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a book to the table.
     *
     * @param book The Book object to be added.
     */
    public void addBookToTable(Book book) {
        books.add(book);
        library.refresh();
    }

    /**
     * Sets the main view container.
     *
     * @param mainView The main view container.
     */
    public void setMainView(AnchorPane mainView) {
        this.mainView = mainView;
    }

    /**
     * Sets the LibraryService instance.
     *
     * @param libraryService The LibraryService instance.
     */
    public void setLibraryService(LibraryService libraryService) {
        this.libraryService = libraryService;
    }
}