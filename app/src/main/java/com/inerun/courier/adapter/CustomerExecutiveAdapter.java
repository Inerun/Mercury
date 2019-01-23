package com.inerun.courier.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.inerun.courier.data.CustomerExecutiveData;


import java.util.ArrayList;

/**
 * Created by vineet on 8/24/2016.
 */
public class CustomerExecutiveAdapter extends ArrayAdapter<CustomerExecutiveData> {


    private Context context;

    private ArrayList<CustomerExecutiveData> countryDataArrayList;

    public CustomerExecutiveAdapter(Context context, int textViewResourceId, ArrayList<CustomerExecutiveData> countryDataArrayList) {
        super(context, textViewResourceId, countryDataArrayList);
        this.context = context;
        this.countryDataArrayList = countryDataArrayList;
    }

    public int getCount() {
        return countryDataArrayList.size() - 1;
    }

    public CustomerExecutiveData getItem(int position) {
        return countryDataArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = super.getView(position, convertView, parent);
        if (position == getCount()) {
            ((TextView) v.findViewById(android.R.id.text1)).setText("");
            ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount()).getUsername()); //"Hint to be displayed"

        } else {

            ((TextView) v.findViewById(android.R.id.text1)).setText(countryDataArrayList.get(position).getUsername());
        }

        ((TextView) v.findViewById(android.R.id.text1)).setTextSize(18);
        return v;


//        TextView label = new TextView(context);
//        label.setText(countryDataArrayList.get(position).getLable());
//        label.setTextSize(18);
//        return label;


//        View v = super.getView(position, convertView, parent);
//        TextView label = (TextView) v.findViewById(android.R.id.text1);
////        label.setTextColor(context.getResources().getColor(R.color.button_color));
//        label.setText(countryDataArrayList.get(position).getLable());
//        label.setTextSize(18);
//        return v;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        View v = super.getView(position, convertView, parent);

        ((TextView) v.findViewById(android.R.id.text1)).setTextSize(18);
        ((TextView) v.findViewById(android.R.id.text1)).setText(countryDataArrayList.get(position).getUsername());

        return v;

//            TextView label = new TextView(context);
//            label.setTextSize(18);
//            label.setText(countryDataArrayList.get(position).getLable());
//            return label;

    }


}
