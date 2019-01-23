package com.inerun.courier.printer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.inerun.courier.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DateFormatSymbols;
import java.util.ArrayList;

/**
 * Created by vinay on 2/8/2016.
 */
public class EPOSHelper {

    public static final String ROOT_FOLDER_NAME = ".POS";

    public static final String FOLDER = "data";

    private static final String SEPERATOR = File.separator;


    public static void savePrintSettings(Context context, String target, int printerseries, int printerlang, int printerpulse, int printerpin) {

        AppConstant.SharedPref.savePrinterTarget(context, target);
//        AppConstant.SharedPref.saveUSBReaderAddress(context, usbreader);
        AppConstant.SharedPref.savePrinterSeries(context, printerseries);
        AppConstant.SharedPref.savePrinterLang(context, printerlang);
        AppConstant.SharedPref.savePrinterPulse(context, printerpulse);
        AppConstant.SharedPref.savePrinterPin(context, printerpin);
//        AppConstant.SharedPref.savePos(context, posurl);
//        AppConstant.SharedPref.saveExceptionUrl(context, exceptionurl);
//        AppConstant.SharedPref.savePosConstant(context, pos_constant);
//        AppConstant.SharedPref.saveReaderActive(context, readeractive);
//        AppConstant.SharedPref.saveLogsActive(context, logsactive);
//        AppConstant.SharedPref.saveForceSwitching(context, forceprinting);
//        if (readeractive) {
//            AppConstant.SharedPref.saveReaderAddress(context, readerAddress);
//        }
        Toast.makeText(context, R.string.settings_saved, Toast.LENGTH_LONG).show();


    }

    public static boolean isSettingsSaved(Context context) {
        String target = AppConstant.SharedPref.getPrinterTarget(context);
        int series = AppConstant.SharedPref.getPrinterSeries(context);
        int lang = AppConstant.SharedPref.getPrinterLang(context);
        int pulse = AppConstant.SharedPref.getPrinterPulse(context);
        int pin = AppConstant.SharedPref.getPrinterPin(context);
//        int pin = AppConstant.SharedPref.getP(context);

//        if (target == null || target.length() == 0 || series == AppConstant.SharedPref.DEFAULT_INT || lang == AppConstant.SharedPref.DEFAULT_INT || pulse == AppConstant.SharedPref.DEFAULT_INT || pin == AppConstant.SharedPref.DEFAULT_INT) {
//        if (series == AppConstant.SharedPref.DEFAULT_INT || lang == AppConstant.SharedPref.DEFAULT_INT || pulse == AppConstant.SharedPref.DEFAULT_INT || pin == AppConstant.SharedPref.DEFAULT_INT) {
        if (series == AppConstant.SharedPref.DEFAULT_INT || lang == AppConstant.SharedPref.DEFAULT_INT || pulse == AppConstant.SharedPref.DEFAULT_INT || pin == AppConstant.SharedPref.DEFAULT_INT) {
            return false;
        } else {
            return true;
        }
    }

    public static ArrayList<SpnModelsItem> getPrinterPulseList(Context context) {
        ArrayList<SpnModelsItem> list = new ArrayList<>();
        list.add(new SpnModelsItem("100", Printer.PULSE_100));
        list.add(new SpnModelsItem("200", Printer.PULSE_200));
        list.add(new SpnModelsItem("300", Printer.PULSE_300));
        list.add(new SpnModelsItem("400", Printer.PULSE_400));
        list.add(new SpnModelsItem("500", Printer.PULSE_500));
        list.add(new SpnModelsItem("Default", Printer.PARAM_DEFAULT));
        list.add(new SpnModelsItem("Unspecified", Printer.PARAM_UNSPECIFIED));
        return list;
    }


    public static ArrayList<SpnModelsItem> getPrinterPinList(Context context) {
        ArrayList<SpnModelsItem> list = new ArrayList<>();
        list.add(new SpnModelsItem(context.getString(R.string.drawer_2pin), Printer.DRAWER_2PIN));
        list.add(new SpnModelsItem(context.getString(R.string.drawer_5pin), Printer.DRAWER_5PIN));
        list.add(new SpnModelsItem(context.getString(R.string.drawer_high), Printer.DRAWER_HIGH));
        list.add(new SpnModelsItem(context.getString(R.string.drawer_low), Printer.DRAWER_LOW));
        return list;
    }

    public static ArrayList<SpnModelsItem> getPrinterLangList(Context context) {
        ArrayList<SpnModelsItem> list = new ArrayList<>();
        list.add(new SpnModelsItem(context.getString(R.string.lang_ank), Printer.MODEL_ANK));
        list.add(new SpnModelsItem(context.getString(R.string.lang_japanese), Printer.MODEL_JAPANESE));
        list.add(new SpnModelsItem(context.getString(R.string.lang_chinese), Printer.MODEL_CHINESE));
        list.add(new SpnModelsItem(context.getString(R.string.lang_taiwan), Printer.MODEL_TAIWAN));
        list.add(new SpnModelsItem(context.getString(R.string.lang_korean), Printer.MODEL_KOREAN));
        list.add(new SpnModelsItem(context.getString(R.string.lang_thai), Printer.MODEL_THAI));
        list.add(new SpnModelsItem(context.getString(R.string.lang_southasia), Printer.MODEL_SOUTHASIA));
        return list;
    }


    public static ArrayList<SpnModelsItem> getStyles(Context context) {
        ArrayList<SpnModelsItem> list = new ArrayList<>();
        list.add(new SpnModelsItem("Solid Line: Fine", Printer.LINE_THIN));
        list.add(new SpnModelsItem("Solid Line: Middle", Printer.LINE_MEDIUM));
        list.add(new SpnModelsItem("Solid Line: Thick", Printer.LINE_THICK));
        list.add(new SpnModelsItem("Double Line: Fine", Printer.LINE_THICK_DOUBLE));
        list.add(new SpnModelsItem("Double Line: Middle", Printer.LINE_MEDIUM_DOUBLE));
        list.add(new SpnModelsItem("Double Line: Thick", Printer.LINE_THICK_DOUBLE));
        list.add(new SpnModelsItem("Default", Printer.PARAM_DEFAULT));
        return list;
    }


    public static ArrayList<SpnModelsItem> getPrinterSeriesList(Context context) {
        ArrayList<SpnModelsItem> list = new ArrayList<>();
        list.add(new SpnModelsItem(context.getString(R.string.printerseries_m10), Printer.TM_M10));
        list.add(new SpnModelsItem(context.getString(R.string.printerseries_m30), Printer.TM_M30));
        list.add(new SpnModelsItem(context.getString(R.string.printerseries_p20), Printer.TM_P20));
        list.add(new SpnModelsItem(context.getString(R.string.printerseries_p60), Printer.TM_P60));
        list.add(new SpnModelsItem(context.getString(R.string.printerseries_p60ii), Printer.TM_P60II));
        list.add(new SpnModelsItem(context.getString(R.string.printerseries_p80), Printer.TM_P80));
        list.add(new SpnModelsItem(context.getString(R.string.printerseries_t20), Printer.TM_T20));
        list.add(new SpnModelsItem(context.getString(R.string.printerseries_t70), Printer.TM_T70));
        list.add(new SpnModelsItem(context.getString(R.string.printerseries_t81), Printer.TM_T81));
        list.add(new SpnModelsItem(context.getString(R.string.printerseries_t82), Printer.TM_T82));
        list.add(new SpnModelsItem(context.getString(R.string.printerseries_t83), Printer.TM_T83));
        list.add(new SpnModelsItem(context.getString(R.string.printerseries_t88), Printer.TM_T88));
        list.add(new SpnModelsItem(context.getString(R.string.printerseries_t90), Printer.TM_T90));
        list.add(new SpnModelsItem(context.getString(R.string.printerseries_t90kp), Printer.TM_T90KP));
        list.add(new SpnModelsItem(context.getString(R.string.printerseries_u220), Printer.TM_U220));
        list.add(new SpnModelsItem(context.getString(R.string.printerseries_u330), Printer.TM_U330));
        list.add(new SpnModelsItem(context.getString(R.string.printerseries_l90), Printer.TM_L90));
        list.add(new SpnModelsItem(context.getString(R.string.printerseries_h6000), Printer.TM_H6000));
        return list;
    }

    public static boolean createRecieptHeader(Context context, int logo, Printer mPrinter) throws Epos2Exception {

        String method = "";
        Bitmap logoData;
        logoData = BitmapFactory.decodeResource(context.getResources(), logo);
        StringBuilder textData = new StringBuilder();


        if (mPrinter == null) {
            return false;
        }


        method = "addTextAlign";
        mPrinter.addTextAlign(Printer.ALIGN_CENTER);

        method = "addImage";
        mPrinter.addImage(logoData, 0, 0,
                logoData.getWidth(),
                logoData.getHeight(),
                Printer.COLOR_1,
                Printer.MODE_MONO,
                Printer.HALFTONE_DITHER,
                Printer.PARAM_DEFAULT,
                Printer.COMPRESS_AUTO);


////            method = "5 Pin";
//            mPrinter.addPulse(Printer.DRAWER_5PIN, Printer.PULSE_100);


        return true;

    }

