package com.inerun.courier.service;

import android.content.Context;
import android.location.Location;

import com.google.gson.Gson;
import com.inerun.courier.base.DeviceInfoUtil;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.constant.Utils;
import com.inerun.courier.data.ParcelListingData;
import com.inerun.courier.data.ParcelSearchData;
import com.inerun.courier.data.ReadyParcelData;
import com.inerun.courier.data.TransactionData;
import com.inerun.courier.data.UpdatedParcelData;
import com.inerun.courier.data.UpdatedPickupParcelData;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.loopj.android.http.RequestParams;
//import com.tigmoo.Data.CheckoutData;
//import com.tigmoo.Data.FilterData;
//import com.tigmoo.Data.MyWishlistData;
//import com.tigmoo.MainActivity;
//import com.tigmoo.cart.CartViewData;
//import com.tigmoo.cart.ProCartBean;
//import com.tigmoo.constant.UrlConstants;
//import com.tigmoo.constant.Utils;
//import com.tigmoo.utils.DeviceInfoUtil;

/**
 * Created by vineet on 5/26/2016.
 */
public class DIRequestCreator {
    static DIRequestCreator requestCreator;
    static Context context;
    RequestParams params;
    Map<String, String> mapParams;


    public static DIRequestCreator getInstance(Context context) {

        DIRequestCreator.context = context;
        if (requestCreator == null)
            requestCreator = new DIRequestCreator();
        requestCreator.mapParams = new HashMap<String, String>();
        requestCreator.params = new RequestParams();
//        requestCreator.putLangauge();
//        requestCreator.putStore();
//        requestCreator.putAndroidId();
        return requestCreator;


    }
//    private void putStore() {
////        String lan_code = BTDbHelper.getLangCodeFromSharedPref(context);
////        if (lan_code != null) {
////            params.put(UrlConstants.KEY_LANG, lan_code);
////        }
//        int storeid = Utils.getStoreId(context);
//        if (storeid > -1) {
//            params.put(UrlConstants.STORE_ID_KEY, storeid);
//        }
//    }


//    private void putAndroidId() {
//
//        params.put(UrlConstants.KEY_ANDROID_ID, DeviceInfoUtil.getAndroidID(context));
//    }
//
//    public RequestParams getAndroidId() {
//
//        return params;
//    }
//
//    public RequestParams getStore() {
//
//        return params;
//    }


    public RequestParams getLoginParams(String email, String pass) {

        params.put(UrlConstants.KEY_Usename, email);
        params.put(UrlConstants.KEY_Password, pass);
        return params;
    }

    public Map<String, String> getLoginMapParams(String email, String pass) {


        String gcmid = Utils.getGcmId(context);
        mapParams.put(UrlConstants.KEY_Usename, email);
        mapParams.put(UrlConstants.KEY_Password, pass);
        if (gcmid != null && gcmid.length() > 0) {
            mapParams.put(UrlConstants.KEY_GCM_REGID, gcmid);
        }
        mapParams.put(UrlConstants.KEY_ANDROID_ID, DeviceInfoUtil.getAndroidID(context));

        return mapParams;

    }

    public Map<String, String> getGcmRegistrationParams(Context context, String gcm_id) {

        mapParams.put(UrlConstants.KEY_GCM_REGID, gcm_id);
        if (Utils.isUserLoggedIn(context)) {
            mapParams.put(UrlConstants.KEY_USER_ID, Utils.getUserId(context));
            mapParams.put(UrlConstants.KEY_USER_TYPE, Utils.getUserType(context) + "");

        }
        mapParams.put(UrlConstants.KEY_ANDROID_ID, DeviceInfoUtil.getAndroidID(context));
        return mapParams;
    }

