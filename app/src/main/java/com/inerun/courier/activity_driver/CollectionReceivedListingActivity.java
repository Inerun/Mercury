package com.inerun.courier.activity_driver;

import android.support.v4.app.Fragment;

import com.inerun.courier.R;

/**
 * Created by vineet on 9/27/2017.
 */

public class CollectionReceivedListingActivity extends FragmentBaseActivity {
    @Override
    public Fragment getFragment() {
        return CollectionReceivedListingFragment.newInstance();
    }

    @Override
    public int toolBarTitle() {
        return R.string.title_received_pickup_parcel;
    }
}
