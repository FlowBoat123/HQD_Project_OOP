package logic;

import java.util.Date;

public class BookLoan {
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

}
