package com.sebasth.alifood.controllers;

import com.sebasth.alifood.AliFoodAplication;
import com.sebasth.alifood.database.UserDAO;
import com.sebasth.alifood.models.User;
import com.sebasth.alifood.utils.PasswordUtils;
import com.sebasth.alifood.views.LoginView;
import com.sebasth.alifood.views.PantryView;
import com.sebasth.alifood.views.RegisterView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
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

    // Method to load the LoginView
    public void loadLoginView(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sebasth/alifood/login-view.fxml"));
        Parent root = loader.load();
        LoginView loginView = loader.getController();
        loginView.setLoginController(new LoginController());

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("AliFood - Inicio de Sesión");
        stage.show();
    }
}
