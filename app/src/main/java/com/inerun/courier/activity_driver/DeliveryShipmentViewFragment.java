package com.inerun.courier.activity_driver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.inerun.courier.R;
import com.inerun.courier.activity_auction.IonServiceManager;
import com.inerun.courier.adapter.DeliveryShipmentViewParcelsAdapter;
import com.inerun.courier.base.BaseFragment;
import com.inerun.courier.base.ClickListener;
import com.inerun.courier.base.DeviceInfoUtil;
import com.inerun.courier.base.SweetAlertUtil;
import com.inerun.courier.constant.AppConstant;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.data.ParcelType;
import com.inerun.courier.data.PickupParcelData;
import com.inerun.courier.data.Services;
import com.inerun.courier.data.Surcharge;
import com.inerun.courier.helper.DIHelper;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by vinay on 01/01/19.
 */

public class DeliveryShipmentViewFragment extends BaseFragment {
    public static final int EDIT_SHIPMENT = 101;
    public static final int VIEW_SHIPMENT = 102;
    private Context context;
    private PickupParcelData pickupParcelData;
    private DeliveryShipmentViewParcelsAdapter mAdapter;
    private RecyclerView recyclerView;
    private SweetAlertDialog progressdialog;
    private ImageView delivery_shipment_document, delivery_shipment_parcel;
    private EditText parcel_content_edt, specialinsedt, insuredvalueedt;
    private LinearLayout addmoreshipmentlayout, insuredvaluelayout;
    private TextView totaldecvalue, mytotalchargeableweight, insuredvaluetxt;
    private RadioGroup holdforcollectionRG, insuracerequiredRG;
    private RadioButton holdforcollectionyes, holdforcollectionno, insuracerequiredyes, insuracerequiredno;
    private ClickListener itemclicklistener;
    private Button next_btn;
    private MaterialBetterSpinner servicespinner;
    private ArrayAdapter<String> servicesarrayAdapter;
    private TextView delivery_shipment_document_title, delivery_shipment_parcel_title;
    ArrayList<ParcelType> parcelTypeArrayList;
    ArrayList<Services> servicelist;
    ClickListener clicklistener;
    String insurancepercent;
    private MaterialBetterSpinner surchargespinner;
    private ArrayAdapter<String> surchargearrayAdapter;
    ArrayList<Surcharge> surchargelist;

    public static DeliveryShipmentViewFragment newInstance(PickupParcelData pickupParcelData) {
        Bundle args = new Bundle();

        DeliveryShipmentViewFragment fragment = new DeliveryShipmentViewFragment();
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
        return R.layout.delivery_shipment_view;
    }

    @Override
    public void customOnCreateView(View root, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) throws Exception {
        ((CollectionAddressEditActivity) getActivity()).clickingOnTab(2);
        setShowBackArrow(true);
        delivery_shipment_document = (ImageView) root.findViewById(R.id.delivery_shipment_document);
        delivery_shipment_parcel = (ImageView) root.findViewById(R.id.delivery_shipment_parcel);
        totaldecvalue = (TextView) root.findViewById(R.id.totaldecvalue);
        mytotalchargeableweight = (TextView) root.findViewById(R.id.mytotalchargeableweight);
        holdforcollectionRG = (RadioGroup) root.findViewById(R.id.holdforcollectionRG);
        holdforcollectionyes = (RadioButton) root.findViewById(R.id.holdforcollectionyes);
        holdforcollectionno = (RadioButton) root.findViewById(R.id.holdforcollectionno);
        insuracerequiredRG = (RadioGroup) root.findViewById(R.id.insuracerequiredRG);
        insuracerequiredyes = (RadioButton) root.findViewById(R.id.insuracerequiredyes);
        insuracerequiredno = (RadioButton) root.findViewById(R.id.insuracerequiredno);
        parcel_content_edt = (EditText) root.findViewById(R.id.parcel_content_edt);
        insuredvaluetxt = (TextView) root.findViewById(R.id.insuredvaluetxt);
        specialinsedt = (EditText) root.findViewById(R.id.specialinsedt);
        servicespinner = (MaterialBetterSpinner) root.findViewById(R.id.servicespinner);
        delivery_shipment_document_title = (TextView) root.findViewById(R.id.delivery_shipment_document_title);
        delivery_shipment_parcel_title = (TextView) root.findViewById(R.id.delivery_shipment_parcel_title);
        addmoreshipmentlayout = (LinearLayout) root.findViewById(R.id.addmoreshipmentlayout);
        insuredvaluelayout = (LinearLayout) root.findViewById(R.id.insuredvaluelayout);
        recyclerView = root.findViewById(R.id.shipmentviewrecyclerView);
        next_btn = root.findViewById(R.id.next_btn);
        surchargespinner = (MaterialBetterSpinner) root.findViewById(R.id.surchargespinner);


        setListiner();

        next_btn.setOnClickListener(clicklistener);

        if (servicelist == null)
            getShipmentService();

    }

