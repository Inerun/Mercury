package com.inerun.courier.activity_customer_care;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inerun.courier.R;
import com.inerun.courier.base.BaseFragment;

/**
 * Created by vinay on 15/12/16.
 */

public class CustomerDashboardFragment extends BaseFragment implements View.OnClickListener {

    Context context;
    public static Fragment newInstance() {
        CustomerDashboardFragment fragment = new CustomerDashboardFragment();
        return fragment;
    }


    @Override
    public int inflateView() {
        return R.layout.customer_care_home;
    }

    @Override
    public void customOnCreateView(View root, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context=activity();

        getViewById(R.id.searchparcel_image_opacity).setOnClickListener(this);

        getViewById(R.id.readyparcel_image_opacity).setOnClickListener(this);

        getViewById(R.id.returnparcel_image_opacity).setOnClickListener(this);

        setToolBarTitle(R.string.app_name);


    }

    @Override
    protected String getAnalyticsName() {
        return null;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {


            case R.id.searchparcel_image_opacity:
                navigateToFragment(context,CustomerSearchParcelFragment.newInstance());

                break;

            case R.id.readyparcel_image_opacity:
//                navigateToFragment(context, CustomerReadyParcelFragment.newInstance());
                navigateToFragment(context, CustReadyInvoiceDeliveryFragment.newInstance());

                break;

            case R.id.returnparcel_image_opacity:
                navigateToFragment(context, CustReturnParcelFragment.newInstance());

                break;

        }

    }
}
