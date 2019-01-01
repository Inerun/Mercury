package com.inerun.courier.activity_driver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.inerun.courier.CourierApplication;
import com.inerun.courier.R;
import com.inerun.courier.activity_customer_care.CustomerDashboardActivity;
import com.inerun.courier.activity_warehouse.WhDashboardActivity;
import com.inerun.courier.base.BaseActivity;
import com.inerun.courier.base.CheckConnectionUtil;
import com.inerun.courier.base.SweetAlertUtil;
import com.inerun.courier.constant.AppConstant;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.constant.Utils;
import com.inerun.courier.data.LoginData;
import com.inerun.courier.sql.DIDbHelper;

import java.util.Timer;
import java.util.TimerTask;


public class SplashActivity extends BaseActivity {
    Context context;

    private static final int FORCE_UPDATE_DAILOG = 101;
    private Timer timer;


    @Override
    public int customSetContentView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        return R.layout.activity_splash;
    }

//

    @Override
    public void customOnCreate(Bundle savedInstanceState) {
        context = SplashActivity.this;

        handleExitintent();

        setSplash(true);

        setDataToApplication();
        Log.i("SplashActivity", "" + getIntent().getExtras());
//        if (!NotiHelper.isNotificationIntent(getIntent())) {
            timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

//                            gotoFirstActivity();

                            if(Utils.getPageType(SplashActivity.this) == UrlConstants.LOGIN){

                                if (CheckConnectionUtil.checkMyConnectivity(SplashActivity.this)) {

                                    if (hasPermissions(context, AppConstant.requiredPermissions())) {
                                        DIDbHelper.ensureDatabaseIsCorrect(context);
                                    }
//
                                    gotoPodDeleteActivity();

//                            if (Utils.isUserLoggedIn(SplashActivity.this)) {
//                                if (DropInsta.getUser().isDeliveryUser()) {
//                                    gotoHomeActivity();
//                                } else {
//                                    gotoWarehouseActivity();
//                                }
//                            } else {
//                                gotoLoginActivity();
//
//                            }

                                } else {
                                    if (Utils.isUserLoggedIn(SplashActivity.this)) {
                                        if (CourierApplication.getUser().isDeliveryUser()) {
                                            gotoHomeActivity();
                                        } else {
                                            SweetAlertUtil.showAlertDialogFinishActivity(SplashActivity.this, getString(R.string.activity_base_alert_message_unknown_host_exception));

                                        }
                                    } else {
                                        gotoLoginActivity();

                                    }
//                                AlertUtil.showAlertDialogFinishActivity(SplashActivity.this, getString(R.string.activity_base_alert_message_unknown_host_exception));
                                }


                            }else if(Utils.getPageType(SplashActivity.this) == UrlConstants.RREQUEST){
                                gotoParcelRequestActivity();
                            }else{

                            }


//                            if (CheckConnectionUtil.checkMyConnectivity(SplashActivity.this)) {
//
//                                if (hasPermissions(context, AppConstant.requiredPermissions())) {
//                                    DIDbHelper.ensureDatabaseIsCorrect(context);
//                                }
////
//                                gotoPodDeleteActivity();
//
////                            if (Utils.isUserLoggedIn(SplashActivity.this)) {
////                                if (DropInsta.getUser().isDeliveryUser()) {
////                                    gotoHomeActivity();
////                                } else {
////                                    gotoWarehouseActivity();
////                                }
////                            } else {
////                                gotoLoginActivity();
////
////                            }
//
//                            } else {
//                                if (Utils.isUserLoggedIn(SplashActivity.this)) {
//                                    if (DropInsta.getUser().isDeliveryUser()) {
//                                        gotoHomeActivity();
//                                    } else {
//                                        SweetAlertUtil.showAlertDialogFinishActivity(SplashActivity.this, getString(R.string.activity_base_alert_message_unknown_host_exception));
//
//                                    }
//                                } else {
//                                    gotoLoginActivity();
//
//                                }
////                                AlertUtil.showAlertDialogFinishActivity(SplashActivity.this, getString(R.string.activity_base_alert_message_unknown_host_exception));
//                            }

                        }

                    });


                }
            }, 2500);