    public static boolean addRecieptLogo(Context context, int logo, Printer mPrinter) throws Epos2Exception, Exception {
        if (mPrinter != null) {


            Bitmap logoData;
            ;
            String storage = AppConstant.SharedPref.getLogo(context);
            if (storage != null && storage.length() > 0) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 1;
                logoData = BitmapFactory.decodeFile(storage, options);
            } else {
                logoData = BitmapFactory.decodeResource(context.getResources(), logo);
            }
            mPrinter.addTextAlign(Printer.ALIGN_CENTER);
            if (logoData != null) {
                mPrinter.addImage(logoData, 0, 0,
                        logoData.getWidth(),
                        logoData.getHeight(),
                        Printer.COLOR_1,
                        Printer.MODE_GRAY16,
                        Printer.HALFTONE_DITHER,
                        Printer.PARAM_DEFAULT,
                        Printer.COMPRESS_NONE);
            }
            mPrinter.addFeedLine(1);
        } else {
            return false;
        }
        return true;
    }

    public static boolean addRecieptSmiley(Context context, Printer mPrinter) throws Epos2Exception, Exception {


        Bitmap logoData = BitmapFactory.decodeResource(context.getResources(), R.drawable.smiley);
        mPrinter.addTextAlign(Printer.ALIGN_CENTER);
        mPrinter.addImage(logoData, 0, 0,
                logoData.getWidth(),
                logoData.getHeight(),
                Printer.COLOR_1,
                Printer.MODE_GRAY16,
                Printer.HALFTONE_DITHER,
                Printer.PARAM_DEFAULT,
                Printer.COMPRESS_NONE);

        mPrinter.addFeedLine(1);
        return true;
    }

    public static boolean createPOSRecieptHeader(Context context, JSONObject json, Printer mPrinter, String receipt_header) throws Epos2Exception, JSONException {


        String method = "";
        String website = AppConstant.WEBSITE;


        String address = AppConstant.ADDRESS;

//        String post_code = json.getString(AppConstant.Keys.Postcode);

        String city = AppConstant.CITY;
        String tel = AppConstant.TEL;

        String reg_info = AppConstant.REG_INFO;
        String name = AppConstant.NAME;

        StringBuilder textData = new StringBuilder();
        method = "addTextAlign";
        mPrinter.addTextAlign(Printer.ALIGN_CENTER);


        mPrinter.addText(name + "\n");
        mPrinter.addText(address + " " + city + "\n");
//        mPrinter.addText(post_code + " " + city + "\n");
//        mPrinter.addText( city + "\n");
        mPrinter.addText(website + "\n");
        mPrinter.addText(tel + "\n");
        mPrinter.addFeedLine(1);
        mPrinter.addText(context.getString(R.string.legal_info, reg_info) + "\n");

        addLine(mPrinter);
        mPrinter.addText(receipt_header + "\n");
        addLine(mPrinter);


        return true;

    }

    public static boolean createAccountRecieptHeader(Context context, JSONObject json, Printer mPrinter, String receipt_header) throws Epos2Exception, JSONException {


        String address = json.getString(AppConstant.Keys.Address);

        String post_code = json.getString(AppConstant.Keys.Postcode);

        String city = json.getString(AppConstant.Keys.City);

        String website = json.getString(AppConstant.Keys.WEBSITE);
        String reg_info = AppConstant.REG_INFO;
        String method = "";
        String name = AppConstant.NAME;

        StringBuilder textData = new StringBuilder();
        method = "addTextAlign";
        mPrinter.addTextAlign(Printer.ALIGN_CENTER);
        mPrinter.addText(name + "\n");
        mPrinter.addText(address + "\n");
        mPrinter.addText(post_code + " " + city + "\n");
        mPrinter.addText(website + "\n");
        mPrinter.addFeedLine(1);
        mPrinter.addText(context.getString(R.string.legal_info, reg_info) + "\n");
        addLine(mPrinter);
        mPrinter.addText(receipt_header + "\n");
        addLine(mPrinter);


        return true;

    }


    public static boolean createPaymentSlip(Context mContext, Printer mPrinter, JSONObject json, boolean isrfid) throws Epos2Exception, JSONException, Exception {
        Log.i("Json", "" + json.toString());
        JSONArray items = json.getJSONArray(AppConstant.Keys.Items);
        if (!addRecieptLogo(mContext, R.mipmap.recieptlogo, mPrinter)) {
            return false;
        }
        if (!createPOSRecieptHeader(mContext, json, mPrinter, mContext.getString(R.string.reciept))) {
            return false;
        }
        if (!addOrderInfo(mContext, mPrinter, json)) {
            return false;
        }
        addOrders(mContext, mPrinter, items);
        addOrderPaymentDetails(mContext, mPrinter, json, false, isrfid);
        addLine(mPrinter);
        addThankyouMessage(mContext, mPrinter);
        mPrinter.addFeedLine(1);

        mPrinter.addCut(Printer.CUT_FEED);
        return true;
    }

    public static boolean createCancelOrderSlip(Context mContext, Printer mPrinter, JSONObject json) throws Epos2Exception, JSONException, Exception {
        Log.i("Json", "" + json.toString());
        JSONArray items = json.getJSONArray(AppConstant.Keys.Items);
        if (!addRecieptLogo(mContext, R.mipmap.logo, mPrinter)) {
            return false;
        }
        if (!createPOSRecieptHeader(mContext, json, mPrinter, mContext.getString(R.string.cancel_reciept))) {
            return false;
        }
        if (!addOrderInfo(mContext, mPrinter, json)) {
            return false;
        }
        addOrders(mContext, mPrinter, items);
        addOrderPaymentDetails(mContext, mPrinter, json, true, false);
//        addLine(mPrinter);
//        addThankyouMessage(mContext, mPrinter);
        mPrinter.addFeedLine(1);

        mPrinter.addCut(Printer.CUT_FEED);
        return true;


    }


    public static boolean addOrderInfo(Context context, Printer mPrinter, JSONObject json) throws Epos2Exception, JSONException {
        StringBuffer textData = new StringBuffer();
        String order_num = json.getString(AppConstant.Keys.Order_num);
//        String order_time = json.getString(AppConstant.Keys.Order_time);
        String order_date_time = json.getString(AppConstant.Keys.Order_date);
        String cashier_name = json.getString(AppConstant.Keys.Cashier_name);
        String customer_name = json.getString(AppConstant.Keys.Customer_name);
        String customer_email = json.getString(AppConstant.Keys.Customer_email);
        String customer_phone = json.getString(AppConstant.Keys.Customer_phone);
//        String order_date_time = order_date + "  " + order_time;
//        String reg_name = json.getString(AppConstant.Keys.Register_name);

        mPrinter.addTextSize(1, 1);
        mPrinter.addTextAlign(Printer.ALIGN_LEFT);

        boldLine(mPrinter, true);


        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.order_number) + context.getString(R.string.space15)).substring(0, 15));

        boldLine(mPrinter, false);

        mPrinter.addText("" + order_num + "\n");

        boldLine(mPrinter, true);

        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.order_date) + context.getString(R.string.space21)).substring(0, 15));

        boldLine(mPrinter, false);

        mPrinter.addText(order_date_time + "\n");

        boldLine(mPrinter, true);

        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.served_by) + context.getString(R.string.space20)).substring(0, 15));

        boldLine(mPrinter, false);

        mPrinter.addText(cashier_name + "\n");

        boldLine(mPrinter, true);

        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.customer_name) + context.getString(R.string.space20)).substring(0, 15));

        boldLine(mPrinter, false);

        mPrinter.addText(customer_name + "\n");
        boldLine(mPrinter, true);

        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.customer_email) + context.getString(R.string.space20)).substring(0, 15));

        boldLine(mPrinter, false);

        mPrinter.addText(customer_email + "\n");
        boldLine(mPrinter, true);

        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.customer_mobile) + context.getString(R.string.space20)).substring(0, 15));

        boldLine(mPrinter, false);

        mPrinter.addText(customer_phone + "\n");

//        boldLine(mPrinter, true);
//
//        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.register_name) + context.getString(R.string.space20)).substring(0, 20));
//
//        boldLine(mPrinter, false);
//
//
//        mPrinter.addText(reg_name + "\n");

        addLine(mPrinter);


        mPrinter.addTextAlign(Printer.ALIGN_LEFT);


//        mPrinter.addTextSize(2, 2);
        boldLine(mPrinter, true);
        String desc_head = context.getString(R.string.desc) + context.getString(R.string.space25);
        desc_head = desc_head.substring(0, 15);
