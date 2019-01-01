package com.inerun.courier.activity_customer_care;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.inerun.courier.CourierApplication;
import com.inerun.courier.Exception.ExceptionMessages;
import com.inerun.courier.R;
import com.inerun.courier.activity_driver.ParcelDetailActivity;
import com.inerun.courier.adapter.DeliverySearchAdapter;
import com.inerun.courier.base.BaseFragment;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.data.ParcelListingData;
import com.inerun.courier.helper.DIHelper;
import com.inerun.courier.service.DIRequestCreator;
import com.inerun.courier.sql.DIDbHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by vineet on 2/20/2017.
 */

public class CustReturnParcelFragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final String RETURN_PARCEL = "return_parcel";
    private ArrayList<ParcelListingData.ParcelData> parcellist;

    private Button submit;
    private RecyclerView recyclerView;
    private DeliverySearchAdapter mAdapter;
    private CheckBox selectall_checkbox;
    ArrayList<ParcelListingData.ParcelData> returnselectedparcellist;
    private RelativeLayout checkbox_layout;
    private LinearLayout btn_layout;

    private int start = 0;
    private int limit = 20;

    public static Fragment newInstance() {
        CustReturnParcelFragment fragment = new CustReturnParcelFragment();
        return fragment;
    }

    @Override
    public int inflateView() {
        return R.layout.cust_return_parcel;
    }

    @Override
    public void customOnCreateView(View root, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) throws Exception {
        setShowBackArrow(true);
        setToolBarTitle(R.string.return_parcel_title);
        initView();
    }

    private void initView() {
        checkbox_layout = (RelativeLayout) getViewById(R.id.checkbox_layout);
        recyclerView = (RecyclerView) getViewById(R.id.return_parcel_listview);
        submit = (Button) getViewById(R.id.parcel_return_submit);

        submit.setOnClickListener(this);

        selectall_checkbox = (CheckBox) getViewById(R.id.checkbox_allselected);
        selectall_checkbox.setOnCheckedChangeListener(this);
        btn_layout = (LinearLayout) getViewById(R.id.btn_layout);
    }


    @Override
    public void onResume() {
        super.onResume();
        getData();
//        setdata();
    }

    private void getData() {
//        parcellist = DIDbHelper.getPendingParcelInfoForListing(activity());
        Map<String, String> params = DIRequestCreator.getInstance(activity()).getReadyInvoiceMapParams(start, limit);

        CourierApplication.serviceManager().postRequest(UrlConstants.URL_RETURN_PARCEL_LIST, params, getProgress(), return_response_listener, return_response_errorlistener, RETURN_PARCEL);

    }

    Response.Listener<String> return_response_listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            hideProgress();
            Gson gson = new Gson();

            ParcelListingData parcelListingData = gson.fromJson(response, ParcelListingData.class);
            parcellist = parcelListingData.getDeliveryData();
//            setData(parcelListingData.getDeliveryData());
            setdata();
        }
    };

    Response.ErrorListener return_response_errorlistener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            hideProgress();
//            setData(null);
            showSnackbar(error.getMessage());

        }
    };

    private void setdata() {

        mAdapter = new DeliverySearchAdapter(activity(), parcellist);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//            recyclerView.addItemDecoration(new DividerItemDecoration(getDrawable(this, R.drawable.parcel_item_thumbnail_divider)));
//        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(activity()));

        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new DeliverySearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.i("onItemClick", "OnClick");
                Bundle bundle = new Bundle();
                bundle.putSerializable(UrlConstants.KEY_DATA, parcellist.get(position));

                gotoActivity(ParcelDetailActivity.class, bundle);
                //slide from right to left
                activity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            }
        });

        if(parcellist != null && parcellist.size() > 0) {
            checkbox_layout.setVisibility(View.VISIBLE);
            btn_layout.setVisibility(View.VISIBLE);
        }else{
            checkbox_layout.setVisibility(View.GONE);
            btn_layout.setVisibility(View.GONE);
        }
    }

    @Override
    protected String getAnalyticsName() {
        return null;
    }


    @Override
    public void onClick(View view) {

        ArrayList<ParcelListingData.ParcelData> selectedparcellist;

        switch (view.getId()) {
            case R.id.parcel_return_submit:

                selectedparcellist = mAdapter.getSelectedParcels();
                if (validateParcels(selectedparcellist)) {
                    returnParcel(selectedparcellist);
                }
                break;
        }
    }


    private boolean validateParcels(ArrayList<ParcelListingData.ParcelData> selectedparcellist) {
        if (selectedparcellist != null && selectedparcellist.size() > 0) {
            return true;
        } else {
            showSnackbar(R.string.selected_parcel_error);
        }
        return false;
    }

    private void returnParcel(ArrayList<ParcelListingData.ParcelData> selectedparcellist) {

        try {
            returnselectedparcellist= new ArrayList<>();
            returnselectedparcellist.addAll(selectedparcellist);
            Map<String, String> params = DIRequestCreator.getInstance(activity()).getReturnParcelMapParams(selectedparcellist);
            CourierApplication.serviceManager().postRequest(UrlConstants.URL_RETURN_PARCEL, params, getProgress(), response_listener, response_errorlistener, "RETURN_PARCEL");
        } catch (JSONException e) {
            e.printStackTrace();
            showSnackbar(R.string.exception_alert_message_parsing_exception);
        }

    }


    Response.Listener response_listener = new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {

//            Log.d("Response: ", response.toString());
            try {
                JSONObject jsonObject = new JSONObject(response);

                showSnackbar(DIHelper.getMessage(jsonObject));
                if (DIHelper.getStatus(jsonObject)) {

                    Log.i("DB", "DeliveryReturnParcelFragment: " + System.currentTimeMillis());
                    DIDbHelper.updateReturnParcelStatus(activity(),returnselectedparcellist);
                    getData();
                    setdata();

                } else {

//                    login_btn.setClickable(true);

                }

            } catch (JSONException e) {
                e.printStackTrace();

                showSnackbar(ExceptionMessages.getMessageFromException(activity(), -1, e));

            } catch (Exception e) {
                e.printStackTrace();

                showSnackbar(ExceptionMessages.getMessageFromException(activity(), -1, e));
            }

            hideProgress();
        }
    };


    Response.ErrorListener response_errorlistener = new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("onErrorResponse: ", error.toString());
            hideProgress();

            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                showSnackbar(R.string.exception_alert_message_timeout_exception);
            } else if (error instanceof AuthFailureError) {
                //TODO
                showSnackbar("AuthFailure Error");
            } else if (error instanceof ServerError) {
                //TODO
                showSnackbar(R.string.exception_alert_message_internal_server_error);
            } else if (error instanceof NetworkError) {
                //TODO
                showSnackbar(R.string.exception_alert_message_network);
            } else if (error instanceof ParseError) {
                //TODO
                showSnackbar(R.string.exception_alert_message_parsing_exception);
            } else {
                showSnackbar(R.string.exception_alert_message_error);
            }
        }
    };

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        mAdapter.selectAllParcels(compoundButton.isChecked());
    }
}
