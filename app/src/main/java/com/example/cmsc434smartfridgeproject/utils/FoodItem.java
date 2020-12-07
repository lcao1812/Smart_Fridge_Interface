package com.example.cmsc434smartfridgeproject.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class FoodItem {
    public FoodItem(String name, int amount,  Date expdate, Set<String> allergens, String owner) throws ParseException {
        this.name = name;
        this.amount = amount;
        this.allergens = allergens;
        this.expDate = expdate;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }



    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) throws ParseException {
        this.expDate = new SimpleDateFormat("MM dd, yyyy").parse(expDate);
    }
    public Set<String> getAllergens() {
        return allergens;
    }

    public void setAllergens(Set allergens) {
        this.allergens = allergens;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    private String name;
    private int amount;
    private Date expDate;
    private Set allergens;
    private String owner;
}