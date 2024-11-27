package logic;

import java.util.Date;
import java.util.Objects;

/**
 * Represents a book loan in the library system.
 * This class encapsulates the details of a book loan, including the book, user ID,
 * loan date, return date, and loan ID.
 */
public class BookLoan {

    /**
     * Constant representing the status when a book is not borrowed.
     */
    public static final int NOT_BORROWED = 0;

    /**
     * Constant representing the status when a book loan is waiting.
     */
    public static final int WAITING = 1;

    /**
     * Constant representing the status when a book is currently being read.
     */
    public static final int READING = 2;

    /**
     * Constant representing the status when a book loan is completed.
     */
    public static final int COMPLETED = 3;

    private Book book;
    private int userID;
    private Date loanDate;
    private Date returnDate;
    private int loanID;

    /**
     * Constructs a new BookLoan with the specified details.
     *
     * @param loanID     The ID of the loan.
     * @param book       The book being loaned.
     * @param userID     The ID of the user who borrowed the book.
     * @param loanDate   The date the book was loaned.
     * @param returnDate The date the book was returned.
     */
    public BookLoan(int loanID, Book book, int userID, Date loanDate, Date returnDate) {
        this.loanID = loanID;
        this.book = book;
        this.userID = userID;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    /**
     * Default constructor for the BookLoan class.
     */
    public BookLoan() {
    }

    /**
     * Gets the loan ID.
     *
     * @return The loan ID.
     */
    public int getLoanID() {
        return loanID;
    }

    /**
     * Sets the loan ID.
     *
     * @param loanID The loan ID to set.
     */
    public void setLoanID(int loanID) {
        this.loanID = loanID;
    }

    /**
     * Gets the book being loaned.
     *
     * @return The book being loaned.
     */
    public Book getBook() {
        return book;
    }

    /**
     * Sets the book being loaned.
     *
     * @param book The book to set.
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * Gets the user ID of the borrower.
     *
     * @return The user ID of the borrower.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Sets the user ID of the borrower.
     *
     * @param userID The user ID to set.
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Gets the loan date.
     *
     * @return The loan date.
     */
    public Date getLoanDate() {
        return loanDate;
    }

    /**
     * Sets the loan date.
     *
     * @param loanDate The loan date to set.
     */
    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    /**
     * Gets the return date.
     *
     * @return The return date.
     */
    public Date getReturnDate() {
        return returnDate;
    }

    /**
     * Sets the return date.
     *
     * @param returnDate The return date to set.
     */
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * Gets the loan status.
     *
     * @return The loan status, which can be NOT_BORROWED, WAITING, READING, or COMPLETED.
     */
    public int getLoanStatus() {
        if (returnDate != null && loanDate != null) {
            return COMPLETED;
        } else if (returnDate == null && loanDate != null) {
            return READING;
        } else {
            return WAITING;
        }
    }

    /**
     * Checks if this BookLoan is equal to another object.
     *
     * @param o The object to compare with this BookLoan.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookLoan loan = (BookLoan) o;
        return userID == loan.userID && Objects.equals(book, loan.book);
    }

    /**
     * Returns a string representation of the BookLoan.
     *
     * @return A string representation of the BookLoan.
     */
    @Override
    public String toString() {
        return "BookLoan{" +
                "book=" + book +
                ", userID=" + userID +
                ", loanDate=" + loanDate +
                ", returnDate=" + returnDate +
                ", loanID=" + loanID +
                '}';
    }
}