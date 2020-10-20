package com.example.cmsc434smartfridgeproject.ui.inventory;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InventoryFragment extends Fragment {

    private InventoryViewModel inventoryViewModel;
    ListView fridgelistView;
    ListView freezerlistView;
    ScrollView inventoryView;
    CardListAdapter arrayAdapter;
    public static int [] imgs={R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground,};
    List<FoodItem> list;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        inventoryViewModel =
                ViewModelProviders.of(this).get(InventoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_inventory, container, false);
        inventoryView = root.findViewById(R.id.inventory_scroll_view);
        list = new ArrayList<FoodItem>();
        try {
            list.add(new FoodItem("orange",2,"06 05, 2020",new HashSet(),"Bob"));
            list.add(new FoodItem("orange",2,"06 05, 2020",new HashSet(),"Bob"));
            list.add(new FoodItem("orange",2,"06 05, 2020",new HashSet(),"Bob"));
            list.add(new FoodItem("orange",2,"06 05, 2020",new HashSet(),"Bob"));
            list.add(new FoodItem("orange",2,"06 05, 2020",new HashSet(),"Bob"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        arrayAdapter = new CardListAdapter(getActivity().getApplicationContext(), R.layout.inventory_list_card, list, imgs);
        fridgelistView = root.findViewById(R.id._fridge_inventory_list);
        freezerlistView = root.findViewById(R.id._freezer_inventory_list);

        fridgelistView.setAdapter(arrayAdapter);
        freezerlistView.setAdapter(arrayAdapter);
//        inventoryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        fridgelistView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                inventoryView.requestDisallowInterceptTouchEvent(true);
                int action = event.getActionMasked();
                switch (action) {
                    case MotionEvent.ACTION_UP:
                        inventoryView.requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
//        fridgelistView.setOnTouchListener(new ListView.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int action = event.getAction();
//                switch (action) {
//                    case MotionEvent.ACTION_DOWN:
//                        // Disallow ScrollView to intercept touch events.
//                        v.getParent().requestDisallowInterceptTouchEvent(true);
//                        break;
//
//                    case MotionEvent.ACTION_UP:
//                        // Allow ScrollView to intercept touch events.
//                        v.getParent().requestDisallowInterceptTouchEvent(false);
//                        break;
//                }
//
//                // Handle ListView touch events.
//                v.onTouchEvent(event);
//                return true;
//            }
//        });
        freezerlistView.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });
        return root;
    }
}