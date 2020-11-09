package com.example.cmsc434smartfridgeproject.ui.cart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.cmsc434smartfridgeproject.R;
import com.example.cmsc434smartfridgeproject.utils.FoodItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

public class CartFragment extends Fragment{
    private  FloatingActionButton add_fab;
    private CartViewModel cartViewModel;
    private ArrayList<String> itemList = new ArrayList<>();
    private ListView cartList;
    private ArrayAdapter<String> adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cartViewModel =
                ViewModelProviders.of(this).get(CartViewModel.class);
        View cart = inflater.inflate(R.layout.fragment_cart, container, false);

        itemList.add("Nicky's birthday");
        itemList.add("Week of Sept 2nd");
        itemList.add("This is an example scenario -- Number 1");
        itemList.add("This is an example scenario -- Number 2");
        itemList.add("This is an example scenario -- Number 3");
        itemList.add("This is an example scenario -- Number 4");

        cartList = (ListView) cart.findViewById(R.id.itemList);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, itemList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                return view;
            }
        };
        cartList.setAdapter(adapter);


        // action for the fab button
        add_fab = (FloatingActionButton) cart.findViewById(R.id.fab_add);
        add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Function to add Item here
                addCartItem();
                // ****************************
            }
        });

        cartList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getContext(), "item clicked", Toast.LENGTH_SHORT).show();

                String selected = itemList.get(position);
                CartItemFragment cartItemFragment = new CartItemFragment();

                Bundle bundle = new Bundle();
                bundle.putString("selected_cart", selected);

                cartItemFragment.setArguments(bundle);

                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.nav_host_fragment, cartItemFragment, cartItemFragment.getTag()).addToBackStack(null).commit();
            }
        });

        cartList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                deleteItem(position, itemList);
                Toast.makeText(getContext(), "Item Removed", Toast.LENGTH_SHORT).show();

                return true;
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
                adapter.notifyDataSetChanged();
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