    public Map<String, String> getSyncDataMapParams(ArrayList<UpdatedParcelData> parcelDatas, ArrayList<TransactionData> transcDatas) {

        String android_version = DeviceInfoUtil.getDeviceAndroidVersionName(context);
        String brand = DeviceInfoUtil.getBrandName(context);
        String model = DeviceInfoUtil.getModelName(context);
        String android_id = DeviceInfoUtil.getAndroidID(context);
        String version_code = "" + DeviceInfoUtil.getSelfVersionCode(context);

        if (Utils.isUserLoggedIn(context)) {
            mapParams.put(UrlConstants.KEY_USER_ID, Utils.getUserId(context));
            mapParams.put(UrlConstants.KEY_USERTYPE, "" + Utils.getUserType(context));
        }

        mapParams.put(UrlConstants.KEY_ANDROID_VERSION, android_version);
        mapParams.put(UrlConstants.KEY_BRAND, brand);
        mapParams.put(UrlConstants.KEY_MODEL, model);
        mapParams.put(UrlConstants.KEY_ANDROID_ID, android_id);
        mapParams.put(UrlConstants.KEY_VERSION_CODE, version_code);


        if (parcelDatas != null && parcelDatas.size() > 0) {

            Gson gson = new Gson();
            String json = gson.toJson(parcelDatas);

            mapParams.put(UrlConstants.KEY_UPDATE_DATA, json);
        } else {

            mapParams.put(UrlConstants.KEY_UPDATE_DATA, "[]");
        }


        Gson tgson = new Gson();
        String tjson = tgson.toJson(transcDatas);


        mapParams.put(UrlConstants.KEY_TRANS_DATA, tjson);


        return mapParams;

    }

    public Map<String, String> getSearchMapParams(String parcelnum, String name, String email, String contact, String customerid, String parcel_invoice_no) {


        if (Utils.isUserLoggedIn(context)) {
            mapParams.put(UrlConstants.KEY_USER_ID, Utils.getUserId(context));
            mapParams.put(UrlConstants.KEY_USERTYPE, "" + Utils.getUserType(context));
        }
        mapParams.put(UrlConstants.KEY_NAME, name);
        mapParams.put(UrlConstants.KEY_EMAIL, email);
        mapParams.put(UrlConstants.KEY_BARCODE, parcelnum);
        mapParams.put(UrlConstants.KEY_PHONE_NO, contact);
        mapParams.put(UrlConstants.KEY_CUSTOMER_ID, customerid);
        mapParams.put(UrlConstants.KEY_INVOICE_NUMBER, parcel_invoice_no);


        return mapParams;

    }

    public Map<String, String> getSearchMapParamsNew(ParcelSearchData parcelSearchData) {


        if (Utils.isUserLoggedIn(context)) {
            mapParams.put(UrlConstants.KEY_USER_ID, Utils.getUserId(context));
            mapParams.put(UrlConstants.KEY_USERTYPE, "" + Utils.getUserType(context));
        }
        mapParams.put(UrlConstants.KEY_NAME, parcelSearchData.getParcel_name());
        mapParams.put(UrlConstants.KEY_EMAIL, parcelSearchData.getParcel_email());
        mapParams.put(UrlConstants.KEY_BARCODE, parcelSearchData.getParcel_no());
        mapParams.put(UrlConstants.KEY_PHONE_NO, parcelSearchData.getParcel_phone());
        mapParams.put(UrlConstants.KEY_CUSTOMER_ID, parcelSearchData.getParcel_custid());
        mapParams.put(UrlConstants.KEY_INVOICE_NUMBER, parcelSearchData.getParcel_invoice_no());


        return mapParams;

    }


    public Map<String, String> getInvoiceSearchMapParams(String parcel_invoice_no) {


        if (Utils.isUserLoggedIn(context)) {
            mapParams.put(UrlConstants.KEY_USER_ID, Utils.getUserId(context));
            mapParams.put(UrlConstants.KEY_USERTYPE, "" + Utils.getUserType(context));
        }

        mapParams.put(UrlConstants.KEY_INVOICE_NUMBER, parcel_invoice_no);


        return mapParams;

    }

    public Map<String, String> getReadyParcelDeliveredMapParams(ArrayList<ReadyParcelData> readyParcelDataArrayList, String CustomerCareId) {

        if (Utils.isUserLoggedIn(context)) {
            mapParams.put(UrlConstants.KEY_USER_ID, Utils.getUserId(context));
            mapParams.put(UrlConstants.KEY_USERTYPE, "" + Utils.getUserType(context));
        }

        mapParams.put(UrlConstants.KEY_CUSTOMER_CARE_ID, CustomerCareId);

        if (readyParcelDataArrayList != null && readyParcelDataArrayList.size() > 0) {

            Gson gson = new Gson();
            String json = gson.toJson(readyParcelDataArrayList);

            mapParams.put(UrlConstants.KEY_UPDATE_DATA, json);
        } else {

            mapParams.put(UrlConstants.KEY_UPDATE_DATA, "[]");
        }


        return mapParams;

    }

