package com.inerun.courier.activity_driver;

import android.os.Bundle;

import com.inerun.courier.data.PickupParcelData;
import com.inerun.courier.sql.DIDbHelper;

import java.util.ArrayList;

/**
 * Created by vineet on 9/27/2017.
 */

public class CollectionReceivedListingFragment extends CollectionAllListingFragment {

    public static CollectionReceivedListingFragment newInstance() {

        Bundle args = new Bundle();

        CollectionReceivedListingFragment fragment = new CollectionReceivedListingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ArrayList<PickupParcelData> getData() {
        return DIDbHelper.getCollectionReceivedInfoForListing(activity());
    }
}
