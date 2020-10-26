package com.example.cmsc434smartfridgeproject.ui.inventory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.cmsc434smartfridgeproject.CardListAdapter;
import com.example.cmsc434smartfridgeproject.R;
import com.example.cmsc434smartfridgeproject.utils.FoodItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InventoryFragment extends Fragment {

    private InventoryViewModel inventoryViewModel;
    ListView fridgelistView;
    ScrollView inventoryView;
    CardListAdapter arrayAdapter;
    List<Integer> imgs;
    List<FoodItem> list;
    FloatingActionButton addItemButton;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        inventoryViewModel =
                ViewModelProviders.of(this).get(InventoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_inventory, container, false);
        list = new ArrayList<FoodItem>();
        imgs = new ArrayList<Integer>();

        try {
            for (int i = 0; i < 10 ; i++){
                imgs.add( R.drawable.ic_launcher_foreground);
                list.add(new FoodItem("orange",2,new Date(),new HashSet(),"Bob"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        arrayAdapter = new CardListAdapter(getActivity().getApplicationContext(), R.layout.inventory_list_card, list, imgs);
        fridgelistView = root.findViewById(R.id._fridge_inventory_list);
        fridgelistView.setAdapter(arrayAdapter);
        addItemButton = root.findViewById(R.id.inventory_add_button);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Function to add Item here
                addCartItem();
                // ****************************
                Snackbar.make(v, "Item Added To The Cart", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return root;
    }
    private void addCartItem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add New Item");

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.add_food_item_form, null, false);

        builder.setView(v);
        final EditText foodName = v.findViewById(R.id.addFoodName);
        final EditText foodAmount = v.findViewById(R.id.addFoodAmount);
        final EditText foodDate = v.findViewById(R.id.addFoodDate);
        final EditText foodAllergens = v.findViewById(R.id.addFoodAllergens);
        final EditText foodOwner= v.findViewById(R.id.addFoodOwner);
        final ImageButton todayButton = v.findViewById(R.id.addFoodDateTodayButton);
        todayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Function to add Item here
                Date today = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

                foodDate.setText(formatter.format(today));
            }
        });
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!foodName.getText().toString().isEmpty() && !foodAmount.getText().toString().isEmpty()
                && !foodDate.getText().toString().isEmpty() && !foodAllergens.getText().toString().isEmpty()
                && !foodOwner.getText().toString().isEmpty()) {
                    try {
                        list.add(
                            new FoodItem(
                                    foodName.getText().toString().trim(),
                                    Integer.parseInt(foodAmount.getText().toString().trim()),
                                    new SimpleDateFormat("MM/dd/yyyy").parse(foodDate.getText().toString().trim()),
                                    new HashSet(),
                                    foodOwner.getText().toString().trim())
                            );
                        imgs.add(R.drawable.ic_launcher_foreground);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    arrayAdapter.notifyDataSetChanged();

                } else {
                    foodName.setError("add item here !");
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

}