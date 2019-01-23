package com.inerun.courier.printer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;

import com.epson.epos2.Epos2CallbackCode;
import com.epson.epos2.Epos2Exception;
import com.inerun.courier.R;


public class ShowMsg {
    public static void showException(Exception e, String method, Context context) {

        String msg = "Die Verbindung zum Drucker wurde unterbrochen.";
        if (e instanceof Epos2Exception) {
            msg = String.format(
                      "%s\n\t%s\n%s\n\t%s",
                      context.getString(R.string.title_err_code),
                      getEposExceptionText(((Epos2Exception) e).getErrorStatus()),
                      context.getString(R.string.title_err_method),
                      method);
        }
        else {
            msg = e.toString();
        }
        msg="Die Verbindung zum Drucker wurde unterbrochen.";
        show(msg, context);
    }

    public static void showResult(int code, String errMsg, Context context) {
        String msg = "";
        if (errMsg.isEmpty()) {
            msg = String.format(
                      "\t%s\n\t%s\n",
                      context.getString(R.string.title_msg_result),
                      getCodeText(code));
        }
        else {
            msg = String.format(
                      "\t%s\n\t%s\n\n\t%s\n\t%s\n",
                      context.getString(R.string.title_msg_result),
                      getCodeText(code),
                      context.getString(R.string.title_msg_description),
                      errMsg);
        }
        show(msg, context);
    }

    public static void showMsg(String msg, Context context) {
        show(msg, context);
    }

    private static void show(final String msg, final Context context) {
//        if(Settings.canDrawOverlays()) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                    alertDialog.setMessage(msg);
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            try {
//                            ((Activity) context).finish();
                            } catch (Exception e) {

                            }
                            return;
                        }
                    });
                    AlertDialog alert = alertDialog.create();
                    try {


                    alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG);
                    alert.show();
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
            });
