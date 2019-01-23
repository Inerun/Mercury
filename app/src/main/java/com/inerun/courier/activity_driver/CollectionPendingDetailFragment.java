package com.inerun.courier.activity_driver;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.inerun.courier.R;
import com.inerun.courier.adapter.ShipmentAdapter;
import com.inerun.courier.adapter.StatusSpinnerAdapter;
import com.inerun.courier.base.BaseFragment;
import com.inerun.courier.base.SweetAlertUtil;
import com.inerun.courier.constant.AppConstant;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.constant.Utils;
import com.inerun.courier.data.ParcelListingData;
import com.inerun.courier.data.ParcelStatus;
import com.inerun.courier.data.PickupParcelData;
import com.inerun.courier.data.StatusData;
import com.inerun.courier.helper.DIHelper;
import com.inerun.courier.helper.DecimalDigitsInputFilter;
import com.inerun.courier.helper.ExpandableHeightRecyclerView;
import com.inerun.courier.scanner.CameraTestActivity;
import com.inerun.courier.sql.DIDbHelper;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by vineet on 9/28/2017.
 */

public class CollectionPendingDetailFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, TextWatcher {

    private static final int AWB_SCAN = 101;
    private static final int PICKUP_ADDRESS_EDIT = 102;
    private static final int DELIVERY_ADDRESS_EDIT = 103;
    private static final int SHIPMENT_EDIT = 104;
    private PickupParcelData pickupParcelData;
    private TextView parcel_id, regis_date, pickup_name_txt, pickup_company_txt, pickup_department_txt, pickup_address_txt, pickup_phone_txt, pickup_line_txt, pickup_ext_txt;
    private TextView delivery_name_txt, delivery_company_txt, delivery_department_txt, delivery_address_txt, delivery_phone_txt, delivery_line_txt, delivery_ext_txt;
    private TextView received_awb_txt, received_payment_label, received_payment_txt, received_transid_txt;
    private TextView total_count_txt, weight_txt, special_ins_txt, service_txt, freight_txt, return_txt, on_account_price_txt, cod_price_txt, comment, amount;
    private EditText cash_price_edt, card_price_edt, card_trans_id_edt;
    private CheckBox on_account_chk, COD_chk;
    private Button pickup_btn, not_pick_btn;
    private Context context;
    private EditText comment_edt;
    private Spinner pickupStatusSpinner;
    private ArrayList<StatusData> status_options_List;
    private StatusSpinnerAdapter statusSpinnerAdapter;
    private LinearLayout comment_layout, cash_lay, card_lay, on_account_lay, COD_lay, payment_method_lay, received_payment_method_lay, awb_scan_layout, received_awb_layout, received_transactionid_lay, btn_layout, special_ins_lay;
    private ExpandableHeightRecyclerView recyclerview;
    private ShipmentAdapter adapter;
    private ImageView scanAWB_img, addbailedt_img_btn;
    private EditText addAWBbarcode_edt;
    private TextView collection_address_edit, delivery_address_edit;
    private TextView shipment_edit;

