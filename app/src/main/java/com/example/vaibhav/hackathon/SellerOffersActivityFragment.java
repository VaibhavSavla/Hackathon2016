package com.example.vaibhav.hackathon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.vaibhav.hackathon.adapter.OfferListAdapter;
import com.example.vaibhav.hackathon.model.Offer;
import com.example.vaibhav.hackathon.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class SellerOffersActivityFragment extends Fragment {

    public static final String TAG = SellerOffersActivityFragment.class.getSimpleName();

    ListView mOffersListView;
    OfferListAdapter mOfferListAdapter;

    int shopId;
    int categoryId;

    private ArrayList<Offer> offers;

    public SellerOffersActivityFragment() {
        setHasOptionsMenu(true);
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_seller_offers, menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_offer) {
            Intent intent = new Intent(getActivity(), AddOfferActivity.class);
            intent.putExtra(Constants.EXTRA_CATEGORY_ID, categoryId);
            intent.putExtra(Constants.EXTRA_SHOP_ID, shopId);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_offers, container, false);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String email = preferences.getString(Constants.PREF_EMAIL, "");
        String url = "http://52.35.99.222:8000/api/v1/offer/?email=" + email;

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null
                , new Response.Listener<JSONArray>() {
            @Override public void onResponse(JSONArray response) {
                offers = new ArrayList<>();
                Log.d(TAG, response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject offer = (JSONObject) response.get(i);
                        if (i == 0) {
                            shopId = offer.getInt("shop");
                            categoryId = offer.getInt("category");
                        }
                        int offerId = offer.getInt("id");
                        String offerTitle = offer.getString("name");
                        String description = offer.getString("description");
                        String tnc = offer.getString("tnc");
                        String date = offer.getString("created");
                        int count = offer.getInt("count");
                        offers.add(new Offer(offerId, null, offerTitle, description, tnc, new ArrayList<String>(), date, count, 0.0));

                        Log.d(TAG, offer.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mOfferListAdapter = new OfferListAdapter(getActivity(), R.layout.offer_list_item, offers);
                    mOffersListView.setAdapter(mOfferListAdapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Volley Error " + error);
            }
        });

        queue.add(request);

        //mOffersListView.setAdapter(mOfferListAdapter);
        mOffersListView = (ListView) view.findViewById(R.id.listview_offers_store);
        mOffersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO sentiment analysis here
                Intent intent = new Intent(getActivity(), OfferAnalysisActivity.class);
                intent.putExtra(Constants.EXTRA_OFFER, offers.get(position).id);
//                Offer offer = offers.get(position);
//                intent.putExtra(Constants.EXTRA_DESCRIPTION, offer.offerDescription);
//                intent.putExtra(Constants.EXTRA_TERMS, offer.offerTerms);
//                StringBuilder builder = new StringBuilder();
//                for (String review: offer.reviews) {
//                    builder.append(review).append("\n\n");
//                }
//                intent.putExtra(Constants.EXTRA_REVIEW, builder.toString());
                startActivity(intent);
            }
        });

        return view;
    }
}