package com.example.vaibhav.hackathon;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.vaibhav.hackathon.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class OfferDetailActivityFragment extends Fragment {

    @BindView(R.id.textView_description) TextView mTextViewDescription;
    @BindView(R.id.textView_terms) TextView mTextViewTerms;
    @BindView(R.id.textView_review) TextView mTextViewReviews;
    @BindView(R.id.button_give_review) Button mButtonGiveReview;
    @BindView(R.id.button_claim) Button mButtonClaim;

    double lat;
    double lon;

    public OfferDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();

        View view = inflater.inflate(R.layout.fragment_offer_detail, container, false);
        ButterKnife.bind(this, view);

        mTextViewDescription.setText(intent.getStringExtra(Constants.EXTRA_DESCRIPTION));
        mTextViewTerms.setText(intent.getStringExtra(Constants.EXTRA_TERMS));
        mTextViewReviews.setText(intent.getStringExtra(Constants.EXTRA_REVIEW));

        lat = intent.getDoubleExtra(Constants.EXTRA_LAT, 0);
        lon = intent.getDoubleExtra(Constants.EXTRA_LON, 0);

        return view;
    }

    @OnClick(R.id.button_claim)
    public void onOfferClaimed() {
        mButtonClaim.setVisibility(View.GONE);
        mButtonGiveReview.setVisibility(View.VISIBLE);

        //TODO increase offer count
    }

    @OnClick(R.id.button_map)
    public void onMapClicked() {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + lat + "," + lon + "\n");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    @OnClick(R.id.button_give_review)
    public void onGiveReview() {
        GiveReviewDialog dialog = new GiveReviewDialog(getActivity());
        dialog.show(getFragmentManager(), "GiveReview");
    }
}
