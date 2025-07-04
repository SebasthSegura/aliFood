package com.sebasth.alifood.database;

import com.sebasth.alifood.models.Food;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FoodDAO {

    // Method to add food to the database
    public void addfood(Food food) throws SQLException{
        String sql = "INSERT INTO food (name, inDate, expDate, inFridge, isbasic, autoDelete) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, food.getName());
            pstmt.setDate(2, Date.valueOf(food.getInDate()));
            pstmt.setDate(3, food.getExpDate() != null ? Date.valueOf(food.getExpDate()) : null);
            pstmt.setBoolean(4, food.isInFridge());
            pstmt.setBoolean(5, food.isIsbasic());
            pstmt.setBoolean(6, food.isAutoDelete());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        food.setId(generatedKeys.getInt(1));
                    }
                }
            }
        }
    }

    // Method to get food by ID
    public Food getFoodId(int id) throws SQLException {
        String sql = "SELECT * FROM food WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()){
                if (rs.next()){
                    return new Food(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDate("inDate").toLocalDate(),
                            rs.getDate("expDate") != null ? rs.getDate("expDate").toLocalDate() : null,
                            rs.getBoolean("inFridge"),
                            rs.getBoolean("isbasic"),
                            rs.getBoolean("autoDelete")
                    );
                }
            }
        }
        return null;
    }

    // Method to get all basic food items
    public List<Food> getBasicFood() throws SQLException{
        List<Food> basicFood = new ArrayList<>();
        String sql = "SELECT * FROM food WHERE isbasic = TRUE ADN autoDelete = FALSE"; // Exclude auto-deleted items
        try (Connection conn = DatabaseConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()){
                basicFood.add(new Food(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDate("inDate").toLocalDate(),
                        rs.getDate("expDate") != null ? rs.getDate("expDate").toLocalDate() : null,
                        rs.getBoolean("inFridge"),
                        rs.getBoolean("isbasic"),
                        rs.getBoolean("autoDelete")
                ));
            }
        }
        return basicFood;
    }

    // Method to get all food items
    public List<Food> getAllFood() throws SQLException{
        List<Food> food = new ArrayList<>();
        String sql = "SELECT * FROM food WHERE autoDelete = FALSE"; // Exclude auto-deleted items
        try (Connection conn = DatabaseConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()){
                food.add(new Food(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDate("inDate").toLocalDate(),
                        rs.getDate("expDate") != null ? rs.getDate("expDate").toLocalDate() : null,
                        rs.getBoolean("inFridge"),
                        rs.getBoolean("isbasic"),
                        rs.getBoolean("autoDelete")
                ));
            }
        }
        return food;
    }

    // Method to get more than week food in the fridge
    public List<Food> getMoreThanWeekInFridge() throws  SQLException{
       List<Food> food = new ArrayList<>();
       String sql = "SELECT * FROM food WHERE inFridge = TRUE AND inDate <= ? AND autoDelete = FALSE"; // Exclude auto-deleted items
        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            LocalDate oneWeekAgo = LocalDate.now().minusWeeks(1 );
            pstmt.setDate(1 , Date.valueOf(oneWeekAgo));
            try(ResultSet rs = pstmt.executeQuery()){
                while (rs.next()){
                    food.add(new Food(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDate("inDate").toLocalDate(),
                            rs.getDate("expDate") != null ? rs.getDate("expDate").toLocalDate() : null,
                            rs.getBoolean("inFridge"),
                            rs.getBoolean("isbasic"),
                            rs.getBoolean("autoDelete")
                    ));
                }
            }
        }
        return food;
    }

    // Method to get food early expired
    public List<Food> getEarlyExpiredfood(int thresholdDays) throws SQLException{
        List<Food> food = new ArrayList<>();
        String sql = "SELECT * FROM food WHERE expDate IS NOT NULL AND expDate <= ? AND autoDeolete = FALSE"; // Exclude auto-deleted items
        try (Connection conn  = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)){
            LocalDate thresholdDate = LocalDate.now().plusDays(thresholdDays);
            pstmt.setDate(1, Date.valueOf(thresholdDate));
            try (ResultSet rs = pstmt.executeQuery()){
                while (rs.next()){
                    food.add(new Food(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDate("inDate").toLocalDate(),
                            rs.getDate("expDate") != null ? rs.getDate("expDate").toLocalDate() : null,
                            rs.getBoolean("inFridge"),
                            rs.getBoolean("isbasic"),
                            rs.getBoolean("autoDelete")
                    ));
                }
            }
        }
        return food;
    }

    // Method to update food
    public void updateFood(Food food) throws  SQLException{
        String sql = "UPDATE food SET name = ?, inDate = ?, expDate = ?, inFridge = ?, isbasic = ?, autoDelete = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, food.getName());
            pstmt.setDate(2, Date.valueOf(food.getInDate()));
            pstmt.setDate(3, food.getExpDate() != null ? Date.valueOf(food.getExpDate()) : null);
            pstmt.setBoolean(4, food.isInFridge());
            pstmt.setBoolean(5, food.isIsbasic());
            pstmt.setBoolean(6, food.isAutoDelete());
            pstmt.setInt(7, food.getId());
            pstmt.executeUpdate();
        }
    }

    // Method to delete food
    public void deleteFood(int id) throws SQLException {
        String sql = "DELETE FROM food WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    // Method to mark food as auto-delete
    public  List<Food> autoDeleteFoodExpired(int discardDays) throws SQLException {
        List<Food> delete = new ArrayList<>();
        String sqlSelect = "SELECT * FROM food WHERE inDate <= ? AND autoDelete = FALSE";
        String sqlUpdate = "UPDATE food SET autoDelete = TRUE WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect);
        PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate);) {

            LocalDate limtDate = LocalDate.now().minusDays(discardDays);
            pstmtSelect.setDate(1, Date.valueOf(limtDate));

            try (ResultSet rs = pstmtSelect.executeQuery()){
                while (rs.next()){
                    Food food = new Food(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDate("inDate").toLocalDate(),
                            rs.getDate("expDate") != null ? rs.getDate("expDate").toLocalDate() : null,
                            rs.getBoolean("inFridge"),
                            rs.getBoolean("isbasic"),
                            rs.getBoolean("autoDelete")
                    );
                    delete.add(food);

                    // update food to auto-delete
                    pstmtUpdate.setInt(1, food.getId());
                    pstmtUpdate.executeUpdate();
                }
            }
            return delete;
        }

    }
}
