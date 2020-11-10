package com.example.cmsc434smartfridgeproject.ui.recipes;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecipesViewModel extends ViewModel {
    private ArrayList<String> recipeList = new ArrayList<>();
    private MutableLiveData<String> mText;

    public RecipesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is a recipe fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
