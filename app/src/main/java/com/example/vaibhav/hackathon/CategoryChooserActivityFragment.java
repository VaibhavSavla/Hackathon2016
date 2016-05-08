package com.example.vaibhav.hackathon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.vaibhav.hackathon.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class CategoryChooserActivityFragment extends Fragment {

    @BindView(R.id.checkbox_food) CheckBox mCheckBoxFood;
    @BindView(R.id.checkbox_lifestyle) CheckBox mCheckBoxLifestyle;
    @BindView(R.id.checkbox_tech) CheckBox mCheckBoxTech;

    public CategoryChooserActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_chooser, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.button_next_brands)
    public void onNextClicked() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean(Constants.PREF_FOOD_CHECKED, mCheckBoxFood.isChecked());
        editor.putBoolean(Constants.PREF_LIFESTYLE_CHECKED, mCheckBoxLifestyle.isChecked());
        editor.putBoolean(Constants.PREF_TECH_CHECKED, mCheckBoxTech.isChecked());
        editor.commit();

        Intent intent = new Intent(getActivity(), BrandChooserActivity.class);
        startActivity(intent);
    }
}
