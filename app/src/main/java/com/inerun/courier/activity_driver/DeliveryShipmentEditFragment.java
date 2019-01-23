package com.inerun.courier.activity_driver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.inerun.courier.R;
import com.inerun.courier.base.BaseFragment;
import com.inerun.courier.constant.AppConstant;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.data.PickupParcelData;
import com.inerun.courier.data.Services;
import com.inerun.courier.data.Shipment;
import com.inerun.courier.helper.DIHelper;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by vinay on 01/01/19.
 */

public class DeliveryShipmentEditFragment extends BaseFragment implements View.OnClickListener {
    private Context context;
    private PickupParcelData pickupParcelData;

    private RecyclerView recyclerView;
    private SweetAlertDialog progressdialog;

    private EditText shipment_edit_pcs, shipment_edit_length, shipment_edit_width, shipment_edit_height, shipment_edit_gross_weight, shipment_edit_dec_value;
    private int requestcode, position;
    private Button shipment_edit_dec_cancel, shipment_edit_dec_submit;
    private TextView shipment_vol_weight_txt, shipment_chr_weight_txt;
    private MaterialBetterSpinner unitspinner;
    private ArrayAdapter<String> unitarrayAdapter;


    public static DeliveryShipmentEditFragment newInstance(int requestcode, int position, PickupParcelData pickupParcelData) {
        Bundle args = new Bundle();

        DeliveryShipmentEditFragment fragment = new DeliveryShipmentEditFragment();
        args.putSerializable(UrlConstants.KEY_DATA, pickupParcelData);
        args.putInt(UrlConstants.KEY_REQUEST_CODE, requestcode);
        args.putInt(UrlConstants.KEY_POSITION, position);

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = activity();

        pickupParcelData = (PickupParcelData) getArguments().getSerializable(UrlConstants.KEY_DATA);
        requestcode = getArguments().getInt(UrlConstants.KEY_REQUEST_CODE);
        position = getArguments().getInt(UrlConstants.KEY_POSITION);


    }

    @Override
    public int inflateView() {
        return R.layout.delivershipment_edit_row;
    }

    @Override
    public void customOnCreateView(View root, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) throws Exception {
        setShowBackArrow(true);
        shipment_edit_pcs = (EditText) root.findViewById(R.id.shipment_edit_pcs);
        shipment_edit_length = (EditText) root.findViewById(R.id.shipment_edit_length);
        shipment_edit_width = (EditText) root.findViewById(R.id.shipment_edit_width);
        shipment_edit_height = (EditText) root.findViewById(R.id.shipment_edit_height);
        shipment_chr_weight_txt = (TextView) root.findViewById(R.id.shipment_edit_chr_weight);
        shipment_edit_gross_weight = (EditText) root.findViewById(R.id.shipment_edit_gross_weight);
        shipment_edit_dec_cancel = (Button) root.findViewById(R.id.shipment_edit_dec_cancel);
        shipment_edit_dec_value = (EditText) root.findViewById(R.id.shipment_edit_dec_value);
        shipment_edit_dec_submit = (Button) root.findViewById(R.id.shipment_edit_dec_submit);
        shipment_vol_weight_txt = (TextView) root.findViewById(R.id.shipment_edit_vol_weight);
        unitspinner = root.findViewById(R.id.unitspinner);

        recyclerView = root.findViewById(R.id.shipmenteditrecyclerView);

        setListener();
        setUnits();


        setData();
    }

