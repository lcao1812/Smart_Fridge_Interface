package com.example.cmsc434smartfridgeproject.ui.cart;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cmsc434smartfridgeproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CartViewModel extends ViewModel {
    // array list of strings to store item name. This is the first step, it will become
    // list of item objects later
    private ArrayList<String> itemList = new ArrayList<>();
    private MutableLiveData<String> mText;

    public CartViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is cart fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
