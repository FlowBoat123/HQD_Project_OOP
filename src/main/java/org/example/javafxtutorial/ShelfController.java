package org.example.javafxtutorial;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ShelfController {

    @FXML
    private TableColumn<?, ?> bookAuthorsCol;

    @FXML
    private TableColumn<?, ?> bookGenresCol;

    @FXML
    private TableColumn<?, ?> bookIsbn10Col;

    @FXML
    private TableColumn<?, ?> bookIsbn13Col;

    @FXML
    private TableColumn<?, ?> bookTitleCol;

    @FXML
    private TextField searchField;

    @FXML
    private Label shelfName;
    public void initializeShelfView(Shelf shelf) {
        shelfName.setText(shelf.getName());
    }


    @FXML
    void handleDelete(ActionEvent event) {
        System.out.println("delete");
    }

    @FXML
    void handleEdit(ActionEvent event) {
        System.out.println("edit");
    }

    @FXML
    void handleSearch(MouseEvent event) {
        System.out.println(searchField.getText());
    }

}
