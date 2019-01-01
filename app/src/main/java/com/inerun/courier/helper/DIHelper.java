package com.inerun.courier.helper;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Environment;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.util.Patterns;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.inerun.courier.R;
import com.inerun.courier.base.BaseActivity;
import com.inerun.courier.constant.AppConstant;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.data.DriverData;
import com.inerun.courier.data.PickupParcelData;
import com.inerun.courier.data.SalesFilterData;
import com.inerun.courier.data.StatusData;
import com.inerun.courier.network.ServiceManager;
import com.inerun.courier.sqldb.AppDatabase;
import com.raizlabs.android.dbflow.config.FlowManager;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by vinay on 23/11/16.
 */

public class DIHelper {
    public static boolean validateLoginData(Context context, String email, String pass) {


        if (email.length() < 1) {
//            Toast.makeText(context, R.string.error_email_field, Toast.LENGTH_LONG).show();
            ((BaseActivity) context).showSnackbar(R.string.error_email_field);
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            Toast.makeText(context, R.string.error_invalid_email_field, Toast.LENGTH_LONG).show();
            ((BaseActivity) context).showSnackbar(R.string.error_invalid_email_field);
            return false;
        }
        if (pass.length() < 1) {
//            Toast.makeText(context, R.string.error_password_field, Toast.LENGTH_LONG).show();
            ((BaseActivity) context).showSnackbar(R.string.error_password_field);
            return false;
        }

        return true;
    }

    public static boolean validateReceiverName(Context context, String receiverName) {
        if (receiverName.length() < 1) {
            Toast.makeText(context, R.string.error_receiver_name, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public static boolean validateComment(Context context, String comment) {
        if (comment == null || comment.trim().length() < 1) {
//            Toast.makeText(context, R.string.error_comment, Toast.LENGTH_LONG).show();
            ((BaseActivity) context).showSnackbar(R.string.error_comment);
            return false;
        }

        return true;
    }

    public static boolean validatePickupComment(Context context, String comment) {
        if (comment == null || comment.trim().length() < 1) {
            Toast.makeText(context, R.string.error_comment, Toast.LENGTH_LONG).show();
//            ((BaseActivity) context).showSnackbar(R.string.error_comment);
            return false;
        }

        return true;
    }

    public static boolean getStatus(JSONObject json) throws JSONException {
        if (json.has(UrlConstants.KEY_STATUS)) {
            return json.getBoolean(UrlConstants.KEY_STATUS);
        }
        return false;

    }

    public static String getMessage(JSONObject json) throws JSONException {
        if (json.has(UrlConstants.KEY_MESSAGE)) {
            return json.getString(UrlConstants.KEY_MESSAGE);
        }
        return "";
    }

    public static ArrayList<String> getStringArray(){
        ArrayList<String> status = new ArrayList<>();
        status.add("Delivered");
        status.add("Pending");
        status.add("Door Closed");

        return status;
    }

    public static ArrayList<StatusData> getStatusArrayList(){
        ArrayList<StatusData> status = new ArrayList<>();
        StatusData  statusData = new StatusData("0","Receiver not present.");
        status.add(statusData);
        StatusData  statusData1 = new StatusData("1","Door Closed");
        status.add(statusData1);
        StatusData statusData3 = new StatusData("","Select Option");
        status.add(statusData3);

        return status;
    }

    public static ArrayList<StatusData> getPickupStatusArrayList(){
        ArrayList<StatusData> status = new ArrayList<>();
        StatusData  statusData = new StatusData("0","Not present.");
        status.add(statusData);
        StatusData  statusData1 = new StatusData("1","Door Closed");
        status.add(statusData1);
        StatusData statusData3 = new StatusData("","Select Option");
        status.add(statusData3);

        return status;
    }

    public static String getDateTime(String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        return formatter.format(calendar.getTime());

    }



    public static void playBeepSound() {
        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);

    }


    public static boolean validateLoginPickupAddParcel(Context context, PickupParcelData pickupParcelData) {

        if(pickupParcelData.getFname().length() < 1){
            ((BaseActivity) context).showSnackbar(R.string.error_password_field);
            return false;
        }else if(pickupParcelData.getLname().length() < 1){
            ((BaseActivity) context).showSnackbar(R.string.error_password_field);
            return false;
        }else if(pickupParcelData.getLname().length() < 1){
            ((BaseActivity) context).showSnackbar(R.string.error_password_field);
            return false;
        }else if(pickupParcelData.getLname().length() < 1){
            ((BaseActivity) context).showSnackbar(R.string.error_password_field);
            return false;
        }


//        if (email.length() < 1) {
////            Toast.makeText(context, R.string.error_email_field, Toast.LENGTH_LONG).show();
//            ((BaseActivity) context).showSnackbar(R.string.error_email_field);
//            return false;
//        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
////            Toast.makeText(context, R.string.error_invalid_email_field, Toast.LENGTH_LONG).show();
//            ((BaseActivity) context).showSnackbar(R.string.error_invalid_email_field);
//            return false;
//        }
//        if (pass.length() < 1) {
////            Toast.makeText(context, R.string.error_password_field, Toast.LENGTH_LONG).show();
//            ((BaseActivity) context).showSnackbar(R.string.error_password_field);
//            return false;
//        }

        return true;
    }

    public static Gson getGsonInstance() {
        return new GsonBuilder().setDateFormat(AppConstant.DATEFORMAT)


                .registerTypeAdapter(Float.class, new FloatTypeAdapter())
                .registerTypeAdapter(float.class, new FloatTypeAdapter())
                .registerTypeAdapter(Integer.class, new IntegerTypeAdapter())
                .registerTypeAdapter(int.class, new IntegerTypeAdapter())
                .create();
    }


    static class FloatTypeAdapter extends TypeAdapter<Float> {

        @Override
        public Float read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return null;
            }
            String stringValue = reader.nextString();
            try {
                Float value = Float.valueOf(stringValue);
                return value;
            } catch (NumberFormatException e) {
                return null;
            }
        }

        @Override
        public void write(JsonWriter writer, Float value) throws IOException {
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }

    }

