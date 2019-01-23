package com.inerun.courier.activity_driver;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.inerun.courier.R;
import com.inerun.courier.activity_auction.IonServiceManager;
import com.inerun.courier.adapter.CollectionServiceEditAdapter;
import com.inerun.courier.base.BaseActivity;
import com.inerun.courier.base.BaseFragment;
import com.inerun.courier.base.DeviceInfoUtil;
import com.inerun.courier.base.SweetAlertUtil;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.data.Payment;
import com.inerun.courier.data.PickupParcelData;
import com.inerun.courier.data.Services;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by vinay on 01/01/19.
 */

public class CollectionServiceEditFragment extends BaseFragment implements View.OnClickListener {
    private Context context;
    private PickupParcelData pickupParcelData;
    private CollectionServiceEditAdapter mAdapter;
    private RecyclerView recyclerView;
    private SweetAlertDialog progressdialog;

    private Button next_btn;

    public static CollectionServiceEditFragment newInstance(PickupParcelData pickupParcelData) {
        Bundle args = new Bundle();

        CollectionServiceEditFragment fragment = new CollectionServiceEditFragment();
        args.putSerializable(UrlConstants.KEY_DATA, pickupParcelData);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = activity();
        pickupParcelData = (PickupParcelData) getArguments().getSerializable(UrlConstants.KEY_DATA);


    }

    @Override
    public int inflateView() {
        return R.layout.activity_collection_service_edit;
    }

    @Override
    public void customOnCreateView(View root, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) throws Exception {
        ((CollectionAddressEditActivity) getActivity()).clickingOnTab(3);
        setShowBackArrow(true);

        recyclerView = root.findViewById(R.id.servicerecyclerview);
        next_btn = root.findViewById(R.id.next_btn);
        next_btn.setOnClickListener(this);
        setData();
    }


    private void setData() {


        mAdapter = new CollectionServiceEditAdapter(activity());
//        mAdapter.setOnItemClickListener(onClickAdapterListener);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//            recyclerView.addItemDecoration(new DividerItemDecoration(getDrawable(this, R.drawable.parcel_item_thumbnail_divider)));
//        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(activity()));

        recyclerView.setAdapter(mAdapter);
        mAdapter.setDataList(pickupParcelData.getServicesList());
    }



    @Override
    protected String getAnalyticsName() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.next_btn:
                if(validateSerivce()) {
                    try {
                        getPaymentService();
                    } catch (IonServiceManager.InvalidParametersException e) {
                        e.printStackTrace();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                break;
        }
    }


    private boolean validateSerivce()  {
        Services services1 = null;
        for (Services services:mAdapter.getDataList()){
            if(services.isDefaultCheck()){
                services1 = services;
            }
        }
        if(services1 == null){
            Toast.makeText(activity(),"Pls Select one service", Toast.LENGTH_LONG).show();
            return false;
        }else{
            pickupParcelData.setService(services1);
            return true;
        }


    }



    private void getPaymentService() throws IonServiceManager.InvalidParametersException {
        progressdialog = SweetAlertUtil.getProgressDialog(activity());
        progressdialog.show();

//
        String customerid = pickupParcelData.getCustomer_id();
        String amount = pickupParcelData.getService().getSpecial_rate();


        String params[] = {IonServiceManager.KEYS.ANDROID_ID, DeviceInfoUtil.getAndroidID(activity()), IonServiceManager.KEYS.CUST_ID, customerid, IonServiceManager.KEYS.SERVICE_AMOUNT, amount};
        Log.i("params", params.toString());
        IonServiceManager serviceManager = getApp().ionserviceManager;
        serviceManager.postRequestToServerWOprogress(serviceManager.getAddress().PaymentService, new IonServiceManager.ResponseCallback(activity()) {
            @Override
            public void onException(final String exception) {
                Log.i("Req Completed", "" + exception);
//                showException(exception);
                progressdialog.dismiss();
                SweetAlertUtil.showWarningWithCallback(activity(), exception, activity().getString(R.string.ok), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
//                        ((Activity) activity()).finish();
                        Log.i("MSG", exception.toString());
                    }
                });
                hideProgress();
                pickupParcelData.setPaymentList(getPaymentDataList()); // dummy data
                navigateToFragment(getActivity(), CollectionPaymentEditFragment.newInstance(pickupParcelData));

            }

            @Override
            public void onResponse(boolean status, String message, Object result) throws JSONException, JsonSyntaxException {

                progressdialog.dismiss();
                JsonObject jsonObject = (JsonObject) result;
                JsonArray jsonArray = jsonObject.get(IonServiceManager.KEYS.PAYMENT_ARRAY).getAsJsonArray();
//                jsonArray.remove(0);

                List<Payment> paymentList = (ArrayList<Payment>) new Gson().fromJson(jsonArray.toString(), new TypeToken<ArrayList<Payment>>() {}.getType());

                pickupParcelData.setPaymentList(paymentList);


//                pickupParcelData.setPaymentList(getPaymentDataList()); // dummy data

                ((BaseActivity) getActivity()).navigateToFragment(getActivity(), CollectionPaymentEditFragment.newInstance(pickupParcelData));

            }

        }, params);
    }

    private List<Payment> getPaymentDataList() {

        List<Payment> paymentList = new ArrayList<>();

        Payment payment = new Payment(1, "CARD");
        Payment payment1 = new Payment(2, "CASH");
        Payment payment2 = new Payment(3, "ON ACCOUNT");
        Payment payment3 = new Payment(4, "COD");

        paymentList.add(payment);
        paymentList.add(payment1);
        paymentList.add(payment2);
        paymentList.add(payment3);

        return paymentList;
    }

}
