package com.ilegra.letslunchnative;

import android.app.Activity;
import android.content.Context;
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
    private JSONArray[] result;
    private TextView restaurantName;
    private TextView restaurantAddress;
    private ImageView restaurantImage;
    ImageLoader imageLoader;
    private int restaurantPosition = 0;

    String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&type=restaurant&keyword=cruise&key=AIzaSyCp5OnViFVhTyj9R-3RuyWgyCaAGlbxdms";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the application context
        mContext = getApplicationContext();
        mActivity = MainActivity.this;

        TextView header = (TextView) findViewById(R.id.toolbarHeaderId);
        restaurantName = (TextView) findViewById(R.id.restaurantNameId);
        restaurantAddress = (TextView) findViewById(R.id.restaurantAddressId);
        restaurantImage = (ImageView) findViewById(R.id.restaurantImageId);

        Typeface font = Typeface.createFromAsset(getAssets(), "font/Bitter-Bold.ttf");
        header.setTypeface(font);
        restaurantName.setTypeface(font);

        // Create global configuration and initialize ImageLoader with this config
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);

        result = new JSONArray[1];

        getFirstData();


    }

    private void getFirstData() {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            imageLoader = ImageLoader.getInstance();
                            result[0] = response.getJSONArray("results");
                            updateRestaurantInfo();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
    }

    private void updateRestaurantInfo() throws JSONException {
        restaurantName.setText(result[0].getJSONObject(restaurantPosition).getString("name"));
        restaurantAddress.setText(result[0].getJSONObject(restaurantPosition).getString("vicinity"));
        JSONArray photo = result[0].getJSONObject(restaurantPosition).getJSONArray("photos");
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

