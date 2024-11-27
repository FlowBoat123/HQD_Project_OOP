package logic;

import database.BookLoanDAO;
import database.CommentDAO;
import database.LibraryDAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Service class for managing library operations.
 * This class provides methods to add, remove, and update books, manage book loans,
 * and handle comments and requests.
 */
public class LibraryService {

    private LibraryDAO libraryDAO;
    private UserSession userSession;
    private ArrayList<Book> books;
    private ArrayList<BookLoan> bookLoans;
    private ArrayList<Comment> comments;
    private BookLoanDAO bookLoanDAO;
    private CommentDAO commentDAO;

    /**
     * Constructs a new LibraryService instance.
     * Initializes the DAOs, user session, and loads the initial data.
     */
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

    /**
     * Checks if a book is in the library.
     *
     * @param book The book to check.
     * @return True if the book is in the library, false otherwise.
     */
    public boolean checkIfBookIsInLibrary(Book book) {
        return books.contains(book);
    }

    /**
     * Adds copies of a book to the library.
     *
     * @param book            The book to add copies of.
     * @param numberOfCopies  The number of copies to add.
     */
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

    /**
     * Removes all copies of a book from the library.
     *
     * @param book The book to remove.
     */
    public void removeCopiesFromLibrary(Book book) {
        if (checkIfBookIsInLibrary(book)) {
            Book existingBook = books.get(books.indexOf(book));
            existingBook.setQuantity(0);
            libraryDAO.update(existingBook);
        }
        printBooks();
    }

    /**
     * Prints all books in the library.
     */
    public void printBooks() {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    /**
     * Gets all books in the library.
     *
     * @return An ArrayList of all books.
     */
    public ArrayList<Book> getBooks() {
        return books;
    }

    /**
     * Gets all book loans.
     *
     * @return An ArrayList of all book loans.
     */
    public ArrayList<BookLoan> getBookLoans() {
        return bookLoans;
    }

    /**
     * Borrows a book.
     *
     * @param book The book to borrow.
     * @return True if the book was borrowed successfully, false if added to the waiting list.
     */
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

    /**
     * Returns a borrowed book.
     *
     * @param book The book to return.
     */
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

    /**
     * Gets the loan status of a book.
     *
     * @param book The book to check.
     * @return The loan status of the book.
     */
    public int getLoanStatus(Book book) {
        for (BookLoan bookLoan : bookLoans) {
            if (bookLoan.getBook().equals(book)) {
                return bookLoan.getLoanStatus();
            }
        }
        return BookLoan.NOT_BORROWED;
    }

    /**
     * Prints all book loans.
     */
    public void printBookLoan() {
        for (BookLoan bookLoan : bookLoans) {
            System.out.println(bookLoan.getLoanID() + bookLoan.getBook().toString() + ' ' + bookLoan.getLoanStatus());
        }
    }

    /**
     * Gets books by loan status for a specific user.
     *
     * @param loanStatus The loan status to filter by.
     * @param userID     The ID of the user.
     * @return An ArrayList of books with the specified loan status.
     */
    public ArrayList<Book> getBooksByLoanStatus(int loanStatus, int userID) {
        ArrayList<Book> booksByLoanStatus = new ArrayList<>();
        for (BookLoan bookLoan : bookLoans) {
            if (bookLoan.getLoanStatus() == loanStatus && bookLoan.getUserID() == userID) {
                booksByLoanStatus.add(bookLoan.getBook());
            }
        }
        return booksByLoanStatus;
    }

    /**
     * Gets all books that have been loaned.
     *
     * @return An ArrayList of all books that have been loaned.
     */
    public ArrayList<Book> getAllBooksHasLoan() {
        ArrayList<Book> booksHasLoan = new ArrayList<>();
        for (BookLoan bookLoan : bookLoans) {
            booksHasLoan.add(bookLoan.getBook());
        }
        return booksHasLoan;
    }

    /**
     * Updates a waiting book to a loaned book.
     *
     * @param book The book to update.
     */
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

    /**
     * Adds a comment to the library.
     *
     * @param comment The comment to add.
     */
    public void addComment(Comment comment) {
        commentDAO.add(comment);
        comments.add(comment);
    }

    /**
     * Gets all comments for a specific book.
     *
     * @param book The book to get comments for.
     * @return An ArrayList of comments for the specified book.
     */
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

    /**
     * Gets the number of requests for each book.
     *
     * @return A map of book ISBNs to the number of requests.
     */
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

    /**
     * Updates the number of requested copies for each book.
     */
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