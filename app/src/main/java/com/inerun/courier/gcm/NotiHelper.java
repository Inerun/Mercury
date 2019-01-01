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
import com.inerun.courier.activity_driver.NotiHandlerActivity;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.constant.Utils;
import com.inerun.courier.data.LoginData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by vinay on 12/11/16.
 */

public class NotiHelper {

    public static final int NOTI_REQUEST_GENERATED = 101;
    public static final int NOTI_INVOICE_GENERATED = 102;
    public static final int NOTI_LOCATION_REQUEST = 103;


    public static void processNotification(Context context, String title, String text, String data, String big_picture) {
//        Intent resultIntent = new Intent(context, SplashActivity.class);
        Intent resultIntent = new Intent(context, NotiHandlerActivity.class);
        try {


            resultIntent.putExtra(UrlConstants.KEY_DATA, data);
            resultIntent.putExtra(UrlConstants.KEY_IS_NOTIFICATION, true);

//            showNotification(context, resultIntent, title, text, big_picture);
            Log.i("UserType1", ("" + Utils.getUserType(context)).equalsIgnoreCase(LoginData.USER_TYPE_WAREHOUSE) + "");
            Log.i("UserType2", ("" + Utils.getUserType(context)).equalsIgnoreCase(LoginData.USER_TYPE_CUSTOMER_CARE) + "");
//            Utils.getUserType(context) == Integer.parseInt(LoginData.USER_TYPE_WAREHOUSE)


            if (Utils.isUserLoggedIn(context) && ("" + Utils.getUserType(context)).equalsIgnoreCase(LoginData.USER_TYPE_DELIVERY)) {
                Log.i("USER_TYPE_DELIVERY", Utils.getUserType(context) + "");
               NotiHelperNew.processLocationRequestNotification(context,data);
               return;
            }if (Utils.isUserLoggedIn(context) && ("" + Utils.getUserType(context)).equalsIgnoreCase(LoginData.USER_TYPE_WAREHOUSE)) {
                Log.i("USER_TYPE_WAREHOUSE", Utils.getUserType(context) + "");
                showNotification(context, resultIntent, title, text, big_picture);
            }
            if (Utils.isUserLoggedIn(context) && ("" + Utils.getUserType(context)).equalsIgnoreCase(LoginData.USER_TYPE_CUSTOMER_CARE)) {
                Log.i("USER_TYPE_CUSTOMER_CARE", Utils.getUserType(context) + "");
                showNotification(context, resultIntent, title, text, big_picture);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void processLocationRequestNotification(Context context) {
//
        Log.i("NotiProcessLocReq", "success");
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
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
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
}
