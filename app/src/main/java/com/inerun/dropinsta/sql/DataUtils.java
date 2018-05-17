package com.inerun.dropinsta.sql;

/**
 * Created by vineet on 8/2/2016.
 */
public class DataUtils {

    public static final String COLUMN_ID = "id";

    public static final String TABLE_NAME_PARCEL = "PARCEL";

    public static final String PARCEL_ID = "parcel_id";
    public static final String PARCEL_BARCODE = "barcode";
    public static final String CONSIGNEE_NAME = "name";
    public static final String CONSIGNEE_ID = "ConsigneeId"; //Added
    public static final String PARCEL_WEIGHT = "weight";
    public static final String PARCEL_SPECIAL_INSTRUCTION = "specialinstructions";
    public static final String PARCEL_DELIVERY_STATUS = "deliverystatus"; //Pending/Delivered
    public static final String PARCEL_DELIVERY_COMMENT = "deliverycomments";
    public static final String PARCEL_PAYMENT_TYPE = "payment_type";     //Prepaid/PostPaid
    public static final String PARCEL_PAYMENT_STATUS = "payment_status";
    public static final String PARCEL_TYPE = "parcel_type";               // BAG/Box,Letter
    public static final String PARCEL_AMOUNT = "amount";
    public static final String PARCEL_CURRENCY = "currency";
    public static final String PARCEL_DATE = "date";

    public static final String SOURCE_ADDRESS1 = "source_address1";
    public static final String SOURCE_ADDRESS2 = "source_address2";
    public static final String SOURCE_CITY = "source_city";
    public static final String SOURCE_STATE = "source_state";
    public static final String SOURCE_PIN = "source_pin";
    public static final String SOURCE_PHONE = "source_phone";

    public static final String DELIVERY_ADDRESS1 = "delivery_address1";
    public static final String DELIVERY_ADDRESS2 = "delivery_address2";
    public static final String DELIVERY_CITY = "delivery_city";
    public static final String DELIVERY_STATE = "delivery_state";
    public static final String DELIVERY_PIN = "delivery_pin";
    public static final String DELIVERY_PHONE = "delivery_phone";

    public static final String UPDATE_STATUS = "update_status";
    public static final String UPDATE_DATE = "update_date";
    public static final String TRANSCROWID = "trans_row_id";

    /*===========POD TABLE============================*/
    public static final String TABLE_NAME_POD = "PROOF_OF_DELIVERY";

    public static final String POD_ROW_ID = "pod_id";
    public static final String POD_NAME = "pod_name";
    public static final String POD_STATUS = "pod_status";
    public static final String POD_CREATED_TIME = "pod_created_time";
    public static final String POD_UPDATED_TIME = "pod_updated_time";
    public static final String POD_NAME_ON_SERVER = "pod_name_on_server";

    /*============Transaction Table==========*/
    public static final String TABLE_NAME_TRANSACTION = "TRANSCTABLE";

    public static final String TRANS_ID = "transId";
    public static final String TRANS_TYPE = "transType";
    public static final String TRANS_TIME_STAMP = "transTimeStamp";
    public static final String TRANS_RECEIVER_NAME = "receiver_name";
    public static final String TRANS_NATIONAL_ID = "national_id";
    public static final String TRANS_TOTAL_AMOUNT = "total_amt";
    public static final String TRANS_CURRENCY = "currency";

    /*============Status Table==========*/
    public static final String TABLE_NAME_STATUS = "STATUS";

    public static final String STATUS_TYPE = "statusType"; //Pending/Delivered/Closed
    public static final String STATUS_COMMENT = "statusComment";
    public static final String STATUS_TIME_STAMP = "statusTimeStamp";


    /*=================PickUp Data Table=====================*/
    public static final String TABLE_NAME_PICKUP_DATA = "Pickup_Data";

    public static final String CUSTOMER_ID = "customer_id";
    public static final String USER_FNAME = "user_fname";
    public static final String USER_LNAME = "user_lname";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_PHONE = "user_phone";
    public static final String USER_lANDLINE = "user_landline";
    public static final String USER_EXTENSION = "user_extension";
    public static final String PARCEL_PICKUP_STATUS = "pickup_status"; //Pending/pickedup

    /*=================Address=====================*/


    /*===================Pickup Address==========================*/

    public static final String DELIVERY_ADDRESS_FNAME = "delivery_add_fname";
    public static final String DELIVERY_ADDRESS_LNAME = "delivery_add_lname";
    public static final String DELIVERY_ADDRESS_EMAIL = "delivery_add_email";
    public static final String DELIVERY_ADDRESS_PHONE = "delivery_add_phone";
    public static final String DELIVERY_ADDRESS_lANDLINE = "delivery_add_landline";
    public static final String DELIVERY_ADDRESS_EXTENSION = "delivery_add_extension";
    public static final String DELIVERY_ADDRESS_ADDRESS1 = "delivery_add_address1";
    public static final String DELIVERY_ADDRESS_ADDRESS2 = "delivery_add_address2";
    public static final String DELIVERY_ADDRESS_COUNTRY = "delivery_add_country";
    public static final String DELIVERY_ADDRESS_STATE = "delivery_add_state";
    public static final String DELIVERY_ADDRESS_CITY = "delivery_add_city";
    public static final String DELIVERY_ADDRESS_ZIP_CODE = "delivery_add_zip_code";

    /*===================Delivery Address==========================*/

    public static final String PICKUP_ADD_FNAME = "pickup_add_fname";
    public static final String PICKUP_ADD_LNAME = "pickup_add_lname";
    public static final String PICKUP_ADD_EMAIL = "pickup_add_email";
    public static final String PICKUP_ADD_PHONE = "pickup_add_phone";
    public static final String PICKUP_ADD_lANDLINE = "pickup_add_landline";
    public static final String PICKUP_ADD_EXTENSION = "pickup_add_extension";
    public static final String PICKUP_ADD_ADDRESS1 = "pickup_add_address1";
    public static final String PICKUP_ADD_ADDRESS2 = "pickup_add_address2";
    public static final String PICKUP_ADD_COUNTRY = "pickup_add_country";
    public static final String PICKUP_ADD_STATE = "pickup_add_state";
    public static final String PICKUP_ADD_CITY = "pickup_add_city";
    public static final String PICKUP_ADD_ZIP_CODE = "pickup_add_zip_code";

    /*=================Parcel Detail Data=====================*/

    public static final String PARCEL_WIDTH = "parcel_width";
    public static final String PARCEL_HEIGHT= "parcel_height";
    public static final String PARCEL_LENGTH = "parcel_length";
    public static final String PARCEL_VOLUME_WEIGHT = "parcel_volume_weight";
    public static final String PARCEL_ACTUAL_WEIGHT = "parcel_actual_weight";
    public static final String PARCEL_PRICE = "parcel_price";
    public static final String PARCEL_SPECIAL_INS = "parcel_special_instructions";
    public static final String PARCEL_DESCRIPTION = "parcel_description";
    public static final String PARCEL_ASSIGN_DATE = "parcel_assign_date";
    public static final String PARCEL_CREATED_ON = "parcel_created_on";
    public static final String PARCEL_PICKUP_COMMENT = "parcel_pickup_comment";



    //Put Barcode in each table






}
