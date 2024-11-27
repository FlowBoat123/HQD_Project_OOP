package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import logic.User;
import logic.UserSession;

/**
 * Controller class for managing comments in a JavaFX application.
 * This class handles the initialization of the comment text area,
 * capturing user input, and retrieving user information.
 */
public class CommentController implements Initializable {

    private String lastText;

    @FXML
    private TextArea textArea;

    /**
     * Handles the key event when text is typed in the text area.
     * This method captures the current text in the text area.
     *
     * @param event The key event triggered by the user.
     */
    @FXML
    void textAreaTyped(KeyEvent event) {
        ObservableList<CharSequence> list = textArea.getParagraphs();
        lastText = textArea.getText();
    }

    /**
     * Initializes the controller class. This method sets the initial text
     * in the text area to the last saved text.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textArea.setText(lastText);
    }

    /**
     * Handles the action of going back. This method retrieves the current
     * comment text, disables further editing, and closes the stage.
     */
    @FXML
    private void handleBack() {
        String comment = textArea.getText();

        textArea.setEditable(false);

        Stage stage = (Stage) textArea.getScene().getWindow();
        stage.close();
    }

    /**
     * Gets the current comment text from the text area.
     *
     * @return The current comment text.
     */
    public String getComment() {
        return textArea.getText();
    }

    /**
     * Gets the username of the current user from the user session.
     *
     * @return The username of the current user.
     */
    public String getUsername() {
        return UserSession.getInstance().getUser().getUsername();
    }

    /**
     * Gets the user ID of the current user from the user session.
     *
     * @return The user ID of the current user.
     */
    public int getUserID() {
        return UserSession.getInstance().getUserID();
    }

    /**
     * Gets the user image of the current user from the user session.
     * This method retrieves the user's avatar image and sets it to the ImageView.
     *
     * @return The ImageView containing the user's avatar image.
     */
    public ImageView getUserImage() {
        ImageView userImage = new ImageView();
        String path;
        User currentUser = UserSession.getInstance().getUser();
        System.out.println(currentUser.getAvatar());
        if (currentUser.getAvatar().isEmpty()) {
            System.out.println("Empty");
            path = getClass().getResource("/images/usericon.png").toExternalForm();
        } else {
            path = getClass().getResource(currentUser.getAvatar()).toExternalForm();
        }
        userImage.setImage(new Image(path, true));
        userImage.setFitHeight(54.0);
        userImage.setFitWidth(70.0);
        userImage.setPreserveRatio(true);
        return userImage;
    }
}