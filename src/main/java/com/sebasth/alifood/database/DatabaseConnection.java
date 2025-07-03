package com.sebasth.alifood.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties; // for future use

public class DatabaseConnection {
    // database connection instance
    private static Connection connection = null;
    private static final String URL = "jdbc:mysql://localhost:3306/alifood";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    // public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static Connection getConnection() throws SQLException{
        if (connection == null || connection.isClosed()){
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                System.err.println("Error connecting to the database: " + e.getMessage());
                throw e;
            }
        }
        return connection;
    }

    // Close the connection
    public static void closeConnection(){
        if (connection != null){
            try {
                connection.close();
                connection = null;
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing the database connection: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // method to initialize the database connection(create tables if not exist)
    public static void initializeDatabase(){
        try (Connection conn = getConnection();
        java.sql.Statement stmt = conn.createStatement()) {

            // Create table of user
            String createUserTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(50) NOT NULL UNIQUE," +
                    "passwordHash VARCHAR(255) NOT NULL" +
                    ")";
            stmt.execute(createUserTableSQL);
            System.out.println("User table created/verify.");

            // Crete table of food
            String createFoodTableSQL = "CREATE TABLE IF NOT EXISTS food (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(100) NOT NULL," +
                    "inDate DATE NOT NULL," +
                    "expDate DATE NOT NULL," +
                    "inFridge BOOLEAN NOT NULL," +
                    "isbasic BOOLEAN NOT NULL," +
                    "autoDelete BOOLEAN NOT NULL" +
                    ")";
            stmt.execute(createFoodTableSQL);
            System.out.println("Food table created/verify.");

            // Create table shoppingList (gestion the food that the user wants to buy)
            String createShoppingListTableSQL = "CREATE TABLE IF NOT EXISTS shoppingList (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "foodId INT NOT NULL," +
                    "item_name TEXT NOT NULL," +
                    "quantity INT NOT NULL," +
                    "FOREIGN KEY (foodId) REFERENCES food(id)" +
                    ")";
            stmt.execute(createShoppingListTableSQL);
            System.out.println("Shopping List table created/verify.");
        } catch (SQLException e) {
            System.out.println("Error initializing the database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
