package database;

import logic.Comment;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CommentDAO implements DAO<Comment> {
    private DataSource dataSource;

    public CommentDAO() {
        this.dataSource = DataSourceFactory.getDataSource();
    }

    @Override
    public void add(Comment comment) {
        String query = "INSERT INTO comments (book_isbn_13, userID, comment, time) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, comment.getBook_isbn_13());
            statement.setInt(2, comment.getUserID());
            statement.setString(3, comment.getComment());
            statement.setString(4, comment.getDate());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Comment comment) {

    }

    @Override
    public void update(Comment comment) {

    }

    @Override
    public ArrayList<Comment> getAll() {
        ArrayList<Comment> comments = new ArrayList<>();
        String query = "SELECT * FROM comments JOIN users u on u.id = comments.userID";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Comment comment = new Comment(
                        resultSet.getInt("userID"),
                        resultSet.getString("username"),
                        resultSet.getString("avatar"),
                        resultSet.getString("comment"),
                        resultSet.getString("time"),
                        resultSet.getString("book_isbn_13")
                );
                comments.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }
}
