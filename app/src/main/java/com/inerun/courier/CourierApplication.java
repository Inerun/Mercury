package com.inerun.courier;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.location.LocationRequest;
import com.inerun.courier.activity_auction.IonServiceManager;
import com.inerun.courier.activity_driver.FragmentBaseActivity;
import com.inerun.courier.base.AuctionBaseActivity;
import com.inerun.courier.base.BaseActivity;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.constant.Utils;
import com.inerun.courier.data.LoginData;
import com.inerun.courier.fontlib.TypefaceUtil;
import com.inerun.courier.network.ServiceManager;
import com.inerun.courier.service.DIRequestCreator;
import com.inerun.courier.sqldb.AppDatabase;
import com.koushikdutta.ion.Ion;
import com.raizlabs.android.dbflow.config.FlowLog;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.vishalsojitra.easylocation.EasyLocationRequest;
import com.vishalsojitra.easylocation.EasyLocationRequestBuilder;

import java.util.Map;

/**
 * Created by vinay on 23/11/16.
 */

public class CourierApplication extends Application {


    private static final String EXCEPTION_SERVICE = "ExceptionService";
    public static LoginData user;
    static ServiceManager serviceManager;
   public static IonServiceManager ionserviceManager;
   public    EasyLocationRequest easyLocationRequest;
    private CourierApplication appcontext;
    public AuctionBaseActivity currentActivity;
    public BaseActivity currentBaseActivity;
    public FragmentBaseActivity currentFragmentBaseActivity;


    @Override
    public void onCreate() {
        super.onCreate();
        // Create default options which will be used for every
        //  displayImage(...) call if no options will be passed to this method
//        Log.i("TMApplication", "onCreate");
        appcontext = CourierApplication.this;
//        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this));
        serviceManager = ServiceManager.init(CourierApplication.this);

        FlowManager.init(this);
        try {

//            FlowManager.init(new FlowConfig.Builder(this)
//                    .addDatabaseConfig(new
//                            DatabaseConfig.Builder(AppDatabase.class)
//                            .openHelper(new DatabaseConfig.OpenHelperCreator() {
//                                @Override
//                                public OpenHelper createHelper(DatabaseDefinition databaseDefinition, DatabaseHelperListener helperListener) {
//                                    return new SQLCipherHelperImpl(databaseDefinition, helperListener,DropInsta.this);
//                                }
//                            })
//                            .build()).openDatabasesOnInit(true)
//                    .build());

//
            // add for verbose logging
            FlowLog.setMinimumLoggingLevel(FlowLog.Level.D);
            Log.i("PATH", "" + FlowManager.getContext().getDatabasePath(AppDatabase.NAME));

            initServiceManager();
        }catch (Exception e)
        {
            //handle exception with Exception handler
            e.printStackTrace();

        }


        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/robotolight.ttf");
        updateGcmIdToServer();
    }

    private void updateGcmIdToServer() {
        String gcmid = Utils.getGcmId(appcontext);
        Log.d("updateGcmIdToServer", "Gcm id: "+gcmid);
        if (Utils.isUserLoggedIn(appcontext) && gcmid != null && gcmid.length() > 0) {

            Log.i("updateGcmIdToServer", "Updating Gcm id To Server");
            Map<String, String> params = DIRequestCreator.getInstance(CourierApplication.this).getGcmRegistrationParams(appcontext, gcmid);

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
        CourierApplication.user = user;
    }

    private void initServiceManager() {
        IonServiceManager.Builder builder = new IonServiceManager.Builder(this);
        ionserviceManager = builder.build();
        String ip = UrlConstants.BASE_ADDRESS;
        if (ip != null && ip.length() > 0) {
            setBaseUrl(ip);
        }

        Ion.getDefault(this).configure().setLogging("ION", Log.DEBUG);
        LocationRequest locationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                .setInterval(5000)
                .setFastestInterval(5000);
         easyLocationRequest = new EasyLocationRequestBuilder()
                .setLocationRequest(locationRequest)
                .setFallBackToLastLocationTime(3000)
                .build();
    }

    public void setCurrentActivity(AuctionBaseActivity currentActivity) {
        this.currentActivity = currentActivity;
        if(ionserviceManager!=null) {
            ionserviceManager.setActivity(currentActivity);
        }
    }

    public void setCurrentBaseActivity(BaseActivity currentBaseActivity) {
        this.currentBaseActivity = currentBaseActivity;
    }

    public void setCurrentFragmentBaseActivity(FragmentBaseActivity currentFragmentBaseActivity) {
        this.currentFragmentBaseActivity = currentFragmentBaseActivity;
    }

    public void setBaseUrl(String url) {
        ionserviceManager.changeBaseUrlToThis(url);
    }



//    public void setCurrentFragment(Fragment currentFragment) {
//        this.ionserviceManager = currentFragment;
//    }


}
