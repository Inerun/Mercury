package com.inerun.courier.activity_driver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.inerun.courier.CourierApplication;
import com.inerun.courier.R;
import com.inerun.courier.base.BaseActivity;
import com.inerun.courier.base.SweetAlertUtil;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.service.DIRequestCreator;
import com.victor.loading.rotate.RotateLoading;

import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by vinay on 3/9/2016.
 */
public class ExceptionHandlerActivity extends BaseActivity {
    private static final int EXCEPTION_DIALOG = 111;
    private static final String EXCEPTION_SERVICE = "EXCEPTION_SERVICE";
    private TextView txt;
    String logs = "";
    Context context;
    private RotateLoading progress;
    private String exception;
//    public ServiceClient client;

    @Override
    public int customSetContentView() {
        return R.layout.activity_exception;
    }

    @Override
    public void customOnCreate(Bundle savedInstanceState) {
        Log.i("ExceptionHandlerAc", "onCreate Called");
        context = ExceptionHandlerActivity.this;
        setShow_home(false);

        txt = (TextView) findViewById(R.id.exception);
        if (getIntent() != null && getIntent().hasExtra(UrlConstants.KEY_EXCEPTION)) {
            exception = getIntent().getStringExtra(UrlConstants.KEY_EXCEPTION);
            txt.setText(getString(R.string.foodmenu_error_unknown_error));
            Log.i("Exception", "" + exception);
            prepareLogs(exception);
        }

    }

    private void prepareLogs(String exception) {


        SweetAlertDialog sweetAlertDialog = SweetAlertUtil.showDialogwithNeutralButton(context, context.getString(R.string.exception), context.getString(R.string.exceptionhandler_message), context.getString(R.string.ok), listener);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.show();

    }


    SweetAlertDialog.OnSweetClickListener listener = new SweetAlertDialog.OnSweetClickListener() {


        @Override
        public void onClick(SweetAlertDialog sweetAlertDialog) {
            Map<String, String> params = DIRequestCreator.getInstance(context).getExceptionLogs(exception);
            CourierApplication.serviceManager().postRequest(UrlConstants.URL_EXCEPTION, params, progress, response_listener, response_errorlistener, EXCEPTION_SERVICE);
        }


    };
    private Response.Listener<String> response_listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            onExceptionUpdated();
        }
    };

    private void onExceptionUpdated() {
        Intent intent = new Intent(context, SplashActivity.class);

        startActivity(intent);
        ExceptionHandlerActivity.this.finish();
        System.exit(1);
    }


    private Response.ErrorListener response_errorlistener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            onExceptionUpdated();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (client != null) {
//            client.cancelService();
//        }
        CourierApplication.serviceManager().cancelAllRequest(EXCEPTION_SERVICE);
    }
}


