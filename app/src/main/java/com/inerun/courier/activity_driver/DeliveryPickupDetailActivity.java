package com.inerun.courier.activity_driver;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.data.PickupParcelData;

/**
 * Created by vineet on 9/28/2017.
 */

public class DeliveryPickupDetailActivity extends FragmentBaseActivity {
    PickupParcelData pickupParcelData;

    @Override
    public Fragment getFragment() {
        Bundle bundle = getBundleFromIntent(DeliveryPickupDetailActivity.this);
        if(bundle != null){
            pickupParcelData = (PickupParcelData) bundle.getSerializable(UrlConstants.KEY_DATA);
        }
        return DeliveryPickupDetailFragment.newInstance(pickupParcelData);
    }


}
