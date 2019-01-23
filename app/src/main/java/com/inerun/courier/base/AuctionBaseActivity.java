package com.inerun.courier.base;

import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.inerun.courier.CourierApplication;
import com.inerun.courier.R;
import com.inerun.courier.activity_auction.AuctionHomeFragment;
import com.inerun.courier.activity_auction.AuctionSettingsFragment;
import com.inerun.courier.activity_auction.IonServiceManager;
import com.inerun.courier.activity_auction.SyncActivity;
import com.inerun.courier.activity_driver.LoginActivity;
import com.inerun.courier.constant.Utils;
import com.inerun.courier.sql.DIDbHelper;
import com.ncapdevi.fragnav.FragNavController;
import com.vishalsojitra.easylocation.EasyLocationAppCompatActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by vinay on 09/09/17.
 */

abstract public class AuctionBaseActivity extends EasyLocationAppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, FragNavController.RootFragmentListener, FragNavController.TransactionListener {

    private static final int PERFORM_SYNC = 101;
    public int base_activity_layout = R.layout.activity_main;
    private Toolbar toolbar;
    public DrawerLayout drawer;

    private FrameLayout fabfilterlayout;
    private FragNavController.Builder builder;
    private FragNavController fragNavController;
    private TextView navDeviceNo, navDeviceName, navLastSync;
    private ActionBarDrawerToggle toggle;
    private DrawerArrowDrawable drawerArrow;
    private RecyclerView hospitalRecyclerView;
    private boolean isShowAction = true;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layout = initLayout();
        setContentView(layout);


        initBase();
        initView();

        builder = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), initFragmentContainer());

//        FragNavTransactionOptions.Builder transbuilder = FragNavTransactionOptions.newBuilder();
//        transbuilder.transition(FragmentTransaction.TRANSIT_EXIT_MASK);
//        builder.defaultTransactionOptions(transbuilder.build());
        builder.rootFragment(initRootFragment());
        builder.rootFragmentListener(this, 1);
        builder.transactionListener(this);
        fragNavController = builder.build();


//        addHomeFragment();

    }


    public Fragment initRootFragment() {
//        Hospital_Station station = getApp().appData.getStation();
//        if (station != null && station.isBuffetOrder()) {
//            return BuffetHomeFragment.newInstance();
//
//        } else {
        return AuctionHomeFragment.newInstance();
//        }
    }

    public int initFragmentContainer() {
        return R.id.fragmentContainer;
    }


    public void initBase() {


        setUpToolbar();

//        setUpFabMenu();

//        setUpDrawer();


//        setUpFloatingActionButton();


//        setUpNavigationListener();


    }



    public void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setToolBarTitle(getHeaderTitle());

    }

    public void setToolBarTitle(String headerTitle) {
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(headerTitle);
    }

    private void setUpDrawer() {

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        drawerArrow = new DrawerArrowDrawable(this);
        drawerArrow.setColor(ContextCompat.getColor(this, android.R.color.white));

        toolbar.setNavigationIcon(drawerArrow);
        toggle.setToolbarNavigationClickListener(new  ClickListener(this) {
            @Override
            public void click(View view)throws Exception {
                onBackPressed();
            }
        });
        if(isShowAction){
            toolbar.setVisibility(View.VISIBLE);
        }else {
            toolbar.setVisibility(View.GONE);
        }


    }


    private void setUpNavigationListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        filterNavView = (NavigationView) findViewById(R.id.filternav_view);
        navDeviceNo = navigationView.findViewById(R.id.navigation_device_number);
        navDeviceName = navigationView.findViewById(R.id.navigation_device_name);
        navLastSync = navigationView.findViewById(R.id.navigation_last_sync);
//        navigationView.findViewById(R.id.navigation_pateint_order_layout).setOnClickListener(this);
//        navigationView.findViewById(R.id.navigation_location_layout).setOnClickListener(this);
        navigationView.findViewById(R.id.navigation_start_sync_button).setOnClickListener(this);
        navigationView.findViewById(R.id.navigation_settings_layout).setOnClickListener(this);

        navigationView.setNavigationItemSelectedListener(this);


//        getApp().appData.setLastsynctimestamp(getPrefs().lastSync());
//        navDeviceNo.setText(getString(R.string.navigation_header_device, getApp().appData.getDeviceno())));
//        Helper.setSimpleText(navDeviceName, getPrefs().deviceName());
//        navLastSync.setText(getString(R.string.navigation_sync_time,getApp().appData.getLastsynctimestamp() ));
//        Helper.setSimpleText(navLastSync, getString(R.string.navigation_sync_time, Helper.getDateSync(this, getApp().appData.getLastsynctimestamp())));
//        hospitalRecyclerView = navigationView.findViewById(R.id.hospital_recycler);
//        setUpHospitalList();
    }


