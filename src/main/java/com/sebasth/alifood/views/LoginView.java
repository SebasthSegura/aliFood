package com.sebasth.alifood.views;

import com.sebasth.alifood.controllers.LoginController;
import com.sebasth.alifood.controllers.RegisterController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginView {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    private LoginController loginController;
    private RegisterController registerController;

    // Initialize the controller
    @FXML
    public void setLoginController(LoginController loginController){
        this.loginController = loginController;
    }

    @FXML
    public void setRegisterController(RegisterController registerController){
        this.registerController = registerController;
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event){
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (loginController.authenticateUser(username, password)){
            messageLabel.setText("Inicio de sesión exitoso.");
            try {
                loginController.showPantryView((Stage) usernameField.getScene().getWindow());
            } catch (IOException e) {
                messageLabel.setText("Error al cargar la vista de despensa: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            messageLabel.setText("Nombre de usuario o contraseña incorrectos.");
        }
    }

    @FXML
    private void handleRegisterButtonAction(ActionEvent event){
        try {
            loginController.loadRegisterView((Stage) usernameField.getScene().getWindow());
        } catch (IOException e) {
            messageLabel.setText("Error al cargar la vista de registro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