//        textData.append(context.getString(R.string.space2) + context.getString(R.string.qty) + context.getString(R.string.space2) + desc_head + context.getString(R.string.space2) + context.getString(R.string.price) + context.getString(R.string.space2) + context.getString(R.string.amount) + "\n");
        textData.append(context.getString(R.string.space2) + context.getString(R.string.qty) + context.getString(R.string.space6) + desc_head + context.getString(R.string.space5) + context.getString(R.string.amount) + "\n");
        mPrinter.addText(textData.toString());
        boldLine(mPrinter, false);
        textData.delete(0, textData.length());
        addLine(mPrinter);


        return true;
    }


    private static void addOrders(Context context, Printer mPrinter, JSONArray jsonarray) throws Epos2Exception, JSONException {
        StringBuffer textData = new StringBuffer();
        mPrinter.addTextAlign(Printer.ALIGN_LEFT);
        mPrinter.addTextSize(1, 1);
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject json = jsonarray.getJSONObject(i);
            String ordername = json.getString(AppConstant.Keys.Desc);

            if (ordername.length() > 17) {
                ordername = ordername.substring(0, 17);
            } else {
                ordername = ordername + context.getString(R.string.space18);
                ordername = ordername.substring(0, 17);
            }
//            String price = context.getString(R.string.euro) + json.getString(AppConstant.Keys.Price) + context.getString(R.string.space11);
//            price = (price).substring(0, 7);

            String amount = getFormattedPriceFor5DigitKwacha(context, json.getString(AppConstant.Keys.Amount));
            amount = context.getString(R.string.zmw) + context.getString(R.string.space1) + amount + context.getString(R.string.space11);
            amount = amount.substring(0, 8);

            String itemid = json.getString(AppConstant.Keys.Qty) + context.getString(R.string.space12);
            itemid = itemid.substring(0, 12);


//            textData.append(context.getString(R.string.space1) + itemid + context.getString(R.string.space2) + ordername + context.getString(R.string.space2) + price + context.getString(R.string.space1) + amount + "\n");
            textData.append(context.getString(R.string.space1) + itemid + context.getString(R.string.space2) + ordername + context.getString(R.string.space1) + amount + "\n");


        }
        mPrinter.addText(textData.toString());
        textData.delete(0, textData.length());
        mPrinter.addFeedLine(1);


    }

    private static void addOrderPaymentDetails(Context context, Printer mPrinter, JSONObject json, boolean cancel, boolean isrfid) throws Epos2Exception, JSONException {
        StringBuffer textData = new StringBuffer();
//        String subtotal = json.getString(AppConstant.Keys.Subtotal);
//        String subtotal_net = json.getString(AppConstant.Keys.subtotal_net);
        String total_incl_vat = json.getString(AppConstant.Keys.total_incl_vat);
        String payment_method = json.getString(AppConstant.Keys.payment_method);
        String cash_given = "";
        String cash_received = "";
//        boolean signon=true;
        boolean signon = json.has(AppConstant.Keys.SignOn);
        boolean azubi = json.has(AppConstant.Keys.Azubi);
        if (!cancel) {
            cash_given = json.getString(AppConstant.Keys.cash_given);
            cash_received = json.getString(AppConstant.Keys.cash_received);
        }
        String subt_heading = context.getString(R.string.subtotal) + context.getString(R.string.space29);
        subt_heading = subt_heading.substring(0, 28);
//        subtotal = getFormattedPrice(context, subtotal);
//        subtotal = context.getString(R.string.euro) + subtotal + context.getString(R.string.space7);
//        subtotal = subtotal.substring(0, 7);
        String discount_rate = "";
        String discount_value = "";
        if (json.has(AppConstant.Keys.Discount_Rate)) {
            discount_rate = json.getString(AppConstant.Keys.Discount_Rate);
            discount_value = json.getString(AppConstant.Keys.Discount_value);
        }

        mPrinter.addTextSize(1, 1);
        mPrinter.addTextAlign(Printer.ALIGN_RIGHT);

//        if (signon) {
////            if (json.has(AppConstant.Keys.Guest)) {
////            }
//            mPrinter.addText(context.getString(R.string.subtotal) + context.getString(R.string.space2) + subtotal + context.getString(R.string.space1) + "\n");
//            if (json.has(AppConstant.Keys.Guest)) {
//                addPaymentDetails(context, mPrinter, context.getString(R.string.discount), getFormattedPrice(context, json.getString(AppConstant.Keys.Guest)), false, false);
//            }
//        } else {
//            mPrinter.addText(context.getString(R.string.subtotal) + context.getString(R.string.space2) + subtotal + context.getString(R.string.space1) + "\n");
//            if (json.has(AppConstant.Keys.Guest)) {
//                addPaymentDetails(context, mPrinter, context.getString(R.string.discount), getFormattedPrice(context, json.getString(AppConstant.Keys.Guest)), false, false);
//            }
//        }
//
//
//        if (json.has(AppConstant.Keys.vat1_amount)) {
//            String vat_percent = json.getString(AppConstant.Keys.vat1);
//            String vat_amt = json.getString(AppConstant.Keys.vat1_amount);
//            addPaymentDetails(context, mPrinter, context.getString(R.string.vat, vat_percent), getFormattedPrice(context, vat_amt), false, false);
//        }
//        if (json.has(AppConstant.Keys.vat2_amount)) {
//            String drink_percent = json.getString(AppConstant.Keys.vat2);
//            String drink_amount = json.getString(AppConstant.Keys.vat2_amount);
//            addPaymentDetails(context, mPrinter, context.getString(R.string.vat, drink_percent), getFormattedPrice(context, drink_amount), false, false);
//        }
//
//        addPaymentDetails(context, mPrinter, context.getString(R.string.sub_total_net), getFormattedPrice(context, subtotal_net), false, false);
//        if (signon) {
////            addLine(mPrinter);
//            addPaymentDetails(context, mPrinter, context.getString(R.string.signon), getFormattedPrice(context, json.getString(AppConstant.Keys.SignOn)), false, false);
//        }
//        if (azubi) {
////            addLine(mPrinter);
//            addPaymentDetails(context, mPrinter, context.getString(R.string.azubi), getFormattedPrice(context, json.getString(AppConstant.Keys.Azubi)), false, false);
//        }
//
//
////        String one_time_fee="0,50";
////        addPaymentDetails(context, mPrinter, context.getString(R.string.customer_onetime), getFormattedPrice(context, one_time_fee),false);
//
////
//        if (!json.has("Guest")) {
//            if (discount_rate != null && discount_rate.length() > 0) {
//                addPaymentDetails(context, mPrinter, context.getString(R.string.discount2, discount_rate), getFormattedPrice(context, discount_value), true, false);
//            }
//        }
//
//
////        }
//
        boldLine(mPrinter, true);
//        mPrinter.addFeedLine(1);


        addPaymentDetails(context, mPrinter, context.getString(R.string.total_incl_vat).toUpperCase(), getFormattedPriceFor5DigitKwacha(context, total_incl_vat), false, false);


        boldLine(mPrinter, false);
        mPrinter.addTextSize(1, 1);
//        mPrinter.addFeedLine(1);
//        if (!cancel) {
//            if (isrfid) {
//                addPaymentDetails(context, mPrinter, context.getString(R.string.deducted_value), getFormattedPrice(context, cash_given), true, false);
//                addPaymentDetailsfor4Digits(context, mPrinter, context.getString(R.string.card_balance), getFormattedPrice(context, cash_received), false, false);
//            } else {

//        addPaymentDetails(context, mPrinter, context.getString(R.string.payment_method),  payment_method, false, false);


        /*
         Removed for hiding payment by
         mPrinter.addTextAlign(Printer.ALIGN_RIGHT);
        payment_method =  payment_method + context.getString(R.string.space11);
        payment_method = payment_method.substring(0, 8);

            mPrinter.addText(context.getString(R.string.payment_method) + context.getString(R.string.space2));

        mPrinter.addTextAlign(Printer.ALIGN_LEFT);
        mPrinter.addText(payment_method + context.getString(R.string.space2) + "\n");
        ends here

        */
//        addPaymentDetails(context, mPrinter, context.getString(R.string.cash), getFormattedPriceFor5DigitKwacha(context, cash_given), false, false);

        if (json.has(AppConstant.Keys.cash_given)) {
            String amount=    json.getString(AppConstant.Keys.cash_given);
            addPaymentDetails(context, mPrinter, context.getString(R.string.cash), getFormattedPriceFor5DigitKwacha(context, amount), false, false);
        }
        if (json.has(AppConstant.Keys.card_amount)) {
          String amount=    json.getString(AppConstant.Keys.card_amount);
            addPaymentDetails(context, mPrinter, context.getString(R.string.card), getFormattedPriceFor5DigitKwacha(context, amount), false, false);

        }
        if (json.has(AppConstant.Keys.cheque_amount)) {
            String amount=    json.getString(AppConstant.Keys.cheque_amount);
            addPaymentDetails(context, mPrinter, context.getString(R.string.cheque), getFormattedPriceFor5DigitKwacha(context, amount), false, false);
        }
        if (json.has(AppConstant.Keys.bank_amount)) {
            String amount=    json.getString(AppConstant.Keys.bank_amount);
            addPaymentDetails(context, mPrinter, context.getString(R.string.bank_transfer), getFormattedPriceFor5DigitKwacha(context, amount), false, false);
        }






//        addPaymentDetails(context, mPrinter, context.getString(R.string.cash), getFormattedPriceFor5DigitKwacha(context, cash_given), false, false);
        mPrinter.addTextAlign(Printer.ALIGN_LEFT);
        addPaymentDetails(context, mPrinter, context.getString(R.string.cash_returned), getFormattedPriceFor5DigitKwacha(context, cash_received), false, false);
//            }
//        } else {
//            addPaymentDetails(context, mPrinter, context.getString(R.string.cash_refunded), getFormattedPrice(context, total_incl_vat), true, false);
//        }

    }

    private static void addPaymentDetails(Context context, Printer mPrinter, String heading, String value, boolean negative, boolean positive) throws Epos2Exception {


        mPrinter.addTextAlign(Printer.ALIGN_RIGHT);
        value = context.getString(R.string.zmw) + context.getString(R.string.space1) + value + context.getString(R.string.space11);
        value = value.substring(0, 8);
        if (negative) {
            mPrinter.addText(heading + "-" + context.getString(R.string.space1));
        } else if (positive) {
            mPrinter.addText(heading + "+" + context.getString(R.string.space1));
        } else {
            mPrinter.addText(heading + context.getString(R.string.space2));
        }
        mPrinter.addTextAlign(Printer.ALIGN_LEFT);
        mPrinter.addText(value + context.getString(R.string.space2) + "\n");
    }

    private static void addPaymentDetailsfor4Digits(Context context, Printer mPrinter, String heading, String value, boolean negative, boolean positive) throws Epos2Exception {

        mPrinter.addTextAlign(Printer.ALIGN_RIGHT);
        value = context.getString(R.string.euro_without_space) + " " + value + context.getString(R.string.space8);
        value = value.substring(0, 7);
        if (negative) {
            mPrinter.addText(heading + "-" + context.getString(R.string.space1));
        } else if (positive) {
            mPrinter.addText(heading + "+" + context.getString(R.string.space1));
        } else {
            mPrinter.addText(heading + context.getString(R.string.space2));
        }
        mPrinter.addTextAlign(Printer.ALIGN_LEFT);
        mPrinter.addText(value + context.getString(R.string.space1) + "\n");
    }


    private static void addThankyouMessage(Context context, Printer mPrinter) throws Epos2Exception, Exception {
        mPrinter.addTextAlign(Printer.ALIGN_CENTER);

        mPrinter.addText(context.getString(R.string.thankyou_msg));

        addRecieptSmiley(context, mPrinter);
        mPrinter.addFeedLine(1);
//        mPrinter.addText("Guten Appetit! "+"\n");
    }


    public static boolean createSummarySlip(Context mContext, Printer mPrinter, JSONObject json) throws Epos2Exception, JSONException, Exception {

//        String status = json.getString(AppConstant.Keys.Status);
        String header = "";
//        boolean is_open = (status.equalsIgnoreCase(AppConstant.STATUS_OPEN));
//        if (is_open) {

        String month = json.getString(AppConstant.Keys.MONTH);
        month = getMonth(Integer.parseInt(month));
        header = mContext.getString(R.string.summary_reciept_title, month);
//        } else {
//            header = mContext.getString(R.string.cash_reciept_closed);
//        }
        addRecieptLogo(mContext, R.mipmap.logo, mPrinter);
        createAccountRecieptHeader(mContext, json, mPrinter, header);
        createSummaryRecieptDetail(mContext, json, mPrinter);

        return true;
    }

    public static boolean createCashMachineSlip(Context mContext, Printer mPrinter, JSONObject json) throws Epos2Exception, JSONException, Exception {

//        String status = json.getString(AppConstant.Keys.Status);
        String header = "";
//        boolean is_open = (status.equalsIgnoreCase(AppConstant.STATUS_OPEN));
//        if (is_open) {


        header = mContext.getString(R.string.cashmachine_reciept_title);
//        } else {
//            header = mContext.getString(R.string.cash_reciept_closed);
//        }
        addRecieptLogo(mContext, R.mipmap.logo, mPrinter);
        createAccountRecieptHeader(mContext, json, mPrinter, header);
        createCashMachineRecieptDetail(mContext, json, mPrinter);
        return true;
    }


    private static String getFormattedPrice(Context context, String price) {
        if (price.contains(",")) {
            String[] split = price.split(",");
            if (split[0].length() == 1) {
                split[0] = context.getString(R.string.space1) + split[0];
            }
            return (split[0] + "," + split[1]);
        } else {
            return price;
        }

    }

    private static String getFormattedPriceFor5DigitKwacha(Context context, String price) {


//        String hundreds="";
//        String thousand="";
//        if (!price.contains(",")&&price.length()>3)
//        {
//            DecimalFormat formatter = new DecimalFormat("#,###,###");
//
//            price = formatter.format(price);
//        }
        if (price.length() == 6) {
            price = price;
        } else if (price.length() == 5) {
            price = context.getString(R.string.space1) + price;
        } else if (price.length() == 4) {
            price = context.getString(R.string.space2) + price;
        } else if (price.length() == 3) {
            price = context.getString(R.string.space3) + price;
        } else if (price.length() == 2) {
            price = context.getString(R.string.space4) + price;
        } else if (price.length() == 1) {
            price = context.getString(R.string.space5) + price;
        }


        return price;


    }

    private static String get4FormattedPriceFor5Digits(Context context, String price) {
        String[] split = (price.contains(",") ? price.split(",") : new String[]{price});
        if (split[0].length() == 4) {
            split[0] = context.getString(R.string.space1) + split[0];
        } else if (split[0].length() == 3) {
            split[0] = context.getString(R.string.space2) + split[0];
        } else if (split[0].length() == 2) {
            split[0] = context.getString(R.string.space3) + split[0];
        } else if (split[0].length() == 1) {
            split[0] = context.getString(R.string.space4) + split[0];
        }
        if (split.length > 1) {
            return (split[0] + "," + split[1]);
        }
        return (split[0]);

    }

    private static String get4FormattedPrice(Context context, String price) {

        String[] split = (price.contains(",") ? price.split(",") : new String[]{price});
        if (split[0].length() == 3) {
            split[0] = context.getString(R.string.space1) + split[0];
        } else if (split[0].length() == 2) {
            split[0] = context.getString(R.string.space2) + split[0];
        } else if (split[0].length() == 1) {
            split[0] = context.getString(R.string.space3) + split[0];
        }
        if (split.length > 1) {
            return (split[0] + "," + split[1]);
        }
        return (split[0]);


    }

    private static String get4FormattedQuantity(Context context, String quantity) {

        if (quantity.length() == 3) {
            quantity = context.getString(R.string.space1) + quantity;
        } else if (quantity.length() == 2) {
            quantity = context.getString(R.string.space2) + quantity;
        } else if (quantity.length() == 1) {
            quantity = context.getString(R.string.space3) + quantity;
        }
        return quantity;

    }

    public static boolean createAccRecieptDetail(Context context, JSONObject json, Printer mPrinter, String status, boolean is_open) throws Epos2Exception, JSONException {
        String method = "";
        String cashier_name = json.getString(AppConstant.Keys.Cashier_name);
        String reg_name = json.getString(AppConstant.Keys.Register_name);

        String timestamp = json.getString(AppConstant.Keys.Timestamp);


        StringBuilder textData = new StringBuilder();
        mPrinter.addTextAlign(Printer.ALIGN_LEFT);
        boldLine(mPrinter, true);
        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.order_date) + context.getString(R.string.space15)).substring(0, 20));
        boldLine(mPrinter, false);
        mPrinter.addText(timestamp + "\n");

        if (!is_open) {
            String receipt_no = json.getString(AppConstant.Keys.Sno);
//            String receipt_no = "123";
            boldLine(mPrinter, true);
            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.receipt_sno) + context.getString(R.string.space15)).substring(0, 20));

            boldLine(mPrinter, false);
            mPrinter.addText(receipt_no + "\n");
        }

        boldLine(mPrinter, true);
        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.served_by) + context.getString(R.string.space15)).substring(0, 20));
        boldLine(mPrinter, false);
        mPrinter.addText(cashier_name + "\n");
        boldLine(mPrinter, true);
        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.register_name) + context.getString(R.string.space15)).substring(0, 20));
        boldLine(mPrinter, false);
        mPrinter.addText(reg_name + "\n");
        boldLine(mPrinter, true);
        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.status) + context.getString(R.string.space15)).substring(0, 20));
        boldLine(mPrinter, false);
        mPrinter.addText(status + "\n");
        addLine(mPrinter);
        if (is_open) {
            createAccLoginRecieptDetail(context, json, mPrinter, is_open);
        } else {
            createAccLogoutRecieptDetail(context, json, mPrinter, is_open);
        }
        mPrinter.addFeedLine(2);
        mPrinter.addCut(Printer.CUT_FEED);


        return true;
    }

    public static boolean createSummaryRecieptDetail(Context context, JSONObject json, Printer mPrinter) throws Epos2Exception, JSONException {
        String method = "";
        String cashier_name = json.getString(AppConstant.Keys.Cashier_name);
        String reg_name = json.getString(AppConstant.Keys.Register_name);
        String month = json.getString(AppConstant.Keys.MONTH);
        month = getMonth(Integer.parseInt(month));
        String timestamp = json.getString(AppConstant.Keys.Timestamp);
        String receipt_no = json.getString(AppConstant.Keys.s_no);
        //        String total_sales = "1002,00";
        String total_sales = json.getString(AppConstant.Keys.Total_Sales);
        String total_sales_7 = json.getString(AppConstant.Keys.Total_Sales_7);
        String total_sales_19 = json.getString(AppConstant.Keys.Total_Sales_19);
//        String cancel_orders = "92,05";
        String canceled_orders = "";

        if (json.isNull(AppConstant.Keys.Canceled_Order)) {
            canceled_orders = "0";
        } else {
            canceled_orders = json.getString(AppConstant.Keys.Canceled_Order);
        }


        String cancel_orders = json.getString(AppConstant.Keys.Cancel_Order);
//        String withdrawal = "500,00";
        String withdrawal = json.getString(AppConstant.Keys.Withdrawal);
//        String cashadded = "700,00";
        String cashadded = json.getString(AppConstant.Keys.Cash_Added);
//        String daily_sum = "1002,00";
        String daily_sum = json.getString(AppConstant.Keys.Total_Sales);
//        String vat1_rate = "19%";
//        String vat1_amount = "1,90";
//        String vat2_rate = "7%";
//        String vat2_amount = "1,70";

        String vat1_rate = json.getString(AppConstant.Keys.vat1);
        String vat1_amount = json.getString(AppConstant.Keys.vat1_amount);
        String vat2_rate = json.getString(AppConstant.Keys.vat2);
        String vat2_amount = json.getString(AppConstant.Keys.vat2_amount);


        StringBuilder textData = new StringBuilder();
        mPrinter.addTextAlign(Printer.ALIGN_LEFT);
        boldLine(mPrinter, true);
        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.order_date) + context.getString(R.string.space15)).substring(0, 20));
        boldLine(mPrinter, false);
        mPrinter.addText(timestamp + "\n");


