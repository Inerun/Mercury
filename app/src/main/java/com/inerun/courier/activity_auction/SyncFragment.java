package com.inerun.courier.activity_auction;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.arasthel.asyncjob.AsyncJob;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.inerun.courier.R;
import com.inerun.courier.base.AuctionBaseFragment;
import com.inerun.courier.base.DeviceInfoUtil;
import com.inerun.courier.base.SweetAlertUtil;
import com.inerun.courier.constant.Utils;
import com.inerun.courier.helper.DIHelper;
import com.inerun.courier.model.AuctionDetail;
import com.inerun.courier.model.AuctionInvoice;
import com.inerun.courier.model.AuctionInvoice_Table;
import com.inerun.courier.model.AuctionItem;
import com.inerun.courier.model.AuctionItem_Table;
import com.inerun.courier.model.BaseModelClass;
import com.inerun.courier.model.SiteConfiguration;
import com.inerun.courier.model.SiteConfiguration_Table;
import com.inerun.courier.model.SyncData;
import com.inerun.courier.model.SyncResponseBo;
import com.inerun.courier.network.ServiceManager;
import com.inerun.courier.sqldb.AppDatabase;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.json.JSONException;

import java.io.File;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by vinay on 26/09/17.
 */

public class SyncFragment extends AuctionBaseFragment implements View.OnClickListener {

    TextView device_number, company_name, sync_heading, sync_msg;
    View indicator_view;
    View indicator_circle;
    DonutProgress donut_progress;
    Button start_btn, ok_btn;
    private int numberOfInvoicesSynced = 0;
    private boolean gotoLocations = true;
    SweetAlertDialog progressdialog;

    public static SyncFragment newInstance() {

        Bundle args = new Bundle();

        SyncFragment fragment = new SyncFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int initLayout() {
        return R.layout.auction_fragment_sync;
    }

    @Override
    protected void initView(View rootview) {

        device_number = rootview.findViewById(R.id.sync_device_number);
        company_name = rootview.findViewById(R.id.sync_company_name);
        indicator_view = rootview.findViewById(R.id.sync_indicator_view);
        indicator_circle = rootview.findViewById(R.id.sync_indicator_circle);
        donut_progress = rootview.findViewById(R.id.donut_progress);
        sync_heading = rootview.findViewById(R.id.sync_text);
        sync_msg = rootview.findViewById(R.id.sync_msg);
        start_btn = rootview.findViewById(R.id.sync_start_button);
        ok_btn = rootview.findViewById(R.id.sync_ok_button);

//        if (!getApp().fcsPref.hasLastSync()) {
        setData();
//        } else {
//            startActivity(new Intent(activity(), HomeActivity.class));
//            activity().finish();
//        }


    }

    private void setData() {
        device_number.setText(getString(R.string.navigation_header_device, ""));
//        Helper.setSimpleText(device_number,getApp().fcsPref.deviceName());
        start_btn.setOnClickListener(this);
        ok_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sync_start_button:
                try {
                    startSync();
                } catch (ServiceManager.InvalidParametersException e) {
                    showException(e);

                } catch (Exception e) {
                    showException(e);
                }


                break;


            case R.id.sync_ok_button:

                saveSync();

                break;
        }

    }


    /**
     * perform synchronisation between offline and online databases
     * change text and buttons
     * call syncCompleted in callback to show user that sync has been completed
     */
    private void startSync() throws ServiceManager.InvalidParametersException {


        DIHelper.setSimpleText(sync_heading, getString(R.string.sync_processing_text));
        DIHelper.setSimpleText(sync_msg, getString(R.string.sync_processing_msg));
//        donut_progress.setVisibility(View.VISIBLE);
        start_btn.setVisibility(View.INVISIBLE);
        indicator_circle.setVisibility(View.GONE);
        indicator_view.setVisibility(View.GONE);

//        if (!isTokenPresent()) {
//            Log.i("AuthenticationFragment", "Authentication token not present request it from server");
//            logout();
//        } else {
//
////            getApp().appData.setToken(getPrefs().token());
////            getApp().appData.setDeviceno(getPrefs().deviceNo());
//            Log.i("AuthenticationFragment", "Authentication token is present Goto SyncActivity");
//
//
        syncWithServer();
//
//        }
//        new Handler().postDelayed(
//
//                new Runnable() {
//            @Override
//            public void run() {
//                syncCompleted();
//            }
//        }, 2000);


    }

