package com.inerun.courier.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inerun.courier.R;
import com.inerun.courier.base.ClickListener;
import com.inerun.courier.data.PickupParcelData;
import com.inerun.courier.data.Shipment;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

/**
 * Created by vineet on 11/29/2016.
 */


public class DeliveryShipmentViewParcelsAdapter extends BaseRecyclerViewAdapter {
    private  PickupParcelData parcelData;
    private Context context;
    private ArrayList<Shipment.ShipmentDetail> parcelDataList;
    private static OnItemClickListener mItemClickListener;
    ClickListener clickListener;




    public DeliveryShipmentViewParcelsAdapter(Context context, PickupParcelData parcelData, ClickListener clickListener) {
        this.parcelData = parcelData;
        this.context = context;
        parcelDataList= new ArrayList<>();
        this.clickListener= clickListener;
        parcelDataList.addAll(parcelData.getShipment().getShipment_details());
    }

    public ArrayList<Shipment.ShipmentDetail> getParcelDataList() {
        return parcelDataList;
    }



    @Override
    protected BaseRecyclerViewAdapter.ViewHolder getViewHolder(View itemView) {
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    protected View oncreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivershipment_view_row, parent, false);


        return itemView;
    }

    @Override
    public void onbindViewHolder(BaseRecyclerViewAdapter.ViewHolder viewholder, int position) {

        ViewHolder holder= (ViewHolder) viewholder.holder;
        Shipment.ShipmentDetail data = parcelDataList.get(position);

//        holder.barcode.setText(data.getBarcode());
        holder.ship_view_row_pcs.setText(""+data.getNo_of_parcels());
        holder.ship_view_row_lenbrewid.setText(data.getDimension());
//        holder.ship_edit_row_bre.setText(data.getBreath());
//        holder.ship_edit_row_wid.setText(data.getHeight());
        holder.ship_view_row_vol_weight_val.setText(data.getVol_weight());
        holder.decvalue_vol_weight.setText(data.getDeclared_value());
        holder.ship_view_row_gross_weight.setText(data.getGross_weight());
        holder.ship_edit_row_chrg_weight.setText(data.getChargeable_weight());
        holder.item_rootLayout.setTag(position);
        holder.item_rootLayout.setOnClickListener(clickListener);
//        holder.ship_edit_row_gross_weight_type.setText(data.getPickParcelDetailData().());
//        holder.id.setText(context.getString(R.string.parcel_customer_id,data.getCustid()));
//        holder.phone.setText(data.getDelivery_phone());
//        holder.date.setText(data.getDate());
//        holder.address.setText(data.getDeliveryAddress());
//        if(data.getPayment_type() == 0) {
//            if(data.isPrepaid()){
//                holder.payment.setText(context.getString(R.string.prepaid_label));
//            }else{
//                holder.payment.setText(DIHelper.getValueInDecimal(data.getAmount()) + " " + data.getCurrency());
//            }
//
//
////            holder.payment.setText(data.getAmount() + " " + data.getCurrency());
//        }else{
//            if(data.isPrepaid()){
//                holder.payment.setText(context.getString(R.string.prepaid_label));
//            }else{
//                holder.payment.setText(context.getString(R.string.payment_prepaid));
//            }
////            holder.payment.setText(context.getString(R.string.payment_prepaid));
//        }
////        holder.comment.setText(data.getSpecialinstructions());
//        holder.item_rootLayout.setTag(position);
//        holder.callbtn.setTag(position);
    }

    public class ViewHolder extends BaseRecyclerViewAdapter.ViewHolder implements View.OnClickListener {
        public TextView ship_view_row_lenbrewid,ship_view_row_vol_weight_val;
        public TextView ship_view_row_pcs,ship_view_row_gross_weight,ship_edit_row_chrg_weight, decvalue_vol_weight,phone, address, payment, comment;
        public ImageButton callbtn;
        public LinearLayout item_rootLayout;
        public MaterialBetterSpinner ship_view_row_gross_weight_type;
        int pos;

        public ViewHolder(View view) {
            super(view);
            ship_view_row_pcs = (TextView) view.findViewById(R.id.ship_view_row_pcs);
            ship_view_row_lenbrewid = (TextView) view.findViewById(R.id.ship_view_row_lenbrewid);
            ship_view_row_vol_weight_val = (TextView) view.findViewById(R.id.ship_view_row_vol_weight_val);
            ship_view_row_gross_weight = (TextView) view.findViewById(R.id.ship_view_row_gross_weight);
            ship_edit_row_chrg_weight = (TextView) view.findViewById(R.id.ship_edit_row_chrg_weight);
            decvalue_vol_weight = (TextView) view.findViewById(R.id.decvalue_vol_weight);
            item_rootLayout = (LinearLayout) view.findViewById(R.id.item_rootLayout);

//            id = (TextView) view.findViewById(R.id.customer_id);
//            address = (TextView) view.findViewById(R.id.address);
//            barcode = (TextView) view.findViewById(R.id.barcode);
//            payment = (TextView) view.findViewById(R.id.payment);
//            phone = (TextView) view.findViewById(R.id.phone_txt);
//            callbtn = (ImageButton) view.findViewById(R.id.call_action);
//            item_rootLayout = (LinearLayout) view.findViewById(R.id.item_rootLayout);
//            item_rootLayout.setOnClickListener(this);
//            callbtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // TODO Auto-generated method stub
            if (mItemClickListener != null) {
                int pos= (int) view.getTag();
                Log.i("pos",""+pos);
                if(pos>=0) {
                    mItemClickListener.onItemClick(view, pos);
                }
            }
        }
    }

    public void refreshData(PickupParcelData parcelData)
    {
        this.parcelData = parcelData;

        parcelDataList= new ArrayList<>();

        parcelDataList.addAll(parcelData.getShipment().getShipment_details());
    }

    @Override
    public ArrayList initObjectList() {
        return parcelDataList;
    }

    public interface OnItemClickListener {
        public void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        DeliveryShipmentViewParcelsAdapter.mItemClickListener = mItemClickListener;
    }
}