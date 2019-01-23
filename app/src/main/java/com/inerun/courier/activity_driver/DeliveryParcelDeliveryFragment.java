package com.inerun.courier.activity_driver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.inerun.courier.CourierApplication;
import com.inerun.courier.R;
import com.inerun.courier.base.BaseFragment;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.constant.Utils;
import com.inerun.courier.data.POD;
import com.inerun.courier.data.ParcelListingData;
import com.inerun.courier.data.ParcelStatus;
import com.inerun.courier.data.TransactionData;
import com.inerun.courier.helper.DIHelper;
import com.inerun.courier.sql.DIDbHelper;
import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;

/**
 * Created by vinay on 16/01/17.
 */

public class DeliveryParcelDeliveryFragment extends BaseFragment implements View.OnClickListener {

    private ArrayList<ParcelListingData.ParcelData> arrayList;
    private TextView payment;
    private Button cardpayment, cashpayment;
    private float amount = 0;
    private EditText transcid;
    private EditText collectedby, national_id;
    boolean iscard;
    private TransactionData transcdata;
    private final int SIGN_REQUEST = 102;
    private RotateLoading progress;
    private Context context;
    private boolean isOnlyPrepaid = true;


    public static Fragment newInstance(ArrayList<ParcelListingData.ParcelData> arrayList) {
        DeliveryParcelDeliveryFragment fragment = new DeliveryParcelDeliveryFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(UrlConstants.KEY_UPDATE_DATA, arrayList);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = activity();
        arrayList = (ArrayList<ParcelListingData.ParcelData>) getArguments().getSerializable(UrlConstants.KEY_UPDATE_DATA);
    }

    @Override
    public int inflateView() {
        return R.layout.activity_delivery_payment_layout;
    }

    @Override
    public void customOnCreateView(View root, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initview(root);
        setData();

    }

    private void setData() {
        amount = getTotalFromParcels();
        if(amount == 0 && isOnlyPrepaid){
            payment.setText(getString(R.string.prepaid_label));
            cashpayment.setVisibility(View.GONE);
            cardpayment.setVisibility(View.GONE);
        }else {
            payment.setText(DIHelper.getValueInDecimal(amount));
            cashpayment.setVisibility(View.VISIBLE);
            cardpayment.setVisibility(View.VISIBLE);
        }
//        collectedby.setText(DropInsta.getUser().getName());
        if(arrayList != null && arrayList.size() > 0) {
            String userName = arrayList.get(0).getName();
            collectedby.setText(userName);
        }
    }

    private float getTotalFromParcels() {
        isOnlyPrepaid = true;
        for (ParcelListingData.ParcelData parcelData : arrayList) {
            if(!parcelData.isPrepaid()){
                amount += Float.parseFloat(parcelData.getAmount());
                isOnlyPrepaid = false;
            }
        }
        return amount;
    }

    private void initview(View root) {
        payment = (TextView) root.findViewById(R.id.payment_receieved_amount);
        cashpayment = (Button) root.findViewById(R.id.payment_cash);
        cardpayment = (Button) root.findViewById(R.id.payment_card);
        progress = getProgress();
        transcid = (EditText) root.findViewById(R.id.payment_transcid);
        collectedby = (EditText) root.findViewById(R.id.payment_collectedby);
        national_id = (EditText) root.findViewById(R.id.payment_national_id);
        cashpayment.setOnClickListener(this);
        cardpayment.setOnClickListener(this);
        root.findViewById(R.id.parcel_cancel).setOnClickListener(this);
        root.findViewById(R.id.parcel_deliver).setOnClickListener(this);

    }

    @Override
    protected String getAnalyticsName() {
        return null;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.payment_card:
                iscard=true;
                setCardPayment(iscard);

                break;
            case R.id.payment_cash:
                iscard=false;
                setCardPayment(iscard);


                break;
            case R.id.parcel_cancel:

//                activity().setResult(Activity.RESULT_CANCELED);
                activity().finish();
                break;
            case R.id.parcel_deliver:
                String transid=(""+transcid.getText()).trim();
                String collectdby=(""+collectedby.getText()).trim();
                String nationalId=(""+ national_id.getText()).trim();

                if(validatePayment(iscard, transid, collectdby, nationalId)) {
                     transcdata = new TransactionData(iscard, "" + amount, transid, collectdby, nationalId);
//                    got
                    startActivityForResult(new Intent(activity(), SignActivity.class), SIGN_REQUEST);

                }

                break;
        }
    }

    private boolean validatePayment(boolean iscard, String transid, String collectdby, String nationalId) {
        if(!isStringValid(collectdby))
        {
            showSnackbar(R.string.error_receiver_name);
            return false;
        }
        if(iscard&&!isStringValid(transid))
        {
            showSnackbar(R.string.error_transid);
            return false;
        }

        if(!(collectdby.equalsIgnoreCase(CourierApplication.getUser().getName()))){
            if(!isStringValid(nationalId))
            {
                showSnackbar(R.string.error_national_id);
                return false;
            }
        }

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


            switch (requestCode) {

                case SIGN_REQUEST:
                    if (resultCode == Activity.RESULT_OK && data.hasExtra(SignActivity.INTENT_FILENAME)) {
                        String path = data.getStringExtra(SignActivity.INTENT_FILENAME);

//                    setImage(path);
                        Log.i("POD_path", path);
//                        Log.i("Receiver_Name", receiver_name);
                        String pod_name = path.substring(path.lastIndexOf("/") + 1);
                        Log.i("POD_Name", pod_name);

                        POD pod = new POD(pod_name, transcdata.getCollectedby());
                        deliverParcel(transcdata.iscard(),arrayList,transcdata.getTotalamount(),transcdata.getTranscid(),transcdata.getCollectedby(), pod, transcdata.getNationalId());
                        //activity().finish();
//                    Toast.makeText(this, "Saved at"+ path, Toast.LENGTH_SHORT).show();
//                        syncData();
                    }
                    break;



            }



    }

    private void deliverParcel(boolean iscard, ArrayList<ParcelListingData.ParcelData> parcelDatas, String totalamount, String transcid, String collectedby, POD pod, String nationalId) {

        DIDbHelper.deliverParcelandUpdateTransaction(activity(),parcelDatas,new ParcelStatus(""+ParcelListingData.ParcelData.DELIVERED,"DELIVERED"),iscard,transcid,collectedby,totalamount,parcelDatas.get(0).getCurrency(),pod,nationalId);
        if (Utils.isConnectingToInternet(context)) {
//            ((BaseActivity) activity()).syncData();
            syncData();
            Toast.makeText(context, getString(R.string.saved_success), Toast.LENGTH_LONG).show();
            activity().finish();
        }else{
            hideProgress();
            Toast.makeText(context, getString(R.string.saved_success), Toast.LENGTH_LONG).show();
            activity().finish();
        }


    }

    private void setCardPayment(boolean iscard) {
        if (iscard) {
            cardpayment.setBackgroundColor(getColor(R.color.buttonColor));
            cardpayment.setTextColor(getColor(R.color.white));
            cashpayment.setBackgroundColor(getColor(android.R.color.transparent));
            cashpayment.setTextColor(getColor(android.R.color.black));
            transcid.setVisibility(View.VISIBLE);
            transcid.requestFocus();

        } else {
            cashpayment.setBackgroundColor(getColor(R.color.buttonColor));
            cardpayment.setTextColor(getColor(android.R.color.black));
            cardpayment.setBackgroundColor(getColor(android.R.color.transparent));
            cashpayment.setTextColor(getColor(android.R.color.white));
            transcid.setVisibility(View.GONE);

        }



    }
}
