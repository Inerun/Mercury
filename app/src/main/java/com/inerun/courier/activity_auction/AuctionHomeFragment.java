package com.inerun.courier.activity_auction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.inerun.courier.R;
import com.inerun.courier.base.AuctionBaseFragment;
import com.inerun.courier.base.ClickListener;
import com.inerun.courier.model.AuctionDetail;
import com.inerun.courier.model.AuctionInvoice;
import com.inerun.courier.model.AuctionInvoice_Table;
import com.inerun.courier.sqldb.AppDatabase;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinay on 24/05/18.
 */

public class AuctionHomeFragment extends AuctionBaseFragment implements View.OnClickListener {

    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    private RelativeLayout createInvoice_lay, invoiceList_lay;
    private LinearLayout totalCollection_lay;

    private TextView auction_detail, total_collection;
    private AuctionDetail auctionDetail;
    private List<AuctionInvoice> auctionInvoiceList;
    private FloatingActionsMenu floationbutton_menu;


    public static AuctionHomeFragment newInstance() {

        Bundle args = new Bundle();

        AuctionHomeFragment fragment = new AuctionHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.auction_home;
    }

    @Override
    protected void initView(View rootview) throws IonServiceManager.InvalidParametersException {
        setActionBarTitle(getString(R.string.default_auction_title));

        auction_detail = rootview.findViewById(R.id.auction_detail);
        total_collection = rootview.findViewById(R.id.collection_value);

        createInvoice_lay = rootview.findViewById(R.id.create_invoice_layout);
        invoiceList_lay = rootview.findViewById(R.id.all_invoice_layout);
        totalCollection_lay = rootview.findViewById(R.id.collection_layout);
        floationbutton_menu = (FloatingActionsMenu) rootview.findViewById(R.id.floationbutton_menu);
        setUpFabMenu();
        createInvoice_lay.setOnClickListener(this);
        invoiceList_lay.setOnClickListener(this);
        totalCollection_lay.setOnClickListener(this);

    }

    private void getData() {
        auctionDetail = SQLite.select().from(AuctionDetail.class).querySingle();
        auctionInvoiceList = SQLite.select().from(AuctionInvoice.class).where(AuctionInvoice_Table.sync_status.eq(AuctionInvoice.SYNC_STATUS_PENDING)).and(AuctionInvoice_Table.status.eq(1)).queryList();
//        auctionInvoiceList = SQLite.select().from(AuctionInvoice.class).where(AuctionInvoice_Table.sync_status.eq(AuctionInvoice.SYNC_STATUS_PENDING)).and(AuctionInvoice_Table.status.eq(1)).queryList();
        Log.i("Query_Home", SQLite.select().from(AuctionInvoice.class).where(AuctionInvoice_Table.sync_status.eq(AuctionInvoice.SYNC_STATUS_PENDING)).and(AuctionInvoice_Table.status.eq(1)).getQuery());
        setData();
    }


    private void setUpFabMenu() {


        FloatingActionButton actionSync = (FloatingActionButton) floationbutton_menu.findViewById(R.id.parcel_detail_sync);
        actionSync.setOnClickListener(new ClickListener(activity()) {
            @Override
            public void click(View view) throws Exception{

                floationbutton_menu.collapse();
                activity().gotoSyncActivity();

//                menuMultipleActions.setEnabled(!menuMultipleActions.isEnabled());
            }
        });
        FloatingActionButton actionSettings = (FloatingActionButton) floationbutton_menu.findViewById(R.id.parcel_detail_settings);
        actionSettings.setOnClickListener(new ClickListener(activity()) {
            @Override
            public void click(View view)throws Exception {


                floationbutton_menu.collapse();
                pushFragment(AuctionSettingsFragment.newInstance());


//                menuMultipleActions.setEnabled(!menuMultipleActions.isEnabled());
            }
        });


        //        FloatingActionButton addedOnce = new FloatingActionButton(activity());
//        addedOnce.setTitle("Added once");
//        floationbutton_menu.addButton(addedOnce);

//        FloatingActionButton addedTwice = new FloatingActionButton(activity());
//        addedTwice.setTitle("Added twice");
//        floationbutton_menu.addButton(addedTwice);
//        floationbutton_menu.removeButton(addedTwice);
//        floationbutton_menu.addButton(addedTwice);
    }


    private void setData() {

        if(auctionDetail != null) {

            auction_detail.setText(auctionDetail.getAuction_name() + " / " + auctionDetail.getStart_date());
        }else{
            auction_detail.setText(getString(R.string.auction_detail_val));
        }

//        total_collection.setText(""+getInvoicesGrandTotal(auctionInvoiceList)+ " ZMW");
        total_collection.setText(getString(R.string.auction_collection_val, ""+getInvoicesGrandTotal(auctionInvoiceList)));

        if (activity().isMarshMallow() && checkAndRequestPermissions()) {
            // carry on the normal flow, as the case of  permissions  granted.
            copyDBToAnotherLocation();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.collection_layout:
                pushFragment(AuctionCollectionFragment.newInstance());
                break;
            case R.id.create_invoice_layout:
                pushFragment(AuctionCreateInvoiceFragment.newInstance());
                break;
            case R.id.all_invoice_layout:
                pushFragment(AuctionAllInvoiceFragment.newInstance());
                break;

        }

    }

    private boolean checkAndRequestPermissions() {
        int locationPermission = ContextCompat.checkSelfPermission(activity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionSendMessage = ContextCompat.checkSelfPermission(activity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            requestPermissions(listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    private void copyDBToAnotherLocation() {

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
            if(source.exists()) {
                FileUtils.copyFile(source, destination);
            }else{
                Log.i("Not Exist", "File not find");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        setData();
        getData();
    }

   public float getInvoicesGrandTotal(List<AuctionInvoice> auctionInvoiceList){
        float total = 0;
       if(auctionInvoiceList != null && auctionInvoiceList.size() > 0) {
           for (AuctionInvoice auctionInvoice : auctionInvoiceList) {
               total += auctionInvoice.getGrand_total();
           }
       }
        return total;

    }

}
