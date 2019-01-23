package com.inerun.courier.activity_auction;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteException;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.inerun.courier.R;
import com.inerun.courier.base.SweetAlertUtil;
import com.inerun.courier.helper.DIHelper;
import com.inerun.courier.helper.DeviceInfoUtil;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.koushikdutta.ion.builder.Builders;

import org.json.JSONException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by vinay on 10/10/17.
 */

public class IonServiceManager {

    private static final int TIMEOUT_TIME =60 * 1000 ;
    static boolean enableLogs = true;
    static String TAG = "ServiceManager";

    public static final int ORDER_PATIENT = 0;
    public static final int ORDER_STATION = 1;
    public static final int ORDER_TEXT = 2;
    public static final int ORDER_BUFFET = 3;


    Builder builder;

    Context context;
    FragmentActivity activityContext;
    Address address;
    static boolean progressenabled = true;


    RequestParameters requestParameters;
    //    private static String BaseUrl = "http://app1.elbtv.de/fcs/api/";
//    private static String BaseUrl = "http://192.168.1.108/fcsnew/hospital.catering/api/";
//    private static String BaseUrl = "http://192.168.1.128/hospital.catering/api/";    // 192.168.1.31/hospital.catering/api/   url for App

    private static String BaseUrl = "";
    static private SweetAlertDialog pDialog;

    private IonServiceManager(Context context) {
        this.context = context;
        this.address = new Address();
        this.requestParameters = new RequestParameters();

    }

    public void setActivity(FragmentActivity activityContext) {
        this.activityContext = activityContext;
    }

    public static class Builder {
        Context context;

        public Builder(Context context) {
            this.context = context;
        }


        public IonServiceManager build() {
            IonServiceManager serviceManager = new IonServiceManager(context);

            return serviceManager;
        }
    }

    public void changeBaseUrlToThis(String baseUrl) {
        BaseUrl = baseUrl;
        this.address = new Address();
    }


    public class Address {
//        private String API="/api/";
//        public String RequestAuthentication = BaseUrl +API+ "deviceauthentication";
//        public String SyncData = BaseUrl +API+ "syncdata";
//        public String UploadDb = BaseUrl +API+ "uploaddb";
//        public String IMAGE_URL = BaseUrl +"/public/images/thumb/";
        public String SyncData = BaseUrl + "syncdata";
        public String ShipmentService = BaseUrl + "shipmentservice";
        public String ValidateService = BaseUrl + "validateservice";
        public String PaymentService = BaseUrl + "getcustomercredit";
        public String LocationService = BaseUrl + "getLocations";
        public String SaveService = BaseUrl + "save";
        public String SyncPhysicalStockData = BaseUrl + "physicalstockcheck";


    }

    public class RequestParameters {


        private JsonObject jsonObject;

        public RequestParameters() {
            jsonObject = new JsonObject();
        }

        public JsonObject getAuthenticationRequest() {


            JsonObject json = new JsonObject();

            json.addProperty(KEYS.ANDROID_ID, DeviceInfoUtil.getAndroidID(context));
            json.addProperty(KEYS.BRAND, DeviceInfoUtil.getBrandName(context));
            json.addProperty(KEYS.MODEL, DeviceInfoUtil.getModelName(context));
            json.addProperty(KEYS.IMEI_NO, DeviceInfoUtil.getDeviceIMEI(context));
            json.addProperty(KEYS.ANDROID_VERSION, DeviceInfoUtil.getDeviceAndroidVersionName(context));
            json.addProperty(KEYS.VERSION_CODE, DeviceInfoUtil.getSelfVersionCode(context));


            showLogs("" + json);

            return json;
        }

    }

    public Address getAddress() {
        return address;
    }

    public RequestParameters getRequestParameters() {
        return requestParameters;
    }


    public class KEYS {
        public static final String PASSWORD = "password";

        public static final String USERNAME = "email";
        public static final String EMAIL = "email";
        public static final String NAME = "name";
        public static final String USERTYPE = "usertype";
        public static final String PHONE = "phone";


        public static final String STATUS = "status";
        public static final String MESSAGE = "message";

        public static final String USER_ID = "userid";


        public static final String ANDROID_VERSION = "androidversion";
        public static final String BRAND = "brand";
        public static final String MODEL = "model";
        public static final String ANDROID_ID = "androidid";
        public static final String IMEI_NO = "imei";
        public static final String VERSION_CODE = "versioncode";
        public static final String TRANS_DATA = "transdata";