    private void setListiner() {
        clicklistener = new ClickListener(activity()) {

            public void click(View v) throws Exception {
                switch (v.getId()) {
                    case R.id.delivery_shipment_document:
                        if (parcelTypeArrayList.get(0).hasTextfield()) {
                            parcel_content_edt.setVisibility(View.VISIBLE);
                            parcel_content_edt.setHint(parcelTypeArrayList.get(0).getTextfield());
                            pickupParcelData.getShipment().setParceltype(parcelTypeArrayList.get(0).getType_id());
                        } else {
                            parcel_content_edt.setVisibility(View.GONE);
                        }
                        delivery_shipment_document.setBackgroundColor(context.getColor(R.color.grey_300));
                        delivery_shipment_parcel.setBackgroundColor(context.getColor(R.color.float_transparent));


                        break;
                    case R.id.delivery_shipment_parcel:
                        if (parcelTypeArrayList.get(1).hasTextfield()) {
                            parcel_content_edt.setVisibility(View.VISIBLE);
                            parcel_content_edt.setHint(parcelTypeArrayList.get(1).getTextfield());
                            pickupParcelData.getShipment().setParceltype(parcelTypeArrayList.get(1).getType_id());
                        } else {
                            parcel_content_edt.setVisibility(View.GONE);
                        }

                        delivery_shipment_document.setBackgroundColor(context.getColor(R.color.float_transparent));
                        delivery_shipment_parcel.setBackgroundColor(context.getColor(R.color.grey_300));


                        break;
                    case R.id.addmoreshipmentlayout:
                        Intent intent = new Intent(context, DeliveryShipmentEditActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(UrlConstants.KEY_DATA, pickupParcelData);
                        intent.putExtra(AppConstant.Keys.DATA, bundle);

                        intent.putExtra(UrlConstants.KEY_REQUEST_CODE, VIEW_SHIPMENT);
                        startActivityForResult(intent, VIEW_SHIPMENT);
                        break;

                    case R.id.next_btn:
                        validateShipment();

//
//                        if (parcel_content_edt.getVisibility() == View.VISIBLE) {
//                            pickupParcelData.getShipment().setParceltypetext("" + parcel_content_edt.getText());
//                        }
//
//
//                        String pickup_country_id = pickupParcelData.getPickup_address().getCountryid();
//                        String delivery_country_id = pickupParcelData.getDelivery_address().getCountryid();
//                        String shipperid = pickupParcelData.getPickup_address().getCountryid();
//                        String destinationid = pickupParcelData.getDelivery_address().getCountryid();
//
//                        if (pickup_country_id.equals(delivery_country_id)) {
//                            shipperid = pickupParcelData.getPickup_address().getCityid();
//                            destinationid = pickupParcelData.getDelivery_address().getCityid();
//                        } else {
//                            shipperid = pickupParcelData.getPickup_address().getCountryid();
//                            destinationid = pickupParcelData.getDelivery_address().getCountryid();
//                        }
//
//
//                        String parceltype = pickupParcelData.getShipment().getParceltype();
//                        String totalweight = pickupParcelData.getShipment().getGross_weight();
//                        String service_id = pickupParcelData.getShipment().getSelectedservice();
//                        String customerid = pickupParcelData.getCustomer_id();
//                        String pickup = "1"; // self 0 and collection 1
//                        String preferred_collection_date = "2019-01-18";
//                        String applyinsurance = pickupParcelData.getShipment().getInsurancerequired();
//
//
//                        if ((!isEmpty(pickup_country_id)) && (!isEmpty(delivery_country_id)) && (!isEmpty(shipperid)) && (!isEmpty(destinationid)) && (!isEmpty(parceltype)) && (!isEmpty(totalweight))
//                                && (!isEmpty(service_id)) && (!isEmpty(customerid)) && (!isEmpty(pickup)) && (!isEmpty(preferred_collection_date)) && (!isEmpty(applyinsurance))) {
//                            String params[] = {IonServiceManager.KEYS.ANDROID_ID, DeviceInfoUtil.getAndroidID(activity()), IonServiceManager.KEYS.PICKUP_COUNTRY_ID, shipperid, IonServiceManager.KEYS.DELIVERY_COUNTRY_ID, destinationid, IonServiceManager.KEYS.PARCEL_TYPE, parceltype, IonServiceManager.KEYS.TOTAL_WEIGHT, totalweight, IonServiceManager.KEYS.SERVICE_ID, service_id, IonServiceManager.KEYS.CUST_ID, customerid, IonServiceManager.KEYS.PICKUP_TYPE, pickup, IonServiceManager.KEYS.COLLECTION_DATE, preferred_collection_date, IonServiceManager.KEYS.APPLY_INSURANCE, applyinsurance};
//                            callService(params);
//                        } else {
//                            SweetAlertUtil.showWarningWithCallback(activity(), getString(R.string.empty_val_error_msg), getString(R.string.ok), new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                    sweetAlertDialog.cancel();
////
//                                }
//                            });
//                        }

                        break;
                }
            }
        };
    }

    private void getShipmentService() throws IonServiceManager.InvalidParametersException {
        progressdialog = SweetAlertUtil.getProgressDialog(activity());
        progressdialog.show();
        Gson gson = new Gson();
        String pickup_address = gson.toJson(pickupParcelData.getPickup_address());
        String delivery_address = gson.toJson(pickupParcelData.getDelivery_address());
        String params[] = {IonServiceManager.KEYS.ANDROID_ID, DeviceInfoUtil.getAndroidID(activity()), IonServiceManager.KEYS.PICKUP_ADDRESS, pickup_address, IonServiceManager.KEYS.DELIVERY_ADDRESS, delivery_address};
        Log.i("params", params.toString());
        IonServiceManager serviceManager = getApp().ionserviceManager;
        serviceManager.postRequestToServerWOprogress(serviceManager.getAddress().ShipmentService, new IonServiceManager.ResponseCallback(activity()) {
            @Override
            public void onException(String exception) {
                Log.i("Req Completed", "" + exception);
//                showException(exception);
                progressdialog.dismiss();
                SweetAlertUtil.showWarningWithCallback(activity(), exception, activity().getString(R.string.ok), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        ((Activity) activity()).finish();
                    }
                });
                hideProgress();

            }

            @Override
            public void onResponse(boolean status, String message, Object result) throws JSONException, JsonSyntaxException {

                progressdialog.dismiss();
                JsonObject jsonObject = (JsonObject) result;

                JsonArray servicelistjson = jsonObject.get(IonServiceManager.KEYS.SERVICE).getAsJsonArray();
                JsonArray parceltypelistjson = jsonObject.get(IonServiceManager.KEYS.PARCEL_TYPE).getAsJsonArray();
                insurancepercent = jsonObject.get(IonServiceManager.KEYS.TAX).getAsString();
                servicelist = getGsonInstance().fromJson(servicelistjson, new TypeToken<ArrayList<Services>>() {
                }.getType());
                parcelTypeArrayList = getGsonInstance().fromJson(parceltypelistjson, new TypeToken<ArrayList<ParcelType>>() {
                }.getType());

                surchargelist = getGsonInstance().fromJson(parceltypelistjson, new TypeToken<ArrayList<Surcharge>>() {
                }.getType());

                setData(servicelist, parcelTypeArrayList, insurancepercent, surchargelist);

            }

        }, params);

    }

    private void setData(ArrayList<Services> servicelist, ArrayList<ParcelType> parcelTypeArrayList, String insurancepercent, ArrayList<Surcharge> surchargelist) {


        mytotalchargeableweight.setText(pickupParcelData.getShipment().getChargeable_weight());
        totaldecvalue.setText(pickupParcelData.getShipment().getTotal_declaredvalue());
        specialinsedt.setText(pickupParcelData.getShipment().getSpecialinstructions());


        delivery_shipment_document_title.setText(parcelTypeArrayList.get(0).getType_name());
        delivery_shipment_parcel_title.setText(parcelTypeArrayList.get(1).getType_name());
        setInsuranceValue(insurancepercent);


        mAdapter = new DeliveryShipmentViewParcelsAdapter(activity(), pickupParcelData, new ClickListener(activity()) {
            @Override
            public void click(View v) throws Exception {
                int pos = (int) v.getTag();
                Intent intent = new Intent(context, DeliveryShipmentEditActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(UrlConstants.KEY_DATA, pickupParcelData);
                intent.putExtra(AppConstant.Keys.DATA, bundle);
                intent.putExtra(UrlConstants.KEY_REQUEST_CODE, EDIT_SHIPMENT);
                intent.putExtra(UrlConstants.KEY_POSITION, pos);
                startActivityForResult(intent, EDIT_SHIPMENT);
            }
        });
        insuracerequiredRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.insuracerequiredyes) {
                    insuredvaluelayout.setVisibility(View.VISIBLE);
                    insuredvaluetxt.setText(pickupParcelData.getShipment().getInsuracevalue());
                } else if (checkedId == R.id.insuracerequiredno) {
                    insuredvaluelayout.setVisibility(View.GONE);
                }
            }
        });
        (pickupParcelData.getShipment().isHoldforcollection() ? holdforcollectionyes : holdforcollectionno).setChecked(true);
        (pickupParcelData.getShipment().isInsurancerequired() ? insuracerequiredyes : insuracerequiredno).setChecked(true);

        int parceltypeindex = parcelTypeArrayList.indexOf(new ParcelType(pickupParcelData.getShipment().getParceltype()));
        if (parceltypeindex == 0) {
            if (parcelTypeArrayList.get(0).hasTextfield()) {
                parcel_content_edt.setVisibility(View.VISIBLE);
                parcel_content_edt.setHint(parcelTypeArrayList.get(0).getTextfield());
                pickupParcelData.getShipment().setParceltype(parcelTypeArrayList.get(0).getType_id());
            } else {
                parcel_content_edt.setVisibility(View.GONE);
            }
            delivery_shipment_document.setBackgroundColor(context.getColor(R.color.grey_300));
            delivery_shipment_parcel.setBackgroundColor(context.getColor(R.color.float_transparent));

        } else if (parceltypeindex == 1) {
            if (parcelTypeArrayList.get(1).hasTextfield()) {
                parcel_content_edt.setVisibility(View.VISIBLE);
                parcel_content_edt.setHint(parcelTypeArrayList.get(1).getTextfield());
                pickupParcelData.getShipment().setParceltype(parcelTypeArrayList.get(1).getType_id());
            } else {
                parcel_content_edt.setVisibility(View.GONE);
            }
            delivery_shipment_parcel.setBackgroundColor(context.getColor(R.color.grey_300));
            delivery_shipment_document.setBackgroundColor(context.getColor(R.color.float_transparent));
        }

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        delivery_shipment_document.setOnClickListener(clicklistener);
        delivery_shipment_parcel.setOnClickListener(clicklistener);
        addmoreshipmentlayout.setOnClickListener(clicklistener);
        setServices(servicelist);
        setSurcharge(surchargelist);
    }

    private void setInsuranceValue(String insurancepercent) {
        Float percentfloat = Float.parseFloat(insurancepercent);
        float insuracevalue = (percentfloat * Float.parseFloat(pickupParcelData.getShipment().getTotal_declaredvalue()) / 100);
        pickupParcelData.getShipment().setInsuracevalue("" + insuracevalue);
        insuredvaluetxt.setText("" + insuracevalue);
    }

    private void setServices(final ArrayList<Services> servicesArrayList) {


        Log.i("serviceList", servicesArrayList.size() + "");
        servicesarrayAdapter = new ArrayAdapter<>(activity(), android.R.layout.simple_dropdown_item_1line, DIHelper.getServices(servicesArrayList));
        servicespinner.setAdapter(servicesarrayAdapter);
//        servicespinner.
        int serviceindex = servicelist.indexOf(new Services(pickupParcelData.getShipment().getSelectedservice()));
        if (serviceindex != -1) {
            servicespinner.setText(servicelist.get(serviceindex).getService_name());
        }

        servicespinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Pos", position + "");
//                region_spinner.setVisibility(View.GONE);
//                city_spinner.setVisibility(View.GONE);
//                setRegion(countryList.get(position));
//                servicespinner.setText(servicesArrayList.get(position).getService_name());
//                pickupParcelData.getShipment().setService(servicesArrayList.get(position));
                pickupParcelData.getShipment().setSelectedservice(servicesArrayList.get(position).getService_id());

            }
        });


    }

    private void setSurcharge(final ArrayList<Surcharge> surchargeArrayList) {


        Log.i("surchargeList", surchargeArrayList.size() + "");
        surchargearrayAdapter = new ArrayAdapter<>(activity(), android.R.layout.simple_dropdown_item_1line, DIHelper.getSurcharge(surchargeArrayList));
        surchargespinner.setAdapter(servicesarrayAdapter);
//        servicespinner.
        int serviceindex = surchargelist.indexOf(new Surcharge(pickupParcelData.getShipment().getSurcharge_type()));
        if (serviceindex != -1) {
            surchargespinner.setText(surchargelist.get(serviceindex).getSurcharge_name());
        }

        surchargespinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Pos", position + "");
//                region_spinner.setVisibility(View.GONE);
//                city_spinner.setVisibility(View.GONE);
//                setRegion(countryList.get(position));
//                servicespinner.setText(servicesArrayList.get(position).getService_name());
//                pickupParcelData.getShipment().setService(servicesArrayList.get(position));
                pickupParcelData.getShipment().setSurcharge_type(surchargeArrayList.get(position).getId());

            }
        });


    }

    @Override
    protected String getAnalyticsName() {
        return null;
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.delivery_shipment_document:
//                if (parcelTypeArrayList.get(0).hasTextfield()) {
//                    parcel_content_edt.setVisibility(View.VISIBLE);
//                    parcel_content_edt.setHint(parcelTypeArrayList.get(0).getTextfield());
//                    pickupParcelData.getShipment().setParceltype(parcelTypeArrayList.get(0).getType_id());
//                } else {
//                    parcel_content_edt.setVisibility(View.GONE);
//                }
//                delivery_shipment_document.setBackgroundColor(context.getColor(R.color.grey_300));
//                delivery_shipment_parcel.setBackgroundColor(context.getColor(R.color.float_transparent));
//
//
//                break;
//            case R.id.delivery_shipment_parcel:
//                if (parcelTypeArrayList.get(1).hasTextfield()) {
//                    parcel_content_edt.setVisibility(View.VISIBLE );
//                    parcel_content_edt.setHint(parcelTypeArrayList.get(1).getTextfield());
//                    pickupParcelData.getShipment().setParceltype(parcelTypeArrayList.get(1).getType_id());
//                } else {
//                    parcel_content_edt.setVisibility(View.GONE);
//                }
//
//                delivery_shipment_document.setBackgroundColor(context.getColor(R.color.float_transparent));
//                delivery_shipment_parcel.setBackgroundColor(context.getColor(R.color.grey_300));
//
//
//                break;
//            case R.id.addmoreshipmentlayout:
//                Intent intent = new Intent(context, DeliveryShipmentEditActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable(UrlConstants.KEY_DATA, pickupParcelData);
//                intent.putExtra(AppConstant.Keys.DATA, bundle);
//
//                intent.putExtra(UrlConstants.KEY_REQUEST_CODE, VIEW_SHIPMENT);
//                startActivityForResult(intent, VIEW_SHIPMENT);
//                break;
//
//            case R.id.next_btn:
//
//                try {
//                    validateService();
//                } catch (IonServiceManager.InvalidParametersException e) {
//                    e.printStackTrace();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
//        }
//    }

    public void refreshAdapter(PickupParcelData pickupParcelData) {
        this.pickupParcelData = pickupParcelData;

        mytotalchargeableweight.setText(pickupParcelData.getShipment().getChargeable_weight());
        totaldecvalue.setText(pickupParcelData.getShipment().getTotal_declaredvalue());

        setInsuranceValue(insurancepercent);
        mAdapter.refreshData(pickupParcelData);
        mAdapter.notifyDataSetChanged();
    }

    private void validateShipment() throws IonServiceManager.InvalidParametersException {

        if (parcel_content_edt.getVisibility() == View.VISIBLE) {
            pickupParcelData.getShipment().setParceltypetext("" + parcel_content_edt.getText());
        }


        String pickup_country_id = pickupParcelData.getPickup_address().getCountryid();
        String delivery_country_id = pickupParcelData.getDelivery_address().getCountryid();
        String shipperid = pickupParcelData.getPickup_address().getCountryid();
        String destinationid = pickupParcelData.getDelivery_address().getCountryid();
        String type = "0";

        if (pickup_country_id.equals(delivery_country_id)) {
            shipperid = pickupParcelData.getPickup_address().getCityid();
            destinationid = pickupParcelData.getDelivery_address().getCityid();
            type = "0";
        } else {
            shipperid = pickupParcelData.getPickup_address().getCountryid();
            destinationid = pickupParcelData.getDelivery_address().getCountryid();
            type = "1";
        }


        String parceltype = pickupParcelData.getShipment().getParceltype();
        String totalweight = pickupParcelData.getShipment().getGross_weight();
        String service_id = pickupParcelData.getShipment().getSelectedservice();
        String customerid = pickupParcelData.getCustomer_id();
        String pickup = pickupParcelData.getPickup_type(); // self 0 and collection 1
        String preferred_collection_date = pickupParcelData.getPreferred_collection_date();

        String applyinsurance = pickupParcelData.getShipment().getInsurancerequired();

        if (pickupParcelData.getShipment().isInsurancerequired()) {
            applyinsurance = pickupParcelData.getShipment().getInsuracevalue();
        } else {
            applyinsurance = "0";
        }


        //+++++++++++++++

//        String totalweight = pickupParcelData.getShipment().getChargeable_weight();
//        String service_id = pickupParcelData.getShipment().getService().getService_id();


        //+++++++++++++


        if ((!isEmpty(pickup_country_id)) && (!isEmpty(delivery_country_id)) && (!isEmpty(shipperid)) && (!isEmpty(destinationid)) && (!isEmpty(parceltype)) && (!isEmpty(totalweight))
                && (!isEmpty(service_id)) && (!isEmpty(customerid)) && (!isEmpty(pickup)) && (!isEmpty(preferred_collection_date)) && (!isEmpty(applyinsurance))) {
//            String params[] = {IonServiceManager.KEYS.ANDROID_ID, DeviceInfoUtil.getAndroidID(activity()), IonServiceManager.KEYS.PICKUP_COUNTRY_ID, shipperid, IonServiceManager.KEYS.DELIVERY_COUNTRY_ID, destinationid, IonServiceManager.KEYS.PARCEL_TYPE, parceltype, IonServiceManager.KEYS.TOTAL_WEIGHT, totalweight, IonServiceManager.KEYS.SERVICE_ID, service_id, IonServiceManager.KEYS.CUST_ID, customerid, IonServiceManager.KEYS.PICKUP_TYPE, pickup, IonServiceManager.KEYS.COLLECTION_DATE, preferred_collection_date, IonServiceManager.KEYS.APPLY_INSURANCE, applyinsurance};
            String params[] = {IonServiceManager.KEYS.ANDROID_ID, DeviceInfoUtil.getAndroidID(activity()), IonServiceManager.KEYS.PICKUP_COUNTRY_ID, shipperid, IonServiceManager.KEYS.DELIVERY_COUNTRY_ID, destinationid, IonServiceManager.KEYS.COUNTRY_TYPE, type, IonServiceManager.KEYS.PARCEL_TYPE, parceltype, IonServiceManager.KEYS.TOTAL_WEIGHT, totalweight, IonServiceManager.KEYS.SERVICE_ID, service_id, IonServiceManager.KEYS.CUST_ID, customerid, IonServiceManager.KEYS.PICKUP_TYPE, pickup, IonServiceManager.KEYS.COLLECTION_DATE, preferred_collection_date, IonServiceManager.KEYS.APPLY_INSURANCE, applyinsurance};
            callService(params);
        } else {
            SweetAlertUtil.showWarningWithCallback(activity(), getString(R.string.empty_val_error_msg), getString(R.string.ok), new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.cancel();
//
                }
            });
        }

    }

    //shipperid=1&destinationid=1&type=0&parceltype=11&totalweight=1&customerid=NJ29&pickup=1&preferred_collection_date=2019-01-18&applyinsurance=0.1&service_id=1 // 12jan
