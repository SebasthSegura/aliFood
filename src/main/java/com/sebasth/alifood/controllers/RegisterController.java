package com.sebasth.alifood.controllers;

import com.sebasth.alifood.database.UserDAO;
import com.sebasth.alifood.models.User;
import com.sebasth.alifood.utils.PasswordUtils;

import java.sql.SQLException;

public class RegisterController {
    private UserDAO userDAO;
    private User newUser;

    // Constructor to inject UserDAO dependency
    public RegisterController() {
        this.userDAO = new UserDAO();
    }

    // Method to register a new user
    public boolean registerUser(String username, String password) throws SQLException {
        String hashedPassword = PasswordUtils.hashPassword(password);
        // Check if the user already exists
        if (userDAO.userExists(username)) {
            System.out.println("Usuario ya existe: " + username);
            return false; // User already exists
        }
        newUser = new User(username, hashedPassword);
        userDAO.registerUser(newUser);
        return true; // Registration successful
    }
}
