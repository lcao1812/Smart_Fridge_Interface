package com.example.cmsc434smartfridgeproject.ui.home;

import android.graphics.Paint;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
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
import com.example.cmsc434smartfridgeproject.ui.cart.CartItemFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ArrayList<String> toPurchaseHome = new ArrayList<>();
    private ListView itemListHome;
    private ArrayAdapter<String> itemAdapterHome;
    private ArrayList<String> purchasedHome = new ArrayList<>();
    private ListView itemListPurchasedHome;
    private ArrayAdapter<String> purchasedAdapterHome;
    private ArrayList<String> cartsHome = new ArrayList<>();
    private ListView cartListHome;
    private ArrayAdapter<String> cartAdapterHome;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Display Current Information
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
        Date today = new Date();
        final TextView temp = root.findViewById(R.id.home_temp);
        final TextView time = root.findViewById(R.id.home_time);
        final TextView date = root.findViewById(R.id.home_date);
        temp.setText("32° F");
        time.setText(timeFormat.format(today));
        date.setText(formatter.format(today));

        // Display Current cart list
        if (toPurchaseHome.isEmpty()) {
            toPurchaseHome.add("Orange");
            toPurchaseHome.add("Salmon");
            toPurchaseHome.add("Pork Chops");
            toPurchaseHome.add("Milk");
            toPurchaseHome.add("Hot Dogs");

        }

        itemListHome = (ListView) root.findViewById(R.id.currentCartHome);
        itemAdapterHome = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, toPurchaseHome){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
                return view;
            }
        };
        itemListHome.setAdapter(itemAdapterHome);

        itemListHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String item = toPurchaseHome.get(position);
                if (!purchasedHome.contains(item)) {
                    purchasedHome.add(0, item);
                    toPurchaseHome.remove(item);
                    Toast.makeText(getContext(), item + " purchased.", Toast.LENGTH_SHORT).show();
                }

                itemAdapterHome.notifyDataSetChanged();
                purchasedAdapterHome.notifyDataSetChanged();

            }
        });

        itemListPurchasedHome = (ListView) root.findViewById(R.id.currentCartHomePurchased);
        purchasedAdapterHome = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, purchasedHome) {
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

        itemListPurchasedHome.setAdapter(purchasedAdapterHome);

        itemListPurchasedHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String item = purchasedHome.get(position);
                Toast.makeText(getContext(), item + " removed from purchased.", Toast.LENGTH_SHORT).show();
                toPurchaseHome.add(0, item);
                purchasedHome.remove(item);

                itemAdapterHome.notifyDataSetChanged();
                purchasedAdapterHome.notifyDataSetChanged();

            }
        });

        if (cartsHome.isEmpty()) {
            cartsHome.add("Week of Sept 2nd");
            cartsHome.add("This is an example scenario -- Number 1");
            cartsHome.add("This is an example scenario -- Number 2");
            cartsHome.add("This is an example scenario -- Number 3");
            cartsHome.add("This is an example scenario -- Number 4");
        }


        cartListHome = (ListView) root.findViewById(R.id.allCartsHome);
        cartAdapterHome = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, cartsHome) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
                return view;
            }
        };
        cartListHome.setAdapter(cartAdapterHome);

        cartListHome.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selected = cartsHome.get(position);
                Toast.makeText(getContext(), "Cart for " + selected + " selected.", Toast.LENGTH_SHORT).show();
                CartItemFragment cartItemFragment = new CartItemFragment();

                Bundle bundle = new Bundle();
                bundle.putString("selected_cart", selected);

                cartItemFragment.setArguments(bundle);

                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.nav_host_fragment, cartItemFragment, cartItemFragment.getTag()).addToBackStack(null).commit();
            }
        });

        return root;
    }
}