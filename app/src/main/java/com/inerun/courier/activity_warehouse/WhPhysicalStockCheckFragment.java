package com.inerun.courier.activity_warehouse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arasthel.asyncjob.AsyncJob;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.inerun.courier.R;
import com.inerun.courier.activity_auction.IonServiceManager;
import com.inerun.courier.adapter.WhAddStockParcelAdapter;
import com.inerun.courier.base.BaseFragment;
import com.inerun.courier.base.DeviceInfoUtil;
import com.inerun.courier.base.SweetAlertUtil;
import com.inerun.courier.constant.Utils;
import com.inerun.courier.model.SyncPhysicalStockData;
import com.inerun.courier.model.WhPhysicalStock;
import com.inerun.courier.network.ServiceManager;
import com.inerun.courier.scanner.CameraTestActivity;
import com.inerun.courier.sqldb.AppDatabase;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by vineet on 06/06/18.
 */

public class WhPhysicalStockCheckFragment extends BaseFragment implements View.OnClickListener {

    private static final int PARCEL_SCAN = 102;
    private RecyclerView recyclerView;
    private WhAddStockParcelAdapter adapter;
    private LinearLayout addparcel;
    private TextView sync_btn;
    SweetAlertDialog progressdialog;
    public IonServiceManager serviceManager;
    private String msg = "";
    private RelativeLayout root_lay;

