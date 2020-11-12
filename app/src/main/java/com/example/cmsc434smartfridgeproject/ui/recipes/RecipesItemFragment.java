package com.example.cmsc434smartfridgeproject.ui.recipes;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cmsc434smartfridgeproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;

public class RecipesItemFragment extends Fragment {

    private ArrayList<String> toPurchase = new ArrayList<>();
    private ArrayList<String> purchased = new ArrayList<>();
    private ListView ingredientsList;
    private ArrayAdapter<String> recipeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View mRecipeItemView = inflater.inflate(R.layout.fragment_recipes_item, container, false);
        TextView recipeInstructions = (TextView) mRecipeItemView.findViewById(R.id.recipeInstructions);
        TextView recipeTitle = (TextView) mRecipeItemView.findViewById(R.id.recipeTitle);

        Bundle bundle = getArguments();

        if (bundle != null) {
            recipeInstructions.setText(bundle.getString("recipe_instructions"));
            recipeTitle.setText(bundle.getString("recipe_title"));
        }

        ingredientsList = (ListView) mRecipeItemView.findViewById(R.id.recipeIngredients);
        recipeAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, bundle.getStringArray("recipe_ingredients"));
        ingredientsList.setAdapter(recipeAdapter);

        ingredientsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView text = (TextView) view;

                if ((text.getPaintFlags() & Paint.STRIKE_THRU_TEXT_FLAG) > 0) {
                    text.setPaintFlags(text.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));

                } else {
                    text.setPaintFlags(text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }

                // check box
                CheckedTextView checkedTextView = ((CheckedTextView) view);
                checkedTextView.setChecked(!checkedTextView.isChecked());

            }
        });

        return mRecipeItemView;
    }
}
