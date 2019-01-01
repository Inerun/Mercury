package com.inerun.courier.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.inerun.courier.R;
import com.inerun.courier.base.ClickListener;
import com.inerun.courier.data.WhReadyParcelData;

import java.util.ArrayList;

/**
 * Created by vineet on 11/29/2016.
 */


public class WhRequestListAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<WhReadyParcelData.RequestData> requestDataList;
    private static OnItemClickListener mItemClickListener;
    ClickListener onclickListener;
    private LayoutInflater inflater;

    private final int ITEM_VIEW_TYPE_BASIC = 0;
    private final int ITEM_VIEW_TYPE_FOOTER = 1;
    private boolean isLoading;


    public WhRequestListAdapter(Context context, ArrayList<WhReadyParcelData.RequestData> requestDataList, ClickListener searchlickListener) {
        this.requestDataList = requestDataList;
        this.context = context;
        this.onclickListener = searchlickListener;
        inflater = LayoutInflater.from(context);
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        if (viewType == ITEM_VIEW_TYPE_BASIC) {
            View view = inflater.inflate(R.layout.wh_request_list_item, parent, false);

            vh = new WhRequestListAdapter.WhRequestViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.prograss_bar, parent, false);

            vh = new WhRequestListAdapter.ProgressViewHolder(view);
        }

        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof WhRequestListAdapter.WhRequestViewHolder) {

            WhReadyParcelData.RequestData data = requestDataList.get(position);

            ((WhRequestListAdapter.WhRequestViewHolder) holder).request_id.setText(data.getRequest_id());

            if(data.getParcelData() != null && data.getParcelData().size() >= 0) {
                ((WhRequestListAdapter.WhRequestViewHolder) holder).no_of_parcel.setText("" + data.getParcelData().size());
            }else{
                ((WhRequestListAdapter.WhRequestViewHolder) holder).no_of_parcel.setText("0");
            }

            ((WhRequestListAdapter.WhRequestViewHolder) holder).parentView.setTag(position);

        } else {
            if (!isLoading) {
                ((WhRequestListAdapter.ProgressViewHolder) holder).progressBar.setVisibility(View.VISIBLE);
                ((WhRequestListAdapter.ProgressViewHolder) holder).progressBar.setIndeterminate(true);
            } else
                ((WhRequestListAdapter.ProgressViewHolder) holder).progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return requestDataList.get(position) != null ? ITEM_VIEW_TYPE_BASIC : ITEM_VIEW_TYPE_FOOTER;
    }

    @Override
    public int getItemCount() {
        return requestDataList == null ? 0: requestDataList.size();
    }


    public class WhRequestViewHolder extends RecyclerView.ViewHolder  {
        public TextView request_id, no_of_parcel;
        public View parentView;


        public WhRequestViewHolder(View view) {
            super(view);
            parentView = view;
            request_id = (TextView) view.findViewById(R.id.request_id);
            no_of_parcel = (TextView) view.findViewById(R.id.no_of_parcel);
            view.setOnClickListener(onclickListener);
        }


    }


    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        }
    }



    public interface OnItemClickListener {
        public void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        WhRequestListAdapter.mItemClickListener = mItemClickListener;
    }

    public ArrayList<WhReadyParcelData.RequestData> getRequestDataList() {
        return requestDataList;
    }

    public void setRequestDataList(ArrayList<WhReadyParcelData.RequestData> requestDataList) {
        this.requestDataList = requestDataList;
    }


    public void add(boolean isLoading , ArrayList<WhReadyParcelData.RequestData> items) {
        this.isLoading = isLoading;
        int previousDataSize = this.requestDataList.size();
        this.requestDataList.addAll(items);
        notifyDataSetChanged();
//        notifyItemRangeInserted(previousDataSize, items.size());
    }
}