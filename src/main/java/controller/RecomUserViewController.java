package controller;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import logic.Book;

/**
 * Controller class for managing the recommendation view in a user interface.
 * This class handles the initialization of book cards based on various criteria
 * such as recently added books, most popular books, recommended books, and fiction books.
 */
public class RecomUserViewController extends UserViewController {

    @FXML
    private HBox cardLayout1;

    @FXML
    private HBox cardLayout2;

    @FXML
    private HBox cardLayout3;

    @FXML
    private HBox cardLayout4;

    /**
     * Initializes the recommendation view by populating the card layouts with books
     * based on different criteria such as recently added, most popular, recommended, and fiction books.
     */
    public void init() {
        try {
            List<Book> books = libraryService.getBooks();
            List<Book> userBorrowedBooks = libraryService.getAllBooksHasLoan();

            for (Book u : userBorrowedBooks) {
                System.out.println(u);
            }

            List<Book> list_book1 = books.reversed();

            initializeCardLayout(cardLayout1, list_book1.subList(0, Math.min(books.size(), 10))); // Recently added

            books.sort(Comparator.comparingInt(Book::getBorrowedCopies).reversed());
            initializeCardLayout(cardLayout2, books.subList(books.size() / 4, books.size() / 2)); // Most popular

            List<Book> notBorrowedBooks = books.stream()
                    .filter(book -> !userBorrowedBooks.contains(book))
                    .collect(Collectors.toList());
            List<Book> recommendedBooks = notBorrowedBooks.subList(notBorrowedBooks.size() / 2, 3 * notBorrowedBooks.size() / 4);

            initializeCardLayout(cardLayout3, recommendedBooks); // Recommend for you

            List<Book> fictionBooks = books.stream()
                    .filter(book -> book.getGenresAsString().contains("Fiction"))
                    .collect(Collectors.toList());
            fictionBooks.sort(Comparator.comparingInt(Book::getBorrowedCopies).reversed());

            initializeCardLayout(cardLayout4, fictionBooks.subList(0, Math.min(fictionBooks.size(), 10))); // Science
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes a card layout with a list of books.
     * This method loads the book card FXML, sets the book details, and adds the card to the layout.
     *
     * @param cardLayout The HBox layout to be populated with book cards.
     * @param books The list of books to be displayed in the card layout.
     * @throws IOException If there is an error loading the book card FXML.
     */
    private void initializeCardLayout(HBox cardLayout, List<Book> books) throws IOException {
        for (Book book : books) {
            FXMLLoader cardLoader = new FXMLLoader(
                    getClass().getResource("/application/recom-card.fxml"));
            HBox cardBox = cardLoader.load();
            BookCardController bookCardController = cardLoader.getController();
            bookCardController.setBook(book, this);
            cardLayout.getChildren().add(cardBox);
        }
    }

    /**
     * Launches the book view for a selected book.
     * This method loads the book user view FXML, sets up the main view, and initializes the book view.
     *
     * @param book The book to be displayed in the book view.
     */
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
}