package com.inerun.courier.activity_driver;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.inerun.courier.R;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.data.PickupParcelData;

/**
 * Created by vinay on 01/01/19.
 */

public class DeliveryShipmentEditActivity extends FragmentBaseActivity {
    PickupParcelData pickupParcelData;
    int requestcode,position;

    @Override
    public Fragment getFragment() {
        Bundle bundle = getBundleFromIntent(this);
        if (bundle != null) {
            pickupParcelData = (PickupParcelData) bundle.getSerializable(UrlConstants.KEY_DATA);
         requestcode=   getIntent().getIntExtra(UrlConstants.KEY_REQUEST_CODE, -1);
         position=   getIntent().getIntExtra(UrlConstants.KEY_POSITION, -1);
        }

//        return DeliveryPickupDetailFragment.newInstance(pickupParcelData);
        return DeliveryShipmentEditFragment.newInstance(requestcode,position,pickupParcelData);
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
