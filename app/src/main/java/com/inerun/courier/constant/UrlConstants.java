package com.inerun.courier.constant;

/**
 * Created by vineet on 5/24/2016.
 */
public class UrlConstants {


//    public static final String BASE_ADDRESS = "http://192.168.1.129/dropinsta/app/";
//    public static final String BASE_ADDRESS = "http://192.168.1.117/dropinsta/app/";
//    public static final String BASE_ADDRESS = "http://148.251.29.69/dropinsta/app/";
//    public static final String BASE_ADDRESS = "http://148.251.29.69/dropinsta1/app/"; // Fist URL for Demo before implementing Pickup
//    public static final String BASE_ADDRESS = "http://148.251.29.69/courier/app/";   //Last Used URL
//    public static final String BASE_ADDRESS = "http://tigmooshopnship.com/app/";        // Live
//    public static final String BASE_ADDRESS = "https://tigmooshopnship.com/old/app/";        // OLD Live
//    public static final String BASE_ADDRESS = "https://tigmooshopnship.com/app/";        // new Live
//    public static final String BASE_ADDRESS = "http://192.168.1.118/dropinsta/app/"; // Prabhat
//    public static final String BASE_ADDRESS = "http://192.168.1.113/dropinsta/app/";  // Sumit
//    public static final String BASE_ADDRESS = "http://192.168.1.105/dropinsta/app/";  // Shivani
//    public static final String BASE_ADDRESS = "http://148.251.29.69/dropinsta001/app/";  // 4Apr2018


//    public static final String BASE_ADDRESS = "http://148.251.29.69/dropinsta2/app/";
//    public static final String BASE_ADDRESS = "http://192.168.1.33/dropinsta1/app/";
//    public static final String BASE_ADDRESS = "http://148.251.29.69/sns27feb/app/";  //6Apr2018   // Demo Old
//    public static final String BASE_ADDRESS = "http://148.251.29.69/sns13ap/app/";  //6Apr2018  // Demo New1
//    public static final String BASE_ADDRESS = "http://192.168.1.33/sns13ap/app/";  //6Apr2018   Shabeena
//    public static final String BASE_ADDRESS = "http://192.168.1.32/oldsns/app/";  //6Apr2018  sumit OLD     (Auction)
//    public static final String BASE_ADDRESS = "http://192.168.1.32/newsns/app/";  //6Apr2018  sumit NEW   (Physical Stock Check)
//    public static final String BASE_ADDRESS = "http://148.251.29.69/sns6spt/app/";  //22Sept2018  Demo New2


    //courier
//    public static final String BASE_ADDRESS = "http://148.251.29.69/mercury/app/";  //22Sept2018  Demo New2d
	//public static final String BASE_ADDRESS = "http://148.251.29.69/courier/app/";  //22Sept2018  Demo New2d
//	public static final String BASE_ADDRESS = "http://148.251.29.69/mercury_dev/app/";  //22Sept2018  Demo 21Dec2018

    // Mercury
//    public static final String BASE_ADDRESS = "http://192.168.1.33/mercury/app/";  //12Nov2018 Shabeena
    public static final String BASE_ADDRESS = "http://192.168.1.31/mercury/app/";  //14Nov2018  Baal Divas || Prabhat
//    public static final String BASE_ADDRESS = "http://94.130.181.130/mercury/app/";  //28Dec2018 || Live



    /* BaseURL + service_name*/
    public static final String URL_LOGIN = BASE_ADDRESS + "login";

    public static final String URL_LOGOUT = BASE_ADDRESS + "logout";
    public static final String URL_SYNC_AND_UPDATE = BASE_ADDRESS + "index";
    public static final String URL_EXCEPTION = BASE_ADDRESS + "exception";
    public static final String URL_POD_UPLOAD = BASE_ADDRESS + "uploadpod";
    public static final String URL_WH_POD_UPLOAD = BASE_ADDRESS + "uploadpodwarehouse";
    public static final String URL_SEARCH = BASE_ADDRESS + "search";
    public static final String URL_ADD = BASE_ADDRESS + "warehouse" ;
    public static final String URL_INVOICE_SEARCH = BASE_ADDRESS + "invoicesearch";
    public static final String URL_INVOICE_DELIVERY = BASE_ADDRESS + "invoicedelivery";
    public static final String URL_RETURN_PARCEL = BASE_ADDRESS + "returnparcel";

    public static final String URL_READY_INVOICE_LIST = BASE_ADDRESS + "invoicelist";
    public static final String URL_GCM = BASE_ADDRESS + "updategcim";
    public static final String URL_INVOICE_DELIVERED_CUSTOMER = BASE_ADDRESS + "invoicedelivery";

