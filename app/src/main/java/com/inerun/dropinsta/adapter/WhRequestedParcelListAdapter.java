package com.inerun.dropinsta.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inerun.dropinsta.R;
import com.inerun.dropinsta.activity_warehouse.WhSearchParcelFragment;
import com.inerun.dropinsta.data.ParcelListingData;

import java.util.List;

/**
 * Created by vinay on 19/12/16.
 */


public class WhRequestedParcelListAdapter extends RecyclerView.Adapter<WhRequestedParcelListAdapter.ViewHolder> implements CompoundButton.OnCheckedChangeListener {

    private final Context context;
    private final WhSearchParcelFragment fragment;
    private List<ParcelListingData.ParcelData> parcelDataList;

    View.OnClickListener onclickListener;
    private boolean onBind;
//    boolean checkenabled=false;


    public WhRequestedParcelListAdapter(WhSearchParcelFragment fragment, Context context, List<ParcelListingData.ParcelData> parcelDataList, View.OnClickListener onclickListener) {
        this.parcelDataList = parcelDataList;
        this.onclickListener = onclickListener;
        this.context = context;
        this.fragment = fragment;
    }

    public WhRequestedParcelListAdapter(Context context, List<ParcelListingData.ParcelData> parcelDataList, View.OnClickListener onclickListener) {
        this.parcelDataList = parcelDataList;
        this.onclickListener = onclickListener;
        this.context = context;
        fragment = new WhSearchParcelFragment();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.wh_requested_parcel_list_item, parent, false);

        WhRequestedParcelListAdapter.ViewHolder viewHolder = new WhRequestedParcelListAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ParcelListingData.ParcelData parcelData = parcelDataList.get(position);
        holder.nametxt.setText((parcelData.getName()));
        holder.parcelnumtxt.setText((parcelData.getBarcode()));
        holder.racknumtxt.setText((parcelData.getRackno()));
        holder.binnumtxt.setText((parcelData.getBinno()));
        holder.parentView.setTag(position);
        if(parcelData.isselected()) {
            holder.search_num_radio.setChecked(true);
            holder.opacity_layout.setVisibility(View.VISIBLE);
        }else{
            holder.opacity_layout.setVisibility(View.GONE);
            holder.search_num_radio.setChecked(false);
        }
//        holder.search_num_radio.setTag(position);
//        if (!parcelData.isDelivered()) {
//            holder.search_num_radio.setVisibility(View.VISIBLE);
//        } else {
//            holder.search_num_radio.setVisibility(View.GONE);
//        }
//        onBind = true;
//        holder.search_num_radio.setChecked(parcelData.isselected());
//        onBind = false;


//        holder.search_num_radio.setOnCheckedChangeListener(this);


    }

    @Override
    public int getItemCount() {
        return parcelDataList.size();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        if (!onBind) {
            int pos = (int) compoundButton.getTag();
            parcelDataList.get(pos).setIsselected(checked);

            // your process when checkBox changed
            // ...

            notifyDataSetChanged();
        }


    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nametxt, parcelnumtxt, racknumtxt, binnumtxt;
        public View parentView;
        private LinearLayout opacity_layout;
        public CheckBox search_num_radio;


        public ViewHolder(View view) {
            super(view);
            nametxt = (TextView) view.findViewById(R.id.search_name);
            parentView = view;
            parcelnumtxt = (TextView) view.findViewById(R.id.search_num);
            racknumtxt = (TextView) view.findViewById(R.id.search_rack_num);
            binnumtxt = (TextView) view.findViewById(R.id.search_bin_num);
            opacity_layout = (LinearLayout) view.findViewById(R.id.opacity_layout);
            search_num_radio = (CheckBox) view.findViewById(R.id.search_num_cb);
//            search_num_radio.setOnCheckedChangeListener(WhReadyParcelAdapter.this);

            view.setOnClickListener(onclickListener);

        }


    }


    public void selectAllParcels(boolean checked) {

        for (int i = 0; i < parcelDataList.size(); i++) {
            if (!parcelDataList.get(i).isDelivered()) {
                parcelDataList.get(i).setIsselected(checked);
            }
        }
        notifyDataSetChanged();


    }
//    public boolean isCheckenabled() {
//        return checkenabled;
//    }
//
//    public void setCheckenabled(boolean checkenabled) {
//        this.checkenabled = checkenabled;
//    }

    public List<ParcelListingData.ParcelData> getParcelDataList() {

        return parcelDataList;
    }


}
