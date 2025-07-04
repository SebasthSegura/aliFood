package com.sebasth.alifood.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShopListDAO {

    /*
    Here we define the methods to manage the shopping list on future.
     */

    // Methods to add items to the shopping list
    public void addItemToShoppingList(String itemName) throws SQLException {
        String sql = "INSERT INTO shopping_List (item_name) VALUES (?)";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, itemName);
            pstmt.executeUpdate();
        }
    }

    // Method to get items from the shopping list
    public List<String> getItems() throws SQLException{
        List<String> items = new ArrayList<>();
        String sql = "SELECT item_name FROM shopping_List";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()){
                items.add(rs.getString("item_name"));
            }
        }
        return items;
    }

    // Method to delete an item from the shopping list
    public void deleteItemFromShoppingList(String itemName) throws SQLException {
        String sql = "DELETE FROM shopping_List WHERE item_name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, itemName);
            pstmt.executeUpdate();
        }
    }

    // Method to clear the shopping list
    public void clearShoppingList() throws SQLException {
        String sql = "DELETE FROM shopping_List";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    // Method to mark buying items as done on status
    public void markItemAsBought(String itemName) throws SQLException {
        String sql = "UPDATE shopping_List SET status = 'bought' WHERE item_name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, itemName);
            pstmt.executeUpdate();
        }
    }

    // Method to move items from the shopping list to the food list if they are bought
    public void moveItemToFoodList(String itemName) throws SQLException {
        String sql = "INSERT INTO food (name) SELECT item_name FROM shopping_List WHERE status = 'bought' AND item_name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, itemName);
            pstmt.executeUpdate();
        }
        deleteItemFromShoppingList(itemName); // Remove the item from the shopping list after moving it
    }


}
