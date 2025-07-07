package com.sebasth.alifood.database;

import  com.sebasth.alifood.models.ItemShopList;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShopListDAO {

    /*
    Here we define the methods to manage the shopping list on future.
     */

    // Methods to add items to the shopping list
    public void addItemToShoppingList(ItemShopList itemShopList) throws SQLException {
        String sql = "INSERT INTO shopping_List (item_name, quantity) VALUES (?.?)";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            pstmt.setString(1, itemShopList.getNameItem());
            pstmt.setString(2, itemShopList.getQuantity());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0){
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()){
                    if (generatedKeys.next()){
                        itemShopList.setId_Item(generatedKeys.getInt(1));
                    }
                }
            }
        }
    }

    // Method to get items from the shopping list
    public List<ItemShopList> getItems() throws SQLException{
        List<ItemShopList> items = new ArrayList<>();
        String sql = "SELECT item_name FROM shopping_List";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()){
                items.add(new ItemShopList(
                        rs.getInt("Id_Item"),
                        rs.getString("itemName"),
                        rs.getString("quantity"),
                        rs.getString("status"
                )));
            }
        }
        return items;
    }

    // Method to delete an item from the shopping list
    public void deleteItemFromShoppingList(int id) throws SQLException   {
        String sql = "DELETE FROM shopping_List WHERE Id_Item = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
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
    public void markItemAsBought(int id) throws SQLException {
        String sql = "UPDATE shopping_List SET status = 'bought' WHERE id_Item = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    // Method to update an item in the shopping list
    public void updateItemSopList(ItemShopList itemShopList) throws SQLException{
        String sql = "UPDATE shopping_List SET item_name = ?, quantity = ?, status = ? WHERE id_Item = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, itemShopList.getNameItem());
            pstmt.setString(2, itemShopList.getQuantity());
            pstmt.setString(3, itemShopList.getStatus());
            pstmt.setInt(4, itemShopList.getId_Item());
            pstmt.executeUpdate();
        }
    }

    /*
    The method cant be used because the operation is bettween two tables (shopping_List and food).

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
*/

}
