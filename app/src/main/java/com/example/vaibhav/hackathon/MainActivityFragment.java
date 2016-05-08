package com.example.vaibhav.hackathon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.vaibhav.hackathon.utils.Constants;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public static final String TAG = MainActivityFragment.class.getSimpleName();
    int RC_SIGN_IN = 0;

    GoogleApiClient mGoogleApiClient;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String role = sharedPreferences.getString(Constants.PREF_ROLE, "");

        if (!role.equals("")) {
            if (role.equals("customer")) {
                Intent intent = new Intent(getActivity(), CustomerHomeActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getActivity(), SellerOffersActivity.class);
                startActivity(intent);
            }
        }
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity() /* FragmentActivity */,
                        new GoogleApiClient.OnConnectionFailedListener() {
                            @Override
                            public void onConnectionFailed(ConnectionResult connectionResult) {
                                Log.e(TAG, "Connection Failed");
                            }
                        }/* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        return view;
    }

    @OnClick(R.id.sign_in_button)
    public void onSignInClicked() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Constants.PREF_DISPLAY_NAME, acct.getDisplayName());
            editor.putString(Constants.PREF_EMAIL, acct.getEmail());
            editor.commit();
            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }

    private void updateUI(boolean status) {
        if (status) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String role = preferences.getString(Constants.PREF_ROLE, "");
            Intent intent;
            if (role.equals(""))
                intent = new Intent(getActivity(), ChooseRoleActivity.class);
            else {
                if (role.equals("customer"))
                    intent = new Intent(getActivity(), CustomerHomeActivity.class);
                else
                    intent = new Intent(getActivity(), SellerOffersActivity.class);
            }
            startActivity(intent);
        } else
            Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_SHORT).show();
    }

}
