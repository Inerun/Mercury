package com.inerun.courier.activity_driver;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.inerun.courier.R;
import com.inerun.courier.constant.UrlConstants;

/**
 * Created by vinay on 16/01/17.
 */

public class DeliveryPaymentsActivity extends FragmentBaseActivity {
    private int tab_selection = 1;
    @Override
    public Fragment getFragment() {
        Bundle bundle = getBundleFromIntent(this);
        if (bundle != null && bundle.containsKey(UrlConstants.KEY_TAB_SELECTION)) {
            tab_selection = bundle.getInt(UrlConstants.KEY_TAB_SELECTION);
//            return DeliveryPaymentsFragment.newInstance(tab_selection);
            return DeliveryPaymentsFragment_new.newInstance(tab_selection);
        } else{
//            return DeliveryPaymentsFragment.newInstance(tab_selection);
            return DeliveryPaymentsFragment_new.newInstance(tab_selection);
        }

    }

    @Override
    public int toolBarTitle() {
        return R.string.payment_collection;
    }
}
