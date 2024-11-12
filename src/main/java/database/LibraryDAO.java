package database;

import logic.Book;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static database.DataSourceFactory.getDataSource;

public class LibraryDAO implements DAO<Book>{

    private DataSource dataSource;

    public LibraryDAO() {
        this.dataSource = DataSourceFactory.getDataSource();
    }
    @Override
    public void add(Book book) {
        String query = "INSERT INTO library (title, authors, description, isbn_10, isbn_13, genres, cover_url, quantity, borrowedCopies) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
             Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             statement.setString(1, book.getTitle());
             statement.setString(2, book.getAuthorsAsString());
             statement.setString(3, book.getDescription());
             statement.setString(4, book.getIsbn_10());
             statement.setString(5, book.getIsbn_13());
             statement.setString(6, book.getGenresAsString());
             statement.setString(7, book.getCoverImgUrl());
             statement.setInt(8, book.getQuantity());
             statement.setInt(9, book.getBorrowedCopies());
             statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Book book) {

    }

    @Override
    public void update(Book book) {
        // Update the quantity of the book
        String query = "UPDATE library SET quantity = ? WHERE isbn_10 = ?";
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, book.getQuantity());
            statement.setString(2, book.getIsbn_10());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

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
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
}
