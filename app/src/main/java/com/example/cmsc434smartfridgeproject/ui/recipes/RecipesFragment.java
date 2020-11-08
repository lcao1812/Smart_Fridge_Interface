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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmsc434smartfridgeproject.CardListAdapter;
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
    private static final String TAG = "RecipesFragment";
    private RecipesViewModel recipesViewModel;

    private ListView mRecipesListView;

    private ArrayAdapter<String> adapter;

    // need to add recipeDetails (long string), tags, hasAllergens, isVegetarian via a horizontal scroll view

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recipesViewModel =
                ViewModelProviders.of(this).get(RecipesViewModel.class);
        View book = inflater.inflate(R.layout.fragment_recipes_list, container, false);

        mRecipesListView = (ListView) book.findViewById(R.id.recipesListView);
        ArrayList<Recipe> recipeList = new ArrayList<>();
        recipeList.add(new Recipe("drawable://" + R.drawable.bakedsalmon, "Baked Salmon"));
        recipeList.add(new Recipe("drawable://" + R.drawable.bakedsalmon, "Shakshuka"));
        recipeList.add(new Recipe("@drawable/vietporkchops", "Vietnamese Pork Chops"));
        recipeList.add(new Recipe("@drawable/greeksalad", "Greek Salad"));

        RecipeListAdapter adapter = new RecipeListAdapter(this.getContext(), R.layout.fragment_recipes, recipeList);
        mRecipesListView.setAdapter(adapter);

//        rList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Toast.makeText(getContext(), "recipe clicked", Toast.LENGTH_SHORT).show();
//
//                String selected = recipeList.get(position);
//                RecipesItemFragment recipesItemFragment = new RecipesItemFragment();
//
//                Bundle bundle = new Bundle();
//
//                FragmentManager manager = getFragmentManager();
////              manager.beginTransaction().replace(R.id.nav_host_fragment, recipesItemFragment, recipesItemFragment.getTag()).commit();
//            }
//        });

        return book;
    }
}
