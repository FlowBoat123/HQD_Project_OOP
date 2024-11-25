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
import org.example.javafxtutorial.LibraryService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LibraryViewController {

    private LibraryService libraryService;

    private AnchorPane mainView;

    @FXML
    private TableView<Book> library;

    @FXML
    private TableColumn<Book, String> bookTitleCol;

    @FXML
    private TableColumn<Book, String> bookAuthorsCol;

    @FXML
    private TableColumn<Book, String> bookGenresCol;

    @FXML
    private TableColumn<Book, String> bookIsbn13Col;

    @FXML
    private TableColumn<Book, Integer> quantityCol;

    @FXML
    private TableColumn<Book, Integer> borrowedCopiesCol;

    @FXML
    private TableColumn<Book, Integer> requestedCopiesCol;

    @FXML
    private TextField searchField;

    ObservableList<Book> books = FXCollections.observableArrayList();

    ArrayList<Book> result = new ArrayList<>();

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
//        library.setItems(books);

        FilteredList<Book> filteredData = new FilteredList<>(books, b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(book -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (book.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (book.getAuthorsAsString()!= null && book.getAuthorsAsString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (book.getGenresAsString()!= null && book.getGenresAsString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (book.getIsbn_13()!= null && book.getIsbn_13().toLowerCase().contains(lowerCaseFilter)) {
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

    public void launchBookView(Book book) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/javafxtutorial/book-view.fxml"));
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
    public void addBookToTable(Book book) {
        books.add(book);
        library.refresh();
    }

    public void setMainView(AnchorPane mainView) {
        this.mainView = mainView;
    }
    public void setLibraryService(LibraryService libraryService) {
        this.libraryService = libraryService;
    }
}
