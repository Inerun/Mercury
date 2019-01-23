package com.inerun.courier.base;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.inerun.courier.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Class to define show alert dialog and toast.
 *
 * @author Vinay.
 */
public class SweetAlertUtil {

    public static final int TOAST_TYPE = 6;
    private static final long HIDE_DELAY = 1000;
    private static final long DIALOG_VISIBLE_TIME = 1200;
    private static final long DIALOG_VISIBLE_TIME_LONG = 2000;
    private static final long ERROR_VISIBLE_TIME = 2000;
    /**
     * This method is used to show alert dialog.
     *
     * @param context
     * @param alertMessage
     * @param isGoBack
     */
    // @SuppressWarnings("deprecation")
    // public static void showAlertDialog(String alertMessage) {
    // final Context context = BaseActivity.currentVisibleActivity;
    // AlertDialog alertDialog = new AlertDialog.Builder(context).create();
    // alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    // alertDialog.setMessage(alertMessage);
    // alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
    // public void onClick(DialogInterface dialog, int which) {
    // BaseActivity.currentVisibleActivity.finish();
    //
    // }
    // });
    //
    // alertDialog.show();
    // }

//    static ConnectionDialogClickListener dialoglistener;
    private int dialog_id;

    public SweetAlertUtil(int dialog_id) {
        this.dialog_id = dialog_id;
    }

    /**
     * This method is used to show alert dialog.
     *
     * @param context
     */
    // @SuppressWarnings("deprecation")
    // public static void showAlertDialogNotification(String alertMessage) {
    // final Context context = BaseActivity.currentVisibleActivity;
    // AlertDialog alertDialog = new AlertDialog.Builder(context).create();
    // alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    // alertDialog.setMessage(alertMessage);
    // alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
    // public void onClick(DialogInterface dialog, int which) {
    // return;
    // }
    // });
    //
    // alertDialog.show();
    // }
    public static void showToastMessage(Context context, String message) {

        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

    }

    /**
     * This method is used to show alert dialog.
     *
     * @param context
     * @param alertMessage
     */
    @SuppressWarnings("deprecation")
    public static void showAlertDialog(final Context context,
                                       String alertMessage) {
        // final Context context =
        // MobileSaverBaseActivity.currentVisibleActivity;
//        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
//        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        alertDialog.setMessage(alertMessage);
//        alertDialog.setButton("OK", new OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//
//            }
//        });


        new SweetAlertDialog(context)
                .setContentText(alertMessage)
                .show();
    }

    @SuppressWarnings("deprecation")
    public static void showAlertDialogFinishActivity(final Context context,
                                                     String alertMessage) {
        // final Context context =
        // MobileSaverBaseActivity.currentVisibleActivity;
        SweetAlertDialog alertDialog;

        alertDialog = new SweetAlertDialog(context);


        alertDialog.setContentText(alertMessage);
        alertDialog.setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();


    }

    public static SweetAlertDialog showDialogwithNeutralButton(
            Context context, String title, String errormessage, String button,
            SweetAlertDialog.OnSweetClickListener dialogclicklistener) {
        SweetAlertDialog alert;
//        SweetAlertUtil.dialoglistener = dialogclicklistener;
        alert = new SweetAlertDialog(context);

        alert.setTitle(title);
        alert.setContentText(errormessage);
        alert.setConfirmText(button);
        alert.setConfirmClickListener(dialogclicklistener);

        return alert;

    }

    public static SweetAlertDialog showAlertDialogwithListener(
            Context context, int title, int errormessage, int postivebutton,
            int negative_button, SweetAlertDialog.OnSweetClickListener confirmlistener, SweetAlertDialog.OnSweetClickListener cancellistener) {
        SweetAlertDialog alert = null;
//        dialoglistener = listener;

        alert = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);