    public Map<String, String> getReadyParcelDeliveredMapParamsForCustSupport(String invoice_number, String customerCareId) {

        if (Utils.isUserLoggedIn(context)) {
            mapParams.put(UrlConstants.KEY_USER_ID, Utils.getUserId(context));
            mapParams.put(UrlConstants.KEY_USERTYPE, "" + Utils.getUserType(context));
        }
        mapParams.put(UrlConstants.KEY_CUSTOMER_CARE_ID, customerCareId);
        mapParams.put(UrlConstants.KEY_INVOICE_NUMBER, invoice_number);


        return mapParams;

    }

    public Map<String, String> getRequestedParcelHandoverMapParamsForCustSupport(String request_id, String customerCareId) {

        if (Utils.isUserLoggedIn(context)) {
            mapParams.put(UrlConstants.KEY_USER_ID, Utils.getUserId(context));
            mapParams.put(UrlConstants.KEY_USERTYPE, "" + Utils.getUserType(context));
        }
        mapParams.put(UrlConstants.KEY_CUSTOMER_CARE_ID, customerCareId);
        mapParams.put(UrlConstants.KEY_REQUEST_ID, request_id);


        return mapParams;

    }


    public Map<String, String> getAddParcelMapParams(String rack, List<String> parcellist) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        for (String parcel : parcellist) {
            JSONObject json = new JSONObject();
            json.put(UrlConstants.KEY_PARCEL_NO, parcel);
            jsonArray.put(json);
        }

        if (Utils.isUserLoggedIn(context)) {
            mapParams.put(UrlConstants.KEY_USER_ID, Utils.getUserId(context));
            mapParams.put(UrlConstants.KEY_USERTYPE, "" + Utils.getUserType(context));
        }
        mapParams.put(UrlConstants.KEY_BIN_NO, rack);
        mapParams.put(UrlConstants.KEY_PARCELS, jsonArray.toString());


        return mapParams;
    }

    public Map<String, String> getExceptionLogs(String exception) {
        insertUserID();

        String android_version = DeviceInfoUtil.getDeviceAndroidVersionName(context);
        String brand = DeviceInfoUtil.getBrandName(context);
        String model = DeviceInfoUtil.getModelName(context);
        String android_id = DeviceInfoUtil.getAndroidID(context);
        String version_code = "" + DeviceInfoUtil.getSelfVersionCode(context);

        mapParams.put(UrlConstants.KEY_androidversion, android_version);
        mapParams.put(UrlConstants.KEY_brand, brand);
        mapParams.put(UrlConstants.KEY_model, model);
        mapParams.put(UrlConstants.KEY_ANDROID_ID, android_id);
        mapParams.put(UrlConstants.KEY_versioncode, version_code);
        mapParams.put(UrlConstants.KEY_exception, exception);

//        String an
        return mapParams;
    }

    public void insertUserID() {
        if (Utils.isUserLoggedIn(context)) {

            mapParams.put(UrlConstants.KEY_USER_ID, Utils.getUserId(context));
        }
    }

    public Map<String, String> getReturnParcelMapParams(ArrayList<ParcelListingData.ParcelData> selectedparcellist) throws JSONException {
        JSONArray jsonArray = new JSONArray();

        for (ParcelListingData.ParcelData parcel : selectedparcellist) {
            JSONObject json = new JSONObject();
            json.put(UrlConstants.KEY_PARCEL_NO, parcel.getBarcode());
            jsonArray.put(json);
        }

        if (Utils.isUserLoggedIn(context)) {
            mapParams.put(UrlConstants.KEY_USER_ID, Utils.getUserId(context));
            mapParams.put(UrlConstants.KEY_USERTYPE, "" + Utils.getUserType(context));
        }

        mapParams.put(UrlConstants.KEY_PARCELS, jsonArray.toString());

        return mapParams;
    }

    public Map<String, String> getReadyInvoiceMapParams(int start, int limit) {
        if (Utils.isUserLoggedIn(context)) {
            mapParams.put(UrlConstants.KEY_USER_ID, Utils.getUserId(context));
            mapParams.put(UrlConstants.KEY_USERTYPE, "" + Utils.getUserType(context));
        }
        mapParams.put(UrlConstants.KEY_START, "" + start);
        mapParams.put(UrlConstants.KEY_LIMIT, "" + limit);
        return mapParams;
    }

    public Map<String, String> getReadyForExecutiveMapParams(int start, int limit) {
        if (Utils.isUserLoggedIn(context)) {
            mapParams.put(UrlConstants.KEY_USER_ID, Utils.getUserId(context));
            mapParams.put(UrlConstants.KEY_USERTYPE, "" + Utils.getUserType(context));
        }
        mapParams.put(UrlConstants.KEY_START, "" + start);
        mapParams.put(UrlConstants.KEY_LIMIT, "" + limit);
        return mapParams;
    }

    public Map<String, String> getReturnParcelMapParams() {
        if (Utils.isUserLoggedIn(context)) {
            mapParams.put(UrlConstants.KEY_USER_ID, Utils.getUserId(context));
            mapParams.put(UrlConstants.KEY_USERTYPE, "" + Utils.getUserType(context));
        }
        return mapParams;
    }


