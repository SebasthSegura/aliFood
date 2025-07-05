package com.sebasth.alifood.controllers;

import com.sebasth.alifood.models.Food;
import com.sebasth.alifood.database.FoodDAO;
import com.sebasth.alifood.utils.DateUtils;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PantryController {

    private FoodDAO foodDAO;

    private static final int THRESHOLD_EXPIRED_DAYS = 9;
    private static final int DAYS_INFRIDGE_ALERT = 7;

    // Constructor initializes the FoodDAO instance
    public PantryController() {
        this.foodDAO = new FoodDAO();
    }

    // Instance of FoodDAO to interact with the database
    public List<Food> getAllFood() throws SQLException {
        return foodDAO.getAllFood();
    }

    // Method to add food to the pantry
    public void addFood(String name, LocalDate incDate, LocalDate expDate, boolean inFridge, boolean isBasic) throws SQLException {
        Food newFood = new Food(name, incDate, expDate, inFridge, isBasic);
        foodDAO.addfood(newFood);
    }

    // Method to update food in the pantry
    public void updateFood(Food food) throws SQLException {
        foodDAO.updateFood(food);
    }

    // Method to Delete food from the pantry
    public void deleteFood(int idFood) throws SQLException {
        foodDAO.deleteFood(idFood);
    }

    // Method eject auto delete expired food
    public List<Food> ejectAutoDeleteExpiredFood() throws SQLException {
        return foodDAO.autoDeleteFoodExpired(THRESHOLD_EXPIRED_DAYS);
    }

    // Method to get food that is expiring soon
    public List<Food> getExpiringSoonFood(int thresholdDays) throws SQLException{
        return foodDAO.getEarlyExpiredfood(thresholdDays);
    }

    // Method to verify food in fridge that has been there for more than a week
    public List<Food> verifyFoodInFridgeForWeek() throws SQLException{
        return foodDAO.getMoreThanWeekInFridge();
    }

    // Method to get name available food in the pantry
    public List<String> getNameAviableFood() throws SQLException{
        return foodDAO.getAllFood().stream()
                .map(Food::getName)
                .collect(Collectors.toList());
    }
}
