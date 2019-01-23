package com.inerun.courier.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.inerun.courier.R;
import com.inerun.courier.activity_auction.AuctionCreateInvoiceFragment;
import com.inerun.courier.base.SweetAlertUtil;
import com.inerun.courier.helper.EditTextBackEvent;
import com.inerun.courier.helper.RecyclerViewHolderOnClickListener;
import com.inerun.courier.model.AuctionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinay on 20/12/16.
 */

public class AuctionAddItemAdapter extends BaseAdapter implements View.OnClickListener {

    List<AuctionItem> auctionItemList;
    Context context;
    LayoutInflater inflater;
    AuctionCreateInvoiceFragment auctionCreateInvoiceFragment;


    public AuctionAddItemAdapter(Context context, AuctionCreateInvoiceFragment auctionCreateInvoiceFragment) {
        this.auctionItemList = new ArrayList<>();
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.auctionCreateInvoiceFragment = auctionCreateInvoiceFragment;
//        setTotalValue(auctionItemList);
    }

//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.whaddparcelrow, parent, false);
//
//        final WhAddParcelAdapter.ViewHolder viewHolder = new WhAddParcelAdapter.ViewHolder(itemView);
//
//        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("WhAddParcelAdapter", "position = " + viewHolder.getAdapterPosition());
//
//
//            }
//        });
//        viewHolder.done.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//
////        holder.done.setTag(1, position);
////        holder.done.setTag(2, holder);
//
////        holder.edit.setOnClickListener(new RecyclerViewHolderOnClickListener( position) {
////
////            @Override
////            public void onRecyclerClick( int pos, View view) {
////
////
////            }
////        });
//        holder.delete.setOnClickListener(this);
////        holder.done.setOnClickListener(new RecyclerViewHolderOnClickListener() {
////            @Override
////            public void onRecyclerClick(int pos, View view) {
////                ViewHolder viewholder = (ViewHolder) view.getTag();
//////                int pos = (int) view.getTag(1);
//////                String parcel = "" + viewholder.parcelnumedt.getText();
//////                viewholder.parcelnumedt.setVisibility(View.GONE);
//////                viewholder.done.setVisibility(View.GONE);
//////                parcellist.set(pos, parcel);
//////
//////                viewholder.parcelnumtxt.setText(parcel);
//////
//////                viewholder.parcelnumtxt.setVisibility(View.VISIBLE);
//////                viewholder.edit.setVisibility(View.VISIBLE);
//////                notifyDataSetChanged();
////            }
////        });
//        holder.done.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Log.d("WhAddParcelAdapter", "position = " + viewHolder.getAdapterPosition());
////
////
////
////                String parcel = "" + viewHolder.parcelnumedt.getText();
////                viewHolder.parcelnumedt.setVisibility(View.GONE);
////                viewHolder.done.setVisibility(View.GONE);
////                parcellist.set(viewHolder.getAdapterPosition(), parcel);
////
////                viewHolder.parcelnumtxt.setText(parcel);
////
////                viewHolder.parcelnumtxt.setVisibility(View.VISIBLE);
////                viewHolder.edit.setVisibility(View.VISIBLE);
////                notifyDataSetChanged();
//
//            }
//        });
//
//
//
//
//
//    }

//    @Override
//    public int getItemCount() {
//        return parcellist.size();
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_parcel_delete:
                int pos = (int) view.getTag();
                auctionItemList.remove(pos);
                setTotalValue(auctionItemList);
                notifyDataSetChanged();


