package com.inerun.courier.activity_customer_care;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.inerun.courier.CourierApplication;
import com.inerun.courier.R;
import com.inerun.courier.activity_driver.ParcelDetailFragment;
import com.inerun.courier.activity_driver.SignActivity;
import com.inerun.courier.adapter.CustomerExecutiveAdapter;
import com.inerun.courier.adapter.CustomerReadyAdapter;
import com.inerun.courier.base.BaseFragment;
import com.inerun.courier.base.ClickListener;
import com.inerun.courier.constant.AppConstant;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.data.CustomerExecutiveData;
import com.inerun.courier.data.POD;
import com.inerun.courier.data.ParcelListingData;
import com.inerun.courier.data.ReadyParcelData;
import com.inerun.courier.data.WhInvoiceSearchParcelData;
import com.inerun.courier.helper.DIHelper;
import com.inerun.courier.service.DIRequestCreator;
import com.inerun.courier.service.whParcelUploadService;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by vineet on 2/17/2017.
 */

public class CustomerReadyParcelFragment extends BaseFragment implements View.OnClickListener {

    private static final String SEARCH = "SEARCH_SERVICE";
    private static final String DELIVERED = "delivered";
    private Context context;
    private LinearLayout submit_layout;
    private EditText invoiceno_edt;
    private TextView error_txt, go;
    private RecyclerView detaillistview;
    private CustomerReadyAdapter adapter;
//    private WhSearchAdapter adapter;
    private Button delivery_button;
    private ArrayList<ParcelListingData.ParcelData> selectedparcelDataArrayList;
    private ArrayList<ReadyParcelData> updatedArrayList;
    private Spinner executiveSpinner;
    private CustomerExecutiveAdapter customerExecutiveAdapter;
    private final int SIGN_REQUEST = 102;

    public static Fragment newInstance() {
        CustomerReadyParcelFragment fragment = new CustomerReadyParcelFragment();
        return fragment;
    }

    @Override
    public int inflateView() {
        return R.layout.wh_ready_parcel;
    }

    @Override
    public void customOnCreateView(View root, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) throws Exception {
        setShowBackArrow(true);
        context = activity();

        intView();
        setToolBarTitle(R.string.ready_for_delivery);
    }

    private void intView() {
        error_txt = (TextView) getViewById(R.id.error_textview);
        invoiceno_edt = (EditText) getViewById(R.id.invoiceno_edt);
        go = (TextView) getViewById(R.id.search_go);
        detaillistview = (RecyclerView) getViewById(R.id.detail_listview);

        go.setOnClickListener(this);

        submit_layout = (LinearLayout) getViewById(R.id.submit_layout);
        delivery_button = (Button) getViewById(R.id.delivery_button);

        delivery_button.setOnClickListener(this);
        executiveSpinner = (Spinner) getViewById(R.id.customer_care_spinner);

    }

