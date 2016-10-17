package ivan.osago.network;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by ivan on 16.10.16.
 */

public class Request {
    private Context context;
    public Request(Context context){
        this.context = context;
    }

    public void request(String url){

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Get a RequestQueue
        RequestQueue queue = VolleySingletone.getInstance(context).
                getRequestQueue();

        VolleySingletone.getInstance(context).addToRequestQueue(stringRequest);
    }
}
