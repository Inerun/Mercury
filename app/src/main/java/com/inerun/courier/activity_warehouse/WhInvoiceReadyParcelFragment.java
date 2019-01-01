package com.inerun.courier.activity_warehouse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.inerun.courier.CourierApplication;
import com.inerun.courier.Exception.ExceptionMessages;
import com.inerun.courier.R;
import com.inerun.courier.activity_driver.ParcelDetailFragment;
import com.inerun.courier.adapter.CustomerExecutiveAdapter;
import com.inerun.courier.adapter.WhReadyParcelAdapter;
import com.inerun.courier.base.BaseFragment;
import com.inerun.courier.base.ClickListener;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.data.CustomerExecutiveData;
import com.inerun.courier.data.ParcelListingData;
import com.inerun.courier.data.ReadyParcelData;
import com.inerun.courier.data.WhInvoiceParcelData;
import com.inerun.courier.helper.DIHelper;
import com.inerun.courier.scanner.CameraTestActivity;
import com.inerun.courier.service.DIRequestCreator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by vineet on 2/17/2017.
 */

public class WhInvoiceReadyParcelFragment extends BaseFragment implements View.OnClickListener {

    private static final int PARCEL_SCAN = 102;
    private static final String SEARCH = "SEARCH_SERVICE";
    private static final String DELIVERED = "customer_support_delivered";
    private Context context;
    private LinearLayout submit_layout, parcel_scan_layout;
    private TextView error_txt;
    private RecyclerView detaillistview;
    private WhReadyParcelAdapter adapter;
    private Button delivery_button;
    private ArrayList<ParcelListingData.ParcelData> selectedparcelDataArrayList;
    private ArrayList<ReadyParcelData> updatedArrayList;
    private Spinner executiveSpinner;
    private CustomerExecutiveAdapter customerExecutiveAdapter;
    private WhInvoiceParcelData.Invoice invoice;
    private ArrayList<CustomerExecutiveData> executivedata;

    public static Fragment newInstance(WhInvoiceParcelData.Invoice invoice, ArrayList<CustomerExecutiveData> executivedata) {
        WhInvoiceReadyParcelFragment fragment = new WhInvoiceReadyParcelFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(UrlConstants.KEY_DATA, invoice);
        bundle.putSerializable("EXECUTIVE_KEY", executivedata);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        invoice  = (WhInvoiceParcelData.Invoice) getArguments().getSerializable(UrlConstants.KEY_DATA);
        executivedata  = (ArrayList<CustomerExecutiveData>) getArguments().getSerializable("EXECUTIVE_KEY");
        CustomerExecutiveData executiveData_hint = new CustomerExecutiveData("", getString(R.string.customer_executive_hint));
        executivedata.add(executiveData_hint);
    }

    @Override
    public int inflateView() {
        return R.layout.wh_ready_invoice_parcel;
    }

    @Override
    public void customOnCreateView(View root, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) throws Exception {
        setShowBackArrow(true);
        context = activity();

        intView();
        setSearchData(invoice, executivedata);
    }

    private void intView() {
        error_txt = (TextView) getViewById(R.id.error_textview);
        parcel_scan_layout = (LinearLayout) getViewById(R.id.parcel_scan_layout);
        detaillistview = (RecyclerView) getViewById(R.id.detail_listview);

        submit_layout = (LinearLayout) getViewById(R.id.submit_layout);
        delivery_button = (Button) getViewById(R.id.delivery_button);

        delivery_button.setOnClickListener(this);
        parcel_scan_layout.setOnClickListener(this);
        executiveSpinner = (Spinner) getViewById(R.id.customer_care_spinner);

    }