//    private void setUpHospitalList() {
////        ArrayList<String> itemList = new ArrayList<>();
////        itemList.add("Hospital");
//        List<Hospital> hospitalList = SQLite.select().from(Hospital.class).queryList();
//
////        hospitalRecyclerView = findViewById(R.id.hospital_recycler);
//        navigationHospitalAdpter = new NavigationHospitalListAdpter(this, hospitalList);
//        hospitalRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        hospitalRecyclerView.setItemAnimator(new DefaultItemAnimator());
////        patientdetailrecyclerview.addItemDecoration(new SimpleDividerItemDecoration(getActivity(), mDivider));
////        hospitalRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));  // For divider
//        hospitalRecyclerView.setAdapter(navigationHospitalAdpter);
//
//
//    }
//
//    public void onNavigationItemClick(NavigationHospitalListAdpter navigationHospitalAdpter, int position) {
//
//
//        Log.i("BaseActivity", "onNavigationItemClick");
//
////                startActivity(new Intent(BaseActivity.this, StationsActivity.class));
////                pushFragment(StationsFragment.newInstance(hospital, true));
//    }
//
//
////    private void addHomeFragment() {
////        Fragment fragment = (Fragment) HomeFragment.newInstance();
////
////
////        FragmentManager fragmentManager = getSupportFragmentManager();
////        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
////    }

    public void pushFragment(Fragment fragment) {
        fragNavController.pushFragment(fragment);
    }

    public void popFragment() {
        fragNavController.popFragment();
//


    }
//
//    public void popFragment(Pair pair) {
//
//
//        FragNavTransactionOptions transaction = FragNavTransactionOptions.newBuilder().addSharedElement(pair).build();
//        fragNavController.popFragment(transaction);
//
//    }

    public void popMultipleFragments(int num) {
        fragNavController.popFragments(num);
    }

    public void replaceCurrentFragment(Fragment fragment) {
        fragNavController.replaceFragment(fragment);

    }

    public FragNavController getFragController() {
        return fragNavController;

    }

    public void clearFragmentStack() {
        fragNavController.clearStack();
    }

    private void gotoHomeFragment() {
        fragNavController.clearStack();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragNavController != null) {
            fragNavController.onSaveInstanceState(outState);
        }
    }

    abstract public String getHeaderTitle();

    /**
     * function to get layout from activities, layout cannot be zero or null
     *
     * @return layout resource id
     */
    abstract public int initLayout();

    /**
     * function to get layout from activities, layout cannot be zero or null
     *
     * @return layout resource id
     */
    abstract public void initView();

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {
        super.onResume();

        getApp().currentActivity = this;


    }

    @Override
    protected void onPause() {
        super.onPause();
        getApp().currentActivity = null;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * function to get Application Instance in Activities
     *
     * @return DropInsta Instance
     */

    public CourierApplication getApp() {
        return (CourierApplication) getApplication();
    }

    /**
     * function to get common container for activities
     *
     * @return R.layout.activity_main
     */
    public int getBase_activity_layout() {
        return base_activity_layout;
    }


    @Override
    public void onBackPressed() {

        handleBackEvent();
    }

    public void handleBackEvent() {
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer != null && drawer.isDrawerOpen(GravityCompat.END)) { /*Close Diet Type filter drawer*/
            drawer.closeDrawer(GravityCompat.END);
        } else if (!fragNavController.isRootFragment()) {


            popFragment();


        } else {
////            super.onBackPressed();
//            if (doubleBackToExitPressedOnce) {
////                fm.popBackStack();
//                Log.i("CheckoutActivity", "nothing on backstack, calling super");
////                super.onBackPressed();
//                finish();
////                return;
//            }
//
//            this.doubleBackToExitPressedOnce = true;
////            Snackbar.make(cordinatorLayout, R.string.exit_msg, Snackbar.LENGTH_LONG).show();
////            showSnackbar(R.string.exit_msg);
////            SweetAlertUtil.showSweetMessageLongToast(this, getString(R.string.exit_msg));
//
//            new Handler().postDelayed(new Runnable() {
//
//                @Override
//                public void run() {
//                    doubleBackToExitPressedOnce = false;
//                }
//            }, 2000);




            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        int menuid = getMenuLayout();
        if (menuid != -1) {
            getMenuInflater().inflate(menuid, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_warehouse, menu);
        }
        return true;
    }

    protected abstract int getMenuLayout();

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify showfab1 parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        switch (id) {
//            case R.id.action_search:
//
//                onSearchClick();
//                return true;
//            case android.R.id.home:
//
//                handleBackEvent();
//                return true;
//
//
//            case R.id.action_openFilter:
//                drawer.openDrawer(GravityCompat.END); /*Opens the Right Drawer*/
//                return true;
//
//        }
//
//
//        return super.onOptionsItemSelected(item);
//    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        }
//        else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        }
        else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null)
            drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean isValidString(String string) {
        return string != null && string.length() > 0;

    }

    @Override
    public void onClick(View view) {

        drawer.closeDrawer(GravityCompat.START);
//        clearFragmentStack();
        switch (view.getId()) {
//            case R.id.navigation_location_layout:
//
//                pushFragment(LocationsFragment.newInstance());
//                break;
//
//            case R.id.navigation_pateint_order_layout:
//
//                pushFragment(PateintOrdersFragment.newInstance());
//                break;
//
            case R.id.navigation_start_sync_button:

                gotoSyncActivity();
                break;

            case R.id.navigation_settings_layout:

                pushFragment(AuctionSettingsFragment.newInstance());


                break;
        }

    }

    /**
     * open Sync Activity for Synchronisation
     * when synchronisation will happen. check for the result in onActivityResult with requestCode PERFORM_SYNC
     */
    public void gotoSyncActivity() {
        Intent intent = new Intent(this, SyncActivity.class);

        startActivityForResult(intent, PERFORM_SYNC);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PERFORM_SYNC:

                if (resultCode == RESULT_OK) {
                    onSyncCompleted();

                } else {
                    onSyncFailed();
                }

                break;
        }


    }

    public void onSyncCompleted() {
        Log.i("BaseActivity", "Sync Completed");
        Toast.makeText(this, "Sync Completed", Toast.LENGTH_LONG).show();


    }

    private void onSyncFailed() {
        Log.i("BaseActivity", "Sync Failed");
        Toast.makeText(this, "Sync Failed", Toast.LENGTH_LONG).show();
    }

    public IonServiceManager serviceManager() {

        return getApp().ionserviceManager;
    }

