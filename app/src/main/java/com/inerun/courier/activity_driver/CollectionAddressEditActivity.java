package com.inerun.courier.activity_driver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.inerun.courier.R;
import com.inerun.courier.constant.AppConstant;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.data.PickupParcelData;

/**
 * Created by vineet on 01/01/19.
 */

public class CollectionAddressEditActivity extends FragmentBaseActivity implements View.OnClickListener {

    PickupParcelData pickupParcelData;
    public static Button PickupAddressBtn, DeliveryAddressBtn, ShipmentDetailBtn, ServiceBtn, PaymentBtn;
    android.support.v4.app.FragmentManager manager;


    private CollectionAddressEditPickupFragment pickup_apddress_fragment;
    private CollectionAddressEditDeliveryFragment delivery_address_fragment;
    private DeliveryShipmentViewFragment shipment_detail_fragment;
    private CollectionServiceEditFragment service_fragment;
    private CollectionAddressEditDeliveryFragment payment_Fragment;
    private Fragment confirmFragment;

    @Override
    public int customSetContentView() {
        return R.layout.activity_collection_edit_container;
    }


    @Override
    protected void initFragmewnt() {
        initViews();
        Bundle bundle = getBundleFromIntent(this);
        pickupParcelData = (PickupParcelData) bundle.getSerializable("DATA");
        navigateToFragment(this, CollectionAddressEditPickupFragment.newInstance(pickupParcelData));
    }

    @Override
    public Fragment getFragment() {
//        boolean isPickup = getIntent().getBooleanExtra("ISPICKUP",false);

//        Bundle bundle = getBundleFromIntent(this);
//        pickupParcelData = (PickupParcelData) bundle.getSerializable("DATA");
//        initViews();
//        navigateToFragment(this, CollectionAddressEditPickupFragment.newInstance(pickupParcelData));
        return null;
    }

//    @Override
//    public int toolBarTitle() {
////        return isPickUp ? R.string.pickup_address_edit_label : R.string.delivery_address_edit_label;
//        return R.string.pickup_address_edit_label;
//    }

    private void initViews() {
        PickupAddressBtn = (Button) findViewById(R.id.checkout_billing);
        DeliveryAddressBtn = (Button) findViewById(R.id.checkout_shipping);
        ShipmentDetailBtn = (Button) findViewById(R.id.checkout_payment);
        ServiceBtn = (Button) findViewById(R.id.checkout_review);
        PaymentBtn = (Button) findViewById(R.id.checkout_confirm);

        PickupAddressBtn.setOnClickListener(this);
        DeliveryAddressBtn.setOnClickListener(this);
        ShipmentDetailBtn.setOnClickListener(this);
        ServiceBtn.setOnClickListener(this);
        PaymentBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        FragmentActivity activity = (FragmentActivity) CollectionAddressEditActivity.this;
//        FragmentActivity activity = (FragmentActivity) v.getContext();
        manager = activity.getSupportFragmentManager();

        // TODO Auto-generated method stub
        switch (v.getId()) {

            case R.id.checkout_billing:

                if (pickup_apddress_fragment == null) {
                    pickup_apddress_fragment = CollectionAddressEditPickupFragment.newInstance(pickupParcelData);
                }
                navigateToFragment(activity, pickup_apddress_fragment);
                break;
            case R.id.checkout_shipping:
                if (delivery_address_fragment == null) {
                    delivery_address_fragment = CollectionAddressEditDeliveryFragment.newInstance(pickupParcelData);
                }

                navigateToFragment(activity, delivery_address_fragment);

                break;
            case R.id.checkout_payment:
                if (shipment_detail_fragment == null) {
                    shipment_detail_fragment = DeliveryShipmentViewFragment.newInstance(pickupParcelData);
                }
                navigateToFragment(activity, shipment_detail_fragment);
                break;
            case R.id.checkout_review:
                if (service_fragment == null) {
                    service_fragment = CollectionServiceEditFragment.newInstance(pickupParcelData);
                }
//                manager.beginTransaction()
//                        .replace(R.id.container, service_fragment).commit();
                navigateToFragment(activity, service_fragment);
                break;

            case R.id.checkout_confirm:
//                manager.beginTransaction()
//                        .replace(R.id.container, CollectionAddressEditDeliveryFragment.newInstance(pickupParcelData))
//                        .commit();

                if (payment_Fragment == null) {
                    payment_Fragment = CollectionAddressEditDeliveryFragment.newInstance(pickupParcelData);
                }

                navigateToFragment(activity, payment_Fragment);

                break;

            default:
                break;
        }
    }

