package com.inerun.courier.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.inerun.courier.constant.AppConstant;
import com.inerun.courier.data.ParcelListingData;
import com.inerun.courier.data.ParcelStatus;
import com.inerun.courier.data.PickupParcelData;
import com.inerun.courier.data.Shipment;
import com.inerun.courier.helper.DIHelper;

import java.util.ArrayList;

/**
 * Created by vineet on 12/1/2016.
 */

public class ShipmentDao {

    protected Context mContext;
    protected SQLiteDatabase mSQLiteDatabase;
    protected SQLiteStatement mInsertStatement;
    private OpenHelper lOpenHelper;


    public ShipmentDao(Context mContext) {
        this.mContext = mContext;
        closeDatabase();
        lOpenHelper = new OpenHelper(mContext);
        mSQLiteDatabase = lOpenHelper.getWritableDatabase();

    }

    public void insertMultipleShipments(String multiplevalues) {
        String parcelmasterquery = " INSERT INTO " + DataUtils.TABLE_NAME_SHIPMENT_DATA + getShipmentColumns() + multiplevalues + " ;";
        mSQLiteDatabase = lOpenHelper.getWritableDatabase();
        //Log.i("StatusDao","Deleting Table"+  DataUtils.TABLE_NAME_STATUS);
//        Log.i("insertDeliveryStatus", "execSQL " + System.currentTimeMillis());
        Log.i("insertMultipleParcels", parcelmasterquery);
        mSQLiteDatabase.execSQL(parcelmasterquery);
        closeDatabase();

    }


    // code to get all Pending Parcels for Pickup Listing
    public ArrayList<Shipment.ShipmentDetail> getPickupShipmentDetailForListing(String barcode) {
        ArrayList<Shipment.ShipmentDetail> list = new ArrayList<>();

        // Select All Query
//        String selectQuery = "SELECT  * FROM " + DataUtils.TABLE_NAME_DELIVERY;
//        String selectQuery = "SELECT * FROM " + DataUtils.TABLE_NAME_PICKUP_DATA + " WHERE " + DataUtils.PARCEL_DELIVERY_STATUS + " != " + AppConstant.StatusKeys.DELIVERED_STATUS;
        String selectQuery = "SELECT * FROM " + DataUtils.TABLE_NAME_SHIPMENT_DATA+ " WHERE " + DataUtils.PARCEL_BARCODE + " = '" + barcode +"'";
//        String selectQuery = "SELECT * FROM " + DataUtils.TABLE_NAME_SHIPMENT_DATA;
        Log.i("getPendingParcelForList", selectQuery);

        mSQLiteDatabase = lOpenHelper.getWritableDatabase();
        Cursor cursor = mSQLiteDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Log.i("SHIPMENT", "" + cursor.getString(1) + "       " + cursor.getString(2) + "       " + cursor.getString(3) + "       " + cursor.getString(4) + "       " + cursor.getString(5) + "       " + cursor.getString(6) + "       " + cursor.getString(7) + "       " + cursor.getString(8) + "       " + cursor.getString(9) + "       " + cursor.getString(10) + "       " + cursor.getString(11) + "       " + cursor.getString(12) + "       " + cursor.getString(13));

                Shipment.ShipmentDetail shipmentDetail = new Shipment().new ShipmentDetail(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13));


                list.add(shipmentDetail);
            } while (cursor.moveToNext());
        }
        closeDatabase();

        return list;
    }

//    // code to insert the Delivery Info
//
//
//    public void insertDeliveryStatus(ParcelListingData.ParcelData parcelData, ParcelStatus parcelStatus) {
//        Log.i("insertDeliveryStatus", "start " + System.currentTimeMillis());
//        mSQLiteDatabase = lOpenHelper.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//
//        values.put(DataUtils.STATUS_TYPE, parcelStatus.getStatus_type());
//        values.put(DataUtils.STATUS_COMMENT, parcelStatus.getStatus_comment());
//        values.put(DataUtils.STATUS_TIME_STAMP, parcelStatus.getStatus_timestamp());
//        values.put(DataUtils.PARCEL_BARCODE, parcelData.getBarcode());
//        values.put(DataUtils.UPDATE_STATUS, 1);
////        values.put(DataUtils.POD_RECEIVER_NAME, pod.getReceiverName() );
//
//        //Log.i("values", values.toString());
//
////        // Inserting Row
//        mSQLiteDatabase.insert(DataUtils.TABLE_NAME_STATUS, null, values);
//
//        //2nd argument is String containing nullColumnHack
//        mSQLiteDatabase.close(); // Closing database connection
//        Log.i("insertDeliveryStatus", "end " + System.currentTimeMillis());
//    }

//    public void insertMultipleDeliveryStatus(ParcelListingData.ParcelData parcelData, ArrayList<ParcelStatus> parcelStatus) {
////        Log.i("insertDeliveryStatus", "start " + System.currentTimeMillis());
//        String query = " INSERT INTO " + DataUtils.TABLE_NAME_STATUS + "(" + DataUtils.STATUS_TYPE + "," + DataUtils.STATUS_COMMENT + "," + DataUtils.STATUS_TIME_STAMP + "," + DataUtils.PARCEL_BARCODE + ") VALUES ";
//        for (int i=0;i<parcelStatus.size();i++) {
//            ParcelStatus status = parcelStatus.get(i);
//            query+="('"+status.getStatus_type()+"','"+status.getStatus_comment()+"','"+status.getStatus_timestamp()+"','"+parcelData.getBarcode()+ "')";
//            if(i+1!=parcelStatus.size()) {
//                query+=",";
//            }
//
//        }
////        Log.i("INSERTMultipleDelivery", query);
//        mSQLiteDatabase = lOpenHelper.getWritableDatabase();
//        //Log.i("StatusDao","Deleting Table"+  DataUtils.TABLE_NAME_STATUS);
////        Log.i("insertDeliveryStatus", "execSQL " + System.currentTimeMillis());
//        mSQLiteDatabase.execSQL(query);
////        Log.i("insertDeliveryStatus", "end " + System.currentTimeMillis());
//
//    }

