package com.inerun.courier.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.inerun.courier.constant.AppConstant;
import com.inerun.courier.data.POD;
import com.inerun.courier.data.ParcelListingData;
import com.inerun.courier.data.TransactionData;
import com.inerun.courier.helper.DIHelper;

import java.util.ArrayList;

/**
 * Created by vineet on 12/1/2016.
 */

public class TranscDao {

    protected Context mContext;
    protected SQLiteDatabase mSQLiteDatabase;
    protected SQLiteStatement mInsertStatement;
    private OpenHelper lOpenHelper;

    public static final int TRANSTYPE_CARD = 0;
    public static final int TRANSTYPE_CASH = 1;
//    public static final int POD_STATUS_FAILED = 2;

    public TranscDao(Context mContext) {
        this.mContext = mContext;
        closeDatabase();
        lOpenHelper = new OpenHelper(mContext);
        mSQLiteDatabase = lOpenHelper.getWritableDatabase();

    }

    // code to insert the Delivery Info
    public long insertTransaction(int transtype, String transid, String receivername, String totalamt, String currency, String nationalId) {

        mSQLiteDatabase = lOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DataUtils.TRANS_ID, transid);
        values.put(DataUtils.TRANS_TYPE, "" + transtype);
        values.put(DataUtils.TRANS_TIME_STAMP, DIHelper.getDateTime(AppConstant.DATEIME_FORMAT));
        values.put(DataUtils.TRANS_RECEIVER_NAME, receivername);
        values.put(DataUtils.TRANS_TOTAL_AMOUNT, totalamt);
        values.put(DataUtils.TRANS_CURRENCY, currency);
        values.put(DataUtils.TRANS_NATIONAL_ID, nationalId);
//        values.put(DataUtils.TR, pod.getReceiverName() );

        Log.i("values", values.toString());

//        // Inserting Row
        long rowid = mSQLiteDatabase.insertOrThrow(DataUtils.TABLE_NAME_TRANSACTION, null, values);

