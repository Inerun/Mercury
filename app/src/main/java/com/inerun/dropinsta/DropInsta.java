package com.inerun.dropinsta;

import android.app.Application;
import android.util.Log;

import com.inerun.dropinsta.constant.UrlConstants;
import com.inerun.dropinsta.constant.Utils;
import com.inerun.dropinsta.data.LoginData;
import com.inerun.dropinsta.fontlib.TypefaceUtil;
import com.inerun.dropinsta.network.ServiceManager;
import com.inerun.dropinsta.service.DIRequestCreator;

import java.util.Map;

/**
 * Created by vinay on 23/11/16.
 */

public class DropInsta extends Application {


    private static final String EXCEPTION_SERVICE = "ExceptionService";
    public static LoginData user;
    static ServiceManager serviceManager;
    private DropInsta appcontext;


    @Override
    public void onCreate() {
        super.onCreate();
        // Create default options which will be used for every
        //  displayImage(...) call if no options will be passed to this method
//        Log.i("TMApplication", "onCreate");
        appcontext = DropInsta.this;
//        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this));
        serviceManager = ServiceManager.init(DropInsta.this);

        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/robotolight.ttf");
        updateGcmIdToServer();
    }

    private void updateGcmIdToServer() {
        String gcmid = Utils.getGcmId(appcontext);
        Log.d("updateGcmIdToServer", "Gcm id: "+gcmid);
        if (Utils.isUserLoggedIn(appcontext) && gcmid != null && gcmid.length() > 0) {

            Log.i("updateGcmIdToServer", "Updating Gcm id To Server");
            Map<String, String> params = DIRequestCreator.getInstance(DropInsta.this).getGcmRegistrationParams(appcontext, gcmid);

            serviceManager().postRequest(UrlConstants.URL_GCM, params, null, null, null, EXCEPTION_SERVICE);
        } else {
            Log.e("updateGcmIdToServer", "Gcm id Cannot be updated,Either user is not logged in or gcm id is not present");
        }
    }


    public static ServiceManager serviceManager() {
        return serviceManager;
    }


    public static LoginData getUser() {
        return user;
    }

    public static void setUser(LoginData user) {
        DropInsta.user = user;
    }


}
