package com.inerun.courier.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.inerun.courier.CourierApplication;
import com.inerun.courier.Exception.ExceptionMessages;
import com.inerun.courier.Exception.MyExceptionHandler;
import com.inerun.courier.R;
import com.inerun.courier.activity_customer_care.CustReadyInvoiceDeliveryFragment;
import com.inerun.courier.activity_driver.DeliveryReportActivity;
import com.inerun.courier.activity_driver.LoginActivity;
import com.inerun.courier.activity_customer_care.CustReadyInvoiceDeliveryFragment;
import com.inerun.courier.activity_warehouse.WhReadyForExecutiveFragment;
import com.inerun.courier.constant.AppConstant;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.constant.Utils;
import com.inerun.courier.data.ParcelListingData;
import com.inerun.courier.data.TransactionData;
import com.inerun.courier.data.UpdatedParcelData;
import com.inerun.courier.data.UpdatedPickupParcelData;
import com.inerun.courier.gcm.NotiHelper;
import com.inerun.courier.helper.DIHelper;
import com.inerun.courier.service.DIReceiver;
import com.inerun.courier.service.DIRequestCreator;
import com.inerun.courier.service.UploadingService;
import com.inerun.courier.sql.DIDbHelper;
import com.loopj.android.http.RequestParams;
import com.victor.loading.rotate.RotateLoading;
import com.vishalsojitra.easylocation.EasyLocationAppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by vinay on 9/29/2015.
 */
abstract public class BaseActivity extends EasyLocationAppCompatActivity {
    private static final String sTAG = "requestLocation";
    public CoordinatorLayout cordinatorLayout;
    private static final int DIALOG_LOGOUT = 12;
    private static final int DIALOG_EXIT = 11;
    public static final int REQUEST_CODE_LOGIN = 13;
    public static final int REQUEST_CODE_SIGNUP = 14;
    private static final int DIALOG_STORE = 15;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 121;
    private static final int MY_PERMISSIONS_REQUEST_READ_STORAGE = 122;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 123;


    /* AlertUtil.ConnectionDialogClickListener listener = new AlertUtil.ConnectionDialogClickListener() {
             @Override
             public void dialogClicklistener(int dialog_id, int button) {
                 switch (dialog_id) {
                     case DIALOG_LOGOUT:
                         if (button == DialogInterface.BUTTON_POSITIVE) {

                            Utils.deletePrefs(BaseActivity.this);
                            showShortToast(BaseActivity.this, R.string.logout_message);
                            invalidateOptionsMenu();
    //                        finish();
                            onLogoutGotoHome();

                        }
                        break;

                    case DIALOG_EXIT:
                        if (button == DialogInterface.BUTTON_POSITIVE) {

                            sendExitIntent(BaseActivity.this, SplashActivity.class);

                        }
                        break;
                }


            }
        };*/
    private boolean actionbar_menu_visible = true;
    private boolean home_activity = false;
    private boolean show_home = true;
    //    private ServiceClient syncCartClient;
    private boolean innerLaunch;
    private boolean forCart;

    private boolean isWarehouse = false;
    private boolean isCustomerSupport = false;


    private boolean isSplash = false;


    private Toolbar toolbar;
    private boolean doubleBackToExitPressedOnce = false;
    private RotateLoading progress;
    private RelativeLayout progress_layout;


    public boolean isWarehouse() {
        return isWarehouse;
    }

