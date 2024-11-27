module org.example.javafxtutorial {
    requires org.slf4j;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires com.google.gson;
    requires com.zaxxer.hikari;
    requires java.sql;
    requires java.desktop;
    requires org.xerial.sqlitejdbc;
    requires com.google.zxing;
    requires com.google.zxing.javase;


    opens application to javafx.fxml;
    exports application;

    opens controller to javafx.fxml;
    exports controller;

    exports database;
    opens database to javafx.fxml;

    exports logic;
    opens logic to javafx.fxml;
}