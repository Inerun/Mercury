package com.inerun.courier.activity_driver;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.inerun.courier.R;
import com.inerun.courier.adapter.StatusSpinnerAdapter;
import com.inerun.courier.base.BaseFragment;
import com.inerun.courier.base.ClickListener;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.constant.Utils;
import com.inerun.courier.data.ParcelListingData;
import com.inerun.courier.data.ParcelStatus;
import com.inerun.courier.data.PickupParcelData;
import com.inerun.courier.data.StatusData;
import com.inerun.courier.helper.DIHelper;
import com.inerun.courier.sql.DIDbHelper;

import java.util.ArrayList;

/**
 * Created by vineet on 9/28/2017.
 */

public class CollectionTransferDetailFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private PickupParcelData pickupParcelData;
    private TextView parcel_id, regis_date, name, phone, pickup_detail_address, weight, comment, price;
    private Button pickup_btn, not_pick_btn;
    private Context context;
    private EditText comment_edt;
    private Spinner pickupStatusSpinner;
    private ArrayList<StatusData> status_options_List;
    private StatusSpinnerAdapter statusSpinnerAdapter;
    private LinearLayout comment_layout;
    private TextView shipment_edit;

    public static CollectionTransferDetailFragment newInstance(PickupParcelData pickupParcelData) {

        Bundle args = new Bundle();

        CollectionTransferDetailFragment fragment = new CollectionTransferDetailFragment();
        args.putSerializable(UrlConstants.KEY_DATA, pickupParcelData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = activity();
        pickupParcelData = (PickupParcelData) getArguments().getSerializable(UrlConstants.KEY_DATA);
        status_options_List = DIHelper.getPickupStatusArrayList();
    }

    @Override
    public int inflateView() {
        return R.layout.collection_transfer_detail;
    }

    @Override
    public void customOnCreateView(View root, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) throws Exception {
        setShowBackArrow(true);
        initView(root);

    }

    private void initView(View root) {

        parcel_id = (TextView) root.findViewById(R.id.pickup_detail_parcel_id);
        regis_date = (TextView) root.findViewById(R.id.pickup_detail_reg_date);
        name = (TextView) root.findViewById(R.id.pickup_detail_name);
        phone = (TextView) root.findViewById(R.id.pickup_detail_phone);
        weight = (TextView) root.findViewById(R.id.pickup_detail_weight);
        price = (TextView) root.findViewById(R.id.pickup_detail_price);
        comment = (TextView) root.findViewById(R.id.pickup_detail_comment);
        pickup_detail_address = (TextView) root.findViewById(R.id.pickup_detail_address);
        comment_layout = (LinearLayout) root.findViewById(R.id.comment_layout);

        pickup_btn = (Button) root.findViewById(R.id.pickup_btn);
        not_pick_btn = (Button) root.findViewById(R.id.not_pickup_btn);
        shipment_edit = (TextView) root.findViewById(R.id.shipment_edit);
        shipment_edit.setOnClickListener(this);
        pickup_btn.setOnClickListener(this);
        not_pick_btn.setOnClickListener(this);
        setData();


    }

    private void setData() {
        parcel_id.setText(pickupParcelData.getParcel_barcode());
        regis_date.setText(pickupParcelData.getPickParcelDetailData().getParcel_assign_date());
        name.setText(pickupParcelData.getName());
        phone.setText(pickupParcelData.getPhone());
        weight.setText(pickupParcelData.getPickParcelDetailData().getParcel_actual_weight());
        price.setText(pickupParcelData.getPickParcelDetailData().getParcel_price());
        pickup_detail_address.setText(pickupParcelData.getPickUpAddress());


        String comment_pickup = pickupParcelData.getPickParcelDetailData().getParcel_pickup_comment();
        setComment(comment_pickup);
//        if( comment_pickup!= null && comment_pickup.trim().length() > 0) {
//            comment.setText(comment_pickup);
//            comment.setVisibility(View.VISIBLE);
//        }else{
//            comment.setVisibility(View.GONE);
//        }


    }

   public void setComment(String comment_pickup){
       if( comment_pickup!= null && comment_pickup.trim().length() > 0) {
           comment.setText(comment_pickup);
           comment_layout.setVisibility(View.VISIBLE);
       }else{
           comment_layout.setVisibility(View.GONE);
       }
    }

    @Override
    protected String getAnalyticsName() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.not_pickup_btn:
                statusPopup();
                break;
            case R.id.pickup_btn:
                pickupParcel();
                break;
            case R.id.shipment_edit:

//                startActivityForResult(new Intent(activity(), CameraTestActivity.class), AWB_SCAN);
                break;
        }

    }

    private void pickupParcel() {
//        DIDbHelper.deliverParcelandUpdateTransaction(activity(),parcelDatas,new ParcelStatus(""+ParcelListingData.ParcelData.DELIVERED,"DELIVERED"),iscard,transcid,collectedby,totalamount,parcelDatas.get(0).getCurrency(),pod,nationalId);
        DIDbHelper.receivedParcel(context, pickupParcelData, new ParcelStatus(""+ ParcelListingData.ParcelData.PICKUP_RECEIVED, "RECEIVED"));
//        ((BaseActivity)activity()).syncData();
        if (Utils.isConnectingToInternet(context)) {
//            ((BaseActivity) activity()).syncData();
            syncData();
        }else{
            hideProgress();
            Toast.makeText(context, getString(R.string.saved_success), Toast.LENGTH_LONG).show();
            activity().finish();
        }
    }

    /*Dialog for comment*/
    private void statusPopup() {
        // get prompts.xml view
        LayoutInflater inflater = LayoutInflater.from(context);
        View inflateView = inflater.inflate(R.layout.fragment_pickup_parcel_update, null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        final AlertDialog alertDialog;

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setTitle(R.string.update_status);
        alertDialogBuilder.setView(inflateView);


        /*Code for alert dialog title size and color*/
//        TextView myMsg = new TextView(context);
//        myMsg.setText("Succesfully send!");
//        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
//        myMsg.setTextSize(15);
//        myMsg.setTextColor(Color.BLUE);
        //set custom title

//        alertDialogBuilder.setCustomTitle(myMsg);

        comment_edt = (EditText) inflateView.findViewById(R.id.pickup_comment);
        pickupStatusSpinner = (Spinner) inflateView.findViewById(R.id.pickup_status);
        Button submit_btn = (Button) inflateView.findViewById(R.id.submit_btn);
        Button cancel_btn = (Button) inflateView.findViewById(R.id.cancel_btn);

        String comtn_detail = (comment.getText()+"").trim();
        if(comtn_detail != null && comtn_detail.length() > 0 && !comtn_detail.equalsIgnoreCase("Comment")){
            comment_edt.setText(comtn_detail);
        }


        statusSpinnerAdapter = new StatusSpinnerAdapter(activity(), android.R.layout.simple_spinner_item, status_options_List);
        statusSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pickupStatusSpinner.setAdapter(statusSpinnerAdapter);
        pickupStatusSpinner.setOnItemSelectedListener(this);

        pickupStatusSpinner.setSelection(status_options_List.size() - 1);
//        pickupStatusSpinner.setSelection(1);

        // create alert dialog
        alertDialog = alertDialogBuilder.create();
//        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        submit_btn.setOnClickListener(new ClickListener(activity()) {
            @Override
            public void click(View view)throws Exception {
                String comment_txt = (comment_edt.getText() + "").trim();
                if (DIHelper.validatePickupComment(context, comment_txt)) {

                    showProgress();
                    DIDbHelper.updatePickupStatus(context, pickupParcelData, new ParcelStatus(""+ ParcelListingData.ParcelData.PICKUP_ATTEMPTED, comment_txt));
                    if (Utils.isConnectingToInternet(context)) {
//                        ((BaseActivity) context).syncData();
                        syncData();
                    }else{
                        hideProgress();
                        Toast.makeText(context, getString(R.string.saved_success), Toast.LENGTH_LONG).show();
                    }
//                    comment.setText(comment_txt);
                    setComment(comment_txt);
                    alertDialog.cancel();

                }
               /* if (comment_txt != null && comment_txt.length() > 0) {
                    comment.setText(comment_txt);
                    alertDialog.cancel();
                } else {
                    Toast.makeText(context, "Comment not empty", Toast.LENGTH_LONG).show();
                }*/
            }
        });

        cancel_btn.setOnClickListener(new ClickListener(activity()) {
            @Override
            public void click(View view)throws Exception {
                alertDialog.cancel();
            }
        });


        // show it
        alertDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if(status_options_List.size()-1 != i) {

            if (status_options_List.get(i).getValue().length() > 0) {

                comment_edt.setText(status_options_List.get(i).getLable());
            } else {
                comment_edt.setHint(status_options_List.get(i).getLable());
            }
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
