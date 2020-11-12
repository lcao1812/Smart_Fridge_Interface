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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.cmsc434smartfridgeproject.CardListAdapter;
import com.example.cmsc434smartfridgeproject.R;
import com.example.cmsc434smartfridgeproject.utils.CSVFile;
import com.example.cmsc434smartfridgeproject.utils.FoodItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class InventoryFragment extends Fragment {

    private InventoryViewModel inventoryViewModel;
    ListView fridgelistView;
    CardListAdapter arrayAdapter;
    List<Integer> imgs;
    List<FoodItem> list;
    FloatingActionButton addItemButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);// set drawable icon
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setDisplayShowHomeEnabled(true);
        setHasOptionsMenu(true);
        inventoryViewModel =
                ViewModelProviders.of(this).get(InventoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_inventory, container, false);




        list = new ArrayList<FoodItem>();
        imgs = new ArrayList<Integer>();
        InputStream inputStream = getResources().openRawResource(R.raw.data);
        CSVFile csvFile = new CSVFile(inputStream);
        try {
            list = csvFile.read();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        imgs.add(R.drawable.orange);
        imgs.add(R.drawable.eggs);
        imgs.add(R.drawable.apple);
        imgs.add(R.drawable.milk);
        imgs.add(R.drawable.broccoli);
        imgs.add(R.drawable.cauliflower);
        imgs.add(R.drawable.porkshoulder);
        imgs.add(R.drawable.salmon);
        imgs.add(R.drawable.miso);
        imgs.add(R.drawable.onion);
        imgs.add(R.drawable.bellpepper);
        imgs.add(R.drawable.butter);
        imgs.add(R.drawable.chedder);
        imgs.add(R.drawable.lime);
        imgs.add(R.drawable.shallot);
        imgs.add(R.drawable.parsley);
        imgs.add(R.drawable.caper);
        imgs.add(R.drawable.dill);
        imgs.add(R.drawable.oregano);
        imgs.add(R.drawable.garlic);
        imgs.add(R.drawable.cucumber);
        imgs.add(R.drawable.feta);
        imgs.add(R.drawable.lemon);
        imgs.add(R.drawable.plumtomatoes);
        imgs.add(R.drawable.pita);
        imgs.add(R.drawable.olive);


        arrayAdapter = new CardListAdapter(getActivity().getApplicationContext(), getActivity(), R.layout.inventory_list_card, list, imgs);
        fridgelistView = root.findViewById(R.id._fridge_inventory_list);
        fridgelistView.setAdapter(arrayAdapter);
        addItemButton = root.findViewById(R.id.inventory_add_button);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCartItem();

            }
        });
        return root;
    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu items for use in the action bar
        inflater.inflate(R.menu.action_bar, menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
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
        final RadioButton owner = v.findViewById(R.id.sort_by_form_owner);

        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Map<FoodItem,Integer> m = new HashMap<FoodItem,Integer>();
                for(int i =0; i < list.size(); i++){
                    m.put(list.get(i), imgs.get(i));
                }
                if((name.isChecked() && !date.isChecked() && !owner.isChecked()) ||
                        (!name.isChecked() && date.isChecked() && !owner.isChecked()) ||
                        (!name.isChecked() && !date.isChecked() && owner.isChecked())
                ){
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
                        imgs.clear();
                        for(int i =0; i < list.size(); i++){
                            imgs.add(newImgList.get(i));
                        }

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
                        imgs.clear();
                        for(int i =0; i < list.size(); i++){
                            imgs.add(newImgList.get(i));
                        }
                    }
                    if(owner.isChecked()){
                        Collections.sort(
                                list,
                                new Comparator<FoodItem>() {
                                    @Override
                                    public int compare(FoodItem f1, FoodItem f2) {
                                        return f1.getOwner().compareTo(f2.getOwner());
                                    }
                                });
                        ArrayList <Integer> newImgList = new <Integer>ArrayList();
                        for(int i =0; i < list.size(); i++){
                            newImgList.add(m.get(list.get(i)));
                        }
                        imgs.clear();
                        for(int i =0; i < list.size(); i++){
                            imgs.add(newImgList.get(i));
                        }
                    }
                    arrayAdapter.notifyDataSetChanged();
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
                    showNoFoodItem(etItem.getText().toString().trim());
                } else {
                    showNoFoodItem(etItem.getText().toString().trim());
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
    private void showNoFoodItem(String itemName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(itemName + " was not found.");
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        builder.show();
    }
    private void addCartItemError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Item could not be added.");
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
                    addCartItemError();
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