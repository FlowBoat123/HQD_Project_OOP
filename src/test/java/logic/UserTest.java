package logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

  private User user;

  @BeforeEach
  void setUp() {
    user = new User();
  }

  @Test
  void testDefaultConstructor() {
    assertEquals(1010, user.getID());
    assertEquals("FlowBoat123", user.getUsername());
    assertEquals("123", user.getPassword());
    assertEquals("LoveCraft", user.getBio());
    assertEquals("hienflb@gmail.com", user.getEmail());
    assertEquals("love.craft", user.getWebsite());
    assertEquals(0, user.getRepoCount());
    assertEquals(0, user.getFollowerCount());
    assertEquals(0, user.getFollowingCount());
  }

  @Test
  void testConstructorWithParameters() {
    Date creationTime = new Date();
    User user = new User(1, "testUser", "testPass", creationTime, "testBio", "test@email.com", "test.website", 1, 2, 3);

    assertEquals(1, user.getID());
    assertEquals("testUser", user.getUsername());
    assertEquals("testPass", user.getPassword());
    assertEquals(creationTime, user.getCreationTime());
    assertEquals("testBio", user.getBio());
    assertEquals("test@email.com", user.getEmail());
    assertEquals("test.website", user.getWebsite());
    assertEquals(1, user.getRepoCount());
    assertEquals(2, user.getFollowerCount());
    assertEquals(3, user.getFollowingCount());
  }

  @Test
  void testSetAndGetID() {
    user.setID(1);
    assertEquals(1, user.getID());
  }

  @Test
  void testSetAndGetUsername() {
    user.setUsername("testUser");
    assertEquals("testUser", user.getUsername());
  }

  @Test
  void testSetAndGetPassword() {
    user.setPassword("testPass");
    assertEquals("testPass", user.getPassword());
  }

  @Test
  void testSetAndGetCreationTime() {
    Date creationTime = new Date();
    user.setCreationTime(creationTime);
    assertEquals(creationTime, user.getCreationTime());
  }

  @Test
  void testSetAndGetBio() {
    user.setBio("testBio");
    assertEquals("testBio", user.getBio());
  }

  @Test
  void testSetAndGetEmail() {
    user.setEmail("test@email.com");
    assertEquals("test@email.com", user.getEmail());
  }

  @Test
  void testSetAndGetWebsite() {
    user.setWebsite("test.website");
    assertEquals("test.website", user.getWebsite());
  }

  @Test
  void testSetAndGetRepoCount() {
    user.setRepoCount(1);
    assertEquals(1, user.getRepoCount());
  }

  @Test
  void testSetAndGetFollowerCount() {
    user.setFollowerCount(2);
    assertEquals(2, user.getFollowerCount());
  }

  @Test
  void testSetAndGetFollowingCount() {
    user.setFollowingCount(3);
    assertEquals(3, user.getFollowingCount());
  }

  @Test
  void testToString() {
    Date creationTime = new Date();
    User user = new User(1, "testUser", "testPass", creationTime, "testBio", "test@email.com", "test.website", "testDetails", "testAvatar");

    String expected = "User{" +
        "ID=1" +
        ", username='testUser'" +
        ", password='testPass'" +
        ", creationTime=" + creationTime +
        ", bio='testBio'" +
        ", email='test@email.com'" +
        ", website='test.website'" +
        ", details='testDetails'" +
        ", avatar='testAvatar'" +
        ", repoCount=0" +
        ", followerCount=0" +
        ", followingCount=0" +
        '}';

    assertEquals(expected, user.toString());
  }
}