    public void setWarehouse(boolean warehouse) {
        isWarehouse = warehouse;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

            Thread.setDefaultUncaughtExceptionHandler(new
                    MyExceptionHandler(this));
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
//                    WindowManager.LayoutParams.FLAG_SECURE);
            int layoutid = customSetContentView();
            if (layoutid != 0)
                setContentView(layoutid);
            else {
                Log.e("BaseActivity", "Layout not specified ,please specify layout");
                return;
            }
//        ActionBar actionbar = getSupportActionBar();
//        if (actionbar != null) {

//            actionbar.setIcon(R.mipmap.ic_launcher);
            progress = (RotateLoading) findViewById(R.id.progressBar);
            progress_layout = (RelativeLayout) findViewById(R.id.progressbar_layout);
            cordinatorLayout = (CoordinatorLayout) findViewById(R.id.root_appbar);
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            if (!isSplash) {
                if (toolbar != null) {
                    toolbar.setTitle(R.string.app_name);
//                toolbar.setLogo(R.mipmap.toolbaricon);

//            toolbar.setNavigationIcon(R.drawable.toolbar_arrow);
                    setSupportActionBar(toolbar);
                    ActionBar ab = getSupportActionBar();

                    // Enable the Up button

                    ab.setHomeButtonEnabled(true);
                    ab.setDisplayHomeAsUpEnabled(true);

//                getSupportActionBar().setDisplayShowHomeEnabled(true);
//                if (isWarehouse) {
//
//                } else {
//                    getSupportActionBar().setIcon(R.mipmap.toolbaricon);
//                }
//                getSupportActionBar().setDisplayShowTitleEnabled(isWarehouse);
//            Log.i("cart_value",isWarehouse + "");

//        }
                }
            }
            customOnCreate(savedInstanceState);

        } catch (Exception e) {
            e.printStackTrace();
            SweetAlertUtil.showDialogwithNeutralButton(BaseActivity.this, getString(R.string.exception), getString(ExceptionMessages.getExceptionMessage(e)) + "\n" + e.getMessage(), getString(R.string.ok), new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {

                }
            }).show();
        }


    }

    public abstract int customSetContentView();

    public abstract void customOnCreate(Bundle savedInstanceState);


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        initActionBar();
        if (isWarehouse()) {
            getMenuInflater().inflate(R.menu.menu_warehouse, menu);
            getSupportActionBar().setHomeButtonEnabled(false); // disable the button
            getSupportActionBar().setDisplayHomeAsUpEnabled(false); // remove the left caret
            getSupportActionBar().setDisplayShowHomeEnabled(false);
//            MenuItem loginitem = menu.findItem(R.id.action_login_logout);
//            TigmooHelper.setCartBadge(BaseActivity.this, menu);
            //  MenuItem homeitem = menu.findItem(R.id.action_home);
//            if (isHome_activity()) {
//                homeitem.setVisible(false);
//            } else {
//                homeitem.setVisible(true);
//            }

//            if (Utils.isUserLoggedIn(this)) {
//                loginitem.setTitle(getString(R.string.action_logout));
//
//            } else {
//                loginitem.setTitle(getString(R.string.action_login));
//
//            }

//        } else {
//            getMenuInflater().inflate(R.menu.btlogo_menu, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_home, menu);
        }
        super.onCreateOptionsMenu(menu);
        return true;
    }

    protected void initActionBar() {
        if (!isHome_activity() && getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowCustomEnabled(true);

        }

    }


    @Override
    protected void onResume() {
        super.onResume();
//        invalidateOptionsMenu();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();

                return true;
            case R.id.action_refresh:
//                syncData();
                if (Utils.isConnectingToInternet(this)) {
                    syncData();
                } else {
                    SweetAlertUtil.showAlertDialogWithBlackTheme(this, getString(R.string.activity_base_alert_message_unknown_host_exception));
                }

                return true;


//            case R.id.action_addtocart:
//
//                goToActivity(CartActivity.class);
//
//                return true;
////            case R.id.action_home:
////
////                if (isHome_activity()) {
//////                    homeitem.setVisible(false);
////                } else {
//////                    homeitem.setVisible(true);
////                    gotoHomeAcitivity(this);
////                }
////
////                break;
//            case R.id.action_account:
//
//                if (Utils.isUserLoggedIn(this)) {
////                    goToActivity(UserDashboardActivity.class);
//                    Intent intent = new Intent(getApplicationContext(), UserDashboardActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                    startActivity(intent);
//                } else {
//                    goToActivity(LoginActivity.class);
//                }
//
//               /* if (Utils.isUserLoggedIn(this)) {
//                    goToActivity(AccountPagerActivity.class);
//                } else {
//                    goToActivity(LoginActivity.class);
//
//                }*/
//                break;
////            case R.id.action_website:
////                goToActivity(LoginActivity.class);
////
//////                BtHelper.visitWebsite(this);
////
////                break;
////            case R.id.action_change_lang:
////
//////                BtHelper.showLanguageSelectionDialog(this);
////
////                break;
//            case R.id.action_search:
//                goToActivity(SearchActivity.class);
//
//                break;
            case R.id.action_logout:

                if (Utils.isUserLoggedIn(this)) {
                    SweetAlertUtil.showAlertDialogwithListener(this, R.string.logout, R.string.really_logout, R.string.yes, R.string.no, listener, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            sweetAlertDialog.dismiss();

                        }
                    }).show();
                } else {
                    goToActivity(LoginActivity.class);
                }
                break;

            case R.id.action_report:
                startActivity(new Intent(BaseActivity.this, DeliveryReportActivity.class));
                return true;

