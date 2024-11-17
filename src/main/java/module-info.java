module org.example.javafxtutorial {
    requires org.slf4j;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires com.zaxxer.hikari;
    requires java.sql;
  requires java.desktop;

  opens org.example.javafxtutorial to javafx.fxml;
    exports org.example.javafxtutorial;

    opens controller to javafx.fxml;
    exports controller;
    exports database;
    opens database to javafx.fxml;
    exports logic;
    opens logic to javafx.fxml;
}