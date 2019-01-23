package com.inerun.courier.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.inerun.courier.R;
import com.inerun.courier.model.AuctionItem;

import java.util.List;

/**
 * Created by vinay on 20/12/16.
 */

public class InvoiceDetailItemsAdapter_New extends BaseAdapter {

    List<AuctionItem> itemList;
    Context context;
    LayoutInflater inflater;


    public InvoiceDetailItemsAdapter_New(Context context,  List<AuctionItem> itemList) {
        this.itemList = itemList;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int pos) {
        return pos;
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(final int position, View convertview, ViewGroup viewGroup) {
        ViewHolder holder = null;

        if (convertview == null) {

            convertview = inflater.inflate(R.layout.invoice_detail_item_row, null);
            holder = new ViewHolder(convertview);
            convertview.setTag(holder);
        } else {
            holder = (ViewHolder) convertview.getTag();
        }

        AuctionItem item = itemList.get(position);
        holder.idtxt.setText(""+item.getItem_barcode());
        holder.desctxt.setText(item.getDescription());
        holder.pricetxt.setText(""+item.getAap());

//        holder.parentView.setTag(position)


        return convertview;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView idtxt,desctxt ,pricetxt ;
        public View parentView;


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
