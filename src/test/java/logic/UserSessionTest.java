package logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserSessionTest {

  private UserSession userSession;

  @BeforeEach
  void setUp() {
    // Ensure a fresh instance for each test
    userSession = UserSession.getInstance();
    userSession.setUserID(0); // Reset to a default state
  }

  @Test
  void testGetInstance() {
    UserSession instance1 = UserSession.getInstance();
    UserSession instance2 = UserSession.getInstance();
    assertSame(instance1, instance2);
  }

  @Test
  void testGetUserID() {
    userSession.setUserID(123);
    assertEquals(123, userSession.getUserID());
  }

  @Test
  void testIsAdmin() {
    userSession.setUserID(1);
    assertTrue(userSession.isAdmin());

    userSession.setUserID(2);
    assertFalse(userSession.isAdmin());
  }

  @Test
  void testSetUserID() {
    userSession.setUserID(456);
    assertEquals(456, userSession.getUserID());
  }
}