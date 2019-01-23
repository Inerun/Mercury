package com.inerun.courier.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inerun.courier.R;
import com.inerun.courier.data.Shipment;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by vinay on 20/12/16.
 */

public class ShipmentAdapter extends RecyclerView.Adapter<ShipmentAdapter.ViewHolder> {
    List<Shipment.ShipmentDetail> dataList;
    Context context;


    public ShipmentAdapter( Context context) {
        this.dataList = new ArrayList<>();
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shipment_item_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Shipment.ShipmentDetail data = dataList.get(position);
        if (data != null) {
            holder.pieces_count_txt.setText(data.getNo_of_parcels());
            holder.dimension_txt.setText(data.getLength()+"x"+data.getBreath()+"x"+data.getHeight());
            holder.vol_weight_txt.setText(data.getVol_weight());
            holder.weight_txt.setText(data.getGross_weight());

            if (position % 2 == 0) {
                holder.shiping_adapter_layout.setBackgroundColor(context.getResources().getColor(R.color.white));
            } else {
                holder.shiping_adapter_layout.setBackgroundColor(context.getResources().getColor(R.color.grey_100));
            }

        }
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView pieces_count_txt, dimension_txt, vol_weight_txt, weight_txt;
        private LinearLayout shiping_adapter_layout;
        public ImageView edit, delete, done;


        public ViewHolder(View view) {
            super(view);
            pieces_count_txt = (TextView) view.findViewById(R.id.pieces_count);
            dimension_txt = (TextView) view.findViewById(R.id.dimension);
            vol_weight_txt = (TextView) view.findViewById(R.id.vol_weight);
            weight_txt = (TextView) view.findViewById(R.id.weight);
            shiping_adapter_layout = view.findViewById(R.id.shiping_adapter_layout);

        }


    }


    public void addDataToList(Shipment.ShipmentDetail data) {
        if(data != null) {
            dataList.add(0, data);
            notifyDataSetChanged();
        }
    }

    public void addDataListToList(List<Shipment.ShipmentDetail> dataList) {
        if(dataList != null) {
            this.dataList.clear();
            this.dataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    public List<Shipment.ShipmentDetail> getDataList() {

        return dataList;
    }


}
