package com.inerun.courier.activity_driver;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.inerun.courier.CourierApplication;
import com.inerun.courier.R;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.constant.Utils;
import com.inerun.courier.helper.DIHelper;
import com.inerun.courier.service.DIRequestCreator;
import com.vishalsojitra.easylocation.EasyLocationActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by vinay on 14/11/18.
 */

public class LocationSenderActivity extends EasyLocationActivity {


    private static final String sTAG = "requestLocation";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_location_request);



        requestLocationUpdate();
    }

    public void requestLocationUpdate() {
        if (getApp() != null) {
            requestSingleLocationFix(getApp().easyLocationRequest);
        }

    }


    @Override
    public void onLocationPermissionGranted() {
        Log.e("locationPermsnGranted", "abc");
//        updateErrorToServer("locationProvidrEnabled");
    }

    @Override
    public void onLocationPermissionDenied() {
        Log.e("locationPermsnDenied", "abc");
//        updateErrorToServer("locationProvidrEnabled");
    }

    @Override
    public void onLocationReceived(Location location) {
        Log.i("locationReceived", "abc" + location.getLongitude() + "," + location.getLatitude());
        updateLocationToServer(location);
    }

    @Override
    public void onLocationProviderEnabled() {
        Log.e("locationProvidrEnabled", "abc");
//        updateErrorToServer("locationProvidrEnabled");
    }

    @Override
    public void onLocationProviderDisabled() {
        Log.e("locationProvidrDsabled", "abc");
//        updateErrorToServer("locationProvidrEnabled");
    }


    public void updateErrorToServer(String message) {
        Map<String, String> params = DIRequestCreator.getInstance(this).getLocationErrorParams(Utils.getUserId(this), message);

        CourierApplication.serviceManager().postRequest(UrlConstants.URL_DRIVER_LOCATION_UPDATE, params, null, response_listener, response_errorlistener, sTAG);

    }

    public void updateLocationToServer(Location location) {
        Map<String, String> params = DIRequestCreator.getInstance(this).getLocationRequestParams(Utils.getUserId(this), location);

        CourierApplication.serviceManager().postRequest(UrlConstants.URL_DRIVER_LOCATION_UPDATE, params, null, response_listener, response_errorlistener, sTAG);

    }


    public CourierApplication getApp() {
        return (CourierApplication) getApplication();
    }



    Response.Listener response_listener = new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {

            Log.d("Response: ", response.toString());
            try {
                JSONObject jsonObject = new JSONObject(response);
                //String transdata = "[{\"barcode\":\"030300000012\",\"collectedby\":\"Vineet\",\"currency\":\"$\",\"iscard\":false,\"totalamount\":\"116.0\",\"transcid\":\"\",\"transTimeStamp\":\"2017-01-25 04:13:24\",\"transtype\":\"1\"}]";
                //jsonObject.put("transdata", new JSONArray(transdata));
                //response = jsonObject.toString();

                //Toast.makeText(BaseActivity.this, DIHelper.getMessage(jsonObject), Toast.LENGTH_LONG).show();
//                showSnackbar(DIHelper.getMessage(jsonObject));

                if (DIHelper.getStatus(jsonObject)) {


                } else {

//                    login_btn.setClickable(true);

                }

            } catch (JSONException e) {
                e.printStackTrace();

//                showSnackbar(ExceptionMessages.getMessageFromException(BaseActivity.this, -1, e));

            } catch (Exception e) {
                e.printStackTrace();

//                showSnackbar(ExceptionMessages.getMessageFromException(BaseActivity.this, -1, e));
            }

//            hideProgress();
        }
    };

    public void response() {

    }


    Response.ErrorListener response_errorlistener = new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("onErrorResponse: ", error.toString());
//            hideProgress();
//
//            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                showSnackbar(R.string.exception_alert_message_timeout_exception);
//            } else if (error instanceof AuthFailureError) {
//                //TODO
//                showSnackbar("AuthFailure Error");
//            } else if (error instanceof ServerError) {
//                //TODO
//                showSnackbar(R.string.exception_alert_message_internal_server_error);
//            } else if (error instanceof NetworkError) {
//                //TODO
//                showSnackbar(R.string.exception_alert_message_network);
//            } else if (error instanceof ParseError) {
//                //TODO
//                showSnackbar(R.string.exception_alert_message_parsing_exception);
//            } else {
//                showSnackbar(R.string.exception_alert_message_error);
//            }
        }
    };
}
