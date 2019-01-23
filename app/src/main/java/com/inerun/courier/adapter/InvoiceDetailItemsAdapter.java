package com.inerun.courier.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.inerun.courier.R;
import com.inerun.courier.base.ClickListener;
import com.inerun.courier.model.AuctionItem;

import java.util.List;

/**
 * Created by vinay on 19/12/16.
 */


public class InvoiceDetailItemsAdapter extends RecyclerView.Adapter<InvoiceDetailItemsAdapter.ViewHolder> {

    private final Context context;

    private List<AuctionItem> itemList;
    ClickListener onclickListener;
    private boolean onBind;
//    boolean checkenabled=false;


    public InvoiceDetailItemsAdapter( Context context, List<AuctionItem> itemList) {
        this.itemList = itemList;
        this.onclickListener = onclickListener;
        this.context = context;

    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.invoice_detail_item_row, parent, false);

        InvoiceDetailItemsAdapter.ViewHolder viewHolder = new InvoiceDetailItemsAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AuctionItem item = itemList.get(position);
        holder.idtxt.setText(""+item.getItem_barcode());
        holder.desctxt.setText(item.getDescription());
        holder.pricetxt.setText(""+item.getAap());

        holder.parentView.setTag(position);



    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView idtxt,desctxt ,pricetxt ;
        public View parentView;
        public CheckBox search_num_radio;


        public ViewHolder(View view) {
            super(view);
            idtxt = (TextView) view.findViewById(R.id.invoice_detail_itemid);
            parentView = view;
            desctxt = (TextView) view.findViewById(R.id.invoice_detail_itemdesc);
            pricetxt = (TextView) view.findViewById(R.id.invoice_detail_itemamt);

//            search_num_radio.setOnCheckedChangeListener(InvoiceDetailItemsAdapter.this);



        }


    }





}
