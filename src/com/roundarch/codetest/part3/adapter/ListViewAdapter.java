package com.roundarch.codetest.part3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.roundarch.codetest.R;
import com.roundarch.codetest.part3.data.model.Location;

import java.util.ArrayList;

/**
 * Created by TAE on 25-Feb-18.
 */

public class ListViewAdapter extends ArrayAdapter<Location> {
    private TextView mCity, mZipclass;

    public ListViewAdapter(Context context, ArrayList<Location> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Location user = getItem(position);



        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.part3_listview_item,
                    parent, false);
        }
         mCity = convertView.findViewById(R.id.mCity);
         mZipclass = convertView.findViewById(R.id.textView);
         mZipclass.setText("zip class: " + user.getZipClass() +
                "\nlong:" + user.getLongitude());
         mCity.setText("City: " + user.getCity() + "\nCountry: " + user.getCounty());

        return convertView;
    }
}