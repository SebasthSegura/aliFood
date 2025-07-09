package com.sebasth.alifood.database;

import com.sebasth.alifood.models.User;
import java.sql.*;

public class UserDAO {

    // Method to register a new user in the database
    public void registerUser(User user) throws SQLException{
        String sql = "INSERT INTO User (username, passwordHash) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPasswordHash());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0){
                try (ResultSet generatedKeys = pstmt.executeQuery()){
                    if (generatedKeys.next()){
                        user.setId(generatedKeys.getInt(1));
                    }
                }
            }
        }
    }

    // Method to get a user by username
    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM User WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("passwordHash")
                    );
                }
            }
        }
        return null;
    }

    // Method to update a user's
    public User updateUser(User user) throws SQLException {
        String sql = "UPDATE User SET username = ?, passwordHash = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPasswordHash());
            pstmt.setInt(3, user.getId());
            pstmt.executeUpdate();
        }
        return user; // Return the updated user
    }

    // Method to delete a user by ID
    public void deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM User WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    // Method to check if a user exists by username
    public boolean userExists(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM User WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Return true if count is greater than 0
                }
            }
        }
        return false; // User does not exist
    }
}
