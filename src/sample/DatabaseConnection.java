package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() {
        Connection connection = null;
        String databaseName = "MeubnGX";
        String databaseUser = "postgres";
        String databasePassword = "root";
        String url = "jdbc:postgresql://localhost:5432/MeubnGX";

        try {
            connection = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}