        alert.setTitleText(context.getString(title))
                .setContentText(context.getString(errormessage))
                .setConfirmText(context.getString(postivebutton))
                .setConfirmClickListener(confirmlistener)
                .setCancelText(context.getString(negative_button))
                .setCancelClickListener(cancellistener);

        return alert;
    }

    public static SweetAlertDialog showDialogwithNeutralButton(
            Context context, int title, int errormessage, int button,
            SweetAlertDialog.OnSweetClickListener dialogclicklistener, int dialog_id) {
        SweetAlertDialog alert = null;
//        dialoglistener = listener;

        alert = new SweetAlertDialog(context);


        alert.setTitle(context.getString(title));
        alert.setContentText(context.getString(errormessage));
        alert.setConfirmText(context.getString(button));

        alert.setConfirmClickListener(dialogclicklistener);

        return alert;

    }

//    public static AlertDialog.Builder showInputDialogwithNeutralButton(
//            Context context, int title, int hint, int button,
//            ConnectionDialogClickListener dialogclicklistener, int dialog_id,
//            EditText input) {
//        AlertDialog.Builder alert;
//        SweetAlertUtil.dialoglistener = dialogclicklistener;
//        if (android.os.Build.VERSION.SDK_INT >= 11) {
//            alert = new AlertDialog.Builder(new ContextThemeWrapper(context,
//                    android.R.style.Theme_Holo_Light_Dialog));
//        } else {
//            alert = new AlertDialog.Builder(new ContextThemeWrapper(context,
//                    android.R.style.Theme_Dialog));
//
//        }
//
//        alert.setTitle(context.getString(title));
//        alert.setNeutralButton(context.getString(button), new SweetAlertUtil(
//                dialog_id));
////		input.setTextColor(Color.WHITE);
//        alert.setCancelable(false);
//        // input.setHintTextColor(context.getResources().getColor(R.color.white_opacity35));
//        input.setHint(hint);
//        input.setInputType(InputType.TYPE_CLASS_NUMBER
//                | InputType.TYPE_NUMBER_FLAG_SIGNED);
//        alert.setView(input);
//        return alert;
//
//    }

//    @Override
//    public void onClick(DialogInterface dialog, int which) {
//        switch (which) {
//            case DialogInterface.BUTTON_POSITIVE:
//                SweetAlertUtil.dialoglistener.dialogClicklistener(dialog_id,
//                        DialogInterface.BUTTON_POSITIVE);
//                break;
//            case DialogInterface.BUTTON_NEGATIVE:
//                SweetAlertUtil.dialoglistener.dialogClicklistener(dialog_id,
//                        DialogInterface.BUTTON_NEGATIVE);
//                break;
//            case DialogInterface.BUTTON_NEUTRAL:
//                SweetAlertUtil.dialoglistener.dialogClicklistener(dialog_id,
//                        DialogInterface.BUTTON_NEUTRAL);
//                break;
//
//            default:
//                break;
//        }
//
//    }

