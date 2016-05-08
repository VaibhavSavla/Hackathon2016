package com.example.vaibhav.hackathon;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Vaibhav Savla on 07/05/16.
 */
public class OfferSearchDialog extends DialogFragment {

    EditText mSearchOffer;
    Context mContext;

    public OfferSearchDialog(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_search_offer, null);
        mSearchOffer = (EditText) view.findViewById(R.id.edit_text_search_offer);
        builder.setView(view)
                .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(mContext, mSearchOffer.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        OfferSearchDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