//        }else
//        {
//
//        }

    }

    private static String getEposExceptionText(int state) {
        String return_text = "";
        switch (state) {
            case    Epos2Exception.ERR_PARAM:
                return_text = "ERR_PARAM";
                break;
            case    Epos2Exception.ERR_CONNECT:
                return_text = "ERR_CONNECT";
                break;
            case    Epos2Exception.ERR_TIMEOUT:
                return_text = "ERR_TIMEOUT";
                break;
            case    Epos2Exception.ERR_MEMORY:
                return_text = "ERR_MEMORY";
                break;
            case    Epos2Exception.ERR_ILLEGAL:
                return_text = "ERR_ILLEGAL";
                break;
            case    Epos2Exception.ERR_PROCESSING:
                return_text = "ERR_PROCESSING";
                break;
            case    Epos2Exception.ERR_NOT_FOUND:
                return_text = "ERR_NOT_FOUND";
                break;
            case    Epos2Exception.ERR_IN_USE:
                return_text = "ERR_IN_USE";
                break;
            case    Epos2Exception.ERR_TYPE_INVALID:
                return_text = "ERR_TYPE_INVALID";
                break;
            case    Epos2Exception.ERR_DISCONNECT:
                return_text = "ERR_DISCONNECT";
                break;
            case    Epos2Exception.ERR_ALREADY_OPENED:
                return_text = "ERR_ALREADY_OPENED";
                break;
            case    Epos2Exception.ERR_ALREADY_USED:
                return_text = "ERR_ALREADY_USED";
                break;
            case    Epos2Exception.ERR_BOX_COUNT_OVER:
                return_text = "ERR_BOX_COUNT_OVER";
                break;
            case    Epos2Exception.ERR_BOX_CLIENT_OVER:
                return_text = "ERR_BOX_CLIENT_OVER";
                break;
            case    Epos2Exception.ERR_UNSUPPORTED:
                return_text = "ERR_UNSUPPORTED";
                break;
            case    Epos2Exception.ERR_FAILURE:
                return_text = "ERR_FAILURE";
                break;
            default:
                return_text = String.format("%d", state);
                break;
        }
        return return_text;
    }

    private static String getCodeText(int state) {
        String return_text = "";
        switch (state) {
            case Epos2CallbackCode.CODE_SUCCESS:
                return_text = "PRINT_SUCCESS";
                break;
            case Epos2CallbackCode.CODE_PRINTING:
                return_text = "PRINTING";
                break;
            case Epos2CallbackCode.CODE_ERR_AUTORECOVER:
                return_text = "ERR_AUTORECOVER";
                break;
            case Epos2CallbackCode.CODE_ERR_COVER_OPEN:
                return_text = "ERR_COVER_OPEN";
                break;
            case Epos2CallbackCode.CODE_ERR_CUTTER:
                return_text = "ERR_CUTTER";
                break;
            case Epos2CallbackCode.CODE_ERR_MECHANICAL:
                return_text = "ERR_MECHANICAL";
                break;
            case Epos2CallbackCode.CODE_ERR_EMPTY:
                return_text = "ERR_EMPTY";
                break;
            case Epos2CallbackCode.CODE_ERR_UNRECOVERABLE:
                return_text = "ERR_UNRECOVERABLE";
                break;
            case Epos2CallbackCode.CODE_ERR_FAILURE:
                return_text = "ERR_FAILURE";
                break;
            case Epos2CallbackCode.CODE_ERR_NOT_FOUND:
                return_text = "ERR_NOT_FOUND";
                break;
            case Epos2CallbackCode.CODE_ERR_SYSTEM:
                return_text = "ERR_SYSTEM";
                break;
            case Epos2CallbackCode.CODE_ERR_PORT:
                return_text = "ERR_PORT";
                break;
            case Epos2CallbackCode.CODE_ERR_TIMEOUT:
                return_text = "ERR_TIMEOUT";
                break;
            case Epos2CallbackCode.CODE_ERR_JOB_NOT_FOUND:
                return_text = "ERR_JOB_NOT_FOUND";
                break;
            case Epos2CallbackCode.CODE_ERR_SPOOLER:
                return_text = "ERR_SPOOLER";
                break;
            case Epos2CallbackCode.CODE_ERR_BATTERY_LOW:
                return_text = "ERR_BATTERY_LOW";
                break;
            default:
                return_text = String.format("%d", state);
                break;
        }
        return return_text;
    }
    public static String getCodeTextString(int state) {
        String return_text = "";
        switch (state) {
            case Epos2CallbackCode.CODE_SUCCESS:
                return_text = "Print succeeded";
                break;
            case Epos2CallbackCode.CODE_PRINTING:
                return_text = "Printing";
                break;
            case Epos2CallbackCode.CODE_ERR_AUTORECOVER:
                return_text = "Automatic recovery error occurred.";
                break;
            case Epos2CallbackCode.CODE_ERR_COVER_OPEN:
                return_text = "Cover open error occurred.";
                break;
            case Epos2CallbackCode.CODE_ERR_CUTTER:
                return_text = "Auto cutter error occurred.";
                break;
            case Epos2CallbackCode.CODE_ERR_MECHANICAL:
                return_text = "Mechanical error occurred.";
                break;
            case Epos2CallbackCode.CODE_ERR_EMPTY:
                return_text = "No paper is left in the roll paper end detector.";
                break;
            case Epos2CallbackCode.CODE_ERR_UNRECOVERABLE:
                return_text = "Unrecoverable error occurred.";
                break;
            case Epos2CallbackCode.CODE_ERR_FAILURE:
                return_text = "Error exists in the requested document syntax.";
                break;
            case Epos2CallbackCode.CODE_ERR_NOT_FOUND:
                return_text = "Printer specified by the device ID does not exist.";
                break;
            case Epos2CallbackCode.CODE_ERR_SYSTEM:
                return_text = "Error occurred with the printing system.";
                break;
            case Epos2CallbackCode.CODE_ERR_PORT:
                return_text = "Error was detected with the communication port.";
                break;
            case Epos2CallbackCode.CODE_ERR_TIMEOUT:
                return_text = "Print timeout occurred.";
                break;
            case Epos2CallbackCode.CODE_ERR_JOB_NOT_FOUND:
                return_text = "Specified print job ID does not exist.";
                break;
            case Epos2CallbackCode.CODE_ERR_SPOOLER:
                return_text = "Print queue is full.";
                break;
            case Epos2CallbackCode.CODE_ERR_BATTERY_LOW:
                return_text = "Battery has run out.";
                break;
            default:
                return_text = String.format("%d", state);
                break;
        }
        return return_text;
    }
}
