package com.inerun.courier.activity_driver;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inerun.courier.CourierApplication;
import com.inerun.courier.R;
import com.inerun.courier.adapter.ParcelAdapter;
import com.inerun.courier.base.BaseActivity;
import com.inerun.courier.base.DeviceInfoUtil;
import com.inerun.courier.base.SweetAlertUtil;
import com.inerun.courier.constant.AppConstant;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.constant.Utils;
import com.inerun.courier.data.ParcelListingData;
import com.inerun.courier.helper.DIHelper;
import com.inerun.courier.helper.SimpleDividerItemDecoration;
import com.inerun.courier.sql.DIDbHelper;
import com.inerun.courier.sqldb.AppDatabase;
import com.raizlabs.android.dbflow.config.FlowManager;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDashBoardActivity extends BaseActivity implements View.OnClickListener {

    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 102;
    AppBarLayout appbar;
    Toolbar toolbar;
    private TextView deliver_num, pending_num, error_text;
    private RecyclerView recyclerView;
    private ParcelAdapter mAdapter;
    private List<ParcelListingData.ParcelData> parcelDataList = new ArrayList<>();
    private ParcelListingData parcelListingData;
    private LinearLayout top_layout, delivered_parcel_lay, pending_parcel_lay;
    //    private CoordinatorLayout cordinatorLayout;
    private boolean doubleBackToExitPressedOnce = false;
    private TextView username, location;
    private RelativeLayout search;
    private TextView all_parcelscount;
    private TextView deliveredparcelscount, pendingparcelscount, paymentTotal_txt;
    private RelativeLayout  add_parcel_lay;
    private TextView returnparcelscount;
    private TextView cash_total, card_total, total_collection_count, dash_collected_num, dash_pending_collection_num;
    private LinearLayout all_parcels, payments, return_parcel_lay, cash_layout, card_layout, collection_layout, delivery_layout, pending_collection_lay, collected_parcel_lay;
    private TextView transfer_ready_count, transfer_waiting_count, accept_count;
    private LinearLayout transfer_ready_layout, transfer_waiting_layout, accept_layout;

    @Override
    public int customSetContentView() {
        return R.layout.app_bar_main;
    }

    @Override
    public void customOnCreate(Bundle savedInstanceState) {

//        cordinatorLayout = (CoordinatorLayout) findViewById(R.id.root_appbar);
//        getSupportActionBar().setIcon(R.mipmap.toolbaricon);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//        toolbar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                if (Build.VERSION.SDK_INT >= 16) {
//                    toolbar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                } else {
//                    toolbar.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                }
//                toolbar.animate().translationY(-toolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
//            }
//        });

//        error_text = (TextView) findViewById(R.id.error_textview);
//        top_layout = (LinearLayout) findViewById(R.id.top_layout);
//        deliver_num = (TextView) findViewById(R.id.dash_delivered_num);

//        pending_num = (TextView) findViewById(R.id.dash_pending_num);
////        deliver_num.setText(""+ parcelListingData.getDelivered_num());
////        pending_num.setText(""+ parcelListingData.getPending_num());
//
//        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
////        recyclerView.addItemDecoration(new DividerItemDecoration(this.getResources().getDrawable(R.drawable.parcel_item_thumbnail_divider)));
//
//
//        setDataAdapter(parcelDataList);

//        mAdapter = new ParcelAdapter(this, parcelDataList);
//        recyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(mAdapter);
//
//        recyclerView.setOnScrollListener(new HidingScrollListener() {
//            @Override
//            public void onHide() {
//                hideView();
//            }
//
//            @Override
//            public void onShow() {
//                showView();
//            }
//        });
        initView();
        getData();
        setData();



    }





    private void initView() {

        appbar = (AppBarLayout) findViewById(R.id.appbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        username = (TextView) findViewById(R.id.wh_home_name);
        location = (TextView) findViewById(R.id.wh_home_location);

        search = (RelativeLayout) findViewById(R.id.search_layout);

        all_parcels = (LinearLayout) findViewById(R.id.total_parcel_layout);
        all_parcelscount = (TextView) findViewById(R.id.total_parcel_count);
        deliveredparcelscount = (TextView) findViewById(R.id.dash_delivered_num);
        pendingparcelscount = (TextView) findViewById(R.id.dash_pending_num);
        returnparcelscount = (TextView) findViewById(R.id.return_count);
        paymentTotal_txt = (TextView) findViewById(R.id.payment_total);
        cash_total = (TextView) findViewById(R.id.cash_total);
        card_total = (TextView) findViewById(R.id.card_total);
        total_collection_count = (TextView) findViewById(R.id.total_collection_count);
        dash_collected_num = (TextView) findViewById(R.id.dash_collected_num);
        dash_pending_collection_num = (TextView) findViewById(R.id.dash_pending_collection_num);
        transfer_ready_count = (TextView) findViewById(R.id.transfer_ready_count);
        transfer_waiting_count = (TextView) findViewById(R.id.transfer_waiting_count);
        accept_count = (TextView) findViewById(R.id.accept_count);

        payments = (LinearLayout) findViewById(R.id.payment_received_layout);
        delivered_parcel_lay = (LinearLayout) findViewById(R.id.delivered_parcel_lay);
        pending_parcel_lay = (LinearLayout) findViewById(R.id.pending_parcel_lay);

        return_parcel_lay = (LinearLayout) findViewById(R.id.return_parcel_layout);
        cash_layout = (LinearLayout) findViewById(R.id.cash_layout);
        card_layout = (LinearLayout) findViewById(R.id.card_layout);
        collection_layout = (LinearLayout) findViewById(R.id.collection_layout);
        pending_collection_lay = (LinearLayout) findViewById(R.id.pending_collection_lay);
        collected_parcel_lay = (LinearLayout) findViewById(R.id.collected_parcel_lay);
        delivery_layout = (LinearLayout) findViewById(R.id.delivery_layout);
        transfer_ready_layout = (LinearLayout) findViewById(R.id.transfer_ready_layout);
        transfer_waiting_layout = (LinearLayout) findViewById(R.id.transfer_waiting_layout);
        accept_layout = (LinearLayout) findViewById(R.id.accept_layout);

        add_parcel_lay = (RelativeLayout) findViewById(R.id.add_parcel_lay);

        search.setOnClickListener(this);
        all_parcels.setOnClickListener(this);
        payments.setOnClickListener(this);
        delivered_parcel_lay.setOnClickListener(this);
        pending_parcel_lay.setOnClickListener(this);

        return_parcel_lay.setOnClickListener(this);
        add_parcel_lay.setOnClickListener(this);
        cash_layout.setOnClickListener(this);
        card_layout.setOnClickListener(this);
        collection_layout.setOnClickListener(this);
        pending_collection_lay.setOnClickListener(this);
        collected_parcel_lay.setOnClickListener(this);
        delivery_layout.setOnClickListener(this);
        transfer_ready_layout.setOnClickListener(this);
        transfer_waiting_layout.setOnClickListener(this);
        accept_layout.setOnClickListener(this);
    }


    private void getData() {
        parcelListingData = DIDbHelper.getParcelListData(this);
        parcelDataList = parcelListingData.getDeliveryData();
    }

    private void setDataAdapter(final List<ParcelListingData.ParcelData> parcelDataList) {
        if (parcelDataList != null && parcelDataList.size() > 0) {
//            error_text.setVisibility(View.GONE);
//            top_layout.setVisibility(View.VISIBLE);
            deliver_num.setText("" + parcelListingData.getDelivered_num());
            pending_num.setText("" + parcelListingData.getPending_num());


            mAdapter = new ParcelAdapter(this, this.parcelDataList);
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
//            recyclerView.addItemDecoration(new DividerItemDecoration(getDrawable(this, R.drawable.parcel_item_thumbnail_divider)));
            recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));

            recyclerView.setAdapter(mAdapter);

//            recyclerView.setOnScrollListener(new HidingScrollListener() {
//                @Override
//                public void onHide() {
//                    hideView();
//                }
//
//                @Override
//                public void onShow() {
//                    showView();
//                }
//            });

            mAdapter.setOnItemClickListener(new ParcelAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    Log.i("onItemClick", "OnClick");
//                Intent intent = new Intent(getActivity(), ProductListingActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(UrlConstants.KEY_DATA, parcelDataList.get(position));

//                startActivity(intent);
                    goToActivity(ParcelDetailActivity.class, bundle);
                    //slide from right to left
                    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);


                }
            });
        } else {
            top_layout.setVisibility(View.GONE);
            error_text.setVisibility(View.VISIBLE);
        }


    }


    private void hideView() {

//        appbar.animate().translationY(-appbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
//        toolbar.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
        top_layout.animate().translationY(-top_layout.getHeight()).setInterpolator(new AccelerateInterpolator(2));

        top_layout.setVisibility(View.GONE);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) recyclerView.getLayoutParams();
        params.weight = 10f;
        recyclerView.setLayoutParams(params);

