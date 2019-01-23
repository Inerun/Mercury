package com.inerun.courier.activity_warehouse;

import android.os.Bundle;
import android.util.Log;

import com.inerun.courier.R;
import com.inerun.courier.base.BaseActivity;
import com.inerun.courier.gcm.NotiHelper;

/**
 * Created by vinay on 15/12/16.
 */

public class WhDashboardActivity extends BaseActivity {
    private boolean doubleBackToExitPressedOnce=false;

    @Override
    public int customSetContentView() {

        setWarehouse(true);
        return R.layout.activity_cordinator_container;
    }

    @Override
    public void customOnCreate(Bundle savedInstanceState) {







//        getSupportActionBar().setIcon(R.mipmap.toolbaricon);


        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                WhDashboardFragment.newInstance()).commit();
        if (NotiHelper.isNotificationIntent(getIntent())) {
            Log.i("WhDashboardActivity","isNotificationIntent");
           processNotiIntent(WhDashboardActivity.this);
        }

    }


    @Override
    public void onBackPressed() {
            handleFragmentBackPressed();

    }


    @Override
    public void whDeliveryUpdated() {
       showSnackbar(R.string.parcel_updated_msg);

        hideProgress();
    }
}