//            String receipt_no = "123";
        boldLine(mPrinter, true);
        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.monthly_receipt_sno) + context.getString(R.string.space15)).substring(0, 20));

        boldLine(mPrinter, false);
        mPrinter.addText(receipt_no + "\n");


        boldLine(mPrinter, true);
        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.served_by) + context.getString(R.string.space15)).substring(0, 20));
        boldLine(mPrinter, false);
        mPrinter.addText(cashier_name + "\n");
        boldLine(mPrinter, true);
        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.register_name) + context.getString(R.string.space15)).substring(0, 20));
        boldLine(mPrinter, false);
        mPrinter.addText(reg_name + "\n");

        addLine(mPrinter);

        mPrinter.addTextAlign(Printer.ALIGN_LEFT);

        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.total_sales) + context.getString(R.string.space23)).substring(0, 20));
        mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, total_sales) + "\n");
//


        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.sales_umsatz, vat1_rate) + context.getString(R.string.space23)).substring(0, 20));
        mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, total_sales_19) + "\n");

        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.sales_umsatz, " " + vat2_rate) + context.getString(R.string.space23)).substring(0, 20));
        mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, total_sales_7) + "\n");


        addLine(mPrinter);
        mPrinter.addTextAlign(Printer.ALIGN_LEFT);

        // implemented on 22 May 2017
            /*    [18/05/17, 4:40:03 PM] Ivo Ralf Müller: we must now split this in cash and nfc turnover
                [18/05/17, 4:41:09 PM] Ivo Ralf Müller: This means we are no longer using sales of 19% and sales 7%. We can turn it off. But we have to add 4 new values
                [18/05/17, 4:41:58 PM] Ivo Ralf Müller: BAR Umsatz 19%, BAR Umsatz 7%, NFC Umsatz 19%, NFC Umsatz 7%



                github #74
                */


//        json.put(AppConstant.Keys.Cash_Sales_7,"10,00");
//        json.put(AppConstant.Keys.Cash_Sales_19,"10,00");
//        json.put(AppConstant.Keys.Nfc_Sales_7,"10,00");
//        json.put(AppConstant.Keys.Nfc_Sales_19,"10,00");
//        String vat1_rate = json.getString(AppConstant.Keys.vat1);
//        String vat1_amount = json.getString(AppConstant.Keys.vat1_amount);
//        String vat2_rate = json.getString(AppConstant.Keys.vat2);
//        String vat2_amount = json.getString(AppConstant.Keys.vat2_amount);


        if (json.has(AppConstant.Keys.Cash_Sales_19)) {
            String cash_sales_19 = json.getString(AppConstant.Keys.Cash_Sales_19);
            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.sales_umsatz_normal, " " + vat1_rate) + context.getString(R.string.space23)).substring(0, 20));
            mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, cash_sales_19) + "\n");
        }
        if (json.has(AppConstant.Keys.Cash_Sales_7)) {
            String cash_sales_7 = json.getString(AppConstant.Keys.Cash_Sales_7);
            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.sales_umsatz_normal, vat2_rate) + context.getString(R.string.space23)).substring(0, 20));
            mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, cash_sales_7) + "\n");
        }

        if (json.has(AppConstant.Keys.Nfc_Sales_19)) {
            String nfc_sales_19 = json.getString(AppConstant.Keys.Nfc_Sales_19);
            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.sales_umsatz_nfc, " " + vat1_rate) + context.getString(R.string.space23)).substring(0, 20));
            mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, nfc_sales_19) + "\n");
        }
        if (json.has(AppConstant.Keys.Nfc_Sales_7)) {
            String nfc_sales_7 = json.getString(AppConstant.Keys.Nfc_Sales_7);
            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.sales_umsatz_nfc, vat2_rate) + context.getString(R.string.space23)).substring(0, 20));
            mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, nfc_sales_7) + "\n");
        }


        //end here


        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.vat_without_colon, vat1_rate) + context.getString(R.string.space23)).substring(0, 20));
        mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, vat1_amount) + "\n");

        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.vat_without_colon, " " + vat2_rate) + context.getString(R.string.space23)).substring(0, 20));
        mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, vat2_amount) + "\n");


        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.cancel_order) + context.getString(R.string.space23)).substring(0, 20));
        mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, cancel_orders) + context.getString(R.string.space2) + "(" + canceled_orders + ")" + "\n");


        addLine(mPrinter);
        mPrinter.addTextAlign(Printer.ALIGN_LEFT);
// Changed on 7 december for Adding num of bottles
//        json.put(AppConstant.Keys.bottle_amount, "1,90");
//        json.put(AppConstant.Keys.bottle_num, "17");

        if (json.has(AppConstant.Keys.bottle_amount) && json.has(AppConstant.Keys.bottle_num)) {
            String bottle_refund = json.getString(AppConstant.Keys.bottle_amount);
            String bottle_num = json.getString(AppConstant.Keys.bottle_num);
            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.num_bottle_deposit) + context.getString(R.string.space23)).substring(0, 20));
            mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, bottle_refund) + context.getString(R.string.space2) + "(" + bottle_num + ")" + "\n");
        }

//        mPrinter.addText(R.string.space2 + get4FormattedQuantity(context, cancel_orders) + "\n");
//        mPrinter.addText(context.getString(R.string.space6) + get4FormattedQuantity(context, cancel_orders) + "\n");

        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.bar_withdrawal) + context.getString(R.string.space23)).substring(0, 20));
        mPrinter.addText(context.getString(R.string.euro) + "-" + get4FormattedPriceFor5Digits(context, withdrawal) + "\n");

        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.bar_cash_added) + context.getString(R.string.space23)).substring(0, 20));
        mPrinter.addText(context.getString(R.string.euro) + "+" + get4FormattedPriceFor5Digits(context, cashadded) + "\n");

