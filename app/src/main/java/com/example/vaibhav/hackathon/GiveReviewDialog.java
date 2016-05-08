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
 * Created by Vaibhav Savla on 08/05/16.
 */
public class GiveReviewDialog extends DialogFragment {

    EditText mGiveReview;
    Context mContext;

    public GiveReviewDialog(Context context) {
        mContext = context;
    }

    @NonNull @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_give_review, null);
        mGiveReview = (EditText) view.findViewById(R.id.edit_text_give_review);
        builder.setView(view)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(mContext, mGiveReview.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        GiveReviewDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
