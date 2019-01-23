package com.inerun.courier.activity_driver;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inerun.courier.R;
import com.inerun.courier.adapter.PickupParcelAdapter;
import com.inerun.courier.base.BaseFragment;
import com.inerun.courier.base.DeviceInfoUtil;
import com.inerun.courier.constant.AppConstant;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.data.PickupParcelData;
import com.inerun.courier.sql.DIDbHelper;

import java.util.ArrayList;

/**
 * Created by vineet on 9/27/2017.
 */

public class CollectionPendingListingFragment extends CollectionAllListingFragment {

    public static CollectionPendingListingFragment newInstance() {

        Bundle args = new Bundle();

        CollectionPendingListingFragment fragment = new CollectionPendingListingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ArrayList<PickupParcelData> getData() {
        return DIDbHelper.getCollectionPendingInfoForListing(activity());
    }
}
