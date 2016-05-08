package com.example.vaibhav.hackathon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.vaibhav.hackathon.adapter.ExpandableListAdapter;
import com.example.vaibhav.hackathon.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A placeholder fragment containing a simple view.
 */
public class BrandChooserActivityFragment extends Fragment {

    public static final String TAG = BrandChooserActivityFragment.class.getSimpleName();
    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    ArrayList<String> dataHeader;
    HashMap<String, ArrayList<String>> listDataChild;
    FloatingActionButton fabNextHomepage;

    public BrandChooserActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brand_chooser, container, false);
        prepareList();
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandable_category);
        expandableListAdapter = new ExpandableListAdapter(getActivity(), dataHeader, listDataChild);
        expandableListView.setAdapter(expandableListAdapter);

        fabNextHomepage = (FloatingActionButton) view.findViewById(R.id.fab_next_homepage);
        fabNextHomepage.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CustomerHomeActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void prepareList() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final boolean food = preferences.getBoolean(Constants.PREF_FOOD_CHECKED, false);
        final boolean lifestyle = preferences.getBoolean(Constants.PREF_LIFESTYLE_CHECKED, false);
        final boolean tech = preferences.getBoolean(Constants.PREF_TECH_CHECKED, false);

        dataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        if (food) {
            dataHeader.add("Food");
            listDataChild.put("Food", new ArrayList<String>());
        }
        if (lifestyle) {
            dataHeader.add("LifeStyle");
            listDataChild.put("LifeStyle", new ArrayList<String>());

        }
        if (tech) {
            dataHeader.add("Technology");
            listDataChild.put("Technology", new ArrayList<String>());

        }


        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "http://52.35.99.222:8000/api/v1/shop/";

        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null
                , new Response.Listener<JSONArray>() {
            @Override public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject offer = (JSONObject) response.get(i);
                        if (food && offer.getInt("category") == 0) {
                            listDataChild.get("Food").add(offer.getString("name"));
                        } else if (lifestyle && offer.getInt("category") == 1) {
                            listDataChild.get("LifeStyle").add(offer.getString("name"));
                        } else if (tech && offer.getInt("category") == 2) {
                            listDataChild.get("Technology").add(offer.getString("name"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Volley Error " + error);
            }
        });


        queue.add(request);

//        if (food) {
//            dataHeader.add("Food");
//            ArrayList<String> foodBrands = new ArrayList<>();
//            foodBrands.add("Dominoes");
//            foodBrands.add("KFC");
//            listDataChild.put("Food", foodBrands);
//        }
//
//        if (lifestyle) {
//            dataHeader.add("LifeStyle");
//            ArrayList<String> lifestyleBrands = new ArrayList<>();
//            lifestyleBrands.add("Allen Solley");
//            lifestyleBrands.add("Myntra");
//            listDataChild.put("LifeStyle", lifestyleBrands);
//        }
//
//        if (tech) {
//            dataHeader.add("Technology");
//            ArrayList<String> foodBrands = new ArrayList<>();
//            foodBrands.add("Vijay Sales");
//            foodBrands.add("Chroma");
//            listDataChild.put("Technology", foodBrands);
//        }
    }
}
