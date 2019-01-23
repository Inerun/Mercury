package com.inerun.courier.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.inerun.courier.R;
import com.inerun.courier.activity_driver.DeliveryReportFragment;
import com.inerun.courier.base.ClickListener;
import com.inerun.courier.data.SalesFilterData;
import com.inerun.courier.helper.DIHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinay on 19/12/16.
 */


public class NavFilterListAdapter extends BaseRecyclerViewAdapter implements CompoundButton.OnCheckedChangeListener {

    private final Context context;
    private ArrayList<SalesFilterData> dataList;

    private boolean onBind;
    //    boolean checkenabled=false;
    private static OnItemClickListener mItemClickListener;
    private  int[] colorResId;
    private int lastSelectedPosition ;
    private DeliveryReportFragment reportSalesFragment;


    public NavFilterListAdapter(Context context, DeliveryReportFragment reportSalesFragment ) {
        this.dataList = new ArrayList<>();
        this.context = context;
        colorResId = context.getResources().getIntArray(R.array.diet_type_color_array);
        this.reportSalesFragment = reportSalesFragment;
    }


    @Override
    protected BaseRecyclerViewAdapter.ViewHolder getViewHolder(View itemView) {
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    protected View oncreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_report_item_row, parent, false);

        return itemView;
    }

    @Override
    public void onbindViewHolder(BaseRecyclerViewAdapter.ViewHolder viewholder, int position) {

        ViewHolder holder = (ViewHolder) viewholder.holder;
        SalesFilterData data = dataList.get(position);
        holder.item_txt.setText(data.getName());


        if(colorResId != null && colorResId.length > 0) {
            int colorPos = position % colorResId.length;
            holder.item_txt.setBackgroundColor(colorResId[colorPos]);
        }else{
            holder.item_txt.setBackgroundColor(DIHelper.getMatColor(context,"900"));
        }


//        holder.barcodetxt.setText((data.getBarcode()));
//        holder.itemtxt.setText(data.getItem());
//        holder.pricetxt.setText(data.getPrice());
//        holder.parentView.setTag(position);

//        if (data.isSelected() ) {
//            holder.search_num_radio.setChecked(true);
//        } else {
//            holder.search_num_radio.setChecked(false);
//        }

        holder.search_num_radio.setChecked(data.isSelected());
        holder.search_num_radio.setTag(position);


//        if (!parcelData.isDelivered()) {
//            holder.search_num_radio.setVisibility(View.VISIBLE);
//        } else {
//            holder.search_num_radio.setVisibility(View.GONE);
//        }
//        onBind = true;
//        holder.search_num_radio.setChecked(data.isSelected());
//        onBind = false;

//        holder.search_num_radio.setOnCheckedChangeListener(this);
        holder.search_num_radio.setOnClickListener(new  ClickListener(context) {
            @Override
            public void click(View v)throws Exception {
                int pos = (int) v.getTag();
                Log.i("lastSelectedPosition", ""+lastSelectedPosition);
                Log.i("Adapter_Pos", ""+pos);

                if(lastSelectedPosition == pos){

                }else {
                    dataList.get(pos).setSelected(true);
                    if (lastSelectedPosition != -1)
                        dataList.get(lastSelectedPosition).setSelected(false);

//                    lastSelectedPosition = pos;
                    reportSalesFragment.setFilterData(pos);
                    // your process when checkBox changed
                    // ...

                    notifyDataSetChanged();

                }

            }
        });



//        holder.search_num_radio.setTag(position);
//        holder.search_num_radio.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int pos = (int) view.getTag();
//                boolean isSelected = dataList.get(pos).isSelected();
//                if(isSelected){
//                    holder.search_num_radio.setChecked(false);
//                    dataList.get(pos).setSelected(false);
//                }else {
//                    holder.search_num_radio.setChecked(true);
//                    dataList.get(pos).setSelected(true);
//                }
//
//                reportSalesFragment.setFilterData(pos);
//            }
//        });
    }


    @Override
    public ArrayList initObjectList() {
        return dataList;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
//        if (!onBind) {
//            int pos = (int) compoundButton.getTag();
//
//
//            if(lastSelectedPosition == pos){
//                notifyDataSetChanged();
//            }else {
//                dataList.get(pos).setSelected(checked);
//                if (lastSelectedPosition != -1)
//                    dataList.get(lastSelectedPosition).setSelected(false);
//
//                lastSelectedPosition = pos;
//                reportSalesFragment.setFilterData(pos);
//
//                // your process when checkBox changed
//                // ...
//
//                notifyDataSetChanged();
//            }
//        }


    }


    public class ViewHolder extends BaseRecyclerViewAdapter.ViewHolder implements View.OnClickListener {
        public TextView item_txt;
//        public CheckBox search_num_radio;
        public RadioButton search_num_radio;
        public View parentView;

        public ViewHolder(View view) {
            super(view);
            parentView = view;
            item_txt = itemView.findViewById(R.id.nav_diet_item);
            search_num_radio = itemView.findViewById(R.id.nav_diet_type_checkbox);


//            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (mItemClickListener != null) {
//                int position = getAdapterPosition();
                int position = (int) v.getTag();
                if (position != RecyclerView.NO_POSITION)
                    mItemClickListener.onItemClick(v, position);
            }
        }
    }


    public void selectAllParcels(boolean checked) {

        for (int i = 0; i < dataList.size(); i++) {
//            if (!dataList.get(i).isDelivered()) {
//                dataList.get(i).setIsselected(checked);
//            }
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

    public List<SalesFilterData> getDataList() {

        return dataList;
    }

    public interface OnItemClickListener {
        public void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        NavFilterListAdapter.mItemClickListener = mItemClickListener;
    }

    public void addDataToList(SalesFilterData data) {
        if(data != null) {
            dataList.add(0, data);
            notifyDataSetChanged();
        }
    }

    public void addDataListToList(List<SalesFilterData> dataList) {
        if(dataList != null && dataList.size() > 0) {
            this.dataList.clear();
            this.dataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    public void setLastSelectedPosition(int lastSelectedPosition) {
        this.lastSelectedPosition = lastSelectedPosition;
    }
}
