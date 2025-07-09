package com.sebasth.alifood.controllers;

import com.sebasth.alifood.database.UserDAO;
import com.sebasth.alifood.views.PantryView;
import com.sebasth.alifood.models.User;
import com.sebasth.alifood.utils.PasswordUtils;
import com.sebasth.alifood.views.RegisterView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    private UserDAO userDAO;

    // Method to log user
    public LoginController(){
        this.userDAO = new UserDAO();
    }

    // Method to authenticate user
    public boolean authenticateUser(String username, String password) {
        try{
            User user = userDAO.getUserByUsername(username);
            if (user != null && PasswordUtils.verifyPassword(password, user.getPasswordHash())){
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error authenticating user: " + e.getMessage());
            e.printStackTrace();
        }
    return false;
    }

    // Method to register a new user with logic to check if user already exists
    public boolean registeNewUser(String username, String password){
        try {
            // verify if user already exists
            if (userDAO.getUserByUsername(username) != null){
                System.out.println("User already exists.");
                return false; // User already exists
            }
            // Hash the password
            String hashedPassword = PasswordUtils.hashPassword(password);
            // Create a new user object
            User newUser = new User(username, hashedPassword);
            // Save the new user to the database
            userDAO.registerUser(newUser);
            System.out.println("User registered successfully." + username);
            return true; // User registered successfully
        } catch (Exception e) {
            System.out.println("Error database registering user: " + e.getMessage());
            e.printStackTrace();
        }
        return false; // Error registering user
    }

    // Method to load the pantry application view
    public void showPantryView(Stage stage) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sebasth/alifood/views/pantry-view.fxml"));
        Parent root = loader.load();
        PantryView pantryView = loader.getController();
        pantryView.setPantryController(new PantryController());

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("AliFood - Despensa");
        stage.show();
    }

    // Method to load the register view
    public void loadRegisterView(Stage stage) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sebasth/alifood/register-view.fxml"));
        Parent root = loader.load();
        RegisterView registerView = loader.getController();
        registerView.setLoginController(this);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("AliFood - Registro");
        stage.show();

    }


}