        //2nd argument is String containing nullColumnHack
        closeDatabase();
        return rowid;
        // Closing database connection
    }

    public String getTotalPayment() {
        mSQLiteDatabase = lOpenHelper.getWritableDatabase();
        String selectQuery = "select sum(" + DataUtils.TRANS_TOTAL_AMOUNT + "), " + DataUtils.TRANS_CURRENCY + " from " + DataUtils.TABLE_NAME_TRANSACTION;
        Log.i("selectQuery", "" + selectQuery);
        Cursor c = mSQLiteDatabase.rawQuery(selectQuery, null);

        String amount = "0";
        if (c.moveToFirst()) {
            amount = "" + c.getInt(0);
            String currency = c.getString(1);
            if (currency != null) {
                amount = amount + " " + currency;
            }

        }

        c.close();
        closeDatabase();
        return amount;
    }

    public String getTotalPaymentWithPickUp(float amountVal) {
        mSQLiteDatabase = lOpenHelper.getWritableDatabase();
        String selectQuery = "select sum(" + DataUtils.TRANS_TOTAL_AMOUNT + "), " + DataUtils.TRANS_CURRENCY + " from " + DataUtils.TABLE_NAME_TRANSACTION;
        Log.i("selectQuery", "" + selectQuery);
        Cursor cursor = mSQLiteDatabase.rawQuery(selectQuery, null);

        String amount = "0";
        if (cursor.moveToFirst()) {
            do {

                String currency = cursor.getString(1);
                if (currency != null && currency.length() > 0) {

                    float total = Float.parseFloat(cursor.getString(0)) + amountVal;
                    amount = DIHelper.getValueInDecimal(total) + " " + currency;
                }else{
                    amount = "" + DIHelper.getValueInDecimal(amountVal);
                }


            } while (cursor.moveToNext());

        }

        cursor.close();
        closeDatabase();
        return amount;
    }

    public ArrayList<TransactionData> getInvoices() {
//        String selectQuery = "SELECT T.* , GROUP_CONCAT (P.barcode) FROM TRANSCTABLE AS T LEFT JOIN PARCEL AS P  ON T.id==P.trans_row_id where P.update_status = 1 GROUP BY T.id";
//        String selectQuery = "SELECT T.* , GROUP_CONCAT (P.barcode), GROUP_CONCAT (P.awb) FROM TRANSCTABLE AS T LEFT JOIN PARCEL AS P  ON T.id==P.trans_row_id where P.update_status = 1 GROUP BY T.id"; //with AWB
        String selectQuery = "SELECT T.* , GROUP_CONCAT (P.barcode), GROUP_CONCAT (P.awb) FROM TRANSCTABLE AS T LEFT JOIN PARCEL AS P  ON T.id==P.trans_row_id where P.update_status = 1 and P.mercury_payment_type = 4 GROUP BY T.id";

        ArrayList<TransactionData> list = new ArrayList<>();

        // Select All Query
//        String selectQuery = "SELECT  * FROM " + DataUtils.TABLE_NAME_DELIVERY ;

        Log.i("getAllPendingPODs", selectQuery);

        mSQLiteDatabase = lOpenHelper.getWritableDatabase();
        Cursor cursor = mSQLiteDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Log.i("POD_ROW_ID", "" + cursor.getInt(0));
//                UpdatedParcelData updatedParcelData = new UpdatedParcelData(cursor.getString(1),Integer.parseInt(cursor.getString(5)),cursor.getString(6),Integer.parseInt(cursor.getString(8)),cursor.getString(26));
                boolean iscard = (cursor.getInt(2) == TRANSTYPE_CARD);
                TransactionData transactionData = new TransactionData(iscard, cursor.getString(5), cursor.getString(1), cursor.getString(4), cursor.getString(6), cursor.getString(8), cursor.getString(2), cursor.getString(3), cursor.getString(7), cursor.getString(9));
                list.add(transactionData);
            } while (cursor.moveToNext());
        }
       closeDatabase();

        return list;


    }

    // code to update the single POD STATUS
    public int updatePODStatus(int id, String podNameOnServer, int status) {

        mSQLiteDatabase = lOpenHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DataUtils.POD_STATUS, status);
        values.put(DataUtils.POD_UPDATED_TIME, DIHelper.getDateTime(AppConstant.DATEIME_FORMAT));

        if (podNameOnServer != null && podNameOnServer.trim().length() > 0) {
            values.put(DataUtils.POD_NAME_ON_SERVER, podNameOnServer);
        }
        // updating row
        int value = mSQLiteDatabase.update(DataUtils.TABLE_NAME_POD, values, DataUtils.POD_ROW_ID + " = " + id, null);

        closeDatabase();

        return value;

    }

    public POD getCurrency(int id) {

        POD pod = new POD();
        // Select All Query
        String selectQuery = "SELECT * FROM " + DataUtils.TABLE_NAME_POD + " WHERE " + DataUtils.POD_ROW_ID + " = " + id;
        Log.i("getPODById", selectQuery);

        mSQLiteDatabase = lOpenHelper.getWritableDatabase();
        Cursor cursor = mSQLiteDatabase.rawQuery(selectQuery, null);


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Log.i("POD_ROW_ID", "" + cursor.getInt(0));
                pod = new POD(cursor.getInt(0), cursor.getString(4), cursor.getString(5), cursor.getString(1));

            } while (cursor.moveToNext());
        }
        closeDatabase();

        return pod;
    }

    public void closeDatabase() {
        if ((mSQLiteDatabase != null) && mSQLiteDatabase.isOpen()) {
            mSQLiteDatabase.close();
        }
    }


    public ArrayList<ParcelListingData.ParcelData> getPayments(boolean iscardpayments) {
        ArrayList<ParcelListingData.ParcelData> list = new ArrayList<>();
        String where = DataUtils.TRANS_TYPE + " = " + (iscardpayments ? TRANSTYPE_CARD : TRANSTYPE_CASH);
        String selectQuery = "SELECT T.* ,  P.barcode,P.amount FROM TRANSCTABLE AS T  JOIN PARCEL AS P  ON T.id==P.trans_row_id WHERE " + where;
        mSQLiteDatabase = lOpenHelper.getWritableDatabase();

        Cursor cursor = mSQLiteDatabase.rawQuery(selectQuery, null);
        Log.i("selectQuery", "" + selectQuery);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Log.i("POD_ROW_ID", "" + cursor.getInt(0));
//                UpdatedParcelData updatedParcelData = new UpdatedParcelData(cursor.getString(1),Integer.parseInt(cursor.getString(5)),cursor.getString(6),Integer.parseInt(cursor.getString(8)),cursor.getString(26));
                boolean iscash = (cursor.getInt(2) == TRANSTYPE_CASH);
//                if(iscash)
//                {
                ParcelListingData p = new ParcelListingData();
                String barcode = cursor.getString(8);
                String amount = cursor.getString(9);
                String currency = cursor.getString(6);
                ParcelListingData.ParcelData pp = p.new ParcelData(barcode, amount, currency);
                list.add(pp);
//                }


            } while (cursor.moveToNext());
        }
        closeDatabase();

        return list;

    }

    public String getPaymentsTotal(boolean iscardpayment) {
        String sum = "0";
        String selectQuery = "SELECT SUM(" + DataUtils.TRANS_TOTAL_AMOUNT + ")," + DataUtils.TRANS_CURRENCY + " FROM TRANSCTABLE WHERE transType=" + (iscardpayment ? TRANSTYPE_CARD : TRANSTYPE_CASH);
        mSQLiteDatabase = lOpenHelper.getWritableDatabase();

        Cursor cursor = mSQLiteDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Log.i("SUM", "" + cursor.getString(0));
                Log.i("Currency", "" + cursor.getString(1));
//                UpdatedParcelData updatedParcelData = new UpdatedParcelData(cursor.getString(1),Integer.parseInt(cursor.getString(5)),cursor.getString(6),Integer.parseInt(cursor.getString(8)),cursor.getString(26));

                if (cursor.getString(0) != null && cursor.getString(0).length() > 0) {
                    sum = cursor.getString(0) + " " + cursor.getString(1);
                }


            } while (cursor.moveToNext());
        }
        closeDatabase();
        return sum;

    }

    public String getPaymentsTotal(float amount, boolean iscardpayment) {
        String sum = "0";
        String selectQuery = "SELECT SUM(" + DataUtils.TRANS_TOTAL_AMOUNT + ")," + DataUtils.TRANS_CURRENCY + " FROM TRANSCTABLE WHERE transType=" + (iscardpayment ? TRANSTYPE_CARD : TRANSTYPE_CASH);
        mSQLiteDatabase = lOpenHelper.getWritableDatabase();

        Cursor cursor = mSQLiteDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Log.i("SUM", "" + cursor.getString(0));
                Log.i("Currency", "" + cursor.getString(1));
//                UpdatedParcelData updatedParcelData = new UpdatedParcelData(cursor.getString(1),Integer.parseInt(cursor.getString(5)),cursor.getString(6),Integer.parseInt(cursor.getString(8)),cursor.getString(26));

                if (cursor.getString(0) != null && cursor.getString(0).length() > 0) {

                    float total = Float.parseFloat(cursor.getString(0)) + amount;

                    sum = DIHelper.getValueInDecimal(total) + " " + cursor.getString(1);
//                    sum = cursor.getString(0) + " " + cursor.getString(1);

                } else {
//                    sum = "" + amount;
                    sum = "" + DIHelper.getValueInDecimal(amount);
                }


            } while (cursor.moveToNext());
        }
        closeDatabase();
        return sum;

    }

    public ArrayList<ParcelListingData.ParcelData> getALLPayments() {
        ArrayList<ParcelListingData.ParcelData> list = new ArrayList<>();
        String selectQuery = "SELECT T.* ,  P.barcode,P.amount FROM TRANSCTABLE AS T  JOIN PARCEL AS P  ON T.id==P.trans_row_id ";
        mSQLiteDatabase = lOpenHelper.getWritableDatabase();

        Cursor cursor = mSQLiteDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Log.i("POD_ROW_ID", "" + cursor.getInt(0));
//                UpdatedParcelData updatedParcelData = new UpdatedParcelData(cursor.getString(1),Integer.parseInt(cursor.getString(5)),cursor.getString(6),Integer.parseInt(cursor.getString(8)),cursor.getString(26));
//                boolean iscard=(cursor.getInt(2)==TRANSTYPE_CARD);
//                if(iscard)
//                {
                ParcelListingData p = new ParcelListingData();
                String barcode = cursor.getString(7);
                String amount = cursor.getString(8);
                String currency = cursor.getString(6);
                ParcelListingData.ParcelData pp = p.new ParcelData(barcode, amount, currency);
                list.add(pp);
//                }


            } while (cursor.moveToNext());
        }
        closeDatabase();

        return list;

    }

    // Deleting Table Transaction
    public void deleteTransactionTable() {
        mSQLiteDatabase = lOpenHelper.getWritableDatabase();
        Log.i("TransDao", "Deleting Table" + DataUtils.TABLE_NAME_TRANSACTION);

        mSQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DataUtils.TABLE_NAME_TRANSACTION);

        lOpenHelper.onCreate(mSQLiteDatabase);

        closeDatabase();
    }
}