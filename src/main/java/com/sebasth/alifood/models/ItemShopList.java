package com.sebasth.alifood.models;

public class ItemShopList {
    private int id_Item;
    private String nameItem;
    private String quantity;
    private String status;

    // Create a complete constructor
    public ItemShopList(int id_Item, String nameItem, String quantity, String status) {
        this.id_Item = id_Item;
        this.nameItem = nameItem;
        this.quantity = quantity;
        this.status = status;
    }

    // Create new constructor without id_Item
    public ItemShopList(String nameItem, String quantity) {
        this(-1, nameItem, quantity, "pending");
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Override toString method for better representation

    @Override
    public String toString() {
        return nameItem + (quantity != null && !quantity.isEmpty() ? "(" + quantity + " )" : "");
    }
}
