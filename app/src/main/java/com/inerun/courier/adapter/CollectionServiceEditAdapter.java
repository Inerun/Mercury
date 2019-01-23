package com.inerun.courier.adapter;

/**
 * Created by vinay on 18/01/17.
 */


import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inerun.courier.R;
import com.inerun.courier.data.ParcelListingData;
import com.inerun.courier.data.PickupParcelData;
import com.inerun.courier.data.Services;
import com.inerun.courier.helper.DIHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vineet on 11/29/2016.
 */


public class CollectionServiceEditAdapter extends BaseRecyclerViewAdapter_new implements CompoundButton.OnCheckedChangeListener {
    private Context context;
    private List<Services> dataList;
    private static OnItemClickListener mItemClickListener;
    private boolean onBind;


    public void setDataList(List<Services> dataList) {
        this.dataList = dataList;
    }

    public CollectionServiceEditAdapter(Context context) {
        this.dataList = new ArrayList<>();
        this.context = context;
    }


    @Override
    protected BaseRecyclerViewAdapter_new.ViewHolder getViewHolder(View itemView) {

        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    protected View oncreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_service_edit_item_row, parent, false);

        return itemView;
    }


    @Override
    public void onbindViewHolder(BaseRecyclerViewAdapter_new.ViewHolder viewholder, int position) {
        ViewHolder holder= (ViewHolder) viewholder.holder;
        Services data = dataList.get(position);

        holder.service_name.setText(data.getService_name());
        holder.est_date_value.setText(data.getEstimated_delivery_date());
        holder.time_value.setText(data.getTime());
        holder.rate_currency.setText(data.getRate_currency());
        holder.rate_value.setText(DIHelper.getValueInInteger(data.getRate()));
        holder.rate_value.setPaintFlags( holder.rate_value.getPaintFlags() |  Paint.STRIKE_THRU_TEXT_FLAG);

        if(data.getSpecial_rate() != null && data.getSpecial_rate().length() > 0) {

            holder.special_rate_currency.setText(data.getRate_currency());
//        holder.special_price.setText(data.getSpecial_rate());
            holder.special_price.setText(DIHelper.getValueInInteger(data.getSpecial_rate()));
            holder.rate_layout.setVisibility(View.VISIBLE);
            holder.divider.setVisibility(View.VISIBLE);
        }else{
            holder.rate_layout.setVisibility(View.GONE);
            holder.divider.setVisibility(View.GONE);

            holder.special_rate_currency.setText(data.getRate_currency());
//        holder.special_price.setText(data.getSpecial_rate());
            holder.special_price.setText(DIHelper.getValueInInteger(data.getRate()));

        }


        holder.checkbox.setChecked(data.isDefaultCheck());
        holder.checkbox.setTag(position);

        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                setCheck(pos);
            }
        });

        holder.item_rootLayout.setTag(position);
    }

    private void setCheck(int pos) {
        for (Services services: dataList){
            services.setDefaultCheck(false);
        }
        dataList.get(pos).setDefaultCheck(true);
        notifyDataSetChanged();
    }


//    @Override
//    public int getItemViewType(int position) {
//
//        if (parcelDataList.get(position).isDelivered()) {
//            return ParcelListingData.ParcelData.DELIVERED;
//        } else{
//            return ParcelListingData.ParcelData.PENDING;
//        }
//
//    }



    @Override
    public List initObjectList() {
        return  dataList;
    }

    public interface OnItemClickListener {
        public void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        CollectionServiceEditAdapter.mItemClickListener = mItemClickListener;
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
//        if (!onBind) {
//            int pos = (int) compoundButton.getTag();
//            if (validateCheck(parcelDataList.get(pos))) {
//                parcelDataList.get(pos).setIsselected(checked);
//            }
//
//
//            // your process when checkBox changed
//            // ...
//
//            notifyDataSetChanged();
//        }


    }

  /*  private boolean validateCheck(ParcelListingData.ParcelData checkparcel) {

        boolean flag = true;

        for (int i = 0; i < parcelDataList.size(); i++) {
            ParcelListingData.ParcelData parcel = parcelDataList.get(i);

            if (parcel.isselected() && (!parcel.getCustid().equalsIgnoreCase(checkparcel.getCustid()))) {

                flag = false;
                ((BaseActivity)context).showSnackbar(R.string.different_parcels_error);
            }
        }

        return flag;
    }*/

   /* public ArrayList<ParcelListingData.ParcelData> getSelectedParcels() {

        ArrayList<ParcelListingData.ParcelData> selectedParcelList= new ArrayList<>();

        for (int i = 0; i < parcelDataList.size(); i++) {
            ParcelListingData.ParcelData parcel = parcelDataList.get(i);
            if (parcel.isselected() ) {
                selectedParcelList.add(parcel);


            }
        }

        return selectedParcelList;
    }*/
   /* public void selectAllParcels(boolean checked) {

        for (int i = 0; i < parcelDataList.size(); i++) {
            if (!parcelDataList.get(i).isDelivered()) {
                parcelDataList.get(i).setIsselected(checked);
            }
        }
        notifyDataSetChanged();


    }*/

    public class ViewHolder extends BaseRecyclerViewAdapter_new.ViewHolder implements View.OnClickListener {
        public TextView service_name, time_value, est_date_value, rate_currency, rate_value, special_rate_currency, special_price;
        public CheckBox checkbox;
        public RelativeLayout item_rootLayout;
        public LinearLayout rate_layout;
        public View divider;

        public ViewHolder(View view) {
            super(view);


            service_name = (TextView) view.findViewById(R.id.service_name);
            est_date_value = (TextView) view.findViewById(R.id.est_date_value);
            time_value = (TextView) view.findViewById(R.id.time_value);
            rate_currency = (TextView) view.findViewById(R.id.rate_currency);

            rate_value = (TextView) view.findViewById(R.id.rate_value);
            special_rate_currency = (TextView) view.findViewById(R.id.special_rate_currency);
            special_price = (TextView) view.findViewById(R.id.special_price);
            checkbox = (CheckBox) view.findViewById(R.id.checkbox);

            divider = view.findViewById(R.id.rate_divider);
            rate_layout = view.findViewById(R.id.rate_layout);
            item_rootLayout = view.findViewById(R.id.item_rootLayout);

            item_rootLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // TODO Auto-generated method stub
            int pos= (int) view.getTag();
            Log.i("pos",""+pos);
            if(pos>=0) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(view, pos);
                }
            }
        }

    }


//    @Override
//    public int getItemviewType(int position) {
//
//        if (dataList.get(position).isReceived()) {
//            return ParcelListingData.ParcelData.PICKUP_RECEIVED;
//        } else{
//            return ParcelListingData.ParcelData.PICKUP_PENDING;
//        }
//    }


    public void addDataToList(Services data) {
        if(data != null) {
            dataList.add(0, data);
            notifyDataSetChanged();
        }
    }

    public void addDataListToList(List<Services> dataList) {
        if(dataList != null ) {
            this.dataList.clear();
            this.dataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    public List<Services> getDataList() {
        return dataList;
    }

}
