package com.inerun.courier.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.inerun.courier.R;
import com.inerun.courier.activity_auction.AuctionAllInvoiceFragment;
import com.inerun.courier.base.ClickListener;
import com.inerun.courier.base.SweetAlertUtil;
import com.inerun.courier.constant.Utils;
import com.inerun.courier.model.AuctionInvoice;
import com.inerun.courier.model.AuctionInvoice_Table;
import com.inerun.courier.model.AuctionItem;
import com.inerun.courier.model.AuctionItem_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by vineet on 11/29/2016.
 */


public class AuctionInvoiceListAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<AuctionInvoice> invoiceArrayList;
    private static OnItemClickListener mItemClickListener;
    ClickListener onclickListener;
    private LayoutInflater inflater;


    private final int ITEM_VIEW_TYPE_BASIC = 0;
    private final int ITEM_VIEW_TYPE_FOOTER = 1;
    private boolean isLoading;
    private AuctionAllInvoiceFragment auctionAllInvoiceFragment;

    // This object helps you save/restore the open/close state of each view
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public AuctionInvoiceListAdapter(Context context, List<AuctionInvoice> invoiceArrayList, ClickListener searchlickListener, AuctionAllInvoiceFragment auctionAllInvoiceFragment) {
        this.invoiceArrayList = invoiceArrayList;
        this.context = context;
        this.onclickListener = searchlickListener;
        inflater = LayoutInflater.from(context);
        // uncomment the line below if you want to open only one row at a time
        viewBinderHelper.setOpenOnlyOne(true);
        this.auctionAllInvoiceFragment = auctionAllInvoiceFragment;
    }

    public class CustInvoiceViewHolder extends RecyclerView.ViewHolder  {
        public TextView invoice_no, no_of_parcel, customer_name, delete;
        public View parentView;
        private SwipeRevealLayout swipeLayout;
        private View frontLayout;

        public CustInvoiceViewHolder(View view) {
            super(view);
            parentView = view;
            invoice_no = (TextView) view.findViewById(R.id.invoice_no);
            no_of_parcel = (TextView) view.findViewById(R.id.no_of_parcel);
            customer_name = (TextView) view.findViewById(R.id.customer_name);
            swipeLayout = view.findViewById(R.id.swipe_layout);
            delete = view.findViewById(R.id.delete_txt);
            frontLayout = view.findViewById(R.id.front_layout);
//            view.setOnClickListener(onclickListener);
            view.setOnClickListener(new ClickListener(context) {
                @Override
                public void click(View view) throws Exception {

                    // TODO Auto-generated method stub
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(view, getPosition());
                    }
                }

            });
            frontLayout.setOnClickListener(new ClickListener(context) {
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
            View view = inflater.inflate(R.layout.auction_invoice_list_item_new, parent, false);

            vh = new AuctionInvoiceListAdapter.CustInvoiceViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.prograss_bar, parent, false);

            vh = new AuctionInvoiceListAdapter.ProgressViewHolder(view);
        }

        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof AuctionInvoice) {


        AuctionInvoice data = invoiceArrayList.get(position);

        // Save/restore the open/close state.
        // You need to provide a String id which uniquely defines the data object.
        viewBinderHelper.bind(((AuctionInvoiceListAdapter.CustInvoiceViewHolder) holder).swipeLayout, data.getId() + "");

        ((AuctionInvoiceListAdapter.CustInvoiceViewHolder) holder).invoice_no.setText(data.getInvoice_number());
        ((AuctionInvoiceListAdapter.CustInvoiceViewHolder) holder).parentView.setTag(position);
//            if(data.getParcelData() != null && data.getParcelData().size() >= 0) {
        ((AuctionInvoiceListAdapter.CustInvoiceViewHolder) holder).no_of_parcel.setText("" + data.getGrand_total());
        ((AuctionInvoiceListAdapter.CustInvoiceViewHolder) holder).customer_name.setText("" + data.getCustomer_name());

        ((AuctionInvoiceListAdapter.CustInvoiceViewHolder) holder).delete.setTag(position);
        ((AuctionInvoiceListAdapter.CustInvoiceViewHolder) holder).delete.setOnClickListener( new ClickListener(context) {
            @Override
            public void click(View v) throws Exception{
                final int pos = (int) v.getTag();
                SweetAlertUtil.showMessageWithCallback(context, context.getString(R.string.cancel_confirm_title), context.getString(R.string.cancel_confirm_msg), context.getString(R.string.yes), context.getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        Log.i("delete", "pos");
                        int id = invoiceArrayList.get(pos).getId();
                        AuctionInvoice auctionInvoice = SQLite.select().from(AuctionInvoice.class).where(AuctionInvoice_Table.id.eq(id)).querySingle();


                        auctionInvoice.setStatus(0);
                        auctionInvoice.setIs_cancel(1);
                        if(Utils.isUserLoggedIn(context)){
                            auctionInvoice.setCancel_by(Integer.parseInt(Utils.getUserId(context)));
                        }
                        auctionInvoice.setSync_status(AuctionInvoice.SYNC_STATUS_PENDING);
                        auctionInvoice.update();

                        List<AuctionItem> auctionItemList = SQLite.select().from(AuctionItem.class).where(AuctionItem_Table.invoice_id.eq(auctionInvoice.getInvoice_number())).queryList();

                        for (AuctionItem auctionItem: auctionItemList){
                            auctionItem.setAap(0);
                            auctionItem.setAuction_id(null);
                            auctionItem.setInvoice_id(null);
                            auctionItem.setSync_status(AuctionItem.SYNC_STATUS_PENDING);
                            auctionItem.update();
                        }

                        invoiceArrayList.remove(pos);
                        notifyDataSetChanged();
//                        notifyItemRemoved(pos);
                        sweetAlertDialog.cancel();
                        if(invoiceArrayList.size() <= 0){
                            auctionAllInvoiceFragment.detaillistview.setVisibility(View.GONE);
                            auctionAllInvoiceFragment.error_txt.setVisibility(View.VISIBLE);
                        }else{
                            auctionAllInvoiceFragment.detaillistview.setVisibility(View.VISIBLE);
                            auctionAllInvoiceFragment.error_txt.setVisibility(View.GONE);
                        }
//                        viewBinderHelper.closeLayout(invoiceArrayList.get(pos).getId() + "");
                    }
                }, new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.cancel();
                        viewBinderHelper.closeLayout(invoiceArrayList.get(pos).getId() + "");
                    }
                });
            }
        });


