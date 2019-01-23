package com.inerun.courier.activity_auction;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inerun.courier.R;
import com.inerun.courier.adapter.AuctionInvoiceListAdapter;
import com.inerun.courier.base.AuctionBaseFragment;
import com.inerun.courier.base.ClickListener;
import com.inerun.courier.data.CustInvoiceParcelData;
import com.inerun.courier.helper.SimpleDividerItemDecoration;
import com.inerun.courier.model.AuctionInvoice;
import com.inerun.courier.model.AuctionInvoice_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;


/**
 * Created by vineet on 28/05/18.
 */

public class AuctionAllInvoiceFragment extends AuctionBaseFragment implements View.OnClickListener {

    public RecyclerView detaillistview;
    private AuctionInvoiceListAdapter adapter;
    public TextView error_txt;
    private Drawable mDivider;
    private EditText search_edt;
    private ImageView search_btn;
    private LinearLayout invoice_search_layout;

    private int start = 0;
    private int limit = 4;

    boolean isLoading;
    private LinearLayoutManager mLayoutManager;
    private int visibleThreshold = 5;
    int totalItemCount, lastVisibleItem;
    int total;

    private CustInvoiceParcelData whInvoiceParcelData;
    private List<AuctionInvoice> custInvoiceParcelDataList;


