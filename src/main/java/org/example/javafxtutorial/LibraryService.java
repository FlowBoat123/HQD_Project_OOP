package org.example.javafxtutorial;

import java.util.ArrayList;

public class LibraryService {
    private static LibraryService instance;
    private UserSession userSession;
    private ArrayList<Book> allBooks;
    private ArrayList<Book> readingBooks;
    private ArrayList<Book> planToReadBooks;
    private ArrayList<Book> onHoldBooks;
    private ArrayList<Book> completedBooks;
    private BookDAO bookDAO;

    private LibraryService() {
        userSession = UserSession.getInstance();
        bookDAO = new BookDAO();
        allBooks = bookDAO.getAll(userSession.getUsername());
        readingBooks = new ArrayList<>();
        planToReadBooks = new ArrayList<>();
        onHoldBooks = new ArrayList<>();
        completedBooks = new ArrayList<>();
        for (Book book : allBooks) {
            switch (book.getStatus()) {
                case Book.READING:
                    readingBooks.add(book);
                    break;
                case Book.PLAN_TO_READ:
                    planToReadBooks.add(book);
                    break;
                case Book.ON_HOLD:
                    onHoldBooks.add(book);
                    break;
                case Book.COMPLETED:
                    completedBooks.add(book);
                    break;
            }
        }
    }

    public static synchronized LibraryService getInstance() {
        if (instance == null) {
            instance = new LibraryService();
        }
        return instance;
    }

    public void addBook(Book book) {
        allBooks.add(book);
        switch (book.getStatus()) {
            case Book.READING:
                readingBooks.add(book);
                break;
            case Book.PLAN_TO_READ:
                planToReadBooks.add(book);
                break;
            case Book.ON_HOLD:
                onHoldBooks.add(book);
                break;
            case Book.COMPLETED:
                completedBooks.add(book);
                break;
        }
        bookDAO.save(book, userSession.getUsername());
    }

    public void removeBook(Book book) {
        allBooks.remove(book);
        switch (book.getStatus()) {
            case Book.READING:
                readingBooks.remove(book);
                break;
            case Book.PLAN_TO_READ:
                planToReadBooks.remove(book);
                break;
            case Book.ON_HOLD:
                onHoldBooks.remove(book);
                break;
            case Book.COMPLETED:
                completedBooks.remove(book);
                break;
        }
        bookDAO.delete(book, userSession.getUsername());
    }

    public int checkIfBookExists(Book book) {
        for(Book b : allBooks) {
            if(b.getTitle().equals(book.getTitle())) {
                return b.getStatus();
            }
        }
        return -1;
    }

    public void updateBookStatus(String title, int status) {
        for(Book book : allBooks) {
            if(book.getTitle().equals(title)) {
                book.setStatus(status);
                switch (status) {
                    case Book.READING:
                        readingBooks.add(book);
                        break;
                    case Book.PLAN_TO_READ:
                        planToReadBooks.add(book);
                        break;
                    case Book.ON_HOLD:
                        onHoldBooks.add(book);
                        break;
                    case Book.COMPLETED:
                        completedBooks.add(book);
                        break;
                }
                bookDAO.update(book, userSession.getUsername());
                break;
            }
        }
    }


}
