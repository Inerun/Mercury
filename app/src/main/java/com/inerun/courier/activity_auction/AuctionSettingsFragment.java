package com.inerun.courier.activity_auction;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.inerun.courier.Exception.PrinterExceptions;
import com.inerun.courier.R;
import com.inerun.courier.base.AuctionBaseFragment;
import com.inerun.courier.base.SweetAlertUtil;
import com.inerun.courier.printer.AppConstant;
import com.inerun.courier.printer.DiscoveryActivity;
import com.inerun.courier.printer.EPOSHelper;
import com.inerun.courier.printer.PrinterManager;
import com.inerun.courier.printer.SpnModelsItem;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by vinay on 25/05/18.
 */

public class AuctionSettingsFragment extends AuctionBaseFragment implements View.OnClickListener {

    private static final int DISCOVERY_REQUEST = 101;
    private static final int MY_PERMISSIONS_REQUEST_SYS_ALERT = 102;
    private EditText printeraddress;
    private Button printerscan;
    private Button printertest;
    PrinterManager printerManager;
    private MaterialBetterSpinner mSpnSeries, mSpnLang, mSpnPin, mSpnPulse;
    private Button savesettings;

    public static AuctionSettingsFragment newInstance() {

        Bundle args = new Bundle();

        AuctionSettingsFragment fragment = new AuctionSettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        setShowBackArrow(true);
        return R.layout.auction_settings;
    }

    @Override
    protected void initView(View rootview) throws IonServiceManager.InvalidParametersException {
        setActionBarTitle(getString(R.string.action_settings));

        printeraddress = rootview.findViewById(R.id.settings_url_edt);
        printerscan = rootview.findViewById(R.id.settings_printer_scan);
        printertest = rootview.findViewById(R.id.settings_printer_test);
        savesettings = rootview.findViewById(R.id.save_settings);
        mSpnSeries = (MaterialBetterSpinner) rootview.findViewById(R.id.spnModel);
        mSpnLang = (MaterialBetterSpinner) rootview.findViewById(R.id.spnLang);
        mSpnPin = (MaterialBetterSpinner) rootview.findViewById(R.id.spnPin);
        mSpnPulse = (MaterialBetterSpinner) rootview.findViewById(R.id.spnPulse);
        printerManager = new PrinterManager(activity());
        printerscan.setOnClickListener(this);
        savesettings.setOnClickListener(this);
        printertest.setOnClickListener(this);

        setHasOptionsMenu(true);
        setData();
        setSavedSettings();
    }

