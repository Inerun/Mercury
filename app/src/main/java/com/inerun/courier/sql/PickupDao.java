package com.inerun.courier.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.inerun.courier.constant.AppConstant;
import com.inerun.courier.data.ParcelStatus;
import com.inerun.courier.data.Payment;
import com.inerun.courier.data.PickParcelDetailData;
import com.inerun.courier.data.PickupAddress;
import com.inerun.courier.data.PickupParcelData;
import com.inerun.courier.data.Services;
import com.inerun.courier.data.Shipment;
import com.inerun.courier.data.UpdatedPickupParcelData;
import com.inerun.courier.helper.DIHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vineet on 10/5/2017.
 */

public class PickupDao {
    protected Context mContext;
    protected SQLiteDatabase mSQLiteDatabase;
    protected SQLiteStatement mInsertStatement;
    private OpenHelper lOpenHelper;

    public PickupDao(Context mContext) {
        this.mContext = mContext;
        closeDatabase();
        lOpenHelper = new OpenHelper(mContext);
        mSQLiteDatabase = lOpenHelper.getWritableDatabase();
    }

    public void insertMultipleParcels(String multiplevalues) {
        String parcelmasterquery = " INSERT INTO " + DataUtils.TABLE_NAME_PICKUP_DATA + getParcelColumns() + multiplevalues + " ;";
        mSQLiteDatabase = lOpenHelper.getWritableDatabase();
        //Log.i("StatusDao","Deleting Table"+  DataUtils.TABLE_NAME_STATUS);
//        Log.i("insertDeliveryStatus", "execSQL " + System.currentTimeMillis());
        mSQLiteDatabase.execSQL(parcelmasterquery);
        closeDatabase();

    }

