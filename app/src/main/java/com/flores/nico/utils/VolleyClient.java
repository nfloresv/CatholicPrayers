package com.flores.nico.utils;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nicoflores on 06-12-14.
 */
public class VolleyClient {
    private static Context context;
    // Volley
    private static VolleyClient volleyClient;
    private RequestQueue queue;
    private static final String TAG = "VolleyClient";
    // URLs
    private final String BASE_URL = "http://feed.evangelizo.org/v2/reader.php?lang=AM&";
    private final String SAINT_URL = BASE_URL + "type=saint";
    private final String GOSPEL_TITLE_URL = BASE_URL + "type=reading_st&content=GSP";
    private final String GOSPEL_URL = BASE_URL + "type=reading&content=GSP";
    // Utils
    private String DATE_PARAM = "&date=";
    private Cache cache;

    private VolleyClient () {
        queue = Volley.newRequestQueue(context);
        cache = queue.getCache();
        DATE_PARAM = DATE_PARAM + new SimpleDateFormat("yyyyMMdd").format(new Date()).concat
                (DATE_PARAM);
    }

    private String testCache (String url, Response.Listener<String> success,
                             Response.ErrorListener error) {
        Cache.Entry entry = cache.get(url);
        if (entry != null) {
            try {
                return new String(entry.data);
            } catch (Exception e) {
                Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
        else {
            cache.clear();
            StringRequest request = new StringRequest(Request.Method.GET, url,
                    success, error);
            request.setTag(TAG);
            queue.add(request);
        }
        return null;
    }

    public static VolleyClient getInstance (Context ctx) {
        if (volleyClient == null) {
            context = ctx;
            volleyClient = new VolleyClient();
        }
        return volleyClient;
    }

    public void cancel() {
        queue.cancelAll(TAG);
    }

    public String getSaintOfTheDay (Response.Listener<String> success,
                                   Response.ErrorListener error) {
        String url = SAINT_URL + DATE_PARAM;
        return testCache(url, success, error);
    }

    public String getGospelTitle (Response.Listener<String> success, Response.ErrorListener error) {
        String url = GOSPEL_TITLE_URL + DATE_PARAM;
        return testCache(url, success, error);
    }

    public String getGospel (Response.Listener<String> success, Response.ErrorListener error) {
        String url = GOSPEL_URL + DATE_PARAM;
        return testCache(url, success, error);
    }
}
