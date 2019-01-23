package com.inerun.courier.activity_driver;

import android.os.Bundle;

import com.inerun.courier.data.ParcelListingData;
import com.inerun.courier.sql.DIDbHelper;

import java.util.ArrayList;

/**
 * Created by vinay on 16/01/17.
 */

public class DeliveryPendingParcelsFragment extends DeliveryAllParcelsFragment {

    public static DeliveryPendingParcelsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        DeliveryPendingParcelsFragment fragment = new DeliveryPendingParcelsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected ArrayList<ParcelListingData.ParcelData> getData() {
//        super.getData();
        return DIDbHelper.getPendingParcelInfoForListing(activity());

    }
}