    // code to get count of all Pending Parcels for pickup
    public int getPickupPendingParcelCount() {

        String selectQuery = "SELECT count(*) FROM " + DataUtils.TABLE_NAME_PICKUP_DATA + " WHERE " + DataUtils.PARCEL_PICKUP_STATUS + " != " + AppConstant.StatusKeys.PICKEDUP_STATUS;
//        String selectQuery = "SELECT count(*) FROM " + DataUtils.TABLE_NAME_PICKUP_DATA ;

        //Log.i("getPendingParcelCount", selectQuery);

        mSQLiteDatabase = lOpenHelper.getWritableDatabase();
        Cursor cursor = mSQLiteDatabase.rawQuery(selectQuery, null);

        int count = 0;
        if (null != cursor) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                count = cursor.getInt(0);
            }
            cursor.close();
        }

        closeDatabase();

        return count;
    }

    // code to get count of all Pending Parcels for pickup
    public int getPickupReceivedParcelCount() {

        String selectQuery = "SELECT count(*) FROM " + DataUtils.TABLE_NAME_PICKUP_DATA + " WHERE " + DataUtils.PARCEL_PICKUP_STATUS + " = " + AppConstant.StatusKeys.PICKEDUP_STATUS;
//        String selectQuery = "SELECT count(*) FROM " + DataUtils.TABLE_NAME_PICKUP_DATA ;

        //Log.i("getPendingParcelCount", selectQuery);

        mSQLiteDatabase = lOpenHelper.getWritableDatabase();
        Cursor cursor = mSQLiteDatabase.rawQuery(selectQuery, null);

        int count = 0;
        if (null != cursor) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                count = cursor.getInt(0);
            }
            cursor.close();
        }

        closeDatabase();

        return count;
    }

    // code to get all Pending Parcels for Pickup Listing
    public ArrayList<PickupParcelData> getPickupPendingParcelForListing() {
        ArrayList<PickupParcelData> list = new ArrayList<>();

        // Select All Query
//        String selectQuery = "SELECT  * FROM " + DataUtils.TABLE_NAME_DELIVERY;
//        String selectQuery = "SELECT * FROM " + DataUtils.TABLE_NAME_PICKUP_DATA + " WHERE " + DataUtils.PARCEL_DELIVERY_STATUS + " != " + AppConstant.StatusKeys.DELIVERED_STATUS;
        String selectQuery = "SELECT * FROM " + DataUtils.TABLE_NAME_PICKUP_DATA + " WHERE " + DataUtils.PARCEL_PICKUP_STATUS + " != " + AppConstant.StatusKeys.PICKEDUP_STATUS;
        //Log.i("getPendingParcelForList", selectQuery);

        mSQLiteDatabase = lOpenHelper.getWritableDatabase();
        Cursor cursor = mSQLiteDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //Log.i("Parcel_ID", "" + cursor.getInt(0));

                PickParcelDetailData pickParcelDetailData = new PickParcelDetailData(cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15), cursor.getString(16), cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20));
                PickupAddress pickupAddress = new PickupAddress(cursor.getString(21), cursor.getString(22), cursor.getString(23), cursor.getString(24), cursor.getString(25), cursor.getString(26), cursor.getString(27), cursor.getString(28), cursor.getString(29), cursor.getString(30), cursor.getString(31), cursor.getString(32), cursor.getString(33), cursor.getString(34));
                PickupAddress pickupDeliveryAddress = new PickupAddress(cursor.getString(33), cursor.getString(34), cursor.getString(35), cursor.getString(36), cursor.getString(37), cursor.getString(38), cursor.getString(39), cursor.getString(40), cursor.getString(41), cursor.getString(42), cursor.getString(43), cursor.getString(43), cursor.getString(47), cursor.getString(48));


                PickupParcelData pickupParcelData = new PickupParcelData(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getInt(9), pickupAddress, pickupDeliveryAddress, pickParcelDetailData);

                list.add(pickupParcelData);
            } while (cursor.moveToNext());
        }

        closeDatabase();

        return list;
    }


    // code to get all Pending Parcels for Pickup Listing
    public ArrayList<PickupParcelData> getCollectionParcelForListing(Context context, boolean isPending) {
        ArrayList<PickupParcelData> list = new ArrayList<>();
        ShipmentDao shipmentDao = new ShipmentDao(context);

        // Select All Query
//        String selectQuery = "SELECT  * FROM " + DataUtils.TABLE_NAME_DELIVERY;
//        String selectQuery = "SELECT * FROM " + DataUtils.TABLE_NAME_PICKUP_DATA + " WHERE " + DataUtils.PARCEL_DELIVERY_STATUS + " != " + AppConstant.StatusKeys.DELIVERED_STATUS;
//        String selectQuery = "SELECT * FROM " + DataUtils.TABLE_NAME_PICKUP_DATA + " WHERE " + DataUtils.PARCEL_PICKUP_STATUS + " != " + AppConstant.StatusKeys.PICKEDUP_STATUS;
        String selectQuery = "";
        if (isPending) {
            selectQuery = "SELECT * FROM " + DataUtils.TABLE_NAME_PICKUP_DATA + " WHERE " + DataUtils.PARCEL_PICKUP_STATUS + " != " + AppConstant.StatusKeys.PICKEDUP_STATUS;
        } else {
            selectQuery = "SELECT * FROM " + DataUtils.TABLE_NAME_PICKUP_DATA + " WHERE " + DataUtils.PARCEL_PICKUP_STATUS + " = " + AppConstant.StatusKeys.PICKEDUP_STATUS;
        }
        //Log.i("getPendingParcelForList", selectQuery);

        mSQLiteDatabase = lOpenHelper.getWritableDatabase();
        Cursor cursor = mSQLiteDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //Log.i("Parcel_ID", "" + cursor.getInt(0));
                PickParcelDetailData pickParcelDetailData = new PickParcelDetailData(cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15), cursor.getString(16), cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20), cursor.getString(55));
                PickupAddress pickupAddress = new PickupAddress(cursor.getString(21), cursor.getString(22), cursor.getString(23), cursor.getString(24), cursor.getString(25), cursor.getString(26), cursor.getString(27), cursor.getString(28), cursor.getString(29), cursor.getString(30), cursor.getString(31), cursor.getString(32), cursor.getString(34), cursor.getString(33));
                PickupAddress pickupDeliveryAddress = new PickupAddress(cursor.getString(35), cursor.getString(36), cursor.getString(37), cursor.getString(38), cursor.getString(39), cursor.getString(40), cursor.getString(41), cursor.getString(42), cursor.getString(45), cursor.getString(44), cursor.getString(43), cursor.getString(46), cursor.getString(48), cursor.getString(47));
                Payment payment = new Payment(cursor.getInt(52));
                Services services = new Services(cursor.getString(49), cursor.getString(50), cursor.getString(51));

                Shipment shipment = new Shipment(cursor.getString(53), cursor.getString(54), shipmentDao.getPickupShipmentDetailForListing(cursor.getString(1)));

                PickupParcelData pickupParcelData = new PickupParcelData(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getInt(9), pickupAddress, pickupDeliveryAddress, pickParcelDetailData, "vendor_name", cursor.getString(56), payment, services, shipment);

                list.add(pickupParcelData);
            } while (cursor.moveToNext());
        }

        closeDatabase();

        return list;
    }


    // code to get all Pending Parcels for Pickup Listing
    public ArrayList<PickupParcelData> getCollectionParcelForListingWithCASHCARD(Context context, boolean isCard) {
        ArrayList<PickupParcelData> list = new ArrayList<>();
        ShipmentDao shipmentDao = new ShipmentDao(context);

        // Select All Query
//        String selectQuery = "SELECT  * FROM " + DataUtils.TABLE_NAME_DELIVERY;
//        String selectQuery = "SELECT * FROM " + DataUtils.TABLE_NAME_PICKUP_DATA + " WHERE " + DataUtils.PARCEL_DELIVERY_STATUS + " != " + AppConstant.StatusKeys.DELIVERED_STATUS;
//        String selectQuery = "SELECT * FROM " + DataUtils.TABLE_NAME_PICKUP_DATA + " WHERE " + DataUtils.PARCEL_PICKUP_STATUS + " != " + AppConstant.StatusKeys.PICKEDUP_STATUS;
        String conditionQuery = DataUtils.PARCEL_PAYMENT_TYPE + " = " + (isCard ? AppConstant.StatusKeys.PAYMENT_CARD :AppConstant.StatusKeys.PAYMENT_CASH);

        String selectQuery = "SELECT * FROM " + DataUtils.TABLE_NAME_PICKUP_DATA + " WHERE " + DataUtils.PARCEL_PICKUP_STATUS + " = " + AppConstant.StatusKeys.PICKEDUP_STATUS + " AND " + conditionQuery ;

//        String selectQuery = "";
//
//        if(isCard) {
//            selectQuery = "SELECT * FROM " + DataUtils.TABLE_NAME_PICKUP_DATA + " WHERE " + DataUtils.PARCEL_PICKUP_STATUS + " = " + AppConstant.StatusKeys.PICKEDUP_STATUS + " AND " + DataUtils.PARCEL_PAYMENT_TYPE + " = " + AppConstant.StatusKeys.PAYMENT_CARD ;
//        }else{
//            selectQuery = "SELECT * FROM " + DataUtils.TABLE_NAME_PICKUP_DATA + " WHERE " + DataUtils.PARCEL_PICKUP_STATUS + " = " + AppConstant.StatusKeys.PICKEDUP_STATUS + " AND " + DataUtils.PARCEL_PAYMENT_TYPE + " = " + AppConstant.StatusKeys.PAYMENT_CASH ;
//        }
        Log.i("getPendingParcelForList", selectQuery);

        mSQLiteDatabase = lOpenHelper.getWritableDatabase();
        Cursor cursor = mSQLiteDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //Log.i("Parcel_ID", "" + cursor.getInt(0));
                PickParcelDetailData pickParcelDetailData = new PickParcelDetailData(cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15), cursor.getString(16), cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20), cursor.getString(55));
                PickupAddress pickupAddress = new PickupAddress(cursor.getString(21), cursor.getString(22), cursor.getString(23), cursor.getString(24), cursor.getString(25), cursor.getString(26), cursor.getString(27), cursor.getString(28), cursor.getString(29), cursor.getString(30), cursor.getString(31), cursor.getString(32), cursor.getString(34), cursor.getString(33));
                PickupAddress pickupDeliveryAddress = new PickupAddress(cursor.getString(35), cursor.getString(36), cursor.getString(37), cursor.getString(38), cursor.getString(39), cursor.getString(40), cursor.getString(41), cursor.getString(42), cursor.getString(45), cursor.getString(44), cursor.getString(43), cursor.getString(46), cursor.getString(48), cursor.getString(47));
                Payment payment = new Payment(cursor.getInt(52));
                Services services = new Services(cursor.getString(49), cursor.getString(50), cursor.getString(51));

                Shipment shipment = new Shipment(cursor.getString(53), cursor.getString(54), shipmentDao.getPickupShipmentDetailForListing(cursor.getString(1)));

                PickupParcelData pickupParcelData = new PickupParcelData(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getInt(9), pickupAddress, pickupDeliveryAddress, pickParcelDetailData, "vendor_name", "awb", payment, services, shipment);

                list.add(pickupParcelData);
            } while (cursor.moveToNext());
        }

        closeDatabase();

        return list;
    }


    // code to get all Pending Parcels for Pickup Listing
    public ArrayList<PickupParcelData> getCollectionParcelForListingWithCASHCARDBoth(Context context) {
        ArrayList<PickupParcelData> list = new ArrayList<>();
        ShipmentDao shipmentDao = new ShipmentDao(context);

        // Select All Query
//        String selectQuery = "SELECT  * FROM " + DataUtils.TABLE_NAME_DELIVERY;
//        String selectQuery = "SELECT * FROM " + DataUtils.TABLE_NAME_PICKUP_DATA + " WHERE " + DataUtils.PARCEL_DELIVERY_STATUS + " != " + AppConstant.StatusKeys.DELIVERED_STATUS;
//        String selectQuery = "SELECT * FROM " + DataUtils.TABLE_NAME_PICKUP_DATA + " WHERE " + DataUtils.PARCEL_PICKUP_STATUS + " != " + AppConstant.StatusKeys.PICKEDUP_STATUS;
        String conditionQuery = "( "+DataUtils.PARCEL_PAYMENT_TYPE + " = " + AppConstant.StatusKeys.PAYMENT_CARD +" OR "+ DataUtils.PARCEL_PAYMENT_TYPE + " = " + AppConstant.StatusKeys.PAYMENT_CASH +" )" ;

        String selectQuery = "SELECT * FROM " + DataUtils.TABLE_NAME_PICKUP_DATA + " WHERE " + DataUtils.PARCEL_PICKUP_STATUS + " = " + AppConstant.StatusKeys.PICKEDUP_STATUS + " AND " + conditionQuery ;

//        String selectQuery = "";
//
//        if(isCard) {
//            selectQuery = "SELECT * FROM " + DataUtils.TABLE_NAME_PICKUP_DATA + " WHERE " + DataUtils.PARCEL_PICKUP_STATUS + " = " + AppConstant.StatusKeys.PICKEDUP_STATUS + " AND " + DataUtils.PARCEL_PAYMENT_TYPE + " = " + AppConstant.StatusKeys.PAYMENT_CARD ;
//        }else{
//            selectQuery = "SELECT * FROM " + DataUtils.TABLE_NAME_PICKUP_DATA + " WHERE " + DataUtils.PARCEL_PICKUP_STATUS + " = " + AppConstant.StatusKeys.PICKEDUP_STATUS + " AND " + DataUtils.PARCEL_PAYMENT_TYPE + " = " + AppConstant.StatusKeys.PAYMENT_CASH ;
//        }
        Log.i("getPendingParcelForList", selectQuery);

        mSQLiteDatabase = lOpenHelper.getWritableDatabase();
        Cursor cursor = mSQLiteDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //Log.i("Parcel_ID", "" + cursor.getInt(0));
                PickParcelDetailData pickParcelDetailData = new PickParcelDetailData(cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15), cursor.getString(16), cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20), cursor.getString(55));
                PickupAddress pickupAddress = new PickupAddress(cursor.getString(21), cursor.getString(22), cursor.getString(23), cursor.getString(24), cursor.getString(25), cursor.getString(26), cursor.getString(27), cursor.getString(28), cursor.getString(29), cursor.getString(30), cursor.getString(31), cursor.getString(32), cursor.getString(34), cursor.getString(33));
                PickupAddress pickupDeliveryAddress = new PickupAddress(cursor.getString(35), cursor.getString(36), cursor.getString(37), cursor.getString(38), cursor.getString(39), cursor.getString(40), cursor.getString(41), cursor.getString(42), cursor.getString(45), cursor.getString(44), cursor.getString(43), cursor.getString(46), cursor.getString(48), cursor.getString(47));
                Payment payment = new Payment(cursor.getInt(52));
                Services services = new Services(cursor.getString(49), cursor.getString(50), cursor.getString(51));

                Shipment shipment = new Shipment(cursor.getString(53), cursor.getString(54), shipmentDao.getPickupShipmentDetailForListing(cursor.getString(1)));

                PickupParcelData pickupParcelData = new PickupParcelData(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getInt(9), pickupAddress, pickupDeliveryAddress, pickParcelDetailData, "vendor_name", "awb", payment, services, shipment);

                list.add(pickupParcelData);
            } while (cursor.moveToNext());
        }

        closeDatabase();

        return list;
    }

    // code to get all Pending Parcels for Pickup Listing
    public ArrayList<PickupParcelData> getPickupPendingParcelForListing(Context context) {
        ArrayList<PickupParcelData> list = new ArrayList<>();
        ShipmentDao shipmentDao = new ShipmentDao(context);

        // Select All Query
//        String selectQuery = "SELECT  * FROM " + DataUtils.TABLE_NAME_DELIVERY;
//        String selectQuery = "SELECT * FROM " + DataUtils.TABLE_NAME_PICKUP_DATA + " WHERE " + DataUtils.PARCEL_DELIVERY_STATUS + " != " + AppConstant.StatusKeys.DELIVERED_STATUS;
        String selectQuery = "SELECT * FROM " + DataUtils.TABLE_NAME_PICKUP_DATA + " WHERE " + DataUtils.PARCEL_PICKUP_STATUS + " != " + AppConstant.StatusKeys.PICKEDUP_STATUS;
        //Log.i("getPendingParcelForList", selectQuery);

        mSQLiteDatabase = lOpenHelper.getWritableDatabase();
        Cursor cursor = mSQLiteDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //Log.i("Parcel_ID", "" + cursor.getInt(0));
                PickParcelDetailData pickParcelDetailData = new PickParcelDetailData(cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15), cursor.getString(16), cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20), cursor.getString(55));
                PickupAddress pickupAddress = new PickupAddress(cursor.getString(21), cursor.getString(22), cursor.getString(23), cursor.getString(24), cursor.getString(25), cursor.getString(26), cursor.getString(27), cursor.getString(28), cursor.getString(29), cursor.getString(30), cursor.getString(31), cursor.getString(32), cursor.getString(34), cursor.getString(33));
                PickupAddress pickupDeliveryAddress = new PickupAddress(cursor.getString(35), cursor.getString(36), cursor.getString(37), cursor.getString(38), cursor.getString(39), cursor.getString(40), cursor.getString(41), cursor.getString(42), cursor.getString(45), cursor.getString(44), cursor.getString(43), cursor.getString(46), cursor.getString(48), cursor.getString(47));
                Payment payment = new Payment(cursor.getInt(52));
                Services services = new Services(cursor.getString(49), cursor.getString(50), cursor.getString(51));

                Shipment shipment = new Shipment(cursor.getString(53), cursor.getString(54), shipmentDao.getPickupShipmentDetailForListing(cursor.getString(1)));

                PickupParcelData pickupParcelData = new PickupParcelData(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getInt(9), pickupAddress, pickupDeliveryAddress, pickParcelDetailData, "vendor_name", "awb", payment, services, shipment);

                list.add(pickupParcelData);
            } while (cursor.moveToNext());
        }

        closeDatabase();

        return list;
    }


    // code to get all Parcels for Pickup Listing
    public ArrayList<PickupParcelData> getCollectionAllParcelForListing(Context context) {
        ArrayList<PickupParcelData> list = new ArrayList<>();
        ShipmentDao shipmentDao = new ShipmentDao(context);

        // Select All Query
//        String selectQuery = "SELECT  * FROM " + DataUtils.TABLE_NAME_DELIVERY;
//        String selectQuery = "SELECT * FROM " + DataUtils.TABLE_NAME_PICKUP_DATA + " WHERE " + DataUtils.PARCEL_DELIVERY_STATUS + " != " + AppConstant.StatusKeys.DELIVERED_STATUS;
//        String selectQuery = "SELECT * FROM " + DataUtils.TABLE_NAME_PICKUP_DATA + " ORDER BY " + DataUtils.PARCEL_PICKUP_STATUS + " DESC";
        String selectQuery = "SELECT * FROM " + DataUtils.TABLE_NAME_PICKUP_DATA + " ORDER BY " + DataUtils.PARCEL_PICKUP_STATUS + " ASC";
        //Log.i("getPendingParcelForList", selectQuery);

        mSQLiteDatabase = lOpenHelper.getWritableDatabase();
        Cursor cursor = mSQLiteDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //Log.i("Parcel_ID", "" + cursor.getInt(0));
                PickParcelDetailData pickParcelDetailData = new PickParcelDetailData(cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15), cursor.getString(16), cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20), cursor.getString(55));
                PickupAddress pickupAddress = new PickupAddress(cursor.getString(21), cursor.getString(22), cursor.getString(23), cursor.getString(24), cursor.getString(25), cursor.getString(26), cursor.getString(27), cursor.getString(28), cursor.getString(29), cursor.getString(30), cursor.getString(31), cursor.getString(32), cursor.getString(34), cursor.getString(33));
                PickupAddress pickupDeliveryAddress = new PickupAddress(cursor.getString(35), cursor.getString(36), cursor.getString(37), cursor.getString(38), cursor.getString(39), cursor.getString(40), cursor.getString(41), cursor.getString(42), cursor.getString(45), cursor.getString(44), cursor.getString(43), cursor.getString(46), cursor.getString(48), cursor.getString(47));
                Payment payment = new Payment(cursor.getInt(52));
                Services services = new Services(cursor.getString(49), cursor.getString(50), cursor.getString(51));

                Shipment shipment = new Shipment(cursor.getString(53), cursor.getString(54), shipmentDao.getPickupShipmentDetailForListing(cursor.getString(1)));

                PickupParcelData pickupParcelData = new PickupParcelData(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getInt(9), pickupAddress, pickupDeliveryAddress, pickParcelDetailData, "vendor_name", cursor.getString(56), payment, services, shipment);

                list.add(pickupParcelData);
            } while (cursor.moveToNext());
        }

        closeDatabase();

        return list;
    }


    // code to get all Parcels for Pickup Listing
    public ArrayList<PickupParcelData> getCollectionDataFromAWB(Context context, String awbStr) {
        ArrayList<PickupParcelData> list = new ArrayList<>();
        ShipmentDao shipmentDao = new ShipmentDao(context);

        // Select All Query
//        String selectQuery = "SELECT  * FROM " + DataUtils.TABLE_NAME_DELIVERY;
//        String selectQuery = "SELECT * FROM " + DataUtils.TABLE_NAME_PICKUP_DATA + " WHERE " + DataUtils.PARCEL_DELIVERY_STATUS + " != " + AppConstant.StatusKeys.DELIVERED_STATUS;
//        String selectQuery = "SELECT * FROM " + DataUtils.TABLE_NAME_PICKUP_DATA + " ORDER BY " + DataUtils.PARCEL_PICKUP_STATUS + " DESC";
        String selectQuery = "SELECT * FROM " + DataUtils.TABLE_NAME_PICKUP_DATA + " WHERE " + DataUtils.PICKUP_AWB + " = '" + awbStr +"'";
        Log.i("CollectionDataFromAWB", selectQuery);

        mSQLiteDatabase = lOpenHelper.getWritableDatabase();
        Cursor cursor = mSQLiteDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //Log.i("Parcel_ID", "" + cursor.getInt(0));
                PickParcelDetailData pickParcelDetailData = new PickParcelDetailData(cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15), cursor.getString(16), cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20), cursor.getString(55));
                PickupAddress pickupAddress = new PickupAddress(cursor.getString(21), cursor.getString(22), cursor.getString(23), cursor.getString(24), cursor.getString(25), cursor.getString(26), cursor.getString(27), cursor.getString(28), cursor.getString(29), cursor.getString(30), cursor.getString(31), cursor.getString(32), cursor.getString(34), cursor.getString(33));
                PickupAddress pickupDeliveryAddress = new PickupAddress(cursor.getString(35), cursor.getString(36), cursor.getString(37), cursor.getString(38), cursor.getString(39), cursor.getString(40), cursor.getString(41), cursor.getString(42), cursor.getString(45), cursor.getString(44), cursor.getString(43), cursor.getString(46), cursor.getString(48), cursor.getString(47));
                Payment payment = new Payment(cursor.getInt(52));
                Services services = new Services(cursor.getString(49), cursor.getString(50), cursor.getString(51));

                Shipment shipment = new Shipment(cursor.getString(53), cursor.getString(54), shipmentDao.getPickupShipmentDetailForListing(cursor.getString(1)));

                PickupParcelData pickupParcelData = new PickupParcelData(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getInt(9), pickupAddress, pickupDeliveryAddress, pickParcelDetailData, "vendor_name", cursor.getString(56), payment, services, shipment);

                list.add(pickupParcelData);
            } while (cursor.moveToNext());
        }

        closeDatabase();

        return list;
    }


    // code to update the Pickup Comment
    public int updatePickupComment(int id, int status, String pickup_comment) {

        mSQLiteDatabase = lOpenHelper.getWritableDatabase();
        //Log.i("id", "" + id);
        //Log.i("delivery_comment", "" + delivery_comment);
        ContentValues values = new ContentValues();
        values.put(DataUtils.PARCEL_PICKUP_STATUS, status);
        values.put(DataUtils.PARCEL_PICKUP_COMMENT, pickup_comment);
        values.put(DataUtils.UPDATE_DATE, DIHelper.getDateTime(AppConstant.DATEIME_FORMAT));
        values.put(DataUtils.UPDATE_STATUS, 1);

        // updating row
        int value = mSQLiteDatabase.update(DataUtils.TABLE_NAME_PICKUP_DATA, values, DataUtils.COLUMN_ID + " = " + id, null);
        //Log.i("values", "" + values.toString());

        closeDatabase();

        return value;

    }

    // code to update the Pickup Comment
    public int updatePickupCommentAndTransactionId(int id, int status, String pickup_comment, String card_transaction_id, String awb) {

        mSQLiteDatabase = lOpenHelper.getWritableDatabase();
        //Log.i("id", "" + id);
        //Log.i("delivery_comment", "" + delivery_comment);
        ContentValues values = new ContentValues();
        values.put(DataUtils.PICKUP_AWB, awb);
        values.put(DataUtils.PAYMENT_CARD_TRANSACTION_ID, card_transaction_id);
        values.put(DataUtils.PARCEL_PICKUP_STATUS, status);
        values.put(DataUtils.PARCEL_PICKUP_COMMENT, pickup_comment);
        values.put(DataUtils.UPDATE_DATE, DIHelper.getDateTime(AppConstant.DATEIME_FORMAT));
        values.put(DataUtils.UPDATE_STATUS, 1);

        // updating row
        int value = mSQLiteDatabase.update(DataUtils.TABLE_NAME_PICKUP_DATA, values, DataUtils.COLUMN_ID + " = " + id, null);
        //Log.i("values", "" + values.toString());

        closeDatabase();

        return value;

    }

    // code to get all Delivery Info for SYNC
    public ArrayList<UpdatedPickupParcelData> getPickupInfoForUpdateAndSYNC(Context context) {
        ArrayList<UpdatedPickupParcelData> list = new ArrayList<>();

        // Select All Query
//        String selectQuery = "SELECT P.*,POD.pod_name_on_server,T.receiver_name, T.national_id FROM PARCEL AS P LEFT JOIN PROOF_OF_DELIVERY AS POD ON P.pod_id=POD.id LEFT JOIN TRANSCTABLE AS T ON T.id==P.trans_row_id WHERE update_status = 1";
        String selectQuery = "SELECT  * FROM " + DataUtils.TABLE_NAME_PICKUP_DATA + " WHERE " + DataUtils.UPDATE_STATUS + " = 1";
        //Log.i("DeliveryForUpdateSYNC", selectQuery);

        mSQLiteDatabase = lOpenHelper.getWritableDatabase();
        Cursor cursor = mSQLiteDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String barcode = cursor.getString(1);

                ArrayList<ParcelStatus> statusDataList = DIDbHelper.getStatusbyParcelId(context, barcode);

//                UpdatedParcelData updatedParcelData = new UpdatedParcelData(barcode, Integer.parseInt(cursor.getString(5)), cursor.getString(6), Integer.parseInt(cursor.getString(8)), cursor.getString(26), cursor.getString(30), cursor.getString(31),statusDataList);
//                UpdatedPickupParcelData updatedParcelData = new UpdatedPickupParcelData(barcode, cursor.getInt(9), cursor.getString(20), cursor.getString(46), statusDataList);
                UpdatedPickupParcelData updatedParcelData = new UpdatedPickupParcelData(barcode, cursor.getInt(9), cursor.getString(20), cursor.getString(58), statusDataList, cursor.getString(55), cursor.getString(56));

                list.add(updatedParcelData);
            } while (cursor.moveToNext());
        }
        closeDatabase();

        return list;
    }

    public int receivedParcel(int column_id, int status, String updateDate) {

        mSQLiteDatabase = lOpenHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DataUtils.PARCEL_PICKUP_STATUS, status);
        values.put(DataUtils.UPDATE_DATE, updateDate);
        values.put(DataUtils.UPDATE_STATUS, 1);

        // updating row
        int value = mSQLiteDatabase.update(DataUtils.TABLE_NAME_PICKUP_DATA, values, DataUtils.COLUMN_ID + " = " + column_id, null);
        //Log.i("values", "" + values.toString());

        closeDatabase();

        return value;

    }


    private String getParcelColumns() {

        String columns = "(" + DataUtils.PARCEL_BARCODE
                + "," + DataUtils.CUSTOMER_ID + ","
                + DataUtils.USER_FNAME + ","
                + DataUtils.USER_LNAME + ","
                + DataUtils.USER_EMAIL + ","
                + DataUtils.USER_PHONE + ","
                + DataUtils.USER_lANDLINE + ","
                + DataUtils.USER_EXTENSION + ","
                + DataUtils.PARCEL_PICKUP_STATUS + ","

                + DataUtils.PARCEL_LENGTH + ","
                + DataUtils.PARCEL_WIDTH + ","
                + DataUtils.PARCEL_HEIGHT + ","
                + DataUtils.PARCEL_VOLUME_WEIGHT + ","
                + DataUtils.PARCEL_ACTUAL_WEIGHT + ","
                + DataUtils.PARCEL_PRICE + ","
                + DataUtils.PARCEL_DESCRIPTION + ","
                + DataUtils.PARCEL_SPECIAL_INS + ","
                + DataUtils.PARCEL_ASSIGN_DATE + ","
                + DataUtils.PARCEL_CREATED_ON + ","
                + DataUtils.PARCEL_PICKUP_COMMENT + ","


                + DataUtils.PICKUP_ADD_FNAME + ","
                + DataUtils.PICKUP_ADD_LNAME + ","
                + DataUtils.PICKUP_ADD_EMAIL + ","
                + DataUtils.PICKUP_ADD_PHONE + ","
                + DataUtils.PICKUP_ADD_lANDLINE + ","
                + DataUtils.PICKUP_ADD_EXTENSION + ","
                + DataUtils.PICKUP_ADD_ADDRESS1 + ","
                + DataUtils.PICKUP_ADD_ADDRESS2 + ","
                + DataUtils.PICKUP_ADD_COUNTRY + ","
                + DataUtils.PICKUP_ADD_STATE + ","
                + DataUtils.PICKUP_ADD_CITY + ","
                + DataUtils.PICKUP_ADD_ZIP_CODE + ","
                + DataUtils.PICKUP_ADD_COMPANY + ","
                + DataUtils.PICKUP_ADD_DEPARTMENT + ","

                + DataUtils.DELIVERY_ADDRESS_FNAME + ","
                + DataUtils.DELIVERY_ADDRESS_LNAME + ","
                + DataUtils.DELIVERY_ADDRESS_EMAIL + ","
                + DataUtils.DELIVERY_ADDRESS_PHONE + ","
                + DataUtils.DELIVERY_ADDRESS_lANDLINE + ","
                + DataUtils.DELIVERY_ADDRESS_EXTENSION + ","
                + DataUtils.DELIVERY_ADDRESS_ADDRESS1 + ","
                + DataUtils.DELIVERY_ADDRESS_ADDRESS2 + ","
                + DataUtils.DELIVERY_ADDRESS_COUNTRY + ","
                + DataUtils.DELIVERY_ADDRESS_STATE + ","
                + DataUtils.DELIVERY_ADDRESS_CITY + ","
                + DataUtils.DELIVERY_ADDRESS_ZIP_CODE + ","
                + DataUtils.DELIVERY_ADDRESS_COMPANY + ","
                + DataUtils.DELIVERY_ADDRESS_DEPARTMENT + ","

                + DataUtils.SERVICE_ID + ","
                + DataUtils.SERVICE_NAME + ","
                + DataUtils.SERVICE_FREIGHT_CHARGES + ","

                + DataUtils.PAYMENT_TYPE + ","


                + DataUtils.SHIPMENT_TOTAL_PIECES + ","
                + DataUtils.SHIPMENT_WEIGHT + ","
                + DataUtils.PAYMENT_CARD_TRANSACTION_ID + ","

                + DataUtils.PICKUP_AWB


                + ") VALUES ";
        return columns;
    }


    public void closeDatabase() {
        if ((mSQLiteDatabase != null) && mSQLiteDatabase.isOpen()) {
            mSQLiteDatabase.close();
        }
    }
}
