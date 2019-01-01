package com.inerun.courier.activity_driver;

import android.os.Bundle;

import com.inerun.courier.R;
import com.inerun.courier.base.BaseActivity;

/**
 * Created by vineet on 12/10/2016.
 */

public class ParcelUpdateActivity extends BaseActivity {
    @Override
    public int customSetContentView() {
        return R.layout.activity_cordinator_container;
    }

    @Override
    public void customOnCreate(Bundle savedInstanceState) {

            getSupportFragmentManager().beginTransaction().replace(R.id.container,
                    ParcelUpdateFragment.newInstance()).commit();


    }


}
