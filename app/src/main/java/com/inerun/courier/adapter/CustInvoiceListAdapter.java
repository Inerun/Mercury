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
import com.inerun.courier.data.CustInvoiceParcelData;

import java.util.ArrayList;

/**
 * Created by vineet on 11/29/2016.
 */


public class CustInvoiceListAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<CustInvoiceParcelData.Invoice> invoiceArrayList;
    private static OnItemClickListener mItemClickListener;
    ClickListener onclickListener;
    private LayoutInflater inflater;


    private final int ITEM_VIEW_TYPE_BASIC = 0;
    private final int ITEM_VIEW_TYPE_FOOTER = 1;
    private boolean isLoading;


    public CustInvoiceListAdapter(Context context, ArrayList<CustInvoiceParcelData.Invoice> invoiceArrayList, ClickListener searchlickListener) {
        this.invoiceArrayList = invoiceArrayList;
        this.context = context;
        this.onclickListener = searchlickListener;
        inflater = LayoutInflater.from(context);
    }

    public class CustInvoiceViewHolder extends RecyclerView.ViewHolder  {
        public TextView invoice_no, no_of_parcel;
        public View parentView;


        public CustInvoiceViewHolder(View view) {
            super(view);
            parentView = view;
            invoice_no = (TextView) view.findViewById(R.id.invoice_no);
            no_of_parcel = (TextView) view.findViewById(R.id.no_of_parcel);
            view.setOnClickListener(new ClickListener(context) {
                @Override
                public void click(View view) throws Exception {

                    // TODO Auto-generated method stub
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(view, getPosition());
                    }
                }

            });
        }


    }


    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        }
    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        if (viewType == ITEM_VIEW_TYPE_BASIC) {
            View view = inflater.inflate(R.layout.invoice_list_item, parent, false);

            vh = new CustInvoiceListAdapter.CustInvoiceViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.prograss_bar, parent, false);

            vh = new CustInvoiceListAdapter.ProgressViewHolder(view);
        }

        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CustInvoiceListAdapter.CustInvoiceViewHolder) {




            CustInvoiceParcelData.Invoice data = invoiceArrayList.get(position);

            ((CustInvoiceListAdapter.CustInvoiceViewHolder) holder).invoice_no.setText(data.getInvoice_number());
            ((CustInvoiceListAdapter.CustInvoiceViewHolder) holder).parentView.setTag(position);
            if(data.getParcelData() != null && data.getParcelData().size() >= 0) {
                ((CustInvoiceListAdapter.CustInvoiceViewHolder) holder).no_of_parcel.setText("" + data.getParcelData().size());
            }else{
                ((CustInvoiceListAdapter.CustInvoiceViewHolder) holder).no_of_parcel.setText("0");
            }

            ((CustInvoiceListAdapter.CustInvoiceViewHolder) holder).parentView.setTag(position);

        } else {
            if (!isLoading) {
                ((CustInvoiceListAdapter.ProgressViewHolder) holder).progressBar.setVisibility(View.VISIBLE);
                ((CustInvoiceListAdapter.ProgressViewHolder) holder).progressBar.setIndeterminate(true);
            } else
                ((CustInvoiceListAdapter.ProgressViewHolder) holder).progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return invoiceArrayList.get(position) != null ? ITEM_VIEW_TYPE_BASIC : ITEM_VIEW_TYPE_FOOTER;
    }

    @Override
    public int getItemCount() {
        return invoiceArrayList == null ? 0: invoiceArrayList.size();
    }

    public interface OnItemClickListener {
        public void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        CustInvoiceListAdapter.mItemClickListener = mItemClickListener;
    }

    public ArrayList<CustInvoiceParcelData.Invoice> getInvoiceArrayList() {
        return invoiceArrayList;
    }

    public void setInvoiceArrayList(ArrayList<CustInvoiceParcelData.Invoice> invoiceArrayList) {
        this.invoiceArrayList = invoiceArrayList;
    }

    public void add(boolean isLoading , ArrayList<CustInvoiceParcelData.Invoice> items) {
        this.isLoading = isLoading;
        int previousDataSize = this.invoiceArrayList.size();
        this.invoiceArrayList.addAll(items);
        notifyDataSetChanged();
//        notifyItemRangeInserted(previousDataSize, items.size());
    }
}