//    /**
//     * @params this interface is for tracking of dialog button int button will
//     * be result either DialogInterface.BUTTON_POSITIVE or
//     * DialogInterface.BUTTON_NEGATIVE
//     */
//
//    public interface ConnectionDialogClickListener extends SweetAlertDialog.OnSweetClickListener {
//
//        public void dialogClicklistener(int dialog_id, int button);
//    }


    public static void showAlertDialogWithBlackTheme(Context context,
                                                     String alertMessage) {
        // final Context context =
        // MobileSaverBaseActivity.currentVisibleActivity;
        SweetAlertDialog alertDialog;

        alertDialog = new SweetAlertDialog(context);


        alertDialog.setTitle(R.string.exception);
        alertDialog.setContentText(alertMessage);
        alertDialog.setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.cancel();
                    }


                });

        alertDialog.show();
    }


    public static void hideDialogAutomatically(BaseActivity baseActivity, final SweetAlertDialog sweetAlertDialog) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sweetAlertDialog.dismissWithAnimation();
            }
        }, HIDE_DELAY);
    }

    /**
     * function to show a message with Proceed and Cancel button ,with confirm callbacks
     *
     * @param context
     * @param msg
     * @param listener
     */
    public static void showWarningWithCallback(Context context, String msg, String positive, SweetAlertDialog.OnSweetClickListener listener) {

        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(context.getString(R.string.sweet_alert_warning_title))
                .setContentText(msg)

                .setConfirmText(positive)


                .setConfirmClickListener(listener)
                .show();
    }

    public static SweetAlertDialog getProgressDialog(Context context) {
        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor(context.getString(R.color.sweet_dialog_progress_color)));
        pDialog.setTitleText(context.getString(R.string.service_progressdialog_title));
        pDialog.setCancelable(false);
        return pDialog;
    }

    /**
     * function to show error message with ok button , without button callback
     *
     * @param context
     * @param msg
     */
    public static void showErrorMessage(Context context, String msg) {
        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(context.getString(R.string.sweet_alert_error_title))
                .setContentText(msg)
                .show();
    }

    /**
     * function to show a basic message with ok button , without button callback
     *
     * @param context
     * @param msg
     */
    public static void showSweetMessageToast(Context context, String msg) {
        final SweetAlertDialog sweetalert = new SweetAlertDialog(context, TOAST_TYPE)
                .setContentText(msg);
        sweetalert.show();
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sweetalert.dismiss();
            }
        }, DIALOG_VISIBLE_TIME);

    }

    /**
     * function to show a basic message with ok button , without button callback
     *
     * @param context
     * @param msg
     */
    public static void showSweetMessageLongToast(Context context, String msg) {
        final SweetAlertDialog sweetalert = new SweetAlertDialog(context, TOAST_TYPE)
                .setContentText(msg);
        sweetalert.show();
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sweetalert.dismiss();
            }
        }, DIALOG_VISIBLE_TIME_LONG);

    }

    /**
     * function to show a basic message with ok button , without button callback
     *
     * @param context
     * @param msg
     */
    public static void showSweetErrorToast(Context context, String msg) {
        final SweetAlertDialog sweetalert = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setContentText(msg);
        sweetalert.show();
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sweetalert.dismiss();
            }
        }, ERROR_VISIBLE_TIME);

    }

    /**
     * function to show a message with Proceed and Cancel button ,with confirm callbacks
     *
     * @param context
     * @param msg
     */
    public static void showMessageWithCallback(Context context, String title, String msg, String positive, String negative, SweetAlertDialog.OnSweetClickListener positivelistener, SweetAlertDialog.OnSweetClickListener negativelistener) {
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(msg)
                .setCancelText(negative)
                .setConfirmText(positive)
                .showCancelButton(true)
                .setCancelClickListener(negativelistener)
                .setConfirmClickListener(positivelistener)
                .show();
    }

    /**
     * function to show error message with ok button , without button callback
     *
     * @param context
     * @param msg
     */
    public static void showLongErrorMessage(Context context, String msg) {
//        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
//                .setTitleText(context.getString(R.string.sweet_alert_error_title))
//                .setContentText(msg)
//                .show();


        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
//                TextView text = (TextView) alertDialog.findViewById(R.id.title_text);
//                text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                text.setSingleLine(false);
                TextView text2 = (TextView) alertDialog.findViewById(R.id.content_text);
                text2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                text2.setSingleLine(false);
            }
        });

        dialog.setTitleText(msg);
        dialog.setContentText(msg);
        dialog.show();
    }

    /**
     * function to show a basic message with ok button , without button callback
     *
     * @param context
     * @param msg
     */
    public static void showSweetSuccessMessageToast(Context context, String msg) {
        final SweetAlertDialog sweetalert = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE).setContentText(msg);
        sweetalert.show();

//        new android.os.Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                sweetalert.dismiss();
//            }
//        }, DIALOG_VISIBLE_TIME);

    }

}