    @Override
    protected String getAnalyticsName() {
        return null;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.search_go:
                searchParcel();
                break;

            case R.id.delivery_button:
                parcelDelivery();
                break;
        }
    }


    private void searchParcel() {
        String invoice_no = ("" + invoiceno_edt.getText()).trim();

        if (isStringValid(invoice_no)) {

            Map<String, String> params = DIRequestCreator.getInstance(activity()).getInvoiceSearchMapParams(invoice_no);

            CourierApplication.serviceManager().postRequest(UrlConstants.URL_INVOICE_SEARCH, params, getProgress(), response_listener, response_errorlistener, SEARCH);

        } else {
            showLongToast("Invoice number not provided");
        }
    }

    Response.Listener<String> response_listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            hideProgress();
            Gson gson = new Gson();

            WhInvoiceSearchParcelData data = gson.fromJson(response, WhInvoiceSearchParcelData.class);
            setSearchData(data);
        }
    };
    Response.ErrorListener response_errorlistener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            hideProgress();
            setSearchData(null);
            showSnackbar(error.getMessage());

        }
    };

    private void setSearchData(WhInvoiceSearchParcelData searchParcelData) {

        if (searchParcelData != null && searchParcelData.getDeliverydata() != null && searchParcelData.getDeliverydata().size() > 0) {
            detaillistview.setVisibility(View.VISIBLE);
//            adapter = new WhSearchAdapter(activity(), searchParcelData.getDeliverydata(), searchlickListener);
            adapter = new CustomerReadyAdapter(activity(), searchParcelData.getDeliverydata());

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity());
            detaillistview.setLayoutManager(mLayoutManager);
            detaillistview.setItemAnimator(new DefaultItemAnimator());
            detaillistview.setAdapter(adapter);
            submit_layout.setVisibility(View.VISIBLE);
            error_txt.setVisibility(View.GONE);

//            setExecutiveData(searchParcelData.getExecutivedata());


        } else {
            error_txt.setVisibility(View.VISIBLE);
            invoiceno_edt.setText("");
            detaillistview.setVisibility(View.GONE);
            submit_layout.setVisibility(View.GONE);
            showSnackbar(R.string.search_error);

        }


    }

    private void setExecutiveData(ArrayList<CustomerExecutiveData> executivedata) {
        CustomerExecutiveData executiveData_hint = new CustomerExecutiveData("", getString(R.string.customer_executive_hint));
        executivedata.add(executiveData_hint);

        customerExecutiveAdapter = new CustomerExecutiveAdapter(activity(), android.R.layout.simple_spinner_item, executivedata);
        customerExecutiveAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        executiveSpinner.setAdapter(customerExecutiveAdapter);
        executiveSpinner.setSelection(executivedata.size() - 1);
    }

    View.OnClickListener searchlickListener = new ClickListener(activity()) {
        @Override
        public void click(View view) throws Exception{
            int pos = (int) view.getTag();

            ParcelListingData.ParcelData parceldata = adapter.getParcelDataList().get(pos);
            ArrayList<ParcelListingData.ParcelData> parcelDataArrayList = new ArrayList<>();
            parcelDataArrayList.add(parceldata);

            navigateToFragment(activity(), ParcelDetailFragment.newInstance(parceldata));


        }
    };


    private void parcelDelivery() {

//        String customerCareId = "";
//        customerCareId = ((CustomerExecutiveData) executiveSpinner.getSelectedItem()).getId();

        selectedparcelDataArrayList = new ArrayList<>();
        updatedArrayList = new ArrayList<>();
        for (int i = 0; i < adapter.getParcelDataList().size(); i++) {
            if (adapter.getParcelDataList().get(i).isselected()) {
                selectedparcelDataArrayList.add(adapter.getParcelDataList().get(i));
            }
        }

        if (selectedparcelDataArrayList.size() == adapter.getParcelDataList().size() ) {

//            if(isStringValid(customerCareId)) {

                for (int i = 0; i < selectedparcelDataArrayList.size(); i++) {
                    ParcelListingData.ParcelData parceldata = selectedparcelDataArrayList.get(i);
                    ReadyParcelData readyParcelData = new ReadyParcelData(parceldata.getBarcode());
//                selectedparcelDataArrayList.set(i, parceldata);
                    selectedparcelDataArrayList.get(i).setUpdate_date(DIHelper.getDateTime(AppConstant.DATEIME_FORMAT));
                    updatedArrayList.add(readyParcelData);
                }
            Intent intent=new Intent(activity(), CustSignActivity.class);
            intent.putExtra(SignActivity.INTENT_RECEIVER_NAME,selectedparcelDataArrayList.get(0).getName());
            startActivityForResult(intent, SIGN_REQUEST);
//
//            }else{
//                showSnackbar(R.string.customer_care_error);
//            }
        } else {
            showSnackbar(R.string.select_all_parcel);
        }


    }

    Response.Listener<String> response_listener_delivery = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            hideProgress();

            showSnackbar("");
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

            case SIGN_REQUEST:
                if (resultCode == Activity.RESULT_OK && data.hasExtra(SignActivity.INTENT_FILENAME)) {
                    showProgress();
                    String path = data.getStringExtra(SignActivity.INTENT_FILENAME);

//                    setImage(path);
                    Log.i("POD_path", path);
//                        Log.i("Receiver_Name", receiver_name);
                    String pod_name = path.substring(path.lastIndexOf("/") + 1);
                    Log.i("POD_Name", pod_name);
                    String receiver_name = data.getStringExtra(SignActivity.INTENT_RECEIVER_NAME);
                    String nationalid = data.getStringExtra(SignActivity.INTENT_NATIONAL_ID);
//                    setImage(path);
                    Log.i("POD_path", path);
                    Log.i("Receiver_Name", ""+receiver_name);

                    POD pod = new POD(pod_name, receiver_name,nationalid);
//                    deliverParcel(transcdata.iscard(),arrayList,transcdata.getTotalamount(),transcdata.getTranscid(),transcdata.getCollectedby(), pod, transcdata.getNationalId());
                    //activity().finish();
//                    Toast.makeText(this, "Saved at"+ path, Toast.LENGTH_SHORT).show();
//                        syncData();

                    Intent i = new Intent(activity(), whParcelUploadService.class);
                    i.putExtra(UrlConstants.KEY_POD,pod);
                    for(int j =0;j<selectedparcelDataArrayList.size();j++)
                    {
                        selectedparcelDataArrayList.get(j).setDeliverystatus(ParcelListingData.ParcelData.DELIVERED);
                    }

                    i.putExtra(UrlConstants.KEY_DATA,selectedparcelDataArrayList);
                    activity().startService(i);
//                    Map<String, String> params = DIRequestCreator.getInstance(activity()).getReadyParcelDeliveredMapParamsForCustSupport(updatedArrayList);
////
//                DropInsta.serviceManager().postRequest(UrlConstants.URL_INVOICE_DELIVERY, params, getProgress(), response_listener_delivery, response_errorlistener_delivery, DELIVERED);
                }
                break;



        }



    }

}
