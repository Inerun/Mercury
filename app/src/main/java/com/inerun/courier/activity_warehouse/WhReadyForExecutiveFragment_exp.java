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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.inerun.courier.CourierApplication;
import com.inerun.courier.R;
import com.inerun.courier.adapter.WhRequestListAdapter_exp;
import com.inerun.courier.base.BaseFragment;
import com.inerun.courier.base.ClickListener;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.data.WhReadyParcelData;
import com.inerun.courier.data.WhReadyParcelData_New;
import com.inerun.courier.helper.EndlessRecyclerViewScrollListener;
import com.inerun.courier.helper.SimpleDividerItemDecoration;
import com.inerun.courier.service.DIRequestCreator;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by vineet on 2/17/2017.
 */

// tigmoo api implemented
public class WhReadyForExecutiveFragment_exp extends BaseFragment {

    private static final String READY_REQUEST = "ready_request";
    private Context context;
    private TextView error_txt;
    private RecyclerView recyclerView;
    private WhRequestListAdapter_exp adapter;
    private WhReadyParcelData whReadyParcelData;
    private WhReadyParcelData_New whReadyParcelData1;
    private Drawable mDivider;
    private ProgressBar progressBar_bootom;

    private int start = 0;
    private int limit =20;

    boolean isLoading;
    private LinearLayoutManager mLayoutManager;
    private int visibleThreshold = 5;
    int totalItemCount,lastVisibleItem;
    int total;

    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener scrollListener;

    public static Fragment newInstance() {
        WhReadyForExecutiveFragment_exp fragment = new WhReadyForExecutiveFragment_exp();
        return fragment;
    }

    @Override
    public int inflateView() {
        return R.layout.wh_ready_for_executive;
    }

    @Override
    public void customOnCreateView(View root, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) throws Exception {
        whReadyParcelData1 = new WhReadyParcelData_New();
        ArrayList<WhReadyParcelData_New.ProductData> data = new ArrayList<>();
        whReadyParcelData1.setProductData(data);
        setShowBackArrow(true);
        setToolBarTitle(R.string.ready_for_delivery);
        context = activity();
        intView();
        getData();
//        setData();

    }


    private void intView() {
        error_txt = (TextView) getViewById(R.id.error_textview);
        recyclerView = (RecyclerView) getViewById(R.id.ready_request_listview);
        mDivider = getDrawable(R.drawable.payment_line_divider);
        progressBar_bootom = (ProgressBar) getViewById(R.id.progressBar_bottom);


    }

    private void getData() {
        Log.i("Start_TIME", "" + System.currentTimeMillis());
//        Map<String, String> params = DIRequestCreator.getInstance(activity()).getReadyForExecutiveMapParams(start,limit);
        Map<String, String> params = DIRequestCreator.getInstance(activity()).getReadyForExecutiveMapParams_exp(start, limit);

        CourierApplication.serviceManager().postRequest(UrlConstants.URL_DEMO, params, getProgress(), response_listener, response_errorlistener, READY_REQUEST);
    }

    Response.Listener<String> response_listener =  new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            hideProgress();
            Gson gson = new Gson();
//            whReadyParcelData = gson.fromJson(response, WhReadyParcelData.class);
            whReadyParcelData1 = gson.fromJson(response, WhReadyParcelData_New.class);
            total = whReadyParcelData1.getTotal();
            start = whReadyParcelData1.getProductData().size();
            Log.i("END_TIME", "" + System.currentTimeMillis());
            setData(whReadyParcelData1);
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


    private void setData(final WhReadyParcelData_New whReadyParcelData) {
        if (whReadyParcelData != null && whReadyParcelData.getProductData() != null && whReadyParcelData.getProductData().size() > 0) {
            adapter = new WhRequestListAdapter_exp(activity(), whReadyParcelData.getProductData(), searchlickListener);
            recyclerView.setHasFixedSize(true);
//            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity());
            mLayoutManager = new LinearLayoutManager(activity());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
//            Drawable mDivider = getDrawable(R.drawable.payment_line_divider);
            recyclerView.addItemDecoration(new SimpleDividerItemDecoration(activity(), mDivider));

            recyclerView.setAdapter(adapter);


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



            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = mLayoutManager.getItemCount();
                    lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();

                    if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        loadNextDataFromApi(start, limit);
                        isLoading = true;
                    }

                }
            });





        }else {
            error_txt.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
//            showSnackbar(R.string.search_error);

        }
    }

//    private void setData() {
//            adapter = new WhRequestListAdapter_exp(activity(), whReadyParcelData1.getProductData(), searchlickListener);
//            recyclerView.setHasFixedSize(true);
////            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity());
//            LinearLayoutManager mLayoutManager = new LinearLayoutManager(activity());
//            recyclerView.setLayoutManager(mLayoutManager);
//            recyclerView.setItemAnimator(new DefaultItemAnimator());
////            Drawable mDivider = getDrawable(R.drawable.payment_line_divider);
//            recyclerView.addItemDecoration(new SimpleDividerItemDecoration(activity(), mDivider));
//
//            recyclerView.setAdapter(adapter);
//            loadNextDataFromApi(start, limit);
//
//
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
//
//
//
//    }

    ClickListener searchlickListener = new ClickListener(activity()) {
        @Override
        public void click(View view) throws Exception{
            int pos = (int) view.getTag();
            navigateToFragment(activity(),WhReadyRequestedParcelFragment.newInstance(whReadyParcelData.getCustRequestData().get(pos), whReadyParcelData.getExecutivedata()));

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
                whReadyParcelData1.getProductData().add(null);
                adapter.notifyItemInserted(whReadyParcelData1.getProductData().size() - 1);
            }
        });


        Log.i("Start_TIME", "loadNextDataFromApi : " + System.currentTimeMillis());
//        Map<String, String> params = DIRequestCreator.getInstance(activity()).getReadyForExecutiveMapParams(start, limit);
        Map<String, String> params = DIRequestCreator.getInstance(activity()).getReadyForExecutiveMapParams_exp(start, limit);

        CourierApplication.serviceManager().postRequest(UrlConstants.URL_DEMO, params, null, response_listener1, response_errorlistener1, READY_REQUEST);
    }

    Response.Listener<String> response_listener1 =  new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
//            hideProgress();
//            progressBar_bootom.setVisibility(View.GONE);
            Gson gson = new Gson();

//            WhReadyParcelData whReadyParcelData_new = gson.fromJson(response, WhReadyParcelData.class);
            final WhReadyParcelData_New whReadyParcelData_new = gson.fromJson(response, WhReadyParcelData_New.class);
            Log.i("END_TIME", "" + System.currentTimeMillis());

//            whReadyParcelData1.getCustRequestData().addAll(whReadyParcelData_new.getCustRequestData());
//            adapter.add(whReadyParcelData.getCustRequestData());



            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    whReadyParcelData1.getProductData().remove(whReadyParcelData1.getProductData().size() - 1);
                    adapter.notifyItemRemoved(whReadyParcelData1.getProductData().size());

                    int end = start + limit;
                    int size = (total > end) ? end : total;
                    isLoading = total == size;

                    adapter.add(isLoading, whReadyParcelData_new.getProductData());
                    start = whReadyParcelData1.getProductData().size();
                }
            });

        }
    };

    Response.ErrorListener response_errorlistener1 = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
//            hideProgress();
//            progressBar_bootom.setVisibility(View.GONE);
            setData(null);
            showSnackbar(error.getMessage());
            Log.i("Error_MSG", error.getMessage());

        }
    };
}
