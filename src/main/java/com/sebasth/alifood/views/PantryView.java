package com.sebasth.alifood.views;

/*
import's package self class
*/
import com.sebasth.alifood.controllers.PantryController;
import com.sebasth.alifood.models.Food;
import com.sebasth.alifood.utils.DateUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/*
import's sql classes
*/
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PantryView {

    @FXML
    private TableView<Food> foodTableView;

    @FXML
    private TableColumn<Food, String> nameColumn;

    @FXML
    private TableColumn<Food, LocalDate> incColumn;

    @FXML
    private TableColumn<Food, LocalDate> expColumn;

    @FXML
    private TableColumn<Food, Boolean> inFridgeColumn;

    @FXML
    private TableColumn<Food, Boolean> isbasicColumn;

    @FXML
    private TableColumn<Food, String> restDaysColumn; // is a calculated column

    @FXML
    private TextField txtFoodName;

    @FXML
    private DatePicker dateInc;

    @FXML
    private DatePicker dateExp;

    @FXML
    private CheckBox chkInFridge;

    @FXML
    private CheckBox chkIsBasic;

    @FXML
    private Label masageAlert;

    private ObservableList<Food> foodList;
    private PantryController pantryController;

    // Create Alert dialog constants
    private static final int EXPIRY_THRESHOLD_ALERT = 5; // alert if food expires after 5 days on 7th day
    private static final int THRESHOLD_EXPIRED_DAYS_ELIMINATION = 9; // eliminate food after 9 days expired

    // Create a constructor to FXML loader
    public PantryView(){
    }

    // Method to inject the PantryController
    public void setPantryController(PantryController pantryController) {
        this.pantryController = pantryController;
        inicializeTableView();
        chargeFoodTableView();
        verifyAlertExpired();
    }

    // Method to initialize
    @FXML
    public void initialize(){
        dateInc.setValue(LocalDate.now());
    }

    // Method to charge the food table view
    public void inicializeTableView(){
        // Initialize the columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        incColumn.setCellValueFactory(new PropertyValueFactory<>("dateInc"));
        expColumn.setCellValueFactory(new PropertyValueFactory<>("dateExp"));
        inFridgeColumn.setCellValueFactory(new PropertyValueFactory<>("inFridge"));
        isbasicColumn.setCellValueFactory(new PropertyValueFactory<>("isBasic"));

        // Configure the restDaysColumn to calculate the remaining days
        restDaysColumn.setCellValueFactory(cellData -> {
            Food food = cellData.getValue();
            if (food.getExpDate() != null){
                long daysLeft = DateUtils.getDaysBetween(LocalDate.now(), food.getExpDate());
                if (daysLeft < 0){
                    return new javafx.beans.property.SimpleStringProperty("Caducado hace " + Math.abs(daysLeft) + " días");
                } else if (daysLeft <= EXPIRY_THRESHOLD_ALERT) {
                    return new javafx.beans.property.SimpleStringProperty("Quedan " + daysLeft + " días");
                } else {
                    return new javafx.beans.property.SimpleStringProperty(daysLeft + " dias");
                }
            }
            return new javafx.beans.property.SimpleStringProperty("Fecha no disponible");
        });

        // Apply aditional styles to the column
        restDaysColumn.setCellFactory(column -> new TableCell<Food, String>(){
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
                setStyle(""); // Reset style

                if (item != null && !empty) {
                    Food food = getTableView().getItems().get(getIndex());
                    if (food.getExpDate() != null){
                        long daysLeft = DateUtils.getDaysBetween(LocalDate.now(), food.getExpDate());
                        if (daysLeft < 0){
                            setTextFill(Color.DARKOLIVEGREEN);
                            setStyle("-fx-font-weight: bold;");
                        } else if (daysLeft <= EXPIRY_THRESHOLD_ALERT) {
                            setTextFill(Color.PURPLE);
                            setStyle("-fx-font-weight: bold;");

                        }
                    }
                }
            }
        });

        foodList = FXCollections.observableArrayList();
        foodTableView.setItems(foodList);

        // Listener to select a food item and precharge the edit fields
        foodTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showFoodDetails(newValue));
    }

    // Method to charge the food table view with data from the database
    private void chargeFoodTableView(){
        try {
            List<Food> food = pantryController.getAllFood();
            foodList.setAll(food);
        } catch (SQLException e) {
            showErrorDialog("Error al cargar los alimentos", "No se pudieron cargar los alimentos desde la base de datos.");
            e.printStackTrace();
        }
    }

    // Method to show food details
    private void showFoodDetails(Food food){
        if (food != null){
            txtFoodName.setText(food.getName());
            dateInc.setValue(food.getInDate());
            dateExp.setValue(food.getExpDate());
            chkInFridge.setSelected(food.isInFridge());
            chkIsBasic.setSelected(food.isIsbasic());
        } else {
            clearFields();
        }
    }

    // Method's to handle the add, update and delete food actions
    @FXML
    private void handleAddFood(){
        if (validateFields()){
            try {
                String name = txtFoodName.getText();
                LocalDate incDate = dateInc.getValue();
                LocalDate expDate = dateExp.getValue();
                boolean inFridge = chkInFridge.isSelected();
                boolean isBasic = chkIsBasic.isSelected();

                pantryController.addFood(name, incDate, expDate, inFridge, isBasic);
                clearFields();
                chargeFoodTableView();
                masageAlert.setTextFill(Color.GREEN);
                masageAlert.setText("Alimento agregado correctamente.");
            } catch (SQLException e){
                showErrorDialog("Error al agregar el alimento", "No se pudo agregar el alimento a la base de datos." + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleUpdateFood(){
        Food selectedFood = foodTableView.getSelectionModel().getSelectedItem();
        if (selectedFood != null && validateFields()) {
        try {
            selectedFood.setName(txtFoodName.getText());
            selectedFood.setInDate(dateInc.getValue());
            selectedFood.setExpDate(dateExp.getValue());
            selectedFood.setInFridge(chkInFridge.isSelected());
            selectedFood.setIsbasic(chkIsBasic.isSelected());

            pantryController.updateFood(selectedFood);
            clearFields();
            chargeFoodTableView();
            masageAlert.setTextFill(Color.GREEN);
            masageAlert.setText("Alimento actualizado correctamente.");
        } catch (SQLException e){
            showErrorDialog("Error al actualizar el alimento", "No se pudo actualizar el alimento en la base de datos." + e.getMessage());
            e.printStackTrace();
        }

        } else if (selectedFood == null) {
            masageAlert.setTextFill(Color.RED);
            masageAlert.setText("Selecione un alimento para actualizar.");
        }
    }

    @FXML
    private void handleDeleteFood(){
        Food selectedFood = foodTableView.getSelectionModel().getSelectedItem();
        if (selectedFood != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminacion");
            alert.setHeaderText(null);
            alert.setContentText("¿Está seguro de que desea eliminar el alimento seleccionado: " + selectedFood.getName() + "?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    pantryController.deleteFood(selectedFood.getId());
                    clearFields();
                    chargeFoodTableView();
                    masageAlert.setTextFill(Color.RED);
                    masageAlert.setText("Alimento eliminado correctamente.");
                } catch (SQLException e) {
                    showErrorDialog("Error al eliminar el alimento", "No se pudo eliminar el alimento de la base de datos." + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                masageAlert.setTextFill(Color.RED);
                masageAlert.setText("Seleccione un alimento para eliminar.");
            }
        }
    }

    // Method to validate the fields before adding or updating food
    private boolean validateFields() {
        String masageError = "";
        if (txtFoodName.getText() == null || txtFoodName.getText().isEmpty()){
            masageError += "El nombre del alimento no puede estar vacío.\n";
        }
        if (dateInc.getValue() == null){
            masageError += "La fecha de ingreso no puede estar vacía.\n";
        }
        if (dateExp.getValue() != null && dateExp.getValue().isBefore(dateInc.getValue())){
            masageError += "La fecha de caducidad no puede ser menor a la fecha de ingreso.\n";
        }
        if(masageError.isEmpty()){
            return  true;
        } else {
            masageAlert.setTextFill(Color.PURPLE);
            masageAlert.setText(masageError);
            return false;
        }
    }

    // Method to clear the input fields
    private void clearFields() {
        txtFoodName.clear();
        dateInc.setValue(LocalDate.now());
        dateExp.setValue(null);
        chkInFridge.setSelected(false);
        chkIsBasic.setSelected(false);
        masageAlert.setText("");
        foodTableView.getSelectionModel().clearSelection();
    }

    // Method to show an error dialog
    private void showErrorDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Method to verify alerts for expired food and food in the fridge
    private void verifyAlertExpired(){
        try {

            // Delete expired food automatically
            List<Food> deletedFoods = pantryController.ejectAutoDeleteExpiredFood();
            if (!deletedFoods.isEmpty()){
                StringBuilder sb = new StringBuilder("Se han eliminado los siguientes alimentos: por tener mas de " + THRESHOLD_EXPIRED_DAYS_ELIMINATION + " dias en despensa:\n");
                deletedFoods.forEach(a -> sb.append("- ").append(a.getName()).append("\n"));
                showAlertInformation("Alimentos eliminados", sb.toString());
                chargeFoodTableView();
            }

            // Check for food that will expire soon
            List<Food> expiringSoon = pantryController.getExpiringSoonFood(EXPIRY_THRESHOLD_ALERT);
            if (!expiringSoon.isEmpty()) {
                StringBuilder sb = new StringBuilder("Los siguientes alimentos están por caducar en menos de " + EXPIRY_THRESHOLD_ALERT + " días:\n");
                expiringSoon.forEach(a -> sb.append("- ").append(a.getName()).append(" (Caduca el ").append(a.getExpDate()).append(")\n"));
                showAlertInformation("Alimentos por caducar", sb.toString());
            }

            // Food in the refrigerator for more than a week
            List<Food> inFridgeForWeek = pantryController.verifyFoodInFridgeForWeek();
            if (!inFridgeForWeek.isEmpty()){
                StringBuilder sb = new StringBuilder("Los siguientes alimentos están en la nevera por más de una semana:\n");
                inFridgeForWeek.forEach(a -> sb.append("- ").append(a.getName()).append(" (Ingresado el ").append(a.getInDate()).append(")\n"));
                showAlertInformation("Alimentos en la nevera por más de una semana", sb.toString());
            }
        } catch (SQLException e){
            showErrorDialog("Error al verificar alimentos", "No se pudo verificar los alimentos en la base de datos." + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to show an information alert
    private void showAlertInformation(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
