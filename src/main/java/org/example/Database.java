package org.example;

import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

    private static Database instance;
    private final Connection connection;

    private static final String URL = "jdbc:h2:./testdb";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private Database() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            Flyway flyway = Flyway.configure()
                    .dataSource(URL, USER, PASSWORD)
                    .load();

            flyway.migrate();

        } catch (Exception e) {
            throw new RuntimeException("Cannot connect to DB", e);
        }
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}