package database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DataSourceFactory {
    private static HikariDataSource dataSource = null;

    static {
//        dataSource = new HikariDataSource();
//        dataSource.setJdbcUrl("jdbc:sqlite:src\\main\\java\\database\\data.sqlite");

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

    public static DataSource getDataSource() {
        return dataSource;
    }
}