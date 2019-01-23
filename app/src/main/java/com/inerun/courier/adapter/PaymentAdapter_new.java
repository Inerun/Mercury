package com.inerun.courier.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inerun.courier.R;
import com.inerun.courier.data.ParcelListingData;
import com.inerun.courier.helper.DIHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vineet on 11/29/2016.
 */


public class PaymentAdapter_new extends BaseRecyclerViewAdapter {
    private Context context;
    private ArrayList<ParcelListingData.ParcelData> parcelDataList;
    private static OnItemClickListener mItemClickListener;

    public class ViewHolder extends BaseRecyclerViewAdapter.ViewHolder implements View.OnClickListener {
        public TextView barcode, payment, parcel_type;


        public ViewHolder(View view) {
            super(view);
            barcode = (TextView) view.findViewById(R.id.barcode);
            payment = (TextView) view.findViewById(R.id.payment);
            parcel_type = (TextView) view.findViewById(R.id.parcel_type);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // TODO Auto-generated method stub
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getPosition());
            }
        }
    }


    public PaymentAdapter_new(Context context) {
        this.parcelDataList = new ArrayList<>();
        this.context = context;
    }



    @Override
    protected BaseRecyclerViewAdapter.ViewHolder getViewHolder(View itemView) {
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    protected View oncreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_list_item_new, parent, false);
        return itemView;
    }

    @Override
    public void onbindViewHolder(BaseRecyclerViewAdapter.ViewHolder viewholder, int position) {
        ViewHolder holder= (ViewHolder) viewholder.holder;
        ParcelListingData.ParcelData data = parcelDataList.get(position);

        if(data != null) {

            holder.barcode.setText(data.getBarcode());
            if (data.getPayment_type() == 0) {
//                holder.payment.setText(data.getAmount() + " " + data.getCurrency());
                holder.payment.setText(DIHelper.getValueInDecimal(data.getAmount()) + " " + data.getCurrency());
            } else {
                holder.payment.setText(context.getString(R.string.payment_prepaid));
            }
            if (data.isCollection()) {
                holder.parcel_type.setText("COLLECTION");
                holder.parcel_type.setBackground(context.getResources().getDrawable(R.drawable.round_rect_fill_collection));
            } else {
                holder.parcel_type.setText("DELIVERY");
                holder.parcel_type.setBackground(context.getResources().getDrawable(R.drawable.round_rect_fill_delivery));
            }
        }
    }



//    @Override
//    public int getItemViewType(int position) {
//
//        if (parcelDataList.get(position).isDelivered()) {
//            return ParcelListingData.ParcelData.DELIVERED;
//        } else {
//            return ParcelListingData.ParcelData.PENDING;
//        }
//
//    }



    @Override
    public ArrayList initObjectList() {
        return parcelDataList;
    }

    public interface OnItemClickListener {
        public void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        PaymentAdapter_new.mItemClickListener = mItemClickListener;
    }


    public List<ParcelListingData.ParcelData> getParcelDataList() {
        return parcelDataList;
    }

    public void setParcelDataList(ArrayList<ParcelListingData.ParcelData> parcelDataList) {
        this.parcelDataList = parcelDataList;
    }

}