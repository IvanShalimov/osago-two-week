package ivan.osago.network;

import android.content.Context;
import android.util.Log;
import static java.nio.charset.StandardCharsets.*;
import com.android.volley.*;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.joda.time.DateTime;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import ivan.osago.Application;

/**
 * Created by ivan on 17.10.16.
 */

public class DataRequest {

    Context context;
    Callback callback;
    public DataRequest(Context context){
        this.context = context;
    }

    public DataRequest(Context context,Callback callback){
        this.context = context;
        this.callback = callback;
    }

    public void request(String url, final String captcha){

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(callback != null){
                            String utfString = null;

                            try {
                                utfString = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }

                            Log.d("Test",""+utfString);
                            callback.handleResult(utfString);
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Test",""+error.networkResponse.statusCode);
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> mHeaders = new HashMap<>();
                mHeaders.put("Cookie","JSESSIONID="+Application.getInstancce().getPhpSessionId());
                mHeaders.put("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
                mHeaders.put("Accept","application/json");
                mHeaders.put("Accept-Language","en-US,en;q=0.5");
                mHeaders.put("Accept-Encoding","gzip, deflate");
                mHeaders.put("X-Requested-With","XMLHttpRequest");
                mHeaders.put("Connection","keep-alive");
                mHeaders.put("Referer","http://dkbm-web.autoins.ru/dkbm-web-1.0/osagovehicle.htm");
                return mHeaders;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> mParams = new HashMap<>();
                mParams.put("serialOsago",Application.getInstancce().getSerialOsago());
                mParams.put("numberOsago",Application.getInstancce().getNumberOsago());
                DateTime dateTime = new DateTime();
                mParams.put("dateRequest",dateTime.getDayOfMonth()+"."+dateTime.getMonthOfYear()+"."+
                        dateTime.getYear());

                Log.d("Test",captcha);
                mParams.put("answer",""+captcha);
                return mParams;
            }
        };


        VolleySingletone.getInstance(context).addToRequestQueue(stringRequest);
    }

    public interface Callback{
        void handleResult(String result);
    }
}
