package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.javafxtutorial.DatabaseConnection;
import org.example.javafxtutorial.User;
import org.example.javafxtutorial.UserProfileController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserFormController {

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> passwordColumn;

    @FXML
    private TableColumn<User, LocalDateTime> creationTimeColumn;

    @FXML
    private TableColumn<User, Void> deleteColumn;

    @FXML
    private ProgressIndicator loadingIndicator;

    @FXML
    private Button masterTrashButton;

    private ObservableList<User> userData = FXCollections.observableArrayList();
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);
    private boolean showTrashIcons = false;

    @FXML
    private void initialize() {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        creationTimeColumn.setCellValueFactory(new PropertyValueFactory<>("creationTime"));

        loadUserData();

        userTable.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1 && !(event.getTarget() instanceof Button)) {
                    User clickedRow = row.getItem();
                    loadUserProfile(clickedRow);
                }
            });
            return row;
        });

        // Add the custom cell factory for the delete button
        deleteColumn.setCellFactory(createDeleteButtonCellFactory());

        // Handle master trash button click
        masterTrashButton.setOnAction(event -> toggleTrashIcons());
    }

    private void toggleTrashIcons() {
        showTrashIcons = !showTrashIcons;
        userTable.refresh();
    }

    private Callback<TableColumn<User, Void>, TableCell<User, Void>> createDeleteButtonCellFactory() {
        return new Callback<>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                final TableCell<User, Void> cell = new TableCell<>() {

                    private final Button trashButton = new Button();

                    {
                        ImageView trashIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/trash.png")));
                        trashIcon.setFitHeight(20);
                        trashIcon.setFitWidth(20);
                        trashButton.setGraphic(trashIcon);
                        trashButton.setOnAction(event -> {
                            User user = getTableView().getItems().get(getIndex());
                            System.out.println("Delete icon clicked for user: " + user.getUsername());
                            deleteUser(user);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || !showTrashIcons) {
                            setGraphic(null);
                        } else {
                            setGraphic(trashButton);
                        }
                    }
                };
                return cell;
            }
        };
    }

    private void deleteUser(User user) {
        System.out.println("Deleting user: " + user.getUsername());
        boolean removed = userData.remove(user);
        if (removed) {
            System.out.println("User removed from list: " + user.getUsername());
        } else {
            System.out.println("Failed to remove user from list: " + user.getUsername());
        }
        userTable.refresh();

        executorService.submit(() -> {
            try (Connection connection = DatabaseConnection.getConnection()) {
                if (connection != null) {
                    String deleteQuery = "DELETE FROM users WHERE username = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                        preparedStatement.setString(1, user.getUsername());
                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("User deleted from database: " + user.getUsername());
                        } else {
                            System.out.println("Failed to delete user from database: " + user.getUsername());
                        }
                    }
                } else {
                    System.out.println("Failed to establish database connection.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Failed to delete user data");
            }
        });
    }

    private void loadUserData() {
        Task<ObservableList<User>> task = new Task<>() {
            @Override
            protected ObservableList<User> call() {
                ObservableList<User> tempData = FXCollections.observableArrayList();
                String query = "SELECT username, password, creation_time FROM users";

                try (Connection connection = DatabaseConnection.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(query);
                     ResultSet resultSet = preparedStatement.executeQuery()) {

                    while (resultSet.next()) {
                        String username = resultSet.getString("username");
                        String password = resultSet.getString("password");
                        LocalDateTime creationTime = resultSet.getTimestamp("creation_time").toLocalDateTime();
                        tempData.add(new User(username, password, creationTime));
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                return tempData;
            }
        };

        task.setOnRunning(event -> loadingIndicator.setVisible(true));
        task.setOnSucceeded(event -> {
            loadingIndicator.setVisible(false);
            userData.clear();
            userData.addAll(task.getValue());
            userTable.setItems(userData);
        });

        task.setOnFailed(event -> {
            loadingIndicator.setVisible(false);
            Throwable exception = task.getException();
            exception.printStackTrace();
            // Handle exception
        });

        new Thread(task).start();
    }

    public void refreshTable() {
        loadUserData();
    }

    private void loadUserProfile(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/javafxtutorial/user_profile.fxml"));
            Parent userProfile = loader.load();

            // Pass user data to UserProfileController
            UserProfileController controller = loader.getController();
            controller.setUser(user);

            Stage stage = (Stage) userTable.getScene().getWindow();
            stage.setScene(new Scene(userProfile));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void stop() {
        executorService.shutdown();
    }
}
