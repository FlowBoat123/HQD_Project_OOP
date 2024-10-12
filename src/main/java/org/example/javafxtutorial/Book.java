package org.example.javafxtutorial;

import java.util.ArrayList;
import java.util.Objects;

public class Book {
    private String title;
    private ArrayList<String> authors = null;
    private String publisher;
    private String description;
    private ArrayList<String> genres = null;
    private String isbn_10;
    private String isbn_13;
    private String coverImgUrl;

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
        if (coverImgUrl == null || coverImgUrl.isEmpty()) {
            this.coverImgUrl = "https://via.placeholder.com/180x240";
        }
        this.coverImgUrl = coverImgUrl;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn_10, book.isbn_10) && Objects.equals(isbn_13, book.isbn_13);
    }
    public ArrayList<String> getAuthors() {
        return authors;
    }
    public void addAuthor(String author) {
        if (authors == null) {
            authors = new ArrayList<>();
        }
        authors.add(author);
    }
    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }
    public String getAuthorsAsString(){
        StringBuilder authorsString = new StringBuilder();
        if (authors == null) {
            return "Unknown author";
        }
        for (int i = 0; i<authors.size(); i++){
            if (i==0) {
                authorsString.append(authors.get(i));
            } else {
                authorsString.append(", ").append(authors.get(i));
            }
        }
        return authorsString.toString();
    }
    public ArrayList<String> getGenres() {
        return genres;
    }
    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }
    public void addGenre(String genre) {
        if (genres == null) {
            genres = new ArrayList<>();
        }
        genres.add(genre);
    }
    public String getGenresAsString(){
        StringBuilder genresString = new StringBuilder();
        if (genres == null) {
            return "Genres: Unknown";
        }
        for (int i = 0; i<genres.size(); i++){
            if (i==0) {
                genresString.append("Genres: ").append(genres.get(i));
            } else {
                genresString.append(", ").append(genres.get(i));
            }
        }
        return genresString.toString();
    }
}
