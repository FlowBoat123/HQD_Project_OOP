package org.example.javafxtutorial;

import logic.Book;

import java.util.ArrayList;

public class Shelf {
    private String name;
    private ArrayList<Book> books = new ArrayList<>();
    public Shelf(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public ArrayList<Book> getBooks() {
        return books;
    }
}