                break;

        }

    }


    @Override
    public int getCount() {
        return auctionItemList.size();
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

            convertview = inflater.inflate(R.layout.auctionadditemrow_new, null);
            holder = new ViewHolder(convertview);
            convertview.setTag(holder);
        } else {
            holder = (ViewHolder) convertview.getTag();
        }

        final AuctionItem auctionItem = auctionItemList.get(position);
        holder.parcelnumtxt.setText(auctionItem.getItem_barcode());

        holder.item_price_edt.setText(auctionItem.getAap() + "");
        holder.item_price_edt.setTag(position);

        final ViewHolder finalHolder = holder;
        holder.item_price_edt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                int pos = (int) v.getTag();

                if ((actionId == EditorInfo.IME_ACTION_DONE) || (actionId == EditorInfo.IME_ACTION_NEXT)) {
                    Log.i("onEditorAction", "Enter pressed" + pos);


                    if(v.getText().toString().trim().length() > 0){
                        float app = Float.parseFloat(v.getText().toString().trim());
                        auctionItemList.get(pos).setAap(app);
                        setTotalValue(auctionItemList);
                    }else{
                        String app = ""+ auctionItemList.get(pos).getAap();
                        finalHolder.item_price_edt.setText(app);
                        SweetAlertUtil.showSweetErrorToast(context, context.getString(R.string.empty_val_error_msg));
                    }


                }
                return false;
            }
        });


        holder.item_price_edt.setOnEditTextImeBackListener(new EditTextBackEvent.EditTextImeBackListener() {
            @Override
            public void onImeBack(EditTextBackEvent ctrl, String text) {
                int pos = (int) ctrl.getTag();
                if(ctrl.getText().toString().trim().length() > 0){
                    float app = Float.parseFloat(ctrl.getText().toString().trim());
                    auctionItemList.get(pos).setAap(app);
                    setTotalValue(auctionItemList);
                }else{
                    String app = ""+ auctionItemList.get(pos).getAap();
                    finalHolder.item_price_edt.setText(app);
                    SweetAlertUtil.showSweetErrorToast(context, context.getString(R.string.empty_val_error_msg));
                }

//                String app = ""+ auctionItemList.get(pos).getAap();
//                finalHolder.item_price_edt.setText(app);
            }
        });

        holder.item_price_edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                int pos = (int) v.getTag();

                EditTextBackEvent editTextBackEvent = v.findViewById(R.id.add_item_price_edt);

                if(editTextBackEvent.getText().toString().trim().length() > 0){
                    float app = Float.parseFloat(editTextBackEvent.getText().toString().trim());
                    if(auctionItemList != null && auctionItemList.size() > 0) {
                        auctionItemList.get(pos).setAap(app);
                        setTotalValue(auctionItemList);
                    }
                }else{
                    String app = ""+ auctionItemList.get(pos).getAap();
                    finalHolder.item_price_edt.setText(app);
                    SweetAlertUtil.showSweetErrorToast(context, context.getString(R.string.empty_val_error_msg));
                }

            }
        });


        holder.edit.setTag(holder);
        holder.delete.setTag(position);
        holder.done.setTag(holder);
        holder.edit.setOnClickListener(new RecyclerViewHolderOnClickListener(position) {
            @Override
            public void onRecyclerClick(int pos, View view) {
                Log.d("WhAddParcelAdapter", "position = " + pos);


                ViewHolder viewHolder = (ViewHolder) view.getTag();
                String parcel = "" + viewHolder.parcelnumtxt.getText();
                viewHolder.parcelnumedt.setVisibility(View.VISIBLE);
                viewHolder.done.setVisibility(View.VISIBLE);

                viewHolder.parcelnumedt.setText(parcel);

                viewHolder.parcelnumtxt.setVisibility(View.GONE);
                viewHolder.edit.setVisibility(View.GONE);
                notifyDataSetChanged();

            }
        });
        holder.done.setOnClickListener(new RecyclerViewHolderOnClickListener(position) {
            @Override
            public void onRecyclerClick(int pos, View view) {
                Log.d("WhAddParcelAdapter", "position = " + pos);

                ViewHolder viewHolder = (ViewHolder) view.getTag();
                String parcel = "" + viewHolder.parcelnumedt.getText();
                viewHolder.parcelnumedt.setVisibility(View.GONE);
                viewHolder.done.setVisibility(View.GONE);
//                parcellist.set(pos, parcel);

                viewHolder.parcelnumtxt.setText(parcel);

                viewHolder.parcelnumtxt.setVisibility(View.VISIBLE);
                viewHolder.edit.setVisibility(View.VISIBLE);
                notifyDataSetChanged();
            }
        });
        holder.delete.setOnClickListener(this);
        return convertview;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView parcelnumtxt;
        public EditText parcelnumedt;
//        public EditText item_price_edt;
        public EditTextBackEvent item_price_edt;
        public ImageView edit, delete, done;


        public ViewHolder(View view) {
            super(view);
            edit = (ImageView) view.findViewById(R.id.add_parcel_edit);
            delete = (ImageView) view.findViewById(R.id.add_parcel_delete);
            done = (ImageView) view.findViewById(R.id.add_parcel_done);
            parcelnumtxt = (TextView) view.findViewById(R.id.add_parcel_num);
            parcelnumedt = (EditText) view.findViewById(R.id.add_parcel_num_edt);
            item_price_edt = (EditTextBackEvent) view.findViewById(R.id.add_item_price_edt);
//            done.setOnClickListener();

        }


    }


    public void addItemToList(AuctionItem auctionItem) {
        auctionItemList.add(auctionItem);
        setTotalValue(auctionItemList);
        notifyDataSetChanged();
    }

    public List<AuctionItem> getAuctionItemList() {
        return auctionItemList;
    }

    public float getTotalValue(List<AuctionItem> auctionItemList) {
        float sum = 0;
        for (AuctionItem auctionItem : auctionItemList) {
            sum += auctionItem.getAap();
        }

        return sum;

    }

    public void setTotalValue(List<AuctionItem> auctionItemList) {
        float sum = 0;
        if (auctionItemList != null && auctionItemList.size() > 0) {
            for (AuctionItem auctionItem : auctionItemList) {
                sum += auctionItem.getAap();
            }
            Log.i("sum", "" + sum);

        }
        auctionCreateInvoiceFragment.invoice_total.setText(context.getString(R.string.auction_collection_val, "" + sum));
    }

    private class MyTextWatcher implements TextWatcher {

        View view;

        public MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            EditText qtyView = (EditText) view.findViewById(R.id.add_item_price_edt);
            int pos = (int) qtyView.getTag();
            float app = Float.parseFloat(s.toString().trim());
            Log.i("onTextChanged", "" + app);
            auctionItemList.get(pos).setAap(app);
            setTotalValue(auctionItemList);
//            auctionCreateInvoiceFragment.invoice_total.setText(""+getTotalValue(auctionItemList));

        }

        @Override
        public void afterTextChanged(Editable s) {

//            EditText qtyView = (EditText) view.findViewById(R.id.add_item_price_edt);
//            int pos = (int) qtyView.getTag();
//            float app = Float.parseFloat(s.toString().trim());
//            auctionItemList.get(pos).setAap(app);
//            auctionCreateInvoiceFragment.invoice_total.setText(""+getTotalValue(auctionItemList));

        }
    }
}
