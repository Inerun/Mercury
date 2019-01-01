package com.inerun.courier.activity_customer_care;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.inerun.courier.CourierApplication;
import com.inerun.courier.R;
import com.inerun.courier.activity_driver.ParcelDetailActivity;
import com.inerun.courier.activity_driver.SignActivity;
import com.inerun.courier.activity_warehouse.WhSignActivity;
import com.inerun.courier.adapter.CustomerReadyAdapter;
import com.inerun.courier.base.BaseActivity;
import com.inerun.courier.base.BaseFragment;
import com.inerun.courier.base.ClickListener;
import com.inerun.courier.constant.AppConstant;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.data.POD;
import com.inerun.courier.data.ParcelListingData;
import com.inerun.courier.data.ParcelSearchData;
import com.inerun.courier.data.UpdatedParcelData;
import com.inerun.courier.data.WhSearchParcelData;
import com.inerun.courier.helper.DIHelper;
import com.inerun.courier.scanner.CameraTestActivity;
import com.inerun.courier.service.whParcelUploadService;

import java.util.ArrayList;

/**
 * Created by vinay on 21/02/17.
 */

public class CustomerSearchParcelFragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, View.OnLongClickListener {

    private static final String SEARCH = "SEARCH_SERVICE";
    private static final int BARCODE_SCAN = 101;
    private static final int SIGN_REQUEST = 102;
    private Context context;
    private Button parcelsearch;
    private EditText parcel_edittext, name_edittext, email_edittext, phone_edittext;
    private RecyclerView detaillistview;
    CustomerReadyAdapter adapter;
    private ImageView parcel_scan;
    private EditText custid_edittext, invoice_edttext;

    private CheckBox selectall_checkbox;
    private Snackbar mySnackbar;
    private RelativeLayout selectall_layout;
    private Button updatebtn;
    private ArrayList<ParcelListingData.ParcelData> selectedparcelDataArrayList;
    private ArrayList<UpdatedParcelData> updatedArrayList;

    public static Fragment newInstance() {
        CustomerSearchParcelFragment fragment = new CustomerSearchParcelFragment();
        return fragment;
    }


    @Override
    public int inflateView() {
        return R.layout.wh_parcel_search;
    }

    @Override
    public void customOnCreateView(View root, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setShowBackArrow(true);
        context = activity();
        initView();
        setToolBarTitle(R.string.search_parcel);
        setListeners();


    }


    private void initView() {
        parcelsearch = (Button) getViewById(R.id.search_button);

        parcel_edittext = (EditText) getViewById(R.id.parcel_edt);
        parcel_scan = (ImageView) getViewById(R.id.parcel_scan_image);
        name_edittext = (EditText) getViewById(R.id.name_edt);
        email_edittext = (EditText) getViewById(R.id.email_edt);
        custid_edittext = (EditText) getViewById(R.id.custid_edt);
        phone_edittext = (EditText) getViewById(R.id.phone_edt);
        invoice_edttext = (EditText) getViewById(R.id.invoice_edt);

        selectall_checkbox = (CheckBox) getViewById(R.id.search_checkbox_selected);
        selectall_layout = (RelativeLayout) getViewById(R.id.checkbox_layout);
        detaillistview = (RecyclerView) getViewById(R.id.detail_listview);
        updatebtn = (Button) getViewById(R.id.search_updatebtn);
    }

    private void setListeners() {
        parcelsearch.setOnClickListener(this);
        parcel_scan.setOnClickListener(this);
        updatebtn.setOnClickListener(this);
        detaillistview.setOnLongClickListener(this);
        selectall_checkbox.setOnCheckedChangeListener(this);


    }

    private void deliverParcel(ParcelListingData.ParcelData parceldata) {

        Bundle bundle = new Bundle();
        bundle.putSerializable(UrlConstants.KEY_DATA, parceldata);

//                startActivity(intent);
        ((BaseActivity) activity()).goToActivity(ParcelDetailActivity.class, bundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.getParcelDataList().clear();
            adapter.notifyDataSetChanged();
        }
        selectall_layout.setVisibility(View.GONE);
    }

