package com.inerun.courier.printer;

import android.content.Context;
import android.content.SharedPreferences;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vinay on 2/8/2016.
 */
public class AppConstant {


    public static final String REG_INFO = "DE 162463073";
    public static final String STATUS_OPEN = "Geöffnet";
    public static final String STATUS_CLOSED = "Geschlossen";
    public static final String NAME = "Tigmoo Shop N Ship";
    public static final String WEBSITE = "www.tigmooshopnship.com";
    public static final int LOGO_SIZE = 500;
    public static final int DEFAULT_TIMEOUT = 10 * 1000;
    public static final String READER_ALARM_ACTION ="com.pos.reader.alarm.ACTION" ;
    public static final String ADDRESS = "Plot 6392 Dundudza Chididza Road Longacres";
    public static final String CITY = "Lusaka";
    public static final String TEL = "09712 69390";

    //    {"data":[{"type":"FL","lines":1},{"type":"text","text":"THE STORE 123 (555) 555 â€“ 5555"},{"type":"size","width":2,"height":2},{"type":"barcode","barcode":"01209457","width":2,"height":100}]}
    public static JSONObject getSampleJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", getSampleJsonArray());

        return jsonObject;
    }

    private static JSONArray getSampleJsonArray() throws JSONException {
        JSONArray array = new JSONArray();


        array.put(new JSONObject("type:FL,lines:1"));
        array.put(new JSONObject("type:text,text:THE STORE 123 (555) 555 â€“ 5555"));
        array.put(new JSONObject("type:text,text:STORE DIRECTOR â€“ John Smith"));
        array.put(new JSONObject("type:text,text:\\n"));
        array.put(new JSONObject("type:text,text:7/01/07 16:58 6153 05 0191 134"));
        array.put(new JSONObject("type:text,text:ST# 21 OP# 001 TE# 01 TR# 747"));
        array.put(new JSONObject("type:text,text:400 OHEIDA 3PK SPRINGF  9.99 R"));
        array.put(new JSONObject("type:text,text:410 3 CUP BLK TEAPOT    9.99 R"));
        array.put(new JSONObject("type:text,text:38 CANDYMAKER ASSORT   4.99 R"));
        array.put(new JSONObject("type:text,text:------------------------------"));
        array.put(new JSONObject("type:text,text:SUBTOTAL                160.38"));
        array.put(new JSONObject("type:text,text:TAX                      14.43"));
        array.put(new JSONObject("type:size,width:2,height:2"));
        array.put(new JSONObject("type:text,text:TOTAL    174.81"));
        array.put(new JSONObject("type:size,width:1,height:1"));
        array.put(new JSONObject("type:FL,lines:1"));
        array.put(new JSONObject("type:text,text:CASH                    200.00"));
        array.put(new JSONObject("type:text,text:CHANGE                   25.19"));
        array.put(new JSONObject("type:text,text:------------------------------"));
        array.put(new JSONObject("type:text,text:Purchased item total number"));
        array.put(new JSONObject("type:text,text:Sign Up and Save"));
        array.put(new JSONObject("type:text,text:With Preferred Saving Card"));
        array.put(new JSONObject("type:FL,lines:2"));
        array.put(new JSONObject("type:barcode,barcode:01209457,width:2,height:100"));


        return array;
    }

    public static class Urls {

        public static final String ADMIN_URL = "http://dev.elbtv.de/dussmann/wp-admin";

        //POS URLs
        public static final String POS_LIVE_URL = "http://dev.elbtv.de/dussmann/point-of-sale/friedrichstadt/kasse-1/";
        public static final String POS_POS_URL = "http://dev.elbtv.de/dussmann/point-of-sale/friedrichstadt/pos/";
        public static final String POS_DUSSMANN_URL = "http://dev.elbtv.de/dussmann/point-of-sale/friedrichstadt/dussmann/";

//        public static final String ADMIN_URL="http://dev.elbtv.de/pos/wp-admin";
//        public static final String ADMIN_URL="http://148.251.29.69/dussmann/wp-admin";
        // public static final String ADMIN_URL="http://dev.elbtv.de/pos/admin";
        //	String URL = "http://dev.elbtv.de/pos/";
        //	String URL = "http://192.168.1.27/dussmann/wp-admin";

    }

    public static class Keys {


        public static final String READER_ADDRESS = "readeraddress";
        public static final String READER_USB_ADDRESS = "readerusbaddress";
        public static final String PRINTER_ADDRESS = "printeraddress";
        public static final String T10000 = "10000";


        public static final String Msg = "Msg";


        public static final String server = "server";
        public static final String username = "username";
        public static final String password = "password";
        public static final String path = "path";
        public static final String localfilefullpath = "localfilefullpath";
        public static final String FtpAdminurl = "ftpadminurl";
        public static final String port = "port";
        public static final String FTP_target = "target_ftp";
        public static final String posurl = "posurl";


        public static final String TARGET = "target";
        public static final String LOGO = "logo";
        public static final String PIN = "pin";
        public static final String POS = "pos";
        public static final String WEBSITE = "website";
        public static final String Address = "address";
        public static final String FinalValue = "final_value";
        public static final String TranscValue = "transc_value";
        public static final String ActualValue = "actual_value";
        public static final String Postcode = "postcode";
        public static final String City = "city";
        public static final String Reg_info = "city";
        public static final String FAX = "fax";
        public static final String TEL = "tel";
        public static final String EMAIL = "email";
        public static final String Exception = "exception";
        public static final String POS_CONSTANT = "pos_constant";
        public static final String READER_ACTIVE = "reader_active";
        public static final String LOGS_ACTIVE = "logs_active";
        public static final String FORCE_SWITCHING = "force_switching";
        public static final String PULSE = "pulse";
        public static final String LANG = "lang";
        public static final String SERIES = "series";
        public static final String Qty = "qty";
        public static final String Desc = "desc";
        public static final String Price = "price";
        public static final String Amount = "amount";
        public static final String ActualAmount = "actualamount";
        public static final String TransAmount = "transamt";
        public static final String Transtype = "transtype";
        public static final String Outlet = "outlet";
        public static final String Date = "date";
        public static final String Cash = "cash";
        public static final String Name = "name";
        public static final String Data = "data";
        public static final String Cardid = "cardid";
        public static final String Transid = "transid";
        public static final String FLAG = "flag";
        public static final String Block = "block";
        public static final String Order_num = "order_num";
        public static final String Order_time = "date_time";
        public static final String Order_date = "date";
        public static final String Served_by = "by";
        public static final String Subtotal = "subtotal";
        public static final String Total = "total";
        public static final String vat1 = "vat";
        public static final String vat1_amount = "vat_amount";
        public static final String vat2 = "vat2";
        public static final String vat2_amount = "vat2_amount";
        public static final String bottle_amount = "bottle_amount";
        public static final String bottle_num = "bottle_num";
        public static final String nfcadded = "nfcadded";
        public static final String nfcwithdraw = "nfcwithdraw";
        public static final String zzbonchecksum = "zzbonchecksum";
        public static final String cashdenom = "cashdenom";
        public static final String cash_qty = "cash_qty";
        public static final String cash_total = "cash_total";
        public static final String subtotal_net = "subtotal_net";
        public static final String total_incl_vat = "total_incl_vat";
        public static final String payment_method = "payment_method";
        public static final String Guest = "Guest";
        public static final String SignOn = "Signon";
        public static final String Azubi = "Azubi";
        public static final String cash_given = "cash_given";
        public static final String cash_received = "cash_received";
        public static final String Sales = "sales";
        public static final String Change = "change";
        public static final String Num_items = "num_items";
        public static final String Barcode = "barcode";
        public static final String Items = "items";
        public static final String Type = "type";
        public static final String Beep = "beep";
        public static final String Start = "start";
        public static final String Status = "status";
        public static final String End = "end";
        public static final String Style = "style";
        public static final String Cashier_name = "cashier_name";
        public static final String Customer_name = "customer_name";
        public static final String Customer_email = "customer_email";
        public static final String Customer_phone = "customer_phone";
        public static final String Register_name = "register_name";
        public static final String Discount_Rate = "discount_rate";
        public static final String Discount_value = "discount_value";
        public static final String Outlet_name = "outlet_name";
        public static final String Timestamp = "timestamp";
        public static final String cashmachine_no = "cashmachine_sno";
        public static final String cashmachine_trans = "cashmachine_transactions";
        public static final String cash_qty_sum = "cash_qty_sum";
        public static final String cash_grand_total = "cash_grand_total";
        public static final String Sno = "Sno";
        public static final String s_no = "s_no";
        public static final String Reg_Amount = "register_amount";
        public static final String Drawer_Amount = "drawer_amount";
        public static final String Trans_Amount = "trans_amount";
        public static final String Diff = "diff";
        public static final String Message = "message";
        public static final String Total_Sales = "total_sales";
        public static final String Total_Sales_7 = "total_sales_7";
        public static final String Total_Sales_19 = "total_sales_19";


        public static final String Cash_Sales_7 = "cash_sales_7";
        public static final String Cash_Sales_19 = "cash_sales_19";

        public static final String Nfc_Sales_7 = "nfc_sales_7";
        public static final String Nfc_Sales_19 = "nfc_sales_19";


        public static final String Cancel_Order = "cancel_order";
        public static final String Canceled_Order = "canceled_order";
        public static final String Withdrawal = "withdrawal";
        public static final String Daily_Sum = "daily_sum";
        public static final String Cash_Added = "cash_added";
        public static final String MONTH = "month";
        public static final int TYPE_PRINT_RECEIPT = 1001;
        public static final int TYPE_WELCOME_RECEIPT = 1002;
        public static final int TYPE_OPEN_DRAWER = 1003;
        public static final int TYPE_PRINT_RECEIPT_ONLY = 1004;
        public static final int TYPE_PRINT_LINE = 1005;
        public static final int TYPE_PRINT_CANCEL = 1006;
        public static final int TYPE_PRINT_ADD_CASH = 1007;
        public static final int TYPE_PRINT_WITHDRAW_CASH = 1008;
        public static final int TYPE_PRINT_SUMMARY = 1009;
        public static final int TYPE_PRINT_ACK = 1010;
        public static final int TYPE_PRINT_RFID_RECEIPT_ONLY = 1011;
        public static final int TYPE_PRINT_CASHMACHINE_RECEIPT = 1012;
        public static final int TYPE_PRINT_NFC_ADD_CASH = 1013;
        public static final int TYPE_PRINT_NFC_WITHDRAW_CASH = 1014;
        public static final int TYPE_PRINT_LOGOUT_WITHDRAW_CASH = 1015;

        public static final int TYPE_PRINTER_STATUS = 1101;
        public static final int TYPE_PRINTER_CHANGED = 1102;

        public static final int TYPE_PRINTER_SUCCESS = 111;
        public static final int TYPE_PRINTER_ERROR = 110;
        public static final int TYPE_APP_FORCE_CLOSE = 112;

        public static String card_amount="card_amount";
        public static String cheque_amount="cheque_amount";
        public static String bank_amount="bank_amount";
    }

    public static class SharedPref {
        public static final int DEFAULT_INT = 1001010101;
        public static final String DEFAULT_STRING = "";
        private static final String USER_PREFRENCES_NAME = "shared_pref";


        public static final int SUCCESS = 1;
        public static final int FAILURE_CARD_READY = 2;
        public static final int FAILURE_CARD_NOT_READY = 0;

        public static void saveReaderAddress(Context context, String address) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(Keys.READER_ADDRESS, "" + address);
            editor.commit();
        }

        public static String getReaderAddress(Context context) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            String value = sharedPref.getString(Keys.READER_ADDRESS, AppConstant.SharedPref.DEFAULT_STRING);
            return value;
        }

        public static void saveUSBReaderAddress(Context context, String address) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(Keys.READER_USB_ADDRESS, "" + address);
            editor.commit();
        }

        public static String getUSBReaderAddress(Context context) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            String value = sharedPref.getString(Keys.READER_USB_ADDRESS, AppConstant.SharedPref.DEFAULT_STRING);
            return value;
        }

        public static void savePrinterTarget(Context context, String target) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(Keys.TARGET, "" + target);
            editor.commit();
        }

        public static String getPrinterTarget(Context context) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            String value = sharedPref.getString(Keys.TARGET, "");
            return value;
        }

        public static void savePrinterSeries(Context context, int series) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(Keys.SERIES, series);
            editor.commit();
        }

        public static String getLogo(Context context) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            String value = sharedPref.getString(Keys.LOGO, "");
            return value;
        }

        public static void saveLogo(Context context, String filename) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(Keys.LOGO, filename);
            editor.commit();
        }

        public static int getPrinterSeries(Context context) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            int value = sharedPref.getInt(Keys.SERIES, DEFAULT_INT);
            return value;
        }

        public static void savePrinterLang(Context context, int series) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(Keys.LANG, series);
            editor.commit();
        }

        public static int getPrinterLang(Context context) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            int value = sharedPref.getInt(Keys.LANG, DEFAULT_INT);

            return value;
        }

        public static void savePrinterPin(Context context, int pin) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(Keys.PIN, pin);
            editor.commit();
        }

        public static int getPrinterPin(Context context) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            int value = sharedPref.getInt(Keys.PIN, DEFAULT_INT);
            return value;
        }

        public static void savePos(Context context, String pos) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(Keys.POS, pos);
            editor.commit();
        }

        public static String getPos(Context context) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            String value = sharedPref.getString(Keys.POS, DEFAULT_STRING);
            return value;
        }

        public static void saveExceptionUrl(Context context, String pos) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(Keys.Exception, pos);
            editor.commit();
        }

        public static String getExceptionUrl(Context context) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            String value = sharedPref.getString(Keys.Exception, DEFAULT_STRING);
            return value;
        }

        public static void savePosConstant(Context context, int pos) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(Keys.POS_CONSTANT, pos);
            editor.commit();
        }


        public static int getPosConstant(Context context) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            int value = sharedPref.getInt(Keys.POS_CONSTANT, DEFAULT_INT);
            return value;
        }


        public static void saveReaderActive(Context context, boolean active) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(Keys.READER_ACTIVE, active);
            editor.commit();
        }


        public static boolean isReaderActive(Context context) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            boolean active = sharedPref.getBoolean(Keys.READER_ACTIVE, false);
            return active;
        }

        public static void saveLogsActive(Context context, boolean active) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(Keys.LOGS_ACTIVE, active);
            editor.commit();
        }


        public static boolean isLogsActive(Context context) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            boolean active = sharedPref.getBoolean(Keys.LOGS_ACTIVE, false);
            return active;
        }
    public static void saveForceSwitching(Context context, boolean active) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(Keys.FORCE_SWITCHING, active);
            editor.commit();
        }


        public static boolean isForceSwitchingActive(Context context) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            boolean active = sharedPref.getBoolean(Keys.FORCE_SWITCHING, false);
            return active;
        }


        public static void savePrinterPulse(Context context, int pulse) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(Keys.PULSE, pulse);
            editor.commit();
        }

        public static int getPrinterPulse(Context context) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            int value = sharedPref.getInt(Keys.PULSE, DEFAULT_INT);
            return value;
        }







        public static void saveFtpServer(Context context, String server) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(Keys.server, server);
            editor.commit();
        }

        public static String getFtpServer(Context context) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            String value = sharedPref.getString(Keys.server, DEFAULT_STRING);
            return value;
        }

        public static void saveFtpUsername(Context context, String username) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(Keys.username, username);
            editor.commit();
        }

        public static String getFtpUsername(Context context) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            String value = sharedPref.getString(Keys.username, DEFAULT_STRING);
            return value;
        }

        public static void saveFtpPassword(Context context, String password) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(Keys.password, password);
            editor.commit();
        }

        public static String getFtpPassword(Context context) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            String value = sharedPref.getString(Keys.password, DEFAULT_STRING);
            return value;
        }

        public static void saveFtpPort(Context context, String port) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(Keys.port, port);
            editor.commit();
        }

        public static String getFtpPort(Context context) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            String value = sharedPref.getString(Keys.port, DEFAULT_STRING);
            return value;
        }

        public static void saveFtpPath(Context context, String path) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(Keys.path, path);
            editor.commit();
        }

        public static String getFtpPath(Context context) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            String value = sharedPref.getString(Keys.path, DEFAULT_STRING);
            return value;
        }

        public static void saveFtpLocalFilePath(Context context, String localfilefullpath) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(Keys.localfilefullpath, localfilefullpath);
            editor.commit();
        }

        public static String getFtpLocalFilePath(Context context) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            String value = sharedPref.getString(Keys.localfilefullpath, DEFAULT_STRING);
            return value;
        }
        public static void saveFtpAdminUrl(Context context, String ftpadminurl) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(Keys.FtpAdminurl, ftpadminurl);
            editor.commit();
        }

        public static String getFtpAdminUrl(Context context) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            String value = sharedPref.getString(Keys.FtpAdminurl, DEFAULT_STRING);
            return value;
        }

        public static void saveFtpTargetFile(Context context, String ftptarget) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(Keys.FTP_target, ftptarget);
            editor.commit();
        }

        public static String getFtpTargetFile(Context context) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            String value = sharedPref.getString(Keys.FTP_target, DEFAULT_STRING);
            return value;
        }

        public static void saveFtpPosUrl(Context context, String posurl) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(Keys.posurl, posurl);
            editor.commit();
        }

        public static String getFtpPosUrl(Context context) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    USER_PREFRENCES_NAME,
                    Context.MODE_PRIVATE);
            String value = sharedPref.getString(Keys.posurl, DEFAULT_STRING);
            return value;
        }


    }


