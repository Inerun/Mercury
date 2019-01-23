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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.inerun.courier.R;
import com.inerun.courier.adapter.CollectionAcceptListingAdapter;
import com.inerun.courier.adapter.CollectionTransferListingAdapter;
import com.inerun.courier.base.BaseFragment;
import com.inerun.courier.base.DeviceInfoUtil;
import com.inerun.courier.base.SweetAlertUtil;
import com.inerun.courier.constant.AppConstant;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.data.DriverData;
import com.inerun.courier.data.PickupParcelData;
import com.inerun.courier.helper.DIHelper;
import com.inerun.courier.sql.DIDbHelper;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vineet on 9/27/2017.
 */

public class CollectionAcceptFragment extends BaseFragment implements View.OnClickListener {


    private RecyclerView recyclerView;
    private ArrayList<PickupParcelData> pickupParcelDataArrayList;
    private CollectionAcceptListingAdapter mAdapter;
    private Context context;
    private Button submit_btn;
    private LinearLayout submit_layout;
    private RelativeLayout root_layout;

    public static CollectionAcceptFragment newInstance() {

        Bundle args = new Bundle();

        CollectionAcceptFragment fragment = new CollectionAcceptFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int inflateView() {
        return R.layout.activity_collection_accept_list;
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
        submit_btn = (Button) getViewById(R.id.submit_btn);
        submit_layout = (LinearLayout) getViewById(R.id.submit_layout);
        root_layout = (RelativeLayout) getViewById(R.id.root_layout);
        root_layout.setVisibility(View.GONE);

        submit_btn.setOnClickListener(this);
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

        mAdapter = new CollectionAcceptListingAdapter(activity());
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//            recyclerView.addItemDecoration(new DividerItemDecoration(getDrawable(this, R.drawable.parcel_item_thumbnail_divider)));
//        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(activity()));

        recyclerView.setAdapter(mAdapter);

        if(pickupParcelDataArrayList != null && pickupParcelDataArrayList.size() > 0){
            mAdapter.addDataListToList(pickupParcelDataArrayList);
            submit_layout.setVisibility(View.VISIBLE);
        }else{
            mAdapter.addDataListToList(null);
            submit_layout.setVisibility(View.GONE);
        }
        root_layout.setVisibility(View.VISIBLE);



        mAdapter.setOnItemClickListener(new CollectionAcceptListingAdapter.OnItemClickListener() {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit_btn:
                validateData();
                break;
        }
    }

    private void validateData() {
        List<PickupParcelData> parcelDataArrayList = new ArrayList<>();

        for (PickupParcelData data: mAdapter.getDataList()){
            if(data.isSelected()){
                parcelDataArrayList.add(data);
            }
        }
        if(!(parcelDataArrayList != null && parcelDataArrayList.size() > 0)){
            SweetAlertUtil.showSweetMessageLongToast(activity(),getString(R.string.transfer_empty_parcel_msg));
        }else{
            SweetAlertUtil.showSweetMessageLongToast(activity(),"WIP");
        }
    }

}
