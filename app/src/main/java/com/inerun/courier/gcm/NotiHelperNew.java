package com.inerun.courier.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.inerun.courier.R;
import com.inerun.courier.activity_driver.LocationSenderActivity;
import com.inerun.courier.activity_driver.NotiHandlerActivity;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.constant.Utils;
import com.inerun.courier.data.LoginData;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * Created by vinay on 12/11/16.
 */

public class NotiHelperNew {

    public static final int NOTI_REQUEST_GENERATED = 101;
    public static final int NOTI_INVOICE_GENERATED = 102;
    public static final int NOTI_LOCATION_REQUESTED = 103;

    private Context context;
    private Map<String, String> params;
    private static NotiHelperNew controller;


    public NotiHelperNew(Context context, Map<String, String> params) {
        this.context = context;
        this.params = params;

    }

    public static NotiHelperNew getInstance(Context context, Map<String, String> params) {
        controller = new NotiHelperNew(context, params);
        return controller;
    }


    public void processNotification() {
        switch (getType(params)) {
            case NOTI_REQUEST_GENERATED:
                processRequestNotification();
                break;
            case NOTI_INVOICE_GENERATED:
                processInvoiceNotification();
                break;
            case NOTI_LOCATION_REQUESTED:
//                processLocationRequestNotification(context, data);
                break;
        }
    }

    public void processRequestNotification() {
//        Intent resultIntent = new Intent(context, SplashActivity.class);
        String data = "" + getType(params);
        String title = getTitle(params);
        String big_picture = null;
        if (params.containsKey(UrlConstants.KEY_IMAGE)) {
            big_picture = params.get(UrlConstants.KEY_IMAGE);
        }
        String text = getMessage(params);

        Intent resultIntent = new Intent(context, NotiHandlerActivity.class);
        try {

            resultIntent.putExtra(UrlConstants.KEY_DATA, data);
            resultIntent.putExtra(UrlConstants.KEY_IS_NOTIFICATION, true);

//            showNotification(context, resultIntent, title, text, big_picture);

            if (Utils.isUserLoggedIn(context) && ("" + Utils.getUserType(context)).equalsIgnoreCase(LoginData.USER_TYPE_WAREHOUSE)) {
                showNotification(context, resultIntent, title, text, big_picture);
            }
            if (Utils.isUserLoggedIn(context) && ("" + Utils.getUserType(context)).equalsIgnoreCase(LoginData.USER_TYPE_CUSTOMER_CARE)) {
                showNotification(context, resultIntent, title, text, big_picture);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void processInvoiceNotification() {
//        Intent resultIntent = new Intent(context, SplashActivity.class);
        String data = "" + getType(params);

        String title = getTitle(params);
        String big_picture = null;
        if (params.containsKey(UrlConstants.KEY_IMAGE)) {
            big_picture = params.get(UrlConstants.KEY_IMAGE);
        }
        String text = getMessage(params);

        Intent resultIntent = new Intent(context, NotiHandlerActivity.class);
        try {


            resultIntent.putExtra(UrlConstants.KEY_DATA, data);
            resultIntent.putExtra(UrlConstants.KEY_IS_NOTIFICATION, true);

//            showNotification(context, resultIntent, title, text, big_picture);

            if (Utils.isUserLoggedIn(context) && ("" + Utils.getUserType(context)).equalsIgnoreCase(LoginData.USER_TYPE_WAREHOUSE)) {
                showNotification(context, resultIntent, title, text, big_picture);
            }
            if (Utils.isUserLoggedIn(context) && ("" + Utils.getUserType(context)).equalsIgnoreCase(LoginData.USER_TYPE_CUSTOMER_CARE)) {
                showNotification(context, resultIntent, title, text, big_picture);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void processLocationRequestNotification(Context context, String data) {
//
        Log.i("NotiProcessLocReq", "success" + data);
        try {
            JSONObject jsondata = new JSONObject(data);
            String typestring = jsondata.getString(UrlConstants.KEY_TYPE);
            int type = Integer.parseInt(typestring);
            if (type == NOTI_LOCATION_REQUESTED) {
                Intent intent = new Intent(context, LocationSenderActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void processNotification(Context context, String title, String text, String data, String big_picture) {
//        Intent resultIntent = new Intent(context, SplashActivity.class);
        Intent resultIntent = new Intent(context, NotiHandlerActivity.class);
        try {


            resultIntent.putExtra(UrlConstants.KEY_DATA, data);
            resultIntent.putExtra(UrlConstants.KEY_IS_NOTIFICATION, true);

//            showNotification(context, resultIntent, title, text, big_picture);

            if (Utils.isUserLoggedIn(context) && ("" + Utils.getUserType(context)).equalsIgnoreCase(LoginData.USER_TYPE_WAREHOUSE)) {
                showNotification(context, resultIntent, title, text, big_picture);
            }
            if (Utils.isUserLoggedIn(context) && ("" + Utils.getUserType(context)).equalsIgnoreCase(LoginData.USER_TYPE_WAREHOUSE)) {
                showNotification(context, resultIntent, title, text, big_picture);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showNotification(Context context, Intent intent, String title, String text, String bigpicture) {
//        ImageLoader imgloader = ImageLoader.getInstance();
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context);
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

        mBuilder.setSmallIcon(R.mipmap.notification_icon_trans);
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                R.mipmap.notification_icon_trans));
//        } else {
//            mBuilder.setSmallIcon(R.mipmap.notification_icon);
//
//        }

//        if (bigpicture != null && bigpicture .length()>0) {
//            mBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(imgloader.loadImageSync(bigpicture)).setSummaryText(text));
//        }else
//        {
        mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(text));
//        }
//                        .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(alarmSound);
        mBuilder.setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(text);


// Creates an explicit intent for an Activity in your app


// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(NotiHandlerActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(createID(), mBuilder.build());
    }

    public static boolean isNotificationIntent(Intent intent) {

        return intent.getExtras() != null || intent.hasExtra(UrlConstants.KEY_IS_NOTIFICATION);

    }


    public static int createID() {
        Date now = new Date();
        int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss", Locale.US).format(now));
        return id;
    }

    /**
     * function to get Type from Bundle
     *
     * @param params
     */
    private int getType(Map<String, String> params) {
        if (params.containsKey(UrlConstants.KEY_DATA)) {
            String type = params.get(UrlConstants.KEY_DATA);
            Log.i(" Type", type);
            return Integer.parseInt(type);
        } else {
            Log.i(" Type", "type not found in notification");
            return -1;
        }

    }

    /**
     * function to get Message from Bundle
     */
    private String getMessage(Map<String, String> params) {
        if (params.containsKey(UrlConstants.KEY_Text)) {
            String msg = params.get(UrlConstants.KEY_Text);
            Log.i(" Message", msg);
            return msg;
        } else {
            Log.i(" Message", "Message not found in notification");
            return null;
        }

    }

    /**
     * function to get Title from Bundle
     */
    private String getTitle(Map<String, String> params) {
        if (params.containsKey(UrlConstants.KEY_Title)) {
            String title = params.get(UrlConstants.KEY_Title);
            Log.i(" Title", title);
            return title;
        } else {
            Log.i(" Title", "Title not found in notification");
            return null;
        }

    }
}