//        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mFabButton.getLayoutParams();
//        int fabBottomMargin = lp.bottomMargin;
//        mFabButton.animate().translationY(mFabButton.getHeight()+fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    private void showView() {

//        appbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
//        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
        top_layout.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
        top_layout.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) recyclerView.getLayoutParams();
        params.weight = 7f;
        recyclerView.setLayoutParams(params);

//        mFabButton.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {

//            performRequestSyncData();
//            syncData();
            if (Utils.isConnectingToInternet(this)) {
                syncData();
            }else{
                SweetAlertUtil.showAlertDialogWithBlackTheme(this, getString(R.string.activity_base_alert_message_unknown_host_exception));
            }
//            parcelListingData = DIDbHelper.getParcelListData(this);
//            parcelDataList = parcelListingData.getDeliveryData();
//            Log.i("onOptionsItemSelected","" + parcelDataList.size());
//            setDataAdapter(parcelDataList);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }else if (id == R.id.nav_logout) {
//            logout();
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

    private void logout() {
        Utils.deletePrefs(this);
        showSnackbar(R.string.msg_logout);
        goToActivity(LoginActivity.class);
        finish();
    }


    @Override
    public void response() {
        super.response();
        parcelListingData = DIDbHelper.getParcelListData(DeliveryDashBoardActivity.this);
        parcelDataList = parcelListingData.getDeliveryData();
//        Log.i("onOptionsItemSelected", "" + parcelDataList.size());
//        setDataAdapter(parcelDataList);
//        initView();
        setData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
//        getData();
//        setData();
//        parcelListingData = DIDbHelper.getParcelListData(DeliveryDashBoardActivity.this);
//        Log.i("parcelListingData", "" + parcelListingData);
//
//        if (parcelListingData.getDeliveryData() != null && parcelListingData.getDeliveryData().size() > 0) {
//            parcelDataList = parcelListingData.getDeliveryData();
//            Log.i("onOptionsItemSelected", "" + parcelDataList.size());
//            setDataAdapter(parcelDataList);
//        } else {
//            syncData();
//        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.search_layout:
                goToActivity(DeliverySearchActivity.class);

                break;
            case R.id.payment_received_layout:
                goToActivity(DeliveryPaymentsActivity.class);

                break;
            case R.id.total_parcel_layout:
                goToActivity(DeliveryAllParcelsActivity.class);

                break;
            case R.id.delivered_parcel_lay:
                goToActivity(DeliveryDeliveredParcelsActivity.class);

                break;
            case R.id.pending_parcel_lay:
//                goToActivity(DeliveryPendingParcelsActivity.class);
                goToActivity(DeliverySearchActivity.class);
                break;
            case R.id.return_parcel_layout:
                goToActivity(DeliveryReturnParcelActivity.class);

                break;
            case R.id.add_parcel_lay:
                goToActivity(DeliveryAddParcelActivity.class);

                break;
            case R.id.cash_layout:
                Bundle bundle = new Bundle();
                bundle.putInt(UrlConstants.KEY_TAB_SELECTION, UrlConstants.CASH_TAB);
                goToActivity(DeliveryPaymentsActivity.class, bundle);

                break;
            case R.id.card_layout:
                Bundle bundle1 = new Bundle();
                bundle1.putInt(UrlConstants.KEY_TAB_SELECTION, UrlConstants.CARD_TAB);
                goToActivity(DeliveryPaymentsActivity.class, bundle1);

                break;
            case R.id.collection_layout:
//                goToActivity(CollectionPendingListingActivity.class);
                goToActivity(CollectionAllListingActivity.class);

                break;
            case R.id.pending_collection_lay:
                goToActivity(CollectionPendingListingActivity.class);

                break;
            case R.id.collected_parcel_lay:
                goToActivity(CollectionReceivedListingActivity.class);

                break;
            case R.id.delivery_layout:
                goToActivity(DeliveryAllParcelsActivity.class);

                break;
            case R.id.transfer_ready_layout:
                goToActivity(CollectionTransferReadyActivity.class);

                break;
            case R.id.transfer_waiting_layout:
                goToActivity(CollectionTransferWaitActivity.class);

                break;
            case R.id.accept_layout:
                goToActivity(CollectionAcceptActivity.class);

                break;
        }




    }

    private void setData()
    {

//        int parcelnum=50;
//        int parceldelivered=20;
//        int parcelpending=30;


        username.setText(CourierApplication.getUser().getName());
        location.setText(CourierApplication.getUser().getLocation());
        all_parcelscount.setText(""+parcelListingData.getDeliveryData().size());
        deliveredparcelscount.setText(""+parcelListingData.getDelivered_num());
        pendingparcelscount.setText(""+parcelListingData.getPending_num());
        returnparcelscount.setText(""+parcelListingData.getPending_num());

//        paymentparcelscount.setText(""+DIDbHelper.getPaymentTotalWithPickup(this));
//        paymentTotal_txt.setText(DIHelper.getValueInDecimal(DIDbHelper.getPaymentTotalWithPickup(this)));
        paymentTotal_txt.setText(DIDbHelper.getPaymentTotalWithPickup(this));

        cash_total.setText(""+ DIDbHelper.getTotalCashPaymentWithPickUp(this));
        card_total.setText(""+DIDbHelper.getTotalCardPaymentWithPickup(this));

        total_collection_count.setText("" + (parcelListingData.getPickup_num()+parcelListingData.getPickup_received_num()));
        dash_collected_num.setText("" + parcelListingData.getPickup_received_num());
        dash_pending_collection_num.setText("" + parcelListingData.getPickup_num());

        transfer_ready_count.setText("" + parcelListingData.getPickup_num());
        transfer_waiting_count.setText("" + 0);
        accept_count.setText("" + 0);


    }

    private void copyDBToAnotherLocation() {

//        String sourcePath = FlowManager.getContext().getDatabasePath(DBFlow_DriverDatabase.NAME) + ".db";
        String sourcePath = FlowManager.getContext().getDatabasePath(AppConstant.DB_NAME) +"";
        File source = new File(sourcePath);

        String destinationPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + AppConstant.DB_NAME + "";
        File destination = new File(destinationPath);
        try {
            Log.i("sourcepath", "" + sourcePath);
            Log.i("file", "" + source.exists());
            Log.i("file", "" + source.canWrite());
            Log.i("file", "" + source.canWrite());
//            destination.createNewFile();
            if (source.exists()) {
                FileUtils.copyFile(source, destination);
            } else {
                Log.i("Not Exist", "File not find");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        copyDBToAnotherLocation1();
    }

    private void copyDBToAnotherLocation1() {

        String sourcePath = FlowManager.getContext().getDatabasePath(AppDatabase.NAME) + ".db";
        File source = new File(sourcePath);

        String destinationPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + AppDatabase.NAME + ".db";
        File destination = new File(destinationPath);
        try {
            Log.i("sourcepath", "" + sourcePath);
            Log.i("file", "" + source.exists());
            Log.i("file", "" + source.canWrite());
            Log.i("file", "" + source.canWrite());
//            destination.createNewFile();
            if (source.exists()) {
                FileUtils.copyFile(source, destination);
            } else {
                Log.i("Not Exist", "File not find");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkAndRequestPermissions() {
        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionSendMessage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            }else{
                Log.i("request_permission","request");
            }
//            requestPermissions(listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    /**
     * function to Check Whether Device has Marshmallow or Above
     *
     * @return True if device has marshmallow or greater otherwise false
     */
    public boolean isMarshMallow() {
        return DeviceInfoUtil.getDeviceApiVersion(this) >= Build.VERSION_CODES.M;
    }

    private void refreshData(){
        getData();
        setData();
        if (isMarshMallow() && checkAndRequestPermissions()) {
            // carry on the normal flow, as the case of  permissions  granted.
            copyDBToAnotherLocation();
        }
    }
}