        public static final String UPDATE_DATA = "update_data";

        public static final String DATA = "data";
        public static final String hospitalid = "hospitalid";
        public static final String hospital = "hospital";
        public static final String TYPE = "type";
        public static final String EXCEPTION = "exception";


        public static final String BARCODE = "barcode";

        public static final String PHONE_NO = "phoneno";
        public static final String RECEIVER_NAME = "receiver_name";
        public static final String NATIONAL_ID = "nationalid";
        public static final String COMMENT = "comment";


        public static final String RACK_NO = "rackno";
        public static final String BIN_NO = "binno";
        public static final String PARCELS = "parcels";
        public static final String PARCEL_NO = "parcelno";

        public static final String CUSTOMER_ID = "customerid";

        public static final String androidversion = "androidversion";
        public static final String brand = "brand";
        public static final String model = "model";
        public static final String userid = "userid";
        public static final String androidid = "androidid";
        public static final String versioncode = "versioncode";
        public static final String exception = "exception";

        public static final String POD = "pod";
        public static final String INVOICE_NUMBER = "invoice_no";

        public static final String CUSTOMER_CARE_ID = "customer_care_id";
        public static final String GCM_REGID = "Gcmid";
        public static final String Title = "Title";
        public static final String IMAGE = "Image";
        public static final String Text = "Text";
        public static final String IS_NOTIFICATION = "APP_NOTIFICATION";

        public static final String Cipher_Key = "cipher_key";
        public static final String TOKEN = "token";
        public static final String URL = "url";
        public static final String Device_Name = "device_name";
        public static final String Last_Sync = "last_sync";
        public static final String errormessage = "errormessage";

        public static final String stationId = "stationid";
        public static final String station = "station";
        public static final String Patient = "patient";

        public static final String Meal_Type = "mealtype";
        public static final String Meal_Type_Breakfast = "1";
        public static final String Meal_Type_Lunch = "2";
        public static final String Meal_Type_Dinner = "3";
        public static final String DeviceNo = "deviceNo";
        public static final String position = "position";
        public static final String flag = "flag";
        public static final String isedit = "IS_EDIT";
        public static final String LANGUAGE = "language";
        public static final String IMAGES_FOLDER_NAME = "FCS_image";

        public static final String PICKUP_ADDRESS="pickup_address";
        public static final String DELIVERY_ADDRESS="delivery_address";

        public static final String PICKUP_COUNTRY_ID="shipperid";
        public static final String DELIVERY_COUNTRY_ID="destinationid";
        public static final String PARCEL_TYPE="parceltype";
        public static final String COUNTRY_TYPE="type";
        public static final String TOTAL_WEIGHT="totalweight";
        public static final String SERVICE_ID="service_id";
        public static final String CUST_ID="customerid";
        public static final String PICKUP_TYPE="pickup";
        public static final String COLLECTION_DATE="preferred_collection_date";
        public static final String APPLY_INSURANCE="applyinsurance";
        public static final String SEVICE_ARRAY="service";
        public static final String PAYMENT_ARRAY="payment";
        public static final String SERVICE_AMOUNT="amount";
		public static final String SERVICE = "service";
        public static final String TAX = "tax";


        public static final String KEY_USER_ID = "userid";
        public static final String KEY_USERTYPE = "usertype";
        public static final String KEY_ANDROID_VERSION = "androidversion";
        public static final String KEY_BRAND = "brand";
        public static final String KEY_MODEL = "model";
        public static final String KEY_ANDROID_ID = "androidid";
        public static final String KEY_VERSION_CODE = "versioncode";
    }
//    shipperid    1
//    destinationid    1
//    type    0
//    parceltype    11
//    totalweight    8
//    service_id    3

    abstract static public class ResponseCallback implements FutureCallback<Response<JsonObject>> {

        Context activityContext;

        public ResponseCallback(Context activityContext) {
            this.activityContext = activityContext;
        }