    public void clickingOnTab(int priority) {
        switch (priority) {
            case 0:
                PickupAddressBtn.setOnClickListener(null);
                DeliveryAddressBtn.setOnClickListener(null);
                ShipmentDetailBtn.setOnClickListener(null);
                ServiceBtn.setOnClickListener(null);
                PaymentBtn.setOnClickListener(null);
                //
                PickupAddressBtn
                        .setBackgroundResource(R.color.primary);

                DeliveryAddressBtn
                        .setBackgroundResource(R.color.white);
                ShipmentDetailBtn
                        .setBackgroundResource(R.color.white);
                ServiceBtn
                        .setBackgroundResource(R.color.white);
                PaymentBtn
                        .setBackgroundResource(R.color.white);
                PickupAddressBtn.setTextColor(getResources().getColor(R.color.white));
                DeliveryAddressBtn.setTextColor(getResources().getColor(R.color.black));
                ShipmentDetailBtn.setTextColor(getResources().getColor(R.color.black));
                ServiceBtn.setTextColor(getResources().getColor(R.color.black));
                PaymentBtn.setTextColor(getResources().getColor(R.color.black));

                break;
            case 1:
                PickupAddressBtn.setOnClickListener(CollectionAddressEditActivity.this);
                DeliveryAddressBtn.setOnClickListener(null);
                ShipmentDetailBtn.setOnClickListener(null);
                ServiceBtn.setOnClickListener(null);
                PaymentBtn.setOnClickListener(null);
                //
                PickupAddressBtn
                        .setBackgroundResource(R.color.primary);
                DeliveryAddressBtn
                        .setBackgroundResource(R.color.primary);
                ShipmentDetailBtn
                        .setBackgroundResource(R.color.white);
                ServiceBtn
                        .setBackgroundResource(R.color.white);
                PaymentBtn
                        .setBackgroundResource(R.color.white);
                PickupAddressBtn.setTextColor(getResources().getColor(R.color.white));
                DeliveryAddressBtn.setTextColor(getResources().getColor(R.color.white));
                ShipmentDetailBtn.setTextColor(getResources().getColor(R.color.black));
                ServiceBtn.setTextColor(getResources().getColor(R.color.black));
                PaymentBtn.setTextColor(getResources().getColor(R.color.black));
                break;
            case 2:
                PickupAddressBtn.setOnClickListener(CollectionAddressEditActivity.this);
                DeliveryAddressBtn.setOnClickListener(CollectionAddressEditActivity.this);
                ShipmentDetailBtn.setOnClickListener(null);
                ServiceBtn.setOnClickListener(null);
                PaymentBtn.setOnClickListener(null);
                //
                PickupAddressBtn
                        .setBackgroundResource(R.color.primary);
                DeliveryAddressBtn
                        .setBackgroundResource(R.color.primary);
                ShipmentDetailBtn
                        .setBackgroundResource(R.color.primary);
                ServiceBtn
                        .setBackgroundResource(R.color.white);
                PaymentBtn
                        .setBackgroundResource(R.color.white);
                PickupAddressBtn.setTextColor(getResources().getColor(R.color.white));
                DeliveryAddressBtn.setTextColor(getResources().getColor(R.color.white));
                ShipmentDetailBtn.setTextColor(getResources().getColor(R.color.white));
                ServiceBtn.setTextColor(getResources().getColor(R.color.black));
                PaymentBtn.setTextColor(getResources().getColor(R.color.black));
                break;
            case 3:
                PickupAddressBtn.setOnClickListener(CollectionAddressEditActivity.this);
                DeliveryAddressBtn.setOnClickListener(CollectionAddressEditActivity.this);
                ShipmentDetailBtn.setOnClickListener(CollectionAddressEditActivity.this);
                ServiceBtn.setOnClickListener(null);
                PaymentBtn.setOnClickListener(null);
                //
                PickupAddressBtn
                        .setBackgroundResource(R.color.primary);
                DeliveryAddressBtn
                        .setBackgroundResource(R.color.primary);
                ShipmentDetailBtn
                        .setBackgroundResource(R.color.primary);
                ServiceBtn.setBackgroundResource(R.color.primary);
                PaymentBtn
                        .setBackgroundResource(R.color.white);
                PickupAddressBtn.setTextColor(getResources().getColor(R.color.white));
                DeliveryAddressBtn.setTextColor(getResources().getColor(R.color.white));
                ShipmentDetailBtn.setTextColor(getResources().getColor(R.color.white));
                ServiceBtn.setTextColor(getResources().getColor(R.color.white));
                PaymentBtn.setTextColor(getResources().getColor(R.color.black));
                break;
            case 4:
                // PickupAddressBtn.setOnClickListener(clickListenerFragmentChanger);
                // DeliveryAddressBtn.setOnClickListener(clickListenerFragmentChanger);
                // ShipmentDetailBtn.setOnClickListener(clickListenerFragmentChanger);
                // ServiceBtn.setOnClickListener(clickListenerFragmentChanger);
                // PaymentBtn.setOnClickListener(clickListenerFragmentChanger);
                PickupAddressBtn.setOnClickListener(null);
                DeliveryAddressBtn.setOnClickListener(null);
                ShipmentDetailBtn.setOnClickListener(null);
                ServiceBtn.setOnClickListener(null);
                PaymentBtn.setOnClickListener(null);
                //
                PickupAddressBtn
                        .setBackgroundResource(R.color.primary);
                DeliveryAddressBtn
                        .setBackgroundResource(R.color.primary);
                ShipmentDetailBtn
                        .setBackgroundResource(R.color.primary);
                ServiceBtn.setBackgroundResource(R.color.primary);
                PaymentBtn
                        .setBackgroundResource(R.color.primary);
                PickupAddressBtn.setTextColor(getResources().getColor(R.color.white));
                DeliveryAddressBtn.setTextColor(getResources().getColor(R.color.white));
                ShipmentDetailBtn.setTextColor(getResources().getColor(R.color.white));
                ServiceBtn.setTextColor(getResources().getColor(R.color.white));
                PaymentBtn.setTextColor(getResources().getColor(R.color.white));
                break;
            default:
                break;
        }

    }

