package com.inerun.courier.service;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.gcm.NotiHelper;

import java.util.Map;

/**
 * Created by vinay on 20/02/17.
 */

public class DropInstaFirebaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        Log.d("Notification", "data: " + remoteMessage.getData());

        Map<String, String> params = remoteMessage.getData();
//        params.put(UrlConstants.KEY_DATA,"{'type':101}");
//        params.put(UrlConstants.KEY_Title,"Notification Title");
//        params.put(UrlConstants.KEY_IMAGE,"");
//        params.put(UrlConstants.KEY_Text,"This is the Notification text which must be shown on Notification");
        Log.d("params", " " + remoteMessage.getData());
        if(params!=null) {
            try {
                String string = params.get(UrlConstants.KEY_DATA);

                String title = params.get(UrlConstants.KEY_Title);
                String big_picture = null;
                if (params.containsKey(UrlConstants.KEY_IMAGE)) {
                    big_picture = params.get(UrlConstants.KEY_IMAGE);
                }
                String text = params.get(UrlConstants.KEY_Text);


                NotiHelper.processNotification(getApplicationContext(), title, text, string, big_picture);
//                NotiHelperNew.getInstance(getApplicationContext(), params).processNotification();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
