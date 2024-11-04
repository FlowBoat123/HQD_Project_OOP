package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LibraryViewController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
