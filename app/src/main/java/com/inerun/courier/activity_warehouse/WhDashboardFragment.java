package com.inerun.courier.activity_warehouse;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inerun.courier.R;
import com.inerun.courier.base.BaseFragment;
import com.inerun.courier.base.DeviceInfoUtil;
import com.inerun.courier.helper.DIHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinay on 15/12/16.
 */

public class WhDashboardFragment extends BaseFragment implements View.OnClickListener {

    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    Context context;
    public static Fragment newInstance() {
        WhDashboardFragment fragment = new WhDashboardFragment();
        return fragment;
    }


    @Override
    public int inflateView() {
        return R.layout.wh_parcel_home;
    }

    @Override
    public void customOnCreateView(View root, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context=activity();

//        getViewById(R.id.addparcel_layout).setOnClickListener(this);
        getViewById(R.id.addparcel_image_opacity).setOnClickListener(this);
//        getViewById(R.id.addparcel_image).setOnClickListener(this);
//        getViewById(R.id.searchparcel_layout).setOnClickListener(this);
        getViewById(R.id.searchparcel_image_opacity).setOnClickListener(this);

        getViewById(R.id.readyparcel_image_opacity).setOnClickListener(this);
//        getViewById(R.id.searchparcel_image).setOnClickListener(this);

        getViewById(R.id.stockparcel_image_opacity).setOnClickListener(this);



    }

    @Override
    protected String getAnalyticsName() {
        return null;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
//            case R.id.addparcel_layout:
//            navigateToFragment(context,WhAddParcelFragment.newInstance());
//
//                break;
//            case R.id.searchparcel_layout:
//                navigateToFragment(context,WhSearchParcelFragment.newInstance());
//
//                break;
            case R.id.addparcel_image_opacity:
                navigateToFragment(context,WhAddParcelFragment.newInstance());

                break;
            case R.id.searchparcel_image_opacity:
                navigateToFragment(context,WhSearchParcelFragment.newInstance());

                break;

            case R.id.readyparcel_image_opacity:
//                navigateToFragment(context, WhInvoiceFragment.newInstance());
                navigateToFragment(context, WhReadyForExecutiveFragment.newInstance());
//                navigateToFragment(context, WhReadyForExecutiveFragment_exp.newInstance());

                break;

            case R.id.stockparcel_image_opacity:
                navigateToFragment(context,WhPhysicalStockCheckFragment.newInstance());

                break;
//            case R.id.addparcel_image:
//                navigateToFragment(context,WhAddParcelFragment.newInstance());
//
//                break;
//            case R.id.searchparcel_image:
//                navigateToFragment(context,WhSearchParcelFragment.newInstance());
//
//                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (isMarshMallow() && checkAndRequestPermissions()) {
            // carry on the normal flow, as the case of  permissions  granted.
            DIHelper.copyDBToAnotherLocation();
        }
    }

    private boolean checkAndRequestPermissions() {
        int locationPermission = ContextCompat.checkSelfPermission(activity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionSendMessage = ContextCompat.checkSelfPermission(activity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            requestPermissions(listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    /**
     * function to Check Whether Device has Marshmallow or Above
     *
     * @return True if device has marshmallow or greater otherwise false
     */
    public boolean isMarshMallow() {
        return DeviceInfoUtil.getDeviceApiVersion(activity()) >= Build.VERSION_CODES.M;
    }
}
