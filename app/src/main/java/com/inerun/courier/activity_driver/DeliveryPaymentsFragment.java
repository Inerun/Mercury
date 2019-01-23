package com.inerun.courier.activity_driver;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inerun.courier.R;
import com.inerun.courier.adapter.PaymentAdapter;
import com.inerun.courier.base.BaseFragment;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.data.ParcelListingData;
import com.inerun.courier.helper.SimpleDividerItemDecoration;
import com.inerun.courier.sql.DIDbHelper;

import java.util.ArrayList;

/**
 * Created by vinay on 16/01/17.
 */

public class DeliveryPaymentsFragment extends BaseFragment implements View.OnClickListener {

    private TextView cash, card;
    private RecyclerView recyclerView;
    private PaymentAdapter mAdapter;
    private ArrayList<ParcelListingData.ParcelData> parcellist;
    private ArrayList<ParcelListingData.ParcelData> cashpaymentlist;
    private ArrayList<ParcelListingData.ParcelData> cardpaymentlist;
    private int tab_selection;

    public static Fragment newInstance(int tab_selection) {
        Bundle bundle = new Bundle();
        DeliveryPaymentsFragment fragment = new DeliveryPaymentsFragment();
        bundle.putInt(UrlConstants.KEY_TAB_SELECTION,tab_selection);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tab_selection = getArguments().getInt(UrlConstants.KEY_TAB_SELECTION);
    }

    @Override
    public int inflateView() {
        return R.layout.activity_delivery_payment_collection;
    }

    @Override
    public void customOnCreateView(View root, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setShowBackArrow(true);
        initView(root);
        getData();
        setData();
    }

    private void initView(View root) {
        cash = (TextView) root.findViewById(R.id.cash);
        card = (TextView) root.findViewById(R.id.card);

        cash.setOnClickListener(this);
        card.setOnClickListener(this);
//        selectTab(1);

        recyclerView = (RecyclerView) getViewById(R.id.payment_recycler);


    }

    private void getData() {
//        parcellist = DIDbHelper.getDeliveryInfoForListing(activity());
        parcellist = DIDbHelper.getCashPayments(activity());
        setData();

    }

    private void setData() {
        mAdapter = new PaymentAdapter(activity(), parcellist);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//            recyclerView.addItemDecoration(new DividerItemDecoration(getDrawable(this, R.drawable.parcel_item_thumbnail_divider)));
//        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(activity()));
       Drawable mDivider = getDrawable(R.drawable.payment_line_divider);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(activity(),mDivider));

        recyclerView.setAdapter(mAdapter);
        cash.setText(getString(R.string.payment_collection_cash, DIDbHelper.getTotalCashPayment(activity())));
        card.setText(getString(R.string.payment_collection_card, DIDbHelper.getTotalCardPayment(activity())));

        selectTab(tab_selection);
    }

    @Override
    protected String getAnalyticsName() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cash:
                selectTab(1);
//                mAdapter.setParcelDataList(DIDbHelper.getDeliveryInfoForListing(activity()));
//                if(cashpaymentlist==null) {
//                    cashpaymentlist = DIDbHelper.getCashPayments(activity());
//                }
//                mAdapter.setParcelDataList(cashpaymentlist);
//
//                mAdapter.notifyDataSetChanged();
                break;
            case R.id.card:
                selectTab(2);
//                mAdapter.setParcelDataList(DIDbHelper.getDeliveryInfoForListing(activity()));
//                if(cardpaymentlist==null) {
//                    cardpaymentlist = DIDbHelper.getCardPayments(activity());
//                }
//                mAdapter.setParcelDataList(cardpaymentlist);
//
//                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void selectTab(int selection) {

        switch (selection) {
            case 1:
                cash.setBackgroundColor(getColor(R.color.colorAccent));
                card.setBackgroundColor(getColor(R.color.white));

                cash.setTextColor(getColor(R.color.white));
                card.setTextColor(getColor(R.color.text_color));
                setCashAdapter();
                break;
            case 2:
                cash.setBackgroundColor(getColor(R.color.white));
                card.setBackgroundColor(getColor(R.color.colorAccent));

                cash.setTextColor(getColor(R.color.text_color));
                card.setTextColor(getColor(R.color.white));
                setCardAdapter();
                break;
            default:
                cash.setBackgroundColor(getColor(R.color.colorAccent));
                card.setBackgroundColor(getColor(R.color.white));

                cash.setTextColor(getColor(R.color.white));
                card.setTextColor(getColor(R.color.text_color));
                setCashAdapter();
                break;

        }

    }




    private ArrayList<ParcelListingData.ParcelData> makeCashData(){
        ArrayList<ParcelListingData.ParcelData> list = new ArrayList<>();

        ParcelListingData p = new ParcelListingData();

        ParcelListingData.ParcelData pp = p.new ParcelData("#333322222", "100", "K");
        list.add(pp);
        ParcelListingData.ParcelData pp1 = p.new ParcelData("#333322777", "1000", "K");
        list.add(pp1);
        ParcelListingData.ParcelData pp2 = p.new ParcelData("#333324444", "500", "K");
        list.add(pp2);

        return list;

    }


    private ArrayList<ParcelListingData.ParcelData> makeCardData(){
        ArrayList<ParcelListingData.ParcelData> list = new ArrayList<>();

        ParcelListingData p = new ParcelListingData();

        ParcelListingData.ParcelData pp = p.new ParcelData("#11111111", "1001", "$");
        list.add(pp);
        ParcelListingData.ParcelData pp1 = p.new ParcelData("#32121212", "1000", "$");
        list.add(pp1);
        ParcelListingData.ParcelData pp2 = p.new ParcelData("#323555wewe", "23221", "$");
        list.add(pp2);
        ParcelListingData.ParcelData pp3 = p.new ParcelData("#323555wewe", "23221", "$");
        list.add(pp3);
        ParcelListingData.ParcelData pp4 = p.new ParcelData("#323555wewe", "23221", "$");
        list.add(pp4);


        return list;

    }

    private void setCashAdapter(){
        if(cashpaymentlist==null) {
            cashpaymentlist = DIDbHelper.getCashPayments(activity());
        }
        mAdapter.setParcelDataList(cashpaymentlist);

        mAdapter.notifyDataSetChanged();
    }

    private void setCardAdapter(){
        if(cardpaymentlist==null) {
            cardpaymentlist = DIDbHelper.getCardPayments(activity());
        }
        mAdapter.setParcelDataList(cardpaymentlist);

        mAdapter.notifyDataSetChanged();

    }
}
