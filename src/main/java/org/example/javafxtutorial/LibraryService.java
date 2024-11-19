package org.example.javafxtutorial;

import database.BookLoanDAO;
import database.LibraryDAO;
import logic.Book;
import database.BookDAO;
import logic.BookLoan;

import java.util.ArrayList;
import java.util.Date;

public class LibraryService {

    private LibraryDAO libraryDAO;
    private UserSession userSession;
    private ArrayList<Book> books;
    private ArrayList<BookLoan> bookLoans;
    private BookLoanDAO bookLoanDAO;
    public LibraryService() {
        this.libraryDAO = new LibraryDAO();
        this.bookLoanDAO = new BookLoanDAO();
        this.userSession = UserSession.getInstance();
        this.books = libraryDAO.getAll();
        if (userSession.isAdmin()) {
            System.out.println("Admin");
            bookLoans = bookLoanDAO.getAll();
        } else {
            System.out.println("User");
            bookLoans = bookLoanDAO.getUserLoan(userSession.getUserID());
        }
    }

    public boolean checkIfBookIsInLibrary(Book book) {
        return books.contains(book);
    }

    public void addCopiesToLibrary(Book book, int numberOfCopies) {
        if (!checkIfBookIsInLibrary(book)) {
            book.setQuantity(numberOfCopies);
            book.setBorrowedCopies(0);
            libraryDAO.add(book);
            books.add(book);
        } else {
            Book existingBook = books.get(books.indexOf(book));
            existingBook.setQuantity(existingBook.getQuantity() + numberOfCopies);
            libraryDAO.update(existingBook);
        }
        printBooks();
    }

    public void removeCopiesFromLibrary(Book book) {
        if (checkIfBookIsInLibrary(book)) {
            Book existingBook = books.get(books.indexOf(book));
            existingBook.setQuantity(0);
            libraryDAO.update(existingBook);
        }
        printBooks();
    }
    public void printBooks() {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public ArrayList<BookLoan> getBookLoans() {
        return bookLoans;
    }

    public boolean borrowBook(Book book) {
        BookLoan bookLoan = new BookLoan();
        if (book.getBorrowedCopies() < book.getQuantity()) {
            book.setBorrowedCopies(book.getBorrowedCopies() + 1);
            bookLoan.setBook(book);
            System.out.println(userSession.getUserID());
            bookLoan.setUserID(userSession.getUserID());
            bookLoan.setLoanDate(new Date());
            bookLoan.setReturnDate(null);
            libraryDAO.updateBorrowedCopies(book);
            bookLoanDAO.add(bookLoan);
            bookLoans.add(bookLoan);
            System.out.println("Loan book successfully");
            return true;
        } else {
            bookLoan.setBook(book);
            bookLoan.setUserID(userSession.getUserID());
            bookLoan.setLoanDate(null);
            bookLoan.setReturnDate(null);
            bookLoanDAO.add(bookLoan);
            bookLoans.add(bookLoan);
            System.out.println("Add to waiting list");
            return false;
        }
    }

    public void returnBook(Book book) {
        for (BookLoan bookLoan : bookLoans) {
            if (bookLoan.getBook().equals(book)) {
                book.setBorrowedCopies(book.getBorrowedCopies() - 1);
                libraryDAO.updateBorrowedCopies(book);
                bookLoan.setReturnDate(new Date());
                bookLoanDAO.update(bookLoan);
                System.out.println("Book returned successfully");
                break;
            }
        }
    }

    public int getLoanStatus(Book book) {
        for(BookLoan bookLoan : bookLoans) {
            if (bookLoan.getBook().equals(book)) {
                return bookLoan.getLoanStatus();
            }
        }
        return BookLoan.NOT_BORROWED;
    }
}
