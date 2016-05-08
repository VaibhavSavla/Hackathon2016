package com.example.vaibhav.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.vaibhav.hackathon.utils.Constants;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OfferAnalysisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_analysis);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        int offerId = intent.getIntExtra(Constants.EXTRA_OFFER, 0);

        String url = "http://52.35.99.222:8000/api/v1/offerdata/" + offerId;

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(url, null
                , new Response.Listener<JSONObject>() {
            @Override public void onResponse(JSONObject response) {
                try {
                    float positive = (float) response.getDouble("positive");
                    float negative = (float) response.getDouble("negative");
                    float neutral = (float) response.getDouble("neutral");

                    PieChart chart = (PieChart) findViewById(R.id.chart);

                    ArrayList<Entry> entries = new ArrayList<>();
                    entries.add(new Entry(positive, 0));
                    entries.add(new Entry(negative, 1));
                    entries.add(new Entry(neutral, 2));

                    PieDataSet dataset = new PieDataSet(entries, "Sentiments");

                    ArrayList<String> labels = new ArrayList<String>();
                    labels.add("Positive");
                    labels.add("Negative");
                    labels.add("Neutral");

                    PieData data = new PieData(labels, dataset); // initialize Piedata
                    chart.setData(data);

                    dataset.setColors(ColorTemplate.COLORFUL_COLORS);
                } catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }

}