    private void setData() {
        ArrayAdapter<SpnModelsItem> seriesAdapter = new ArrayAdapter<SpnModelsItem>(activity(), R.layout.spinner_item);
        seriesAdapter.setDropDownViewResource(R.layout.spinner_layout);
        seriesAdapter.addAll(EPOSHelper.getPrinterSeriesList(activity()));
        mSpnSeries.setAdapter(seriesAdapter);


        ArrayAdapter<SpnModelsItem> langAdapter = new ArrayAdapter<SpnModelsItem>(activity(), R.layout.spinner_item);
        langAdapter.setDropDownViewResource(R.layout.spinner_layout);
        langAdapter.addAll(EPOSHelper.getPrinterLangList(activity()));
        mSpnLang.setAdapter(langAdapter);



        //new code

        ArrayAdapter<SpnModelsItem> pinAdapter = new ArrayAdapter<SpnModelsItem>(activity(), R.layout.spinner_item);
        pinAdapter.setDropDownViewResource(R.layout.spinner_layout);


        pinAdapter.addAll(EPOSHelper.getPrinterPinList(activity()));

        mSpnPin.setAdapter(pinAdapter);



        ArrayAdapter<SpnModelsItem> pulseAdapter = new ArrayAdapter<SpnModelsItem>(activity(), R.layout.spinner_item);
        pulseAdapter.setDropDownViewResource(R.layout.spinner_layout);
        pulseAdapter.addAll(EPOSHelper.getPrinterPulseList(activity()));


        mSpnPulse.setAdapter(pulseAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings_printer_scan:


//                checkAppPermission();
                startActivityForResult(new Intent(activity(), DiscoveryActivity.class), DISCOVERY_REQUEST);
                break;
            case R.id.settings_printer_test:

                String data = "{\"date\":\"01.04.2016\",\"order_num\":\"1330\",\"date_time\":\"11:35\",\"cashier_name\":\"admin\",\"customer_name\":\"Amit Rathi\",\"customer_email\":\"rathi_kota@yahoo.co.in\",\"customer_phone\":\"9988998899\",\"register_name\":\"POS\",\"vat1\":\"20%\",\"vat1_amount\":\"19,50\",\"subtotal\":\"100,00\",\"subtotal_net\":\"80,50\",\"total_incl_vat\":\"900\",\"cash_given\":\"1,100\",\"cash_received\":\"100\",\"payment_method\":\"Cheque\",\"sales\":\"100,00\",\"change\":\"0,00\",\"num_items\":2,\"barcode\":\"1330\",\"items\":[{\"qty\":\"123456789011\",\"desc\":\"Mobile Phone Etc...\",\"price\":\"70,00\",\"amount\":\"99999\"},{\"qty\":\"120987654321\",\"desc\":\"DSLR Camera\",\"price\":\"30,00\",\"amount\":\"99900\"}],\"outlet\":\"Friedrichstadt\",\"email\":\"enquiries@tigmoo.com\",\"tel\":\"09712 69390 \",\"fax\":\"\",\"website\":\"www.tigmooshopnship.com\",\"address\":\" Plot 6392 Dundudza Chididza Road \nLongacres,\",\"city\":\"Lusaka\"}";

//old
//                printerService.addPrintJob(AppConstant.Keys.TYPE_PRINT_RECEIPT_ONLY, data);
                try {
                    printerManager.addRequestToPrinter(AppConstant.Keys.TYPE_PRINT_RECEIPT_ONLY, data);
                } catch (PrinterExceptions printerExceptions) {
                    printerExceptions.printStackTrace();
                    SweetAlertUtil.showAlertDialog(activity(), printerExceptions.toString());


                }


                break;
            case R.id.save_settings:


                SweetAlertUtil.showAlertDialogwithListener(activity(), R.string.title_settings, R.string.title_dialog_save_msg, R.string.yes, R.string.no, positivedialog_listener, canceldialog_listener).show();

                break;
        }
    }


    SweetAlertDialog.OnSweetClickListener positivedialog_listener = new SweetAlertDialog.OnSweetClickListener() {
        @Override
        public void onClick(SweetAlertDialog sweetAlertDialog) {

            sweetAlertDialog.dismiss();
            String target = "" + printeraddress.getText();




            int printerseries = getConstantFromSpinnerListByName(EPOSHelper.getPrinterSeriesList(activity()), "" + mSpnSeries.getText());
            int printerlang = getConstantFromSpinnerListByName(EPOSHelper.getPrinterLangList(activity()), "" + mSpnLang.getText());
            int printerpulse = getConstantFromSpinnerListByName(EPOSHelper.getPrinterPulseList(activity()), "" + mSpnPulse.getText());
            int printerpin = getConstantFromSpinnerListByName(EPOSHelper.getPrinterPinList(activity()), "" + mSpnPin.getText());

            if (target!=null&&target.length()>0&&printerseries!=-1&&printerlang!=-1&&printerpulse!=-1&&printerpin!=-1) {
                try {
                    printerManager.restartPrinterService();
//                            readerManager.restartReaderService();
                } catch (Exception printerExceptions) {
                    printerExceptions.printStackTrace();
                }

                EPOSHelper.savePrintSettings(activity(), target, printerseries, printerlang, printerpulse, printerpin);

            }else
            {

                SweetAlertUtil.showErrorMessage(activity(),getString(R.string.dialog_printer_settings_error_msg));
            }

//                        SettingsActivity.this.finish();


        }
    };

    private int getConstantFromSpinnerListByName(ArrayList<SpnModelsItem> list, String name) {

        if(name!=null&&name.length()>0) {
            int index = list.indexOf(new SpnModelsItem(name, -2) {
                @Override
                public boolean equals(Object obj) {
                    return getmModelName().equals(((SpnModelsItem) obj).getmModelName());
                }
            });
            return list.get(index).getModelConstant();
        }else
        {
            return -1;
        }

    }

