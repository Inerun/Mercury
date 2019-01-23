package com.inerun.courier.activity_driver;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.inerun.courier.CourierApplication;
import com.inerun.courier.R;
import com.inerun.courier.base.BaseActivity;
import com.inerun.courier.base.CheckConnectionUtil;
import com.inerun.courier.base.SweetAlertUtil;
import com.inerun.courier.constant.AppConstant;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.constant.Utils;
import com.inerun.courier.data.CustRequestParcelData;
import com.inerun.courier.service.DIRequestCreator;
import com.inerun.courier.sql.DIDbHelper;

import java.util.Map;

/**
 * Created by vineet on 25/05/18.
 */

public class ParcelRequestActivity extends BaseActivity implements View.OnClickListener {


    private static final String sTAG = "requestList";
    private EditText customerId_edt;
    private Button submit_btn;

    @Override
    public int customSetContentView() {
        return R.layout.activity_parcel_request;
    }

    @Override
    public void customOnCreate(Bundle savedInstanceState) {

        customerId_edt = findViewById(R.id.customerid_edt);
        submit_btn = findViewById(R.id.submit_btn);

        submit_btn.setOnClickListener(this);
        findViewById(R.id.gotologin).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gotologin:
                Utils.savePageType(this, UrlConstants.LOGIN);
                gotoLogin();
                break;
            case R.id.submit_btn:
                requestParcelList();
                break;
        }

    }

    private void requestParcelList() {
        String customerId = customerId_edt.getText() + "";

        performRequest(customerId);

    }

    private void performRequest(String customerId) {

        Map<String, String> params = DIRequestCreator.getInstance(this).getRequestParcelMapParams(customerId);

        CourierApplication.serviceManager().postRequest(UrlConstants.URL_REQUEST_PARCEL_LIST, params, null, response_listener, response_errorlistener, sTAG);

    }


    Response.Listener response_listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            Gson gson = new Gson();
            CustRequestParcelData custRequestParcelData = gson.fromJson(response, CustRequestParcelData.class);


        }
    };

    Response.ErrorListener response_errorlistener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
//            progress_layout.setVisibility(View.GONE);
//            progress.stop();
            Log.d("Response: ", error.toString());
            showSnackbar(error.toString());

        }
    };

    private void gotoLogin() {

        if (CheckConnectionUtil.checkMyConnectivity(this)) {

            if (hasPermissions(this, AppConstant.requiredPermissions())) {
                DIDbHelper.ensureDatabaseIsCorrect(this);
            }
            finish();
            gotoPodDeleteActivity();


        } else {
            if (Utils.isUserLoggedIn(this)) {
                if (CourierApplication.getUser().isDeliveryUser()) {
                    finish();
                    gotoHomeActivity();
                } else {
                    SweetAlertUtil.showAlertDialogFinishActivity(this, getString(R.string.activity_base_alert_message_unknown_host_exception));

                }
            } else {
                finish();
                gotoLoginActivity();

            }
        }
    }


    private void gotoPodDeleteActivity() {
        goToActivity(PodDeleteActivity.class);

    }

    private void gotoLoginActivity() {
        Intent intent = new Intent(this,
                LoginActivity.class);
        startActivity(intent);


    }

    private void gotoHomeActivity() {

        Intent intent = new Intent(this,
                DeliveryDashBoardActivity.class);
        startActivity(intent);

    }
}
