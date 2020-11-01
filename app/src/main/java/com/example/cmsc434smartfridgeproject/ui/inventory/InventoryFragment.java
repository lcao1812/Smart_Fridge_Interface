package com.example.cmsc434smartfridgeproject.ui.inventory;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.cmsc434smartfridgeproject.CardListAdapter;
import com.example.cmsc434smartfridgeproject.R;
import com.example.cmsc434smartfridgeproject.utils.FoodItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InventoryFragment extends Fragment {

    private InventoryViewModel inventoryViewModel;
    ListView fridgelistView;
    CardListAdapter arrayAdapter;
    List<Integer> imgs;
    List<FoodItem> list;
    FloatingActionButton addItemButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        inventoryViewModel =
                ViewModelProviders.of(this).get(InventoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_inventory, container, false);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

//        onCreateOptionsMenu(R.menu.action_bar,actionBar.)
//        actionBar.setIcon(R.drawable.);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
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

        arrayAdapter = new CardListAdapter(getActivity().getApplicationContext(), getActivity(), R.layout.inventory_list_card, list, imgs);
        fridgelistView = root.findViewById(R.id._fridge_inventory_list);
        fridgelistView.setAdapter(arrayAdapter);
        addItemButton = root.findViewById(R.id.inventory_add_button);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCartItem();
                Snackbar.make(v, "Item Added To The Cart", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return root;
    }
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.add_menu, menu);
//        return true;
//    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu items for use in the action bar
        inflater.inflate(R.menu.action_bar, menu);
//        MenuItem someMenuItem = menu.findItem(R.id.action_favorite);
//        someMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem m) {
//                return true;
//            }
//        });

//        mRefreshMenuItem = menu.findItem(R.id.refresh);
//        mRefreshMenuItem.setVisible(false);
//        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.inventory_search_button:
                searchItem();
                break;
            case R.id.sort_setting:
                sortItems();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
    private void sortItems() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Search for a item");

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.sort_by_form, null, false);

        builder.setView(v);
        final RadioButton name = v.findViewById(R.id.sort_by_form_name);
        final RadioButton date = v.findViewById(R.id.sort_by_form_date);

        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Map<FoodItem,Integer> m = new HashMap<FoodItem,Integer>();
                for(int i =0; i < list.size(); i++){
                    m.put(list.get(i), imgs.get(i));
                }
                if(name.isChecked()){
                    Collections.sort(
                            list,
                            new Comparator<FoodItem>() {
                                @Override
                                public int compare(FoodItem f1, FoodItem f2) {
                                    return f1.getName().compareTo(f2.getName());
                                }
                            });
                    ArrayList <Integer> newImgList = new <Integer>ArrayList();
                    for(int i =0; i < list.size(); i++){
                        newImgList.add(m.get(list.get(i)));
                    }
                    imgs= newImgList;

                }
                if(date.isChecked()){
                    Collections.sort(
                            list,
                            new Comparator<FoodItem>() {
                                @Override
                                public int compare(FoodItem f1, FoodItem f2) {
                                    return f1.getBuyDate().compareTo(f2.getBuyDate());
                                }
                            });
                    ArrayList <Integer> newImgList = new <Integer>ArrayList();
                    for(int i =0; i < list.size(); i++){
                        newImgList.add(m.get(list.get(i)));
                    }
                    imgs= newImgList;
                }
                arrayAdapter.notifyDataSetChanged();
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
    private void searchItem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Search for a item");

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.item_dialog, null, false);

        builder.setView(v);
        final EditText etItem = v.findViewById(R.id.etItem);
        builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!etItem.getText().toString().isEmpty()) {
                    for(int i = 0; i < arrayAdapter.getCount(); i++){
                        FoodItem f = (FoodItem)arrayAdapter.getItem(i);
                        if(f.getName().equals(etItem.getText().toString().trim())){
                            showFoodItem(f, (int) arrayAdapter.getItemImage(i));
                            return;
                        }
                    }
                    etItem.setError("Item does not exist");
                } else {
                    etItem.setError("Item does not exist");
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
    private void showFoodItem(FoodItem f, int imageId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Search for a item");

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.inventory_list_card, null, false);

        builder.setView(v);
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        TextView foodName=(TextView) v.findViewById(R.id.inventory_card_food_name);
        TextView foodDate=(TextView) v.findViewById(R.id.inventory_card_food_buy_date);
        TextView foodAmount=(TextView) v.findViewById(R.id.inventory_card_food_amount);
        TextView foodAllergens=(TextView) v.findViewById(R.id.inventory_card_food_allergen);
        ImageView food_image=(ImageView) v.findViewById(R.id.inventory_card_image);
        foodName.setText(f.getName());

        foodDate.setText("Bought on " + formatter.format(f.getBuyDate()));
        foodAmount.setText(String.valueOf(f.getAmount()));
        StringBuilder foodAllergies = new StringBuilder();
        int i = 0;
        for(String foodAllergy: f.getAllergens()){
            if(i < f.getAllergens().size() - 1){
                foodAllergies.append(foodAllergy + ", ");
            }else{
                foodAllergies.append(foodAllergy);
            }
            i++;
        }

        foodAllergens.setText(foodAllergies.toString());
        food_image.setImageResource(imageId);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        builder.show();
    }

    private void addCartItem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add New Item");

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.add_food_item_form, null, false);

        builder.setView(v);
        final EditText foodName = v.findViewById(R.id.addFoodName);
        final EditText foodAmount = v.findViewById(R.id.addFoodAmount);
        final EditText foodDate = v.findViewById(R.id.addFoodDate);
        final CheckBox[] foodAllergens = {
                v.findViewById(R.id.addFoodAllergensDairy),
                v.findViewById(R.id.addFoodAllergensGluten),
                v.findViewById(R.id.addFoodAllergensPeanut),
                v.findViewById(R.id.addFoodAllergensTreeNut),
                v.findViewById(R.id.addFoodAllergensShellfish),
                v.findViewById(R.id.addFoodAllergensSoy)
        };
        final TextView[] foodAllergensText = {
                v.findViewById(R.id.addFoodAllergensDairyText),
                v.findViewById(R.id.addFoodAllergensGlutenText),
                v.findViewById(R.id.addFoodAllergensPeanutText),
                v.findViewById(R.id.addFoodAllergensTreeNutText),
                v.findViewById(R.id.addFoodAllergensShellfishText),
                v.findViewById(R.id.addFoodAllergensSoyText)
        };
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
                && !foodDate.getText().toString().isEmpty()
                && !foodOwner.getText().toString().isEmpty()) {
                    try {
                        HashSet <String> foodAllergies = new HashSet<String>();
                        for(int i = 0; i < foodAllergens.length;i++){
                            if(foodAllergens[i].isChecked()){
                                foodAllergies.add(foodAllergensText[i].getText().toString().trim());
                            }
                        }
                        list.add(
                            new FoodItem(
                                    foodName.getText().toString().trim(),
                                    Integer.parseInt(foodAmount.getText().toString().trim()),
                                    new SimpleDateFormat("MM/dd/yyyy").parse(foodDate.getText().toString().trim()),
                                    foodAllergies,
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