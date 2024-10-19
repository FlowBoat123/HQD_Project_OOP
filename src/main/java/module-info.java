module org.example.javafxtutorial {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires com.zaxxer.hikari;
    requires java.sql;


    opens org.example.javafxtutorial to javafx.fxml;
    exports org.example.javafxtutorial;
}