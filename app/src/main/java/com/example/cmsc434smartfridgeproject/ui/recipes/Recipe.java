package com.example.cmsc434smartfridgeproject.ui.recipes;

import java.util.ArrayList;

public class Recipe {
    private String imageURL;
    private String title;
    private String[] ingredients;
    private String instructions;

    public Recipe(String imageURL, String title, String[] ingredients, String instructions){
        this.imageURL = imageURL;
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
