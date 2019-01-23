package com.inerun.courier.printer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.epson.epos2.ConnectionListener;
import com.epson.epos2.Epos2CallbackCode;
import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;
import com.inerun.courier.R;
import com.inerun.courier.activity_driver.ExceptionHandlerActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.Queue;

public class PrinterService extends Service {


    private Context mContext;
    private String LOG_TAG = "PrinterService";
    private String method = "";
    private IBinder mBinder = new MyBinder();
    private Queue<MyPrinterThread> myQueue;

    private MyPrinterThread thread;
    private String printertarget = "USB:/dev/bus/usb/001/002";
    private int printerseries = Printer.TM_T20;
    private int printerlang = Printer.MODEL_ANK;
    private int printerpulse = Printer.PULSE_100;
    private int printerpin = Printer.DRAWER_2PIN;
    private int start = 0;
    private int end = 1;
    private int style = Printer.PARAM_DEFAULT;
    private Printer mPrinter;
    private String log = "";
    boolean printerexceptionshown = false;
    private String printerlogo;

    private ReceiveListener printrecieve_listener = new ReceiveListener() {
        @Override
        public void onPtrReceive(Printer printerObj, final int code, PrinterStatusInfo printerStatusInfo, String printJobId) {

            new Thread(new Runnable() {
                @Override
                public synchronized void run() {
                    try {
                        if(code== Epos2CallbackCode.CODE_SUCCESS) {
                            Log.e("onPtrReceive","printing CODE_SUCCESS"+code);
                            setPrinterStatus(AppConstant.Keys.TYPE_PRINTER_SUCCESS);
                            setPrinterStatus("print completed" + System.currentTimeMillis());
                            end();
                            disconnect();
                            mPrinter.clearCommandBuffer();
                            manageQueue();
                        }else if(code== Epos2CallbackCode.CODE_PRINTING)
                        {
                            Log.e("onPtrReceive","printing"+code);
                        }else
                        {
                            String errormsg=ShowMsg.getCodeTextString(code);
                            setPrinterStatus(AppConstant.Keys.TYPE_PRINTER_ERROR,errormsg);
                            Log.e("onPtrReceive","some error  ,code:"+code+",msg:"+errormsg);
                            end();
                            disconnect();
                            mPrinter.clearCommandBuffer();
                            manageQueue();
                        }
                    } catch (Exception e) {
                        setPrinterStatus(AppConstant.Keys.TYPE_PRINTER_ERROR);
//                        handleException(e);
                    }
//                    setPrinterStatus("Buffer Cleared");
                }
            }).start();


        }
    };


    private static void show(final String msg, final Context context) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setMessage(msg);
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        try {
                            ((Activity) context).finish();
                        } catch (Exception e) {

                        }
                        return;
                    }
                });
                AlertDialog alert = alertDialog.create();
                alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                alert.show();

            }
        });

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = PrinterService.this;
//        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(mContext));
        initData();
        try {
            setPrinterStatus("in onCreate");
            initPrinter();
        } catch (Exception e) {
            setPrinterStatus(""+e.toString());
            e.printStackTrace();
            stopSelf();

        } catch (Error e) {
            e.printStackTrace();
            setPrinterStatus(""+e.toString());
            stopSelf();

        }

//

    }

    @Override
    public IBinder onBind(Intent intent) {
        setPrinterStatus("in onBind");
        method = "OnBind Connect Printer";
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        setPrinterStatus("in onRebind");

    }

    public void addPrintJob(int type, String data) {

        if (myQueue == null) {
            myQueue = new LinkedList<MyPrinterThread>();
        }
        MyPrinterThread tempThread = new MyPrinterThread(type, data);
        myQueue.add(tempThread);
        if (thread == null) {
            thread = myQueue.poll();
            thread.execute();
        }


    }


