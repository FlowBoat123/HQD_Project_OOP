package controller;

import database.DataSourceFactory;
import database.UserDAO;
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
import logic.User;
import logic.LibraryService;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Controller class for managing the user form in a JavaFX application.
 * This class handles the display of user data in a table, loading user profiles,
 * and deleting user records.
 */
public class UserFormController {

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> passwordColumn;

    @FXML
    private TableColumn<User, Date> creationTimeColumn;

    @FXML
    private TableColumn<User, String> bioColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<User, String> websiteColumn;

    @FXML
    private TableColumn<User, Void> deleteColumn;

    @FXML
    private ProgressIndicator loadingIndicator;

    @FXML
    private Button masterTrashButton;

    private DataSource dataSource;
    private ObservableList<User> userData = FXCollections.observableArrayList();
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);
    private boolean showTrashIcons = false;
    private LibraryService libraryService;

    /**
     * Constructor for the UserFormController class.
     * Initializes the data source for database connections.
     */
    public UserFormController() {
        this.dataSource = DataSourceFactory.getDataSource();
    }

    /**
     * Initializes the controller class. This method sets up the table columns,
     * loads user data, and configures the table row and delete button behavior.
     */
    @FXML
    private void initialize() {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        creationTimeColumn.setCellValueFactory(new PropertyValueFactory<>("creationTime"));
        bioColumn.setCellValueFactory(new PropertyValueFactory<>("bio"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        websiteColumn.setCellValueFactory(new PropertyValueFactory<>("website"));
        // Custom cell factory for creation time column
        creationTimeColumn.setCellFactory(column -> new TableCell<User, Date>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter));
                }
            }
        });

        loadUserData();

        // Set row factory to handle row click events
        userTable.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1 && !(event.getTarget() instanceof Button)) {
                    User clickedRow = row.getItem();
                    if (!clickedRow.getUsername().equals("admin")) {
                        loadUserProfile(clickedRow);
                    }
                }
            });
            return row;
        });

        // Add the custom cell factory for the delete button
        deleteColumn.setCellFactory(createDeleteButtonCellFactory());

        // Handle master trash button click
        masterTrashButton.setOnAction(event -> toggleTrashIcons());
    }

    /**
     * Toggles the visibility of the delete icons in the table.
     */
    private void toggleTrashIcons() {
        showTrashIcons = !showTrashIcons;
        if(showTrashIcons) {
            masterTrashButton.setText("Stop");
        }
        else {
            masterTrashButton.setText("Delete user");
        }
        userTable.refresh();
    }

    /**
     * Creates a custom cell factory for the delete button column.
     *
     * @return The custom cell factory for the delete button column.
     */
    private Callback<TableColumn<User, Void>, TableCell<User, Void>> createDeleteButtonCellFactory() {
        return new Callback<>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                final TableCell<User, Void> cell = new TableCell<>() {

                    private final Button trashButton = new Button();

                    {
                        ImageView trashIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/trash.png"))));
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

    /**
     * Deletes a user from the table and the database.
     *
     * @param user The user to be deleted.
     */
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
            try (Connection connection = dataSource.getConnection()) {
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

    /**
     * Loads user data from the database and populates the table.
     */
    private void loadUserData() {
        Task<ObservableList<User>> task = new Task<>() {
            @Override
            protected ObservableList<User> call() {
                ObservableList<User> tempData = FXCollections.observableArrayList();
                UserDAO userDAO = new UserDAO();

                try {
                    List<User> userList = userDAO.getAll();
                    tempData.addAll(userList);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("Failed to load user data", e);
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

    /**
     * Refreshes the user table by reloading the user data.
     */
    public void refreshTable() {
        loadUserData();
    }

    /**
     * Loads the user profile view for a selected user.
     *
     * @param user The user whose profile is to be displayed.
     */
    private void loadUserProfile(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/user_profile.fxml"));
            Parent userProfile = loader.load();

            // Pass user data to UserProfileController
            UserProfileController controller = loader.getController();
            controller.setUser(user);
            controller.setAdmin();
            controller.setLibraryService(libraryService);
            Stage stage = (Stage) userTable.getScene().getWindow();
            stage.setScene(new Scene(userProfile));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stops the executor service and shuts down the thread pool.
     */
    @FXML
    public void stop() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

    /**
     * Sets the library service for this controller.
     *
     * @param libraryService The library service instance.
     */
    public void setLibraryService(LibraryService libraryService) {
        this.libraryService = libraryService;
    }
}