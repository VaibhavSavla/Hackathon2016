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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

/**
 * A placeholder fragment containing a simple view.
 */
public class CustomerProfileActivityFragment extends Fragment {

    public static final String TAG = CustomerProfileActivityFragment.class.getSimpleName();
    @BindView(R.id.editText_name) EditText mEditName;
    @BindView(R.id.editText_age) EditText mEditAge;
    @BindView(R.id.radioGroup_sex) RadioGroup mGenderGroup;
    FloatingActionButton mButtonNext;

    public CustomerProfileActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_customer_profile, container, false);
        ButterKnife.bind(this, view);

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String displayName = preferences.getString(Constants.PREF_DISPLAY_NAME, "");
        mEditName.setText(displayName);

        mButtonNext = (FloatingActionButton) view.findViewById(R.id.button_next);
        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(Constants.PREF_DISPLAY_NAME, mEditName.getText().toString());
                editor.putInt(Constants.PREF_AGE, Integer.parseInt(mEditAge.getText().toString()));
                int id = mGenderGroup.getCheckedRadioButtonId();
                RadioButton selectedGender = (RadioButton) view.findViewById(id);
                editor.putString(Constants.PREF_GENDER, selectedGender.getText().toString());
                editor.commit();

                RequestQueue queue = Volley.newRequestQueue(getActivity());

                String name = mEditName.getText().toString();
                String email = preferences.getString(Constants.PREF_EMAIL, "");
                int age = preferences.getInt(Constants.PREF_AGE, 0);
                String gender = preferences.getString(Constants.PREF_GENDER, "");

                HashMap<String, Object> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("age", age);
                params.put("gender", gender);

                String url = "http://52.35.99.222:8000/api/v1/register/";

                JsonObjectRequest request = new JsonObjectRequest(url, new JSONObject(params)
                        , new Response.Listener<JSONObject>() {
                    @Override public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override public void onErrorResponse(VolleyError error) {

                    }
                });

                queue.add(request);

                Intent intent = new Intent(getActivity(), CategoryChooserActivity.class);
                startActivity(intent);

            }
        });
        return view;
    }


}