//            case R.id.action_contact_us:
//
////                goToActivity(ContactUsActivity.class);
//                Intent intent2 = new Intent(getApplicationContext(), ContactUsActivity.class);
//                intent2.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                startActivity(intent2);
//                break;
//
//            case R.id.action_wishlist:
//                goToActivity(MyWishlistActivity.class);
//                break;
//
//            case R.id.action_my_order:
//                if (Utils.isUserLoggedIn(this)) {
////                    goToActivity(MyOrderActivity.class);
//                    Intent intent = new Intent(getApplicationContext(), MyOrderActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                    startActivity(intent);
//                } else {
//                    goToActivity(LoginActivity.class);
//                }
//
//                break;
//            case R.id.action_my_reviews:
//                if (Utils.isUserLoggedIn(this)) {
//                    goToActivity(MyProductReviewsActivity.class);
//                } else {
//                    goToActivity(LoginActivity.class);
//                }
//
//                break;
//            case R.id.action_my_points_rewards:
//                if (Utils.isUserLoggedIn(this)) {
////                    goToActivity(MyPointsRewardsActivity.class);
//                    Intent intent = new Intent(getApplicationContext(), MyPointsRewardsActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                    startActivity(intent);
//                } else {
//                    goToActivity(LoginActivity.class);
//                }
//
//                break;
//            case R.id.action_newsletter:
//                if (Utils.isUserLoggedIn(this)) {
//                    goToActivity(NewsSubscriptionActivity.class);
//                } else {
//                    goToActivity(LoginActivity.class);
//                }
//
//                break;
//            case R.id.action_trackorder:
////                goToActivity(TrackOrderActivity.class);
//                Intent intent1 = new Intent(getApplicationContext(), TrackOrderActivity.class);
//                intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                startActivity(intent1);
//
//                break;
//            case R.id.action_home:
////                goToActivity(DeliveryDashBoardActivity.class);
//
//                Intent intent = new Intent(getApplicationContext(), DeliveryDashBoardActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//
//                break;
//            case R.id.action_account_info:
//                if (Utils.isUserLoggedIn(this)) {
//                    goToActivity(UserAccountInfoActivity.class);
//                } else {
//                    goToActivity(LoginActivity.class);
//                }
//
//                break;
//
//            case R.id.action_change_store:
//
//                if (Utils.isUserLoggedIn(BaseActivity.this)) {
//                    AlertUtil.showAlertDialogwithListener(BaseActivity.this, R.string.store_title, R.string.store_msg, R.string.yes, R.string.no, listener, DIALOG_STORE).show();
//                } else {
//                    Utils.deletePrefsStore(BaseActivity.this);
//                    Intent intent5 = new Intent(BaseActivity.this, StoreActivity.class);
//                    intent5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent5);
//
//                }
//
//                break;
////            case R.id.action_fb:
////
////
////
//////                 startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(UrlConstants.BREAKTHROUGH_FB_PAGE_URL)));
////
////                break;
////            case R.id.action_exit:
////
////
//////                AlertUtil.showAlertDialogwithListener(this, R.string.quit, R.string.really_quit, R.string.yes, R.string.no, listener, DIALOG_EXIT).show();
////
////                break;


        }
        return true;
    }

    public void onLogoutGotoHome() {
        gotoHomeAcitivity(BaseActivity.this);
    }

    private void sendExitIntent(Context context, Class clas) {
        Intent intent = new Intent(context, clas);
        intent.putExtra("EXIT", true);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    private void gotoSearchActivity() {
        // startActivity(new Intent(BaseActivity.this, SearchActivity.class));
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    public void showLongToast(Context context, int res_id) {
        Toast.makeText(context, res_id, Toast.LENGTH_LONG).show();
    }

    public void showShortToast(Context context, int res_id) {
        Toast.makeText(context, res_id, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_LONG).show();
    }

    public void showShortToast(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }

    public boolean isActionbar_menu_visible() {
        return actionbar_menu_visible;
    }

    public void setActionbar_menu_visible(boolean actionbar_menu_visible) {
        this.actionbar_menu_visible = actionbar_menu_visible;
    }

    public boolean isHome_activity() {
        return home_activity;
    }

    public void setHome_activity(boolean home_activity) {
        this.home_activity = home_activity;
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);

    }


    public void setActionBarTitle(int title) {
        getSupportActionBar().setTitle(getString(title));

    }
//    public void setActionBarIcon(String title) {
//        getSupportActionBar().setTitle(title);
//
//    }


    public void setActionBarIcon(int icon) {
        getSupportActionBar().setIcon(getDrawable(this, icon));

    }

    public static Drawable getDrawable(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getDrawable(id, context.getTheme());
        } else {
            return context.getResources().getDrawable(id);
        }
    }

    public void setActionBarIcon(Drawable drawable) {
        getSupportActionBar().setIcon(drawable);

    }

