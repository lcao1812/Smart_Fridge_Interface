package com.example.cmsc434smartfridgeproject.ui.recipes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cmsc434smartfridgeproject.R;
import com.example.cmsc434smartfridgeproject.ui.recipes.RecipesFragment;
import com.example.cmsc434smartfridgeproject.ui.recipes.RecipesItemFragment;
import com.example.cmsc434smartfridgeproject.ui.recipes.RecipesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

public class RecipesFragment extends Fragment {
    private FloatingActionButton add_fab;
    private RecipesViewModel recipesViewModel;
    private ArrayList<String> recipeList = new ArrayList<>();
    private ListView rList;
    private ArrayAdapter<String> adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recipesViewModel =
                ViewModelProviders.of(this).get(RecipesViewModel.class);
        View book = inflater.inflate(R.layout.fragment_recipes, container, false);

        recipeList.add("Baked Salmon");
        recipeList.add("Week of Sept 2nd");
        recipeList.add("This is an example scenario -- Number 1");

        System.out.println(recipeList.size());

        rList = (ListView) book.findViewById(R.id.recipeList);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, recipeList);
        rList.setAdapter(adapter);

        // action for the fab button
//        add_fab = (FloatingActionButton) book.findViewById(R.id.fab_add);
//        add_fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Function to add Item here
//                addRecipe();
//                // ****************************
//            }
//        });

        rList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getContext(), "recipe clicked", Toast.LENGTH_SHORT).show();

                String selected = recipeList.get(position);
                RecipesItemFragment recipesItemFragment = new RecipesItemFragment();

                Bundle bundle = new Bundle();

                FragmentManager manager = getFragmentManager();
//              manager.beginTransaction().replace(R.id.nav_host_fragment, recipesItemFragment, recipesItemFragment.getTag()).commit();
            }
        });

        return book;
    }

//    private void addRecipe() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("Add New Recipe");
//
//        View v = LayoutInflater.from(getActivity()).inflate(R.layout.item_dialog, null, false);
//
//        builder.setView(v);
//        final EditText etItem = v.findViewById(R.id.etItem);
//        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (!etItem.getText().toString().isEmpty()) {
//                    recipeList.add(etItem.getText().toString().trim());
//                    adapter.notifyDataSetChanged();
//                    Toast.makeText(getContext() , "recipe added", Toast.LENGTH_SHORT).show();
//                } else {
//                    etItem.setError("add item here !");
//                }
//            }
//        });
//
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//
//        builder.show();
//    }
}
