package logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BookLoanTest {

  private BookLoan bookLoan;
  private Book book;

  @BeforeEach
  void setUp() {
    book = new Book();
    book.setTitle("The Great Gatsby");
    bookLoan = new BookLoan();
  }

  @Test
  void testConstructor() {
    Date loanDate = new Date();
    Date returnDate = new Date(loanDate.getTime() + 86400000); // 1 day later
    BookLoan loan = new BookLoan(1, book, 123, loanDate, returnDate);

    assertEquals(1, loan.getLoanID());
    assertEquals(book, loan.getBook());
    assertEquals(123, loan.getUserID());
    assertEquals(loanDate, loan.getLoanDate());
    assertEquals(returnDate, loan.getReturnDate());
  }

  @Test
  void testSetAndGetLoanID() {
    bookLoan.setLoanID(1);
    assertEquals(1, bookLoan.getLoanID());
  }

  @Test
  void testSetAndGetBook() {
    bookLoan.setBook(book);
    assertEquals(book, bookLoan.getBook());
  }

  @Test
  void testSetAndGetUserID() {
    bookLoan.setUserID(123);
    assertEquals(123, bookLoan.getUserID());
  }

  @Test
  void testSetAndGetLoanDate() {
    Date loanDate = new Date();
    bookLoan.setLoanDate(loanDate);
    assertEquals(loanDate, bookLoan.getLoanDate());
  }

  @Test
  void testSetAndGetReturnDate() {
    Date returnDate = new Date();
    bookLoan.setReturnDate(returnDate);
    assertEquals(returnDate, bookLoan.getReturnDate());
  }

  @Test
  void testGetLoanStatus() {
    Date loanDate = new Date();
    Date returnDate = new Date(loanDate.getTime() + 86400000); // 1 day later

    bookLoan.setLoanDate(loanDate);
    bookLoan.setReturnDate(returnDate);
    assertEquals(BookLoan.COMPLETED, bookLoan.getLoanStatus());

    bookLoan.setReturnDate(null);
    assertEquals(BookLoan.READING, bookLoan.getLoanStatus());

    bookLoan.setLoanDate(null);
    assertEquals(BookLoan.WAITING, bookLoan.getLoanStatus());
  }

}