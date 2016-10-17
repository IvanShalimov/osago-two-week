package ivan.osago.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import ivan.osago.Application;

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

            };
        }){
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                System.out.println(response.headers.get("Set-Cookie"));
                String phpsessid = response.headers.get("Set-Cookie");
                Log.d("Test",phpsessid);
                Application.getInstancce().setPhpSessionId(phpsessid);
                return super.parseNetworkResponse(response);
            }
        };

        // Get a RequestQueue
        RequestQueue queue = VolleySingletone.getInstance(context).
                getRequestQueue();

        VolleySingletone.getInstance(context).addToRequestQueue(stringRequest);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    Callback callback;

    public void executeImageRequest(String url){


        // Retrieves an image specified by the URL, displays it in the UI.
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        Log.d("Test","bitmap"+bitmap);
                        if(callback != null)
                            callback.setImage(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
            public Map<String, String> getHeaders() {
                Map<String, String> mHeaders = new HashMap<>();
                mHeaders.put("Cookie","JSESSIONID="+Application.getInstancce().getPhpSessionId());
                return mHeaders;
            }
            //JSESSIONID=
        };

        VolleySingletone.getInstance(context).addToRequestQueue(request);
    }

    public interface Callback{
        void setImage(Bitmap bitmap);
    }
}
