package com.inerun.courier.activity_driver;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.inerun.courier.CourierApplication;
import com.inerun.courier.R;
import com.inerun.courier.activity_customer_care.CustomerDashboardActivity;
import com.inerun.courier.activity_warehouse.WhDashboardActivity;
import com.inerun.courier.activity_auction.AuctionHomeActivity;
import com.inerun.courier.base.BaseActivity;
import com.inerun.courier.base.CheckConnectionUtil;
import com.inerun.courier.constant.AppConstant;
import com.inerun.courier.constant.Utils;
import com.inerun.courier.data.POD;
import com.inerun.courier.sql.DIDbHelper;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by vineet on 2/1/2017.
 */

public class PodDeleteActivity extends BaseActivity {
    @Override
    public int customSetContentView() {
        return R.layout.activity_blank;
    }

    @Override
    public void customOnCreate(Bundle savedInstanceState) {
//        if(hasPermissions(this, AppConstant.requiredPermissions())) {
//            ArrayList<POD> podArrayList = DIDbHelper.getOldPODsListByDate(this);
//
//            if (podArrayList != null && podArrayList.size() > 0) {
//                for (POD pod : podArrayList) {
//                    String podName = pod.getName();
//
//                    File file = new File(Environment.getExternalStorageDirectory() + File.separator + SignActivity.FOLDERNAME + "/" + podName);
//                    Log.i("file_path", file.toString());
////                File file= new File(android.os.Environment.getExternalStorageDirectory()+ "/myfolder/myimage.jpg");
//                    if (file.exists()) {
//                        file.delete();
//                    }
//                }
//                DIDbHelper.deleteOldPodsData(this, podArrayList);
//
//            } else {
//
//               goFurther();
//            }
//        }else{
//            goFurther();
//        }

        if (CheckConnectionUtil.checkMyConnectivity(PodDeleteActivity.this)) {
            AsyncTaskRunner runner = new AsyncTaskRunner();
            runner.execute();
        } else {
            goFurther();
        }
//        goFurther();
    }

    private void goFurther() {
        if (Utils.isUserLoggedIn(this)) {
            if (CourierApplication.getUser().isDeliveryUser()) {
                gotoHomeActivity();
            } else if (CourierApplication.getUser().isWarehouseUser()) {
                gotoWarehouseActivity();

            } else if (CourierApplication.getUser().isCustomerCareUser()) {
                gotoCustomreCareActivity();
 } else if (CourierApplication.getUser().isCashierUser()) {
                gotoAuctionActivity();
            }
        } else {
            gotoLoginActivity();

        }
        finish();
    }

    private void gotoLoginActivity() {
        Intent intent = new Intent(this,
                LoginActivity.class);
        startActivity(intent);


    }

    private void gotoHomeActivity() {

        Intent intent = new Intent(this,
                DeliveryDashBoardActivity.class);
        startActivity(intent);

    }

    private void gotoWarehouseActivity() {

        Intent intent = new Intent(this,
                WhDashboardActivity.class);
        startActivity(intent);

    }

    private void gotoCustomreCareActivity() {

        Intent intent = new Intent(this,
                CustomerDashboardActivity.class);
        startActivity(intent);

    }

    private void gotoAuctionActivity() {

        Intent intent = new Intent(this,
                AuctionHomeActivity.class);
        startActivity(intent);

    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;


        @Override
        protected String doInBackground(String... params) {


            try {
//                Thread.sleep(5000);
                if (hasPermissions(PodDeleteActivity.this, AppConstant.requiredPermissions())) {
                    ArrayList<POD> podArrayList = DIDbHelper.getOldPODsListByDate(PodDeleteActivity.this);

                    if (podArrayList != null && podArrayList.size() > 0) {
                        for (POD pod : podArrayList) {
                            String podName = pod.getName();

                            File file = new File(Environment.getExternalStorageDirectory() + File.separator + SignActivity.FOLDERNAME + "/" + podName);
                            Log.i("file_path", file.toString());
                            if (file.exists()) {
                                file.delete();
                            }
                        }
                        DIDbHelper.deleteOldPodsData(PodDeleteActivity.this, podArrayList);
//                        Toast.makeText(PodDeleteActivity.this, "POd table date update Successfully",Toast.LENGTH_LONG).show();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            hideProgress();
            goFurther();
//            Toast.makeText(PodDeleteActivity.this, "POd table date update Successfully",Toast.LENGTH_LONG).show();
        }


        @Override
        protected void onPreExecute() {
            showProgress();
        }


    }

}
