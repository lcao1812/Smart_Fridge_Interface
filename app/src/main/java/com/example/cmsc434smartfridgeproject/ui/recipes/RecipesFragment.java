package com.example.cmsc434smartfridgeproject.ui.recipes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
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

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

public class RecipesFragment extends Fragment {
    private RecipesViewModel recipesViewModel;
    private ListView mRecipesListView;
    private ArrayAdapter<String> adapter;

    //The following recipes are taken from these following sites:
    // Salmon: https://www.foodnetwork.com/recipes/oven-baked-salmon-recipe-1911951
    // Shakshuka: https://cookieandkate.com/foolproof-shakshuka-recipe/
    // Pork Chops: https://www.epicurious.com/recipes/food/views/vietnamese-pork-chops-51169530
    // Salad: https://www.simplyrecipes.com/recipes/dads_greek_salad/
    // Omelette: https://www.delish.com/cooking/recipe-ideas/a24892843/how-to-make-omelet/

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recipesViewModel =
                ViewModelProviders.of(this).get(RecipesViewModel.class);
        View book = inflater.inflate(R.layout.fragment_recipes_list, container, false);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);// set drawable icon
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setDisplayShowHomeEnabled(true);
        setHasOptionsMenu(true);

        mRecipesListView = (ListView) book.findViewById(R.id.recipesListView);

        ArrayList<String> instructionsList = new ArrayList<>();
        instructionsList.add("Preheat the oven to 450 degrees F.\n" + "\n" +
                "Season salmon with salt and pepper. Place salmon, skin side down, on a non-stick baking sheet or in a non-stick pan with an oven-proof handle. Bake until salmon is cooked through, about 12 to 15 minutes.");
        instructionsList.add("Preheat the oven to 375 degrees Fahrenheit. Warm the oil in a large, oven-safe skillet (preferably stainless steel) over medium heat. Once shimmering, add the onion, bell pepper, and salt. Cook, stirring often, until the onions are tender and turning translucent, about 4 to 6 minutes.\n" +
                "\n" + "Add the garlic, tomato paste, cumin, paprika and red pepper flakes. Cook, stirring constantly, until nice and fragrant, 1 to 2 minutes.\n" +
                "\n" + "Pour in the crushed tomatoes with their juices and add the cilantro. Stir, and let the mixture come to a simmer. Reduce the heat as necessary to maintain a gentle simmer, and cook for 5 minutes to give the flavors time to meld.\n" +
                "\n" + "Turn off the heat. Taste (careful, it’s hot), and add salt and pepper as necessary. Use the back of a spoon to make a well near the perimeter and crack the egg directly into it. Gently spoon a bit of the tomato mixture over the whites to help contain the egg. Repeat with the remaining 4 to 5 eggs, depending on how many you can fit. Sprinkle a little salt and pepper over the eggs.\n" +
                "\n" + "Carefully transfer the skillet to the oven (it’s heavy) and bake for 8 to 12 minutes, checking often once you reach 8 minutes. They’re done when the egg whites are an opaque white and the yolks have risen a bit but are still soft. They should still jiggle in the centers when you shimmy the pan. (Keep in mind that they’ll continue cooking after you pull the dish out of the oven.)\n" +
                "\n" + "Using oven mitts (both hands!), transfer the hot skillet to a heat-safe surface like the stove. Top with the crumbled feta, fresh cilantro leaves, and more red pepper flakes, if desired. Serve in bowls with crusty bread on the side.");
        instructionsList.add("Whisk shallot, brown sugar, fish sauce, vinegar, and pepper in a shallow dish. Using a fork, pierce pork chops all over (to allow marinade to penetrate faster) and add to marinade in dish. Turn to coat. Cover and let pork chops marinate at room temperature, turning occasionally, 20 minutes.\n" +
                "\n" + "Remove pork chops from marinade, scraping off excess (reserve marinade for sauce). Heat oil in a large skillet over medium-high heat. Lightly season pork chops with salt. Cook until browned and cooked through, about 4 minutes per side. Let pork chops rest 10 minutes before serving.\n" +
                "\n" + "Meanwhile, bring marinade to a boil in a small saucepan and cook until reduced to 1/4 cup, about 4 minutes. Serve pork chops with reduced marinade and lime halves.\n");
        instructionsList.add("Make dressing: Whisk the olive oil, lemon juice, garlic, vinegar, oregano, and dill together until blended. Season to taste with salt and freshly ground black pepper.\n" +
                "\n" + "2 Combine salad ingredients: Combine the tomatoes, cucumber, onion, bell pepper, olives in a bowl. Toss with dressing. Sprinkle with cheese and serve.");
        instructionsList.add("In a medium bowl, beat eggs until no whites remain, then season with salt, pepper, and a pinch red pepper flakes.\n" +
                "\n" + "In a medium non-stick skillet over medium heat, melt butter. Pour in eggs and tilt pan so eggs fully cover the entire pan. As eggs start to set, use a rubber spatula to drag cooked edges into center of pan. Tilt pan to let uncooked egg fall to the edge of the pan. \n" +
                "\n" + "Once the bottom is set, but top is still a little wet, sprinkle cheese and chives on one half of omelet. Fold other side over cheese and slide omelet onto a plate.   ");

        ArrayList<String[]> ingredientsList = new ArrayList<>();
        ingredientsList.add(new String[]{"12 ounce salmon fillet, cut into 4 pieces",
                "Coarse-grained salt",
                "Freshly ground black pepper"});
        ingredientsList.add(new String[]{"2 tablespoons olive oil", "1 large yellow onion, chopped", "1 large red bell pepper or roasted red bell pepper, chopped",
                "¼ teaspoon fine sea salt", "3 cloves garlic, pressed or minced", "2 tablespoons tomato paste", "1 teaspoon ground cumin",
                "½ teaspoon smoked paprika", "¼ teaspoon red pepper flakes, reduce or omit if sensitive to spice",
                "1 large can (28 ounces) crushed tomatoes, preferably fire-roasted",
                "2 tablespoons chopped fresh cilantro or flat-leaf parsley, plus addition cilantro or parsley leaves for garnish",
                "Freshly ground black pepper, to taste", "5 to 6 large eggs", "½ cup crumbled feta"});
        ingredientsList.add(new String[]{"1 small shallot, finely chopped",
                "1/3 cup (packed) light brown sugar",
                "1/4 cup fish sauce (such as nam pla or nuoc nam)",
                "2 tablespoons unseasoned rice vinegar",
                "1 teaspoon freshly ground black pepper",
                "4 1\" thick-cut bone-in pork chops (about 2 1/2 pounds total)",
                "1 tablespoon vegetable oil",
                "Kosher salt" +
                "Lime halves"});
        ingredientsList.add(new String[]{"6 tablespoons extra virgin olive oil",
                "2 tablespoons fresh lemon juice",
                "1/2 teaspoon fresh chopped garlic",
                "1 teaspoon red wine vinegar",
                "1/2 teaspoon dried oregano or 1 teaspoon chopped fresh oregano",
                "1/2 teaspoon dried dill, or 1 teaspoon chopped fresh dill",
                "Salt and freshly ground black pepper",
                "3 large plum tomatoes, seeded, coarsely chopped",
                "3/4 cucumber, peeled, seeded, coarsely chopped",
                "1/2 red onion, peeled, chopped",
                "1 bell pepper, seeded, coarsely chopped",
                "1/2 cup pitted black olives (preferably brine-cured), coarsely chopped",
                "A heaping half cup crumbled feta cheese"});
        ingredientsList.add(new String[]{"2 large eggs",
                "Kosher salt",
                "Freshly ground black pepper",
                "Pinch red pepper flakes",
                "2 tbsp. butter",
                "1/4 c. shredded cheddar",
                "2 tbsp. freshly chopped chives"});

        final ArrayList<Recipe> recipeList = new ArrayList<>();
        recipeList.add(new Recipe("drawable://" + R.drawable.bakedsalmon, "Baked Salmon", ingredientsList.get(0), instructionsList.get(0)));
        recipeList.add(new Recipe("drawable://" + R.drawable.shakshuka, "Shakshuka", ingredientsList.get(1), instructionsList.get(1)));
        recipeList.add(new Recipe("drawable://" + R.drawable.porkchops, "Vietnamese Pork Chops", ingredientsList.get(2), instructionsList.get(2)));
        recipeList.add(new Recipe("drawable://" + R.drawable.salad, "Greek Salad", ingredientsList.get(3), instructionsList.get(3)));
        recipeList.add(new Recipe("drawable://" + R.drawable.omelette, "Omelette", ingredientsList.get(4), instructionsList.get(4)));

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
                bundle.putInt("book_size", recipeList.size());
                recipesItemFragment.setArguments(bundle);

                FragmentManager manager = getFragmentManager();
              manager.beginTransaction().replace(R.id.nav_host_fragment, recipesItemFragment, recipesItemFragment.getTag()).addToBackStack(null).commit();
            }
        });

        return book;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
