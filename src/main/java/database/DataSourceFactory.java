package database;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DataSourceFactory {
    private static HikariDataSource dataSource = null;

    static {
        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:sqlite:src\\main\\java\\database\\data.sqlite");
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}