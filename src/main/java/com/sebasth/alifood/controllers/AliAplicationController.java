package com.sebasth.alifood.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AliAplicationController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}