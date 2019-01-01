package com.inerun.courier.activity_driver;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.inerun.courier.CourierApplication;
import com.inerun.courier.R;
import com.inerun.courier.activity_auction.AuctionHomeActivity;
import com.inerun.courier.activity_customer_care.CustomerDashboardActivity;
import com.inerun.courier.activity_warehouse.WhDashboardActivity;
import com.inerun.courier.base.BaseActivity;
import com.inerun.courier.constant.AppConstant;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.constant.Utils;
import com.inerun.courier.data.LoginData;
import com.inerun.courier.helper.DIHelper;
import com.inerun.courier.service.DIRequestCreator;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by vineet on 5/26/2016.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String sTAG = "Login";
    RotateLoading progress;
    private EditText email_edt, pass_edt;
    private Button login_btn;

    Context context;

    private String email;

    private boolean mSignInClicked;
    private boolean mIntentInProgress;

    private boolean innerlaunch, forCart;
    private RelativeLayout progress_layout;
    private TextView gotoRequest_txt;


    @Override
    public int customSetContentView() {
        return R.layout.activity_login;
    }

    @Override
    public void customOnCreate(Bundle savedInstanceState) {
        context = LoginActivity.this;
        setSplash(true);

        checkForPermissions();
        initView();


    }

    private boolean checkForPermissions() {
        if (!hasPermissions(context, AppConstant.requiredPermissions())) {
            getMultiplePermissions(AppConstant.MULTIPLE_PERMISSION_REQUESTCODE, AppConstant.requiredPermissions());
            return false;
        } else {
            return true;
        }

    }


    private void initView() {
        progress = (RotateLoading) findViewById(R.id.progressBar);
        progress_layout = (RelativeLayout) findViewById(R.id.progressbar_layout);
        email_edt = (EditText) findViewById(R.id.login_email_edt);
        pass_edt = (EditText) findViewById(R.id.login_pass_edt);
        Typeface myTypeface = Typeface.createFromAsset(context
                .getAssets(), "fonts/robotolight.ttf");
        pass_edt.setTypeface(myTypeface);

        pass_edt.setTransformationMethod(new PasswordTransformationMethod());
        login_btn = (Button) findViewById(R.id.login_btn);
        gotoRequest_txt = findViewById(R.id.gotorequest);


        login_btn.setOnClickListener(this);
        gotoRequest_txt.setOnClickListener(this);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (checkForPermissions()) {
            initView();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                performLogin();
                break;
            case R.id.gotorequest:
                Utils.savePageType(this, UrlConstants.RREQUEST);
                goToParcel_Request();
                break;

        }

    }

    private void goToParcel_Request() {
        finish();
        goToActivity(ParcelRequestActivity.class);
    }


    private void performLogin() {

        String email = "" + email_edt.getText();
        String pass = "" + pass_edt.getText();

        if (DIHelper.validateLoginData(context, email, pass)) {
            login_btn.setClickable(false);
//            Utils.saveUserId(context, "1");
            performRequest(email, pass);

//            startActivity(new Intent(context, DeliveryDashBoardActivity.class));


        } else {
            login_btn.setClickable(true);
        }
    }


    private void performRequest(String email, String pass) {
        Log.i("LoginActivity","performRequest");
//        String params = TmRequestCreator.getInstance(this).getLoginParams(email, pass).toString();
//        //Get Request Volley
//        DropInsta.serviceManager().setProgress(progress).addRequest(UrlConstants.URL_LOGIN, responseListener, null, new TypeToken<LoginData>() {
//        }, sTAG, params);
//        //Post request volley
//        DropInsta.serviceManager().setProgress(progress).addPostRequest(UrlConstants.URL_LOGIN, responseListener, null, new TypeToken<LoginData>() {
//        }, sTAG, params);


        Map<String, String> params = DIRequestCreator.getInstance(this).getLoginMapParams(email, pass);
        if (progress != null && progress_layout != null) {
            progress_layout.setVisibility(View.VISIBLE);
            progress.start();
        }
        CourierApplication.serviceManager().postRequest(UrlConstants.URL_LOGIN, params, null, response_listener, response_errorlistener, sTAG);

    }

    Response.Listener response_listener = new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {

//            Log.d("Response: ", response.toString());

            try {
                if (progress != null&&progress_layout!=null) {
                    progress_layout.setVisibility(View.GONE);
                    progress.stop();
                }
                JSONObject jsonObject = new JSONObject(response);
//                Toast.makeText(LoginActivity.this, DIHelper.getMessage(jsonObject), Toast.LENGTH_LONG).show();
                showSnackbar(DIHelper.getMessage(jsonObject));
                if (DIHelper.getStatus(jsonObject)) {
                    Gson gson = new Gson();
                    LoginData loginData = gson.fromJson(response, LoginData.class);
                    Utils.saveLoginData(LoginActivity.this, loginData.getUserid(), loginData.getEmail(), loginData.getName(), Integer.parseInt(loginData.getUsertype()), loginData.getPhoneno(), loginData.getLocation());
                    if (loginData.isDeliveryUser()) {
                        CourierApplication.setUser(loginData);
                        startActivity(new Intent(context, DeliveryDashBoardActivity.class));
                        finish();
                    } else if (loginData.isWarehouseUser()) {
                        CourierApplication.setUser(loginData);
                        startActivity(new Intent(context, WhDashboardActivity.class));
                        finish();
                    }  else if (loginData.isCustomerCareUser()) {
                        CourierApplication.setUser(loginData);
                        startActivity(new Intent(context, CustomerDashboardActivity.class));
                        finish();
                    } else if (loginData.isCashierUser()) {
                        CourierApplication.setUser(loginData);
//                        startActivity(new Intent(context, AuctionSplashActivity.class));
                        startActivity(new Intent(context, AuctionHomeActivity.class));
                        finish();
                        Log.i("UserLogin","Cashier");
                    }else {
//                     showLongToast(context, R.string.error_invalid_email_field);
                        showSnackbar(R.string.error_invalid_usertype);
                    }


                } else {
                    login_btn.setClickable(true);
                }
            } catch (JSONException e) {
                e.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };


    Response.ErrorListener response_errorlistener = new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError response) {

            if (progress != null&&progress_layout!=null) {
                progress_layout.setVisibility(View.GONE);
                progress.stop();
            }
            Log.d("Response: ", response.toString());
            showSnackbar(response.toString());
            login_btn.setClickable(true);
        }
    };


