package com.example.vaibhav.hackathon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.vaibhav.hackathon.utils.Constants;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddOfferActivityFragment extends Fragment {

    public static final String TAG = AddOfferActivityFragment.class.getSimpleName();
    @BindView(R.id.button_pick_offer_expiry) Button mButtonPickExpiry;
    @BindView(R.id.editText_title) EditText mEditTitle;
    @BindView(R.id.editText_description) EditText mEditDescription;
    @BindView(R.id.edit_text_terms) EditText mEditTerms;

    String formattedExpiryDate;

    public AddOfferActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_offer, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.button_pick_offer_expiry)
    public void onClick() {
        ExpiryDatePickerFragment datePicker = new ExpiryDatePickerFragment();
        datePicker.setTargetFragment(AddOfferActivityFragment.this, 1);
        datePicker.show(getFragmentManager(), "DatePicker");
    }

    @OnClick(R.id.fab_add_offer_done)
    public void onAddOffer() {
        String offerTitle = mEditTitle.getText().toString();
        String offerDescription = mEditDescription.getText().toString();
        String offerTerms = mEditTerms.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        HashMap<String, Object> params = new HashMap<>();
        params.put("name", offerTitle);
        params.put("expiration", "2016-05-08T00:00:00Z");
        params.put("count", 0);
        params.put("description", offerDescription);
        params.put("category", getActivity().getIntent().getIntExtra(Constants.EXTRA_CATEGORY_ID, 0));
        params.put("tnc", offerTerms);
        params.put("shop", getActivity().getIntent().getIntExtra(Constants.EXTRA_SHOP_ID, 0));

        String url = "http://52.35.99.222:8000/api/v1/offer/";
        Log.d(TAG, "json request body - " + new JSONObject(params));
        JsonObjectRequest request = new JsonObjectRequest(url, new JSONObject(params)
                , new Response.Listener<JSONObject>() {
            @Override public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
            }
        }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "failed to add request");
            }
        });

        queue.add(request);

        Intent intent = new Intent(getActivity(), SellerOffersActivity.class);
        startActivity(intent);

    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    formattedExpiryDate = bundle.getString("selectedDate", "error");
                }
        }
    }
}
