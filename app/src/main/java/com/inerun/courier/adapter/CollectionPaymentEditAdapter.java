package com.inerun.courier.adapter;

/**
 * Created by vinay on 18/01/17.
 */


import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inerun.courier.R;
import com.inerun.courier.constant.AppConstant;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.data.Payment;
import com.inerun.courier.data.Services;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vineet on 11/29/2016.
 */


public class CollectionPaymentEditAdapter extends BaseRecyclerViewAdapter_new implements CompoundButton.OnCheckedChangeListener {
    private Context context;
    private List<Payment> dataList;
    private static OnItemClickListener mItemClickListener;
    private boolean onBind;
    private int selectPosition = -1;
;

    public void setDataList(List<Payment> dataList) {
        this.dataList = dataList;
    }

    public CollectionPaymentEditAdapter(Context context) {
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


        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_payment_edit_item_row, parent, false);

        return itemView;
    }


    @Override
    public void onbindViewHolder(BaseRecyclerViewAdapter_new.ViewHolder viewholder, int position) {
        ViewHolder holder= (ViewHolder) viewholder.holder;
        Payment data = dataList.get(position);

        if(data.getPayment_type()== AppConstant.StatusKeys.PAYMENT_CARD){
            holder.payment_image.setImageResource(R.mipmap.card);
            holder.payment_name.setText(UrlConstants.CARD);
        }else if(data.getPayment_type()== AppConstant.StatusKeys.PAYMENT_CASH){
            holder.payment_image.setImageResource(R.mipmap.cash);
            holder.payment_name.setText(UrlConstants.CASH);
        }else if(data.getPayment_type()== AppConstant.StatusKeys.PAYMENT_ON_ACCOUNT){
            holder.payment_image.setImageResource(R.mipmap.on_account);
            holder.payment_name.setText(UrlConstants.ON_ACCOUNT);
        }else{
            holder.payment_image.setImageResource(R.mipmap.cod);
            holder.payment_name.setText(UrlConstants.COD);
        }

        holder.checkbox.setChecked(data.isSelect());
        holder.checkbox.setTextIsSelectable(false);
        if(data.isSelect()){
            holder.payment_name.setTextColor(context.getResources().getColor(R.color.blue_700));
//            holder.payment_image.setColorFilter(ContextCompat.getColor(context, R.color.blue_700), android.graphics.PorterDuff.Mode.MULTIPLY);
//            holder.payment_image.setColorFilter(Color.argb(255, 0, 0, 255));
            holder.payment_image.setColorFilter(Color.argb(255, 25, 118, 210));
        }else{
            holder.payment_name.setTextColor(context.getResources().getColor(R.color.black));
            holder.payment_image.setColorFilter(ContextCompat.getColor(context, R.color.black), android.graphics.PorterDuff.Mode.MULTIPLY);
        }
//        holder.checkbox.setTag(position);
//
//        holder.checkbox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int pos = (int) v.getTag();
//                setCheck(pos);
//            }
//        });

        holder.item_rootLayout.setTag(position);

        holder.item_rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                setCheck(pos);
                selectPosition = pos;
            }
        });
    }

    private void setCheck(int pos) {
        for (Payment payment: dataList){
            payment.setSelect(false);
        }
        dataList.get(pos).setSelect(true);
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
        CollectionPaymentEditAdapter.mItemClickListener = mItemClickListener;
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
        public TextView payment_name;
        public CheckBox checkbox;
        public RelativeLayout item_rootLayout;
        private ImageView payment_image;

        public ViewHolder(View view) {
            super(view);


            payment_name = (TextView) view.findViewById(R.id.payment_name);

            checkbox = (CheckBox) view.findViewById(R.id.checkbox);
            payment_image = (ImageView) view.findViewById(R.id.payment_image);

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


    public void addDataToList(Payment data) {
        if(data != null) {
            dataList.add(0, data);
            notifyDataSetChanged();
        }
    }

    public void addDataListToList(List<Payment> dataList) {
        if(dataList != null ) {
            this.dataList.clear();
            this.dataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    public List<Payment> getDataList() {
        return dataList;
    }

    public int getSelectPosition() {
        return selectPosition;
    }
}
