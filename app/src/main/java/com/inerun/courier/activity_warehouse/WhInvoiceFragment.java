package com.inerun.courier.activity_warehouse;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.inerun.courier.CourierApplication;
import com.inerun.courier.R;
import com.inerun.courier.adapter.InvoiceAdapter;
import com.inerun.courier.base.BaseFragment;
import com.inerun.courier.base.ClickListener;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.data.WhInvoiceParcelData;
import com.inerun.courier.helper.SimpleDividerItemDecoration;
import com.inerun.courier.service.DIRequestCreator;

import java.util.Map;

/**
 * Created by vineet on 2/17/2017.
 */

public class WhInvoiceFragment extends BaseFragment {

    private static final String READY_INVOICE = "ready_invoice";
    private Context context;
    private TextView error_txt;
    private RecyclerView detaillistview;
    private InvoiceAdapter adapter;
    private WhInvoiceParcelData whInvoiceParcelData;
    private int start = 0;
    private int limit = 20;

    public static Fragment newInstance() {
        WhInvoiceFragment fragment = new WhInvoiceFragment();
        return fragment;
    }

    @Override
    public int inflateView() {
        return R.layout.wh_ready_invoice;
    }

    @Override
    public void customOnCreateView(View root, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) throws Exception {
        setShowBackArrow(true);
        setToolBarTitle(R.string.invoices);
        context = activity();
        intView();
        getData();

    }


    private void intView() {
        error_txt = (TextView) getViewById(R.id.error_textview);
        detaillistview = (RecyclerView) getViewById(R.id.detail_listview);

    }

    private void getData() {
        Map<String, String> params = DIRequestCreator.getInstance(activity()).getReadyInvoiceMapParams(start, limit);

        CourierApplication.serviceManager().postRequest(UrlConstants.URL_READY_INVOICE_LIST, params, getProgress(), response_listener, response_errorlistener, READY_INVOICE);
    }

    Response.Listener<String> response_listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            hideProgress();
            Gson gson = new Gson();

            whInvoiceParcelData = gson.fromJson(response, WhInvoiceParcelData.class);

            setData(whInvoiceParcelData);
        }
    };

    Response.ErrorListener response_errorlistener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            hideProgress();
            setData(null);
            showSnackbar(error.getMessage());

        }
    };


    private void setData(final WhInvoiceParcelData whInvoiceParcelData) {
        if (whInvoiceParcelData != null && whInvoiceParcelData.getInvoiceData() != null && whInvoiceParcelData.getInvoiceData().size() > 0) {
            adapter = new InvoiceAdapter(activity(), whInvoiceParcelData.getInvoiceData(), searchlickListener);
            detaillistview.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity());
            detaillistview.setLayoutManager(mLayoutManager);
            detaillistview.setItemAnimator(new DefaultItemAnimator());
            Drawable mDivider = getDrawable(R.drawable.payment_line_divider);
            detaillistview.addItemDecoration(new SimpleDividerItemDecoration(activity(), mDivider));

            detaillistview.setAdapter(adapter);


        }else {
            error_txt.setVisibility(View.VISIBLE);
            detaillistview.setVisibility(View.GONE);
            showSnackbar(R.string.search_error);

        }
    }

    ClickListener searchlickListener = new ClickListener(activity()) {
        @Override
        public void click(View view) throws Exception{
            int pos = (int) view.getTag();
            navigateToFragment(activity(),WhInvoiceReadyParcelFragment.newInstance(whInvoiceParcelData.getInvoiceData().get(pos), whInvoiceParcelData.getExecutivedata()));

        }
    };


    @Override
    protected String getAnalyticsName() {
        return null;
    }
}