    static class IntegerTypeAdapter extends TypeAdapter<Integer> {
        @Override
        public void write(JsonWriter jsonWriter, Integer number) throws IOException {
            if (number == null) {
                jsonWriter.nullValue();
                return;
            }
            jsonWriter.value(number);
        }

        @Override
        public Integer read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }

            try {
                String value = jsonReader.nextString();
                if ("".equals(value)) {
                    return 0;
                }
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {

                throw new JsonSyntaxException(e);
            }
        }
    }

    public static String[] getPaymentModeArray() {

        String[] array = {"Card","Cash","Cheque","Bank Transfer"};

        return array;
    }

    public static void setSimpleText(TextView txtview, String text)
    {
        txtview.setText(text);

    }

    public static void copyDBToAnotherLocation() {

        String sourcePath = FlowManager.getContext().getDatabasePath(AppDatabase.NAME) + ".db";
        File source = new File(sourcePath);

        String destinationPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + AppDatabase.NAME + ".db";
        File destination = new File(destinationPath);
        try {
            Log.i("sourcepath", "" + sourcePath);
            Log.i("file", "" + source.exists());
            Log.i("file", "" + source.canWrite());
            Log.i("file", "" + source.canWrite());
//            destination.createNewFile();
            if(source.exists()) {
                FileUtils.copyFile(source, destination);
            }else{
                Log.i("Not Exist", "File not find");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getMatColor(Context context,String typeColor)
    {
        int returnColor = Color.BLACK;
        int arrayId = context.getResources().getIdentifier("mdcolor_" + typeColor, "array", context.getPackageName());

        if (arrayId != 0)
        {
            TypedArray colors = context.getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.BLACK);
            colors.recycle();
        }
        return returnColor;
    }

    public static List<SalesFilterData> getSalesFilterData(Context context){
        List<SalesFilterData> salesFilterDataList = new ArrayList<>();
        String [] filterNameArray = context.getResources().getStringArray(R.array.sales_filter_array);

        for (int i = 0; i < filterNameArray.length; i++){
            SalesFilterData salesFilterData = new SalesFilterData(i+1,filterNameArray[i]);
            if(i == 0){
                salesFilterData.setSelected(true);
            }
            salesFilterDataList.add(salesFilterData);
        }

        return salesFilterDataList;
    }

    public static String getDateRange(int id){
        String date_range = "";
        Calendar calendar = Calendar.getInstance();
        String todayDate = getDate(calendar.getTime(), ServiceManager.DateRangeFormat);

        if(id == ServiceManager.SalesReportFilter.TODAY){

            date_range = todayDate +"-" + todayDate;

        }else if(id == ServiceManager.SalesReportFilter.YESTERDAY){
            calendar.add(Calendar.DATE, -1);
            String yesterdayDate = getDate(calendar.getTime(), ServiceManager.DateRangeFormat);
            date_range = yesterdayDate + "-" + yesterdayDate;

        }else if(id == ServiceManager.SalesReportFilter.LAST_7_DAYS){
            calendar.add(Calendar.DATE, -6);
            String fromDate = getDate(calendar.getTime(), ServiceManager.DateRangeFormat);
            date_range = fromDate + "-" + todayDate;

        }else if(id == ServiceManager.SalesReportFilter.LAST_30_DAYS){
            calendar.add(Calendar.DATE, -29);
            String fromDate = getDate(calendar.getTime(), ServiceManager.DateRangeFormat);
            date_range = fromDate + "-" + todayDate;

        }else if(id == ServiceManager.SalesReportFilter.THIS_MONTH){
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = 1;
            calendar.set(year, month, day);

            int numOfDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            String fromDate = getDate(calendar.getTime(), ServiceManager.DateRangeFormat);

            calendar.add(Calendar.DAY_OF_MONTH, numOfDaysInMonth-1);
            String toDate = getDate(calendar.getTime(), ServiceManager.DateRangeFormat);

            date_range = fromDate + "-" + toDate;

        }else if(id == ServiceManager.SalesReportFilter.LAST_MONTH){
            calendar.add(Calendar.MONTH, -1);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = 1;
            calendar.set(year, month, day);

            int numOfDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            String fromDate = getDate(calendar.getTime(), ServiceManager.DateRangeFormat);

            calendar.add(Calendar.DAY_OF_MONTH, numOfDaysInMonth-1);
            String toDate = getDate(calendar.getTime(), ServiceManager.DateRangeFormat);

            date_range = fromDate + "-" + toDate;

        }else {
            date_range = todayDate +"-" + todayDate;
        }

        return date_range;
    }


    public static String getDate(Date date, String format){
//        Date date = new Date();
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat(format);
        String strDate = dateFormat.format(date);

        return strDate;
    }



    public static Spanned getBoldLabel(String label, String value){
        String sourceString = "<b><font color=#000000>" + label + "</font></b> " + value;
        return Html.fromHtml(sourceString);
    }

    public static Spanned getBoldLabel(String label){
        String sourceString = "<b><font color=#000000>" + label + "</font></b> ";
        return Html.fromHtml(sourceString);
    }

    public static ArrayList<DriverData> getDriverList() {
        ArrayList<DriverData> list = new ArrayList<>();
        list.add(new DriverData(1,"Suresh"));
        list.add(new DriverData(2,"Neeraj"));
        list.add(new DriverData(3,"Rupesh"));
        list.add(new DriverData(4,"Rupesh"));
        list.add(new DriverData(5,"Rupesh"));

        return list;
    }

    public static String getValueInDecimal(float value){
        DecimalFormat df = new DecimalFormat("0.00");
        try {
            return df.format(value);
        }catch (Exception e){
            e.printStackTrace();
            return ""+value;
        }


    }

    public static String getValueInDecimal(String value){
        DecimalFormat df = new DecimalFormat("0.00");
        try {
            if(value != null && value.length() > 0) {
                float f = Float.parseFloat(value);
                return df.format(f);
            }else
                return df.format(value);
        }catch (Exception e){
            e.printStackTrace();
            return value;
        }


    }
}