        @Override
        public void onCompleted(Exception e, Response<JsonObject> response) {
            hideProgress(progressenabled);
            if (e != null) {
                String exception_message = getExceptionMessage(activityContext, e);
                e.printStackTrace();
                showLogs("Exception Occurred" + exception_message);
                hideProgress(progressenabled);
//                showException(exception_message);
                onException(exception_message);


            } else if (response != null) {
                if (response.getHeaders().code() == 200) {

                    //SUCCESS !!
                    showLogs("Response Code 200  Result " + response.getResult());
                    try {
                        if (response.getResult() != null) {

                            parseResponse(response);
                        } else {
                            throw new RuntimeException(activityContext.getString(R.string.server_exception_error_message_response_null));
                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                        String exception_message = getExceptionMessage(activityContext, e1);
//                        showException(exception_message);
                        onException(exception_message);
                    } catch (JsonSyntaxException e1) {
                        e1.printStackTrace();
                        String exception_message = getExceptionMessage(activityContext, e1);
//                        showException(exception_message);
                        onException(exception_message);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        String exception_message = getExceptionMessage(activityContext, e1);
//                        showException(exception_message);
                        onException(exception_message);
                    }


                } else {
                    String exception_message = getErrorMessage(activityContext, response.getHeaders().code());
                    showLogs("Exception Occurred" + exception_message);
                    onException(exception_message);


                    //FAIL!! Show TOAST!
                }
            } else {
                String exception_message = activityContext.getString(R.string.server_exception_error_message_response_null);
                showLogs("Exception Occurred" + exception_message);
                onException(exception_message);
            }


        }

        private void parseResponse(Response<JsonObject> response) throws JSONException, Exception {
            JsonObject jsonObject = response.getResult();
//            JSON.parse(JSON.stringify(yourjson).replace(/null/g, '""'))
//            jsonObject.stringify(data).replace(/null/i, "\"\"");
            boolean status = jsonObject.get(KEYS.STATUS).getAsBoolean();

            String message = jsonObject.get(KEYS.MESSAGE).getAsString();


            if (status) {


//                String encryptedsdata = jsonObject.get(KEYS.DATA).getAsString();
                Object data = jsonObject.get(KEYS.DATA).getAsJsonObject();

//                if(jsonObject.has(KEYS.DATA)){
//                    Object data = jsonObject.get(KEYS.DATA).getAsJsonObject();
//                    onResponse(status, message, data);
//                }
//                Log.i("decrpteddatastart", "st");
//                String decrpteddata = new String(MCrypt.getInstance().decrypt(encryptedsdata));


//                Object data = encryptedsdata;
                ;
                Log.i("decrpteddataend", "end");
                onResponse(status, message, data);
            } else {
//                if (message != null && message.length() > 1) {
//                    SweetAlertUtil.showErrorMessage(activityContext, message);
//                }
                onException(message);
            }
        }


        public Gson getGsonInstance() {
            return DIHelper.getGsonInstance();
        }


        abstract public void onException(String s);

        abstract public void onResponse(boolean status, String message, Object result) throws Exception;


        public void showException(String message) {
            SweetAlertUtil.showWarningWithCallback(activityContext, message, activityContext.getString(R.string.ok), new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.cancel();
//                    ((Activity) activityContext).finish();
                }
            });
        }

        public static String getExceptionMessage(Context context, Exception exception) {
            String exception_message = context.getString(R.string.exception_alert_message_error);
            if (exception.getClass().equals(UnknownHostException.class)) {
                exception_message = context.getString(R.string.activity_base_alert_message_unknown_host_exception);
            } else if (exception.getClass().equals(IOException.class)) {
                exception_message = context.getString(R.string.exception_alert_message_illegalstate_exception);
            } else if (exception.getClass().equals(JSONException.class) || exception.getClass().equals(JsonSyntaxException.class)) {
                exception_message = context.getString(R.string.exception_alert_message_parsing_exception);
            } else if (exception.getClass().equals(TimeoutException.class) || exception.getClass().equals(SocketTimeoutException.class)) {
                exception_message = context.getString(R.string.exception_alert_message_timeout_exception);
            }
//        else if (exception.getClass().equals(NetworkException.class)) {
//
//            exception_message = R.string.exception_alert_message_network;
//        }
            else if (exception.getClass().equals(IllegalStateException.class)) {
                exception_message = context.getString(R.string.exception_alert_message_illegalstate_exception);
            } else if (exception.getClass().equals(InvalidParametersException.class)) {
                exception_message = context.getString(R.string.server_exception_error_message_invalid_parameter);
            } else if (exception.getClass().equals(NullPointerException.class)) {
                exception_message = context.getString(R.string.server_exception_error_message_nullpointerr);
            } else if (exception.getClass().equals(RuntimeException.class)) {
                exception_message = exception.getMessage();
            } else if (exception.getClass().equals(SQLiteException.class) || exception.getClass().equals(SQLiteConstraintException.class)) {
                exception_message = context.getString(R.string.exception_alert_message_sqllite_exception);
            } else {
                exception_message = context.getString(R.string.exception_alert_message_error);
            }
            return exception_message;
        }

