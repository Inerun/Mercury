package com.inerun.courier.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.inerun.courier.R;

import java.util.Random;

/**
 * Created by vinay on 16/11/18.
 */

public class LocationUpdateService extends IntentService {


    /**
     * A constructor is required, and must call the super IntentService(String)
     * constructor with a name for the worker thread.
     */
    Random r;
    int NotID = 1;
    NotificationManager nm;

    public LocationUpdateService() {
        super("number5");  //or whatever name you want to give it.
        r = new Random();
        //showToast("b Intent Service started");
    }

    public LocationUpdateService(String name) {
        super(name);  //or whatever name you want to give it.
        r = new Random();
        //showToast("Intent Service started");
    }

    /*
     *
     * (non-Javadoc)
     * @see android.app.IntentService#onHandleIntent(android.content.Intent)
     */
    @Override
    protected void onHandleIntent(Intent intent) {

    }




    public void makenoti(String message) {

        Notification noti = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.ic_launcher)
                .setWhen(System.currentTimeMillis())  //When the event occurred, now, since noti are stored by time.

                .setContentTitle("Service")   //Title message top row.
                .setContentText(message)  //message when looking at the notification, second row
                .setAutoCancel(true)   //allow auto cancel when pressed.
                .build();  //finally build and return a Notification.

        //Show the notification
        nm.notify(NotID, noti);
        NotID++;
    }


}