//    public void gotoDonateActivity() {
//        goToActivity(DonateActivity.class);
//    }

    public void goToActivity(Class classobj) {
        startActivity(new Intent(BaseActivity.this, classobj));
    }

    public void goToActivity(Class classobj, Bundle bundle) {
        Intent intent = new Intent(BaseActivity.this, classobj);
        intent.putExtra(AppConstant.Keys.DATA, bundle);
        startActivity(intent);
    }


    public Bundle getBundleFromIntent(Activity activity) {
        Intent intent = activity.getIntent();
        if (intent.hasExtra(AppConstant.Keys.DATA)) {
            return intent.getBundleExtra(AppConstant.Keys.DATA);
        } else {
            return null;
        }
    }

//    public void setTabLayout(String[] names, BaseTabLayout tabLayout, ViewPager viewPager, PagerAdapter adapter) {
//
//        setActionBarTitle(names[0]);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        tabLayout.initTabLayout(this, names);
//
//        tabLayout.setAdapter(viewPager, adapter);
//
//
//    }


    public void gotoHomeAcitivity(Context context) {
//        context.startActivity(new Intent(context, S.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }


    public boolean isShow_home() {
        return show_home;
    }

    public void setShow_home(boolean show_home) {
        this.show_home = show_home;
    }


    public interface MenuOnClick {

    }

    SweetAlertDialog.OnSweetClickListener listener = new SweetAlertDialog.OnSweetClickListener() {
        @Override
        public void onClick(SweetAlertDialog sweetAlertDialog) {
            Utils.deletePrefs(BaseActivity.this);
            DIDbHelper.deleteTables(BaseActivity.this);
//                        showShortToast(BaseActivity.this, R.string.logout_message);
//                        CartApplication.getCart().setCartEmpty();
//                        invalidateOptionsMenu();


            goToActivity(LoginActivity.class);
            finish();
        }


    };


//    ServiceClient.ServiceCallBack logout_callback = new ServiceClient.ServiceCallBack() {
//        @Override
//        public void performThisWhenServiceEnds(String message, Object response) {
//
//            if (message.equalsIgnoreCase("true")) {
//                Utils.deletePrefs(BaseActivity.this);
//                showShortToast(BaseActivity.this, "" + response);
//                CartApplication.getCart().setCartEmpty();
//                invalidateOptionsMenu();
//                finish();
//                onLogoutGotoHome();
//            } else {
//                showShortToast(BaseActivity.this, "" + response);
//            }
//        }
//    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_LOGIN && resultCode == RESULT_OK) {
//            innerLaunch = data.getBooleanExtra(UrlConstants.KEY_INNER_LAUNCH, false);
//            forCart = data.getBooleanExtra(UrlConstants.KEY_FOR_CART, false);
//            if (forCart) {
//                synchroniseCart();
//            }
        }
    }


    public boolean isSplash() {
        return isSplash;
    }

    public void setSplash(boolean splash) {
        isSplash = splash;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i("Destroy", "Called");

    }


    public void syncData() {
        if (Utils.isConnectingToInternet(this)) {

            showProgress();
            if (DIDbHelper.isPendingPods(this)) {

                Intent i = new Intent(BaseActivity.this, UploadingService.class);
                startService(i);
            } else {

                performRequestSyncData();
            }

        } else {

            hideProgress();
//            finish();


        }

//        performRequestSyncData();
//        goToActivity(ParcelDetailActivity.class);
    }


    DIReceiver diReceiver = new DIReceiver() {
        @Override
        public void proccessDIReceiver(boolean warehouse) {
            if (warehouse) {
                //process warehouse delivery broadcast
                whDeliveryUpdated();
            } else {
                performRequestSyncData();
            }
        }

        @Override
        public void proccessCustParcelDelivered(boolean isSuccess) {
            Log.i("proccessCustDelivered", "Delivered");
            custParcelDelivered(isSuccess);
        }


        @Override
        public void proccessNetworkChange() {
            syncData();
        }
    };

    public void whDeliveryUpdated() {

    }

    public void custParcelDelivered(boolean isSuccess) {

    }

    private void performRequestSyncData() {
        showProgress();
        Log.i("performRequestSyncData", "start" + System.currentTimeMillis());
        ArrayList<UpdatedParcelData> parcelDatas = DIDbHelper.getDeliveryInfoForUpdateAndSYNC(this);
        ArrayList<UpdatedPickupParcelData> pickupParcelDatas = DIDbHelper.getPickupInfoForUpdateAndSYNC(this);
        ArrayList<TransactionData> transcDatas = DIDbHelper.getInvoices(this);
//        Map<String, String> params = DIRequestCreator.getInstance(this).getSyncDataMapParams(parcelDatas, transcDatas);
        Map<String, String> params = DIRequestCreator.getInstance(this).getSyncDataMapParams_New(parcelDatas, transcDatas, pickupParcelDatas);
        RequestParams requestparams = new RequestParams(params);
        Log.i("Params", "" + requestparams);

        CourierApplication.serviceManager().postRequest(UrlConstants.URL_SYNC_AND_UPDATE, params, null, response_listener, response_errorlistener, "SYNC_DATA");
//        hideProgress();

    }

    public void showProgress() {
        if (progress != null && progress_layout != null) {
            Log.i("BaseActivity", "Progress is visible");
            progress_layout.setVisibility(View.VISIBLE);
            progress.start();
        } else {
            Log.i("BaseActivity", "Progress is null");
        }
    }

    public void hideProgress() {
        if (progress != null && progress_layout != null) {
            Log.i("BaseActivity", "Progress is invisible");
            progress_layout.setVisibility(View.GONE);
            progress.stop();
        } else {
            Log.i("BaseActivity", "Progress is null");
        }
    }

    private void performRequestSyncData(UpdatedParcelData parcelDatas) {
        ArrayList<UpdatedParcelData> arrayList = new ArrayList<>();
        arrayList.add(parcelDatas);
        ArrayList<TransactionData> transcDatas = DIDbHelper.getInvoices(this);

        Map<String, String> params = DIRequestCreator.getInstance(this).getSyncDataMapParams(arrayList, transcDatas);
        CourierApplication.serviceManager().postRequest(UrlConstants.URL_SYNC_AND_UPDATE, params, response_listener, response_errorlistener, "SYNC_DATA");

    }

    Response.Listener response_listener = new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {

//            Log.d("Response: ", response.toString());
            try {
                JSONObject jsonObject = new JSONObject(response);
                //String transdata = "[{\"barcode\":\"030300000012\",\"collectedby\":\"Vineet\",\"currency\":\"$\",\"iscard\":false,\"totalamount\":\"116.0\",\"transcid\":\"\",\"transTimeStamp\":\"2017-01-25 04:13:24\",\"transtype\":\"1\"}]";
                //jsonObject.put("transdata", new JSONArray(transdata));
                response = jsonObject.toString();


                //Toast.makeText(BaseActivity.this, DIHelper.getMessage(jsonObject), Toast.LENGTH_LONG).show();
                showSnackbar(DIHelper.getMessage(jsonObject));
                if (DIHelper.getStatus(jsonObject)) {
                    Gson gson = new Gson();
                    Log.i("DB", "starttime: " + System.currentTimeMillis());
                    ParcelListingData parcelListingData = gson.fromJson(response, ParcelListingData.class);
                    Log.i("gson", "starttime: " + System.currentTimeMillis());
                    DIDbHelper.deleteTables(BaseActivity.this);
                    Log.i("deleteTables", "starttime: " + System.currentTimeMillis());
                    if (parcelListingData != null) {

//                        DIDbHelper.insertDeliveryAndStatusInfoListIntoDb(BaseActivity.this, parcelListingData.getDeliveryData()); // insert only delivery data
                        DIDbHelper.insertDataIntoDb(BaseActivity.this, parcelListingData); // Insert delivery and pickup both data
                        Log.i("DIDbHelper", "starttime: " + System.currentTimeMillis());
                        DIDbHelper.insertTransactionInfoToDatabase(BaseActivity.this, parcelListingData.getTransdata());
                        Log.i("DB", "endtime: " + System.currentTimeMillis());
                    }

                    response();

                } else {

//                    login_btn.setClickable(true);

                }

            } catch (JSONException e) {
                e.printStackTrace();

                showSnackbar(ExceptionMessages.getMessageFromException(BaseActivity.this, -1, e));

            } catch (Exception e) {
                e.printStackTrace();

                showSnackbar(ExceptionMessages.getMessageFromException(BaseActivity.this, -1, e));
            }

            hideProgress();
        }
    };

    public void response() {

    }


    Response.ErrorListener response_errorlistener = new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("onErrorResponse: ", error.toString());
            hideProgress();

            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                showSnackbar(R.string.exception_alert_message_timeout_exception);
            } else if (error instanceof AuthFailureError) {
                //TODO
                showSnackbar("AuthFailure Error");
            } else if (error instanceof ServerError) {
                //TODO
                showSnackbar(R.string.exception_alert_message_internal_server_error);
            } else if (error instanceof NetworkError) {
                //TODO
                showSnackbar(R.string.exception_alert_message_network);
            } else if (error instanceof ParseError) {
                //TODO
                showSnackbar(R.string.exception_alert_message_parsing_exception);
            } else {
                showSnackbar(R.string.exception_alert_message_error);
            }
        }
    };

    public void showSnackbar(int msg) {
        View snakBarView = findViewById(R.id.root_appbar);
        if (snakBarView != null) {
            Snackbar snackbar = Snackbar.make(snakBarView, msg, Snackbar.LENGTH_LONG);
//        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this,  R.color.colorPrimary));
//        snackbar.getView().setBackgroundResource(R.color.colorPrimary);
            snackbar.show();
        }
    }

    public void showSnackbar(String msg) {
        View snakBarView = findViewById(R.id.root_appbar);
        if (snakBarView != null) {
            Snackbar snackbar = Snackbar.make(snakBarView, msg, Snackbar.LENGTH_LONG);
//        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this,  R.color.colorPrimary));
//        snackbar.getView().setBackgroundResource(R.color.colorPrimary);
            snackbar.show();
        }
    }

