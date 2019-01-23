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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.inerun.courier.R;
import com.inerun.courier.adapter.CollectionAcceptListingAdapter;
import com.inerun.courier.adapter.CollectionWaitingListingAdapter;
import com.inerun.courier.base.BaseFragment;
import com.inerun.courier.base.DeviceInfoUtil;
import com.inerun.courier.base.SweetAlertUtil;
import com.inerun.courier.constant.AppConstant;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.data.PickupParcelData;
import com.inerun.courier.sql.DIDbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vineet on 9/27/2017.
 */

public class CollectionTransferWaitFragment extends BaseFragment {


    private RecyclerView recyclerView;
    private ArrayList<PickupParcelData> pickupParcelDataArrayList;
    private CollectionWaitingListingAdapter mAdapter;
    private Context context;
    private RelativeLayout root_layout;

    public static CollectionTransferWaitFragment newInstance() {

        Bundle args = new Bundle();

        CollectionTransferWaitFragment fragment = new CollectionTransferWaitFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int inflateView() {
        return R.layout.activity_collection_waiting_list;
    }

    @Override
    public void customOnCreateView(View root, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) throws Exception {
        context = activity();
        pickupParcelDataArrayList = new ArrayList<>();
        setShowBackArrow(true);
        initView();

    }

    private void initView() {
        recyclerView = (RecyclerView) getViewById(R.id.transfer_listview);
        root_layout = (RelativeLayout) getViewById(R.id.root_layout);
        root_layout.setVisibility(View.GONE);

    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
        setdata();
    }

    private void getData() {

        pickupParcelDataArrayList = DIDbHelper.getPickupInfoForListing(context);
//        pickupParcelDataArrayList = new ArrayList<>();

    }

    private void setdata() {

        mAdapter = new CollectionWaitingListingAdapter(activity());
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//            recyclerView.addItemDecoration(new DividerItemDecoration(getDrawable(this, R.drawable.parcel_item_thumbnail_divider)));
//        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(activity()));

        recyclerView.setAdapter(mAdapter);


        mAdapter.addDataListToList(pickupParcelDataArrayList);

        root_layout.setVisibility(View.VISIBLE);


        mAdapter.setOnItemClickListener(new CollectionWaitingListingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                switch (v.getId()) {
                    case R.id.call_action:


                        if (DeviceInfoUtil.hasPermissions(activity(), AppConstant.requiredPermissions())) {
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mAdapter.getDataList().get(position).getPhone()));
                            startActivity(intent);
                        } else {
                            showLongToast(R.string.permissions_missing);
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts("package", activity().getPackageName(), null));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }

                        break;
                    default:

                        Log.i("onItemClick", "OnClick");
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(UrlConstants.KEY_DATA, pickupParcelDataArrayList.get(position));

                        gotoActivity(CollectionTransferDetailActivity.class, bundle);
                        //slide from right to left
                        activity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                }
            }
        });


    }

    @Override
    protected String getAnalyticsName() {
        return null;
    }


}
