package logic;

import database.BookLoanDAO;
import database.CommentDAO;
import database.LibraryDAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LibraryService {

    private LibraryDAO libraryDAO;
    private UserSession userSession;
    private ArrayList<Book> books;
    private ArrayList<BookLoan> bookLoans;
    private ArrayList<Comment> comments;
    private BookLoanDAO bookLoanDAO;
    private CommentDAO commentDAO;

    public LibraryService() {
        this.libraryDAO = new LibraryDAO();
        this.bookLoanDAO = new BookLoanDAO();
        this.commentDAO = new CommentDAO();
        this.userSession = UserSession.getInstance();
        this.books = libraryDAO.getAll();
        this.comments = commentDAO.getAll();
        if (userSession.isAdmin()) {
            System.out.println("Admin");
            bookLoans = bookLoanDAO.getAll();
        } else {
            System.out.println("User" + userSession.getUserID());
            bookLoans = bookLoanDAO.getUserLoan(userSession.getUserID());
            this.printBookLoan();
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
            if (existingBook.getRequestedCopies() > 0) {
                if (numberOfCopies > existingBook.getRequestedCopies()) {
                    existingBook.setRequestedCopies(0);
                } else {
                    existingBook.setRequestedCopies(existingBook.getRequestedCopies() - numberOfCopies);
                }
            }
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
            this.printBookLoan();
            return true;
        } else {
            bookLoan.setBook(book);
            bookLoan.setUserID(userSession.getUserID());
            bookLoan.setLoanDate(null);
            bookLoan.setReturnDate(null);
            bookLoanDAO.add(bookLoan);
            bookLoans.add(bookLoan);
            System.out.println("Add to waiting list");
            this.printBookLoan();
            return false;
        }

    }

    public void returnBook(Book book) {
        for (BookLoan bookLoan : bookLoans) {
            if (bookLoan.getBook().equals(book)) {
                book.setBorrowedCopies(book.getBorrowedCopies() - 1);
                libraryDAO.updateBorrowedCopies(book);
                bookLoan.setReturnDate(new Date());
                bookLoanDAO.delete(bookLoan);
                System.out.println("Book returned successfully");
                break;
            }
        }
        this.printBookLoan();
    }

    public int getLoanStatus(Book book) {
        for (BookLoan bookLoan : bookLoans) {
            if (bookLoan.getBook().equals(book)) {
                return bookLoan.getLoanStatus();
            }
        }
        return BookLoan.NOT_BORROWED;
    }

    public void printBookLoan() {
        for (BookLoan bookLoan : bookLoans) {
            System.out.println(bookLoan.getLoanID() + bookLoan.getBook().toString() + ' ' + bookLoan.getLoanStatus());
        }
    }

    public ArrayList<Book> getBooksByLoanStatus(int loanStatus, int userID) {
        ArrayList<Book> booksByLoanStatus = new ArrayList<>();
        for (BookLoan bookLoan : bookLoans) {
            if (bookLoan.getLoanStatus() == loanStatus && bookLoan.getUserID() == userID) {
                booksByLoanStatus.add(bookLoan.getBook());
            }
        }
        return booksByLoanStatus;
    }


    public ArrayList<Book> getAllBooksHasLoan() {
        ArrayList<Book> booksHasLoan = new ArrayList<>();
        for (BookLoan bookLoan : bookLoans) {
            booksHasLoan.add(bookLoan.getBook());
        }
        return booksHasLoan;
    }

    public void updateWaitingBookToLoan(Book book) {
        for (BookLoan bookLoan : bookLoans) {
            if (bookLoan.getBook().equals(book)) {
                book.setBorrowedCopies(book.getBorrowedCopies() + 1);
                libraryDAO.updateBorrowedCopies(book);
                bookLoan.setLoanDate(new Date());
                bookLoanDAO.update(bookLoan);
                break;
            }
        }
        this.printBookLoan();
    }

    public void addComment(Comment comment) {
        commentDAO.add(comment);
        comments.add(comment);
    }

    public ArrayList<Comment> getBookComments(Book book) {
        ArrayList<Comment> bookCmt = new ArrayList<>();
        for (Comment comment : comments) {
            if (comment.getBook_isbn_13().equals(book.getIsbn_13())) {
                System.out.println(comment.getComment());
                bookCmt.add(comment);
            }
        }
        return bookCmt;
    }

    public Map<String, Integer> getBooksRequestNumber() {
        Map<String, Integer> requestCounts = new HashMap<>();
        for (BookLoan bookLoan : bookLoans) {
            if (bookLoan.getLoanStatus() == BookLoan.WAITING) {
                Book book = bookLoan.getBook();
                requestCounts.put(book.getIsbn_13(), requestCounts.getOrDefault(book.getIsbn_13(), 0) + 1);
            }
        }
        return requestCounts;
    }

    public void updateBooksRequestNumber() {
        Map<String, Integer> requestCounts = getBooksRequestNumber();
        for (Book book : books) {
            int requestedCopies = requestCounts.getOrDefault(book.getIsbn_13(), 0);
            System.out.println(book.getIsbn_13() + " " + requestedCopies);
            if (requestedCopies + book.getBorrowedCopies() > book.getQuantity()) {
                book.setRequestedCopies(book.getBorrowedCopies() + requestedCopies - book.getQuantity());
            } else {
                book.setRequestedCopies(0);
            }
        }
    }
}