//    public RequestParams getGcmRegistrationParams(Context context,String gcm_id) {
//
//        params.put(UrlConstants.KEY_gcmId, gcm_id);
//        if (Utils.isUserLoggedIn(context) ) {
//            params.put(UrlConstants.KEY_USER_ID, Utils.getUserId(context));
//            params.put(UrlConstants.KEY_CUSTOMER_ID, Utils.getUserId(context));
//        }else{
//		 params.put(UrlConstants.KEY_USER_ID, "");
//            params.put(UrlConstants.KEY_CUSTOMER_ID, "");
//		}
//        params.put(UrlConstants.KEY_ANDROID_ID, DeviceInfoUtil.getAndroidID(context));
//        return params;
//    }

    public Map<String, String> getSyncDataMapParams_New(ArrayList<UpdatedParcelData> parcelDatas, ArrayList<TransactionData> transcDatas, ArrayList<UpdatedPickupParcelData> pickupParcelDatas) {

        String android_version = DeviceInfoUtil.getDeviceAndroidVersionName(context);
        String brand = DeviceInfoUtil.getBrandName(context);
        String model = DeviceInfoUtil.getModelName(context);
        String android_id = DeviceInfoUtil.getAndroidID(context);
        String version_code = "" + DeviceInfoUtil.getSelfVersionCode(context);

        if (Utils.isUserLoggedIn(context)) {
            mapParams.put(UrlConstants.KEY_USER_ID, Utils.getUserId(context));
            mapParams.put(UrlConstants.KEY_USERTYPE, "" + Utils.getUserType(context));
        }

        mapParams.put(UrlConstants.KEY_ANDROID_VERSION, android_version);
        mapParams.put(UrlConstants.KEY_BRAND, brand);
        mapParams.put(UrlConstants.KEY_MODEL, model);
        mapParams.put(UrlConstants.KEY_ANDROID_ID, android_id);
        mapParams.put(UrlConstants.KEY_VERSION_CODE, version_code);


        if (parcelDatas != null && parcelDatas.size() > 0) {

            Gson gson = new Gson();
            String json = gson.toJson(parcelDatas);

//            mapParams.put(UrlConstants.KEY_UPDATE_DATA, json);
            mapParams.put(UrlConstants.KEY_DELIVERY_UPDATE_DATA, json); //Vinay 10 Dec 2019 Happy Anniversary tyagi ji
        } else {

            mapParams.put(UrlConstants.KEY_DELIVERY_UPDATE_DATA, "[]");
        }


        Gson tgson = new Gson();
        String tjson = tgson.toJson(transcDatas);


        mapParams.put(UrlConstants.KEY_TRANS_DATA, tjson);

        if (pickupParcelDatas != null && pickupParcelDatas.size() > 0) {

            Gson gson = new Gson();
            String json = gson.toJson(pickupParcelDatas);

            mapParams.put(UrlConstants.KEY_PICKUP_UPDATE_DATA, json);
        } else {

            mapParams.put(UrlConstants.KEY_PICKUP_UPDATE_DATA, "[]");
        }

        return mapParams;

    }

    public Map<String, String> getReadyForExecutiveMapParams_exp(int start, int limit) {

        mapParams.put("Start", "" + start);
        mapParams.put("Limit", "" + limit);

        mapParams.put("Order", "desc");
        mapParams.put("StoreId", "1");
        mapParams.put("SortBy", "price");
//        mapParams.put("CatId", "348"); //total 342 item
//        mapParams.put("CatId", "349");  //total 17 item
//        mapParams.put("CatId", "350");  //total 88 item
        mapParams.put("CatId", "343");  //total 3 item
//        mapParams.put("CatId", "3");  //total 0 item


        return mapParams;

        //  Order=desc&StoreId=1&Start=0&UserID=&SortBy=price&CustomerId=&Limit=10&CatId=348&androidId=620594a06d5d399a
    }

    public Map<String, String> getRequestParcelMapParams(String customerId) {

        mapParams.put(UrlConstants.KEY_CUSTOMER_ID, customerId);

        mapParams.put(UrlConstants.KEY_ANDROID_ID, DeviceInfoUtil.getAndroidID(context));

        return mapParams;

    }

    public Map<String, String> getLocationRequestParams(String customerId, Location location) {

        mapParams.put(UrlConstants.KEY_CUSTOMER_ID, customerId);
        mapParams.put(UrlConstants.KEY_NAME, Utils.getName(context));
        mapParams.put(UrlConstants.KEY_LAT, "" + location.getLatitude());
        mapParams.put(UrlConstants.KEY_LONG, "" + location.getLongitude());

        mapParams.put(UrlConstants.KEY_ANDROID_ID, DeviceInfoUtil.getAndroidID(context));

        return mapParams;

    }

    public Map<String, String> getLocationErrorParams(String customerId, String message) {

        mapParams.put(UrlConstants.KEY_CUSTOMER_ID, customerId);
        mapParams.put(UrlConstants.KEY_NAME, "" + Utils.getName(context));
        mapParams.put(UrlConstants.KEY_MESSAGE, "" + message);



        mapParams.put(UrlConstants.KEY_ANDROID_ID, DeviceInfoUtil.getAndroidID(context));

        return mapParams;

    }

    public Map<String, String> getDriverReportMapParams(String date_range) {
        if (Utils.isUserLoggedIn(context)) {
            mapParams.put(UrlConstants.KEY_USER_ID, Utils.getUserId(context));
            mapParams.put(UrlConstants.KEY_USERTYPE, "" + Utils.getUserType(context));
        }
        mapParams.put(UrlConstants.KEY_DATE_RANGE, date_range);

        return mapParams;
    }

    public Map<String, String> getLocationMapParams() {

        String android_version = DeviceInfoUtil.getDeviceAndroidVersionName(context);
        String brand = DeviceInfoUtil.getBrandName(context);
        String model = DeviceInfoUtil.getModelName(context);
        String android_id = DeviceInfoUtil.getAndroidID(context);
        String version_code = "" + DeviceInfoUtil.getSelfVersionCode(context);

        if (Utils.isUserLoggedIn(context)) {
            mapParams.put(UrlConstants.KEY_USER_ID, Utils.getUserId(context));
            mapParams.put(UrlConstants.KEY_USERTYPE, "" + Utils.getUserType(context));
        }

        mapParams.put(UrlConstants.KEY_ANDROID_VERSION, android_version);
        mapParams.put(UrlConstants.KEY_BRAND, brand);
        mapParams.put(UrlConstants.KEY_MODEL, model);
        mapParams.put(UrlConstants.KEY_ANDROID_ID, android_id);
        mapParams.put(UrlConstants.KEY_VERSION_CODE, version_code);




        return mapParams;

    }


}
