package com.inerun.courier.printer;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.inerun.courier.Exception.PrinterExceptions;
import com.inerun.courier.R;

import org.json.JSONObject;

/**
 * Created by vinay on 7/18/2016.
 */

public class PrinterManager {
    private Activity activity;
    private String printerAddress;
    boolean mPrinterServiceBound = false;
    //    ServiceConnection mReaderServiceConnection;
    boolean ACTIVE = true;
    PrinterService mPrinterBoundService;


    public PrinterManager(Activity activity) {
        this.activity = activity;
//        ACTIVE= AppConstant.SharedPref.isReaderActive(activity);

    }


    public void startPrinter() throws PrinterExceptions {
        if (ACTIVE) {
            startPrinterService();
        } else {
            throw new PrinterExceptions(activity.getString(R.string.printer_disabled));
        }
    }

    public void stopPrinter() {

        stopPrinterService();

    }

    public void restartPrinterService() throws PrinterExceptions {
        if (ACTIVE) {
            stopPrinterService();
            startPrinterService();
        }
    }


    public void addRequestToPrinter(int type, String data) throws PrinterExceptions {
        Log.i("PrinterService", "Adding Request " + data);
        if (ACTIVE) {
            if (validateJson(data)) {
                if (mPrinterBoundService != null) {
                    mPrinterBoundService.addPrintJob(type, data);
                } else {
                    throw new PrinterExceptions(activity.getString(R.string.printer_connect_fail));
                }
            } else {
                throw new PrinterExceptions(activity.getString(R.string.invalid_printerjson_error));
            }
        }
    }

    private boolean validateJson(String data) {
        try {
            new JSONObject(data);
            return true;
        } catch (Exception e) {

            return false;
        }
    }


    private void startPrinterService() throws PrinterExceptions {

        printerAddress = AppConstant.SharedPref.getPrinterTarget(activity);
        if (EPOSHelper.isSettingsSaved(activity)) {
            {
                Intent intent = new Intent(activity, PrinterService.class);
                activity.startService(intent);
                activity.bindService(intent, mPrinterServiceConnection, Context.BIND_AUTO_CREATE);
            }
        } else {
            throw new PrinterExceptions(activity.getString(R.string.dialog_pos_settings_error_msg));
        }


    }


    private void stopPrinterService() {
        if (mPrinterServiceBound) {
            activity.unbindService(mPrinterServiceConnection);
            mPrinterServiceBound = false;
        }
        Intent intent = new Intent(activity,
                PrinterService.class);
        activity.stopService(intent);

    }

    private ServiceConnection mPrinterServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mPrinterServiceBound = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PrinterService.MyBinder myBinder = (PrinterService.MyBinder) service;
            mPrinterBoundService = myBinder.getService();
            mPrinterServiceBound = true;
        }
    };

    public boolean ismPrinterServiceBound() {
        return mPrinterServiceBound;
    }

    public void setmPrinterServiceBound(boolean mPrinterServiceBound) {
        this.mPrinterServiceBound = mPrinterServiceBound;
    }

    public PrinterService getService() {
        return mPrinterBoundService;
    }

    public boolean isACTIVE() {
        return ACTIVE;
    }

    public void clearLog() {
        if (getService() != null) {
            getService().clearLog();
        }
    }
}
