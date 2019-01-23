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
import com.inerun.courier.data.ParcelListingData;
import com.inerun.courier.data.PickupParcelData;
import com.inerun.courier.sql.DIDbHelper;

import java.util.ArrayList;

/**
 * Created by vineet on 9/27/2017.
 */

public class CollectionAllListingFragment extends BaseFragment {


    private RecyclerView recyclerView;
    private ArrayList<PickupParcelData> pickupParcelDataArrayList;
    private PickupParcelAdapter mAdapter;
    private Context context;

    public static CollectionAllListingFragment newInstance() {

        Bundle args = new Bundle();

        CollectionAllListingFragment fragment = new CollectionAllListingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int inflateView() {
        return R.layout.activity_delivery_pickup_parcel;
    }

    @Override
    public void customOnCreateView(View root, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) throws Exception {
        context = activity();
        pickupParcelDataArrayList = new ArrayList<>();
        setShowBackArrow(true);
        initView();
        initAdapter();

    }

    private void initView() {
        recyclerView = (RecyclerView) getViewById(R.id.pickup_parcel_listview);
    }

    private void initAdapter() {
        mAdapter = new PickupParcelAdapter(activity());
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//            recyclerView.addItemDecoration(new DividerItemDecoration(getDrawable(this, R.drawable.parcel_item_thumbnail_divider)));
//        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(activity()));

        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new PickupParcelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                switch (v.getId()) {
                    case R.id.call_action:

                        if (DeviceInfoUtil.hasPermissions(activity(), AppConstant.requiredPermissions())) {
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mAdapter.getParcelDataList().get(position).getPickup_address().getPhone()));
                            startActivity(intent);
                        } else {
                            showLongToast(R.string.permissions_missing); 
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", activity().getPackageName(), null));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }

                        break;
                    default:

                        Log.i("onItemClick", "OnClick");
                        Bundle bundle = new Bundle();
//                        bundle.putSerializable(UrlConstants.KEY_DATA, pickupParcelDataArrayList.get(position));
                        bundle.putSerializable(UrlConstants.KEY_DATA, mAdapter.getParcelDataList().get(position));

                        if(mAdapter.getParcelDataList().get(position).isReceived()){
                            gotoActivity(CollectionReceivedDetailActivity.class, bundle);
                        }else{
                            gotoActivity(CollectionPendingDetailActivity.class, bundle);
                        }

                        //slide from right to left
                        activity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        pickupParcelDataArrayList = getData();
        setdata();
    }

    public ArrayList<PickupParcelData> getData() {

        return DIDbHelper.getCollectionAllInfoForListing(context);

    }

    private void setdata() {
        mAdapter.addDataListToList(pickupParcelDataArrayList);


    }

    @Override
    protected String getAnalyticsName() {
        return null;
    }
}
