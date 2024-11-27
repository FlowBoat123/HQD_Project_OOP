package database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

/**
 * Factory class for creating and managing the DataSource.
 * This class uses HikariCP for connection pooling to efficiently manage database connections.
 */
public class DataSourceFactory {
    private static HikariDataSource dataSource = null;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:sqlite:src\\main\\java\\database\\data.sqlite");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);
        config.setLeakDetectionThreshold(2000);

        dataSource = new HikariDataSource(config);
    }

    /**
     * Retrieves the DataSource instance.
     *
     * @return The DataSource instance.
     */
    public static DataSource getDataSource() {
        return dataSource;
    }
}