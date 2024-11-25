package logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

  private Book book;

  @BeforeEach
  void setUp() {
    book = new Book();
  }

  @Test
  void testSetAndGetTitle() {
    book.setTitle("The Great Gatsby");
    assertEquals("The Great Gatsby", book.getTitle());
  }

  @Test
  void testSetAndGetDescription() {
    book.setDescription("A novel by F. Scott Fitzgerald");
    assertEquals("A novel by F. Scott Fitzgerald", book.getDescription());
  }

  @Test
  void testSetAndGetIsbn_10() {
    book.setIsbn_10("1234567890");
    assertEquals("1234567890", book.getIsbn_10());
  }

  @Test
  void testSetAndGetIsbn_13() {
    book.setIsbn_13("1234567890123");
    assertEquals("1234567890123", book.getIsbn_13());
  }

  @Test
  void testSetAndGetCoverImgUrl() {
    book.setCoverImgUrl("http://example.com/cover.jpg");
    assertEquals("http://example.com/cover.jpg", book.getCoverImgUrl());
  }

  @Test
  void testSetAndGetStatus() {
    book.setStatus("Available");
    assertEquals("Available", book.getStatus());
  }

  @Test
  void testSetAndGetQuantity() {
    book.setQuantity(10);
    assertEquals(10, book.getQuantity());
  }

  @Test
  void testSetAndGetBorrowedCopies() {
    book.setBorrowedCopies(3);
    assertEquals(3, book.getBorrowedCopies());
  }

  @Test
  void testAddAuthor() {
    book.addAuthor("F. Scott Fitzgerald");
    assertEquals("F. Scott Fitzgerald", book.getAuthors().get(0));
  }

  @Test
  void testSetAuthors() {
    ArrayList<String> authors = new ArrayList<>();
    authors.add("F. Scott Fitzgerald");
    authors.add("Another Author");
    book.setAuthors(authors);
    assertEquals(authors, book.getAuthors());
  }

  @Test
  void testGetAuthorsAsString() {
    book.addAuthor("F. Scott Fitzgerald");
    book.addAuthor("Another Author");
    assertEquals("F. Scott Fitzgerald, Another Author", book.getAuthorsAsString());
  }

  @Test
  void testGetAuthorsAsStringWithNoAuthors() {
    assertEquals("Unknown author", book.getAuthorsAsString());
  }

  @Test
  void testAddGenre() {
    book.addGenre("Fiction");
    assertEquals("Fiction", book.getGenres().get(0));
  }

  @Test
  void testSetGenres() {
    ArrayList<String> genres = new ArrayList<>();
    genres.add("Fiction");
    genres.add("Classic");
    book.setGenres(genres);
    assertEquals(genres, book.getGenres());
  }

  @Test
  void testGetGenresAsString() {
    book.addGenre("Fiction");
    book.addGenre("Classic");
    assertEquals("Fiction, Classic", book.getGenresAsString());
  }

  @Test
  void testGetGenresAsStringWithNoGenres() {
    assertEquals("Unknown", book.getGenresAsString());
  }

  @Test
  void testSetAuthorsFromString() {
    book.setAuthorsFromString("F. Scott Fitzgerald, Another Author");
    assertEquals("F. Scott Fitzgerald, Another Author", book.getAuthorsAsString());
  }

  @Test
  void testSetGenresFromString() {
    book.setGenresFromString("Fiction, Classic");
    assertEquals("Fiction, Classic", book.getGenresAsString());
  }

  @Test
  void testEquals() {
    Book book1 = new Book();
    book1.setIsbn_10("1234567890");
    book1.setTitle("The Great Gatsby");

    Book book2 = new Book();
    book2.setIsbn_10("1234567890");
    book2.setTitle("The Great Gatsby");

    Book book3 = new Book();
    book3.setIsbn_10("0987654321");
    book3.setTitle("Another Book");

    assertTrue(book1.equals(book2));
    assertFalse(book1.equals(book3));
  }

  @Test
  void testToString() {
    book.setTitle("The Great Gatsby");
    book.setAuthorsFromString("F. Scott Fitzgerald");
    book.setDescription("A novel by F. Scott Fitzgerald");
    book.setGenresFromString("Fiction, Classic");
    book.setIsbn_10("1234567890");
    book.setIsbn_13("1234567890123");
    book.setCoverImgUrl("http://example.com/cover.jpg");
    book.setStatus("Available");
    book.setQuantity(10);
    book.setBorrowedCopies(3);

    String expected = "Book{" +
        "title='The Great Gatsby'" +
        ", authors=[F. Scott Fitzgerald]" +
        ", description='A novel by F. Scott Fitzgerald'" +
        ", genres=[Fiction, Classic]" +
        ", isbn_10='1234567890'" +
        ", isbn_13='1234567890123'" +
        ", coverImgUrl='http://example.com/cover.jpg'" +
        ", status=Available" +
        ", quantity=10" +
        ", borrowedCopies=3" +
        '}';

    assertEquals(expected, book.toString());
  }
}