    @Override
    public void onBackPressed() {
        handleBackStack();
    }

//    @Override
//    public void handleActionBarBackPress() {
//        handleBackStack();
//    }


    @Override
    public void handleFragmentBackPressed() {
        handleBackStack();
    }

    public void handleBackStack() {
//        if(isConfirm)
//        {
//            Intent intent= new Intent(this, MainActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
//
//        }else {
        FragmentManager fm = getSupportFragmentManager();
        Log.i("handleBackStack", "collectionAddressEditActivity" + +fm.getBackStackEntryCount());
        if (fm.getBackStackEntryCount() > 1) {
            Log.i("CheckoutActivity", "popping backstack" + +fm.getBackStackEntryCount());
            fm.popBackStack();
        } else {
            fm.popBackStack();
            Log.i("CheckoutActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
//        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            Bundle bundle = data.getBundleExtra(AppConstant.Keys.DATA);
            PickupParcelData pickupParcelData1 = (PickupParcelData) bundle.getSerializable(UrlConstants.KEY_DATA);
            pickupParcelData1.getShipment().reCalculateData();
            shipment_detail_fragment.refreshAdapter(pickupParcelData1);

        }
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//
//            Bundle bundle = data.getBundleExtra(AppConstant.Keys.DATA);
//            PickupParcelData pickupParcelData1 = (PickupParcelData) bundle.getSerializable(UrlConstants.KEY_DATA);
//            shipment_detail_fragment.refreshAdapter(pickupParcelData1);
//
//        }
//    }

    public void setShipmentFragment(DeliveryShipmentViewFragment shipmentviewfragment) {
        shipment_detail_fragment=shipmentviewfragment;
    }


}
