package logic;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;

public class Comment {
    private int userID;
    private String username;
    private ImageView userImage;
    private String comment;
    private String date;
    private String book_isbn_13;
    private String avatarPath;
    public Comment(int userID, String username, ImageView userImage, String comment, String date, String bookIsbn13) {
        this.userID = userID;
        this.username = username;
        this.userImage = userImage;
        this.comment = comment;
        this.date = date;
        this.book_isbn_13 = bookIsbn13;
    }

    public Comment(int userID, String username, String avatarPath, String comment, String date, String book_isbn_13) {
        this.userID = userID;
        this.username = username;
        this.avatarPath = avatarPath;
        this.comment = comment;
        this.date = date;
        this.book_isbn_13 = book_isbn_13;
    }

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

    public ImageView getUserImage() {
        if (userImage != null) {
            return userImage;
        }
        ImageView userImage = new ImageView();
        String path;
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
        userImage.setImage(new Image(path, true));
        userImage.setFitHeight(54.0);
        userImage.setFitWidth(70.0);
        userImage.setPreserveRatio(true);
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