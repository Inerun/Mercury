package com.inerun.courier.activity_driver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import com.inerun.courier.R;
import com.inerun.courier.constant.AppConstant;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.data.PickupParcelData;

/**
 * Created by vineet on 9/28/2017.
 */

public class CollectionPendingDetailActivity extends FragmentBaseActivity {
    PickupParcelData pickupParcelData;
    private CollectionPendingDetailFragment fragment;

    @Override
    public Fragment getFragment() {
        Bundle bundle = getBundleFromIntent(CollectionPendingDetailActivity.this);
        if(bundle != null){
            pickupParcelData = (PickupParcelData) bundle.getSerializable(UrlConstants.KEY_DATA);
        }
//        return DeliveryPickupDetailFragment.newInstance(pickupParcelData);
         fragment = CollectionPendingDetailFragment.newInstance(pickupParcelData);
        return fragment;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu, this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pending_shipment, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_edit);
        if(pickupParcelData.isReceived()){
            item.setVisible(false);
        }else{
            item.setVisible(true);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {

            fragment.onEditClick();


        }
        return true;
    }


}
