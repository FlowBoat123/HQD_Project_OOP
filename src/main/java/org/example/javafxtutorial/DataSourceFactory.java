package org.example.javafxtutorial;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DataSourceFactory {
    private static HikariDataSource dataSource = null;

    static {
        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://mysql-3417d26-vnu-7971.b.aivencloud.com:11871/library_management?useSSL=true");
        dataSource.setUsername("avnadmin");
        dataSource.setPassword("AVNS_KwvYFp4CEP2D-pRqfYd");
    }

    public static DataSource getDataSource() {
        return dataSource;
    }


}
