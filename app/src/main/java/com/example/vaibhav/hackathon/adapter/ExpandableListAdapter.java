package com.example.vaibhav.hackathon.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.vaibhav.hackathon.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Vaibhav Savla on 07/05/16.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    Context mContext;
    ArrayList<String> dataHeader;
    HashMap<String, ArrayList<String>> listDataChild;

    public ExpandableListAdapter(Context mContext, ArrayList<String> dataHeader, HashMap<String, ArrayList<String>> listDataChild) {
        this.mContext = mContext;
        this.dataHeader = dataHeader;
        this.listDataChild = listDataChild;
    }

    @Override public int getGroupCount() {
        return listDataChild.size();
    }

    @Override public int getChildrenCount(int groupPosition) {
        return listDataChild.get(dataHeader.get(groupPosition)).size();
    }

    @Override public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.category_list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.textview_list_group);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override public Object getGroup(int groupPosition) {
        return dataHeader.get(groupPosition);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.category_list_item, null);
        }

        final CheckBox checkBoxListChild = (CheckBox) convertView
                .findViewById(R.id.checkbox_brand_item);

        checkBoxListChild.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBoxListChild.isChecked()) {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean(childText, true);
                    editor.commit();
                } else {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean(childText, false);
                    editor.commit();
                }
            }
        });

        checkBoxListChild.setText(childText);
        return convertView;
    }

    @Override public Object getChild(int groupPosition, int childPosition) {
        return listDataChild.get(dataHeader.get(groupPosition))
                .get(childPosition);
    }

    @Override public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
