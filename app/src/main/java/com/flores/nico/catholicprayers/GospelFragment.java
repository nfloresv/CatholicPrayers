package com.flores.nico.catholicprayers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.flores.nico.utils.VolleyClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.TextNode;

import java.util.List;


public class GospelFragment extends Fragment {
    private VolleyClient volley;


    public static GospelFragment newInstance () {
        return new GospelFragment();
    }

    public GospelFragment () {
        // Required empty public constructor
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        volley = VolleyClient.getInstance(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_gospel, container, false);

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), error.toString(),
                        Toast.LENGTH_LONG).show();
            }
        };

        getSaintOfDay(layout, errorListener);
        getGospelTitle(layout, errorListener);
        getGospelText(layout, errorListener);

        return layout;
    }

    @Override
    public void onStop () {
        super.onStop();
        volley.cancel();
    }

    private void getSaintOfDay (View layout, Response.ErrorListener errorListener) {
        final TextView saint = (TextView) layout.findViewById(R.id.saint_of_the_day_tv_fragment_gospel);
        String saintOfDay = volley.getSaintOfTheDay(
                new Response.Listener<String>() {
                    @Override
                    public void onResponse (String response) {
                        Document doc = Jsoup.parse(response);
                        String result = doc.text();
                        saint.setText(result);
                    }
                }, errorListener
        );
        if (saintOfDay != null) {
            Document doc = Jsoup.parse(saintOfDay);
            String result = doc.text();
            saint.setText(result);
        }
    }

    private void getGospelTitle (View layout, Response.ErrorListener errorListener) {
        final TextView gospel_title = (TextView) layout.findViewById(R.id.gospel_title_tv_fragment_gospel);
        String gospelTitle = volley.getGospelTitle(
                new Response.Listener<String>() {
                    @Override
                    public void onResponse (String response) {
                        Document doc = Jsoup.parse(response);
                        String result = doc.text();
                        gospel_title.setText(result);
                    }
                }, errorListener
        );
        if (gospelTitle != null) {
            Document doc = Jsoup.parse(gospelTitle);
            String result = doc.text();
            gospel_title.setText(result);
        }
    }

    private void getGospelText (View layout, Response.ErrorListener errorListener) {
        final TextView gospel = (TextView) layout.findViewById(R.id.gospel_text_tv_fragment_gospel);
        String gospelText = volley.getGospel(
                new Response.Listener<String>() {
                    @Override
                    public void onResponse (String response) {
                        Document doc = Jsoup.parse(response);
                        List<TextNode> nodes = doc.body().textNodes();
                        String result = "";
                        for (TextNode node : nodes) {
                            result += node.text() + "\n";
                        }
                        gospel.setText(result);
                    }
                }, errorListener
        );
        if (gospelText != null) {
            Document doc = Jsoup.parse(gospelText);
            List<TextNode> nodes = doc.body().textNodes();
            String result = "";
            for (TextNode node : nodes) {
                result += node.text() + "\n";
            }
            gospel.setText(result);
        }
    }

}
