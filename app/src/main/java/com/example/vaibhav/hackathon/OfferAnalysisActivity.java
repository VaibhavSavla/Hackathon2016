package com.example.vaibhav.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

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

import org.json.JSONObject;

import java.util.ArrayList;

public class OfferAnalysisActivity extends AppCompatActivity {

    public static final String TAG = OfferAnalysisActivity.class.getSimpleName();
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


                    PieChart chart = (PieChart) findViewById(R.id.chart_mpandroid);

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
                    chart.setNoDataText("");

                    dataset.setColors(ColorTemplate.COLORFUL_COLORS);


                    /*DecoView mDecoView = (DecoView) findViewById(R.id.dynamicArcView);

                    mDecoView.addSeries(new SeriesItem.Builder(Color.argb(255, 51, 51, 255))
                            .setRange(0, 100, 0)
                            .setSeriesLabel(new SeriesLabel.Builder("")
                                    .setColorBack(Color.argb(218, 0, 0, 0))
                                    .setColorText(Color.argb(255, 255, 255, 255))
                                    .build())
                            .setInitialVisibility(false)
                            .setLineWidth(32f)
                            .build());

                    //Create data series track
                    SeriesItem seriesItem1 = new SeriesItem.Builder(Color.argb(255, 25, 255, 25))
                            .setRange(0, positive, positive)
                            .setSeriesLabel(new SeriesLabel.Builder("Positive")
                                    .setColorBack(Color.argb(218, 0, 0, 0))
                                    .setColorText(Color.argb(255, 255, 255, 255))
                                    .build())
                            .setLineWidth(32f)
                            .build();

                    int series1Index = mDecoView.addSeries(seriesItem1);

                    SeriesItem seriesItem2 = new SeriesItem.Builder(Color.argb(255, 25, 255, 25))
                            .setRange(0, negative, negative)
                            .setSeriesLabel(new SeriesLabel.Builder("Negative")
                                    .setColorBack(Color.argb(218, 0, 0, 0))
                                    .setColorText(Color.argb(255, 255, 255, 255))
                                    .build())
                            .setLineWidth(32f)
                            .build();

                    int series2Index = mDecoView.addSeries(seriesItem2);

                    SeriesItem seriesItem3 = new SeriesItem.Builder(Color.argb(255, 25, 255, 25))
                            .setRange(0, neutral, neutral)
                            .setSeriesLabel(new SeriesLabel.Builder("Neutral")
                                    .setColorBack(Color.argb(218, 0, 0, 0))
                                    .setColorText(Color.argb(255, 255, 255, 255))
                                    .build())
                            .setLineWidth(32f)
                            .build();

                    int series3Index = mDecoView.addSeries(seriesItem3);*/

                } catch (Exception e) {
                    Log.e(TAG, "error" + e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }

}
