package org.example.javafxtutorial;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class LoginApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icon.png")));
        primaryStage.getIcons().add(icon);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/javafxtutorial/login.fxml")));
        primaryStage.setTitle("Library Management");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