//    public void printData(TextView statustv, int id) {
//
////        this.statustv = statustv;
//
//        Log.i(LOG_TAG, "printDataCommand ::" + id);
//
//        if (myQueue == null) {
//            myQueue = new LinkedList<MyPrinterThread>();
//        }
//        MyPrinterThread tempThread = new MyPrinterThread("Print", id);
//        myQueue.add(tempThread);
//        if (thread == null) {
//            thread = myQueue.poll();
//            thread.execute();
//        }
//    }

    @Override
    public boolean onUnbind(Intent intent) {
        setPrinterStatus("in onUnbind");

        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        setPrinterStatus("in onDestroy");


    }

    public void reportSuccess(int id) {
        Toast.makeText(mContext, "SuccessId " + id, Toast.LENGTH_LONG).show();

    }

    private void manageQueue() {
        Log.i(LOG_TAG, "Managing Queue");
        thread = myQueue.poll();
        if (thread != null) {
            thread.execute();
        } else {
            Log.i(LOG_TAG, "Queue size is empty ::" + myQueue.size());
            stopSelf();
        }
    }

    private void initData() {

        if (EPOSHelper.isSettingsSaved(mContext)) {

            printerlogo = AppConstant.SharedPref.getLogo(mContext);
            printertarget = AppConstant.SharedPref.getPrinterTarget(mContext);
            printerseries = AppConstant.SharedPref.getPrinterSeries(mContext);
            printerlang = AppConstant.SharedPref.getPrinterLang(mContext);
            printerpulse = AppConstant.SharedPref.getPrinterPulse(mContext);
            printerpin = AppConstant.SharedPref.getPrinterPin(mContext);


        }

    }

    private void initPrinter() throws Epos2Exception, Exception {
        setPrinterStatus("intialsing");
        Log.i("initPrinter", "printerseries " + printerseries + " printerlang" + printerlang);
        try {


            mPrinter = new Printer(printerseries,
                    printerlang,
                    mContext);
            setPrinterStatus("intialsed");

            mPrinter.setConnectionEventListener(connection_listener);
            mPrinter.setReceiveEventListener(printrecieve_listener);

        } catch (Exception e) {
            Toast.makeText(mContext, R.string.dialog_printer_settings_error_msg, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            throw e;
        }

    }

    ConnectionListener connection_listener = new ConnectionListener() {
        @Override
        public void onConnection(Object deviceObj, int eventType) {
            String event = "Unknown Event";
            Log.i("PrinterConnection", "connection_listener" + " eventtype" + eventType);
            switch (eventType) {

                case EVENT_DISCONNECT:
                    event = "EVENT_DISCONNECT";
                    break;
                case EVENT_RECONNECT:
                    event = "EVENT_RECONNECT";
                    break;
                case EVENT_RECONNECTING:
                    event = "EVENT_RECONNECTING";
                    break;
            }
            Log.i("PrinterConnection", "connection_listener" + " eventtype" + eventType+event);

        }
    };

    private void connect() throws Exception {
//        try {

//            setPrinterStatus("Connecting");
//        new Handler(Looper.getMainLooper()).post(new Runnable() {
//            @Override
//            public void run() {
        try {
            mPrinter.connect(printertarget, Printer.PARAM_DEFAULT);

        } catch (Exception e) {
            setPrinterStatus("Connect Exception");
            setPrinterStatus(AppConstant.Keys.TYPE_PRINTER_ERROR);
//            stopSelf();
            throw e;

        }
//            }
//        });

//            setPrinterStatus("Connected");
//        } catch (Epos2Exception e) {
//            setPrinterStatus("Connect Exception");
//            setPrinterStatus(AppConstant.Keys.TYPE_PRINTER_ERROR);
//
//        }


    }

    private void begin() throws Exception {

        setPrinterStatus("begin Transaction");
//        new Handler(Looper.getMainLooper()).post(new Runnable() {
//
//            @Override
//            public void run() {
        try {
            mPrinter.beginTransaction();
            setPrinterStatus("Begined");
        } catch (Exception e) {
            setPrinterStatus("Begin Exception");
            setPrinterStatus(AppConstant.Keys.TYPE_PRINTER_ERROR);
            throw e;

        }
//            }
//        }
//        );

    }

    private void printReceipt() throws Epos2Exception {

        setPrinterStatus("Adding Data");
        mPrinter.addText("Sample Print" + "\n");


        mPrinter.addFeedLine(2);
        mPrinter.addCut(Printer.CUT_FEED);
        setPrinterStatus("checking Status");
        checkStatus();
        setPrinterStatus("Sending Command To Printer");
        mPrinter.sendData(Printer.PARAM_DEFAULT);


    }

    private boolean checkStatus() {
        PrinterStatusInfo statusInfo = mPrinter.getStatus();
        if (statusInfo.getConnection() == Printer.TRUE) {
            setPrinterStatus("printer is connected");

            if (statusInfo.getOnline() == Printer.TRUE) {
                setPrinterStatus("printer is online");
                return true;
            } else {
                setPrinterStatus("printer is offline");
                return false;
            }
        } else {
            setPrinterStatus("printer is not connected");
            return false;
        }
    }

    private void end() throws Exception {


        setPrinterStatus("ending Transaction");
        mPrinter.endTransaction();
        setPrinterStatus(" Transaction Ended");
    }

    private void disconnect() throws Exception {
        setPrinterStatus("Disconnecting printer");
        mPrinter.disconnect();
        setPrinterStatus(" Printer disconnected");
    }

    private void handleException(Exception e) {
        StringWriter stackTrace = new StringWriter();
        e.printStackTrace(new PrintWriter(stackTrace));
        System.err.println(stackTrace);
        //Intent intent = new Intent(myContext, myActivityClass);
        String s = stackTrace.toString();
        setPrinterStatus(s);
        if (!printerexceptionshown) {
            ShowMsg.showException(e, "", mContext);
            printerexceptionshown = true;
        }
        manageQueue();
        stopSelf();
    }

    public void setPrinterStatus(String status) {
//        Log.i("setPrinterStatus",status);
        if (AppConstant.SharedPref.isLogsActive(mContext)) {
            if (log.length() > 5000) {
                log = "";
            }
            log = log + "\n " + status;
//        Handler handler=new Handler(Looper.getMainLooper());
//        handler.post(new Runnable(){
//            @Override
//            public void run() {
//                statustv.setText(log);
//            }});
            Intent i = new Intent("LOG_UPDATED");
            i.putExtra("msg", log);

            mContext.sendBroadcast(i);
        }
    }

    public void setPrinterStatus(int status) {
//        if (AppConstant.SharedPref.isLogsActive(mContext)) {
            log = log + "\n " + status;
//        Handler handler=new Handler(Looper.getMainLooper());
//        handler.post(new Runnable(){
//            @Override
//            public void run() {
//                statustv.setText(log);
//            }});
            Intent i = new Intent("LOG_UPDATED");
            i.putExtra(AppConstant.Keys.Type, AppConstant.Keys.TYPE_PRINTER_STATUS);
            i.putExtra(AppConstant.Keys.Status, status);

            mContext.sendBroadcast(i);
//        }
    }
    public void setPrinterStatus(int status,String msg) {
//        if (AppConstant.SharedPref.isLogsActive(mContext)) {
            log = log + "\n " + status;
//        Handler handler=new Handler(Looper.getMainLooper());
//        handler.post(new Runnable(){
//            @Override
//            public void run() {
//                statustv.setText(log);
//            }});
            Intent i = new Intent("LOG_UPDATED");
            i.putExtra(AppConstant.Keys.Type, AppConstant.Keys.TYPE_PRINTER_STATUS);
            i.putExtra(AppConstant.Keys.Status, status);
            i.putExtra(AppConstant.Keys.Msg, msg);

            mContext.sendBroadcast(i);
//        }
    }


    public void clearLog() {
        log = "";
    }


    public class MyBinder extends Binder {
        public PrinterService getService() {
            return PrinterService.this;
        }
    }

    class MyPrinterThread extends AsyncTask<Void, String, Exception> {
        private int type = -1;
        private String data;
        private String LOG_TAG = "MyPrinterThread";


        public MyPrinterThread(int type, String data) {

            this.data = data;
            this.type = type;


        }


        @Override
        protected Exception doInBackground(Void... params) {
            try {

                setPrinterStatus("printjobstarted" + System.currentTimeMillis());


                executePrintJob();

            } catch (Exception e) {
                e.printStackTrace();
                StringWriter stackTrace = new StringWriter();
                e.printStackTrace(new PrintWriter(stackTrace));
                System.err.println(stackTrace);
                //Intent intent = new Intent(myContext, myActivityClass);
                String s = stackTrace.toString();
                setPrinterStatus("Exception " + s);
                return e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Exception e) {
            super.onPostExecute(e);
            if (e != null) {
//                setPrinterStatus(AppConstant.Keys.TYPE_PRINTER_ERROR);
                handleException(e);

            } else {
                printerexceptionshown = false;
//                setPrinterStatus(AppConstant.Keys.TYPE_PRINTER_SUCCESS);
            }

//            show("" + id, PrinterService.this);

        }

        private void executePrintJob() throws InterruptedException, Epos2Exception, JSONException, Exception {


            connect();
            begin();
            processPrintJob();


            printData();

        }

        private void processPrintJob() throws Epos2Exception, JSONException, Exception {
            setPrinterStatus("processinf Print Job Status" + type + "  " + data);


            try {


                if (type == AppConstant.Keys.TYPE_OPEN_DRAWER && mPrinter != null) {
                    setPrinterStatus("Adding Pulse To Printer");

                    mPrinter.addPulse(printerpin, printerpulse);
                } else if (type == AppConstant.Keys.TYPE_PRINT_CANCEL) {
                    createCancelReceiptData();
                } else if (type == AppConstant.Keys.TYPE_PRINT_ADD_CASH) {
//                    createCashTransactionReceiptData(false);
                } else if (type == AppConstant.Keys.TYPE_PRINT_WITHDRAW_CASH) {
//                    createCashTransactionReceiptData(true);
                }else if (type == AppConstant.Keys.TYPE_PRINT_LOGOUT_WITHDRAW_CASH) {
//                    createLogoutCashTransactionReceiptData(true);
                } else if (type == AppConstant.Keys.TYPE_WELCOME_RECEIPT) {
//                    createWelcomeReceiptData();
                } else if (type == AppConstant.Keys.TYPE_PRINT_RECEIPT_ONLY) {
                    createReceiptData(false, false);
                } else if (type == AppConstant.Keys.TYPE_PRINT_RECEIPT) {
                    createReceiptData(true, false);
                } else if (type == AppConstant.Keys.TYPE_PRINT_SUMMARY) {
                    createSummaryData();
                } else if (type == AppConstant.Keys.TYPE_PRINT_ACK) {
                    createAckSlip(mPrinter);
                    return;
                } else if (type == AppConstant.Keys.TYPE_PRINT_RFID_RECEIPT_ONLY) {
                    createReceiptData(false, true);

                } else if (type == AppConstant.Keys.TYPE_PRINT_CASHMACHINE_RECEIPT) {
                    createCashMachineData(printerpin, printerpulse);
                }else if (type == AppConstant.Keys.TYPE_PRINT_NFC_ADD_CASH) {
//                    createNFCCashTransactionReceiptData(false);
                } else if (type == AppConstant.Keys.TYPE_PRINT_NFC_WITHDRAW_CASH) {
//                    createNFCCashTransactionReceiptData(true);
                }

                if (mPrinter != null) {

                    checkStatus();
                }
            } catch (Exception e) {
                StringWriter stackTrace = new StringWriter();
                e.printStackTrace(new PrintWriter(stackTrace));
                System.err.println(stackTrace);
                //Intent intent = new Intent(myContext, myActivityClass);
                String s = stackTrace.toString();
                setPrinterStatus(("Exception " + s));
                Intent intent = (new Intent(PrinterService.this, ExceptionHandlerActivity.class));
                intent.putExtra(AppConstant.Keys.Exception, s);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                (PrinterService.this).startActivity(intent);

            }


//            setPrinterStatus("Sending Command To Printer");


        }


        private void printData() throws Epos2Exception {
//            new Handler(Looper.getMainLooper()).post(new Runnable() {
//                @Override
//                public void run() {
            try {


                mPrinter.sendData(Printer.PARAM_DEFAULT);
            } catch (Exception e) {
                setPrinterStatus("Begin Exception");
                setPrinterStatus(AppConstant.Keys.TYPE_PRINTER_ERROR);
                throw e;
            }
//                }
//            });
        }

        private void createCancelReceiptData() throws JSONException, Epos2Exception, Exception {
            JSONObject json = new JSONObject(data);
            EPOSHelper.createCancelOrderSlip(mContext, mPrinter, json);
        }

//        private void createWelcomeReceiptData() throws JSONException, Epos2Exception, Exception {
//            JSONObject json = new JSONObject(data);
//            String status = json.getString(AppConstant.Keys.Status);
//            String header = "";
//            boolean is_open = (status.equalsIgnoreCase(AppConstant.STATUS_OPEN));
//            if (is_open) {
//                header = mContext.getString(R.string.cash_reciept_open);
//            } else {
//                header = mContext.getString(R.string.cash_reciept_closed);
//            }
//            EPOSHelper.addRecieptLogo(mContext, R.mipmap.logo, mPrinter);
//            EPOSHelper.createAccountRecieptHeader(mContext, json, mPrinter, header);
//            EPOSHelper.createAccRecieptDetail(mContext, json, mPrinter, status, is_open);
////            uncomment this for forceclose
////            if (!is_open) {
//////                setPrinterStatus(AppConstant.Keys.TYPE_APP_FORCE_CLOSE);
////            }
//
//
//        }
//
//        private void createCashTransactionReceiptData(boolean withdrawal) throws JSONException, Epos2Exception, Exception {
//            JSONObject json = new JSONObject(data);
//
//            String header = "";
//
//            if (withdrawal) {
//                header = mContext.getString(R.string. withdraw_cash);
//            } else {
//                header = mContext.getString(R.string.add_cash);
//            }
//            EPOSHelper.addRecieptLogo(mContext, R.mipmap.logo, mPrinter);
//            EPOSHelper.createAccountRecieptHeader(mContext, json, mPrinter, header);
//            EPOSHelper.createCashTransactionDetail(mContext, json, mPrinter, withdrawal);
//
//
//        }
//        private void createLogoutCashTransactionReceiptData(boolean withdrawal) throws JSONException, Epos2Exception, Exception {
//            JSONObject json = new JSONObject(data);
//
//            String header = "";
//
////            if (withdrawal) {
//                header = mContext.getString(R.string.withdraw_cash);
////            } else {
////                header = mContext.getString(R.string.add_cash);
////            }
//            EPOSHelper.addRecieptLogo(mContext, R.mipmap.logo, mPrinter);
//            EPOSHelper.createAccountRecieptHeader(mContext, json, mPrinter, header);
//            EPOSHelper.createLogoutCashTransactionDetail(mContext, json, mPrinter, withdrawal);
//
//
//        }
//        private void createNFCCashTransactionReceiptData(boolean withdrawal) throws JSONException, Epos2Exception, Exception {
//            JSONObject json = new JSONObject(data);
//
//            String header = "";
//
//            if (withdrawal) {
//                header = mContext.getString(R.string.withdraw_nfc_cash);
//            } else {
//                header = mContext.getString(R.string.add_nfc_cash);
//            }
//            EPOSHelper.addRecieptLogo(mContext, R.mipmap.logo, mPrinter);
//            EPOSHelper.createAccountRecieptHeader(mContext, json, mPrinter, header);
//            EPOSHelper.createNFCCashTransactionDetail(mContext, json, mPrinter, withdrawal);
//
//
//        }


        private void createReceiptData(boolean opendrawer, boolean isrfid) throws Epos2Exception, JSONException, Exception {


            if (opendrawer) {
                mPrinter.addPulse(printerpin, printerpulse);
            }
            JSONObject json = new JSONObject(data);
            EPOSHelper.createPaymentSlip(mContext, mPrinter, json, isrfid);


        }


        private void createSummaryData() throws Epos2Exception, JSONException, Exception {


//            if (opendrawer) {
//                mPrinter.addPulse(printerpin, printerpulse);
//            }
//            String data="{\"cashier_name\":\"Admin\",\"register_name\":\"Dussmann\",\"outlet_name\":\"Outlet123\",\"timestamp\":\"21.03.2016   15:15 \",\"register_amount\": \"150\",\"drawer_amount\": \"150\",\"diff\":\"1\",\"message\":\"cash matched\",\"email\":\"kontakt@delightful-express.de\",\"tel\"\n" +
//                    ":\"0351-4804727 \",\"fax\":\"\",\"website\":\"www.dussmann.com\",\"address\":\" Wachsbleichstr 29\",\"postcode\":\"Dresden\"\n" +
//                    ",\"city\":\"01067\",\"Sno\":1,\"total_sales\":\"100,00\",\"cancel_order\":\"20,00\",\"withdrawal\":\"20,00\",\"cash_added\":\"10,00\",\"daily_sum\":\"100,00\",\"month\":\"4\",\"canceled_order\":\"4\",\"vat\":\"7%\",\"vat_amount\":\"51,00\",\"vat2\":\"19%\",\"vat2_amount\":\"81,00\"}";
            JSONObject json = new JSONObject(data);
            EPOSHelper.createSummarySlip(mContext, mPrinter, json);


        }

        private void createCashMachineData(int printerpin, int printerpulse) throws Epos2Exception, JSONException, Exception {


//            if (opendrawer) {
//                mPrinter.addPulse(printerpin, printerpulse);
//            }


            JSONObject json = new JSONObject(data);
            EPOSHelper.createCashMachineSlip(mContext, mPrinter, json);
//            mPrinter.addPulse(printerpin, printerpulse);


        }


    }

    private void createAckSlip(Printer mPrinter) throws Epos2Exception {
//        mPrinter.addText("Hello!!" + "\n");
//        mPrinter.addFeedLine(2);
//        mPrinter.addCut(Printer.CUT_FEED);
//        mPrinter.addPulse(printerpin, printerpulse);
        byte[] print = {0x7b};
        mPrinter.addCommand(print);

    }

}
