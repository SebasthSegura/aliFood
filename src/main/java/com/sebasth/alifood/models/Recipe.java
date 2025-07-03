package com.sebasth.alifood.models;

import java.util.List;

public class Recipe {
    private String title;
    private String url;
    private String imageUrl;
    private List<String> ingredients;
    private List<String> aviableIngredients;
    private String foodType;

    // create a complete constructor
    public Recipe(String title, String url, String imageUrl, List<String> ingredients, List<String> aviableIngredients, String foodType) {
        this.title = title;
        this.url = url;
        this.imageUrl = imageUrl;
        this.ingredients = ingredients;
        this.aviableIngredients = aviableIngredients;
        this.foodType = foodType;
    }

    // getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getAviableIngredients() {
        return aviableIngredients;
    }

    public void setAviableIngredients(List<String> aviableIngredients) {
        this.aviableIngredients = aviableIngredients;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    @Override
    public String toString() {
        return "Receta{"+
                "Titulo='" + title + '\'' +
                ", url='" + url + '\'' +
                ", ingredientes=" + ingredients +
                ", Tipo de comida='" + foodType + '\'' +
                '}';
    }
}
