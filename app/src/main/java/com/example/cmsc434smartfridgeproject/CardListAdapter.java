package com.example.cmsc434smartfridgeproject;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.LayoutRes;
import androidx.cardview.widget.CardView;

import com.example.cmsc434smartfridgeproject.utils.FoodItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class CardListAdapter extends BaseAdapter{
    List<FoodItem> result;
    Context context;
    int currentView;
    Activity mainActivity;
    List<Integer> imageId;
    private static LayoutInflater inflater=null;
    public CardListAdapter(Context givenContext, Activity activity,@LayoutRes int listView, List<FoodItem> foods, List<Integer> prgmImages) {
        // TODO Auto-generated constructor stub
        result=foods;
        context=givenContext;
        imageId=prgmImages;
        currentView = listView;
        mainActivity = activity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return result.get(position);
    }
    public Object getItemImage(int position) {
        // TODO Auto-generated method stub
        return imageId.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    public void remove(int position){
        result.remove(position);
        imageId.remove(position);
        notifyDataSetChanged();
    }
    public class Holder
    {
        TextView foodName;
        TextView foodExpDate;
        TextView foodAmount;
        TextView foodAllergens;
        TextView foodOwner;
        ImageView food_image;
        ImageButton incAmount;
        ImageButton decAmount;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        final View rowView;
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        rowView = inflater.inflate(this.currentView, null);
        holder.foodName=(TextView) rowView.findViewById(R.id.inventory_card_food_name);
        holder.foodExpDate=(TextView) rowView.findViewById(R.id.inventory_card_food_exp_date);
        holder.foodAmount=(TextView) rowView.findViewById(R.id.inventory_card_food_amount);
        holder.foodAllergens=(TextView) rowView.findViewById(R.id.inventory_card_food_allergen);
        holder.foodOwner = (TextView) rowView.findViewById(R.id.inventory_card_food_owner);
        holder.food_image=(ImageView) rowView.findViewById(R.id.inventory_card_image);
        holder.incAmount=(ImageButton) rowView.findViewById(R.id.inventory_card_increase_food_amount);
        holder.decAmount=(ImageButton) rowView.findViewById(R.id.inventory_card_decrease_food_amount);
        holder.foodName.setText(result.get(position).getName());
        holder.foodExpDate.setText("Expires on " + formatter.format(result.get(position).getExpDate()));
        if(checkExpired(result.get(position).getExpDate())){
            holder.foodExpDate.setText("Item is Expired");
            holder.foodExpDate.setTextColor(Color.RED);
        }
        holder.foodAmount.setText("X "+ String.valueOf(result.get(position).getAmount()));
        holder.foodOwner.setText("Owner: "+result.get(position).getOwner());
        StringBuilder foodAllergies = new StringBuilder();
        int i = 0;
        foodAllergies.append("Food Allergens: ");
        for(String foodAllergy: result.get(position).getAllergens()){
            if(i < result.get(position).getAllergens().size() - 1){
                foodAllergies.append(foodAllergy + ", ");
            }else{
                foodAllergies.append(foodAllergy);
            }
            i++;
        }

        holder.foodAllergens.setText(foodAllergies.toString());
        holder.food_image.setImageResource(imageId.get(position));
        holder.incAmount.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                int amount= result.get(position).getAmount() + 1;
                result.get(position).setAmount(amount);
                ((TextView) rowView.findViewById(R.id.inventory_card_food_amount)).setText("X "+String.valueOf(amount));
            }
        });
        holder.decAmount.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                int amount= result.get(position).getAmount()-1 > 0 ? result.get(position).getAmount() - 1 : 0;
                if(amount == 0){
                    deleteItem(position);
                }else {
                    result.get(position).setAmount(amount);
                    ((TextView) rowView.findViewById(R.id.inventory_card_food_amount)).setText("X "+String.valueOf(amount));
                }

            }
        });
        rowView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                mainActivity.openOptionsMenu();
                menuOptions(v, position);
                return false;
            }
        });
        return rowView;
    }
    private  boolean checkExpired(Date date){
        Calendar calendar = Calendar.getInstance();
        if (calendar.getTime().compareTo(date) > 0){
            return true;
        }
        return  false;
    }

    private  void menuOptions(View view , final int position){
        final AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        View v = LayoutInflater.from(mainActivity).inflate(R.layout.inventory_item_options, null, false);
        builder.setView(v);
        final TextView editItem = v.findViewById(R.id.edit_item_option);

        TextView  deleteItem = v.findViewById(R.id.delete_item_option);
        editItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                editItem(position);
            }
        });
        deleteItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                deleteItem(position);

            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
        Toast.makeText(mainActivity, "Item has been successfully deleted.",
                Toast.LENGTH_LONG).show();

    }
    private void deleteItem(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        View v = LayoutInflater.from(mainActivity).inflate(R.layout.delete_item, null, false);
        builder.setView(v);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                remove(position);
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
    private void editItem(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        View v = LayoutInflater.from(mainActivity).inflate(R.layout.edit_food_item_form, null, false);
        builder.setView(v);
        final EditText foodName = v.findViewById(R.id.editFoodName);
        final EditText foodAmount = v.findViewById(R.id.editFoodAmount);
        final EditText foodDate = v.findViewById(R.id.editFoodDate);
        final CheckBox[] foodAllergens = {
                v.findViewById(R.id.editFoodAllergensDairy),
                v.findViewById(R.id.editFoodAllergensGluten),
                v.findViewById(R.id.editFoodAllergensPeanut),
                v.findViewById(R.id.editFoodAllergensTreeNut),
                v.findViewById(R.id.editFoodAllergensShellfish),
                v.findViewById(R.id.editFoodAllergensSoy)
        };
        final TextView[] foodAllergensText = {
                v.findViewById(R.id.editFoodAllergensDairyText),
                v.findViewById(R.id.editFoodAllergensGlutenText),
                v.findViewById(R.id.editFoodAllergensPeanutText),
                v.findViewById(R.id.editFoodAllergensTreeNutText),
                v.findViewById(R.id.editFoodAllergensShellfishText),
                v.findViewById(R.id.editFoodAllergensSoyText)
        };
        final EditText foodOwner= v.findViewById(R.id.editFoodOwner);
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

                foodDate.setText(formatter.format(myCalendar.getTime()));
            }

        };

        foodDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(mainActivity, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        foodName.setText(result.get(position).getName());
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        foodDate.setText( formatter.format(result.get(position).getExpDate()));
        foodAmount.setText(Integer.toString(result.get(position).getAmount()));
        foodOwner.setText(result.get(position).getOwner());

        for(String foodAllergy: result.get(position).getAllergens()){
            if(foodAllergy.equals("Dairy")){
                foodAllergens[0].setChecked(true);
            }
            else if(foodAllergy.equals("Gluten")){
                foodAllergens[1].setChecked(true);
            }
            else if(foodAllergy.equals("Peanut")){
                foodAllergens[2].setChecked(true);
            }
            else if(foodAllergy.equals("Tree nut")){
                foodAllergens[3].setChecked(true);
            }
            else if(foodAllergy.equals("Shellfish")){
                foodAllergens[4].setChecked(true);
            }
            else if(foodAllergy.equals("Soy")){
                foodAllergens[5].setChecked(true);
            }

        }




        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                result.get(position).setName(foodName.getText().toString().trim());

                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                try {
                    result.get(position).setExpDate(foodDate.getText().toString().trim());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                result.get(position).setAmount(Integer.parseInt(foodAmount.getText().toString().trim()));
                result.get(position).setOwner(foodOwner.getText().toString().trim());
                HashSet<String> allergens = new HashSet<String>();
                for(int i = 0 ; i < foodAllergens.length; i++){
                    if(foodAllergens[i].isChecked()){
                        allergens.add(foodAllergensText[i].getText().toString());
                    }
                }
                result.get(position).setAllergens(allergens);
                notifyDataSetChanged();
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
