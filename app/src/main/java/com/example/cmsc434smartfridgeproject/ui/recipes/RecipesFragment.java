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
    private RecipesViewModel recipesViewModel;
    private ListView mRecipesListView;
    private ArrayAdapter<String> adapter;

    // need to add recipeDetails (long string) and display tags, hasAllergens, isVegetarian via a horizontal scroll view

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recipesViewModel =
                ViewModelProviders.of(this).get(RecipesViewModel.class);
        View book = inflater.inflate(R.layout.fragment_recipes_list, container, false);

        mRecipesListView = (ListView) book.findViewById(R.id.recipesListView);

        ArrayList<String> instructionsList = new ArrayList<>();
        instructionsList.add("Back for 30 minutes.");
        instructionsList.add("Sautee the tomatoes");
        instructionsList.add("Bake until 160 degrees F");
        instructionsList.add("Toss with the dressing");
        instructionsList.add("Cook at medium heat");

        ArrayList<String[]> ingredientsList = new ArrayList<>();
        ingredientsList.add(new String[]{"4 oz Salmon Fillets", "Salt", "Pepper"});
        ingredientsList.add(new String[]{"4 Large Eggs", "1 can of crushed tomato", "1 Red Bell Pepper, diced"});
        ingredientsList.add(new String[]{"4 oz bone-in Pork Chops", "Packed Brown Sugar"});
        ingredientsList.add(new String[]{"2 heads of Romaine Lettuce", "10 Kalamata Olives", "Feta"});
        ingredientsList.add(new String[]{"5 Eggs"});

        final ArrayList<Recipe> recipeList = new ArrayList<>();
        recipeList.add(new Recipe("drawable://" + R.drawable.bakedsalmon, "Baked Salmon", ingredientsList.get(0), instructionsList.get(0)));
        recipeList.add(new Recipe("drawable://" + R.drawable.bakedsalmon, "Shakshuka", ingredientsList.get(1), instructionsList.get(1)));
        recipeList.add(new Recipe("drawable://" + R.drawable.bakedsalmon, "Vietnamese Pork Chops", ingredientsList.get(2), instructionsList.get(2)));
        recipeList.add(new Recipe("drawable://" + R.drawable.bakedsalmon, "Greek Salad", ingredientsList.get(3), instructionsList.get(3)));
        recipeList.add(new Recipe("drawable://" + R.drawable.bakedsalmon, "Omelette", ingredientsList.get(4), instructionsList.get(4)));

        RecipeListAdapter adapter = new RecipeListAdapter(this.getContext(), R.layout.fragment_recipes, recipeList);
        mRecipesListView.setAdapter(adapter);

        mRecipesListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getContext(), "Recipe clicked!", Toast.LENGTH_SHORT).show();

                Recipe selected = recipeList.get(position);
                RecipesItemFragment recipesItemFragment = new RecipesItemFragment();

                Bundle bundle = new Bundle();
                bundle.putString("recipe_image", selected.getImageURL());
                bundle.putString("recipe_title", selected.getTitle());
                bundle.putStringArray("recipe_ingredients", selected.getIngredients());
                bundle.putString("recipe_instructions", selected.getInstructions());
                recipesItemFragment.setArguments(bundle);

                FragmentManager manager = getFragmentManager();
              manager.beginTransaction().replace(R.id.nav_host_fragment, recipesItemFragment, recipesItemFragment.getTag()).commit();
            }
        });

        return book;
    }
}
