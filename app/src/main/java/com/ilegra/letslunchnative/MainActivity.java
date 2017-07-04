package com.ilegra.letslunchnative;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private JSONArray result;
    private TextView header;
    private TextView restaurantName;
    private TextView restaurantAddress;
    private ImageView restaurantImage;
    ImageLoader imageLoader;
    private int restaurantPosition = 0;
    private Restaurant restaurant;
    private ArrayList<Restaurant> restaurants;
    private String[] restaurantsName;
    private String[] restaurantsAddress;
    private String[] restaurantsPhotoReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        header = (TextView) findViewById(R.id.toolbarHeaderId);
        restaurantName = (TextView) findViewById(R.id.restaurantNameId);
        restaurantAddress = (TextView) findViewById(R.id.restaurantAddressId);
        restaurantImage = (ImageView) findViewById(R.id.restaurantImageId);
        restaurants = new ArrayList<>();

        setFont();

        // Create global configuration and initialize ImageLoader with this config
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();

        Intent intent = getIntent();
        String jsonArray = intent.getStringExtra("result");

        try {
            result = new JSONArray(jsonArray);
            updateRestaurantInfo();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getAssets(), "font/Bitter-Bold.ttf");
        header.setTypeface(font);
        restaurantName.setTypeface(font);
    }

    private void updateRestaurantInfo() throws JSONException {
        if (result.getJSONObject(restaurantPosition).has("photos")) {
            final String name = result.getJSONObject(restaurantPosition).getString("name");
            final String address = result.getJSONObject(restaurantPosition).getString("vicinity");
            JSONArray photo = result.getJSONObject(restaurantPosition).getJSONArray("photos");

            restaurant = new Restaurant(name, address, getPhotoReference(photo, "photo_reference"));
            restaurants.add(restaurant);

            restaurantName.setText(name);
            restaurantAddress.setText(address);
            imageLoader.displayImage(getUrlForImage(getPhotoReference(photo, "photo_reference")), restaurantImage, null,  new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    restaurantImage.setImageResource(R.color.colorAccent);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    Animation anim = AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in);
                    restaurantImage.setAnimation(anim);
                    anim.start();
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });
        }
        restaurantPosition++;

    }

    private String getPhotoReference(JSONArray photo, String photo_reference) throws JSONException {
        return photo.getJSONObject(0).getString(photo_reference);
    }

    private String getUrlForImage(String reference){
        String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=";
        String myKey = "&key=AIzaSyCp5OnViFVhTyj9R-3RuyWgyCaAGlbxdms";

        return url + reference + myKey;
    }

    public void onLikeClick(View view){
        if (10 < restaurantPosition) {
            Intent intent = new Intent(MainActivity.this, FinalActivity.class);
            getArraysOfData();
            intent.putExtra("name", restaurantsName);
            intent.putExtra("addresses", restaurantsAddress);
            intent.putExtra("photoReferences", restaurantsPhotoReference);
            startActivity(intent);
        }
        try {
            updateRestaurantInfo();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void getArraysOfData(){
        int i = 0;
        restaurantsName = new String[restaurants.size()];
        restaurantsAddress = new String[restaurants.size()];
        restaurantsPhotoReference = new String[restaurants.size()];
        for (Restaurant restaurant :
                restaurants) {
            restaurantsName[i] = restaurant.getName();
            restaurantsAddress[i] = restaurant.getAddress();
            restaurantsPhotoReference[i] = restaurant.getPhotoReference();
            i++;
        }
    }

}

