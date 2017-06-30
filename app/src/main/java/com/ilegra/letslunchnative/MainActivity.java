package com.ilegra.letslunchnative;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private Activity mActivity;
    private String urlImage;
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

        // Get the application context
        mContext = getApplicationContext();
        mActivity = MainActivity.this;

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
            Log.i("Result from intent", result.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
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
        restaurantImage.setImageResource(R.color.colorAccent);
        imageLoader.displayImage(getUrlForImage(getPhotoReference(photo, "photo_reference")), restaurantImage);
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