    @Override
    protected String getAnalyticsName() {
        return null;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.search_button:

                searchParcel();

                break;
            case R.id.parcel_scan_image:

                startActivityForResult(new Intent(activity(), CameraTestActivity.class), BARCODE_SCAN);

                break;
            case R.id.parcel_edt:

                selectall_checkbox.setChecked(!selectall_checkbox.isChecked());

                break;
            case R.id.search_updatebtn:

//               showSnackbar("Updating");


                selectedparcelDataArrayList = new ArrayList<>();
                updatedArrayList = new ArrayList<>();
                for (int i = 0; i < adapter.getParcelDataList().size(); i++) {
                    if (adapter.getParcelDataList().get(i).isselected()) {
                        selectedparcelDataArrayList.add(adapter.getParcelDataList().get(i));
                    }
                }
                if (selectedparcelDataArrayList.size() > 0) {
//                    DIDbHelper.deleteDeliveryTable(activity());
//                    DIDbHelper.insertDeliveryInfoListIntoDb(activity(), selectedparcelDataArrayList);

                    for (int i = 0; i < selectedparcelDataArrayList.size(); i++) {
                        ParcelListingData.ParcelData parceldata = selectedparcelDataArrayList.get(i);
                        UpdatedParcelData updateparcel = new UpdatedParcelData(parceldata.getBarcode(), ParcelListingData.ParcelData.DELIVERED, parceldata.getDeliverycomments(), parceldata.getPayment_status(), DIHelper.getDateTime(AppConstant.DATEIME_FORMAT), "", "");


//                        int id = DIDbHelper.getColumnIdFromBarcode(parceldata.getBarcode(), activity());
//                        parceldata.setColumn_id(id);
                        selectedparcelDataArrayList.set(i, parceldata);
                        updatedArrayList.add(updateparcel);
                    }


                    startActivityForResult(new Intent(activity(), WhSignActivity.class), SIGN_REQUEST);


                } else {
                    showSnackbar(R.string.no_parcel_error);
                }


//            DIDbHelper.deleteDeliveryTable(activity());


                break;
        }
    }

    private void searchParcel() {
        String parcel_no = ("" + parcel_edittext.getText()).trim();
        String parcel_name = ("" + name_edittext.getText()).trim();
        String parcel_email = ("" + email_edittext.getText()).trim();
        String parcel_phone = ("" + phone_edittext.getText()).trim();
        String parcel_custid = ("" + custid_edittext.getText()).trim();
        String parcel_invoice_no = ("" + invoice_edttext.getText()).trim();
        if (isStringValid(parcel_no) || isStringValid(parcel_name) || isStringValid(parcel_email) || isStringValid(parcel_phone) || isStringValid(parcel_custid) || isStringValid(parcel_invoice_no)) {

            ParcelSearchData parcelSearchData = new ParcelSearchData(parcel_no, parcel_name, parcel_email, parcel_phone, parcel_custid, parcel_invoice_no);

            navigateToFragment(context, CustSearchListingFragment.newInstance(parcelSearchData));

//            Map<String, String> params = DIRequestCreator.getInstance(activity()).getSearchMapParams(parcel_no, parcel_name, parcel_email, parcel_phone, parcel_custid, parcel_invoice_no);

//            DropInsta.serviceManager().postRequest(UrlConstants.URL_SEARCH, params,getProgress(), response_listener, response_errorlistener, SEARCH);

//            setSearchData(null);

        } else {
            showLongToast("Data not provided");
        }

    }

    private void setSearchData(WhSearchParcelData searchParcelData) {
        if (searchParcelData != null && searchParcelData.getDeliverydata() != null && searchParcelData.getDeliverydata().size() > 0) {
            detaillistview.setVisibility(View.VISIBLE);
            selectall_layout.setVisibility(View.VISIBLE);
            adapter = new CustomerReadyAdapter(activity(),  searchParcelData.getDeliverydata());

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity());
            detaillistview.setLayoutManager(mLayoutManager);
            detaillistview.setItemAnimator(new DefaultItemAnimator());
            detaillistview.setAdapter(adapter);


        } else {
            parcel_edittext.setText("");
            name_edittext.setText("");
            email_edittext.setText("");
            phone_edittext.setText("");
            detaillistview.setVisibility(View.GONE);
            selectall_layout.setVisibility(View.GONE);
            showSnackbar(R.string.search_error);

        }


    }


    Response.Listener<String> response_listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            hideProgress();
            Gson gson = new Gson();

            WhSearchParcelData data = gson.fromJson(response, WhSearchParcelData.class);
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


    View.OnClickListener searchlickListener = new ClickListener(activity()) {
        @Override
        public void click(View view) throws Exception{
            int pos = (int) view.getTag();

            ParcelListingData.ParcelData parceldata = adapter.getParcelDataList().get(pos);
            ArrayList<ParcelListingData.ParcelData> parcelDataArrayList = new ArrayList<>();
            parcelDataArrayList.add(parceldata);

            deliverParcel(parceldata);


//            ParcelListingData.ParcelData parceldata = adapter.getParcelDataList().get(pos);
//            ArrayList<ParcelListingData.ParcelData> parcelDataArrayList = new ArrayList<>();
//            parcelDataArrayList.add(parceldata);
//
////            DIDbHelper.deleteDeliveryTable(activity());
//            DIDbHelper.insertDeliveryInfoListIntoDb(activity(), parcelDataArrayList);
//            int id = DIDbHelper.getColumnIdFromBarcode(parceldata.getBarcode(), activity());
//
//            parceldata.setColumn_id(id);
//
//            deliverParcel(parceldata);


        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setShowBackArrow(true);
        switch (requestCode) {
            case BARCODE_SCAN:

                if (resultCode == Activity.RESULT_OK && data.hasExtra(CameraTestActivity.INTENT_BARCODE_VALUE)) {
                    parcel_edittext.setText(data.getStringExtra(CameraTestActivity.INTENT_BARCODE_VALUE));
                }
                break;
            case SIGN_REQUEST:


                if (resultCode == Activity.RESULT_OK && data.hasExtra(SignActivity.INTENT_FILENAME)) {
                    showProgress();
                    String path = data.getStringExtra(SignActivity.INTENT_FILENAME);
                    String receiver_name = data.getStringExtra(SignActivity.INTENT_RECEIVER_NAME);
//                    setImage(path);
                    Log.i("POD_path", path);
                    Log.i("Receiver_Name", "" + receiver_name);
                    String pod_name = path.substring(path.lastIndexOf("/") + 1);
                    Log.i("POD_Name", "" + pod_name);
                    Log.i("Size", "" + selectedparcelDataArrayList.size());
                    POD pod = new POD(pod_name, receiver_name);
                    Intent i = new Intent(activity(), whParcelUploadService.class);
                    i.putExtra(UrlConstants.KEY_POD, pod);
                    i.putExtra(UrlConstants.KEY_DATA, updatedArrayList);
                    activity().startService(i);

//                    for (int i = 0; i < selectedparcelDataArrayList.size(); i++) {
////                        DIDbHelper.insertPODInfo(pod, selectedparcelDataArrayList.get(i).getColumn_id(), activity());
////                    Toast.makeText(this, "Saved at"+ path, Toast.LENGTH_SHORT).show();
//                    }


//                    ((BaseActivity) activity()).syncData();


                }
                break;

        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        CourierApplication.serviceManager().cancelAllRequest(SEARCH);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

        adapter.selectAllParcels(compoundButton.isChecked());


    }


    @Override
    public boolean onLongClick(View view) {
//        if(selectall_layout.getVisibility()==View.INVISIBLE) {
        selectall_layout.setVisibility(View.VISIBLE);
//        adapter.setCheckenabled(true);
        adapter.notifyDataSetChanged();
//        }
        return false;
    }


}
