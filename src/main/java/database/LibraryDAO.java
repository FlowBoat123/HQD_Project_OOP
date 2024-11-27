package database;

import logic.Book;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Data Access Object (DAO) class for managing Book entities in the database.
 * This class provides methods to add, delete, update, and retrieve Book records.
 */
public class LibraryDAO implements DAO<Book> {

    private DataSource dataSource;

    /**
     * Constructor for the LibraryDAO class.
     * Initializes the data source for database connections.
     */
    public LibraryDAO() {
        this.dataSource = DataSourceFactory.getDataSource();
    }

    /**
     * Adds a new Book record to the database.
     *
     * @param book The Book object to be added.
     */
    @Override
    public void add(Book book) {
        String query = "INSERT INTO library (title, authors, description, isbn_10, isbn_13, genres, cover_url, quantity, borrowedCopies, preview_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthorsAsString());
            statement.setString(3, book.getDescription());
            statement.setString(4, book.getIsbn_10());
            statement.setString(5, book.getIsbn_13());
            statement.setString(6, book.getGenresAsString());
            statement.setString(7, book.getCoverImgUrl());
            statement.setInt(8, book.getQuantity());
            statement.setInt(9, book.getBorrowedCopies());
            statement.setString(10, book.getPreviewUrl());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a Book record from the database.
     * This method is currently not implemented.
     *
     * @param book The Book object to be deleted.
     */
    @Override
    public void delete(Book book) {
        // Not implemented
    }

    /**
     * Updates a Book record in the database.
     * This method updates the quantity of the book.
     *
     * @param book The Book object to be updated.
     */
    @Override
    public void update(Book book) {
        String query = "UPDATE library SET quantity = ? WHERE isbn_10 = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, book.getQuantity());
            statement.setString(2, book.getIsbn_10());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all Book records from the database.
     *
     * @return An ArrayList of all Book objects.
     */
    @Override
    public ArrayList<Book> getAll() {
        String query = "SELECT * FROM library";
        ArrayList<Book> books = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setTitle(resultSet.getString("title"));
                book.setAuthorsFromString(resultSet.getString("authors"));
                book.setDescription(resultSet.getString("description"));
                book.setIsbn_10(resultSet.getString("isbn_10"));
                book.setIsbn_13(resultSet.getString("isbn_13"));
                book.setGenresFromString(resultSet.getString("genres"));
                book.setCoverImgUrl(resultSet.getString("cover_url"));
                book.setQuantity(resultSet.getInt("quantity"));
                book.setBorrowedCopies(resultSet.getInt("borrowedCopies"));
                book.setPreviewUrl(resultSet.getString("preview_url"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    /**
     * Updates the borrowed copies of a Book record in the database.
     *
     * @param book The Book object whose borrowed copies are to be updated.
     */
    public void updateBorrowedCopies(Book book) {
        String query = "UPDATE library SET borrowedCopies = ? WHERE isbn_10 = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, book.getBorrowedCopies());
            statement.setString(2, book.getIsbn_10());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}