    public static final String URL_READY_FOR_EXECUTIVE_LIST = BASE_ADDRESS + "requestlist";
    public static final String URL_RETURN_PARCEL_LIST = BASE_ADDRESS + "invoicelist";
    public static final String URL_REQUEST_PARCEL_LIST = BASE_ADDRESS + "custrequestparcellist";
    public static final String URL_DRIVER_REPORT_DATA = BASE_ADDRESS + "driverreport";
    public static final String URL_DRIVER_LOCATION_UPDATE = BASE_ADDRESS + "driverlocationupdate";
    public static final String URL_LOCATION_DATA = BASE_ADDRESS + "getLocations";


    //Pagination Tigmoo Demo
    public static final String BASE_ADDRESS_Demo = "http://148.251.29.69/tigmoo67/api/index/";  //6Apr2018
    public static final String URL_DEMO = BASE_ADDRESS_Demo + "productlisting";

//    public static final String URL_LOCATION_DATA = "http://148.251.29.69/fauzi/api/getLocations?androidid=aa215ecec141327f&branch_id=2&";



    public static final String KEY_Password = "password";

    public static final String KEY_Usename = "email";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_NAME = "name";
    public static final String KEY_USERTYPE = "usertype";
    public static final String KEY_PHONE = "phone";


    public static final String KEY_STATUS = "status";
    public static final String KEY_MESSAGE = "message";

    public static final String KEY_USER_ID = "userid";


    public static final String KEY_ANDROID_VERSION = "androidversion";
    public static final String KEY_BRAND = "brand";
    public static final String KEY_MODEL = "model";
    public static final String KEY_ANDROID_ID = "androidid";
    public static final String KEY_VERSION_CODE = "versioncode";
    public static final String KEY_TRANS_DATA = "transdata";

    public static final String KEY_UPDATE_DATA = "update_data";
    public static final String KEY_DELIVERY_UPDATE_DATA = "delivery_update_data";

    public static final String KEY_DATA = "data";
    public static final String KEY_TYPE = "type";
    public static final String KEY_EXCEPTION = "exception";
    public static final String KEY_REQUEST_CODE = "requestCode";


    public static final String KEY_BARCODE = "barcode";

    public static final String KEY_PHONE_NO = "phoneno";
    public static final String KEY_RECEIVER_NAME = "receiver_name";
    public static final String KEY_NATIONAL_ID = "nationalid";
    public static final String KEY_COMMENT = "comment";


	public static final String KEY_RACK_NO = "rackno";
    public static final String KEY_BIN_NO = "binno";
    public static final String KEY_PARCELS = "parcels";
    public static final String KEY_PARCEL_NO = "parcelno";

    public static final String KEY_CUSTOMER_ID = "customerid";
    public static final String KEY_LAT = "latitude";
    public static final String KEY_LONG = "longitude";

    public static final String KEY_androidversion = "androidversion";
    public static final String KEY_brand = "brand";
    public static final String KEY_model = "model";
    public static final String KEY_userid = "userid";
    public static final String KEY_androidid = "androidid";
    public static final String KEY_versioncode = "versioncode";
    public static final String KEY_exception = "exception";

    public static final String KEY_POD = "pod";
    public static final String KEY_INVOICE_NUMBER = "invoice_no";

    public static final String KEY_CUSTOMER_CARE_ID = "customer_care_id";
    public static final String KEY_GCM_REGID = "Gcmid";
    public static final String KEY_Title = "Title";
    public static final String KEY_IMAGE = "Image";
    public static final String KEY_Text = "Text";
    public static final String KEY_IS_NOTIFICATION = "APP_NOTIFICATION";


    public static final String KEY_EXECUTIVE_DATA = "executive_data";
    public static final String KEY_REQUEST_ID = "request_id";
    public static final String KEY_DELIVERY_STATUS_FLAG = "Delivery_flag";
    public static final String KEY_USER_TYPE = "user_type";

    public static final String KEY_PICKUP_UPDATE_DATA = "pickup_update_data";
    public static final String KEY_START = "start";
    public static final String KEY_LIMIT = "limit";
    public static final String KEY_PAGETYPE = "pagetype";
    public static final String KEY_TAB_SELECTION = "tab_selection";
    public static final String KEY_DATE_RANGE = "date_range";

    public static final int LOGIN = 0;
    public static final int RREQUEST = 1;
    public static final int CASH_TAB = 1;
    public static final int CARD_TAB = 2;
    public static final String KEY_POSITION = "position";

    public static final String CARD = "CARD";
    public static final String CASH = "CASH";
    public static final String ON_ACCOUNT = "ON ACCOUNT";
    public static final String COD = "COD";
}