//    NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver() {
//        @Override
//        public void onNetworkChange() {
//            syncData();
//        }
//    };

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(diReceiver, new IntentFilter(AppConstant.ACTION_POD));
//        registerReceiver(networkChangeReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE") );
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(diReceiver);
//        unregisterReceiver(networkChangeReceiver);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }


    public void handleFragmentBackPressed() {

        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("CheckoutActivity", "popping backstack" + +fm.getBackStackEntryCount());
            fm.popBackStack();
        } else {
            if (doubleBackToExitPressedOnce) {
//                fm.popBackStack();
                Log.i("CheckoutActivity", "nothing on backstack, calling super");
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
//            Snackbar.make(cordinatorLayout, R.string.exit_msg, Snackbar.LENGTH_LONG).show();
            showSnackbar(R.string.exit_msg);

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

        }
    }

    public CoordinatorLayout getCordinatorLayout() {
        return cordinatorLayout;
    }

    public void navigateToFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();

        FragmentManager manager = getSupportFragmentManager();

//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);

        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
            ft.replace(R.id.container, fragment);
            ft.addToBackStack(backStateName);

            ft.commit();
        }
    }

    public void navigateToFragment(Context context, Fragment fragment) {
        String backStateName = fragment.getClass().getName();

        FragmentManager manager = ((BaseActivity) context).getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.container, fragment);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }


    public boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    public void getMultiplePermissions(int permissionrequestcode, String[] permissions) {


        if (!hasPermissions(this, permissions)) {
            ActivityCompat.requestPermissions(this, permissions, permissionrequestcode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        switch (requestCode) {
            case AppConstant.MULTIPLE_PERMISSION_REQUESTCODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        ) {
                    boolean notgranted = false;
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            notgranted = true;
                            SweetAlertUtil.showDialogwithNeutralButton(this, "Permissions", "Some of permissions are not Granted,Please grand Permission to Continue", "ok", permissiondialoglistener).show();
                            break;
                        }
                    }


                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    finish();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void gotPermission() {


    }


//    private void checkCameraPermission(int requestcode,int request) {
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            // Should we show an explanation?
//
//
//            // No explanation needed, we can request the permission.
//
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.CAMERA},
//                    MY_PERMISSIONS_REQUEST_CAMERA);
//
//            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
//            // app-defined int constant. The callback method gets the
//            // result of the request.
//
//        }else
//        {
//            gotCameraPermission();
//        }
//    }
//
//    private void gotCameraPermission() {
//
//    }

    public void processNotiIntent(Context context) {
        try {
            if (NotiHelper.isNotificationIntent(getIntent())) {
                handleNotificationIntent(context, getIntent());
            }
        } catch (Exception e) {
            e.printStackTrace();
            SweetAlertUtil.showDialogwithNeutralButton(context, getString(R.string.exception), e.getMessage(), getString(R.string.ok), new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {

                }
            }).show();
        }
    }

    /*
Used to process
 */
    public void handleNotificationIntent(Context context, Intent intent) {
        Intent noti_intent = null;
        try {

            Log.i("BaseActivity", "handleNotificationIntent");
            String jsonstring = intent.getStringExtra(UrlConstants.KEY_DATA);
            JSONObject jsonObject = new JSONObject(jsonstring);
            String type = jsonObject.getString(UrlConstants.KEY_TYPE);
            Log.i("BaseActivity", type + "");
            switch (Integer.parseInt(type)) {
                case NotiHelper.NOTI_REQUEST_GENERATED:
                    Log.i("Noti", "NOTI_REQUEST_GENERATED");
//                    navigateToFragment(WhInvoiceFragment.newInstance());
                    navigateToFragment(WhReadyForExecutiveFragment.newInstance());

//                    String catid = jsonObject.getString(UrlConstants.KEY_CATID);
//                    noti_intent = new Intent(context, WhDashboardActivity.class);
//                    noti_intent.putExtra(UrlConstants.KEY_CATID, catid);
//                    Bundle bundle = new Bundle();
//                    bundle.putString(UrlConstants.KEY_CATEGORY_ID, catid);
//                    bundle.putString(UrlConstants.KEY_SUBCATEGORY_ID, catid);
//
//
//                    noti_intent.putExtra(UrlConstants.KEY_DATA, bundle);


                    break;

                case NotiHelper.NOTI_INVOICE_GENERATED:
                    Log.i("Noti", "NOTI_INVOICE_GENERATED");

                    navigateToFragment(CustReadyInvoiceDeliveryFragment.newInstance());


                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private SweetAlertDialog.OnSweetClickListener permissiondialoglistener = new SweetAlertDialog.OnSweetClickListener() {
        @Override
        public void onClick(SweetAlertDialog sweetAlertDialog) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", getPackageName(), null));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }


    };

    /**
     * function to Check Whether Device has Marshmallow or Above
     *
     * @return True if device has marshmallow or greater otherwise false
     */
    public boolean isMarshMallow() {
        return DeviceInfoUtil.getDeviceApiVersion(this) >= Build.VERSION_CODES.M;
    }


    /**
     * function to get Application Instance in Activities
     *
     * @return DropInsta Instance
     */

    public CourierApplication getApp() {
        return (CourierApplication) getApplication();
    }

    public boolean isValidString(String string) {
        return string != null && string.length() > 0;

    }

    public void requestLocationUpdate() {
        if (getApp() != null) {
            requestSingleLocationFix(getApp().easyLocationRequest);
        }

    }


    @Override
    public void onLocationPermissionGranted() {
        Log.i("locationPermsnGranted", "abc");
//        updateErrorToServer("locationProvidrEnabled");
    }

    @Override
    public void onLocationPermissionDenied() {
        Log.i("locationPermsnDenied", "abc");
//        updateErrorToServer("locationProvidrEnabled");
    }

    @Override
    public void onLocationReceived(Location location) {
        Log.i("locationReceived", "abc" + location.getLongitude() + "," + location.getLatitude());
        updateLocationToServer(location);
    }

    @Override
    public void onLocationProviderEnabled() {
        Log.i("locationProvidrEnabled", "abc");
//        updateErrorToServer("locationProvidrEnabled");
    }

    @Override
    public void onLocationProviderDisabled() {
        Log.i("locationProvidrDsabled", "abc");
//        updateErrorToServer("locationProvidrEnabled");
    }


    public void updateErrorToServer(String message) {
        Map<String, String> params = DIRequestCreator.getInstance(this).getLocationErrorParams(Utils.getUserId(this), message);

        CourierApplication.serviceManager().postRequest(UrlConstants.URL_DRIVER_LOCATION_UPDATE, params, null, response_listener, response_errorlistener, sTAG);

    }

    public void updateLocationToServer(Location location) {
        Map<String, String> params = DIRequestCreator.getInstance(this).getLocationRequestParams(Utils.getUserId(this), location);

        CourierApplication.serviceManager().postRequest(UrlConstants.URL_DRIVER_LOCATION_UPDATE, params, null, response_listener, response_errorlistener, sTAG);

    }

}