// Changed on 7 december for Adding NFC Add Cash
//        json.put(AppConstant.Keys.nfcadded, "1,90");
        if (json.has(AppConstant.Keys.nfcadded)) {
            String nfc_added = json.getString(AppConstant.Keys.nfcadded);

            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.nfc_added) + context.getString(R.string.space23)).substring(0, 20));
            mPrinter.addText(context.getString(R.string.euro) + "+" + get4FormattedPriceFor5Digits(context, nfc_added) + "\n");
        }
// Changed on 7 december for Adding NFC Withdraw Cash
//        json.put(AppConstant.Keys.nfcwithdraw, "1,90");
        if (json.has(AppConstant.Keys.nfcwithdraw)) {
            String nfcwithdrawal = json.getString(AppConstant.Keys.nfcwithdraw);

            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.nfc_withdrawal) + context.getString(R.string.space23)).substring(0, 20));
            mPrinter.addText(context.getString(R.string.euro) + "-" + get4FormattedPriceFor5Digits(context, nfcwithdrawal) + "\n");
        }


        addLine(mPrinter);
        addLine(mPrinter);
        boldLine(mPrinter, true);
        mPrinter.addTextAlign(Printer.ALIGN_LEFT);
        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.monthly_receipt_footer) + context.getString(R.string.space23)).substring(0, 20));
        mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, total_sales) + "\n");

//        json.put(AppConstant.Keys.zzbonchecksum, "32");
        if (json.has(AppConstant.Keys.zzbonchecksum)) {
            addLine(mPrinter);
            mPrinter.addTextAlign(Printer.ALIGN_LEFT);
            String checksum = "";


            if (json.isNull(AppConstant.Keys.zzbonchecksum)) {
                checksum = "0";
            } else {
                checksum = json.getString(AppConstant.Keys.zzbonchecksum);
            }


            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.zzbon_checksum) + context.getString(R.string.space23)).substring(0, 20));
            mPrinter.addText(context.getString(R.string.space6) + checksum + "\n");
            addLine(mPrinter);
        }

        mPrinter.addFeedLine(2);
        mPrinter.addCut(Printer.CUT_FEED);

        return true;
    }

    public static boolean createCashMachineRecieptDetail(Context context, JSONObject json, Printer mPrinter) throws Epos2Exception, JSONException {
        String method = "";

        String receipt_no = json.getString(AppConstant.Keys.Sno);
        String timestamp = json.getString(AppConstant.Keys.Timestamp);
        String cashmachine_no = json.getString(AppConstant.Keys.cashmachine_no);

        JSONArray transactionArray = json.getJSONArray(AppConstant.Keys.cashmachine_trans);
        String cash_qty_sum = json.getString(AppConstant.Keys.cash_qty_sum);
        String cash_grand_total = json.getString(AppConstant.Keys.cash_grand_total);


        // RecieptDetail
        StringBuilder textData = new StringBuilder();
        mPrinter.addTextAlign(Printer.ALIGN_LEFT);
        boldLine(mPrinter, true);
        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.cashmachine_receipt_sno) + context.getString(R.string.space15)).substring(0, 20));
        boldLine(mPrinter, false);
        mPrinter.addText(receipt_no + "\n");

        boldLine(mPrinter, true);
        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.order_date) + context.getString(R.string.space15)).substring(0, 20));
        boldLine(mPrinter, false);
        mPrinter.addText(timestamp + "\n");
//            String receipt_no = "123";
        boldLine(mPrinter, true);
        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.cashmachine_sno) + context.getString(R.string.space15)).substring(0, 20));
        boldLine(mPrinter, false);
        mPrinter.addText(cashmachine_no + "\n");
        addLine(mPrinter);

        // TransactionDetailHeader
        mPrinter.addTextAlign(Printer.ALIGN_LEFT);
        boldLine(mPrinter, true);

        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.cash_denomination) + context.getString(R.string.space13)).substring(0, 13));
        mPrinter.addText((context.getString(R.string.cash_quantity) + context.getString(R.string.space13)).substring(0, 13) + (context.getString(R.string.cash_total) + context.getString(R.string.space13)).substring(0, 13) + "\n");
        boldLine(mPrinter, false);

        // Transactions
        for (int i = 0; i < transactionArray.length(); i++) {

            JSONObject transjson = transactionArray.getJSONObject(i);

            String cashdenom = transjson.getString(AppConstant.Keys.cashdenom);
            String cash_qty = transjson.getString(AppConstant.Keys.cash_qty);
            ;
            String cash_total = transjson.getString(AppConstant.Keys.cash_total);
            ;
            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.euro_without_space) + cashdenom + context.getString(R.string.space13)).substring(0, 13));
            mPrinter.addText((cash_qty + context.getString(R.string.space13)).substring(0, 13) + (context.getString(R.string.euro) + context.getString(R.string.space1) + cash_total + context.getString(R.string.space13)).substring(0, 13) + "\n");


        }


        addLine(mPrinter);
        mPrinter.addTextAlign(Printer.ALIGN_LEFT);
        // Transactions total
        boldLine(mPrinter, true);
        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.cash_summe) + context.getString(R.string.space13)).substring(0, 13));

        mPrinter.addText((cash_qty_sum + context.getString(R.string.space13)).substring(0, 13) + (context.getString(R.string.euro) + context.getString(R.string.space1) + cash_grand_total + context.getString(R.string.space13)).substring(0, 13) + "\n");
        boldLine(mPrinter, false);


        mPrinter.addFeedLine(2);
        mPrinter.addCut(Printer.CUT_FEED);

        return true;
    }

    private static boolean createAccLoginRecieptDetail(Context context, JSONObject json, Printer mPrinter, boolean is_open) throws Epos2Exception, JSONException {


        String drawer_amount = json.getString(AppConstant.Keys.Drawer_Amount);
        String reg_amount = json.getString(AppConstant.Keys.Reg_Amount);


        mPrinter.addTextAlign(Printer.ALIGN_LEFT);
        float r_amount = Float.parseFloat(reg_amount);
        float d_amount = Float.parseFloat(drawer_amount);
        float balance = r_amount - d_amount;
        reg_amount = (reg_amount + context.getString(R.string.space8)).substring(0, 8).replace(".", ",");
        drawer_amount = String.format(java.util.Locale.US, "%.2f", d_amount).replace(".", ",");
        drawer_amount = (drawer_amount + context.getString(R.string.space8)).substring(0, 8).replace(".", ",");
        String balance_string = ("" + balance + context.getString(R.string.space8)).substring(0, 6);
        String balance_str = String.format(java.util.Locale.US, "%.2f", balance).replace(".", ",").replace("-", "");

        /**
         * #117 changes
         * implemented on 8 June 2017 #117
         */

        if (is_open) {
            if (json.has(AppConstant.Keys.Reg_Amount) && json.has(AppConstant.Keys.Drawer_Amount)) {


//            float balance = r_amount - (d_amount+t_amount);


                mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.cash_amount) + context.getString(R.string.space23)).substring(0, 20));
                mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, reg_amount) + "\n");
                mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.cash_counted) + context.getString(R.string.space23)).substring(0, 20));
                mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, drawer_amount) + "\n");
                mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.cash_diff) + context.getString(R.string.space23)).substring(0, 20));
                mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, balance_str) + "\n");
//                #122 this feature will be available in future
//                if(balance==0) {
//                    mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, balance_str) + "\n");
//                } else if(balance>0) {
//                    mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.positive) + get4FormattedPriceFor5Digits(context, balance_str) + "\n");
//                } else if(balance<0) {
//                    mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.negative) + get4FormattedPriceFor5Digits(context, balance_str) + "\n");
//                }

            }
            addLine(mPrinter);
            if (json.has(AppConstant.Keys.Change)) {
                mPrinter.addTextAlign(Printer.ALIGN_LEFT);
                String change = json.getString(AppConstant.Keys.Change);
                change = (change + context.getString(R.string.space8)).substring(0, 8).replace(".", ",");
                boldLine(mPrinter, true);
                mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.change_string) + context.getString(R.string.space23)).substring(0, 20));
                mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, change) + "\n");
                boldLine(mPrinter, false);
            }

            //#117 changes end here

//        commented  on 19 April 17 https://drive.google.com/open?id=0B6uYF9O7061PbUwwbzlaSl9SaWs
//        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.cash_amount) + context.getString(R.string.space23)).substring(0, 20));
//        mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, reg_amount) + "\n");
//        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.cash_counted) + context.getString(R.string.space23)).substring(0, 20));
//        mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, drawer_amount) + "\n");
//        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.cash_diff) + context.getString(R.string.space23)).substring(0, 20));
//        mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, balance_str) + "\n");
            //#117 commented for disabling summe on 8 july 17
//        if (is_open) {
//            addLine(mPrinter);
//            addLine(mPrinter);
//            mPrinter.addTextAlign(Printer.ALIGN_LEFT);
//            boldLine(mPrinter, true);
//            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.total) + context.getString(R.string.space23)).substring(0, 20));
//            mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, reg_amount) + "\n");
//        }

        }
        //#117 changes end here
        return true;
    }


    public static boolean createAccLogoutRecieptDetail(Context context, JSONObject json, Printer mPrinter, boolean is_open) throws Epos2Exception, JSONException {


//        String total_sales = "1002,00";
        String total_sales = json.getString(AppConstant.Keys.Total_Sales);
//        String total_sales_7 = "5,05";
//        String total_sales_19 = "2,05";
        String total_sales_7 = json.getString(AppConstant.Keys.Total_Sales_7);
        String total_sales_19 = json.getString(AppConstant.Keys.Total_Sales_19);
//        String cancel_orders = "92,05";
        String canceled_orders = json.getString(AppConstant.Keys.Canceled_Order);
        String cancel_orders = json.getString(AppConstant.Keys.Cancel_Order);
//        String withdrawal = "500,00";
        String withdrawal = json.getString(AppConstant.Keys.Withdrawal);
//        String cashadded = "700,00";
        String cashadded = json.getString(AppConstant.Keys.Cash_Added);
//        String daily_sum = "1002,00";
        String daily_sum = json.getString(AppConstant.Keys.Daily_Sum);
//        String vat1_rate = "19%";
//        String vat1_amount = "1,90";
//        String vat2_rate = "7%";
//        String vat2_amount = "1,70";


        mPrinter.addTextAlign(Printer.ALIGN_LEFT);

        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.total_sales) + context.getString(R.string.space23)).substring(0, 20));
        mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, total_sales) + "\n");


        // Commented on 18 May 2017


        String vat1_rate = json.getString(AppConstant.Keys.vat1);
        String vat1_amount = json.getString(AppConstant.Keys.vat1_amount);
        String vat2_rate = json.getString(AppConstant.Keys.vat2);
        String vat2_amount = json.getString(AppConstant.Keys.vat2_amount);


        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.sales_umsatz, vat1_rate) + context.getString(R.string.space23)).substring(0, 20));
        mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, total_sales_7) + "\n");

        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.sales_umsatz, " " + vat2_rate) + context.getString(R.string.space23)).substring(0, 20));
        mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, total_sales_19) + "\n");

        addLine(mPrinter);
        mPrinter.addTextAlign(Printer.ALIGN_LEFT);

        // implemented on 18 May 2017
            /*    [18/05/17, 4:40:03 PM] Ivo Ralf Müller: we must now split this in cash and nfc turnover
                [18/05/17, 4:41:09 PM] Ivo Ralf Müller: This means we are no longer using sales of 19% and sales 7%. We can turn it off. But we have to add 4 new values
                [18/05/17, 4:41:58 PM] Ivo Ralf Müller: BAR Umsatz 19%, BAR Umsatz 7%, NFC Umsatz 19%, NFC Umsatz 7%



                github #74
                */