//    Response.Listener responseListener = new Response.Listener<LoginData>() {
//        @Override
//        public void onResponse(LoginData dummyObject) {
//            // Deal with the DummyObject here
//            Log.i("stop", "" + System.currentTimeMillis());
//            onApiResponse(dummyObject);
//
//        }
//    };
//
//    Response.ErrorListener errorListener = new Response.ErrorListener() {
//        @Override
//        public void onErrorResponse(VolleyError error) {
//            // Deal with the error here
//            onApiError();
//        }
//    };
//
//    private void onApiResponse(final LoginData dummyObject) {
//        progress.setVisibility(View.GONE);
////        mContent.setVisibility(View.VISIBLE);
//        Toast.makeText(this,dummyObject.getMessage(),Toast.LENGTH_LONG).show();
//        Utils.saveLoginData(this, dummyObject.getUserid(),dummyObject.getEmail(),dummyObject.getName(),Integer.parseInt(dummyObject.getUsertype()),dummyObject.getPhoneno());
//
//    }
//
//    private void onApiError() {
//        progress.setVisibility(View.GONE);
////        mErrorView.setVisibility(View.VISIBLE);
//    }


    @Override
    public void gotPermission() {
        super.gotPermission();
        {
            initView();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("LoginActivity", "onDestroy Called");
        CourierApplication.serviceManager().cancelAllRequest(sTAG);

    }


}
