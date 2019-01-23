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
import com.inerun.courier.adapter.CollectionPaymentEditAdapter;
import com.inerun.courier.adapter.CollectionServiceEditAdapter;
import com.inerun.courier.base.BaseActivity;
import com.inerun.courier.base.BaseFragment;
import com.inerun.courier.base.ClickListener;
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

public class CollectionPaymentEditFragment extends BaseFragment {
    private Context context;
    private PickupParcelData pickupParcelData;
    private CollectionPaymentEditAdapter mAdapter;
    private RecyclerView recyclerView;
    private SweetAlertDialog progressdialog;

    private Button submit_btn;

    public static CollectionPaymentEditFragment newInstance(PickupParcelData pickupParcelData) {
        Bundle args = new Bundle();

        CollectionPaymentEditFragment fragment = new CollectionPaymentEditFragment();
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
        return R.layout.activity_collection_payment_edit;
    }

    @Override
    public void customOnCreateView(View root, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) throws Exception {
        ((CollectionAddressEditActivity) getActivity()).clickingOnTab(4);
        setShowBackArrow(true);

        recyclerView = root.findViewById(R.id.paymentrecyclerview);
        submit_btn = root.findViewById(R.id.submit);

        setListener();

        setData();
    }

    private void setListener() {
        submit_btn.setOnClickListener(new ClickListener(activity()) {
            @Override
            public void click(View v) throws Exception {
                save();
            }
        });
    }


    private void setData() {


        mAdapter = new CollectionPaymentEditAdapter(activity());
//        mAdapter.setOnItemClickListener(onClickAdapterListener);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//            recyclerView.addItemDecoration(new DividerItemDecoration(getDrawable(this, R.drawable.parcel_item_thumbnail_divider)));
//        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(activity()));

        recyclerView.setAdapter(mAdapter);
        mAdapter.setDataList(pickupParcelData.getPaymentList());
    }

    private void save() throws IonServiceManager.InvalidParametersException {
        List<Payment> paymentList = mAdapter.getDataList();
        int selectedPosition =  mAdapter.getSelectPosition();

        if(selectedPosition > -1){
            Payment payment = paymentList.get(selectedPosition);
            pickupParcelData.setPayment(payment);

            String jsonStr = new Gson().toJson(pickupParcelData);
            Log.i("PaymentEditFragment", jsonStr);
            //            saveService();
        }else{
            Toast.makeText(activity(),"Pls select", Toast.LENGTH_LONG).show();
        }
    }

    private void saveService() throws IonServiceManager.InvalidParametersException {
            progressdialog = SweetAlertUtil.getProgressDialog(activity());
            progressdialog.show();

        String jsonStr = new Gson().toJson(pickupParcelData);

        String params[] = {IonServiceManager.KEYS.ANDROID_ID, DeviceInfoUtil.getAndroidID(activity()), IonServiceManager.KEYS.DATA, jsonStr};
            Log.i("params", params.toString());
            IonServiceManager serviceManager = getApp().ionserviceManager;
            serviceManager.postRequestToServerWOprogress(serviceManager.getAddress().SaveService, new IonServiceManager.ResponseCallback(activity()) {
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
                }

                @Override
                public void onResponse(boolean status, String message, Object result) throws JSONException, JsonSyntaxException {

                    progressdialog.dismiss();

                }

            }, params);
    }


    @Override
    protected String getAnalyticsName() {
        return null;
    }


}