    public static WhPhysicalStockCheckFragment newInstance() {

        Bundle args = new Bundle();

        WhPhysicalStockCheckFragment fragment = new WhPhysicalStockCheckFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int inflateView() {
        return R.layout.wh_physical_stock_check_fragment;
    }

    @Override
    public void customOnCreateView(View root, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) throws Exception {

        setShowBackArrow(true);
        IonServiceManager.Builder builder = new IonServiceManager.Builder(activity());
        serviceManager = builder.build();

        root_lay = root.findViewById(R.id.root_lay);
        recyclerView = root.findViewById(R.id.parcelstock_listview);
        addparcel = (LinearLayout) getViewById(R.id.addstockParcel_layout);
        sync_btn = (TextView) getViewById(R.id.sync_btn);

        addparcel.setOnClickListener(this);
        sync_btn.setOnClickListener(this);
        root_lay.setVisibility(View.GONE);

        initAdapter();
        getData();

    }

    private void initAdapter() {
        adapter = new WhAddStockParcelAdapter(activity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void getData() {
        showProgress();

        new AsyncJob.AsyncJobBuilder<List<WhPhysicalStock>>()
                .doInBackground(new AsyncJob.AsyncAction<List<WhPhysicalStock>>() {
                    @Override
                    public List<WhPhysicalStock> doAsync() {
                        Log.i("Query", "doInBackground()");
                        List<WhPhysicalStock> whPhysicalStocks = SQLite.select().from(WhPhysicalStock.class).queryList();
                        Log.i("Query", SQLite.select().from(WhPhysicalStock.class).getQuery());
                        return whPhysicalStocks;
                    }
                })
                .doWhenFinished(new AsyncJob.AsyncResultAction<List<WhPhysicalStock>>() {
                    @Override
                    public void onResult(List<WhPhysicalStock> whPhysicalStocks) {

                        Log.i("Query", "onResult()");
                        try {
                            setData(whPhysicalStocks);
                        } catch (Exception e) {
                            showException(e);
                            hideProgress();

                        }

                    }
                }).create().start();
    }

    private void setData(List<WhPhysicalStock> whPhysicalStocks) {
        Log.i("Query", "setData()");
        List<WhPhysicalStock> emptyWhPhysicalStocks = new ArrayList<>();

        if (whPhysicalStocks != null && whPhysicalStocks.size() > 0) {
            Log.i("Query", "reverse start()");
            Collections.reverse(whPhysicalStocks);
            Log.i("Query", "reverse end()");
            adapter.addParcelToList(whPhysicalStocks);
            Log.i("Query", "addParcelToList ()");
        } else {
            adapter.addParcelToList(emptyWhPhysicalStocks);
        }

        hideProgress();
        root_lay.setVisibility(View.VISIBLE);
        Log.i("Query", "setVisibility ()");
    }



    @Override
    protected String getAnalyticsName() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addstockParcel_layout:

                startActivityForResult(new Intent(activity(), CameraTestActivity.class), PARCEL_SCAN);

                break;
            case R.id.sync_btn:
                startSync();

                break;
        }
    }

    private void startSync() {
        SweetAlertUtil.showMessageWithCallback(activity(), getString(R.string.sync_confirm_title), getString(R.string.sync_confirm_msg), getString(R.string.yes), getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                Log.i("delete", "positive");
                try {
                    syncWithServer();
                } catch (ServiceManager.InvalidParametersException e) {
                    e.printStackTrace();
                }
                sweetAlertDialog.cancel();

            }
        }, new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.cancel();

            }
        });
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
                            if (dataToSendOnServer != null && dataToSendOnServer.length() > 0) {
                                callService(dataToSendOnServer);
                            } else {
                                hideSyncProgress();
                                SweetAlertUtil.showSweetErrorToast(activity(), getString(R.string.no_data_error_msg));
                            }
                        } catch (ServiceManager.InvalidParametersException e) {
                            showException(e);
                            hideSyncProgress();

                        } catch (Exception e) {
                            showException(e);
                            hideSyncProgress();

                        }

                    }
                }).create().start();


    }

    private String getDataFromLocalDbToSyncWithServer() {


        List<WhPhysicalStock> whPhysicalStockList = SQLite.select().from(WhPhysicalStock.class).queryList();

        String userId = "";
        if (Utils.isUserLoggedIn(activity())) {
            userId = Utils.getUserId(activity());
        }

        SyncPhysicalStockData syncData = new SyncPhysicalStockData(whPhysicalStockList, userId);

        String data = "";
        if (whPhysicalStockList != null && whPhysicalStockList.size() > 0) {
            data = getGsonInstance().toJson(syncData);
        }

//        return getGsonInstance().toJson(syncData);
        return data;


    }


    private void callService(String dataToSendOnServer) throws IonServiceManager.InvalidParametersException, Exception {


        String params[] = {IonServiceManager.KEYS.ANDROID_ID, DeviceInfoUtil.getAndroidID(activity()), IonServiceManager.KEYS.Device_Name, DeviceInfoUtil.getModelName(activity()), IonServiceManager.KEYS.DATA, dataToSendOnServer};
        Log.i("params", params.toString());

        serviceManager.postRequestToServerWOprogress(serviceManager.getAddress().SyncPhysicalStockData, new IonServiceManager.ResponseCallback(activity()) {
            @Override
            public void onException(String exception) {
                Log.i("Req Completed", "" + exception);
//                showException(exception);
                SweetAlertUtil.showWarningWithCallback(activity(), exception, getString(R.string.ok), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
//                        ((Activity) activity()).finish();
                    }
                });
                hideSyncProgress();

            }

            @Override
            public void onResponse(boolean status, String message, Object result) throws JSONException, JsonSyntaxException {

                msg = message;
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
//                            JsonObject array = result.getAsJsonObject();
//                            Log.i("Array", "" + array);
                            Log.i("", "" + System.currentTimeMillis());
//                            Gson gson = getGsonInstance();


//                            SyncResponseBo responseBo = gson.fromJson(array, SyncResponseBo.class);


//                            saveDataintoLocalDb(responseBo);
                            return true;
                        } catch (Exception e) {
                            e.printStackTrace();
//                            showException(e);
                            hideSyncProgress();
                            return false;
                        }

                    }
                })
                .doWhenFinished(new AsyncJob.AsyncResultAction<Boolean>() {
                    @Override
                    public void onResult(Boolean result) {


                        if (result) {

//                            syncCompleted();

//                            SweetAlertUtil.showSweetMessageToast(activity(),getString(R.string.sync_complete_msg));
                            SweetAlertUtil.showSweetMessageLongToast(activity(), msg);
                            getData();


                        } else {

                            showException(new RuntimeException(getString(R.string.foodmenu_error_unknown_error)));
                        }
                        hideSyncProgress();

                    }
                }).create().start();


    }

    public void deleteAllMenus() {
        Log.i("deleteAllMenus", "deleteAllMenus");
        String sourcePath = FlowManager.getContext().getDatabasePath(AppDatabase.NAME) + ".db";
        File source = new File(sourcePath);
        if (source.exists()) {
            Delete.tables(WhPhysicalStock.class);
            Log.i("deleteAllMenus", "Completed");
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {

            switch (requestCode) {
                case PARCEL_SCAN:

                    if (resultCode == Activity.RESULT_OK && data.hasExtra(CameraTestActivity.INTENT_BARCODE_VALUE)) {
                        String barcode = data.getStringExtra(CameraTestActivity.INTENT_BARCODE_VALUE);


                        if (barcode != null && barcode.length() > 0) {
                            Log.i("BARCODE", "WhReadyRequestParcelFragment: with space " + barcode);
                            barcode = barcode.replaceAll(" ", "");
                            barcode = barcode.replaceAll("\\x1d", "");
                            Log.i("BARCODE", "WhReadyRequestParcelFragment: " + barcode);

                            WhPhysicalStock whPhysicalStock = new WhPhysicalStock();
                            whPhysicalStock.setParcel_barcode(barcode);


                            if (adapter.getParcellist() != null && adapter.getParcellist().size() > 0) {
                                List<WhPhysicalStock> parcellist = adapter.getParcellist();

                                int index = parcellist.indexOf(new WhPhysicalStock(barcode) {
                                    @Override
                                    public boolean equals(Object obj) {
                                        return getParcel_barcode().equals(((WhPhysicalStock) obj).getParcel_barcode());
                                    }
                                });

                                if (index == -1) {
                                    adapter.addParcelToList(whPhysicalStock);
                                    insertParcelInDB(barcode);
                                    Toast.makeText(activity(), "No of Parcels : " + adapter.getParcellist().size(), Toast.LENGTH_SHORT).show();
                                } else {
                                    SweetAlertUtil.showSweetErrorToast(activity(), getString(R.string.stock_parcel_error_msg));
                                }

                            } else {
                                adapter.addParcelToList(whPhysicalStock);
                                insertParcelInDB(barcode);
                            }
                        } else {

                            SweetAlertUtil.showSweetErrorToast(activity(), getString(R.string.valid_barcode_readable_msg));
                        }


                    }
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void insertParcelInDB(String barcode) {
        WhPhysicalStock physicalStock = new WhPhysicalStock();
        physicalStock.setParcel_barcode(barcode);
        physicalStock.setStatus(1);
        physicalStock.setCreated_on(new Date());

        physicalStock.save();
    }

    public void hideSyncProgress() {
        if (progressdialog != null && progressdialog.isShowing())
            progressdialog.dismiss();

    }
}
