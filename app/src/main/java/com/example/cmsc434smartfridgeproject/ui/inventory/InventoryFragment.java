package com.example.cmsc434smartfridgeproject.ui.inventory;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.cmsc434smartfridgeproject.R;

import java.util.ArrayList;
import java.util.List;

public class InventoryFragment extends Fragment {

    private InventoryViewModel inventoryViewModel;
    ListView fridgelistView;
    ListView freezerlistView;
    ArrayAdapter arrayAdapter;
    List list;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        inventoryViewModel =
                ViewModelProviders.of(this).get(InventoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_inventory, container, false);
        fridgelistView = root.findViewById(R.id._fridge_inventory_list);
        fridgelistView.setOnTouchListener(new ListView.OnTouchListener() {
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
        freezerlistView = root.findViewById(R.id._freezer_inventory_list);
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
        list = new ArrayList();
        list.add("orange");
        list.add("orange");
        list.add("orange");
        list.add("orange");
        list.add("orange");
        list.add("orange");
        list.add("orange");
        list.add("orange");
        list.add("orange");
        list.add("orange");
        list.add("orange");
        list.add("orange");
        list.add("orange");
        list.add("orange");
        list.add("orange");
        list.add("orange");
        list.add("orange");
        list.add("orange");
        list.add("orange");
        list.add("orange");
        list.add("orange");
        list.add("orange");

        arrayAdapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, list);
        fridgelistView.setAdapter(arrayAdapter);
        freezerlistView.setAdapter(arrayAdapter);
//        inventoryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}