//    public void insertMultipleDeliveryStatus(PickupParcelData pickupParcelData, ArrayList<ParcelStatus> parcelStatus) {
////        Log.i("insertDeliveryStatus", "start " + System.currentTimeMillis());
//        String query = " INSERT INTO " + DataUtils.TABLE_NAME_STATUS + "(" + DataUtils.STATUS_TYPE + "," + DataUtils.STATUS_COMMENT + "," + DataUtils.STATUS_TIME_STAMP + "," + DataUtils.PARCEL_BARCODE + ") VALUES ";
//        for (int i=0;i<parcelStatus.size();i++) {
//            ParcelStatus status = parcelStatus.get(i);
//            query+="('"+status.getStatus_type()+"','"+status.getStatus_comment()+"','"+status.getStatus_timestamp()+"','"+pickupParcelData.getParcel_barcode()+ "')";
//            if(i+1!=parcelStatus.size()) {
//                query+=",";
//            }
//
//        }
////        Log.i("INSERTMultipleDelivery", query);
//        mSQLiteDatabase = lOpenHelper.getWritableDatabase();
//        //Log.i("StatusDao","Deleting Table"+  DataUtils.TABLE_NAME_STATUS);
////        Log.i("insertDeliveryStatus", "execSQL " + System.currentTimeMillis());
//        mSQLiteDatabase.execSQL(query);
////        Log.i("insertDeliveryStatus", "end " + System.currentTimeMillis());
//
//    }


//    // code to update the single POD STATUS
//    public int updatePODStatus(int id, String podNameOnServer, int status) {
//
//        mSQLiteDatabase = lOpenHelper.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(DataUtils.POD_STATUS, status);
//        values.put(DataUtils.POD_UPDATED_TIME, DIHelper.getDateTime(AppConstant.DATEIME_FORMAT));
//
//        if (podNameOnServer != null && podNameOnServer.trim().length() > 0) {
//            values.put(DataUtils.POD_NAME_ON_SERVER, podNameOnServer);
//        }
//        // updating row
//        int value = mSQLiteDatabase.update(DataUtils.TABLE_NAME_POD, values, DataUtils.POD_ROW_ID + " = " + id, null);
//
//        mSQLiteDatabase.close();
//
//        return value;
//
//    }

    public void closeDatabase() {
        if ((mSQLiteDatabase != null) && mSQLiteDatabase.isOpen()) {
            mSQLiteDatabase.close();
        }
    }

//    public ArrayList<ParcelStatus> getStatusById(Context context, String barcode) {
//
//        ArrayList<ParcelStatus> list = new ArrayList<>();
//
//        // Select All Query
//        String selectQuery = "SELECT  *  FROM " + DataUtils.TABLE_NAME_STATUS + " WHERE " + DataUtils.PARCEL_BARCODE + " = '" + barcode.toString() + "' AND " + DataUtils.UPDATE_STATUS + " = 1" ;
//        //Log.i("getStatusById", selectQuery);
//
//        mSQLiteDatabase = lOpenHelper.getWritableDatabase();
//        Cursor cursor = mSQLiteDatabase.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//
//                ParcelStatus statusData = new ParcelStatus(cursor.getString(1), cursor.getString(2), cursor.getString(4), cursor.getString(3));
////                ParcelListingData.ParcelData v = p.new ParcelData(cursor.getString(1),cursor.getString(2),Integer.parseInt(cursor.getString(3)),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10),cursor.getString(11),cursor.getString(12));
//
//                list.add(statusData);
//            } while (cursor.moveToNext());
//        }
//        //Log.i("getStatusById",""+ list.size());
//
//
//        return list;
//    }



    private String getShipmentColumns() {

        String columns = "(" + DataUtils.SHIPMENT_BOX_ID
                + "," + DataUtils.SHIP_PARCEL_COUNT + ","
                + DataUtils.SHIP_PARCEL_LENGTH + ","
                + DataUtils.SHIP_PARCEL_BREATH + ","
                + DataUtils.SHIP_PARCEL_HEIGHT + ","
                + DataUtils.SHIP_VOL_WEIGHT + ","
                + DataUtils.SHIP_GROSS_WEIGHT + ","
                + DataUtils.TYPE + "  ,"                     //8
                + DataUtils.CHARGEABLE_WEIGHT + "  ,"        //9
                + DataUtils.DECLARED_VALUE + "  ,"          //10
                + DataUtils.STATUS + "  ,"                  //11
                + DataUtils.SHIPMENT_CREATED_ON + ","
                + DataUtils.PARCEL_BARCODE + ","
                + DataUtils.SHIPMENT_updated_on + " "

                + ") VALUES ";
        return columns;
    }



    // Deleting Table Transaction
    public void deleteStatusTable() {
        mSQLiteDatabase = lOpenHelper.getWritableDatabase();
        //Log.i("StatusDao","Deleting Table"+  DataUtils.TABLE_NAME_STATUS);

        mSQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DataUtils.TABLE_NAME_SHIPMENT_DATA);

        lOpenHelper.onCreate(mSQLiteDatabase);

        closeDatabase();
    }
}