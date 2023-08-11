package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/schema1";
    private static final String JDBC_USERNAME = "sjoo";
    private static final String JDBC_PASSWORD = "pass";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }
}
