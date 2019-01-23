package com.inerun.courier.activity_customer_care;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.inerun.courier.R;
import com.inerun.courier.activity_driver.ParcelDetailFragment;
import com.inerun.courier.activity_driver.SignActivity;
import com.inerun.courier.adapter.CustomerReadyParcelAdapter;
import com.inerun.courier.base.BaseFragment;
import com.inerun.courier.base.ClickListener;
import com.inerun.courier.constant.AppConstant;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.data.CustInvoiceParcelData;
import com.inerun.courier.data.POD;
import com.inerun.courier.data.ParcelListingData;
import com.inerun.courier.data.ReadyParcelData;
import com.inerun.courier.helper.DIHelper;
import com.inerun.courier.service.whParcelUploadService;

import java.util.ArrayList;

/**
 * Created by vineet on 2/17/2017.
 */

public class CustReadyParcelFragment extends BaseFragment implements View.OnClickListener {

    private static final String DELIVERED = "delivered";
    private final int SIGN_REQUEST = 102;
    private Context context;
    private LinearLayout submit_layout;

    private TextView error_txt;
    private RecyclerView recyclerView;
    private CustomerReadyParcelAdapter adapter;
    private Button delivery_button;
    private ArrayList<ParcelListingData.ParcelData> selectedparcelDataArrayList;
    private ArrayList<ReadyParcelData> updatedArrayList;

    private CustInvoiceParcelData.Invoice invoice;

    public static Fragment newInstance(CustInvoiceParcelData.Invoice invoice) {
        CustReadyParcelFragment fragment = new CustReadyParcelFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(UrlConstants.KEY_DATA, invoice);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        invoice  = (CustInvoiceParcelData.Invoice) getArguments().getSerializable(UrlConstants.KEY_DATA);
    }

    @Override
    public int inflateView() {
        return R.layout.cust_ready_parcel;
    }

    @Override
    public void customOnCreateView(View root, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) throws Exception {
        setShowBackArrow(true);
        context = activity();

        intView();
        setToolBarTitle(R.string.ready_parcel);
        setSearchData(invoice);
    }

    private void intView() {
        error_txt = (TextView) getViewById(R.id.error_textview);
        recyclerView = (RecyclerView) getViewById(R.id.ready_parcel_listview);


        submit_layout = (LinearLayout) getViewById(R.id.submit_layout);
        delivery_button = (Button) getViewById(R.id.delivery_button);

        delivery_button.setOnClickListener(this);

    }

    @Override
    protected String getAnalyticsName() {
        return null;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.delivery_button:
                parcelDelivery();
                break;
        }
    }



    private void setSearchData(CustInvoiceParcelData.Invoice searchParcelData) {

        if (searchParcelData != null && searchParcelData.getParcelData() != null && searchParcelData.getParcelData().size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);

            adapter = new CustomerReadyParcelAdapter(activity(), searchParcelData.getParcelData(), searchlickListener);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
            submit_layout.setVisibility(View.VISIBLE);
            error_txt.setVisibility(View.GONE);


        } else {
            error_txt.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            submit_layout.setVisibility(View.GONE);
            showSnackbar(R.string.search_error);

        }

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


    private void parcelDelivery() {

        selectedparcelDataArrayList = new ArrayList<>();
        updatedArrayList = new ArrayList<>();
        for (int i = 0; i < adapter.getParcelDataList().size(); i++) {
            if (adapter.getParcelDataList().get(i).isselected()) {
                selectedparcelDataArrayList.add(adapter.getParcelDataList().get(i));
            }
        }

        if (selectedparcelDataArrayList.size() == adapter.getParcelDataList().size() ) {

                for (int i = 0; i < selectedparcelDataArrayList.size(); i++) {
                    ParcelListingData.ParcelData parceldata = selectedparcelDataArrayList.get(i);
                    ReadyParcelData readyParcelData = new ReadyParcelData(parceldata.getBarcode());

                    selectedparcelDataArrayList.get(i).setUpdate_date(DIHelper.getDateTime(AppConstant.DATEIME_FORMAT));
                    updatedArrayList.add(readyParcelData);
                }
            Intent intent=new Intent(activity(), CustSignActivity.class);
            intent.putExtra(SignActivity.INTENT_RECEIVER_NAME,selectedparcelDataArrayList.get(0).getName());
            startActivityForResult(intent, SIGN_REQUEST);

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

                    Log.i("POD_path", path);
//                        Log.i("Receiver_Name", receiver_name);
                    String pod_name = path.substring(path.lastIndexOf("/") + 1);
                    Log.i("POD_Name", pod_name);
                    String receiver_name = data.getStringExtra(SignActivity.INTENT_RECEIVER_NAME);
                    String nationalid = data.getStringExtra(SignActivity.INTENT_NATIONAL_ID);

                    Log.i("POD_path", path);
                    Log.i("Receiver_Name", ""+receiver_name);

                    POD pod = new POD(pod_name, receiver_name,nationalid);

                    Intent i = new Intent(activity(), whParcelUploadService.class);
                    i.putExtra(UrlConstants.KEY_POD,pod);
                    for(int j =0;j<selectedparcelDataArrayList.size();j++)
                    {
                        selectedparcelDataArrayList.get(j).setDeliverystatus(ParcelListingData.ParcelData.DELIVERED);
                    }

                    i.putExtra(UrlConstants.KEY_DATA,selectedparcelDataArrayList);
                    i.putExtra(UrlConstants.KEY_INVOICE_NUMBER, invoice.getInvoice_number());
                    activity().startService(i);
//                    Map<String, String> params = DIRequestCreator.getInstance(activity()).getReadyParcelDeliveredMapParamsForCustSupport(updatedArrayList);
////
//                DropInsta.serviceManager().postRequest(UrlConstants.URL_INVOICE_DELIVERY, params, getProgress(), response_listener_delivery, response_errorlistener_delivery, DELIVERED);
                }
                break;



        }



    }

}