//        } else {
//            timer = new Timer();
//            timer.schedule(new TimerTask() {
//
//                @Override
//                public void run() {
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Log.i("Timer_else","Splash");
//                            startActivity(getNotificationIntent(getIntent()));
//                            finish();
//
//                        }
//                    });
//
//
//                }
//            }, 2500);
////            startActivity(getNotificationIntent(getIntent()));
////            finish();
//        }


    }

    private void gotoPodDeleteActivity() {
        goToActivity(PodDeleteActivity.class);

    }

    private void setDataToApplication() {
        if (Utils.isUserLoggedIn(context)) {
            CourierApplication.setUser(Utils.getLoginData(context));
        }

    }

    private Intent getNotificationIntent(Intent intent) {
        Intent newIntent = new Intent();
        if (("" + Utils.getUserType(context)).equalsIgnoreCase(LoginData.USER_TYPE_DELIVERY)) {
            Log.i("getNotificationIntent", "USER_TYPE_DELIVERY");
            newIntent = new Intent(SplashActivity.this, DeliveryDashBoardActivity.class);
        } else if (("" + Utils.getUserType(context)).equalsIgnoreCase(LoginData.USER_TYPE_CUSTOMER_CARE)) {
            Log.i("getNotificationIntent", "USER_TYPE_CUSTOMER_CARE");
            newIntent = new Intent(SplashActivity.this, CustomerDashboardActivity.class);
        } else {
            Log.i("getNotificationIntent", "USER_TYPE_WAREHOUSE");
            newIntent = new Intent(SplashActivity.this, WhDashboardActivity.class);
        }

        newIntent.putExtra(UrlConstants.KEY_IS_NOTIFICATION, true);
        newIntent.putExtra(UrlConstants.KEY_DATA, intent.getSerializableExtra(UrlConstants.KEY_DATA));


        return newIntent;
    }


    private boolean validateVersion() {
//        String savedversion = Utils.getVersionCode(SplashActivity.this);
//        String appversion = "" + DeviceInfoUtil.getSelfVersionCode(SplashActivity.this);
//        if (savedversion.equalsIgnoreCase(appversion)) {
//            return true;
//        } else {
//
//            AlertUtil.showDialogwithNeutralButton(SplashActivity.this, R.string.force_update_dialog_title, R.string.force_update_dialog_msg, R.string.ok, dialog_listener, FORCE_UPDATE_DAILOG).show();
//            return false;
//        }
        return true;
    }

    private void handleExitintent() {
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }

    }

    private void doLogin() {
//        if (Utils.getSocialLogin(SplashActivity.this)) {
//            gotoHomeActivity();
//        Intent intent = new Intent(SplashActivity.this, StoreActivity.class);
//        Intent intent = new Intent(SplashActivity.this, DBHomeActivity.class);
//        startActivity(intent);
        goToActivity(DeliveryDashBoardActivity.class);
//        Intent intent = new Intent(SplashActivity.this,
//                DeliveryDashBoardActivity.class);
//        startActivity(intent);
//        } else {
//
//            login_client = BTService.loginService(SplashActivity.this, null, login_callback, Utils.getUserEmail(SplashActivity.this), Utils.getPassword(SplashActivity.this));
//        }

    }


//    private void dosomething(ArrayList<CategoryData> categoryDatas){
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(UrlConstants.KEY_CATEGORY, categoryDatas);
//        goToActivity(StoreActivity.class, bundle);
////        goToActivity(DeliveryDashBoardActivity.class, bundle);
//
//        /*Intent intent = new Intent(SplashActivity.this, DBHomeActivity.class);
//        intent.putExtra(UrlConstants.KEY_CATEGORY, categoryDatas);
//        startActivity(intent);*/
//
//
//    }


    private void gotoFirstActivity() {
        Intent intent = new Intent(SplashActivity.this,
                FirstActivity.class);
        startActivity(intent);


    }

    private void gotoParcelRequestActivity() {
        Intent intent = new Intent(SplashActivity.this,
                ParcelRequestActivity.class);
        startActivity(intent);


    }

    private void gotoLoginActivity() {
        Intent intent = new Intent(SplashActivity.this,
                LoginActivity.class);
        startActivity(intent);


    }

    private void gotoHomeActivity() {

        Intent intent = new Intent(SplashActivity.this,
                DeliveryDashBoardActivity.class);
        startActivity(intent);

    }

    private void gotoWarehouseActivity() {

        Intent intent = new Intent(SplashActivity.this,
                WhDashboardActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if (timer != null) {
            timer.cancel();
            finish();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (lang_client != null) {
//            lang_client.cancelService();
//        }
//        if (login_client != null) {
//            login_client.cancelService();
//        }
    }


}