    SweetAlertDialog.OnSweetClickListener canceldialog_listener = new SweetAlertDialog.OnSweetClickListener() {
        @Override
        public void onClick(SweetAlertDialog sweetAlertDialog) {
            sweetAlertDialog.dismiss();

        }
    };

    private void checkAppPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED || activity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED || activity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?


                // No explanation needed, we can request the permission.

                requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_SYS_ALERT);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.

            } else {
                startActivityForResult(new Intent(activity(), DiscoveryActivity.class), DISCOVERY_REQUEST);
            }
        } else {
            startActivityForResult(new Intent(activity(), DiscoveryActivity.class), DISCOVERY_REQUEST);

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SYS_ALERT:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivityForResult(new Intent(activity(), DiscoveryActivity.class), DISCOVERY_REQUEST);
                    // permission was granted, yay! Do the
                    //related task you need to do.

                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {

//

                    SweetAlertUtil.showDialogwithNeutralButton(activity(), R.string.title_settings, R.string.dialog_permission_denied_error_msg, R.string.ok, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    }, 11).show();

                    // permission denied, boo! Disable the
                    // force it.
                }


                break;


            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    @Override
    public void onResume() {
        super.onResume();
//        registerReceiver(uiUpdated, new IntentFilter("LOG_UPDATED"));

//old
//        startPrinterService();
        try {
            if (printerManager.isACTIVE())
                printerManager.startPrinter();
        } catch (PrinterExceptions printerExceptions) {
            printerExceptions.printStackTrace();

        }
//        readerManager.startReader();
//        registerReceiver(broadcastReceiver, new IntentFilter(AppConstant.ReaderCommands.MA_RECEIVER));
    }


    @Override
    public void onPause() {
        super.onPause();


        try {

//            mReaderBoundService.addRequest(SharedPref.IDS.STOP_POLLING_REQUEST,null);
//            readerManager.stopReader();
//            unregisterReceiver(broadcastReceiver);
//            unregisterReceiver(uiUpdated);

            printerManager.stopPrinter();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == DISCOVERY_REQUEST && data != null && resultCode == Activity.RESULT_OK)) {
            if (data.hasExtra(getString(R.string.title_target))) {
                String target = data.getStringExtra(getString(R.string.title_target));
                if (target != null) {

                    printeraddress.setText(target);
                }
            }
        }

    }


    private void setSavedSettings() {
        if (EPOSHelper.isSettingsSaved(activity())) {
            int pos = 0;
            pos = EPOSHelper.getPrinterLangList(activity()).indexOf(new SpnModelsItem("", AppConstant.SharedPref.getPrinterLang(activity())));

            if (pos != -1) {
                mSpnLang.setText(EPOSHelper.getPrinterLangList(activity()).get(pos).getmModelName());
                pos = 0;
            }

            pos = EPOSHelper.getPrinterPinList(activity()).indexOf((new SpnModelsItem("", AppConstant.SharedPref.getPrinterPin(activity()))));

            if (pos != -1) {
                mSpnPin.setText(EPOSHelper.getPrinterPinList(activity()).get(pos).getmModelName());
                pos = 0;
            }

            pos = EPOSHelper.getPrinterPulseList(activity()).indexOf((new SpnModelsItem("", AppConstant.SharedPref.getPrinterPulse(activity()))));

            if (pos != -1) {
                mSpnPulse.setText(EPOSHelper.getPrinterPulseList(activity()).get(pos).getmModelName());
                pos = 0;
            }

            pos = EPOSHelper.getPrinterSeriesList(activity()).indexOf((new SpnModelsItem("", AppConstant.SharedPref.getPrinterSeries(activity()))));

            if (pos != -1) {
                mSpnSeries.setText(EPOSHelper.getPrinterSeriesList (activity()).get(pos).getmModelName());
                pos = 0;
            }

            String target = "" + AppConstant.SharedPref.getPrinterTarget(activity());


            if (target.length() != 0) {
                printeraddress.setText(target);
            }


        }
    }

}
