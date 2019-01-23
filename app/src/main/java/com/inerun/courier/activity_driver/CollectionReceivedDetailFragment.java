package com.inerun.courier.activity_driver;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.inerun.courier.R;
import com.inerun.courier.adapter.ShipmentAdapter;
import com.inerun.courier.adapter.StatusSpinnerAdapter;
import com.inerun.courier.base.BaseFragment;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.constant.Utils;
import com.inerun.courier.data.ParcelListingData;
import com.inerun.courier.data.ParcelStatus;
import com.inerun.courier.data.PickupParcelData;
import com.inerun.courier.data.StatusData;
import com.inerun.courier.helper.DIHelper;
import com.inerun.courier.helper.ExpandableHeightRecyclerView;
import com.inerun.courier.scanner.CameraTestActivity;
import com.inerun.courier.sql.DIDbHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by vineet on 9/28/2017.
 */

public class CollectionReceivedDetailFragment extends CollectionPendingDetailFragment {

    Context context;
    PickupParcelData pickupParcelData;

    public static CollectionReceivedDetailFragment newInstance(PickupParcelData pickupParcelData) {

        Bundle args = new Bundle();

        CollectionReceivedDetailFragment fragment = new CollectionReceivedDetailFragment();
        args.putSerializable(UrlConstants.KEY_DATA, pickupParcelData);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public PickupParcelData getData() {
        return (PickupParcelData) getArguments().getSerializable(UrlConstants.KEY_DATA);
    }
}
