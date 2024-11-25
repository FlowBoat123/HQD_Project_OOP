package org.example.javafxtutorial;

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

public class CommentController implements Initializable {
    private String lastText;
    @FXML
    private TextArea textArea;

    @FXML
    void textAreaTyped(KeyEvent event) {
        ObservableList<CharSequence> list = textArea.getParagraphs();
        lastText = textArea.getText();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        textArea.setText(lastText);
    }

    @FXML
    private void handleBack() {
        String comment = textArea.getText();

        textArea.setEditable(false);

        Stage stage = (Stage) textArea.getScene().getWindow();
        stage.close();
    }

    public String getComment() {
        return textArea.getText();
    }

    public String getUsername() {
        return UserSession.getInstance().getUser().getUsername();
    }

    public int getUserID() {
        return UserSession.getInstance().getUserID();
    }

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