//shipperid=1&destinationid=1&parceltype=11&totalweight=5&customerid=NJ29&pickup=1&preferred_collection_date=2019-01-12&applyinsurance=0.5&service_id=1
    // parceltype    11 or 10
    private void validateService() throws IonServiceManager.InvalidParametersException {
        progressdialog = SweetAlertUtil.getProgressDialog(activity());
        progressdialog.show();
        String pickup_country_id = pickupParcelData.getPickup_address().getCountryid();
        String delivery_country_id = pickupParcelData.getDelivery_address().getCountryid();
        String shipperid = "";
        String destinationid = "";
        String type = "0";
        if (pickup_country_id.equals(delivery_country_id)) {
            shipperid = pickupParcelData.getPickup_address().getCityid();
            destinationid = pickupParcelData.getDelivery_address().getCityid();
            type = "0";
        } else {
            shipperid = pickupParcelData.getPickup_address().getCountryid();
            destinationid = pickupParcelData.getDelivery_address().getCountryid();
            type = "1";
        }

//        String shipperid = pickupParcelData.getPickup_address().getCountryid();
//        String destinationid = pickupParcelData.getDelivery_address().getCountryid();
        String parceltype = "11";
        String totalweight = pickupParcelData.getShipment().getChargeable_weight();
        String service_id = pickupParcelData.getShipment().getService().getService_id();
        String customerid = pickupParcelData.getCustomer_id();
        String pickup = "1"; // self 0 and collection 1
        String preferred_collection_date = "2019-01-18";

        String applyinsurance = "0";
        if (pickupParcelData.getShipment().isInsurancerequired()) {
            applyinsurance = pickupParcelData.getShipment().getInsuracevalue();
        } else {
            applyinsurance = "0";
        }


//        String shipperid = "1";
//        String destinationid = "1";
//        String parceltype = "11";
//        String totalweight = "1";
//        String service_id = "1";
//        String customerid = pickupParcelData.getCustomer_id();
//        String pickup = "1"; // self 0 and collection 1
//        String preferred_collection_date = "2019-01-18";
//        String applyinsurance = "1";

        String params[] = {IonServiceManager.KEYS.ANDROID_ID, DeviceInfoUtil.getAndroidID(activity()), IonServiceManager.KEYS.PICKUP_COUNTRY_ID, shipperid, IonServiceManager.KEYS.DELIVERY_COUNTRY_ID, destinationid, IonServiceManager.KEYS.COUNTRY_TYPE, type, IonServiceManager.KEYS.PARCEL_TYPE, parceltype, IonServiceManager.KEYS.TOTAL_WEIGHT, totalweight, IonServiceManager.KEYS.SERVICE_ID, service_id, IonServiceManager.KEYS.CUST_ID, customerid, IonServiceManager.KEYS.PICKUP_TYPE, pickup, IonServiceManager.KEYS.COLLECTION_DATE, preferred_collection_date, IonServiceManager.KEYS.APPLY_INSURANCE, applyinsurance};
        Log.i("params", params.toString());
        IonServiceManager serviceManager = getApp().ionserviceManager;
        serviceManager.postRequestToServerWOprogress(serviceManager.getAddress().ValidateService, new IonServiceManager.ResponseCallback(activity()) {
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
//                navigateToFragment(getActivity(), CollectionServiceEditFragment.newInstance(pickupParcelData));

            }

            @Override
            public void onResponse(boolean status, String message, Object result) throws JSONException, JsonSyntaxException {

                progressdialog.dismiss();
                JsonObject jsonObject = (JsonObject) result;
                JsonArray jsonArray = jsonObject.get(IonServiceManager.KEYS.SEVICE_ARRAY).getAsJsonArray();
//                jsonArray.remove(0);

                List<Services> servicesList = (ArrayList<Services>) new Gson().fromJson(jsonArray.toString(), new TypeToken<ArrayList<Services>>() {
                }.getType());
//                List<Services> servicesList = getServiceDataList();

                if (servicesList.size() > 1) {

                    int best = 0;

                    for (int i = 1; i < servicesList.size(); i++) {
                        String special_rate1 = servicesList.get(i).getSpecial_rate();
                        String special_rate = servicesList.get(best).getSpecial_rate();

                        float price1 = 0;
                        float price = 0;

                        if (special_rate1 != null && special_rate1.length() > 0) {
                            price1 = Float.parseFloat(special_rate1);

                        }

                        if (special_rate != null && special_rate.length() > 0) {
                            price = Float.parseFloat(special_rate);

                        }

                        if (price1 < price) {
                            best = i;
                        }


                    }


                    servicesList.get(best).setDefaultCheck(true);
                    pickupParcelData.setServicesList(servicesList);

                } else {
                    servicesList.get(0).setDefaultCheck(true);
                    pickupParcelData.setServicesList(servicesList);
                }


                navigateToFragment(getActivity(), CollectionServiceEditFragment.newInstance(pickupParcelData));

            }

        }, params);
    }

    //shipperid=1&destinationid=1&type=0&parceltype=11&totalweight=1&customerid=NJ29&pickup=1&preferred_collection_date=2019-01-18&applyinsurance=0.1&service_id=1 // 12jan