    @Override
    protected String getAnalyticsName() {
        return null;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.parcel_scan_layout:
                startActivityForResult(new Intent(activity(), CameraTestActivity.class), PARCEL_SCAN);
                break;

            case R.id.delivery_button:
                parcelDelivered();
                break;
        }
    }


    private void setSearchData(WhInvoiceParcelData.Invoice searchParcelData, ArrayList<CustomerExecutiveData> executivedata) {

        if (searchParcelData != null && searchParcelData.getParcelData() != null && searchParcelData.getParcelData().size() > 0) {
            detaillistview.setVisibility(View.VISIBLE);
            parcel_scan_layout.setVisibility(View.VISIBLE);
            adapter = new WhReadyParcelAdapter(activity(), searchParcelData.getParcelData(), searchlickListener);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity());
            detaillistview.setLayoutManager(mLayoutManager);
            detaillistview.setItemAnimator(new DefaultItemAnimator());
            detaillistview.setAdapter(adapter);
            submit_layout.setVisibility(View.VISIBLE);

            setExecutiveData(executivedata);

        } else {
            error_txt.setVisibility(View.VISIBLE);
            detaillistview.setVisibility(View.GONE);
            submit_layout.setVisibility(View.GONE);
            parcel_scan_layout.setVisibility(View.GONE);
            showSnackbar(R.string.search_error);

        }


    }

    private void setExecutiveData(ArrayList<CustomerExecutiveData> executivedata) {

        customerExecutiveAdapter = new CustomerExecutiveAdapter(activity(), android.R.layout.simple_spinner_item, executivedata);
        customerExecutiveAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        executiveSpinner.setAdapter(customerExecutiveAdapter);
        executiveSpinner.setSelection(executivedata.size() - 1);
    }

    ClickListener searchlickListener = new ClickListener(activity()) {
        @Override
        public void click(View view) throws Exception{
            int pos = (int) view.getTag();

            ParcelListingData.ParcelData parceldata = adapter.getParcelDataList().get(pos);
            ArrayList<ParcelListingData.ParcelData> parcelDataArrayList = new ArrayList<>();
            parcelDataArrayList.add(parceldata);

            navigateToFragment(activity(), ParcelDetailFragment.newInstance(parceldata));


        }
    };


    private void parcelDelivered() {

        String customerCareId = "";
        customerCareId = ((CustomerExecutiveData) executiveSpinner.getSelectedItem()).getId();

        selectedparcelDataArrayList = new ArrayList<>();
        updatedArrayList = new ArrayList<>();
        for (int i = 0; i < adapter.getParcelDataList().size(); i++) {
            if (adapter.getParcelDataList().get(i).isselected()) {
                selectedparcelDataArrayList.add(adapter.getParcelDataList().get(i));
            }
        }

        if (selectedparcelDataArrayList.size() == adapter.getParcelDataList().size() ) {

            if(isStringValid(customerCareId)) {

                for (int i = 0; i < selectedparcelDataArrayList.size(); i++) {
                    ParcelListingData.ParcelData parceldata = selectedparcelDataArrayList.get(i);
                    ReadyParcelData readyParcelData = new ReadyParcelData(parceldata.getBarcode());

//                selectedparcelDataArrayList.set(i, parceldata);
                    updatedArrayList.add(readyParcelData);
                }

                Map<String, String> params = DIRequestCreator.getInstance(activity()).getReadyParcelDeliveredMapParamsForCustSupport(invoice.getInvoice_number(), customerCareId);

                CourierApplication.serviceManager().postRequest(UrlConstants.URL_INVOICE_DELIVERED_CUSTOMER, params, getProgress(), response_listener_delivery, response_errorlistener_delivery, DELIVERED);

            }else{
                showSnackbar(R.string.customer_care_error);
            }
        } else {
            showSnackbar(R.string.select_all_parcel);
        }


    }

    Response.Listener<String> response_listener_delivery = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            try {
                JSONObject jsonObject = new JSONObject(response);

                showSnackbar(DIHelper.getMessage(jsonObject));
                if (DIHelper.getStatus(jsonObject)) {

                   navigateToFragment(context, WhInvoiceFragment.newInstance());

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

    Response.ErrorListener response_errorlistener_delivery = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            hideProgress();

            showSnackbar(error.getMessage());

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case PARCEL_SCAN:

                if (resultCode == Activity.RESULT_OK && data.hasExtra(CameraTestActivity.INTENT_BARCODE_VALUE)) {

                    String barcode = data.getStringExtra(CameraTestActivity.INTENT_BARCODE_VALUE);
//                    for(ParcelListingData.ParcelData parcelData:invoice.getParcelData()){
//                        if(parcelData.getBarcode().equalsIgnoreCase(barcode)){
//                            parcelData.setIsselected(true);
//                        }
//                    }
                    int selectedPosition = 0;

                    for(int i=0;i<adapter.getParcelDataList().size();i++)
                    {
                        ParcelListingData.ParcelData parcelData= adapter.getParcelDataList().get(i);
                        if(parcelData.getBarcode().equalsIgnoreCase(barcode)){
                            adapter.getParcelDataList().get(i).setIsselected(true);
                            selectedPosition = i;
                        }
                    }
//                    for(ParcelListingData.ParcelData parcelData : adapter.getParcelDataList()){
//                        if(parcelData.getBarcode().equalsIgnoreCase(barcode)){
//                            parcelData.setIsselected(true);
//                        }
//                    }
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });

                    detaillistview.scrollToPosition(selectedPosition);

                }
                break;
        }

    }

}