//            }else{
//                ((AuctionInvoiceListAdapter.CustInvoiceViewHolder) holder).no_of_parcel.setText("0");
//            }

        ((AuctionInvoiceListAdapter.CustInvoiceViewHolder) holder).parentView.setTag(position);

//        } else {
//            if (!isLoading) {
//                ((AuctionInvoiceListAdapter.ProgressViewHolder) holder).progressBar.setVisibility(View.VISIBLE);
//                ((AuctionInvoiceListAdapter.ProgressViewHolder) holder).progressBar.setIndeterminate(true);
//            } else
//                ((AuctionInvoiceListAdapter.ProgressViewHolder) holder).progressBar.setVisibility(View.GONE);
//        }
    }

    @Override
    public int getItemViewType(int position) {
        return invoiceArrayList.get(position) != null ? ITEM_VIEW_TYPE_BASIC : ITEM_VIEW_TYPE_FOOTER;
    }

    @Override
    public int getItemCount() {
        return invoiceArrayList == null ? 0 : invoiceArrayList.size();
    }

    public interface OnItemClickListener {
        public void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        AuctionInvoiceListAdapter.mItemClickListener = mItemClickListener;
    }

    public List<AuctionInvoice> getInvoiceArrayList() {
        return invoiceArrayList;
    }

    public void setInvoiceArrayList(ArrayList<AuctionInvoice> invoiceArrayList) {
        this.invoiceArrayList = invoiceArrayList;
    }

    public void add(boolean isLoading, List<AuctionInvoice> items) {
        this.isLoading = isLoading;
        int previousDataSize = this.invoiceArrayList.size();
        this.invoiceArrayList.addAll(items);
        notifyDataSetChanged();
//        notifyItemRangeInserted(previousDataSize, items.size());
    }

    public void addDataListToList(List<AuctionInvoice> dataList) {
        this.invoiceArrayList.clear();
        this.invoiceArrayList.addAll(dataList);
        notifyDataSetChanged();
    }
}