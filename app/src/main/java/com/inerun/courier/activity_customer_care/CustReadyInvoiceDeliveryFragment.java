package com.inerun.courier.activity_customer_care;

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
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.inerun.courier.CourierApplication;
import com.inerun.courier.R;
import com.inerun.courier.adapter.CustInvoiceListAdapter;
import com.inerun.courier.base.BaseFragment;
import com.inerun.courier.base.ClickListener;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.data.CustInvoiceParcelData;
import com.inerun.courier.helper.SimpleDividerItemDecoration;
import com.inerun.courier.service.DIRequestCreator;

import java.util.Map;

/**
 * Created by vineet on 2/17/2017.
 */

public class  CustReadyInvoiceDeliveryFragment extends BaseFragment {

    private static final String READY_INVOICE = "ready_invoice";
    private Context context;
    private TextView error_txt;
    private RecyclerView detaillistview;
    private CustInvoiceListAdapter adapter;
    private CustInvoiceParcelData whInvoiceParcelData;
    private Drawable mDivider;


    private int start = 0;
    private int limit = 20;

    boolean isLoading;
    private LinearLayoutManager mLayoutManager;
    private int visibleThreshold = 5;
    int totalItemCount, lastVisibleItem;
    int total;

    public static Fragment newInstance() {
        CustReadyInvoiceDeliveryFragment fragment = new CustReadyInvoiceDeliveryFragment();
        return fragment;
    }

    @Override
    public int inflateView() {
        return R.layout.cust_ready_invoice;
    }

    @Override
    public void customOnCreateView(View root, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) throws Exception {
        setShowBackArrow(true);
        setToolBarTitle(R.string.invoices);
        context = activity();
        intView();
//        getData();

    }


    private void intView() {
        error_txt = (TextView) getViewById(R.id.error_textview);
        detaillistview = (RecyclerView) getViewById(R.id.detail_listview);
        mDivider = getDrawable(R.drawable.payment_line_divider);

    }

    private void getData() {
        showProgress();
        Map<String, String> params = DIRequestCreator.getInstance(activity()).getReadyInvoiceMapParams(start, limit);

        CourierApplication.serviceManager().postRequest(UrlConstants.URL_READY_INVOICE_LIST, params, getProgress(), response_listener, response_errorlistener, READY_INVOICE);
    }

    Response.Listener<String> response_listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            hideProgress();
            Gson gson = new Gson();

            whInvoiceParcelData = gson.fromJson(response, CustInvoiceParcelData.class);

            total = whInvoiceParcelData.getTotal();
            start = whInvoiceParcelData.getInvoiceData().size();

            setData(whInvoiceParcelData);

//            new Timer().schedule(new TimerTask() {
//
//                @Override
//                public void run() {
//
//                    activity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            hideProgress();
//                            setData(whInvoiceParcelData);
//                        }
//
//                    });
//
//                }
//            }, 4000);


        }
    };

    Response.ErrorListener response_errorlistener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            hideProgress();
            setData(null);
//            showSnackbar(error.getMessage());
            Toast.makeText(activity(), error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };


    private void setData(final CustInvoiceParcelData whInvoiceParcelData) {
        if (whInvoiceParcelData != null && whInvoiceParcelData.getInvoiceData() != null && whInvoiceParcelData.getInvoiceData().size() > 0) {
            adapter = new CustInvoiceListAdapter(activity(), whInvoiceParcelData.getInvoiceData(), searchlickListener);
            detaillistview.setHasFixedSize(true);
//            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity());
            mLayoutManager = new LinearLayoutManager(activity());
            detaillistview.setLayoutManager(mLayoutManager);
            detaillistview.setItemAnimator(new DefaultItemAnimator());
//            Drawable mDivider = getDrawable(R.drawable.payment_line_divider);
            detaillistview.addItemDecoration(new SimpleDividerItemDecoration(activity(), mDivider));

            detaillistview.setAdapter(adapter);

            detaillistview.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = mLayoutManager.getItemCount();
                    lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();

//                    Log.i("totalItemCount",""+totalItemCount);
//                    Log.i("lastVisibleItem",""+lastVisibleItem);

                    if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        loadNextDataFromApi(start, limit);
                        isLoading = true;
                    }

                }
            });




        }else {
            error_txt.setVisibility(View.VISIBLE);
            detaillistview.setVisibility(View.GONE);
//            showSnackbar(R.string.search_error);
//            Toast.makeText(activity(), R.string.search_error, Toast.LENGTH_SHORT).show();
        }
    }

   ClickListener searchlickListener = new ClickListener(activity()) {
        @Override
        public void click(View view) throws Exception{
            int pos = (int) view.getTag();
            navigateToFragment(activity(),CustReadyParcelFragment.newInstance(whInvoiceParcelData.getInvoiceData().get(pos)));

        }
    };


    @Override
    protected String getAnalyticsName() {
        return null;
    }


    private void loadNextDataFromApi(int start, int limit) {

        detaillistview.post(new Runnable() {
            @Override
            public void run() {
                whInvoiceParcelData.getInvoiceData().add(null);
                adapter.notifyItemInserted(whInvoiceParcelData.getInvoiceData().size() - 1);
            }
        });

        Map<String, String> params = DIRequestCreator.getInstance(activity()).getReadyInvoiceMapParams(start, limit);

        CourierApplication.serviceManager().postRequest(UrlConstants.URL_READY_INVOICE_LIST, params, getProgress(), response_listener1, response_errorlistener1, READY_INVOICE);
    }

    Response.Listener<String> response_listener1 = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            hideProgress();
            Gson gson = new Gson();

            final CustInvoiceParcelData whInvoiceParcelData_new = gson.fromJson(response, CustInvoiceParcelData.class);

            detaillistview.post(new Runnable() {
                @Override
                public void run() {
                    whInvoiceParcelData.getInvoiceData().remove(whInvoiceParcelData.getInvoiceData().size() - 1);
                    adapter.notifyItemRemoved(whInvoiceParcelData.getInvoiceData().size());

                    int end = start + limit;
                    int size = (total > end) ? end : total;
                    isLoading = total == size;

                    adapter.add(isLoading, whInvoiceParcelData_new.getInvoiceData());
                    start = whInvoiceParcelData.getInvoiceData().size();
                }
            });
        }
    };

    Response.ErrorListener response_errorlistener1 = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            hideProgress();
            setData(null);
//            showSnackbar(error.getMessage());
            Toast.makeText(activity(), error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
  @Override
    public void onResume() {
        start = 0;
        total = 0;
        totalItemCount = 0;
        lastVisibleItem = 0;
        isLoading = false;
        getData();
        super.onResume();
    }
}