    public static AuctionAllInvoiceFragment newInstance() {

        Bundle args = new Bundle();

        AuctionAllInvoiceFragment fragment = new AuctionAllInvoiceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {

        setShowBackArrow(true);
        return R.layout.auction_all_invoice_fragment;
    }

    @Override
    protected void initView(View rootview) throws IonServiceManager.InvalidParametersException {
        setActionBarTitle(getString(R.string.invoice_title));

        detaillistview = rootview.findViewById(R.id.auction_invoice_listview);
        error_txt = rootview.findViewById(R.id.error_textview);
        mDivider = getDrawable(R.drawable.payment_line_divider);


        invoice_search_layout = rootview.findViewById(R.id.invoice_search_layout);
        search_edt = rootview.findViewById(R.id.invoice_search_edt);
        search_btn = rootview.findViewById(R.id.invoice_search_img_btn);

        search_btn.setOnClickListener(this);

        getData();

        search_edt.setHint("\uD83D\uDD0D  "+ getString(R.string.search_hint));

        search_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchStr = ("" + s.toString()).trim();
                searchRefreshData(searchStr);
            }
        });
    }


    private void getData() {
        Log.i("Query_invoice", SQLite.select().from(AuctionInvoice.class).where(AuctionInvoice_Table.status.eq(1)).orderBy(AuctionInvoice_Table.id, false).getQuery());
		custInvoiceParcelDataList = SQLite.select().from(AuctionInvoice.class).where(AuctionInvoice_Table.status.eq(1)).orderBy(AuctionInvoice_Table.id, false).queryList();
//		custInvoiceParcelDataList = SQLite.select().from(AuctionInvoice.class).where(AuctionInvoice_Table.status.eq(1)).offset(start).limit(limit).queryList();

//		custInvoiceParcelDataList.addAll(custInvoiceParcelDataList);
        setData(custInvoiceParcelDataList);
    }

    private void setData(List<AuctionInvoice> custInvoiceParcelDataList) {
        if (custInvoiceParcelDataList != null && custInvoiceParcelDataList.size() > 0) {
            adapter = new AuctionInvoiceListAdapter(activity(), custInvoiceParcelDataList, clicklistener, this);
            detaillistview.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity());
//            mLayoutManager = new LinearLayoutManager(getActivity());
            detaillistview.setLayoutManager(mLayoutManager);
            detaillistview.setItemAnimator(new DefaultItemAnimator());
//            Drawable mDivider = getDrawable(R.drawable.payment_line_divider);
            detaillistview.addItemDecoration(new SimpleDividerItemDecoration(activity(), mDivider));

            detaillistview.setAdapter(adapter);

//            detaillistview.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                @Override
//                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                    super.onScrolled(recyclerView, dx, dy);
//
//                    totalItemCount = mLayoutManager.getItemCount();
//                    lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
//
////                    Log.i("totalItemCount",""+totalItemCount);
////                    Log.i("lastVisibleItem",""+lastVisibleItem);
//
//                    if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
//                        loadNextDataFromApi(start, limit);
//                        isLoading = true;
//                    }
//
//                }
//            });


            adapter.setOnItemClickListener(new AuctionInvoiceListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    pushFragment(AuctionInvoiceDetailFragment.newInstance(AuctionAllInvoiceFragment.this.custInvoiceParcelDataList.get(position).getInvoice_number()));
                }
            });
            invoice_search_layout.setVisibility(View.VISIBLE);


        } else {
            error_txt.setVisibility(View.VISIBLE);
            detaillistview.setVisibility(View.GONE);
            invoice_search_layout.setVisibility(View.GONE);
//            showSnackbar(R.string.search_error);
//            Toast.makeText(getActivity(), R.string.search_error, Toast.LENGTH_SHORT).show();
        }
    }


    private ClickListener clicklistener = new ClickListener(activity()) {
        @Override
        public void click(View v) throws Exception{

            int index = (int) v.getTag();

            pushFragment(AuctionInvoiceDetailFragment.newInstance(custInvoiceParcelDataList.get(index).getInvoice_number()));
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.invoice_search_img_btn:
                String searchStr = ("" + search_edt.getText()).trim();
                searchRefreshData(searchStr);
                break;
        }
    }

    private void searchRefreshData(String searchStr) {

        if(isValidString(searchStr)){
//            List<AuctionInvoice> invoiceList = SQLite.select().from(AuctionInvoice.class).where(AuctionInvoice_Table.invoice_number.like(searchStr)).or(AuctionInvoice_Table.customer_name.like(searchStr)).and(AuctionInvoice_Table.status.eq(1)).queryList();
            List<AuctionInvoice> invoiceList = SQLite.select().from(AuctionInvoice.class).where(AuctionInvoice_Table.invoice_number.like("%"+searchStr+"%"))
                    .or(AuctionInvoice_Table.customer_name.like("%"+searchStr+"%")).and(AuctionInvoice_Table.status.eq(1)).orderBy(AuctionInvoice_Table.id, false).queryList();

            if(invoiceList != null && invoiceList.size() > 0) {
                if(adapter != null) {
                    adapter.addDataListToList(invoiceList);
                    error_txt.setVisibility(View.GONE);
                    detaillistview.setVisibility(View.VISIBLE);
                }
            }else{
                error_txt.setVisibility(View.VISIBLE);
                detaillistview.setVisibility(View.GONE);
            }
        }else{
            List<AuctionInvoice> invoiceList= SQLite.select().from(AuctionInvoice.class).where(AuctionInvoice_Table.status.eq(1)).orderBy(AuctionInvoice_Table.id, false).queryList();
            if(invoiceList != null && invoiceList.size() > 0) {
                if(adapter != null) {
                    adapter.addDataListToList(invoiceList);
                    error_txt.setVisibility(View.GONE);
                    detaillistview.setVisibility(View.VISIBLE);
                }
            }else{
                error_txt.setVisibility(View.VISIBLE);
                detaillistview.setVisibility(View.GONE);
            }
        }
    }


      private void loadNextDataFromApi(int start1, final int limit1) {

        detaillistview.post(new Runnable() {
            @Override
            public void run() {
                custInvoiceParcelDataList.add(null);
                adapter.notifyItemInserted(custInvoiceParcelDataList.size() - 1);
            }
        });

          final List<AuctionInvoice> auctionInvoiceList = SQLite.select().from(AuctionInvoice.class).where(AuctionInvoice_Table.status.eq(1)).offset(start1).limit(limit1).queryList();

          detaillistview.post(new Runnable() {
              @Override
              public void run() {
                  custInvoiceParcelDataList.remove(custInvoiceParcelDataList.size() - 1);
                  adapter.notifyItemRemoved(custInvoiceParcelDataList.size());

                  int end = start + limit;
                  int size = (total > end) ? end : total;
                  isLoading = total == size;

                  adapter.add(isLoading, auctionInvoiceList);
                  start = custInvoiceParcelDataList.size();
              }
          });
      }
}
