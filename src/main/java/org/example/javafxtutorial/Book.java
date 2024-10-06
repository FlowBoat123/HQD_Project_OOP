package org.example.javafxtutorial;

import java.util.Objects;

public class Book {
    private String title;
    private String author;
    private String publisher;
    private String description;
    private String isbn_10;
    private String isbn_13;
    private String coverImgUrl;

    public Book(String title, String author, String publisher, String description, String isbn_10, String isbn_13, String coverImgUrl) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.description = description;
        this.isbn_10 = isbn_10;
        this.isbn_13 = isbn_13;
        this.coverImgUrl = coverImgUrl;
    }
    public Book() {}

    public String getIsbn_10() {
        return isbn_10;
    }

    public void setIsbn_10(String isbn_10) {
        this.isbn_10 = isbn_10;
    }

    public String getIsbn_13() {
        return isbn_13;
    }

    public void setIsbn_13(String isbn_13) {
        this.isbn_13 = isbn_13;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn_10, book.isbn_10) && Objects.equals(isbn_13, book.isbn_13);
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn_10='" + isbn_10 + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", description='" + description + '\'' +
                ", isbn_13='" + isbn_13 + '\'' +
                ", coverImgUrl='" + coverImgUrl + '\'' +
                '}';
    }
}
