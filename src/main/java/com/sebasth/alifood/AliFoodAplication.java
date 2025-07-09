package com.sebasth.alifood;

import com.sebasth.alifood.controllers.LoginController;
import com.sebasth.alifood.controllers.RegisterController;
import com.sebasth.alifood.views.LoginView;
import com.sebasth.alifood.views.RegisterView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AliFoodAplication extends Application {

    private Stage primmaryStage;

    @Override
    public void start(Stage primmaryStage){
        this.primmaryStage = primmaryStage;
        this.primmaryStage.setTitle("AliFood Application");
        showLoginView();
    }

    // Method to show the login view
    public static void showLoginView(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AliFoodAplication.class.getResource("/com/sebasth/alifood/login-view.fxml"));
            AnchorPane loginL = loader.load();

            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            loginStage.setScene(new Scene(loginL));
            LoginView loginView = loader.getController();
            loginView.setLoginController(new LoginController());
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to show the login view
    public void showRegisterView(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/sebasth/alifood/register-view.fxml"));
            AnchorPane registerV = loader.load();

            Stage registerStage = new Stage();
            registerStage.setTitle("Registro");
            registerStage.setScene(new Scene(registerV));
            RegisterView registerView = loader.getController();

            registerView.setRegisterController(new RegisterController());
            registerStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}