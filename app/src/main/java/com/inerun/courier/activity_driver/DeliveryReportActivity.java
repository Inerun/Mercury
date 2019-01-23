package com.inerun.courier.activity_driver;

import android.support.v4.app.Fragment;

import com.inerun.courier.R;


/**
 * Created by vineet on 9/27/2017.
 */

public class DeliveryReportActivity extends FragmentBaseActivity {
    @Override
    public Fragment getFragment() {
        return DeliveryReportFragment.newInstance();
    }

    @Override
    public int toolBarTitle() {
        return R.string.title_driver_report;
    }


}
