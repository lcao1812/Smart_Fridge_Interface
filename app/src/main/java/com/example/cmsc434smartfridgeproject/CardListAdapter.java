package com.example.cmsc434smartfridgeproject;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;

import com.example.cmsc434smartfridgeproject.utils.FoodItem;

import java.text.SimpleDateFormat;
import java.util.Date;
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
        TextView foodDate;
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
        holder.foodDate=(TextView) rowView.findViewById(R.id.inventory_card_food_buy_date);
        holder.foodAmount=(TextView) rowView.findViewById(R.id.inventory_card_food_amount);
        holder.foodAllergens=(TextView) rowView.findViewById(R.id.inventory_card_food_allergen);
        holder.foodOwner = (TextView) rowView.findViewById(R.id.inventory_card_food_owner);
        holder.food_image=(ImageView) rowView.findViewById(R.id.inventory_card_image);
        holder.incAmount=(ImageButton) rowView.findViewById(R.id.inventory_card_increase_food_amount);
        holder.decAmount=(ImageButton) rowView.findViewById(R.id.inventory_card_decrease_food_amount);
        holder.foodName.setText(result.get(position).getName());

        holder.foodDate.setText("Bought on " + formatter.format(result.get(position).getBuyDate()));
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
                ((TextView) rowView.findViewById(R.id.inventory_card_food_amount)).setText(String.valueOf(amount));
            }
        });
        holder.decAmount.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                int amount= result.get(position).getAmount()-1 > 0 ? result.get(position).getAmount() - 1 : 0;
                if(amount == 0){
                    remove(position);
                }else {
                    result.get(position).setAmount(amount);
                    ((TextView) rowView.findViewById(R.id.inventory_card_food_amount)).setText(String.valueOf(amount));
                }

            }
        });
        rowView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteItem(position);
                return false;
            }
        });
        return rowView;
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
}
