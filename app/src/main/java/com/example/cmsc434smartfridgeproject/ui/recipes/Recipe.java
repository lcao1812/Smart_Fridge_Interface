package com.example.cmsc434smartfridgeproject.ui.recipes;

public class Recipe {
    private String imageURL;
    private String title;

    public Recipe(String imageURL, String title){
        this.imageURL = imageURL;
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
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
