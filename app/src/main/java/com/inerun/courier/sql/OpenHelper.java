package com.inerun.courier.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.inerun.courier.constant.AppConstant;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by vineet on 12/1/16.
 */

public class OpenHelper extends SQLiteOpenHelper {


    public OpenHelper(Context context) {
        super(context, AppConstant.DB_NAME, null,
                AppConstant.DB_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_PARCEL_TABLE);

        db.execSQL(CREATE_POD_TABLE);

        db.execSQL(CREATE_TRANSACTION_TABLE);

        db.execSQL(CREATE_STATUS_TABLE);

        db.execSQL(CREATE_PICKUP_DATA_TABLE);

        db.execSQL(CREATE_SHIPMENT_DATA_TABLE);


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
//        db.execSQL("DROP TABLE IF EXISTS " + DataUtils.CATEGORY_TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + DataUtils.SUBCATEGORY_TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + DataUtils.WISHLIST_TABLE_NAME);

        onCreate(db);
    }

    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();

    }




    public static void validateDatabase(Context mContext, String tablename, String... params) {
        try {


            OpenHelper lOpenHelper = new OpenHelper(mContext);
            SQLiteDatabase db = lOpenHelper.getWritableDatabase();
            db.execSQL(CREATE_PARCEL_TABLE);

            db.execSQL(CREATE_POD_TABLE);

            db.execSQL(CREATE_TRANSACTION_TABLE);

            db.execSQL(CREATE_STATUS_TABLE);

            db.execSQL(CREATE_PICKUP_DATA_TABLE);

            db.execSQL(CREATE_SHIPMENT_DATA_TABLE);

            Cursor cursor = db.query(tablename, new String[]{}, null, null, null, null, null);
            ArrayList<HashMap<String, String>> hashMapArrayList = new ArrayList<>();
            if (params.length % 2 == 0) {
                for (int i = 0; i < params.length; i = i + 2) {
                    HashMap<String, String> hashMap = new HashMap();
                    hashMap.put("key", params[i]);
                    hashMap.put("query", "ALTER TABLE " + tablename + " ADD COLUMN " + params[i + 1]);
                    hashMapArrayList.add(hashMap);
                }

                for (int i = 0; i < hashMapArrayList.size(); i++) {

                    HashMap<String, String> hash = hashMapArrayList.get(i);
                    try {

                        Log.i("Cursor Index", "" + cursor.getColumnIndexOrThrow(hash.get("key")));
                    } catch (Exception e) {
                        Log.i("exception", hash.get("query") + "Doesnt Exists ");

                        db.execSQL(hash.get("query"));


                    }
                }
            } else {
                Log.i("validate db", "params Missing");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Exception", "exception in validating table: " + tablename);
        }

    }

    // ====Create Parcel Table====
    public static String CREATE_PARCEL_TABLE = "CREATE TABLE IF NOT EXISTS " + DataUtils.TABLE_NAME_PARCEL + "("
            + DataUtils.COLUMN_ID + " INTEGER PRIMARY KEY,"             //0
            + DataUtils.PARCEL_BARCODE + " TEXT ,"                      //1
            + DataUtils.CONSIGNEE_NAME + " TEXT ,"                      //2

            + DataUtils.PARCEL_WEIGHT + " INTEGER DEFAULT -1,"          //3
            + DataUtils.PARCEL_SPECIAL_INSTRUCTION + " TEXT ,"          //4
            + DataUtils.PARCEL_DELIVERY_STATUS + " INTEGER DEFAULT -1," //5
            + DataUtils.PARCEL_DELIVERY_COMMENT + " TEXT ,"             //6
            + DataUtils.PARCEL_PAYMENT_TYPE + " INTEGER DEFAULT -1,"    //7
            + DataUtils.PARCEL_PAYMENT_STATUS + " INTEGER DEFAULT -1,"  //8
            + DataUtils.PARCEL_TYPE + " INTEGER DEFAULT -1,"            //9
            + DataUtils.PARCEL_AMOUNT + " INTEGER DEFAULT -1,"          //10
            + DataUtils.PARCEL_CURRENCY + " TEXT ,"                     //11
            + DataUtils.PARCEL_DATE + " TEXT ,"                         //12

            + DataUtils.SOURCE_ADDRESS1 + " TEXT,"                      //13
            + DataUtils.SOURCE_ADDRESS2 + " TEXT,"                      //14
            + DataUtils.SOURCE_CITY + " TEXT,"                          //15
            + DataUtils.SOURCE_STATE + " TEXT,"                         //16
            + DataUtils.SOURCE_PIN + " TEXT ,"                          //17
            + DataUtils.SOURCE_PHONE + " TEXT,"                         //18

            + DataUtils.DELIVERY_ADDRESS1 + " TEXT,"                    //19
            + DataUtils.DELIVERY_ADDRESS2 + " TEXT,"                    //20
            + DataUtils.DELIVERY_CITY + " TEXT,"                        //21
            + DataUtils.DELIVERY_STATE + " TEXT,"                       //22
            + DataUtils.DELIVERY_PIN + " TEXT,"                         //23
            + DataUtils.DELIVERY_PHONE + " TEXT,"                       //24
            + DataUtils.DELIVERY_LANDLINE_NO + " TEXT,"                 //25
            + DataUtils.DELIVERY_EXT + " TEXT,"                         //26
            + DataUtils.DELIVERY_COUNTRY + " TEXT,"                     //27
            + DataUtils.DELIVERY_AWB + " TEXT,"                         //28
            + DataUtils.MERCURY_PAYMENT_TYPE + " INTEGER DEFAULT 0,"    //29

            + DataUtils.UPDATE_STATUS + " INTEGER DEFAULT -1,"          //30
            + DataUtils.UPDATE_DATE + " TEXT ,"                         //31
            + DataUtils.CONSIGNEE_ID + " TEXT ,"                        //32
            + DataUtils.TRANSCROWID + " INTEGER DEFAULT -1 ,"           //33
            + DataUtils.POD_ROW_ID + " INTEGER DEFAULT -1 " +           //34
            ")";


    // =====Create POD Table====
    public static String CREATE_POD_TABLE = "CREATE TABLE IF NOT EXISTS " + DataUtils.TABLE_NAME_POD + "("
            + DataUtils.COLUMN_ID + " INTEGER PRIMARY KEY,"             //0
            + DataUtils.POD_NAME + " TEXT ,"                            //1
            + DataUtils.POD_CREATED_TIME + " TEXT ,"                    //2
            + DataUtils.POD_UPDATED_TIME + " TEXT ,"                    //3
            + DataUtils.POD_NAME_ON_SERVER + " TEXT ,"                  //4
            + DataUtils.POD_STATUS + " INTEGER DEFAULT -1 ,"            //5
            + DataUtils.PARCEL_BARCODE + " TEXT "                       //6
            + ")";


    // ====Create Transaction Table====
    public static String CREATE_TRANSACTION_TABLE = "CREATE TABLE IF NOT EXISTS " + DataUtils.TABLE_NAME_TRANSACTION + "("
            + DataUtils.COLUMN_ID + " INTEGER PRIMARY KEY,"             //0
            + DataUtils.TRANS_ID + " TEXT ,"                            //1
            + DataUtils.TRANS_TYPE + " TEXT ,"                          //2
            + DataUtils.TRANS_TIME_STAMP + " TEXT ,"                    //3
            + DataUtils.TRANS_RECEIVER_NAME + " TEXT ,"                 //4
            + DataUtils.TRANS_TOTAL_AMOUNT + " TEXT ,"                  //5
            + DataUtils.TRANS_CURRENCY + " TEXT ,"                      //6
            + DataUtils.TRANS_NATIONAL_ID + " TEXT "                    //7
            + ")";


    // =====Create Status Table====
    public static String CREATE_STATUS_TABLE = "CREATE TABLE IF NOT EXISTS " + DataUtils.TABLE_NAME_STATUS + "("
            + DataUtils.COLUMN_ID + " INTEGER PRIMARY KEY,"             //0
            + DataUtils.STATUS_TYPE + " TEXT ,"                         //1
            + DataUtils.STATUS_COMMENT + " TEXT ,"                      //2
            + DataUtils.STATUS_TIME_STAMP + " TEXT ,"                   //3
            + DataUtils.PARCEL_BARCODE + " TEXT ,"                      //4
            + DataUtils.UPDATE_STATUS + " INTEGER DEFAULT -1"           //5
            + ")";

    // =====Create Pickup Table====
    public static String CREATE_PICKUP_DATA_TABLE = "CREATE TABLE IF NOT EXISTS " + DataUtils.TABLE_NAME_PICKUP_DATA + "("
            + DataUtils.COLUMN_ID + " INTEGER PRIMARY KEY,"     //0
            + DataUtils.PARCEL_BARCODE + " TEXT ,"              //1
            + DataUtils.CUSTOMER_ID + " INTEGER DEFAULT 0,"     //2
            + DataUtils.USER_FNAME + " TEXT ,"                  //3
            + DataUtils.USER_LNAME + " TEXT ,"                  //4
            + DataUtils.USER_EMAIL + " TEXT ,"                  //5
            + DataUtils.USER_PHONE + " TEXT ,"                  //6
            + DataUtils.USER_lANDLINE + " TEXT ,"               //7
            + DataUtils.USER_EXTENSION + " TEXT ,"              //8
            + DataUtils.PARCEL_PICKUP_STATUS + " INTEGER ,"     //9

            + DataUtils.PARCEL_LENGTH + " REAL ,"               //10
            + DataUtils.PARCEL_WIDTH + " REAL ,"                //11
            + DataUtils.PARCEL_HEIGHT + " REAL ,"               //12
            + DataUtils.PARCEL_VOLUME_WEIGHT + " REAL ,"        //13
            + DataUtils.PARCEL_ACTUAL_WEIGHT + " REAL ,"        //14
            + DataUtils.PARCEL_PRICE + " REAL ,"                //15
            + DataUtils.PARCEL_DESCRIPTION + " TEXT ,"          //16
            + DataUtils.PARCEL_SPECIAL_INS + " TEXT ,"          //17
            + DataUtils.PARCEL_ASSIGN_DATE + " TEXT ,"          //18
            + DataUtils.PARCEL_CREATED_ON + " TEXT ,"           //19
            + DataUtils.PARCEL_PICKUP_COMMENT + " TEXT ,"       //20

            + DataUtils.PICKUP_ADD_FNAME + " TEXT ,"            //21
            + DataUtils.PICKUP_ADD_LNAME + " TEXT ,"            //22
            + DataUtils.PICKUP_ADD_EMAIL + " TEXT ,"            //23
            + DataUtils.PICKUP_ADD_PHONE + " TEXT ,"            //24
            + DataUtils.PICKUP_ADD_lANDLINE + " TEXT ,"         //25
            + DataUtils.PICKUP_ADD_EXTENSION + " TEXT ,"        //26
            + DataUtils.PICKUP_ADD_ADDRESS1 + " TEXT ,"         //27
            + DataUtils.PICKUP_ADD_ADDRESS2 + " TEXT ,"         //28
            + DataUtils.PICKUP_ADD_CITY + " TEXT ,"             //29
            + DataUtils.PICKUP_ADD_STATE + " TEXT ,"            //30
            + DataUtils.PICKUP_ADD_COUNTRY + " TEXT ,"          //31
            + DataUtils.PICKUP_ADD_ZIP_CODE + " TEXT ,"         //32
            + DataUtils.PICKUP_ADD_COMPANY + " TEXT ,"          //33
            + DataUtils.PICKUP_ADD_DEPARTMENT + " TEXT ,"       //34
            + DataUtils.PICKUP_ADD_TAX_ID + " TEXT ,"           //35
            + DataUtils.PICKUP_ADD_HOMEOROFFICE + " INTEGER DEFAULT 1 ,"       //36
            + DataUtils.PICKUP_ADD_WEEKEND_AVAILABLE + " INTEGER DEFAULT 0 ,"  //37

            + DataUtils.DELIVERY_ADDRESS_FNAME + " TEXT ,"      //38
            + DataUtils.DELIVERY_ADDRESS_LNAME + " TEXT ,"      //39
            + DataUtils.DELIVERY_ADDRESS_EMAIL + " TEXT ,"      //40
            + DataUtils.DELIVERY_ADDRESS_PHONE + " TEXT ,"      //41
            + DataUtils.DELIVERY_ADDRESS_lANDLINE + " TEXT ,"   //42
            + DataUtils.DELIVERY_ADDRESS_EXTENSION + " TEXT ,"  //43
            + DataUtils.DELIVERY_ADDRESS_ADDRESS1 + " TEXT ,"   //44
            + DataUtils.DELIVERY_ADDRESS_ADDRESS2 + " TEXT ,"   //45
            + DataUtils.DELIVERY_ADDRESS_CITY + " TEXT ,"       //46
            + DataUtils.DELIVERY_ADDRESS_STATE + " TEXT ,"      //47
            + DataUtils.DELIVERY_ADDRESS_COUNTRY + " TEXT ,"    //48
            + DataUtils.DELIVERY_ADDRESS_ZIP_CODE + " TEXT ,"   //49
            + DataUtils.DELIVERY_ADDRESS_COMPANY + " TEXT ,"    //50
            + DataUtils.DELIVERY_ADDRESS_DEPARTMENT + " TEXT ," //51
            + DataUtils.DELIVERY_ADDRESS_TAX_ID + " TEXT ,"     //52
            + DataUtils.DELIVERY_ADDRESS_HOMEOROFFICE + " INTEGER DEFAULT 1 ," //53
            + DataUtils.DELIVERY_ADDRESS_WEEKEND_AVAILABLE + " INTEGER DEFAULT 0 ," //54
            + DataUtils.DELIVERY_ADDRESS_DELIVERED_GUARD + " INTEGER DEFAULT 0 ," //55

            + DataUtils.SERVICE_ID + " TEXT ,"                  //56
            + DataUtils.SERVICE_NAME + " TEXT ,"                //57
            + DataUtils.SERVICE_FREIGHT_CHARGES + " TEXT ,"     //58

            + DataUtils.PAYMENT_TYPE + " INTEGER ,"             //59

            + DataUtils.SHIPMENT_TOTAL_PIECES + " TEXT ,"       //60
            + DataUtils.SHIPMENT_WEIGHT + " TEXT ,"             //61
            + DataUtils.PAYMENT_CARD_TRANSACTION_ID + " TEXT ," //62

            + DataUtils.PICKUP_AWB + " TEXT ,"                  //63


            + DataUtils.UPDATE_STATUS + " INTEGER DEFAULT -1 ,"  //64
            + DataUtils.UPDATE_DATE + " TEXT ,"                  //65

            + DataUtils.HOLD_FOR_COLLECTION + " TEXT ,"  //59/66
            + DataUtils.INSURANCE_REQUIRED + " TEXT ,"  //60/67
            + DataUtils.SPECIAL_INSTRUCTIONS + " TEXT ,"  //61/68
            + DataUtils.SELECTED_SERVICE + " TEXT ,"   //62/69

            + DataUtils.SHIP_GROSS_WEIGHT + " TEXT ,"       //63/70
            + DataUtils.CHARGEABLE_WEIGHT + " TEXT ," //64/71
            + DataUtils.DECLARED_VALUE + " TEXT ," //65/72

            + DataUtils.INSURANCE_VALUE + " TEXT ,"   //73

            + DataUtils.SHIPMENT_TYPE + " TEXT ,"    //74
            + DataUtils.SHIPMENT_TYPE_TEXT + " TEXT ,"    //75
            + DataUtils.SHIPMENT_PICKUP_TYPE + " TEXT ,"    //76
            + DataUtils.SHIPMENT_COLLECTION_DATE + " TEXT ,"    //77
            + DataUtils.SHIPMENT_SURCHARGE_ID + " TEXT ,"    //78

            + DataUtils.SHIPMENT_SURCHARGE_NAME + " TEXT "   //79






            + ")";

    // =====Create Shipment Table====
    public static String CREATE_SHIPMENT_DATA_TABLE = "CREATE TABLE IF NOT EXISTS " + DataUtils.TABLE_NAME_SHIPMENT_DATA + "("
            + DataUtils.COLUMN_ID + " INTEGER PRIMARY KEY,"   //0
            + DataUtils.SHIPMENT_BOX_ID + " TEXT ,"          //1
            + DataUtils.SHIP_PARCEL_COUNT + " TEXT ,"        //2
            + DataUtils.SHIP_PARCEL_LENGTH + " TEXT ,"       //3
            + DataUtils.SHIP_PARCEL_BREATH + " TEXT ,"       //4
            + DataUtils.SHIP_PARCEL_HEIGHT + " TEXT ,"       //5
            + DataUtils.SHIP_VOL_WEIGHT + " TEXT ,"          //6
            + DataUtils.SHIP_GROSS_WEIGHT + " TEXT ,"        //7
            + DataUtils.TYPE + " TEXT ,"                     //8
            + DataUtils.CHARGEABLE_WEIGHT + " TEXT ,"        //9
            + DataUtils.DECLARED_VALUE + " TEXT ,"          //10
            + DataUtils.STATUS + " TEXT ,"                  //11
            + DataUtils.SHIPMENT_CREATED_ON + " TEXT ,"     //12
            + DataUtils.PARCEL_BARCODE + " TEXT ,"          //13
            + DataUtils.SHIPMENT_updated_on + " TEXT "      //14

            + ")";


}

