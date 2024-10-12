package org.example.javafxtutorial;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ShelfController {

    @FXML
    private TextField searchField;

    @FXML
    private TextField shelfFiled;

    @FXML
    private Label shelfName;

    @FXML
    private GridPane bookContainer;

    public void initializeShelfView(Shelf shelf) {
        shelfName.setText(shelf.getName());
        Book book = new Book();
        book.setTitle("Book Title");
        book.addAuthor("Author Name");
        book.addGenre("Genre");
        book.setIsbn_10("1234567890");
        book.setIsbn_13("1234567890123");
        book.setDescription("Book Description");
        book.setCoverImgUrl("https://via.placeholder.com/180x240");
        for( int i=0; i<9;i++) {
            shelf.addBook(book);
        }
        int col = 0;
        int row = 1;
        try {
            for (Book b: shelf.getBooks()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/javafxtutorial/book-card.fxml"));
                VBox bookCard = loader.load();
                BookCardController bookCardController = loader.getController();
                bookCardController.initializeBookCard(b);
                bookContainer.add(bookCard, col, row);
                col++;
                if (col == 5) {
                    col = 0;
                    row++;
                }
                GridPane.setMargin(bookCard, new Insets(2));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