    private void setListener() {
        shipment_edit_pcs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if(isValidString(str))
                calculateVolWeightAndChargeableWeight(unitspinner.getText() + "");
            }
        });

        shipment_edit_length.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if(isValidString(str))
                calculateVolWeightAndChargeableWeight(unitspinner.getText() + "");
            }
        });

        shipment_edit_width.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if(isValidString(str))
                calculateVolWeightAndChargeableWeight(unitspinner.getText() + "");
            }
        });

        shipment_edit_height.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if(isValidString(str))
                calculateVolWeightAndChargeableWeight(unitspinner.getText() + "");
            }
        });

        shipment_edit_gross_weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if(isValidString(str))
                calculateVolWeightAndChargeableWeight(unitspinner.getText() + "");
            }
        });
    }


    private void setData() {


        if (requestcode == DeliveryShipmentViewFragment.EDIT_SHIPMENT) {
            shipment_edit_pcs.setText(pickupParcelData.getShipment().getShipment_details().get(position).getNo_of_parcels());
            shipment_edit_length.setText(pickupParcelData.getShipment().getShipment_details().get(position).getLength());
            shipment_edit_width.setText(pickupParcelData.getShipment().getShipment_details().get(position).getBreath());
            shipment_edit_height.setText(pickupParcelData.getShipment().getShipment_details().get(position).getHeight());
            shipment_vol_weight_txt.setText(pickupParcelData.getShipment().getShipment_details().get(position).getVol_weight());
            shipment_chr_weight_txt.setText(pickupParcelData.getShipment().getShipment_details().get(position).getChargeable_weight());
            shipment_edit_dec_value.setText(pickupParcelData.getShipment().getShipment_details().get(position).getDeclared_value());
            shipment_edit_gross_weight.setText(pickupParcelData.getShipment().getShipment_details().get(position).getGross_weight());
            shipment_edit_dec_value.setText(pickupParcelData.getShipment().getShipment_details().get(position).getDeclared_value());

            String [] array = DIHelper.getUnits(activity());
            for (int i = 0; i < array.length; i++){
                if(pickupParcelData.getShipment().getShipment_details().get(position).getType().toUpperCase().equals(array [i].toUpperCase())){
                    unitspinner.setText(array [i]);
                }
            }

        }

        unitspinner.setText(DIHelper.getUnits(activity())[0]);



        shipment_edit_dec_submit.setOnClickListener(this);
        shipment_edit_dec_cancel.setOnClickListener(this);

    }

    @Override
    protected String getAnalyticsName() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.shipment_edit_dec_cancel:
                Intent intent = new Intent();
                activity().setResult(Activity.RESULT_CANCELED, intent);
                activity().finish();

                break;
            case R.id.shipment_edit_dec_submit:
                if (requestcode == DeliveryShipmentViewFragment.EDIT_SHIPMENT) {
                    submitEditedData();
                } else {
                    submitNewData();
                }
                break;
        }
    }

    private void submitEditedData() {
        String pcs = ("" + shipment_edit_pcs.getText()).trim();
        String length = ("" + shipment_edit_length.getText()).trim();
        String width = ("" + shipment_edit_width.getText()).trim();
        String height = ("" + shipment_edit_height.getText()).trim();
        String volweight = ("" + shipment_vol_weight_txt.getText()).trim();
        String chr_weight = ("" + shipment_chr_weight_txt.getText()).trim();
        String dec_value = ("" + shipment_edit_dec_value.getText()).trim();
        String gross_weight = ("" + shipment_edit_gross_weight.getText()).trim();
        String unit = ("" + unitspinner.getText()).trim().toLowerCase();


        if(!isValidString(pcs)){
            showLongToast("Pieces is required");
        }else if(!isValidString(length)){
            showLongToast("Length is required");
        } else if(!isValidString(width)){
            showLongToast("Width is required");
        } else if(!isValidString(height)){
            showLongToast("Height is required");
        } else if(!isValidString(gross_weight)){
            showLongToast("Gross Weight is required");
        }else {

            pickupParcelData.getShipment().getShipment_details().get(position).setNo_of_parcels(pcs);
            pickupParcelData.getShipment().getShipment_details().get(position).setLength(length);
            pickupParcelData.getShipment().getShipment_details().get(position).setBreath(width);
            pickupParcelData.getShipment().getShipment_details().get(position).setHeight(height);
            pickupParcelData.getShipment().getShipment_details().get(position).setVol_weight(volweight);
            pickupParcelData.getShipment().getShipment_details().get(position).setChargeable_weight(chr_weight);
            pickupParcelData.getShipment().getShipment_details().get(position).setDeclared_value(dec_value);
            pickupParcelData.getShipment().getShipment_details().get(position).setGross_weight(gross_weight);
            pickupParcelData.getShipment().getShipment_details().get(position).setType(unit);


            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable(UrlConstants.KEY_DATA, pickupParcelData);
            intent.putExtra(AppConstant.Keys.DATA, bundle);
            intent.putExtra(UrlConstants.KEY_REQUEST_CODE, DeliveryShipmentViewFragment.EDIT_SHIPMENT);
            intent.putExtra(UrlConstants.KEY_POSITION, position);
            activity().setResult(Activity.RESULT_OK, intent);
            activity().finish();
        }

    }

    private void submitNewData() {
        String pcs = ("" + shipment_edit_pcs.getText()).trim();
        String length = ("" + shipment_edit_length.getText()).trim();
        String width = ("" + shipment_edit_width.getText()).trim();
        String height = ("" + shipment_edit_height.getText()).trim();
        String volweight = ("" + shipment_vol_weight_txt.getText()).trim();
        String chr_weight = ("" + shipment_chr_weight_txt.getText()).trim();
        String dec_value = ("" + shipment_edit_dec_value.getText()).trim();
        String gross_weight = ("" + shipment_edit_gross_weight.getText()).trim();
        String unit = ("" + unitspinner.getText()).trim().toLowerCase();

        if(!isValidString(pcs)){
            showLongToast("Pieces is required");
        }else if(!isValidString(length)){
            showLongToast("Length is required");
        } else if(!isValidString(width)){
            showLongToast("Width is required");
        } else if(!isValidString(height)){
            showLongToast("Height is required");
        } else if(!isValidString(gross_weight)){
            showLongToast("Gross Weight is required");
        }else {

            Shipment.ShipmentDetail shipmentDetail = new Shipment().new ShipmentDetail("", pcs, length, width, height, volweight, gross_weight, unit, chr_weight, dec_value, "", "", "");
//        pickupParcelData.getShipment().getShipment_details().get(position).setNo_of_parcels(pcs);
//        pickupParcelData.getShipment().getShipment_details().get(position).setLength(length);
//        pickupParcelData.getShipment().getShipment_details().get(position).setBreath(width);
//        pickupParcelData.getShipment().getShipment_details().get(position).setHeight(height);
//        pickupParcelData.getShipment().getShipment_details().get(position).setVol_weight(volweight);
//        pickupParcelData.getShipment().getShipment_details().get(position).setChargeable_weight(chr_weight);
//        pickupParcelData.getShipment().getShipment_details().get(position).setDeclared_value(dec_value);
//        pickupParcelData.getShipment().getShipment_details().get(position).setGross_weight(gross_weight);


            pickupParcelData.getShipment().getShipment_details().add(shipmentDetail);

            pickupParcelData.getShipment().reCalculateData();

            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable(UrlConstants.KEY_DATA, pickupParcelData);
            intent.putExtra(AppConstant.Keys.DATA, bundle);
            intent.putExtra(UrlConstants.KEY_REQUEST_CODE, DeliveryShipmentViewFragment.VIEW_SHIPMENT);
            intent.putExtra(UrlConstants.KEY_POSITION, position);
            activity().setResult(Activity.RESULT_OK, intent);
            activity().finish();
        }

    }

    public void calculateVolWeightAndChargeableWeight(String unit) {
        String pieces = ("" + shipment_edit_pcs.getText()).trim();
        String length = ("" + shipment_edit_length.getText()).trim();
        String width = ("" + shipment_edit_width.getText()).trim();
        String height = ("" + shipment_edit_height.getText()).trim();

        String grossWeight = ("" + shipment_edit_gross_weight.getText()).trim();

        float devidend = 5000;
        if (unit.equals("LB")) {
            devidend = 166;
        } else {
            devidend = 5000;
        }

        if (isValidString(pieces) && isValidString(length) && isValidString(width) && isValidString(height)) {
            float volweight = Float.parseFloat(pieces) * Float.parseFloat(length) * Float.parseFloat(width) * Float.parseFloat(height);
            float finalVolWeight = volweight / devidend;

            String finalVolWeightstr = DIHelper.getValue4Decimal(finalVolWeight);

            shipment_vol_weight_txt.setText(finalVolWeightstr);

            if (isValidString(grossWeight)) {
                if (Float.parseFloat(grossWeight) > finalVolWeight) {
                    shipment_chr_weight_txt.setText(grossWeight);
                } else {
                    shipment_chr_weight_txt.setText(finalVolWeightstr);
                }
            } else {
                shipment_chr_weight_txt.setText(finalVolWeightstr);
            }
        }


    }

    private void setUnits() {

        unitarrayAdapter = new ArrayAdapter<>(activity(), android.R.layout.simple_dropdown_item_1line, DIHelper.getUnits(activity()));
        unitspinner.setAdapter(unitarrayAdapter);
////        servicespinner.
//        int serviceindex = servicelist.indexOf(new Services(pickupParcelData.getShipment().getSelectedservice()));
//        if (serviceindex != -1) {
//            servicespinner.setText(servicelist.get(serviceindex).getService_name());
//        }
//        unitspinner.setText(DIHelper.getUnits(activity())[0]);

        unitspinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Pos", position + "");
//                region_spinner.setVisibility(View.GONE);
//                city_spinner.setVisibility(View.GONE);
//                setRegion(countryList.get(position));
//                servicespinner.setText(servicesArrayList.get(position).getService_name());
//                pickupParcelData.getShipment().setService(servicesArrayList.get(position));
//                pickupParcelData.getShipment().setSelectedservice(servicesArrayList.get(position).getService_id());

                calculateVolWeightAndChargeableWeight(DIHelper.getUnits(activity())[position]);

            }
        });


    }
}