    private void syncWithServer() throws ServiceManager.InvalidParametersException {
        progressdialog = SweetAlertUtil.getProgressDialog(activity());
        progressdialog.show();


        new AsyncJob.AsyncJobBuilder<String>()
                .doInBackground(new AsyncJob.AsyncAction<String>() {
                    @Override
                    public String doAsync() {

                        String datatobeSynced = getDataFromLocalDbToSyncWithServer();
//                        String datatobeSynced = null;
                        return datatobeSynced;
                    }
                })
                .doWhenFinished(new AsyncJob.AsyncResultAction<String>() {
                    @Override
                    public void onResult(String dataToSendOnServer) {


                        try {
                            callService(dataToSendOnServer);
                        } catch (ServiceManager.InvalidParametersException e) {
                            showException(e);
                            hideProgress();

                        } catch (Exception e) {
                            showException(e);
                            hideProgress();

                        }

                    }
                }).create().start();


    }


    public void hideProgress() {
        if (progressdialog != null && progressdialog.isShowing())
            progressdialog.dismiss();

    }

    private String getDataFromLocalDbToSyncWithServer() {


        List<AuctionInvoice> auctionInvoices = SQLite.select().from(AuctionInvoice.class).where(AuctionInvoice_Table.sync_status.eq(AuctionInvoice.SYNC_STATUS_PENDING)).queryList();
        numberOfInvoicesSynced = auctionInvoices.size();
        List<AuctionItem> auctionItems = SQLite.select().from(AuctionItem.class).where(AuctionItem_Table.sync_status.eq(AuctionItem.SYNC_STATUS_PENDING)).queryList();
        SiteConfiguration siteConfiguration = SQLite.select().from(SiteConfiguration.class).where(SiteConfiguration_Table.sync_status.eq(BaseModelClass.SYNC_STATUS_PENDING)).querySingle();

        String userId = "";
        if(Utils.isUserLoggedIn(activity())) {
            userId = Utils.getUserId(activity());
        }
        SyncData syncData = new SyncData(auctionItems, auctionInvoices, siteConfiguration, DeviceInfoUtil.getModelName(activity()), DeviceInfoUtil.getAndroidID(activity()), userId);


        return getGsonInstance().toJson(syncData);


    }

