package com.example.cmsc434smartfridgeproject;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;

import com.example.cmsc434smartfridgeproject.utils.FoodItem;

import java.util.List;

public class CardListAdapter extends BaseAdapter{
    List<FoodItem> result;
    Context context;
    int currentView;
    int [] imageId;
    private static LayoutInflater inflater=null;
    public CardListAdapter(Context givenContext, @LayoutRes int listView, List<FoodItem> foods, int[] prgmImages) {
        // TODO Auto-generated constructor stub
        result=foods;
        context=givenContext;
        imageId=prgmImages;
        currentView = listView;
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
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView foodName;
        TextView foodDate;
        TextView foodAmount;
        TextView foodAllergens;
        ImageView food_image;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(this.currentView, null);
        holder.foodName=(TextView) rowView.findViewById(R.id.inventory_card_food_name);
        holder.foodDate=(TextView) rowView.findViewById(R.id.inventory_card_food_buy_date);
        holder.foodAmount=(TextView) rowView.findViewById(R.id.inventory_card_food_amount);
        holder.foodAllergens=(TextView) rowView.findViewById(R.id.inventory_card_food_allergen);
        holder.food_image=(ImageView) rowView.findViewById(R.id.inventory_card_image);
        holder.foodName.setText(result.get(position).getName());
        holder.foodDate.setText(result.get(position).getBuyDate().toString());
        holder.foodAmount.setText(String.valueOf(result.get(position).getAmount()));
//        need to change here
        holder.foodAllergens.setText(result.get(position).getName());
        holder.food_image.setImageResource(imageId[position]);
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked "+result.get(position), Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }
}
