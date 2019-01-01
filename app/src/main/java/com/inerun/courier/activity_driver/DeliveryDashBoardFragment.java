package com.inerun.courier.activity_driver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.inerun.courier.R;
import com.inerun.courier.adapter.StatusSpinnerAdapter;
import com.inerun.courier.base.BaseFragment;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.data.StatusData;
import com.inerun.courier.helper.DIHelper;

import java.util.ArrayList;

/**
 * Created by vineet on 12/10/2016.
 */

public class DeliveryDashBoardFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText delivery_comment;
    private Button update_btn;
    private Spinner statusSpinner;
    private StatusSpinnerAdapter statusSpinnerAdapter;
    ArrayList<StatusData> status_options_List;

    public static Fragment newInstance() {
        DeliveryDashBoardFragment fragment = new DeliveryDashBoardFragment();
        return fragment;
    }

    @Override
    public int inflateView() {
        return R.layout.fragment_parcel_update;
    }

    @Override
    public void customOnCreateView(View root, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setShowBackArrow(true);
        delivery_comment = (EditText) root.findViewById(R.id.delivery_comment);
        update_btn = (Button) root.findViewById(R.id.update_btn);
        update_btn.setOnClickListener(this);

        statusSpinner = (Spinner) root.findViewById(R.id.parcel_status);

        status_options_List = DIHelper.getStatusArrayList();

        statusSpinnerAdapter = new StatusSpinnerAdapter(activity(), android.R.layout.simple_spinner_item, status_options_List);
        statusSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusSpinnerAdapter);
        statusSpinner.setOnItemSelectedListener(this);
        statusSpinner.setSelection(status_options_List.size() - 1);


    }

    @Override
    protected String getAnalyticsName() {
        return null;
    }


    @Override
    public void onClick(View view) {
        String status = ((StatusData) statusSpinner.getSelectedItem()).getValue();
        String comment = ""+ delivery_comment.getText();

        if(DIHelper.validateComment(activity(), comment)){
            Intent intent = new Intent();
            intent.putExtra(UrlConstants.KEY_COMMENT, comment);
            activity().setResult(ParcelDetailActivity.RESULT_OK, intent);
            activity().finish();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        delivery_comment.setText(status_options_List.get(i).getLable());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
