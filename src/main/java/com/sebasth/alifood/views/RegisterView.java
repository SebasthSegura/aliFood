package com.sebasth.alifood.views;

import com.sebasth.alifood.AliFoodAplication;
import com.sebasth.alifood.controllers.LoginController;
import com.sebasth.alifood.controllers.RegisterController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class RegisterView {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private Label messageLabel;

    private RegisterController registerController;
    private LoginController loginController;
    private Stage stage;
    private Runnable onLoginReturn;

    // Create a constructor to FXML
    public RegisterView(){
    }

    // Method to inject the controller and stage

    public void setRegisterController(RegisterController registerController, Stage stage, Runnable onLoginReturn) {
        this.registerController = registerController;
        this.stage = stage;
        this.onLoginReturn = onLoginReturn;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Initialize method to set up the view
    @FXML
    private void initialize() {
        // Initialize any necessary components or listeners here
        messageLabel.setText("");
    }

    // Method to handle the registration process
    @FXML
    private void handleRegister(){
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String confirmPass = confirmPassword.getText();

        // Validate input
        if (username.isEmpty() || password.isEmpty() || confirmPass.isEmpty()) {
            messageLabel.setText("Por favor complete todos los campos.");
            return;
        }

        if (!password.equals(confirmPass)) {
            messageLabel.setText("Las contraseñas no coinciden.");
            return;
        }

        try{
            if (registerController.registerUser(username, password)){
                messageLabel.setTextFill(Color.GREEN);
                messageLabel.setText("Registro Exitoso!");
                clearFields();
            } else {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("Error al registrar usuario. Intente de nuevo.");
            }
        } catch (SQLException e){
            if (e.getMessage().contains("Duplicate entry") && e.getMessage().contains("for key 'username'")){
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("El nombre de usuario ya existe. Por favor, elija otro.");
            } else {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("Error de base de datos: " + e.getMessage());
            }
        }
    }

    // Method to handle the return to login view
    @FXML
    private void handleReturnToLogin() {
        if (stage != null) {
            stage.close();
            if (onLoginReturn != null) {
                onLoginReturn.run();
            }
        }
    }

    // Method to clear the input fields
    private void clearFields() {
        txtUsername.clear();
        txtPassword.clear();
        confirmPassword.clear();
        messageLabel.setText("");
    }
}
