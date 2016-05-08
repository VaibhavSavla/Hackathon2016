package com.example.vaibhav.hackathon;


import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
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
import com.example.vaibhav.hackathon.model.Store;
import com.example.vaibhav.hackathon.utils.Constants;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * A simple {@link Fragment} subclass.
 */
public class PopularOffersFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    public static final String TAG = PopularOffersFragment.class.getSimpleName();
    ListView mOffersListView;
    OfferListAdapter mOfferListAdapter;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    private ArrayList<Offer> offers;


    public PopularOffersFragment() {
        setHasOptionsMenu(true);
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_customer_home, menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_find_similar:
                OfferSearchDialog dialog = new OfferSearchDialog(getActivity());
                dialog.show(getFragmentManager(), "OfferSearchDialog");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_home, container, false);

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mOffersListView = (ListView) view.findViewById(R.id.listview_offers);
        mOffersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), OfferDetailActivity.class);
                Offer offer = offers.get(position);
                intent.putExtra(Constants.EXTRA_DESCRIPTION, offer.offerDescription);
                intent.putExtra(Constants.EXTRA_TERMS, offer.offerTerms);
                intent.putExtra(Constants.EXTRA_LAT, offer.store.latitude);
                intent.putExtra(Constants.EXTRA_LON, offer.store.longitude);
                StringBuilder builder = new StringBuilder();
                for (String review : offer.reviews) {
                    builder.append(review).append("\n\n");
                }
                intent.putExtra(Constants.EXTRA_REVIEW, builder.toString());
                startActivity(intent);
            }
        });

        return view;
    }

    @Override public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            String lat = String.valueOf(mLastLocation.getLatitude());
            String lon = String.valueOf(mLastLocation.getLongitude());

            String url = "http://52.35.99.222:8000/api/v1/nearestoffer?lat=" + lat + "&lon=" + lon;
            Log.d(TAG, url);

            RequestQueue queue = Volley.newRequestQueue(getActivity());

            final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null
                    , new Response.Listener<JSONArray>() {
                @Override public void onResponse(JSONArray response) {
                    offers = new ArrayList<>();
                    Log.d(TAG, response.toString());
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject offer = (JSONObject) response.get(i);
                            JSONObject shop = offer.getJSONObject("shop");
                            int storeId = shop.getInt("id");
                            String brandName = shop.getString("name");
                            double lat = shop.getDouble("latitude");
                            double lon = shop.getDouble("longitude");
                            int catId = shop.getInt("category");
                            String category = "";
                            switch (catId) {
                                case 0:
                                    category = "Food";
                                    break;
                                case 1:
                                    category = "LifeStyle";
                                    break;
                                case 2:
                                    category = "Technology";
                                    break;
                            }
                            Store store = new Store(storeId, brandName, lat, lon, category);

                            String offerTitle = offer.getString("name");
                            String description = offer.getString("description");
                            String tnc = offer.getString("tnc");
                            String date = offer.getString("created");
                            double distance = offer.getDouble("distace");
                            int count = offer.getInt("count");
                            offers.add(new Offer(catId, store, offerTitle, description, tnc, new ArrayList<String>(), date, count, distance));

                            Log.d(TAG, offer.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Collections.sort(offers, new Comparator<Offer>() {
                            @Override public int compare(Offer lhs, Offer rhs) {
                                return rhs.count - lhs.count;
                            }
                        });
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
        }
    }

    @Override public void onConnectionSuspended(int i) {

    }

    @Override public void onConnectionFailed(ConnectionResult connectionResult) {

    }


}