//    public class SharedPref {
//        public static final int DEFAULT_INT = 1001010101;
//        public static final String DEFAULT_STRING = "";
//        private static final String USER_PREFRENCES_NAME = "shared_pref";


//        public static class Keys {
//
//        }


    public static class ReaderData {
        public static final String id = "5264196";
        public static final String version = "0";
        public static final String exchangid = "0";
        public static final String locationid = "0";
        public static final String PKZ = "0";
        public static final String currency = "0";
        public static final String max_amt = "9999";
        public static final String checksumb0 = "192";
        public static final String amount = "0000";
        public static final String transcid = "73";
        public static final String reserved = "0";
        public static final String flags = "2";
        public static final String expiry = "311299";
        public static final String checksumb1 = "204";

    }

    public static class ReaderCommands {

        public static final String SAMPLE_WALLET = "50 53 44 00 00 12 34 56 0A 01 00 00 00 27 10 C0 00 00 0D 87 00 00 00 49 00 00 00 02 31 12 50 CC";
        public static final String GET_ID_COMMAND = "FF CA 00 00 00";
        public static final String SUCCESS_STRING = "90 00";
        public static final String FAILURE_STRING = "6300";
        //normal
        public static final String LOAD_AUTH_COMMAND = "FF 82 00 00 06 FF FF FF FF FF FF";

        //        public static final String LOAD_AUTH_COMMAND = "FF 82 00 00 06 7F 07 88 40";
        public static final String AUTH_COMMAND = "FF 88 00 04 60 00";
        //        public static final String AUTH_COMMAND = "FF 86 00 00 05 01 00 04 60 00";
        public static final String READ_COMMAND = "FF B0 00 04 10";


        //new proservices cash machine cards
        public static final String LOAD_AUTH_COMMAND_PRO = "FF 82 00 00 06 51 57 99 D0 5F A7";
        //        public static final String LOAD_AUTH_COMMAND = "FF 82 00 00 06 7F 07 88 40";

        //change here for Moritz's Card
//
//        public static final String AUTH_COMMAND_PRO = "FF 88 00 28 60 00";
        public static final String AUTH_COMMAND_PRO = "FF 88 00 28 61 00";
//        public static final String AUTH_COMMAND_PRO = "FF 88 00 04 61 00";
        //        public static final String AUTH_COMMAND = "FF 86 00 00 05 01 00 04 60 00";
//            public static final String READ_COMMAND_PRO = "FF B0 00 04 20";
//            public static final String UPDATE_COMMAND_PRO = "FF D6 00 04 20 ";
        public static final String READ_COMMAND_PRO = "FF B0 00 28 20";
        public static final String UPDATE_COMMAND_PRO = "FF D6 00 28 20 ";


        public static final String LOAD_AUTH_COMMAND_KEYS = "FF 82 00 00 06 FF FF FF FF FF FF";
        //        public static final String LOAD_AUTH_COMMAND = "FF 82 00 00 06 7F 07 88 40";
//        changed on 12th oct

        //change here for Moritz's Card   changed on 12th may 2017
        public static final String AUTH_COMMAND_KEYS = "FF 88 00 28 60 00";
//        public static final String AUTH_COMMAND_KEYS = "FF 88 00 28 61 00";
        //        public static final String AUTH_COMMAND = "FF 86 00 00 05 01 00 04 60 00";
//changed on 12th oct
//        public static final String UPDATE_COMMAND_KEYS = "FF D6 00 28 10 3A 92 02 37 08 81 78 77 88 00 51 57 99 D0 5F A7";
        public static final String UPDATE_COMMAND_KEYS = "FF D6 00 2B 10 3A 92 02 37 08 81 78 77 88 00 51 57 99 D0 5F A7";

        public static final String RESET_COMMAND_KEYS = "FF D6 00 2B 10 FF FF FF FF FF FF FF 07 80 69 FF FF FF FF FF FF";



        public static final String UPDATE_COMMAND = "FF D6 00 04 10 ";
        public static final String SET_SLEEP_COMMAND = "E0 00 00 48 0";
        public static final String DISABLE_BEEP_COMMAND = "E00000210101100001";
        //            public static final String DISABLE_BEEP_COMMAND = "E00000280100";
        public static final String HEX_VALUE_FORMATTER = "00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00";
        public static final String DEFAULT_VALUE = "0,00";
        //        public static final String ACCESS_BLOCK_KEYS = "00 00 00 00 00 00 787788 FF FF FF FF FF FF";
        public static final String ACCESS_BLOCK_KEYS = " C1 C2 C3 C4 C5 C6 78 77 88 c1 FF FF FF FF FF FF";
        public static final String WRITE_ACCESS_AUTH_COMMAND = "FF 88 00 07 60 00";
        public static final int SLEEP_TIME_60 = 0;
        public static final int SLEEP_TIME_90 = 1;
        public static final int SLEEP_TIME_120 = 2;
        public static final int SLEEP_TIME_180 = 3;
        public static final int WAKEUP_DELAY = ((int) (2.8 * 60)) * 1000;
        //        public static final int WAKEUP_DELAY =  10*1000;
        public static final String MA_RECEIVER = "MA_RECEIVER";

//


        public static final byte[] AUTO_POLLING_START = {(byte) 0xE0, 0x00, 0x00,
                0x40, 0x01};
        public static final byte[] AUTO_POLLING_STOP = {(byte) 0xE0, 0x00, 0x00,
                0x40, 0x00};
        public static final String DEFAULT_1255_MASTER_KEY = "ACR1255U-J1 Auth";


    }


    public static class ServiceStatus {
        public static final int AUTHENTICATION_COMPLETE = 900;
        public static final int AUTHENTICATION_FAILED = 901;
        public static final int AUTHENTICATION_CARD_PRESENT = 911;
        public static final int AUTHENTICATION_CARD_ABSENT = 912;
        public static final int CARD_READING_COMPLETED = 920;
        public static final int ADDING_AMOUNT_COMPLETED = 921;
        public static final int WITHDRAW_AMOUNT_COMPLETED = 922;
        public static final int RESTART_READER_SERVICE = 923;
        public static final int RFID_WALLET_OBJECT = 924;
        public static final int RFID_PAYMENT_SUCCESS = 991;
        public static final int RFID_PAYMENT_FAIL = 990;
        public static final int RFID_RESTART_SERVICE = 992;
        public static final int READER_NOT_READY = 997;

//
        public static final int ADDNFC_CARD_READING_COMPLETED = 980;
        public static final int ADDNFC_RFID_READING_SUCCESS = 981;
        public static final int ADDNFC_RFID_READING_FAIL = 982;

        public static final int ADDNFC_CARD_WRITE_COMPLETED = 983;
        public static final int ADDNFC_RFID_WRITING_SUCCESS = 984;
        public static final int ADDNFC_RFID_WRITING_FAIL = 985;






        public static final int SUBNFC_CARD_READING_COMPLETED = 986;
        public static final int SUBNFC_RFID_PAYMENT_SUCCESS = 987;
        public static final int SUBNFC_RFID_PAYMENT_FAIL = 988;

        public static final int CHECKOUTNFC_CARD_READING_COMPLETED = 989;
        public static final int CHECKOUTNFC_RFID_PAYMENT_SUCCESS = 990;
        public static final int CHECKOUTNFC_RFID_PAYMENT_FAIL = 993;

        public static final int CHECKOUTNFC_CARD_WRITING_COMPLETED = 994;
        public static final int CHECKOUTNFC_WRITING_RFID_PAYMENT_SUCCESS = 995;
        public static final int CHECKOUTNFC_WRITING_RFID_PAYMENT_FAIL = 996;


    }

    public static class IDS {


        public static final int BROADCAST_TYPE_EXCEPTION = 2011;
        public static final int UI_UPDATION = 2001;
        public static final int FTP_UI_UPDATION = 2003;
        public static final int FTP_FILE_DEL_SUCCESS = 2004;
        public static final int SERVICE_STATUS = 2005;
        public static final int ADDSERVICE_STATUS = 2008;
        public static final int SUBSERVICE_STATUS = 2012;
        public static final int CHECKOUTSERVICE_STATUS = 2013;
        public static final int SERVICE_OBJECT = 2006;
        public static final int ID_GET_BATTERY_LEVEL = 2002;
        public static final int SETTINGS_BATTERY_LEVEL = 2007;
        public static final int BLUETOOTH_DISABLED = 2009;
        public final static int READ_DATA_LOAD_AUTH_ID = 1101;
        public final static int READ_DATA_AUTH_CARD = 1102;
        public final static int READ_DATA_BLOCK = 1103;
        public final static int WRITE_DATA_LOAD_AUTH_ID = 1104;
        public final static int WRITE_DATA_AUTH_CARD = 1105;
        public final static int WRITE_DATA_BLOCK = 1106;
        public final static int STOP_POLLING = 1107;
        public final static int SET_SLEEP_TIME = 1108;
        public final static int WRITE_ACCESS_LOAD_AUTH_ID = 1109;
        public final static int WRITE_ACCESS_AUTH_CARD = 1110;
        public final static int WRITE_ACCESS_BLOCK = 1111;
        public final static int POLLING_ID = 101;
        public final static int READ_ID = 102;
        public final static int LOAD_AUTH_ID = 103;
        public final static int AUTH_ID = 104;
        public final static int READ_BLOCK_ID = 105;
        public final static int LOAD_AUTH_ESCAPE_ID = 106;
        public final static int DISABLE_BEEP_ID = 107;

        public final static int POLL_READ_DATA_LOAD_AUTH_ID = 1112;
        public final static int POLL_READ_DATA_AUTH_CARD = 1113;
        public final static int POLL_READ_DATA_BLOCK = 1114;

        public final static int ADD_READ_DATA_LOAD_AUTH_ID = 1201;
        public final static int ADD_READ_DATA_AUTH_CARD = 1202;
        public final static int ADD_READ_DATA_BLOCK = 1203;
        public final static int ADD_READ_DATA_STOP_POLLING = 1204;
        public final static int ADD_WRITE_DATA_LOAD_AUTH_ID = 1205;
        public final static int ADD_WRITE_DATA_AUTH_CARD = 1206;
        public final static int ADD_WRITE_DATA_BLOCK = 1207;
        public final static int ADD_DATA_STOP_POLLING = 1208;


        public final static int WITHDRAW_READ_DATA_LOAD_AUTH_ID = 1301;
        public final static int WITHDRAW_READ_DATA_AUTH_CARD = 1302;
        public final static int WITHDRAW_READ_DATA_BLOCK = 1303;
        public final static int WITHDRAW_READ_DATA_STOP_POLLING = 1304;
        public final static int WITHDRAW_WRITE_DATA_LOAD_AUTH_ID = 1305;
        public final static int WITHDRAW_WRITE_DATA_AUTH_CARD = 1306;
        public final static int WITHDRAW_WRITE_DATA_BLOCK = 1307;
        public final static int WITHDRAW_DATA_STOP_POLLING = 1308;
        public final static int FIRMWARE_REQUEST = 1408;
        public final static int STOP_POLLING_REQUEST = 1500;


        public final static int PAY_READ_DATA_LOAD_AUTH_ID = 1501;
        public final static int PAY_READ_DATA_AUTH_CARD = 1502;
        public final static int PAY_READ_DATA_BLOCK = 1503;
        public final static int PAY_READ_DATA_STOP_POLLING = 1504;
        public final static int PAY_WRITE_DATA_LOAD_AUTH_ID = 1505;
        public final static int PAY_WRITE_DATA_AUTH_CARD = 1506;
        public final static int PAY_WRITE_DATA_BLOCK = 1507;
        public final static int PAY_DATA_STOP_POLLING = 1508;


        public final static int PRO_READ_DATA_LOAD_AUTH_ID = 1601;
        public final static int PRO_READ_DATA_AUTH_CARD = 1602;
        public final static int PRO_READ_DATA_BLOCK = 1603;
        public final static int PRO_READ_DATA_STOP_POLLING = 1604;
        public final static int PRO_WRITE_DATA_LOAD_AUTH_ID = 1605;
        public final static int PRO_WRITE_DATA_AUTH_CARD = 1606;
        public final static int PRO_WRITE_DATA_BLOCK = 1607;


        public final static int PRO_FORMAT_DATA_LOAD_AUTH_ID = 1701;
        public final static int PRO_FORMAT_DATA_AUTH_CARD = 1702;
        public final static int PRO_FORMAT_DATA_BLOCK = 1703;



        public final static int PRO_FORMAT_KEYS_LOAD_AUTH_ID = 2601;
        public final static int PRO_FORMAT_KEYS_AUTH_CARD = 2602;
        public final static int PRO_FORMAT_KEYS_BLOCK = 2603;


        public final static int PRO_WRITE_NFC_LOAD_AUTH_ID = 2701;
        public final static int PRO_WRITE_NFC_AUTH_CARD = 2702;
        public final static int PRO_WRITE_NFC_BLOCK = 2703;




        public final static int PRO_FLAG_READ_DATA_LOAD_AUTH_ID = 2801;
        public final static int PRO_FLAG_READ_DATA_AUTH_CARD = 2802;
        public final static int PRO_FLAG_READ_DATA_BLOCK = 2803;
        public final static int PRO_FLAG_READ_DATA_STOP_POLLING = 2804;
        public final static int PRO_FLAG_WRITE_DATA_LOAD_AUTH_ID = 2805;
        public final static int PRO_FLAG_WRITE_DATA_AUTH_CARD = 2806;

        public final static int PRO_FLAG_WRITE_DATA_BLOCK = 2807;
        public final static int PRO_FLAG_DATA_STOP_POLLING = 2808;







        public final static int AddNfcCardAmount_READ_DATA__LOAD_AUTH_ID = 2901;
        public final static int AddNfcCardAmount_READ_DATA_AUTH_CARD = 2902;
        public final static int AddNfcCardAmount_READ_DATA_BLOCK = 2903;

        public final static int AddNfcCardAmount_WRITE_DATA_LOAD_AUTH_ID = 2905;
        public final static int AddNfcCardAmount_WRITE_DATA_AUTH_CARD = 2906;
        public final static int AddNfcCardAmount_WRITE_DATA_BLOCK = 2907;



        public final static int SubNfcCardAmount_READ_DATA__LOAD_AUTH_ID = 3001;
        public final static int SubNfcCardAmount_READ_DATA_AUTH_CARD = 3002;
        public final static int SubNfcCardAmount_READ_DATA_BLOCK = 3003;

        public final static int SubNfcCardAmount_WRITE_DATA_LOAD_AUTH_ID = 3005;
        public final static int SubNfcCardAmount_WRITE_DATA_AUTH_CARD = 3006;
        public final static int SubNfcCardAmount_WRITE_DATA_BLOCK = 3007;



        public final static int checkoutNfcCardAmount_READ_DATA__LOAD_AUTH_ID = 3101;
        public final static int checkoutNfcCardAmount_READ_DATA_AUTH_CARD = 3102;
        public final static int checkoutNfcCardAmount_READ_DATA_BLOCK = 3103;

        public final static int checkoutNfcCardAmount_WRITE_DATA_LOAD_AUTH_ID = 3105;
        public final static int checkoutNfcCardAmount_WRITE_DATA_AUTH_CARD = 3106;
        public final static int checkoutNfcCardAmount_WRITE_DATA_BLOCK = 3107;


        public final static int PRO_UNBLOCK_READ_DATA_LOAD_AUTH_ID = 3301;
        public final static int PRO_UNBLOCK_READ_DATA_AUTH_CARD = 3302;
        public final static int PRO_UNBLOCK_READ_DATA_BLOCK = 3303;




        public final static int PRO_RESET_KEYS_LOAD_AUTH_ID = 3201;
        public final static int PRO_RESET_KEYS_AUTH_CARD = 3202;
        public final static int PRO_RESET_KEYS_BLOCK = 3203;

        public static final int ID_ALARM_BATTERY_LEVEL = 3204;


        public static final int BATTERY_ALARM_INTENT =2014 ;
    }

}

//}