//        json.put(AppConstant.Keys.Cash_Sales_7,"10,00");
//        json.put(AppConstant.Keys.Cash_Sales_19,"10,00");
//        json.put(AppConstant.Keys.Nfc_Sales_7,"10,00");
//        json.put(AppConstant.Keys.Nfc_Sales_19,"10,00");
//        String vat1_rate = json.getString(AppConstant.Keys.vat1);
//        String vat1_amount = json.getString(AppConstant.Keys.vat1_amount);
//        String vat2_rate = json.getString(AppConstant.Keys.vat2);
//        String vat2_amount = json.getString(AppConstant.Keys.vat2_amount);


        if (json.has(AppConstant.Keys.Cash_Sales_19)) {
            String cash_sales_19 = json.getString(AppConstant.Keys.Cash_Sales_19);
            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.sales_umsatz_normal, " " + vat1_rate) + context.getString(R.string.space23)).substring(0, 20));
            mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, cash_sales_19) + "\n");
        }
        if (json.has(AppConstant.Keys.Cash_Sales_7)) {
            String cash_sales_7 = json.getString(AppConstant.Keys.Cash_Sales_7);
            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.sales_umsatz_normal, vat2_rate) + context.getString(R.string.space23)).substring(0, 20));
            mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, cash_sales_7) + "\n");
        }

        if (json.has(AppConstant.Keys.Nfc_Sales_19)) {
            String nfc_sales_19 = json.getString(AppConstant.Keys.Nfc_Sales_19);
            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.sales_umsatz_nfc, " " + vat1_rate) + context.getString(R.string.space23)).substring(0, 20));
            mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, nfc_sales_19) + "\n");
        }
        if (json.has(AppConstant.Keys.Nfc_Sales_7)) {
            String nfc_sales_7 = json.getString(AppConstant.Keys.Nfc_Sales_7);
            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.sales_umsatz_nfc, vat2_rate) + context.getString(R.string.space23)).substring(0, 20));
            mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, nfc_sales_7) + "\n");
        }

//end here


        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.vat_without_colon, vat1_rate) + context.getString(R.string.space23)).substring(0, 20));
        mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, vat1_amount) + "\n");

        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.vat_without_colon, " " + vat2_rate) + context.getString(R.string.space23)).substring(0, 20));
        mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, vat2_amount) + "\n");


        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.cancel_order) + context.getString(R.string.space23)).substring(0, 20));
        mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, cancel_orders) + context.getString(R.string.space2) + "(" + canceled_orders + ")" + "\n");
//        mPrinter.addText(R.string.space2 + get4FormattedQuantity(context, cancel_orders) + "\n");
//        mPrinter.addText(context.getString(R.string.space6) + get4FormattedQuantity(context, cancel_orders) + "\n");
        //Comment this to Remove Task 2 to get live 6 december
//        json.put(AppConstant.Keys.bottle_amount, "1,90");
//        json.put(AppConstant.Keys.bottle_num, "17");
        addLine(mPrinter);
        mPrinter.addTextAlign(Printer.ALIGN_LEFT);

        if (json.has(AppConstant.Keys.bottle_amount) && json.has(AppConstant.Keys.bottle_num)) {
            String bottle_refund = json.getString(AppConstant.Keys.bottle_amount);
            String bottle_num = json.getString(AppConstant.Keys.bottle_num);
            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.num_bottle_deposit) + context.getString(R.string.space23)).substring(0, 20));
            mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, bottle_refund) + context.getString(R.string.space2) + "(" + bottle_num + ")" + "\n");
        }


//            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.withdrawal) + context.getString(R.string.space23)).substring(0, 20));
//            mPrinter.addText(context.getString(R.string.euro) + "-" + get4FormattedPriceFor5Digits(context, withdrawal) + "\n");
//
//            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.cash_added) + context.getString(R.string.space23)).substring(0, 20));
//            mPrinter.addText(context.getString(R.string.euro) + "+" + get4FormattedPriceFor5Digits(context, cashadded) + "\n");
//      changed to bar on 19 April 17 https://drive.google.com/open?id=0B6uYF9O7061PbUwwbzlaSl9SaWs
        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.bar_withdrawal) + context.getString(R.string.space23)).substring(0, 20));
        mPrinter.addText(context.getString(R.string.euro) + "-" + get4FormattedPriceFor5Digits(context, withdrawal) + "\n");

        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.bar_cash_added) + context.getString(R.string.space23)).substring(0, 20));
        mPrinter.addText(context.getString(R.string.euro) + "+" + get4FormattedPriceFor5Digits(context, cashadded) + "\n");


//        json.put(AppConstant.Keys.nfcadded, "1,90");
        if (json.has(AppConstant.Keys.nfcadded)) {
            String nfc_added = json.getString(AppConstant.Keys.nfcadded);

            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.nfc_added) + context.getString(R.string.space23)).substring(0, 20));
            mPrinter.addText(context.getString(R.string.euro) + "+" + get4FormattedPriceFor5Digits(context, nfc_added) + "\n");
        }

//        json.put(AppConstant.Keys.nfcwithdraw, "1,90");
        if (json.has(AppConstant.Keys.nfcwithdraw)) {
            String nfcwithdrawal = json.getString(AppConstant.Keys.nfcwithdraw);

            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.nfc_withdrawal) + context.getString(R.string.space23)).substring(0, 20));
            mPrinter.addText(context.getString(R.string.euro) + "-" + get4FormattedPriceFor5Digits(context, nfcwithdrawal) + "\n");
        }
        if (json.has(AppConstant.Keys.Change)) {
            String change = json.getString(AppConstant.Keys.Change);
            change = (change + context.getString(R.string.space8)).substring(0, 8).replace(".", ",");
            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.change_string) + context.getString(R.string.space23)).substring(0, 20));
            mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, change) + "\n");
        }


//        addLine(mPrinter);

        createAccLoginRecieptDetail(context, json, mPrinter, false);
        addLine(mPrinter);
        addLine(mPrinter);
        boldLine(mPrinter, true);
        mPrinter.addTextAlign(Printer.ALIGN_LEFT);

        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.total_sales) + context.getString(R.string.space23)).substring(0, 20));
        mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, total_sales) + "\n");

        mPrinter.addTextAlign(Printer.ALIGN_LEFT);
//        mPrinter.addText(context.getString(R.string.space24) +get4FormattedPrice(context,"0,00"));


//

        return true;
    }


    public static boolean createCashTransactionDetail(Context context, JSONObject json, Printer mPrinter, boolean withdrawal) throws Epos2Exception, JSONException {
        String method = "";
        String cashier_name = json.getString(AppConstant.Keys.Cashier_name);
        String reg_name = json.getString(AppConstant.Keys.Register_name);

        String timestamp = json.getString(AppConstant.Keys.Timestamp);


        StringBuilder textData = new StringBuilder();
        mPrinter.addTextAlign(Printer.ALIGN_LEFT);
        boldLine(mPrinter, true);
        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.order_date) + context.getString(R.string.space15)).substring(0, 20));
        boldLine(mPrinter, false);
        mPrinter.addText(timestamp + "\n");

//        if (!withdrawal) {
//            String receipt_no = json.getString(AppConstant.Keys.Sno);
////            String receipt_no = "123";
//            boldLine(mPrinter, true);
//            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.receipt_sno) + context.getString(R.string.space15)).substring(0, 20));
//
//            boldLine(mPrinter, false);
//            mPrinter.addText(receipt_no + "\n");
//        }

        boldLine(mPrinter, true);
        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.served_by) + context.getString(R.string.space15)).substring(0, 20));
        boldLine(mPrinter, false);
        mPrinter.addText(cashier_name + "\n");
        boldLine(mPrinter, true);
        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.register_name) + context.getString(R.string.space15)).substring(0, 20));
        boldLine(mPrinter, false);
        mPrinter.addText(reg_name + "\n");
//        boldLine(mPrinter, true);
//        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.status) + context.getString(R.string.space15)).substring(0, 20));
//        boldLine(mPrinter, false);
//        mPrinter.addText(status + "\n");
        addLine(mPrinter);
//        String drawer_amount = json.getString(AppConstant.Keys.Drawer_Amount);
        String reg_amount = json.getString(AppConstant.Keys.Reg_Amount);
        String trans_amount = json.getString(AppConstant.Keys.Trans_Amount);
        String total = json.getString(AppConstant.Keys.Total);
//        String trans_amount = "582,05";


        mPrinter.addTextAlign(Printer.ALIGN_LEFT);
//        float r_amount = Float.parseFloat(reg_amount);
//        float d_amount = Float.parseFloat(drawer_amount);
//        float balance = r_amount - d_amount;
        reg_amount = (reg_amount + context.getString(R.string.space8)).substring(0, 8).replace(".", ",");
//        drawer_amount = String.format(java.util.Locale.US, "%.2f", d_amount).replace(".", ",");
//        drawer_amount = (drawer_amount + context.getString(R.string.space8)).substring(0, 8).replace(".", ",");
        trans_amount = (trans_amount + context.getString(R.string.space8)).substring(0, 8).replace(".", ",");
        total = (total + context.getString(R.string.space8)).substring(0, 8).replace(".", ",");
//        String balance_string = ("" + balance + context.getString(R.string.space8)).substring(0, 6);
//        String balance_str = String.format(java.util.Locale.US, "%.2f", balance).replace(".", ",").replace("-", "");
//        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.cash_amount) + context.getString(R.string.space23)).substring(0, 20));
//        mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPrice(context, reg_amount) + "\n");

        if (withdrawal) {


            if (json.has(AppConstant.Keys.Change)) {
                String change = json.getString(AppConstant.Keys.Change);
                change = (change + context.getString(R.string.space8)).substring(0, 8).replace(".", ",");
                mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.change_string) + context.getString(R.string.space23)).substring(0, 20));
                mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPrice(context, change) + "\n");
            }
            addLine(mPrinter);
            boldLine(mPrinter, true);
            mPrinter.addTextAlign(Printer.ALIGN_LEFT);
            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.withdraw_string) + context.getString(R.string.space23)).substring(0, 20));
            mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPrice(context, trans_amount) + "\n");
            boldLine(mPrinter, false);

//            mPrinter.addText(context.getString(R.string.euro) + R.string.space1 + get4FormattedPrice(context, trans_amount) + "\n");
        } else {
            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.add_string) + context.getString(R.string.space23)).substring(0, 20));
            mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPrice(context, trans_amount) + "\n");

        }
