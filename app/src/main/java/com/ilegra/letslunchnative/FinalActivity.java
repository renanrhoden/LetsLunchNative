package com.ilegra.letslunchnative;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

public class FinalActivity extends AppCompatActivity {

    private ArrayList<Restaurant> restaurants;
    ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);


        LinearLayout layout = (LinearLayout) findViewById(R.id.likedRestaurantsLinearLayoutId);
        restaurants = new ArrayList<>();
        Intent intent = getIntent();
        String[] names = intent.getStringArrayExtra("name");
        String[] addresses = intent.getStringArrayExtra("addresses");
        String[] photoReferences = intent.getStringArrayExtra("photoReferences");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();


        for (int i = 0; i < names.length; i++){
            TextView restaurantName = (TextView) getLayoutInflater().inflate(R.layout.custom_textview, null);
            TextView restaurantAddress = new TextView(this);
            final ImageView restaurantImage = (ImageView) getLayoutInflater().inflate(R.layout.restaurant_small_image, null);
            imageLoader.displayImage(getUrlForImage(photoReferences[i]), restaurantImage, null,  new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    restaurantImage.setImageResource(R.color.colorAccent);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    Animation anim = AnimationUtils.loadAnimation(FinalActivity.this, android.R.anim.fade_in);
                    restaurantImage.setAnimation(anim);
                    anim.start();
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });
            restaurantName.setText(names[i]);
            restaurantAddress.setText(addresses[i]);
            layout.addView(restaurantName);
            layout.addView(restaurantAddress);
            layout.addView(restaurantImage);
        }
    }
    private String getUrlForImage(String reference){
        String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=";
        String myKey = "&key=AIzaSyCp5OnViFVhTyj9R-3RuyWgyCaAGlbxdms";

        return url + reference + myKey;
    }
}
