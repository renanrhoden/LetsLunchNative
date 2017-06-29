package com.ilegra.letslunchnative;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView header = (TextView) findViewById(R.id.toolbarHeaderId);
        final TextView restaurantName = (TextView) findViewById(R.id.restaurantNameId);
        final TextView restaurantAddress = (TextView) findViewById(R.id.restaurantAddressId);
        Typeface font = Typeface.createFromAsset(getAssets(), "font/Bitter-Bold.ttf");
        header.setTypeface(font);
        restaurantName.setTypeface(font);

        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&type=restaurant&keyword=cruise&key=AIzaSyCp5OnViFVhTyj9R-3RuyWgyCaAGlbxdms";
        final JSONArray[] result = new JSONArray[1];
        String urlImage = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=CnRtAAAATLZNl354RwP_9UKbQ_5Psy40texXePv4oAlgP4qNEkdIrkyse7rPXYGd9D_Uj1rVsQdWT4oRz4QrYAJNpFX7rzqqMlZw2h2E2y5IKMUZ7ouD_SlcHxYq1yL4KbKUv3qtWgTK0A6QbGh87GB3sscrHRIQiG2RrmU_jF4tENr9wGS_YxoUSSDrYjWmrNfeEHSGSc3FyhNLlBU&key=AIzaSyCp5OnViFVhTyj9R-3RuyWgyCaAGlbxdms"

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.i("Response", response.toString());
                        try {
                            result[0] = response.getJSONArray("results");
                            Log.i("Response", String.valueOf(result[0].length()));
                            restaurantName.setText(result[0].getJSONObject(0).getString("name"));
                            restaurantAddress.setText(result[0].getJSONObject(0).getString("vicinity"));
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
}