//        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.add_nfc_total_string) + context.getString(R.string.space23)).substring(0, 20));
//        mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPrice(context, total) + "\n");
//        if (is_open) {
        addLine(mPrinter);
        addLine(mPrinter);
        mPrinter.addTextAlign(Printer.ALIGN_LEFT);
        boldLine(mPrinter, true);
//        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.total) + context.getString(R.string.space23)).substring(0, 20));
//        mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPrice(context, total) + "\n");
//        }
        mPrinter.addFeedLine(2);
        if (withdrawal) {
            mPrinter.addFeedLine(4);
            addContinousLine(mPrinter);
            mPrinter.addTextAlign(Printer.ALIGN_LEFT);
            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.served_by) + context.getString(R.string.space1) + cashier_name + "\n"));
            mPrinter.addFeedLine(6);
            addContinousLine(mPrinter);
            mPrinter.addTextAlign(Printer.ALIGN_LEFT);
            mPrinter.addText(context.getString(R.string.space2) + context.getString(R.string.withdraw_static_footer));
            mPrinter.addFeedLine(2);

        }
        mPrinter.addCut(Printer.CUT_FEED);


        return true;
    }


    public static boolean createLogoutCashTransactionDetail(Context context, JSONObject json, Printer mPrinter, boolean withdrawal) throws Epos2Exception, JSONException {
        String method = "";
        String cashier_name = json.getString(AppConstant.Keys.Cashier_name);
        String reg_name = json.getString(AppConstant.Keys.Register_name);

        String timestamp = json.getString(AppConstant.Keys.Timestamp);


        StringBuilder textData = new StringBuilder();
        mPrinter.addTextAlign(Printer.ALIGN_LEFT);
        boldLine(mPrinter, true);
        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.order_date) + context.getString(R.string.space15)).substring(0, 20));
        boldLine(mPrinter, false);
        mPrinter.addText(timestamp + "\n");

//        if (!withdrawal) {
//            String receipt_no = json.getString(AppConstant.Keys.Sno);
////            String receipt_no = "123";
//            boldLine(mPrinter, true);
//            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.receipt_sno) + context.getString(R.string.space15)).substring(0, 20));
//
//            boldLine(mPrinter, false);
//            mPrinter.addText(receipt_no + "\n");
//        }

        boldLine(mPrinter, true);
        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.served_by) + context.getString(R.string.space15)).substring(0, 20));
        boldLine(mPrinter, false);
        mPrinter.addText(cashier_name + "\n");
        boldLine(mPrinter, true);
        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.register_name) + context.getString(R.string.space15)).substring(0, 20));
        boldLine(mPrinter, false);
        mPrinter.addText(reg_name + "\n");
//        boldLine(mPrinter, true);
//        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.status) + context.getString(R.string.space15)).substring(0, 20));
//        boldLine(mPrinter, false);
//        mPrinter.addText(status + "\n");
        addLine(mPrinter);
//        String drawer_amount = json.getString(AppConstant.Keys.Drawer_Amount);
        String reg_amount = json.getString(AppConstant.Keys.Reg_Amount);
        String trans_amount = json.getString(AppConstant.Keys.Trans_Amount);
        String total = json.getString(AppConstant.Keys.Total);
//        String trans_amount = "582,05";
        String drawer_amount = json.getString(AppConstant.Keys.Drawer_Amount);
        float r_amount = Float.parseFloat(reg_amount.replace(",", "."));
        float d_amount = Float.parseFloat(drawer_amount);
        float t_amount = Float.parseFloat(trans_amount.replace(",", "."));


        mPrinter.addTextAlign(Printer.ALIGN_LEFT);
//        float r_amount = Float.parseFloat(reg_amount);
//        float d_amount = Float.parseFloat(drawer_amount);
//        float balance = r_amount - d_amount;
        reg_amount = (reg_amount + context.getString(R.string.space8)).substring(0, 8).replace(".", ",");
//        drawer_amount = String.format(java.util.Locale.US, "%.2f", d_amount).replace(".", ",");
//        drawer_amount = (drawer_amount + context.getString(R.string.space8)).substring(0, 8).replace(".", ",");
        trans_amount = (trans_amount + context.getString(R.string.space8)).substring(0, 8).replace(".", ",");
        total = (total + context.getString(R.string.space8)).substring(0, 8).replace(".", ",");
//        String balance_string = ("" + balance + context.getString(R.string.space8)).substring(0, 6);
//        String balance_str = String.format(java.util.Locale.US, "%.2f", balance).replace(".", ",").replace("-", "");
//        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.cash_amount) + context.getString(R.string.space23)).substring(0, 20));
//        mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPrice(context, reg_amount) + "\n");

//        json.put(AppConstant.Keys.Drawer_Amount,"16.50");

        if (json.has(AppConstant.Keys.Reg_Amount) && json.has(AppConstant.Keys.Drawer_Amount)) {


//            float balance = r_amount - (d_amount+t_amount);
            float balance = r_amount - (d_amount);
            reg_amount = (reg_amount + context.getString(R.string.space8)).substring(0, 8).replace(".", ",");
            drawer_amount = String.format(java.util.Locale.US, "%.2f", d_amount).replace(".", ",");
            drawer_amount = (drawer_amount + context.getString(R.string.space8)).substring(0, 8).replace(".", ",");
            String balance_string = ("" + balance + context.getString(R.string.space8)).substring(0, 6);
            String balance_str = String.format(java.util.Locale.US, "%.2f", balance).replace(".", ",").replace("-", "");

            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.cash_amount) + context.getString(R.string.space23)).substring(0, 20));
            mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, reg_amount) + "\n");
            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.cash_counted) + context.getString(R.string.space23)).substring(0, 20));
            mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, drawer_amount) + "\n");
            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.cash_diff) + context.getString(R.string.space23)).substring(0, 20));
            mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, balance_str) + "\n");
            //                #122 this feature will be available in future
//                if(balance==0) {
//                    mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPriceFor5Digits(context, balance_str) + "\n");
//                } else if(balance>0) {
//                    mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.positive) + get4FormattedPriceFor5Digits(context, balance_str) + "\n");
//                } else if(balance<0) {
//                    mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.negative) + get4FormattedPriceFor5Digits(context, balance_str) + "\n");
//                }


        }


        if (withdrawal) {

            addLine(mPrinter);
            if (json.has(AppConstant.Keys.Change)) {
                mPrinter.addTextAlign(Printer.ALIGN_LEFT);
                String change = json.getString(AppConstant.Keys.Change);
                change = (change + context.getString(R.string.space8)).substring(0, 8).replace(".", ",");
                mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.change_string) + context.getString(R.string.space23)).substring(0, 20));
                mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPrice(context, change) + "\n");
            }

            addLine(mPrinter);
            boldLine(mPrinter, true);
            mPrinter.addTextAlign(Printer.ALIGN_LEFT);
            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.withdraw_string) + context.getString(R.string.space23)).substring(0, 20));
            mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPrice(context, trans_amount) + "\n");
            boldLine(mPrinter, false);
//            mPrinter.addText(context.getString(R.string.euro) + R.string.space1 + get4FormattedPrice(context, trans_amount) + "\n");
        } else {
            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.add_string) + context.getString(R.string.space23)).substring(0, 20));
            mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPrice(context, trans_amount) + "\n");

        }
//        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.add_nfc_total_string) + context.getString(R.string.space23)).substring(0, 20));
//        mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPrice(context, total) + "\n");
//        if (is_open) {
        addLine(mPrinter);
        addLine(mPrinter);
        mPrinter.addTextAlign(Printer.ALIGN_LEFT);
        boldLine(mPrinter, true);
//        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.total) + context.getString(R.string.space23)).substring(0, 20));
//        mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPrice(context, total) + "\n");
//        }
        mPrinter.addFeedLine(2);
        if (withdrawal) {
            mPrinter.addFeedLine(4);
            addContinousLine(mPrinter);
            mPrinter.addTextAlign(Printer.ALIGN_LEFT);
            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.served_by) + context.getString(R.string.space1) + cashier_name + "\n"));
            mPrinter.addFeedLine(6);
            addContinousLine(mPrinter);
            mPrinter.addTextAlign(Printer.ALIGN_LEFT);
            mPrinter.addText(context.getString(R.string.space2) + context.getString(R.string.withdraw_static_footer));
            mPrinter.addFeedLine(2);

        }
        mPrinter.addCut(Printer.CUT_FEED);


        return true;
    }

    public static boolean createNFCCashTransactionDetail(Context context, JSONObject json, Printer mPrinter, boolean withdrawal) throws Epos2Exception, JSONException {
        String method = "";
        String cashier_name = json.getString(AppConstant.Keys.Cashier_name);
        String reg_name = json.getString(AppConstant.Keys.Register_name);

        String timestamp = json.getString(AppConstant.Keys.Timestamp);


        StringBuilder textData = new StringBuilder();
        mPrinter.addTextAlign(Printer.ALIGN_LEFT);
        boldLine(mPrinter, true);
        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.order_date) + context.getString(R.string.space15)).substring(0, 20));
        boldLine(mPrinter, false);
        mPrinter.addText(timestamp + "\n");

//        if (!withdrawal) {
//            String receipt_no = json.getString(AppConstant.Keys.Sno);
////            String receipt_no = "123";
//            boldLine(mPrinter, true);
//            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.receipt_sno) + context.getString(R.string.space15)).substring(0, 20));
//
//            boldLine(mPrinter, false);
//            mPrinter.addText(receipt_no + "\n");
//        }

        boldLine(mPrinter, true);
        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.served_by) + context.getString(R.string.space15)).substring(0, 20));
        boldLine(mPrinter, false);
        mPrinter.addText(cashier_name + "\n");
        boldLine(mPrinter, true);
        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.register_name) + context.getString(R.string.space15)).substring(0, 20));
        boldLine(mPrinter, false);
        mPrinter.addText(reg_name + "\n");
//        boldLine(mPrinter, true);
//        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.status) + context.getString(R.string.space15)).substring(0, 20));
//        boldLine(mPrinter, false);
//        mPrinter.addText(status + "\n");
        addLine(mPrinter);
//        String drawer_amount = json.getString(AppConstant.Keys.Drawer_Amount);
        String reg_amount = json.getString(AppConstant.Keys.Reg_Amount);
        String trans_amount = json.getString(AppConstant.Keys.Trans_Amount);
        String total = json.getString(AppConstant.Keys.Total);
//        String trans_amount = "582,05";


        mPrinter.addTextAlign(Printer.ALIGN_LEFT);
//        float r_amount = Float.parseFloat(reg_amount);
//        float d_amount = Float.parseFloat(drawer_amount);
//        float balance = r_amount - d_amount;
        reg_amount = (reg_amount + context.getString(R.string.space8)).substring(0, 8).replace(".", ",");
//        drawer_amount = String.format(java.util.Locale.US, "%.2f", d_amount).replace(".", ",");
//        drawer_amount = (drawer_amount + context.getString(R.string.space8)).substring(0, 8).replace(".", ",");
        trans_amount = (trans_amount + context.getString(R.string.space8)).substring(0, 8).replace(".", ",");
        total = (total + context.getString(R.string.space8)).substring(0, 8).replace(".", ",");
