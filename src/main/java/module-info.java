module org.example.javafxtutorial {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.javafxtutorial to javafx.fxml;
    exports org.example.javafxtutorial;
}