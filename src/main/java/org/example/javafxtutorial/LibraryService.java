package org.example.javafxtutorial;

import database.LibraryDAO;
import logic.Book;
import database.BookDAO;

import java.util.ArrayList;

public class LibraryService {

    private LibraryDAO libraryDAO;
    private UserSession userSession;
    private ArrayList<Book> books;

    public LibraryService() {
        this.libraryDAO = new LibraryDAO();
        this.userSession = UserSession.getInstance();
        this.books = libraryDAO.getAll();
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

}
