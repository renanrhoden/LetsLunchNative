package com.ilegra.letslunchnative;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class FinalActivity extends AppCompatActivity {

    private ArrayList<Restaurant> restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        TextView restaurantName = new TextView(FinalActivity.this);
        TextView restaurantAddress = new TextView(FinalActivity.this);
        LinearLayout layout = (LinearLayout) findViewById(R.id.likedRestaurantsLinearLayoutId);
        restaurants = new ArrayList<>();
        Intent intent = getIntent();
        restaurants = (ArrayList<Restaurant>) intent.getSerializableExtra("restaurants");

        for (Restaurant restaurant: restaurants
             ) {
            restaurantName.setText(restaurant.getName());
            restaurantAddress.setText(restaurant.getAddress());
            layout.addView(restaurantName);
            layout.addView(restaurantAddress);
        }
    }
}
