package database;

import logic.Book;
import logic.BookLoan;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Data Access Object (DAO) class for managing BookLoan entities in the database.
 * This class provides methods to add, delete, update, and retrieve BookLoan records.
 */
public class BookLoanDAO implements DAO<BookLoan> {

    private DataSource dataSource;

    /**
     * Constructor for the BookLoanDAO class.
     * Initializes the data source for database connections.
     */
    public BookLoanDAO() {
        this.dataSource = DataSourceFactory.getDataSource();
    }

    /**
     * Adds a new BookLoan record to the database.
     *
     * @param bookLoan The BookLoan object to be added.
     */
    @Override
    public void add(BookLoan bookLoan) {
        String query = "INSERT INTO loans (book_isbn_13, userID, loanDate, returnDate) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, bookLoan.getBook().getIsbn_13());
            statement.setInt(2, bookLoan.getUserID());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (bookLoan.getLoanDate() != null) {
                statement.setString(3, dateFormat.format(bookLoan.getLoanDate()));
            } else {
                statement.setString(3, null);
            }
            if (bookLoan.getReturnDate() != null) {
                statement.setString(4, dateFormat.format(bookLoan.getReturnDate()));
            } else {
                statement.setString(4, null);
            }
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                bookLoan.setLoanID(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a BookLoan record from the database.
     * This method updates the return date of the loan.
     *
     * @param bookLoan The BookLoan object to be deleted.
     */
    @Override
    public void delete(BookLoan bookLoan) {
        String query = "UPDATE loans SET returnDate = ? WHERE loanID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            statement.setString(1, dateFormat.format(bookLoan.getReturnDate()));
            statement.setInt(2, bookLoan.getLoanID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates a BookLoan record in the database.
     * This method updates the loan date of the loan.
     *
     * @param bookLoan The BookLoan object to be updated.
     */
    @Override
    public void update(BookLoan bookLoan) {
        String query = "UPDATE loans SET loanDate = ? WHERE loanID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            statement.setString(1, dateFormat.format(bookLoan.getLoanDate()));
            statement.setInt(2, bookLoan.getLoanID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all BookLoan records from the database.
     *
     * @return An ArrayList of all BookLoan objects.
     */
    @Override
    public ArrayList<BookLoan> getAll() {
        String query = "SELECT * FROM loans JOIN library ON loans.book_isbn_13 = library.isbn_13";
        ArrayList<BookLoan> bookLoans = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book loanedBook = new Book();
                BookLoan loan = new BookLoan();
                loan.setLoanID(resultSet.getInt("loanID"));
                loanedBook.setTitle(resultSet.getString("title"));
                loanedBook.setAuthorsFromString(resultSet.getString("authors"));
                loanedBook.setDescription(resultSet.getString("description"));
                loanedBook.setIsbn_10(resultSet.getString("isbn_10"));
                loanedBook.setIsbn_13(resultSet.getString("isbn_13"));
                loanedBook.setGenresFromString(resultSet.getString("genres"));
                loanedBook.setCoverImgUrl(resultSet.getString("cover_url"));
                loanedBook.setQuantity(resultSet.getInt("quantity"));
                loanedBook.setBorrowedCopies(resultSet.getInt("borrowedCopies"));
                loanedBook.setPreviewUrl(resultSet.getString("preview_url"));
                loan.setBook(loanedBook);
                loan.setUserID(resultSet.getInt("userID"));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                if (resultSet.getString("loanDate") != null) {
                    loan.setLoanDate(dateFormat.parse(resultSet.getString("loanDate")));
                } else {
                    loan.setLoanDate(null);
                }
                if (resultSet.getString("returnDate") != null) {
                    loan.setReturnDate(dateFormat.parse(resultSet.getString("returnDate")));
                } else {
                    loan.setReturnDate(null);
                }
                bookLoans.add(loan);
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return bookLoans;
    }

    /**
     * Retrieves all BookLoan records for a specific user from the database.
     *
     * @param userID The ID of the user whose loans are to be retrieved.
     * @return An ArrayList of BookLoan objects for the specified user.
     */
    public ArrayList<BookLoan> getUserLoan(int userID) {
        String query = "SELECT * FROM loans JOIN library ON loans.book_isbn_13 = library.isbn_13 WHERE userID = ?";
        ArrayList<BookLoan> bookLoans = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book loanedBook = new Book();
                BookLoan loan = new BookLoan();
                loan.setLoanID(resultSet.getInt("loanID"));
                loanedBook.setTitle(resultSet.getString("title"));
                loanedBook.setAuthorsFromString(resultSet.getString("authors"));
                loanedBook.setDescription(resultSet.getString("description"));
                loanedBook.setIsbn_10(resultSet.getString("isbn_10"));
                loanedBook.setIsbn_13(resultSet.getString("isbn_13"));
                loanedBook.setGenresFromString(resultSet.getString("genres"));
                loanedBook.setCoverImgUrl(resultSet.getString("cover_url"));
                loanedBook.setQuantity(resultSet.getInt("quantity"));
                loanedBook.setBorrowedCopies(resultSet.getInt("borrowedCopies"));
                loanedBook.setPreviewUrl(resultSet.getString("preview_url"));
                loan.setBook(loanedBook);
                loan.setUserID(resultSet.getInt("userID"));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                if (resultSet.getString("loanDate") != null) {
                    loan.setLoanDate(dateFormat.parse(resultSet.getString("loanDate")));
                } else {
                    loan.setLoanDate(null);
                }
                if (resultSet.getString("returnDate") != null) {
                    loan.setReturnDate(dateFormat.parse(resultSet.getString("returnDate")));
                } else {
                    loan.setReturnDate(null);
                }
                bookLoans.add(loan);
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return bookLoans;
    }
}