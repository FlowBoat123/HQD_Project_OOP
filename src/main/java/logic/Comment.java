package logic;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;

/**
 * Represents a user comment on a book, including user details, the comment text,
 * the date it was made, and the associated book's ISBN-13.
 */
public class Comment {
    private int userID;             // Unique identifier for the user
    private String username;        // Username of the user
    private ImageView userImage;    // Avatar image of the user
    private String comment;         // Text of the comment
    private String date;            // Date the comment was made
    private String book_isbn_13;    // ISBN-13 of the associated book
    private String avatarPath;      // Path to the avatar image

    /**
     * Constructs a Comment object with an ImageView for the user avatar.
     *
     * @param userID      Unique identifier for the user
     * @param username    Username of the user
     * @param userImage   ImageView containing the user's avatar
     * @param comment     Text of the comment
     * @param date        Date the comment was made
     * @param bookIsbn13  ISBN-13 of the associated book
     */
    public Comment(int userID, String username, ImageView userImage, String comment, String date, String bookIsbn13) {
        this.userID = userID;
        this.username = username;
        this.userImage = userImage;
        this.comment = comment;
        this.date = date;
        this.book_isbn_13 = bookIsbn13;
    }

    /**
     * Constructs a Comment object with a path to the user avatar image.
     *
     * @param userID       Unique identifier for the user
     * @param username     Username of the user
     * @param avatarPath   Path to the user's avatar image
     * @param comment      Text of the comment
     * @param date         Date the comment was made
     * @param book_isbn_13 ISBN-13 of the associated book
     */
    public Comment(int userID, String username, String avatarPath, String comment, String date, String book_isbn_13) {
        this.userID = userID;
        this.username = username;
        this.avatarPath = avatarPath;
        this.comment = comment;
        this.date = date;
        this.book_isbn_13 = book_isbn_13;
    }

    // Getters and setters for all fields

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the user's avatar as an ImageView.
     * If the avatar is not set, it attempts to load an image from `avatarPath`.
     * If `avatarPath` is null or invalid, it loads a default avatar image.
     *
     * @return ImageView representing the user's avatar
     */
    public ImageView getUserImage() {
        if (userImage != null) {
            return userImage; // Return existing ImageView if available
        }

        ImageView userImage = new ImageView();
        String path;

        // Determine the image path based on avatarPath or fallback to default
        if (avatarPath != null) {
            URL resource = getClass().getResource(avatarPath);
            if (resource != null) {
                path = resource.toExternalForm();
            } else {
                path = getClass().getResource("/images/usericon.png").toExternalForm();
            }
        } else {
            path = getClass().getResource("/images/usericon.png").toExternalForm();
        }

        // Configure the ImageView with the determined image
        userImage.setImage(new Image(path, true));
        userImage.setFitHeight(54.0);  // Set height of the image
        userImage.setFitWidth(70.0);   // Set width of the image
        userImage.setPreserveRatio(true);  // Preserve aspect ratio

        return userImage;
    }

    public void setUserImage(ImageView userImage) {
        this.userImage = userImage;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBook_isbn_13() {
        return book_isbn_13;
    }

    public void setBook_isbn_13(String book_isbn_13) {
        this.book_isbn_13 = book_isbn_13;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }
}
