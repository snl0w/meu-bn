package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Conexão do banco de dados
public class DatabaseConnection {
    public static Connection getConnection() {
        Connection connection = null;
        String databaseName = "meubngx";
        String databaseUser = "root";
        String databasePassword = "243245Afv*";
        String url = "jdbc:mysql://localhost:3306/" + databaseName;

        try {
            connection = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