//        String balance_string = ("" + balance + context.getString(R.string.space8)).substring(0, 6);
//        String balance_str = String.format(java.util.Locale.US, "%.2f", balance).replace(".", ",").replace("-", "");
//        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.cash_amount) + context.getString(R.string.space23)).substring(0, 20));
//        mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPrice(context, reg_amount) + "\n");

        if (withdrawal) {
            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.withdraw_nfc_string) + context.getString(R.string.space23)).substring(0, 22));
            mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPrice(context, trans_amount) + "\n");

//            mPrinter.addText(context.getString(R.string.euro) + R.string.space1 + get4FormattedPrice(context, trans_amount) + "\n");
        } else {
            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.add_nfc_string) + context.getString(R.string.space23)).substring(0, 22));
            mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPrice(context, trans_amount) + "\n");

        }
        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.add_nfc_total_string) + context.getString(R.string.space23)).substring(0, 22));
        mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPrice(context, total) + "\n");
//        if (is_open) {
        addLine(mPrinter);
        addLine(mPrinter);
        mPrinter.addTextAlign(Printer.ALIGN_LEFT);
        boldLine(mPrinter, true);
//        mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.total) + context.getString(R.string.space23)).substring(0, 20));
//        mPrinter.addText(context.getString(R.string.euro) + context.getString(R.string.space1) + get4FormattedPrice(context, total) + "\n");
//        }
        mPrinter.addFeedLine(2);
//        if (withdrawal) {
//            mPrinter.addFeedLine(4);
//            addContinousLine(mPrinter);
//            mPrinter.addTextAlign(Printer.ALIGN_LEFT);
//            mPrinter.addText(context.getString(R.string.space2) + (context.getString(R.string.served_by) + context.getString(R.string.space1) + cashier_name + "\n"));
//            mPrinter.addFeedLine(6);
//            addContinousLine(mPrinter);
//            mPrinter.addTextAlign(Printer.ALIGN_LEFT);
//            mPrinter.addText(context.getString(R.string.space2) + context.getString(R.string.withdraw_static_footer));
//            mPrinter.addFeedLine(2);
//
//        }
        mPrinter.addCut(Printer.CUT_FEED);


        return true;
    }


    public static boolean createRecieptData(Context context, Printer mPrinter) throws Epos2Exception {

        if (mPrinter == null) {
            return false;
        }
        String method = "";
        method = "addFeedLine";
        mPrinter.addFeedLine(1);
        StringBuffer textData = new StringBuffer();
        textData.append("THE STORE 123 (555) 555 â€“ 5555\n");
        textData.append("STORE DIRECTOR â€“ John Smith\n");
        textData.append("\n");
        textData.append("7/01/07 16:58 6153 05 0191 134\n");
        textData.append("ST# 21 OP# 001 TE# 01 TR# 747\n");
        textData.append("------------------------------\n");
        method = "addText";
        mPrinter.addText(textData.toString());
        textData.delete(0, textData.length());

        textData.append("400 OHEIDA 3PK SPRINGF  9.99 R\n");
        textData.append("410 3 CUP BLK TEAPOT    9.99 R\n");
        textData.append("445 EMERIL GRIDDLE/PAN 17.99 R\n");
        textData.append("438 CANDYMAKER ASSORT   4.99 R\n");
        textData.append("474 TRIPOD              8.99 R\n");
        textData.append("433 BLK LOGO PRNTED ZO  7.99 R\n");
        textData.append("458 AQUA MICROTERRY SC  6.99 R\n");
        textData.append("493 30L BLK FF DRESS   16.99 R\n");
        textData.append("407 LEVITATING DESKTOP  7.99 R\n");
        textData.append("441 **Blue Overprint P  2.99 R\n");
        textData.append("476 REPOSE 4PCPM CHOC   5.49 R\n");
        textData.append("461 WESTGATE BLACK 25  59.99 R\n");
        textData.append("------------------------------\n");
        method = "addText";
        mPrinter.addText(textData.toString());
        textData.delete(0, textData.length());

        textData.append("SUBTOTAL                160.38\n");
        textData.append("TAX                      14.43\n");
        method = "addText";
        mPrinter.addText(textData.toString());
        textData.delete(0, textData.length());

        method = "addTextSize";
        mPrinter.addTextSize(2, 2);
        method = "addText";
        mPrinter.addText("TOTAL    174.81\n");
        method = "addTextSize";
        mPrinter.addTextSize(1, 1);
        method = "addFeedLine";
        mPrinter.addFeedLine(1);

        textData.append("CASH                    200.00\n");
        textData.append("CHANGE                   25.19\n");
        textData.append("------------------------------\n");
        method = "addText";
        mPrinter.addText(textData.toString());
        textData.delete(0, textData.length());

        textData.append("Purchased item total number\n");
        textData.append("Sign Up and Save !\n");
        textData.append("With Preferred Saving Card\n");
        method = "addText";
        mPrinter.addText(textData.toString());
        textData.delete(0, textData.length());
        method = "addFeedLine";
        mPrinter.addFeedLine(2);


        method = "addCut";
        mPrinter.addCut(Printer.CUT_FEED);

        method = "Open Drawer";
        return true;
//            Toast.makeText(SettingsActivity.this, ""+((SpnModelsItem)mSpnPin.getSelectedItem()).getModelConstant(), Toast.LENGTH_SHORT).show();
//            Toast.makeText(SettingsActivity.this, ""+((SpnModelsItem)mSpnPulse.getSelectedItem()).getModelConstant(), Toast.LENGTH_SHORT).show();
//        mPrinter.addPulse(printerpin, printerpulse);
    }

    private static void addLine(Printer mPrinter) throws Epos2Exception {
        mPrinter.addTextAlign(Printer.ALIGN_CENTER);
        mPrinter.addTextSize(1, 1);
        boldLine(mPrinter, true);
        mPrinter.addText("-----------------------------------------\n");

        boldLine(mPrinter, false);
    }

    private static void addContinousLine(Printer mPrinter) throws Epos2Exception {
        mPrinter.addTextAlign(Printer.ALIGN_CENTER);
        mPrinter.addTextSize(1, 1);
        boldLine(mPrinter, true);

        mPrinter.addText("_________________________________________\n");
        boldLine(mPrinter, false);
    }

    private static void boldLine(Printer mPrinter, boolean bold) throws Epos2Exception {
        if (bold) {
            mPrinter.addTextStyle(Printer.PARAM_DEFAULT, Printer.PARAM_DEFAULT, Printer.TRUE, Printer.PARAM_DEFAULT);

        } else {
            mPrinter.addTextStyle(Printer.PARAM_DEFAULT, Printer.PARAM_DEFAULT, Printer.FALSE, Printer.PARAM_DEFAULT);

        }
    }

    public static boolean addBarcode(Printer mPrinter, String barcode, int height, int width) throws Epos2Exception {
        if (mPrinter == null) {
            return false;
        }
        final int barcodeWidth = 2;
        final int barcodeHeight = 100;
        String method = "addBarcode";
        mPrinter.addBarcode(barcode,
                Printer.BARCODE_CODE39,
                Printer.HRI_BELOW,
                Printer.FONT_A,
                barcodeWidth,
                barcodeHeight);
        return true;
    }

//

    public static void parsePrintData(Printer mPrinter, String response) throws JSONException, Epos2Exception {
//        {"data":[{"type":"FL","lines":1},{"type":"text","text":"THE STORE 123 (555) 555 â€“ 5555"},{"type":"size","width":2,"height":2},{"type":"barcode","barcode":"01209457","width":2,"height":100}]}
        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);

            appendPrintData(json, mPrinter);
        }
    }

//

    private static void appendPrintData(JSONObject json, Printer mPrinter) throws JSONException, Epos2Exception {
        final String TYPE_FL = "FL";


        final String TYPE_text = "text";
        final String TYPE_textsize = "textsize";
        final String TYPE_barcode = "barcode";
        final String TYPE_image = "image";

        String type = json.getString("type");

        switch (type.toLowerCase()) {
            case TYPE_FL:
                int num_lines = json.getInt("lines");
                mPrinter.addFeedLine(num_lines);
                break;
            case TYPE_text:
                String text = "";
                StringBuffer textData = new StringBuffer();
                text = json.getString("text");
                textData.append(text + "\n");

                mPrinter.addText(textData.toString());
                textData.delete(0, textData.length());


                break;
            case TYPE_textsize:
                int width = json.getInt("width");
                int height = json.getInt("height");

                mPrinter.addTextSize(width, height);


                break;
            case TYPE_barcode:
                width = json.getInt("width");
                height = json.getInt("height");
                String barcode = json.getString("barcode");

                addBarcode(mPrinter, barcode, height, width);
                break;
            case TYPE_image:

                break;

        }


    }


    public static String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month - 1];
    }

//    public static String ProcessImageChosen(final Context context, final ChosenImage chosenImage) {
//
//
////                    progressBar.setVisibility(View.GONE);
//
//        String filepath = chosenImage.getFileThumbnail();
//        String dest_filename = null;
//
//        int size = getFileSize(filepath);
//        Log.i("Size", "" + size);
//        if (size < AppConstant.LOGO_SIZE) {
//
//            dest_filename = copyFileToLocalStorage(context, filepath);
//            Log.i("EposHelper", "dest_filename " + dest_filename);
//            AppConstant.SharedPref.saveLogo(context, dest_filename);
//
//
//        }
//
//        return dest_filename;
//
//    }

    /**
     * Method to get filesize
     *
     * @param filepath
     * @return size in MB otherwise -1 if file doesnt exists
     */
    private static int getFileSize(String filepath) {
        File filenew = new File(filepath);
        int file_size = -1;
        if (filenew != null && filenew.exists()) {
            file_size = Integer.parseInt(String.valueOf(filenew.length() / (1024)));
        }
        return file_size;
    }

//    private static String copyFileToLocalStorage(Context context, String sourcefilepath) {
//
//        String sourcevideothumbname = FileSystemUtil.getNameOnly(sourcefilepath);
//        String destvideothumbname = get_Folder_Path(context) + File.separator + sourcevideothumbname;
//        FileSystemUtil.copyFile(sourcefilepath, destvideothumbname);
//        return destvideothumbname;
//
//
//    }
//
//    public static String get_Root_Path(Context context) {
////            return context.getFilesDir() + File.separator + ".Breakthrough";
//        return FileSystemUtil.getInternalStoragePath(context) + File.separator + ROOT_FOLDER_NAME;
//
//    }


//    public static String get_Folder_Path(Context context) {
//        return get_Root_Path(context) + SEPERATOR + FOLDER + SEPERATOR;
//    }

    public static void forceCloseApp(Context context) {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(homeIntent);
        System.exit(1);
    }

    public static boolean deleteFileFromStorage(String filepath) {
        File fdelete = new File(filepath);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }


}