    public static CollectionPendingDetailFragment newInstance(PickupParcelData pickupParcelData) {

        Bundle args = new Bundle();

        CollectionPendingDetailFragment fragment = new CollectionPendingDetailFragment();
        args.putSerializable(UrlConstants.KEY_DATA, pickupParcelData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = activity();
//        pickupParcelData = (PickupParcelData) getArguments().getSerializable(UrlConstants.KEY_DATA);

        pickupParcelData = getData();
        status_options_List = DIHelper.getPickupStatusArrayList();
    }

    public PickupParcelData getData() {
        return (PickupParcelData) getArguments().getSerializable(UrlConstants.KEY_DATA);
    }

    @Override
    public int inflateView() {
        return R.layout.colection_pending_detail_fragment;
    }

    @Override
    public void customOnCreateView(View root, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) throws Exception {
        setShowBackArrow(true);
        initView(root);

    }

    private void initView(View root) {

        parcel_id = root.findViewById(R.id.pickup_detail_parcel_id);
        regis_date = root.findViewById(R.id.pickup_detail_reg_date);
        pickup_name_txt = root.findViewById(R.id.collection_lay).findViewById(R.id.name_txt);
        pickup_company_txt = root.findViewById(R.id.collection_lay).findViewById(R.id.company_txt);
        pickup_department_txt = root.findViewById(R.id.collection_lay).findViewById(R.id.department_txt);
        pickup_address_txt = root.findViewById(R.id.collection_lay).findViewById(R.id.address_txt);
        pickup_phone_txt = root.findViewById(R.id.collection_lay).findViewById(R.id.phone_txt);
        pickup_line_txt = root.findViewById(R.id.collection_lay).findViewById(R.id.line_txt);
        pickup_ext_txt = root.findViewById(R.id.collection_lay).findViewById(R.id.ext_txt);

        delivery_name_txt = root.findViewById(R.id.delivery_lay).findViewById(R.id.name_txt);
        delivery_company_txt = root.findViewById(R.id.delivery_lay).findViewById(R.id.company_txt);
        delivery_department_txt = root.findViewById(R.id.delivery_lay).findViewById(R.id.department_txt);
        delivery_address_txt = root.findViewById(R.id.delivery_lay).findViewById(R.id.address_txt);
        delivery_phone_txt = root.findViewById(R.id.delivery_lay).findViewById(R.id.phone_txt);
        delivery_line_txt = root.findViewById(R.id.delivery_lay).findViewById(R.id.line_txt);
        delivery_ext_txt = root.findViewById(R.id.delivery_lay).findViewById(R.id.ext_txt);


        total_count_txt = root.findViewById(R.id.total_count_txt);
        weight_txt = root.findViewById(R.id.weight_txt);
        special_ins_txt = root.findViewById(R.id.special_ins_txt);
        service_txt = root.findViewById(R.id.service_txt);
        freight_txt = root.findViewById(R.id.freight_txt);
        return_txt = root.findViewById(R.id.return_txt);
        on_account_price_txt = root.findViewById(R.id.on_account_price_txt);
        cod_price_txt = root.findViewById(R.id.cod_price_txt);
        received_awb_txt = root.findViewById(R.id.received_awb_txt);
        received_payment_label = root.findViewById(R.id.received_payment_label);
        received_payment_txt = root.findViewById(R.id.received_payment_txt);
        received_transid_txt = root.findViewById(R.id.received_transid_txt);
        amount = root.findViewById(R.id.amount);

        cash_price_edt = root.findViewById(R.id.cash_price_edt);
        card_price_edt = root.findViewById(R.id.card_price_edt);
        card_trans_id_edt = root.findViewById(R.id.card_trans_id_edt);

        on_account_chk = root.findViewById(R.id.on_account_chk);
        COD_chk = root.findViewById(R.id.cod_chk);

        payment_method_lay = root.findViewById(R.id.payment_method_lay);
        cash_lay = root.findViewById(R.id.cash_lay);
        card_lay = root.findViewById(R.id.card_lay);
        on_account_lay = root.findViewById(R.id.on_account_lay);
        COD_lay = root.findViewById(R.id.cod_lay);
        btn_layout = root.findViewById(R.id.linearbottom);

        received_payment_method_lay = root.findViewById(R.id.received_payment_method_lay);
        awb_scan_layout = root.findViewById(R.id.awb_scan_layout);
        received_awb_layout = root.findViewById(R.id.received_awb_layout);
        received_transactionid_lay = root.findViewById(R.id.received_transactionid_lay);
        special_ins_lay = root.findViewById(R.id.special_ins_lay);

        comment = root.findViewById(R.id.pickup_detail_comment);
        comment_layout = root.findViewById(R.id.comment_layout);
        recyclerview = root.findViewById(R.id.shipment_recyclerview);


        scanAWB_img = root.findViewById(R.id.scanbail_phone_img);
        addbailedt_img_btn = root.findViewById(R.id.addbail_edt_img);
        addAWBbarcode_edt = root.findViewById(R.id.addbailbarcode_edt);


        pickup_btn = (Button) root.findViewById(R.id.pickup_btn);
        not_pick_btn = (Button) root.findViewById(R.id.not_pickup_btn);

        collection_address_edit = root.findViewById(R.id.collection_address_edit);
        delivery_address_edit = root.findViewById(R.id.delivery_address_edit);
        shipment_edit = (TextView) root.findViewById(R.id.shipment_edit);
        shipment_edit.setOnClickListener(this);

        pickup_btn.setOnClickListener(this);
        not_pick_btn.setOnClickListener(this);
        scanAWB_img.setOnClickListener(this);
        collection_address_edit.setOnClickListener(this);
        delivery_address_edit.setOnClickListener(this);
        cash_price_edt.addTextChangedListener(this);
        cash_price_edt.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2)});
        card_price_edt.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2)});

        initAdapter();
        setData();


    }

    private void setData() {
        parcel_id.setText(pickupParcelData.getParcel_barcode());
        regis_date.setText(pickupParcelData.getPickParcelDetailData().getParcel_assign_date());

        pickup_name_txt.setText(pickupParcelData.getPickup_address().getfName() + " " + pickupParcelData.getPickup_address().getlName());
        pickup_company_txt.setText(pickupParcelData.getPickup_address().getCompany());
        pickup_department_txt.setText(pickupParcelData.getPickup_address().getDepartment());
        pickup_address_txt.setText(pickupParcelData.getPickUpAddress());
        pickup_phone_txt.setText(pickupParcelData.getPickup_address().getPhone());
        pickup_line_txt.setText(pickupParcelData.getPickup_address().getLandline());
        pickup_ext_txt.setText(pickupParcelData.getPickup_address().getExt());

        delivery_name_txt.setText(pickupParcelData.getDelivery_address().getfName() + " " + pickupParcelData.getPickup_address().getlName());
        delivery_company_txt.setText(pickupParcelData.getDelivery_address().getCompany());
        delivery_department_txt.setText(pickupParcelData.getDelivery_address().getDepartment());
        delivery_address_txt.setText(pickupParcelData.getDeliveryAddress());
        delivery_phone_txt.setText(pickupParcelData.getDelivery_address().getPhone());
        delivery_line_txt.setText(pickupParcelData.getDelivery_address().getLandline());
        delivery_ext_txt.setText(pickupParcelData.getDelivery_address().getExt());

        total_count_txt.setText(pickupParcelData.getShipment().getTotal_peices());
        weight_txt.setText(pickupParcelData.getShipment().getWeight());

        if (isStringValid(pickupParcelData.getPickParcelDetailData().getParcel_special_ins())) {
            special_ins_txt.setText(pickupParcelData.getPickParcelDetailData().getParcel_special_ins());
            special_ins_lay.setVisibility(View.VISIBLE);
        } else {
            special_ins_lay.setVisibility(View.GONE);
        }

        service_txt.setText(pickupParcelData.getService().getService_name());
        freight_txt.setText(pickupParcelData.getService().getFreight_charges());
//        amount.setText(getString(R.string.auction_collection_val, "" + pickupParcelData.getPickParcelDetailData().getParcel_price()));
        amount.setText(getString(R.string.auction_collection_val, DIHelper.getValueInDecimal(Float.parseFloat(pickupParcelData.getPickParcelDetailData().getParcel_price()))));
        return_txt.setText(getString(R.string.auction_collection_val, DIHelper.getValueInDecimal(0f)));

        setPayment();
//        name.setText(pickupParcelData.getName());
//        phone.setText(pickupParcelData.getPhone());
//        weight.setText(pickupParcelData.getPickParcelDetailData().getParcel_actual_weight());
//        price.setText(pickupParcelData.getPickParcelDetailData().getParcel_price());
//        pickup_detail_address.setText(pickupParcelData.getPickUpAddress());
//
//
//        String comment_pickup = pickupParcelData.getPickParcelDetailData().getParcel_pickup_comment();
//        setComment(comment_pickup);
        if (pickupParcelData != null && pickupParcelData.getShipment() != null && pickupParcelData.getShipment().getShipment_details().size() > 0) {
            adapter.addDataListToList(pickupParcelData.getShipment().getShipment_details());
        }

        if (pickupParcelData.isReceived()) {
            collection_address_edit.setVisibility(View.GONE);
//            delivery_address_edit.setVisibility(View.GONE);
        } else {
//            collection_address_edit.setVisibility(View.VISIBLE);
//            delivery_address_edit.setVisibility(View.VISIBLE);
        }


    }

    private void setPayment() {
        String amount = DIHelper.getValueInDecimal(pickupParcelData.getPickParcelDetailData().getParcel_price());

        if (pickupParcelData.isReceived()) {
            comment_layout.setVisibility(View.GONE);
            btn_layout.setVisibility(View.GONE);
            shipment_edit.setVisibility(View.GONE);

            payment_method_lay.setVisibility(View.GONE);
            awb_scan_layout.setVisibility(View.GONE);
            received_payment_method_lay.setVisibility(View.VISIBLE);
            received_awb_layout.setVisibility(View.VISIBLE);

            received_awb_txt.setText(pickupParcelData.getAwb());


            if (pickupParcelData.getPayment().getPayment_type() == 1) {

                received_transactionid_lay.setVisibility(View.VISIBLE);
                received_payment_label.setText(getString(R.string.card_prefix_received));
                received_payment_txt.setText(getString(R.string.auction_collection_val, amount));
                received_transid_txt.setText(pickupParcelData.getPickParcelDetailData().getParcel_card_transaction_id());

            } else if (pickupParcelData.getPayment().getPayment_type() == 2) {
                received_transactionid_lay.setVisibility(View.GONE);
                received_payment_label.setText(getString(R.string.cash_prefix_received));
                received_payment_txt.setText(getString(R.string.auction_collection_val, amount));

            } else if (pickupParcelData.getPayment().getPayment_type() == 3) {
                received_transactionid_lay.setVisibility(View.GONE);
                received_payment_label.setText(getString(R.string.on_account_prefix));
                received_payment_txt.setText(getString(R.string.auction_collection_val, amount));

            } else {
                received_transactionid_lay.setVisibility(View.GONE);
                received_payment_label.setText(getString(R.string.cod_prefix));
                received_payment_txt.setText(getString(R.string.auction_collection_val, amount));

            }


        } else {
            String comment_pickup = pickupParcelData.getPickParcelDetailData().getParcel_pickup_comment();
            setComment(comment_pickup);

            btn_layout.setVisibility(View.VISIBLE);
//            shipment_edit.setVisibility(View.VISIBLE);

            payment_method_lay.setVisibility(View.VISIBLE);
            awb_scan_layout.setVisibility(View.VISIBLE);
            received_payment_method_lay.setVisibility(View.GONE);
            received_awb_layout.setVisibility(View.GONE);


            if (pickupParcelData.getPayment().getPayment_type() == 1) {
                card_lay.setVisibility(View.VISIBLE);
                cash_lay.setVisibility(View.GONE);
                on_account_lay.setVisibility(View.GONE);
                COD_lay.setVisibility(View.GONE);

            } else if (pickupParcelData.getPayment().getPayment_type() == 2) {
                card_lay.setVisibility(View.GONE);
                cash_lay.setVisibility(View.VISIBLE);
                on_account_lay.setVisibility(View.GONE);
                COD_lay.setVisibility(View.GONE);
            } else if (pickupParcelData.getPayment().getPayment_type() == 3) {
                card_lay.setVisibility(View.GONE);
                cash_lay.setVisibility(View.GONE);
                on_account_lay.setVisibility(View.VISIBLE);
                COD_lay.setVisibility(View.GONE);
                on_account_price_txt.setText(getString(R.string.auction_collection_val, amount));
            } else {
                card_lay.setVisibility(View.GONE);
                cash_lay.setVisibility(View.GONE);
                on_account_lay.setVisibility(View.GONE);
                COD_lay.setVisibility(View.VISIBLE);
                cod_price_txt.setText(getString(R.string.auction_collection_val, amount));
            }
        }

    }

    public void setComment(String comment_pickup) {

        if (comment_pickup != null && comment_pickup.trim().length() > 0) {
            comment.setText(comment_pickup);
            comment_layout.setVisibility(View.VISIBLE);
        } else {
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
//                pickupParcel();
                if (validate()) {
//                    pickupParcel();
                    confirmPickup();
                }
                break;
            case R.id.scanbail_phone_img:
                addAWBbarcode_edt.setText("");
                startActivityForResult(new Intent(activity(), CameraTestActivity.class), AWB_SCAN);
                break;
            case R.id.collection_address_edit:

//                Intent intent = new Intent(activity(), CollectionAddressEditActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("DATA", pickupParcelData);
//
//                intent.putExtra(AppConstant.Keys.DATA, bundle);
//
//                startActivityForResult(intent, PICKUP_ADDRESS_EDIT);
                break;
            case R.id.delivery_address_edit:
//                Intent intent1 = new Intent(activity(), CollectionAddressEditActivity.class);
//                Bundle bundle1 = new Bundle();
//                bundle1.putSerializable("DATA", pickupParcelData);
//                bundle1.putBoolean("ISPICKUP", false);
//
//                intent1.putExtra(AppConstant.Keys.DATA, bundle1);
//
//                startActivityForResult(intent1, DELIVERY_ADDRESS_EDIT);
                break;
            case R.id.shipment_edit:
                Bundle bundle1 = new Bundle();//
                bundle1.putSerializable(UrlConstants.KEY_DATA, pickupParcelData);
                gotoActivityForResult(DeliveryShipmentViewActivity.class, bundle1, SHIPMENT_EDIT);


                break;
        }

    }

    private void pickupParcel() {

//        DIDbHelper.deliverParcelandUpdateTransaction(activity(),parcelDatas,new ParcelStatus(""+ParcelListingData.ParcelData.DELIVERED,"DELIVERED"),iscard,transcid,collectedby,totalamount,parcelDatas.get(0).getCurrency(),pod,nationalId);
//        DIDbHelper.receivedParcel(context, pickupParcelData, new ParcelStatus(""+ ParcelListingData.ParcelData.PICKUP_RECEIVED, "RECEIVED"));
        DIDbHelper.receivedParcel_new(context, pickupParcelData, new ParcelStatus("" + ParcelListingData.ParcelData.PICKUP_RECEIVED, "RECEIVED"));
//        ((BaseActivity)activity()).syncData();
        if (Utils.isConnectingToInternet(context)) {
//            ((BaseActivity) activity()).syncData();
            syncData();
            Toast.makeText(context, getString(R.string.saved_success), Toast.LENGTH_LONG).show();
//            SweetAlertUtil.showSweetSuccessMessageToast(activity(), getString(R.string.saved_success));
            activity().finish();
        } else {
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

//        String comtn_detail = (comment.getText()+"").trim();
//        if(comtn_detail != null && comtn_detail.length() > 0 && !comtn_detail.equalsIgnoreCase("Comment")){
//            comment_edt.setText(comtn_detail);
//        }


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

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment_txt = (comment_edt.getText() + "").trim();
                if (DIHelper.validatePickupComment(context, comment_txt)) {

                    showProgress();
                    DIDbHelper.updatePickupStatus(context, pickupParcelData, new ParcelStatus("" + ParcelListingData.ParcelData.PICKUP_ATTEMPTED, comment_txt));
                    if (Utils.isConnectingToInternet(context)) {
//                        ((BaseActivity) context).syncData();
                        syncData();
                        Toast.makeText(context, getString(R.string.saved_success), Toast.LENGTH_LONG).show();
                    } else {
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

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });


        // show it
        alertDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if (status_options_List.size() - 1 != i) {

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

    private void initAdapter() {

        adapter = new ShipmentAdapter(activity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity());
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setNestedScrollingEnabled(false);
        recyclerview.setAdapter(adapter);

        if (pickupParcelData != null && pickupParcelData.getShipment() != null && pickupParcelData.getShipment().getShipment_details().size() > 0) {
            adapter.addDataListToList(pickupParcelData.getShipment().getShipment_details());
        }
    }

    private boolean validate() {
        float actualReceivableAmount = Float.parseFloat(pickupParcelData.getPickParcelDetailData().getParcel_price());
        if (pickupParcelData.getPayment().getPayment_type() == 1) {
            String cardStr = "" + card_price_edt.getText();
            String trans_id = "" + card_trans_id_edt.getText();

            if (!(cardStr != null && cardStr.length() > 0)) {
                Toast.makeText(context, getString(R.string.collection_empty_amount_msg), Toast.LENGTH_LONG).show();
                return false;
            } else if (!(trans_id != null && trans_id.length() > 0)) {
                Toast.makeText(context, getString(R.string.collection_empty_transaction_id_msg), Toast.LENGTH_LONG).show();
                return false;
            } else {

                float card = Float.parseFloat(cardStr);
                float returnAmount = card - actualReceivableAmount;
                if (returnAmount == 0) {
                    if (validateAWB()) {
                        pickupParcelData.getPayment().setCard_transaction_id(trans_id);
                        return true;
                    } else {
                        return false;
                    }
                } else {

                    Toast.makeText(context, getString(R.string.collection_valid_amount_msg), Toast.LENGTH_LONG).show();
                    return false;
                }
            }

        } else if (pickupParcelData.getPayment().getPayment_type() == 2) {
            String cashStr = "" + cash_price_edt.getText();
            if (!(cashStr != null && cashStr.length() > 0)) {
                Toast.makeText(context, getString(R.string.collection_empty_amount_msg), Toast.LENGTH_LONG).show();
                return false;
            } else {
                float cash = Float.parseFloat(cashStr);
                float returnAmount = cash - actualReceivableAmount;
                if (returnAmount >= 0) {
                    return validateAWB();
                } else {
                    Toast.makeText(context, getString(R.string.collection_valid_amount_msg), Toast.LENGTH_LONG).show();
                    return false;
                }
            }
        } else if (pickupParcelData.getPayment().getPayment_type() == 3) {
            boolean chk = on_account_chk.isChecked();
            if (chk) {
                return validateAWB();
            } else {
                Toast.makeText(context, getString(R.string.collection_checkbox_msg), Toast.LENGTH_LONG).show();
                return false;

            }
        } else {
            boolean chk = COD_chk.isChecked();
            if (chk) {
                return validateAWB();
            } else {
                Toast.makeText(context, getString(R.string.collection_checkbox_msg), Toast.LENGTH_LONG).show();
                return false;

            }
        }
    }

    private void setReturnAmount() {

        float receivedAmount = 0;

        String cashPriceStr = ("" + cash_price_edt.getText()).trim();

        if (cashPriceStr != null && cashPriceStr.length() > 0) {
            receivedAmount = Float.parseFloat(cashPriceStr);
        }

        float actualReceivableAmount = Float.parseFloat(pickupParcelData.getPickParcelDetailData().getParcel_price());

        float returnAmount = receivedAmount - actualReceivableAmount;

        if (returnAmount < 0) {
            returnAmount = 0f;
        }

        return_txt.setText(getString(R.string.auction_collection_val, DIHelper.getValueInDecimal(returnAmount)));

//        DecimalFormat df = new DecimalFormat("0.00");
//        if (returnAmount >= 0) {
//
//            return_txt.setText(getString(R.string.auction_collection_val, "" + df.format(returnAmount)));
////            return_txt.setText(getString(R.string.auction_collection_val, "" + returnAmount));
//        } else {
//            returnAmount = 0.0f;
//            return_txt.setText(getString(R.string.auction_collection_val, "" + df.format(0f)));
//        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        setReturnAmount();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case AWB_SCAN:

                if (resultCode == Activity.RESULT_OK && data.hasExtra(CameraTestActivity.INTENT_BARCODE_VALUE)) {
                    String barcode = data.getStringExtra(CameraTestActivity.INTENT_BARCODE_VALUE).trim();

                    if (barcode != null && barcode.length() > 0) {
                        addAWBbarcode_edt.setText(barcode);
                    } else {
                        Toast.makeText(context, getString(R.string.valid_barcode_readable_msg), Toast.LENGTH_LONG).show();
                    }
                }
                break;

            case PICKUP_ADDRESS_EDIT:

                if (resultCode == Activity.RESULT_OK && data.hasExtra(CameraTestActivity.INTENT_BARCODE_VALUE)) {
                    activity().finish();
                    Toast.makeText(context, getString(R.string.saved_success), Toast.LENGTH_LONG).show();
                }
                break;
            case DELIVERY_ADDRESS_EDIT:

                if (resultCode == Activity.RESULT_OK && data.hasExtra(CameraTestActivity.INTENT_BARCODE_VALUE)) {
                    activity().finish();
                    Toast.makeText(context, getString(R.string.saved_success), Toast.LENGTH_LONG).show();
                }
                break;
            case SHIPMENT_EDIT:

                if (resultCode == Activity.RESULT_OK && data.hasExtra(CameraTestActivity.INTENT_BARCODE_VALUE)) {
                    String barcode = data.getStringExtra(CameraTestActivity.INTENT_BARCODE_VALUE).trim();

                    if (barcode != null && barcode.length() > 0) {
                        addAWBbarcode_edt.setText(barcode);
                    } else {
                        Toast.makeText(context, getString(R.string.valid_barcode_readable_msg), Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }

    }

    private boolean validateAWB() {
        String awbStr = ("" + addAWBbarcode_edt.getText()).trim();
        if (awbStr != null && awbStr.length() > 0) {
            if (DIDbHelper.validateAWB(activity(), awbStr)) {
                pickupParcelData.setAwb(awbStr);
                return true;
            } else {
                Toast.makeText(context, getString(R.string.collection_awb_exist_msg), Toast.LENGTH_LONG).show();
                return false;
            }
        } else {
            Toast.makeText(context, getString(R.string.collection_empty_awb_msg), Toast.LENGTH_LONG).show();
            return false;
        }
    }


    private void confirmPickup() {
        SweetAlertUtil.showMessageWithCallback(activity(), getString(R.string.collection_confirm_title), getString(R.string.collection_confirm_msg), getString(R.string.yes), getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        pickupParcel();

                        sweetAlertDialog.dismiss();
                    }
                }
                , new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
    }

    public void onEditClick() {
        Intent intent = new Intent(activity(), CollectionAddressEditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("DATA", pickupParcelData);

        intent.putExtra(AppConstant.Keys.DATA, bundle);

        String  jsonStr = new Gson().toJson(pickupParcelData);
        Log.i("PendingDetailActivity",jsonStr);

        startActivityForResult(intent, PICKUP_ADDRESS_EDIT);
    }
}
