package logic;

import java.util.ArrayList;
import java.util.Objects;

public class Book {
    private String title;
    private ArrayList<String> authors = null;
    private String authorString;
    private String description;
    private ArrayList<String> genres = null;
    private String genreString;
    private String isbn_10;
    private String isbn_13;
    private String coverImgUrl;
    private String status;
    private String previewUrl;
    private int quantity;
    private int borrowedCopies;
    private int requestedCopies;


    public Book() {}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getBorrowedCopies() {
        return borrowedCopies;
    }

    public void setBorrowedCopies(int borrowedCopies) {
        this.borrowedCopies = borrowedCopies;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getAuthorString() {
        this.authorString = getAuthorsAsString();
        return authorString;
    }

    public String getGenreString() {
        this.genreString = getGenresAsString();
        return genreString;
    }

    public int getRequestedCopies() {
        return requestedCopies;
    }

    public void setRequestedCopies(int requestedCopies) {
        this.requestedCopies = requestedCopies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn_10, book.isbn_10) || Objects.equals(title, book.title);
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
            return "Unknown";
        }
        for (int i = 0; i<genres.size(); i++){
            if (i==0) {
                genresString.append(genres.get(i));
            } else {
                genresString.append(", ").append(genres.get(i));
            }
        }
        return genresString.toString();
    }
    public void setAuthorsFromString(String authorsString){
        if (authorsString == null || authorsString.isEmpty()) {
            return;
        }
        String[] authorsArray = authorsString.split(", ");
        for (String author : authorsArray) {
            addAuthor(author);
        }
    }
    public void setGenresFromString(String genresString){
        if (genresString == null || genresString.isEmpty()) {
            return;
        }
        String[] genresArray = genresString.split(", ");
        for (String genre : genresArray) {
            addGenre(genre);
        }
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        if (previewUrl == null) {
            this.previewUrl = "https://www.youtube.com/watch?v=IzSYlr3VI1A";
        }
        this.previewUrl = previewUrl;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", authors=" + authors +
                ", description='" + description + '\'' +
                ", genres=" + genres +
                ", isbn_10='" + isbn_10 + '\'' +
                ", isbn_13='" + isbn_13 + '\'' +
                ", coverImgUrl='" + coverImgUrl + '\'' +
                ", status=" + status +
                ", quantity=" + quantity +
                ", borrowedCopies=" + borrowedCopies +
                '}';
    }
}
