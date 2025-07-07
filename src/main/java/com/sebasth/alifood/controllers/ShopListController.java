package com.sebasth.alifood.controllers;

import com.sebasth.alifood.database.FoodDAO;
import com.sebasth.alifood.database.ShopListDAO;
import com.sebasth.alifood.models.ItemShopList;

import java.sql.SQLException;
import java.util.List;

public class ShopListController {
    private FoodDAO foodDAO;
    private ShopListDAO shopListDAO;

    // Method to add food to the shopping list on the database
    public ShopListController() {
        this.foodDAO = new FoodDAO();
    }

    // Method to add item to the shopping list
    public void addItemToList(String nameItem, String quantity) throws SQLException{
        ItemShopList newItem = new ItemShopList(nameItem, quantity);
        shopListDAO.addItemToShoppingList(newItem);
    }

    // Method to get all items from the shopping list
    public List<ItemShopList> getAllItems() throws SQLException{
        return shopListDAO.getItems();
    }

    // Method to update items from the shopping list
    public void updateItemInList(ItemShopList item) throws SQLException{
        shopListDAO.updateItemSopList(item);
    }

    // Method to delete an item from the shopping list
    public void deleteItemFromList(int id) throws SQLException {
        shopListDAO.deleteItemFromShoppingList(id);
    }

    // Method to clear the shopping list
    public void clearShoppingList() throws SQLException {
        shopListDAO.clearShoppingList();
    }

    // Method to mark item like bought and add to pantry
    public void markItemAsBought(ItemShopList itemShopList, boolean inFridge, boolean isBasic) throws SQLException{
        shopListDAO.markItemAsBought(itemShopList.getId_Item());
        // add food to food on the database
        foodDAO.addFoodFromShopList(itemShopList.getNameItem(), inFridge, isBasic);
        // delete item from the shopping list
        shopListDAO.deleteItemFromShoppingList(itemShopList.getId_Item());
    }

}
