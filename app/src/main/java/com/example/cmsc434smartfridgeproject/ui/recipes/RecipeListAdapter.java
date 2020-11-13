package com.example.cmsc434smartfridgeproject.ui.recipes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.cmsc434smartfridgeproject.R;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

// The following code was made while following a tutorial from CodingwithMitch
// This allows for the recipe cards to be displayed in a recycled listview
// and uses the Android Studio's global Image Loader (on Github) to load
// the images for each card.

public class RecipeListAdapter extends ArrayAdapter<Recipe> {

    private static final String TAG = "CustomListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;
    private static LayoutInflater inflater=null;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView title;
        ImageView image;
    }

    /**
     * Default constructor
     * @param context
     * @param resource
     * @param objects
     */
    public RecipeListAdapter(Context context, int resource, ArrayList<Recipe> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        inflater = ( LayoutInflater )mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        //sets up the image loader library
        setupImageLoader();

        //get the recipe information
        String title = getItem(position).getTitle();
        String imgUrl = getItem(position).getImageURL();

        try{

            //ViewHolder object
            ViewHolder holder;

            convertView = inflater.inflate(mResource, parent, false);

            // creates a view holder and initializes the variables per card
            holder= new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.recipeTitle);
            holder.image = (ImageView) convertView.findViewById(R.id.recipeImage);
                convertView.setTag(holder);

                // keeps track of the recipe's position
            lastPosition = position;

            //sets the title of the recipe
            holder.title.setText(title);

            //creates the image loader to load each image
            ImageLoader imageLoader = ImageLoader.getInstance();

            // if there is no image available, fills it in with a blank(transparent) image with a border around it
            int defaultImage = mContext.getResources().getIdentifier("@drawable/border",null,mContext.getPackageName());

            //create display options
            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                    .cacheOnDisc(true).resetViewBeforeLoading(true)
                    .showImageForEmptyUri(defaultImage)
                    .showImageOnFail(defaultImage)
                    .showImageOnLoading(defaultImage).build();

            //download and display image from url
            imageLoader.displayImage(imgUrl, holder.image, options);

            return convertView;
        }catch (IllegalArgumentException e){
            Log.e(TAG, "getView: IllegalArgumentException: " + e.getMessage() );
            return convertView;
        }

    }

    /**
     * Required for setting up the Universal Image loader Library
     * This is part of the Image loader Library code
     */
    private void setupImageLoader(){
        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP
    }
}
