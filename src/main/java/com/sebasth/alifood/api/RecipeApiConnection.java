package com.sebasth.alifood.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebasth.alifood.models.Recipe;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class RecipeApiConnection {

    // API information
    private static final String API_URL = "https://api.spoonacular.com/recipes/complexSearch";
    private static final String API_KEY = "YOUR_API_KEY";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    // Create a constructor for the RecipeApiConnection class and initialize the HttpClient and ObjectMapper
    public RecipeApiConnection(){
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    // Method to search recipes by ingredients
    public List<Recipe> searchRecipes(List<String> ingredients){
        List<Recipe> recipes = new ArrayList<>();
        if (ingredients == null || ingredients.isEmpty()){
            return recipes;
        }

        try {
            // codify the ingredients into a single string to url
            String queryIngredients = ingredients.stream()
                    .map(s -> URLEncoder.encode(s, StandardCharsets.UTF_8))
                    .collect(Collectors.joining(","));

            // construct API URL with query parameters
            String url = API_URL + "/search?ingredients=" + queryIngredients + "&apiKey=" + API_KEY;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "application/json")
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200){
                JsonNode root = objectMapper.readTree(response.body());
                for (JsonNode node : root){
                    String title = node.has("title") ? node.get("title").asText() : "Sin título";
                    String recetaUrl = node.has("sourceUrl") ? node.get("sourceUrl").asText() : "";
                    String imageUrl = node.has("image") ? node.get("image").asText() : null;

                    // recipe ingredients
                    List<String> recipeIngredients = new ArrayList<>();
                    if (node.has("extendedIngredients") && node.get("extendedIngretdients").isArray()){
                        for (JsonNode ingredientNode : node.get("extendedIngredients")) {
                            if (ingredientNode.has("name")) {
                                recipeIngredients.add(ingredientNode.get("name").asText());
                            }
                        }
                    }

                    //calculate aviable ingredients the api has matched
                    List<String> ingtredientsMatched = new ArrayList<>(ingredients);
                    ingtredientsMatched.retainAll(recipeIngredients);

                    recipes.add(new Recipe(title, recetaUrl, imageUrl, recipeIngredients, ingtredientsMatched, "General"));
                }
            }
            else {
                System.out.println("Error: " + response.statusCode() + " - " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error al buscar recetas: " + e.getMessage());
            e.printStackTrace();
        }
        return recipes;
    }

    // Method to get recipe to breakfast, lunch or dinner
    public List<Recipe> getDailyRecipe(List<String> ingredients, String mealType) {
        List<Recipe> recipesDaily = searchRecipes(ingredients);
        if (ingredients == null || ingredients.isEmpty() || mealType == null || mealType.isEmpty()) {
            return recipesDaily;
        }

        try {
            // codify the ingredients into a single string to url
            String queryIngredients = ingredients.stream()
                    .map(s -> URLEncoder.encode(s, StandardCharsets.UTF_8))
                    .collect(Collectors.joining(","));

            // construct API URL with query parameters
            String url = API_URL + "/search?ingredients=" + queryIngredients + "&type=" + mealType + "&apiKey=" + API_KEY;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "application/json")
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                JsonNode root = objectMapper.readTree(response.body());
                for (JsonNode node : root) {
                    String title = node.has("title") ? node.get("title").asText() : "Sin título";
                    String recetaUrl = node.has("sourceUrl") ? node.get("sourceUrl").asText() : "";
                    String imageUrl = node.has("image") ? node.get("image").asText() : null;

                    // recipe ingredients
                    List<String> recipeIngredients = new ArrayList<>();
                    if (node.has("extendedIngredients") && node.get("extendedIngretdients").isArray()) {
                        for (JsonNode ingredientNode : node.get("extendedIngredients")) {
                            if (ingredientNode.has("name")) {
                                recipeIngredients.add(ingredientNode.get("name").asText());
                            }
                        }
                    }

                    //calculate aviable ingredients the api has matched
                    List<String> ingtredientsMatched = new ArrayList<>(ingredients);
                    ingtredientsMatched.retainAll(recipeIngredients);

                    recipesDaily.add(new Recipe(title, recetaUrl, imageUrl, recipeIngredients, ingtredientsMatched, mealType));
                }
            } else {
                System.out.println("Error: " + response.statusCode() + " - " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error al buscar recetas: " + e.getMessage());
            e.printStackTrace();
        }
        return recipesDaily.stream()
                .filter(r -> r.getFoodType().equalsIgnoreCase(mealType)|| r.getTitle().toLowerCase().contains(mealType.toLowerCase()))
                .collect(Collectors.toList());
    }
}