//    public Pre getPrefs() {
//
//        return getApp().fcsPref;
//    }

    @Override
    public Fragment getRootFragment(int i) {

        Log.i("FragNavController", "getRootFragment Called with index" + i);


        return AuctionHomeFragment.newInstance();

    }

    @Override
    public void onTabTransaction(Fragment fragment, int i) {

    }

    @Override
    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {

        if (toggle != null && drawer != null) {

//        arrowAnimation(!fragNavController.isRootFragment());
            if (fragNavController.isRootFragment()) {

//            getSupportActionBar().setDisplayShowHomeEnabled(true);

                toggle.setDrawerIndicatorEnabled(true);

                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                toggle.syncState();

            } else {


                toggle.setDrawerIndicatorEnabled(false);

                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//            getSupportActionBar().setDisplayShowHomeEnabled(false);
//            toggle.syncState();


            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
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
        }
        return true;
    }

    SweetAlertDialog.OnSweetClickListener listener = new SweetAlertDialog.OnSweetClickListener() {
        @Override
        public void onClick(SweetAlertDialog sweetAlertDialog) {
            Utils.deletePrefs(AuctionBaseActivity.this);
            DIDbHelper.deleteTables(AuctionBaseActivity.this);

            goToActivity(LoginActivity.class);
            finish();
        }


    };

    public void goToActivity(Class classobj) {
        startActivity(new Intent(AuctionBaseActivity.this, classobj));
    }

    public boolean isShowAction() {
        return isShowAction;
    }

    public void setShowAction(boolean showAction) {
        isShowAction = showAction;
    }


    /**
     * function to Check Whether Device has Marshmallow or Above
     *
     * @return True if device has marshmallow or greater otherwise false
     */
    public boolean isMarshMallow() {
        return DeviceInfoUtil.getDeviceApiVersion(this) >= Build.VERSION_CODES.M;
    }


    public void requestLocationUpdate() {
        if (getApp() != null) {
            requestSingleLocationFix(getApp().easyLocationRequest);
        }

    }

    @Override
    public void onLocationPermissionGranted() {
        Log.i("locationPermsnGranted","abc");
        updateLocationToServer();
    }

    @Override
    public void onLocationPermissionDenied() {
        Log.i("locationPermsnDenied","abc");
        updateLocationToServer();
    }

    @Override
    public void onLocationReceived(Location location) {
        Log.i("locationReceived","abc");
        updateLocationToServer();
    }

    @Override
    public void onLocationProviderEnabled() {
        Log.i("locationProvidrEnabled","abc");
        updateLocationToServer();
    }

    @Override
    public void onLocationProviderDisabled() {
        Log.i("locationProvidrDsabled","abc");
        updateLocationToServer();
    }



    public void updateLocationToServer()
    {


    }

}
