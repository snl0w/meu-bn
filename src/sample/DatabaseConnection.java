package sample;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection(){
        String databaseMeuBN = "meubngx";
        String databaseUsuario = "root";
        String databaseSenha = "12345Kauan.";
        String url = "jdbc:mysql://localhost/"+ databaseMeuBN;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUsuario, databaseSenha);
        } catch (Exception e){
            e.printStackTrace();
        }

        return databaseLink;
    }
}
