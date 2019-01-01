package com.inerun.courier.activity_driver;

import android.support.v4.app.Fragment;

import com.inerun.courier.R;

/**
 * Created by vineet on 2/20/2017.
 */

public class DeliveryReturnParcelActivity extends FragmentBaseActivity {

    @Override
    public Fragment getFragment() {
        return DeliveryReturnParcelFragment.newInstance();
    }


    @Override
    public int toolBarTitle() {
        return R.string.title_return_parcel;
    }
}
