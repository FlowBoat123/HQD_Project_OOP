package logic;

import java.util.Date;
import java.util.Objects;

public class BookLoan {
    public static final int NOT_BORROWED = 0;
    public static final int WAITING = 1;
    public static final int READING = 2;
    public static final int COMPLETED = 3;
    private Book book;
    private int userID;
    private Date loanDate;
    private Date returnDate;
    private int loanID;
    public BookLoan(int loanID, Book book, int userID, Date loanDate, Date returnDate) {
        this.loanID = loanID;
        this.book = book;
        this.userID = userID;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public BookLoan() {

    }

    public int getLoanID() {
        return loanID;
    }

    public void setLoanID(int loanID) {
        this.loanID = loanID;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public int getLoanStatus() {
        if (returnDate != null && loanDate != null) {
            return COMPLETED;
        } else if (returnDate == null && loanDate != null) {
            return READING;
        } else {
            return WAITING;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookLoan loan = (BookLoan) o;
        return userID == loan.userID  && Objects.equals(book, loan.book) ;
    }

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
