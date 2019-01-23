package com.inerun.courier.adapter;

/**
 * Created by vinay on 18/01/17.
 */


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inerun.courier.R;
import com.inerun.courier.base.ClickListener;
import com.inerun.courier.data.PickupParcelData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vineet on 11/29/2016.
 */


public class CollectionAcceptListingAdapter extends BaseRecyclerViewAdapter_new implements CompoundButton.OnCheckedChangeListener {
    private Context context;
    private List<PickupParcelData> dataList;
    private static OnItemClickListener mItemClickListener;
    private boolean onBind;


    public CollectionAcceptListingAdapter(Context context) {
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
        View itemView;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_accept_list_item, parent, false);
        return itemView;
    }


    @Override
    public void onbindViewHolder(BaseRecyclerViewAdapter_new.ViewHolder viewholder, int position) {
        final ViewHolder holder= (ViewHolder) viewholder.holder;
        PickupParcelData data = dataList.get(position);

//        holder.customerName.setText(context.getString(R.string.parcel_customer_name,data.getName()));
        holder.parcel_id.setText(data.getParcel_barcode());
//        holder.parcel_id.setText(DIHelper.getBoldLabel(data.getParcel_barcode()));
        holder.name.setText(data.getName());
        holder.phone.setText(data.getPhone());
        holder.weight.setText(data.getPickParcelDetailData().getParcel_volum_weight());
        holder.price.setText(data.getPickParcelDetailData().getParcel_price());
        holder.assignedby.setText("Suresh Singh");

        holder.address.setText(data.getDeliveryAddress());
        Log.i("Adddress",data.getDeliveryAddress());


//        holder.parcel_id.setText(DIHelper.getBoldLabel("ID:",data.getParcel_barcode()));
//        holder.name.setText(DIHelper.getBoldLabel("Name:",data.getName()));
//        holder.phone.setText(data.getPhone());
//        holder.weight.setText(DIHelper.getBoldLabel("Weight:",data.getPickParcelDetailData().getParcel_volum_weight()));
//        holder.price.setText(DIHelper.getBoldLabel("Price:",data.getPickParcelDetailData().getParcel_price()));
//
//        holder.address.setText(DIHelper.getBoldLabel("Address:",data.getDeliveryAddress()));



//        holder.checkBox.setChecked(data.isSelected());
        holder.item_rootLayout.setTag(position);
        holder.callbtn.setTag(position);
        holder.checkBox.setTag(position);

        holder.checkBox.setOnClickListener(new ClickListener(context) {
            @Override
            public void click(View v)throws Exception {
                int pos = (int) v.getTag();
                Log.i("checkBox","TransferAdater");
                boolean isSelected = dataList.get(pos).isSelected();
                if(isSelected){
                    holder.checkBox.setChecked(false);
                    dataList.get(pos).setSelected(false);
                }else {
                    holder.checkBox.setChecked(true);
                    dataList.get(pos).setSelected(true);
                }
//                notifyDataSetChanged();
            }
        });
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
        CollectionAcceptListingAdapter.mItemClickListener = mItemClickListener;
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
        public TextView parcel_id, name, phone, address, weight, price, assignedby;
        public ImageButton callbtn;
        public LinearLayout item_rootLayout;
        public CheckBox checkBox;

        public ViewHolder(View view) {
            super(view);


            parcel_id = (TextView) view.findViewById(R.id.pickup_price_id);
            name = (TextView) view.findViewById(R.id.pickup_name);

            address = (TextView) view.findViewById(R.id.pickup_address);

            phone = (TextView) view.findViewById(R.id.pickup_phone);
            weight = (TextView) view.findViewById(R.id.pickup_weight);
            price = (TextView) view.findViewById(R.id.pickup_price);
            assignedby = (TextView) view.findViewById(R.id.assignedby_value);
            callbtn = (ImageButton) view.findViewById(R.id.call_action);
            item_rootLayout = (LinearLayout) view.findViewById(R.id.item_rootLayout);
            checkBox = (CheckBox) view.findViewById(R.id.checkbox);

            item_rootLayout.setOnClickListener(this);
            callbtn.setOnClickListener(this);
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

    public void addDataToList(PickupParcelData data) {
        if(data != null) {
            dataList.add(0, data);
            notifyDataSetChanged();
        }
    }

    public void addDataListToList(List<PickupParcelData> dataList) {
        if(dataList != null && dataList.size() > 0) {
            this.dataList.clear();
            this.dataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    public List<PickupParcelData> getDataList() {
        return dataList;
    }
}
