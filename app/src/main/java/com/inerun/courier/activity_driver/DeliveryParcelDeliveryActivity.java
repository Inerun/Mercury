package com.inerun.courier.activity_driver;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.inerun.courier.R;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.data.ParcelListingData;

import java.util.ArrayList;

/**
 * Created by vineet on 12/10/2016.
 */

public class DeliveryParcelDeliveryActivity extends FragmentBaseActivity {
    ArrayList<ParcelListingData.ParcelData> arrayList;



    @Override
    public int toolBarTitle() {
        return R.string.title_delivery_payment;
    }

    @Override
    public Fragment getFragment() {
        Bundle bundle = getBundleFromIntent(this);
        if (bundle != null && bundle.containsKey(UrlConstants.KEY_UPDATE_DATA)) {
            arrayList = (ArrayList<ParcelListingData.ParcelData>) bundle.getSerializable(UrlConstants.KEY_UPDATE_DATA);
            return DeliveryParcelDeliveryFragment.newInstance(arrayList);
        } else {
            showSnackbar("no data in bundle");
            return null;
        }


    }

    @Override
    public void response() {
        super.response();
        setResult(Activity.RESULT_OK);
        finish();
    }
}