    private void callService(String dataToSendOnServer) throws IonServiceManager.InvalidParametersException, Exception {


//        String encrypteddataToSendOnServer = MCrypt.bytesToHex(MCrypt.getInstance().encrypt(dataToSendOnServer));
//        String encrypteddataToSendOnServer = Base64.encodeToString( MCrypt.getInstance().encrypt(dataToSendOnServer), Base64.NO_WRAP);
//        Base64.encodeToString(encrypted, Base64.NO_WRAP);

//        String params[] = {ServiceManager.KEYS.ANDROID_ID, DeviceInfoUtil.getAndroidID(activity()), ServiceManager.KEYS.TOKEN, DeviceInfoUtil.getAndroidID(activity()), ServiceManager.KEYS.DATA, encrypteddataToSendOnServer};
        String params[] = {IonServiceManager.KEYS.ANDROID_ID, DeviceInfoUtil.getAndroidID(activity()), IonServiceManager.KEYS.DATA, dataToSendOnServer};
        Log.i("params", params.toString());

        serviceManager.postRequestToServerWOprogress(serviceManager.getAddress().SyncData, new IonServiceManager.ResponseCallback(activity()) {
            @Override
            public void onException(String exception) {
                Log.i("Req Completed", "" + exception);
//                showException(exception);
                SweetAlertUtil.showWarningWithCallback(activityContext, exception, activityContext.getString(R.string.ok), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    ((Activity) activityContext).finish();
                    }
                });
                hideProgress();

            }

            @Override
            public void onResponse(boolean status, String message, Object result) throws JSONException, JsonSyntaxException {


//                String trimmedstring = ((String) result).trim();


//                gson.toJsonTree(trimmedstring);
//            String lenientstring=    gson.toJson(result);
//                check this
//                String lenientstring = gson.toJson(result);
//
//                JsonParser parser = new JsonParser();

//                JsonObject finaljson = parser.parse(trimmedstring).getAsJsonObject();
//                JsonObject finaljson = lenientstring.getAsJsonObject();

                parseJsonAndSaveIntoDatabase((JsonObject) result);


            }

        }, params);
    }


    public void parseJsonAndSaveIntoDatabase(final JsonObject result) throws JsonSyntaxException {


        new AsyncJob.AsyncJobBuilder<Boolean>()
                .doInBackground(new AsyncJob.AsyncAction<Boolean>() {
                    @Override
                    public Boolean doAsync() {

                        try {


                            deleteAllMenus();
                            Log.i("deleteAllMenus", "deleteAllMenus");
                            JsonObject array = result.getAsJsonObject();
                            Log.i("Array", "" + array);
                            Log.i("", "" + System.currentTimeMillis());
                            Gson gson = getGsonInstance();


                            SyncResponseBo responseBo = gson.fromJson(array, SyncResponseBo.class);


                            saveDataintoLocalDb(responseBo);
                            return true;
                        } catch (Exception e) {
                            e.printStackTrace();
//                            showException(e);
                            hideProgress();
                            return false;
                        }

                    }
                })
                .doWhenFinished(new AsyncJob.AsyncResultAction<Boolean>() {
                    @Override
                    public void onResult(Boolean result) {


                        if (result) {

                            syncCompleted();
                        } else {

                            showException(new RuntimeException(getString(R.string.foodmenu_error_unknown_error)));
                        }
                        hideProgress();

                    }
                }).create().start();


    }


    public void deleteAllMenus() {
        Log.i("deleteAllMenus", "deleteAllMenus");
        String sourcePath = FlowManager.getContext().getDatabasePath(AppDatabase.NAME) + ".db";
        File source = new File(sourcePath);
        if (source.exists()) {
            Delete.tables(AuctionInvoice.class,
                    AuctionItem.class);
            Log.i("deleteAllMenus", "Completed");
        }

    }

    private void saveDataintoLocalDb(SyncResponseBo responseBo) {

        Log.i("saveData", "saveDataintoLocalDb");

        List invoiceList = responseBo.getAuctioninvoicesdata();
        List itemList = responseBo.getAuctionitemdata();
        AuctionDetail auctionDetail = responseBo.getAuctiondetail();
        SiteConfiguration siteConfiguration = responseBo.getSiteconfiguration();

        if (invoiceList != null) {
            FlowManager.getDatabase(AppDatabase.class)
                    .getTransactionManager()
                    .getSaveQueue()
                    .addAll(invoiceList);

        }
        if (itemList != null) {
            FlowManager.getDatabase(AppDatabase.class)
                    .getTransactionManager()
                    .getSaveQueue()
                    .addAll(itemList);
        }
        if (auctionDetail != null) {
            FlowManager.getDatabase(AppDatabase.class)
                    .getTransactionManager()
                    .getSaveQueue().
                    add(auctionDetail);
        }
        if (siteConfiguration != null) {
            FlowManager.getDatabase(AppDatabase.class)
                    .getTransactionManager()
                    .getSaveQueue().
                    add(siteConfiguration);
        }


//        getApp().fcsPref.saveLastSync("" + Helper.getFormattedDateAsString(System.currentTimeMillis()));
        Log.i("sync", "finished");


    }

    private void logout() {

    }

    /**
     * show the number of orders synchronised
     * change text and buttons
     * inform user that synchronisation has been completed
     */

    public void syncCompleted() {

        DIHelper.setSimpleText(sync_heading,getString(R.string.sync_completed_text));
        if (numberOfInvoicesSynced > 0) {
            DIHelper.setSimpleText(sync_msg, getString(R.string.sync_completed_msg_with_invoicenumber, "" + numberOfInvoicesSynced));
        } else {
            DIHelper.setSimpleText(sync_msg, getString(R.string.sync_completed_msg));
        }
        start_btn.setVisibility(View.GONE);
//        donut_progress.setVisibility(View.GONE);
        indicator_view.setVisibility(View.GONE);
        ok_btn.setVisibility(View.VISIBLE);

    }


    private void saveSync() {

        activity().setResult(Activity.RESULT_OK);
        activity().finish();
    }

}
