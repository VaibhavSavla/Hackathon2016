package com.example.vaibhav.hackathon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vaibhav.hackathon.utils.Constants;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChooseRoleActivityFragment extends Fragment {

    public ChooseRoleActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_role, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.button_customer)
    public void onCustomerClicked() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.PREF_ROLE, "customer");
        editor.commit();
        Intent intent = new Intent(getActivity(), CustomerProfileActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_store)
    public void onStoreClicked() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.PREF_ROLE, "store");
        editor.commit();

        Intent intent = new Intent(getActivity(), SellerOffersActivity.class);
        startActivity(intent);
    }
}
