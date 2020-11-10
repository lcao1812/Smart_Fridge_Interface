package com.example.cmsc434smartfridgeproject.ui.cart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.sax.TextElementListener;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.cmsc434smartfridgeproject.CardListAdapter;
import com.example.cmsc434smartfridgeproject.R;
import com.example.cmsc434smartfridgeproject.utils.FoodItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;


public class CartItemFragment extends Fragment {

    private ArrayList<String> toPurchase = new ArrayList<>();
    private ArrayList<String> purchased = new ArrayList<>();
    private ListView cartList;
    private ListView purchasedList;
    private ArrayAdapter<String> cartAdapter;
    private ArrayAdapter<String> cartAdapterPurchased;
    private FloatingActionButton add_fab;
    private ArrayList<Integer> imgs = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View itemCart = inflater.inflate(R.layout.fragment_cart_item, container, false);
        TextView tvDetail = (TextView) itemCart.findViewById(R.id.tvDetail);

        Bundle bundle = getArguments();

        if (bundle != null) {
            if (bundle.getString("selected_cart") != null) {
                tvDetail.setText("Shopping List for: " + bundle.getString("selected_cart"));
            }
        }

        if (bundle.getString("selected_cart") == "Nicky's birthday") {
            toPurchase.add("Orange");
            toPurchase.add("Onions");
            toPurchase.add("Pork Shoulder");
            toPurchase.add("Eggs");
            toPurchase.add("Hot Dog");
        } else if (bundle.getString("selected_cart") == "Week of Sept 2nd") {
            toPurchase.add("Steak");
            toPurchase.add("Salmon");
            toPurchase.add("Tomato");
            toPurchase.add("Milk");
            toPurchase.add("Broccoli");
        } else if (bundle.getString("selected_cart").contains("This is an example scenario")) {
            toPurchase.add("Example Item 1");
            toPurchase.add("Example Item 2");
            toPurchase.add("Example Item 3");
            toPurchase.add("Example Item 4");
        } else {
            toPurchase.add("Add item to your cart");
        }


        cartList = (ListView) itemCart.findViewById(R.id.toPurchaseList);
        cartAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, toPurchase) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
                return view;
            }
        };
        cartList.setAdapter(cartAdapter);

        purchasedList = (ListView) itemCart.findViewById(R.id.purchasedList);
        cartAdapterPurchased = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, purchased) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                CheckedTextView cv = ((CheckedTextView) view);
                cv.setChecked(true);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
                text.setPaintFlags(text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                return view;
            }
        };
        purchasedList.setAdapter(cartAdapterPurchased);

        add_fab = (FloatingActionButton) itemCart.findViewById(R.id.fab_add_item);
        add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Function to add Item here
                addCartItem();

                // ****************************
            }
        });

        cartList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String item = toPurchase.get(position);
                if (!purchased.contains(item) && item != "Add item to your cart") {
                    purchased.add(0, item);
                    toPurchase.remove(item);
                    Toast.makeText(getContext(), item + " purchased.", Toast.LENGTH_SHORT).show();
                }

                if (item == "Add item to your cart") {
                    addCartItem();
                }

                if (toPurchase.isEmpty()) {
                    toPurchase.add("Add item to your cart");
                }


                cartAdapter.notifyDataSetChanged();
                cartAdapterPurchased.notifyDataSetChanged();

            }
        });

        cartList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                deleteItem(position, toPurchase);

                return true;
            }
        });

        purchasedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String item = purchased.get(position);
                toPurchase.add(0, item);
                purchased.remove(item);
                Toast.makeText(getContext(), item + " removed from purchased", Toast.LENGTH_SHORT).show();

                if (toPurchase.contains("Add item to your cart")) {
                    toPurchase.remove("Add item to your cart");
                }

                cartAdapter.notifyDataSetChanged();
                cartAdapterPurchased.notifyDataSetChanged();

            }
        });

        return itemCart;
    }


    public View getViewByPosition(int position, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (position < firstListItemPosition || position > lastListItemPosition ) {
            return listView.getAdapter().getView(position, listView.getChildAt(position), listView);
        } else {
            final int childIndex = position - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    private void addCartItem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add New Item");

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.item_dialog, null, false);

        builder.setView(v);
        final EditText etItem = v.findViewById(R.id.etItem);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!etItem.getText().toString().isEmpty()) {
                    toPurchase.add(1, etItem.getText().toString().trim());
                    if (toPurchase.contains("Add item to your cart")) {
                        toPurchase.remove("Add item to your cart");
                    }
                    cartAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext() , "item added to the cart", Toast.LENGTH_SHORT).show();
                } else {
                    etItem.setError("add item here !");
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

    private void deleteItem(final int position, final ArrayList<String> list) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.delete_item, null, false);
        builder.setView(v);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                list.remove(position);
                cartAdapter.notifyDataSetChanged();
                cartAdapterPurchased.notifyDataSetChanged();
                Toast.makeText(getContext(), "Item Removed", Toast.LENGTH_SHORT).show();
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