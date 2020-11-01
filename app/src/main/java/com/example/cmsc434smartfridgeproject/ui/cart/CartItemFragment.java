package com.example.cmsc434smartfridgeproject.ui.cart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.sax.TextElementListener;
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

import com.example.cmsc434smartfridgeproject.CardListAdapter;
import com.example.cmsc434smartfridgeproject.R;
import com.example.cmsc434smartfridgeproject.utils.FoodItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;


public class CartItemFragment extends Fragment {

    private ArrayList<String> toPurchase = new ArrayList<>();
    private ArrayList<String> purchased = new ArrayList<>();
    private ListView cartList;
    private ArrayAdapter<String> cartAdapter;
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

        toPurchase.add("Orange");
        toPurchase.add("Salmon");
        toPurchase.add("Pork Chops");
        toPurchase.add("Milk");

        cartList = (ListView) itemCart.findViewById(R.id.toPurchaseList);
        cartAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, toPurchase);
        cartList.setAdapter(cartAdapter);

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
                TextView text = (TextView) view;

                if ((text.getPaintFlags() & Paint.STRIKE_THRU_TEXT_FLAG) > 0){
                    text.setPaintFlags(text.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                } else {
                    text.setPaintFlags(text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }


                CheckedTextView checkedTextView = ((CheckedTextView)view);
                checkedTextView.setChecked(!checkedTextView.isChecked());
            }
        });

//        cartList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                // function to edit item here
//                toPurchase.remove(position);
//                cartAdapter.notifyDataSetChanged();
//
//                return true;
//            }
//        });

        return itemCart;
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
                    toPurchase.add(etItem.getText().toString().trim());
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

}