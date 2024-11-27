package logic;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class CommentTest {

  private Comment comment;
  private int userID = 1;
  private String username = "testUser";
  private String avatarPath = "/images/testAvatar.png";
  private String commentText = "This is a test comment.";
  private String date = "2023-10-01";
  private String bookIsbn13 = "1234567890123";

  @BeforeEach
  void setUp() {
    comment = new Comment(userID, username, avatarPath, commentText, date, bookIsbn13);
  }

  @Test
  void testGetUserID() {
    assertEquals(userID, comment.getUserID());
  }

  @Test
  void testSetUserID() {
    int newUserID = 2;
    comment.setUserID(newUserID);
    assertEquals(newUserID, comment.getUserID());
  }

  @Test
  void testGetUsername() {
    assertEquals(username, comment.getUsername());
  }

  @Test
  void testSetUsername() {
    String newUsername = "newUser";
    comment.setUsername(newUsername);
    assertEquals(newUsername, comment.getUsername());
  }

  @Test
  void testGetComment() {
    assertEquals(commentText, comment.getComment());
  }

  @Test
  void testSetComment() {
    String newComment = "This is a new comment.";
    comment.setComment(newComment);
    assertEquals(newComment, comment.getComment());
  }

  @Test
  void testGetDate() {
    assertEquals(date, comment.getDate());
  }

  @Test
  void testSetDate() {
    String newDate = "2023-10-02";
    comment.setDate(newDate);
    assertEquals(newDate, comment.getDate());
  }

  @Test
  void testGetBook_isbn_13() {
    assertEquals(bookIsbn13, comment.getBook_isbn_13());
  }

  @Test
  void testSetBook_isbn_13() {
    String newBookIsbn13 = "3210987654321";
    comment.setBook_isbn_13(newBookIsbn13);
    assertEquals(newBookIsbn13, comment.getBook_isbn_13());
  }

  @Test
  void testGetAvatarPath() {
    assertEquals(avatarPath, comment.getAvatarPath());
  }

  @Test
  void testSetAvatarPath() {
    String newAvatarPath = "/images/newAvatar.png";
    comment.setAvatarPath(newAvatarPath);
    assertEquals(newAvatarPath, comment.getAvatarPath());
  }
}