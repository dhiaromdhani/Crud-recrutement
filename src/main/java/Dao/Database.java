package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/recrutement_db"; // URL de ta base de données
    private static final String USER = "root"; // Utilisateur MySQL
    private static final String PASSWORD = ""; // Mot de passe MySQL

    public static Connection getConnection() {
        try {
            // Charger le driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
