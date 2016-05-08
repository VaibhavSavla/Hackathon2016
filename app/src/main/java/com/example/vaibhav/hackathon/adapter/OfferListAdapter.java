package com.example.vaibhav.hackathon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.vaibhav.hackathon.R;
import com.example.vaibhav.hackathon.model.Offer;

import java.util.ArrayList;

/**
 * Created by Vaibhav Savla on 07/05/16.
 */
public class OfferListAdapter extends ArrayAdapter {

    private Context mContext;
    private ArrayList<Offer> offers;

    public OfferListAdapter(Context context, int resource, ArrayList<Offer> offers) {
        super(context, resource);
        this.mContext = context;
        this.offers = offers;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.offer_list_item, null);
        }

        Offer offer = offers.get(position);

        TextView brandTitle = (TextView) convertView.findViewById(R.id.textView_brand);
        if (offer.store != null) {
            brandTitle.setText(offer.store.brandName);
        }

        TextView offerTitle = (TextView) convertView.findViewById(R.id.textView_offer_title);
        offerTitle.setText(offer.offerTitle);

        TextView expiryDate = (TextView) convertView.findViewById(R.id.textView_expiry_date);
        expiryDate.setText("Valid till " + offer.expiryDate);

        TextView distance = (TextView) convertView.findViewById(R.id.textView_distance);
        if (offer.store != null) {
            String formattedDist = String.format("%.2f km", offer.distance);
            distance.setText(formattedDist);
        }

        return convertView;
    }

    @Override public int getCount() {
        return offers.size();
    }
}
