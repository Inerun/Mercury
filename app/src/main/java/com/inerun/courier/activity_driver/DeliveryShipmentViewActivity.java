package com.inerun.courier.activity_driver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.inerun.courier.R;
import com.inerun.courier.constant.AppConstant;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.data.PickupParcelData;

/**
 * Created by vinay on 01/01/19.
 */

public class DeliveryShipmentViewActivity extends FragmentBaseActivity {
        PickupParcelData pickupParcelData;
    DeliveryShipmentViewFragment fragment;
        @Override
        public Fragment getFragment() {
            Bundle bundle = getBundleFromIntent(this);
            if(bundle != null){
                pickupParcelData = (PickupParcelData) bundle.getSerializable(UrlConstants.KEY_DATA);
            }
//        return DeliveryPickupDetailFragment.newInstance(pickupParcelData);
            fragment=DeliveryShipmentViewFragment.newInstance(pickupParcelData);
            return fragment;
        }

//
//    @Override
//    public Fragment getFragment() {
//        return DeliveryAddParcelFragment.newInstance();
//    }

    @Override
    public int toolBarTitle() {
        return R.string.edit;
    }



}
