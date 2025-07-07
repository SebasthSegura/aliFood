package com.sebasth.alifood.models;

public class ItemShopList {
    private int id_Item;
    private String nameItem;
    private String quantity;

    // Create a complete constructor
    public ItemShopList(int id_Item, String nameItem, String quantity) {
        this.id_Item = id_Item;
        this.nameItem = nameItem;
        this.quantity = quantity;
    }

    // Create new constructor without id_Item
    public ItemShopList(String nameItem, String quantity) {
        this(-1, nameItem, quantity);
    }

    // Getters and Setters

    public int getId_Item() {
        return id_Item;
    }

    public void setId_Item(int id_Item) {
        this.id_Item = id_Item;
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    // Override toString method for better representation

    @Override
    public String toString() {
        return nameItem + (quantity != null && !quantity.isEmpty() ? "(" + quantity + " )" : "");
    }
}
