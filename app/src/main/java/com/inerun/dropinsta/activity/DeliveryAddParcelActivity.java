package com.inerun.dropinsta.activity;

import android.support.v4.app.Fragment;

import com.inerun.dropinsta.R;

/**
 * Created by vineet on 9/27/2017.
 */

public class DeliveryAddParcelActivity extends FragmentBaseActivity {
    @Override
    public Fragment getFragment() {
        return DeliveryAddParcelFragment.newInstance();
    }

    @Override
    public int toolBarTitle() {
        return R.string.title_add_parcel;
    }


}
