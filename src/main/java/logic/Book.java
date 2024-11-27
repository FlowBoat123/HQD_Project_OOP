package logic;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a Book entity.
 * This class encapsulates the properties and behaviors of a book in the library system.
 */
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

    /**
     * Default constructor.
     */
    public Book() {
    }

    /**
     * Gets the status of the book.
     *
     * @return The status of the book.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the book.
     *
     * @param status The status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the ISBN-10 of the book.
     *
     * @return The ISBN-10 of the book.
     */
    public String getIsbn_10() {
        return isbn_10;
    }

    /**
     * Sets the ISBN-10 of the book.
     *
     * @param isbn_10 The ISBN-10 to set.
     */
    public void setIsbn_10(String isbn_10) {
        this.isbn_10 = isbn_10;
    }

    /**
     * Gets the ISBN-13 of the book.
     *
     * @return The ISBN-13 of the book.
     */
    public String getIsbn_13() {
        return isbn_13;
    }

    /**
     * Sets the ISBN-13 of the book.
     *
     * @param isbn_13 The ISBN-13 to set.
     */
    public void setIsbn_13(String isbn_13) {
        this.isbn_13 = isbn_13;
    }

    /**
     * Gets the title of the book.
     *
     * @return The title of the book.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the quantity of the book.
     *
     * @return The quantity of the book.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the book.
     *
     * @param quantity The quantity to set.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the number of borrowed copies of the book.
     *
     * @return The number of borrowed copies.
     */
    public int getBorrowedCopies() {
        return borrowedCopies;
    }

    /**
     * Sets the number of borrowed copies of the book.
     *
     * @param borrowedCopies The number of borrowed copies to set.
     */
    public void setBorrowedCopies(int borrowedCopies) {
        this.borrowedCopies = borrowedCopies;
    }

    /**
     * Sets the title of the book.
     *
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of the book.
     *
     * @return The description of the book.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the book.
     *
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the cover image URL of the book.
     *
     * @return The cover image URL of the book.
     */
    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    /**
     * Sets the cover image URL of the book.
     * If the URL is null or empty, a default placeholder URL is set.
     *
     * @param coverImgUrl The cover image URL to set.
     */
    public void setCoverImgUrl(String coverImgUrl) {
        if (coverImgUrl == null || coverImgUrl.isEmpty()) {
            this.coverImgUrl = "https://via.placeholder.com/180x240";
        }
        this.coverImgUrl = coverImgUrl;
    }

    /**
     * Gets the authors of the book as a single string.
     *
     * @return The authors of the book as a single string.
     */
    public String getAuthorString() {
        this.authorString = getAuthorsAsString();
        return authorString;
    }

    /**
     * Gets the genres of the book as a single string.
     *
     * @return The genres of the book as a single string.
     */
    public String getGenreString() {
        this.genreString = getGenresAsString();
        return genreString;
    }

    /**
     * Gets the number of requested copies of the book.
     *
     * @return The number of requested copies.
     */
    public int getRequestedCopies() {
        return requestedCopies;
    }

    /**
     * Sets the number of requested copies of the book.
     *
     * @param requestedCopies The number of requested copies to set.
     */
    public void setRequestedCopies(int requestedCopies) {
        this.requestedCopies = requestedCopies;
    }

    /**
     * Checks if this book is equal to another object.
     * Two books are considered equal if they have the same ISBN-10 or title.
     *
     * @param o The object to compare.
     * @return True if the books are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn_10, book.isbn_10) || Objects.equals(title, book.title);
    }

    /**
     * Gets the list of authors of the book.
     *
     * @return The list of authors.
     */
    public ArrayList<String> getAuthors() {
        return authors;
    }

    /**
     * Adds an author to the book.
     *
     * @param author The author to add.
     */
    public void addAuthor(String author) {
        if (authors == null) {
            authors = new ArrayList<>();
        }
        authors.add(author);
    }

    /**
     * Sets the list of authors of the book.
     *
     * @param authors The list of authors to set.
     */
    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    /**
     * Gets the authors of the book as a single string.
     *
     * @return The authors of the book as a single string.
     */
    public String getAuthorsAsString() {
        StringBuilder authorsString = new StringBuilder();
        if (authors == null) {
            return "Unknown author";
        }
        for (int i = 0; i < authors.size(); i++) {
            if (i == 0) {
                authorsString.append(authors.get(i));
            } else {
                authorsString.append(", ").append(authors.get(i));
            }
        }
        return authorsString.toString();
    }

    /**
     * Gets the list of genres of the book.
     *
     * @return The list of genres.
     */
    public ArrayList<String> getGenres() {
        return genres;
    }

    /**
     * Sets the list of genres of the book.
     *
     * @param genres The list of genres to set.
     */
    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    /**
     * Adds a genre to the book.
     *
     * @param genre The genre to add.
     */
    public void addGenre(String genre) {
        if (genres == null) {
            genres = new ArrayList<>();
        }
        genres.add(genre);
    }

    /**
     * Gets the genres of the book as a single string.
     *
     * @return The genres of the book as a single string.
     */
    public String getGenresAsString() {
        StringBuilder genresString = new StringBuilder();
        if (genres == null) {
            return "Unknown";
        }
        for (int i = 0; i < genres.size(); i++) {
            if (i == 0) {
                genresString.append(genres.get(i));
            } else {
                genresString.append(", ").append(genres.get(i));
            }
        }
        return genresString.toString();
    }

    /**
     * Sets the authors of the book from a single string.
     *
     * @param authorsString The authors as a single string.
     */
    public void setAuthorsFromString(String authorsString) {
        if (authorsString == null || authorsString.isEmpty()) {
            return;
        }
        String[] authorsArray = authorsString.split(", ");
        for (String author : authorsArray) {
            addAuthor(author);
        }
    }

    /**
     * Sets the genres of the book from a single string.
     *
     * @param genresString The genres as a single string.
     */
    public void setGenresFromString(String genresString) {
        if (genresString == null || genresString.isEmpty()) {
            return;
        }
        String[] genresArray = genresString.split(", ");
        for (String genre : genresArray) {
            addGenre(genre);
        }
    }

    /**
     * Gets the preview URL of the book.
     *
     * @return The preview URL of the book.
     */
    public String getPreviewUrl() {
        return previewUrl;
    }

    /**
     * Sets the preview URL of the book.
     * If the URL is null, a default placeholder URL is set.
     *
     * @param previewUrl The preview URL to set.
     */
    public void setPreviewUrl(String previewUrl) {
        if (previewUrl == null) {
            this.previewUrl = "https://www.youtube.com/watch?v=IzSYlr3VI1A";
        }
        this.previewUrl = previewUrl;
    }

    /**
     * Returns a string representation of the book.
     *
     * @return A string representation of the book.
     */
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