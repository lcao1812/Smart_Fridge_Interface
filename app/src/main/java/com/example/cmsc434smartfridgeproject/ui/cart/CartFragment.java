package com.example.cmsc434smartfridgeproject.ui.cart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.cmsc434smartfridgeproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CartFragment extends Fragment{
    private  FloatingActionButton add_fab;
    private CartViewModel cartViewModel;
    private ArrayList<String> itemList = new ArrayList<>();
    private ListView cartList;
    ArrayAdapter<String> adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cartViewModel =
                ViewModelProviders.of(this).get(CartViewModel.class);
        View cart = inflater.inflate(R.layout.fragment_cart, container, false);
        cartList = (ListView) cart.findViewById(R.id.itemList);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, itemList);
        cartList.setAdapter(adapter);

        // action for the fab button
        add_fab = (FloatingActionButton) cart.findViewById(R.id.fab_add);
        add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Function to add Item here
                addCartItem();
                // ****************************
                Snackbar.make(v, "Item Added To The Cart", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return cart;
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
                    itemList.add(etItem.getText().toString().trim());
                    adapter.notifyDataSetChanged();

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
