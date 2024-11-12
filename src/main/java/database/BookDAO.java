package database;

import logic.Book;
import org.example.javafxtutorial.UserSession;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookDAO implements DAO<Book> {

    private DataSource dataSource;
    private UserSession userSession;

    public BookDAO() {
        this.dataSource = DataSourceFactory.getDataSource();
        this.userSession = UserSession.getInstance();
    }

    @Override
    public void add(Book book) {

    }

    @Override
    public void delete(Book book) {

    }

    @Override
    public void update(Book book) {

    }

    @Override
    public ArrayList<Book> getAll() {
        return null;
    }

    public void add(Book book, String username) {
        String query = "INSERT INTO books (title, authors, description, genres, isbn_10, isbn_13, cover_url, status, username) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthorsAsString());
            statement.setString(3, book.getDescription());
            statement.setString(4, book.getGenresAsString());
            statement.setString(5, book.getIsbn_10());
            statement.setString(6, book.getIsbn_13());
            statement.setString(7, book.getCoverImgUrl());
            statement.setString(8, book.getStatus());
            statement.setString(9, username);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void delete(Book book, String username) {
        String query = "DELETE FROM books WHERE isbn_10 = ? OR isbn_13 = ? AND username = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getIsbn_10());
            statement.setString(2, book.getIsbn_13());
            statement.setString(3, username);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void update(Book book, String username) {

    }

    public void updateStatus(Book book, String username, int status) {
        String query = "UPDATE books SET status = ? WHERE isbn_10 = ? OR isbn_13 = ? AND username = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, status);
            statement.setString(2, book.getIsbn_10());
            statement.setString(3, book.getIsbn_13());
            statement.setString(4, username);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Book> getAll(String username) {
        String query = "SELECT * FROM books WHERE username = ?";
        ArrayList<Book> books = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setTitle(resultSet.getString("title"));
                book.setAuthorsFromString(resultSet.getString("authors"));
                book.setDescription(resultSet.getString("description"));
                book.setGenresFromString(resultSet.getString("genres"));
                book.setIsbn_10(resultSet.getString("isbn_10"));
                book.setIsbn_13(resultSet.getString("isbn_13"));
                book.setCoverImgUrl(resultSet.getString("cover_url"));
                book.setStatus(resultSet.getString("status"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public ArrayList<Book> getAllBooks() {
        String query = "SELECT * FROM library";
        ArrayList<Book> books = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setTitle(resultSet.getString("title"));
                book.setAuthorsFromString(resultSet.getString("authors"));
                book.setCoverImgUrl(resultSet.getString("cover_url"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
}