        public static String getErrorMessage(Context context, int responsecode) {

            String exception_message = context.getString(R.string.exception_alert_message_error);

            if (responsecode == 500) {
                exception_message = context.getString(R.string.exception_alert_message_internal_server_error);

            } else if (responsecode == 404) {
                exception_message = context.getString(R.string.server_exception_error_message_resource_not_found);

            } else if (responsecode == 403) {
                exception_message = context.getString(R.string.server_exception_error_message_forbidden);

            }
            return exception_message;
        }

    }

    public static void showLogs(String log) {
        if (enableLogs) {
            Log.i(TAG, log);
        }
    }


    public void postRequestToServer(String url, ResponseCallback responseCallback, String... parameters) throws InvalidParametersException {

//        String logParameters = url + "?";
//        showProgress(activityContext, true);
//        Builders.Any.B request = Ion.with(context)
//                .load("POST", url);
//
//        if (parameters == null) {
//            showLogs("Parameters are null");
//        } else if (parameters != null && parameters.length % 2 == 0) {
//            showLogs("Parameters are  " + Arrays.toString(parameters));
//            for (int i = 0; i < parameters.length; i = i + 2) {
//
//                request.setBodyParameter(parameters[i], parameters[i + 1]);
//                logParameters = logParameters + parameters[i] + "=" + parameters[i + 1] + "&";
//
//            }
//        } else {
//            hideProgress(progressenabled);
//            throw new InvalidParametersException();
//        }
//        showLogs("CheckUrl: " + logParameters);
//
//        request.asJsonObject()
//                .withResponse()
//                .setCallback(responseCallback);
        progressenabled = true;
        requestToServer(url, responseCallback, progressenabled, parameters);


//


    }

    public void postRequestToServerWOprogress(String url, ResponseCallback responseCallback, String... parameters) throws InvalidParametersException {


        progressenabled = false;
        requestToServer(url, responseCallback, progressenabled, parameters);


//


    }


    private void requestToServer(String url, ResponseCallback responseCallback, boolean progressenabled, String... parameters) throws InvalidParametersException {

        String logParameters = url + "?";
        showProgress(activityContext, progressenabled);
//        Ion ion = Ion.getDefault(c);
//        ion.configure().createSSLContext("TLS");
//        ion.getHttpClient().getSSLSocketMiddleware().setSSLContext(sslContext);
//        ion.getHttpClient().getSSLSocketMiddleware().setTrustManagers(trustManagers);
        Builders.Any.B request =


                Ion.with(context)
                        .load("POST", url);

        if (parameters == null) {
            showLogs("Parameters are null");
        } else if (parameters != null && parameters.length % 2 == 0) {
            showLogs("Parameters are  " + Arrays.toString(parameters));
            for (int i = 0; i < parameters.length; i = i + 2) {

                request.setBodyParameter(parameters[i], parameters[i + 1]);
                logParameters = logParameters + parameters[i] + "=" + parameters[i + 1] + "&";

            }
        } else {
            hideProgress(progressenabled);
            throw new InvalidParametersException();
        }
        showLogs("CheckUrl: " + logParameters);

        request.setTimeout(TIMEOUT_TIME);
        request.asJsonObject()
                .withResponse()
                .setCallback(responseCallback);


//


    }

    public static void showProgress(Context context, boolean progressenabled) {
        if (context != null && progressenabled) {

            pDialog = SweetAlertUtil.getProgressDialog(context);
            pDialog.show();
        }
    }

    public static void hideProgress(boolean progressenabled) {
        if (pDialog != null && progressenabled) {
            pDialog.cancel();
        }

    }


    /**
     * Custom Class to Represent Exception caused due to Invalid Parameters
     */
    public class InvalidParametersException extends Exception {


        public int message() {
            return R.string.server_exception_error_message_invalid_parameter;
        }
    }

}
