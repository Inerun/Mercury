package com.inerun.courier.activity_warehouse;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.inerun.courier.CourierApplication;
import com.inerun.courier.R;
import com.inerun.courier.adapter.WhRequestListAdapter;
import com.inerun.courier.base.BaseFragment;
import com.inerun.courier.base.ClickListener;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.data.WhReadyParcelData;
import com.inerun.courier.helper.SimpleDividerItemDecoration;
import com.inerun.courier.service.DIRequestCreator;

import java.util.Map;

/**
 * Created by vineet on 2/17/2017.
 */

public class WhReadyForExecutiveFragment extends BaseFragment {

    private static final String READY_REQUEST = "ready_request";
    private Context context;
    private TextView error_txt;
    private RecyclerView recyclerView;
    private WhRequestListAdapter adapter;
    private WhReadyParcelData whReadyParcelData;
    private Drawable mDivider;

    private int start = 0;
    private int limit = 20;

    boolean isLoading;
    private LinearLayoutManager mLayoutManager;
    private int visibleThreshold = 5;
    int totalItemCount, lastVisibleItem;
    int total;


    public static Fragment newInstance() {
        WhReadyForExecutiveFragment fragment = new WhReadyForExecutiveFragment();
        return fragment;
    }

    @Override
    public int inflateView() {
        return R.layout.wh_ready_for_executive;
    }

    @Override
    public void customOnCreateView(View root, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) throws Exception {
        setShowBackArrow(true);
        setToolBarTitle(R.string.ready_for_delivery);
        context = activity();
        intView();
//        getData();

    }


    private void intView() {
        error_txt = (TextView) getViewById(R.id.error_textview);
        recyclerView = (RecyclerView) getViewById(R.id.ready_request_listview);
        mDivider = getDrawable(R.drawable.payment_line_divider);


    }

    private void getData() {
        showProgress();
        Log.i("Start_TIME", "" + System.currentTimeMillis());
        Map<String, String> params = DIRequestCreator.getInstance(activity()).getReadyForExecutiveMapParams(start, limit);

//        DropInsta.serviceManager().postRequest(UrlConstants.URL_READY_FOR_EXECUTIVE_LIST, params, null, response_listener, response_errorlistener, READY_REQUEST);
        CourierApplication.serviceManager().postRequest(UrlConstants.URL_READY_FOR_EXECUTIVE_LIST, params, null, response_listener, response_errorlistener, READY_REQUEST, this);
    }

    Response.Listener<String> response_listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            hideProgress();
            Gson gson = new Gson();

            whReadyParcelData = gson.fromJson(response, WhReadyParcelData.class);

            total = whReadyParcelData.getTotal();
            start = whReadyParcelData.getCustRequestData().size();
            Log.i("END_TIME", "" + System.currentTimeMillis());
            setData(whReadyParcelData);
        }
    };

    Response.ErrorListener response_errorlistener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            hideProgress();
            setData(null);
            showSnackbar(error.getMessage());
            Log.i("Error_MSG", error.getMessage());

        }
    };


    private void setData(final WhReadyParcelData whReadyParcelData) {
        if (whReadyParcelData != null && whReadyParcelData.getCustRequestData() != null && whReadyParcelData.getCustRequestData().size() > 0) {
            adapter = new WhRequestListAdapter(activity(), whReadyParcelData.getCustRequestData(), searchlickListener);
            recyclerView.setHasFixedSize(true);
//            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity());
            mLayoutManager = new LinearLayoutManager(activity());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
//            Drawable mDivider = getDrawable(R.drawable.payment_line_divider);
            recyclerView.addItemDecoration(new SimpleDividerItemDecoration(activity(), mDivider));

            recyclerView.setAdapter(adapter);


            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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


//            scrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
//                @Override
//                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//
//                    Log.i("page",""+page);
//                    Log.i("totalItemsCount",""+totalItemsCount);
//                    // Triggered only when new data needs to be appended to the list
//                    // Add whatever code is needed to append new items to the bottom of the list
//                    start = page *limit;
//                    loadNextDataFromApi(start, limit);
//                }
//            };
//            // Adds the scroll listener to RecyclerView
//            recyclerView.addOnScrollListener(scrollListener);


        } else {
            error_txt.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
//            showSnackbar(R.string.search_error);

        }
    }


    ClickListener searchlickListener = new ClickListener(activity()) {
        @Override
        public void click(View view) throws Exception{
            int pos = (int) view.getTag();
            navigateToFragment(activity(), WhReadyRequestedParcelFragment.newInstance(whReadyParcelData.getCustRequestData().get(pos), whReadyParcelData.getExecutivedata()));

        }
    };


    @Override
    protected String getAnalyticsName() {
        return null;
    }


    private void loadNextDataFromApi(int start, int limit) {

        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                whReadyParcelData.getCustRequestData().add(null);
                adapter.notifyItemInserted(whReadyParcelData.getCustRequestData().size() - 1);
            }
        });

        Log.i("Start_TIME", "loadNextDataFromApi : " + System.currentTimeMillis());
        Map<String, String> params = DIRequestCreator.getInstance(activity()).getReadyForExecutiveMapParams(start, limit);

        CourierApplication.serviceManager().postRequest(UrlConstants.URL_READY_FOR_EXECUTIVE_LIST, params, null, response_listener1, response_errorlistener1, READY_REQUEST);
    }

    Response.Listener<String> response_listener1 = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
//            hideProgress();
            Gson gson = new Gson();

            final WhReadyParcelData whReadyParcelData_new = gson.fromJson(response, WhReadyParcelData.class);
            Log.i("END_TIME", "" + System.currentTimeMillis());

            Log.i("whReadyParcelData_befor", whReadyParcelData.getCustRequestData().size() + "");

            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    whReadyParcelData.getCustRequestData().remove(whReadyParcelData.getCustRequestData().size() - 1);
                    adapter.notifyItemRemoved(whReadyParcelData.getCustRequestData().size());

                    int end = start + limit;
                    int size = (total > end) ? end : total;
                    isLoading = total == size;

                    adapter.add(isLoading, whReadyParcelData_new.getCustRequestData());
                    start = whReadyParcelData.getCustRequestData().size();
                }
            });

        }
    };

    Response.ErrorListener response_errorlistener1 = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
//            hideProgress();

            setData(null);
            showSnackbar(error.getMessage());
            Log.i("Error_MSG", error.getMessage());

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
