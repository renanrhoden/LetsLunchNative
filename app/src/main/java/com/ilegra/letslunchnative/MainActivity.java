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

public class MainActivity extends AppCompatActivity {

    private JSONArray result;
    private TextView header;
    private TextView restaurantName;
    private TextView restaurantAddress;
    private ImageView restaurantImage;
    ImageLoader imageLoader;
    private int restaurantPosition = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        header = (TextView) findViewById(R.id.toolbarHeaderId);
        restaurantName = (TextView) findViewById(R.id.restaurantNameId);
        restaurantAddress = (TextView) findViewById(R.id.restaurantAddressId);
        restaurantImage = (ImageView) findViewById(R.id.restaurantImageId);

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
        restaurantName.setText(result.getJSONObject(restaurantPosition).getString("name"));
        restaurantAddress.setText(result.getJSONObject(restaurantPosition).getString("vicinity"));
        JSONArray photo = result.getJSONObject(restaurantPosition).getJSONArray("photos");
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
        try {
            updateRestaurantInfo();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