//shipperid=1&destinationid=1&parceltype=11&totalweight=5&customerid=NJ29&pickup=1&preferred_collection_date=2019-01-12&applyinsurance=0.5&service_id=1
    // parceltype    11 or 10
    private void callService(String params[]) throws IonServiceManager.InvalidParametersException {
        progressdialog = SweetAlertUtil.getProgressDialog(activity());
        progressdialog.show();


        Log.i("params", params.toString());
        IonServiceManager serviceManager = getApp().ionserviceManager;
        serviceManager.postRequestToServerWOprogress(serviceManager.getAddress().ValidateService, new IonServiceManager.ResponseCallback(activity()) {
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
//                navigateToFragment(getActivity(), CollectionServiceEditFragment.newInstance(pickupParcelData));

            }

            @Override
            public void onResponse(boolean status, String message, Object result) throws JSONException, JsonSyntaxException {

                progressdialog.dismiss();
                JsonObject jsonObject = (JsonObject) result;
                JsonArray jsonArray = jsonObject.get(IonServiceManager.KEYS.SEVICE_ARRAY).getAsJsonArray();
//                jsonArray.remove(0);

                List<Services> servicesList = (ArrayList<Services>) new Gson().fromJson(jsonArray.toString(), new TypeToken<ArrayList<Services>>() {
                }.getType());

                getBestService(servicesList);

                navigateToFragment(getActivity(), CollectionServiceEditFragment.newInstance(pickupParcelData));

            }

        }, params);
    }

    private void getBestService(List<Services> servicesList) {
        if (servicesList.size() > 1) {

            int best = 0;

            for (int i = 1; i < servicesList.size(); i++) {
                String special_rate1 = servicesList.get(i).getSpecial_rate();
                String special_rate = servicesList.get(best).getSpecial_rate();

                float price1 = 0;
                float price = 0;

                if (special_rate1 != null && special_rate1.length() > 0) {
                    price1 = Float.parseFloat(special_rate1);

                }

                if (special_rate != null && special_rate.length() > 0) {
                    price = Float.parseFloat(special_rate);

                }

                if (price1 < price) {
                    best = i;
                }

            }


            servicesList.get(best).setDefaultCheck(true);
            pickupParcelData.setServicesList(servicesList);

        } else {
            servicesList.get(0).setDefaultCheck(true);
            pickupParcelData.setServicesList(servicesList);
        }
    }

    private List<Services> getServiceDataList() {
        List<Services> servicesList = new ArrayList<>();
        Services services = new Services("1", "MEL Same Day Express", "", "2019-01-24", "end of the day", "$", "209", "$", "167", false);
        Services services1 = new Services("2", "MEL Overnight Express", "", "2019-01-29", "2.00pm to 6.00pm", "$", "609", "$", "407", false);
        Services services2 = new Services("3", "MEL Overnight Domestic Freight", "", "2019-02-22", "8.00am to 12.00pm", "$", "709", "$", "500", false);
        servicesList.add(services);
        servicesList.add(services1);
        servicesList.add(services2);
        return servicesList;
    }

}
