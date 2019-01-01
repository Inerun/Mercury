package com.inerun.courier.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;
import com.inerun.courier.CourierApplication;
import com.inerun.courier.base.DeviceInfoUtil;
import com.inerun.courier.constant.AppConstant;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.data.POD;
import com.inerun.courier.data.ParcelListingData;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import cz.msebera.android.httpclient.Header;

public class whParcelUploadService extends Service {

    private static boolean isSuccess = false;
    private static final String TAG = "ImageUploadingServices";


    private Queue<MyImageUplodingThread> myQueue;

    private MyImageUplodingThread thread;
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = whParcelUploadService.this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        ArrayList<POD> model = new ArrayList<>();
        if (intent == null) {
            Log.i(TAG, "Getting Intent null");
            return START_NOT_STICKY;
        }


        ArrayList<POD> arraylist = new ArrayList<>();
        arraylist.add((POD) intent.getSerializableExtra(UrlConstants.KEY_POD));
        ArrayList<ParcelListingData.ParcelData> selectedparcelDataArrayList = (ArrayList<ParcelListingData.ParcelData>) intent.getSerializableExtra(UrlConstants.KEY_DATA);
        String invoice_no = intent.getStringExtra(UrlConstants.KEY_INVOICE_NUMBER);
        model = arraylist;


        Log.i(TAG, "POD_queue_size ::" + model.size());
        if (model != null && model.size() > 0) {
            if (myQueue == null) {
                myQueue = new LinkedList<MyImageUplodingThread>();
            }
            for (POD pod : model) {
                MyImageUplodingThread tempThread = new MyImageUplodingThread(pod, selectedparcelDataArrayList, invoice_no);
                myQueue.add(tempThread);

            }
            if (thread == null) {
                thread = myQueue.poll();
                thread.start();
            }

        }


        return START_STICKY;

    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void manageQueue() {
        thread = myQueue.poll();
        if (thread != null) {
            thread.start();
        } else {
            Log.i(TAG, "Queue size is empty ::" + myQueue.size());

            sendPODUpdationBroadcast();

            stopSelf();
        }
    }

    private void sendPODUpdationBroadcast() {
        Intent intent = new Intent();
        intent.setAction(AppConstant.ACTION_POD);
        if (CourierApplication.getUser().isWarehouseUser()) {
//            intent.putExtra(UrlConstants.KEY_TYPE, DIReceiver.TYPE_WAREHOUSE_PARCEL_DELIVERED);
            //Keeping Same Flow As POS UPDATED
            intent.putExtra(UrlConstants.KEY_TYPE, DIReceiver.TYPE_WH_POD_UPDATED);
        }else if (CourierApplication.getUser().isCustomerCareUser()) {
//            intent.putExtra(UrlConstants.KEY_TYPE, DIReceiver.TYPE_WAREHOUSE_PARCEL_DELIVERED);
            //Keeping Same Flow As POS UPDATED
            intent.putExtra(UrlConstants.KEY_TYPE, DIReceiver.TYPE_CUSTOMER_PARCEL_DELIVERED);
            intent.putExtra(UrlConstants.KEY_DELIVERY_STATUS_FLAG, isSuccess);
        } else {
            intent.putExtra(UrlConstants.KEY_TYPE, DIReceiver.TYPE_POD_UPDATED);
        }

        sendBroadcast(intent);
    }

    class MyImageUplodingThread extends Thread {
        private POD poddata;
        ArrayList<ParcelListingData.ParcelData> selectedparcelDataArrayList;
        String invoice_no;


        public MyImageUplodingThread(POD poddata, ArrayList<ParcelListingData.ParcelData> selectedparcelDataArrayList, String invoice_no) {

            this.poddata = poddata;
            this.selectedparcelDataArrayList = selectedparcelDataArrayList;
            this.invoice_no = invoice_no;


        }

        @Override
        public void run() {

            try {

//                uploadFile(context, poddata, UrlConstants.URL_POD_UPLOAD);
                String url = UrlConstants.URL_WH_POD_UPLOAD;
//                uploadFile(context, poddata, UrlConstants.URL_POD_UPLOAD);
                RequestParams params = getParams(poddata);
                Log.i("params", params.toString());


                uploadFile(context, poddata, url,params);

//                manageQueue();
            } catch (Exception e) {
                e.printStackTrace();
//                BTChatHelper.getInstance(context).updateMessageStatus(model.getId(), MessageModel.STATUS_FAILED, model.getId());
//                DIDbHelper.updatePODStatus(context, poddata.getId(), null, PODDao.POD_STATUS_FAILED);
                manageQueue();
            }

//            BTNotificationController.getInstance(context, null).sendDbChangedBroadCast();
//

        }

        private RequestParams getParams(POD poddata) {
            Gson gson = new Gson();
            String data = gson.toJson(selectedparcelDataArrayList);
            RequestParams params = new RequestParams();
            params.put(UrlConstants.KEY_INVOICE_NUMBER, "" + invoice_no);
            params.put(UrlConstants.KEY_USERTYPE, "" + CourierApplication.getUser().getUsertype());
            params.put(UrlConstants.KEY_ANDROID_ID, DeviceInfoUtil.getAndroidID(context));
            params.put(UrlConstants.KEY_UPDATE_DATA, data);
            params.put(UrlConstants.KEY_RECEIVER_NAME, poddata.getReceiverName());
            if(poddata.getNationalid()!=null && poddata.getNationalid().length()>0) {
                params.put(UrlConstants.KEY_NATIONAL_ID, poddata.getNationalid());
            }else{
                params.put(UrlConstants.KEY_NATIONAL_ID, "");
            }
            String android_version= DeviceInfoUtil.getDeviceAndroidVersionName(context);
            String brand=DeviceInfoUtil.getBrandName(context);
            String model=DeviceInfoUtil.getModelName(context);
            String android_id=DeviceInfoUtil.getAndroidID(context);
            String version_code=""+DeviceInfoUtil.getSelfVersionCode(context);


            params.put(UrlConstants.KEY_ANDROID_VERSION, android_version);
            params.put(UrlConstants.KEY_BRAND, brand);
            params.put(UrlConstants.KEY_MODEL, model);
            params.put(UrlConstants.KEY_ANDROID_ID, android_id);
            params.put(UrlConstants.KEY_VERSION_CODE, version_code);

            return params;
        }


    }


    /**
     * Method to upload file
     *
     * @param context
     * @param poddata
     * @param url
     * @param requestParams
     * @return
     * @throws IOException
     */
    private static void uploadFile(final Context context, final POD poddata, String url, RequestParams requestParams) throws IOException {
        String filepath = AppConstant.POD_FOLDER_PATH + "/" + poddata.getName();
        String filename = filepath.substring(filepath.lastIndexOf("/") + 1);
        Log.i("filename", filename);
        Log.i("filepath", filepath);
//        String url=;
        String embeddedurl = url;

//        String response = HttpService.uploadFilewithDataProgress(context, filepath, filename, embeddedurl, model.getId());
        int progress;
        File myFile = new File(filepath);

        try {
            Log.i("Can Read", "" + myFile.canRead());
            requestParams.put("image", myFile);

            Log.i("embedded_url", embeddedurl);
            Log.i("Params: ", "" + requestParams);
            AsyncHttpClient client = new SyncHttpClient();

            client.post(context, embeddedurl, requestParams, new AsyncHttpResponseHandler() {

                int progress = 0;

                @Override
                public void onProgress(long bytesWritten, long totalSize) {
                    // TODO Auto-generated method stub

                    super.onProgress(bytesWritten, totalSize);
                    int oldprogress = (int) ((bytesWritten * 100) / totalSize);
                    if ((progress + 4) < oldprogress) {
                        progress = oldprogress;
                        Log.i("bytesWritten", "" + bytesWritten);
                        Log.d("SystemTime", new SimpleDateFormat("hh:mm:ss").format(new Date(System.currentTimeMillis())));
//                        Log.i("progress", ""+progress);
//                        MainActivity.this.progress.setProgress(progress);
                    }
                }

                @Override
                public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                      Throwable arg3) {
                    Log.i("Failure", new String(arg2));
//                    DIDbHelper.updatePODStatus(context, poddata.getId(), null, PODDao.POD_STATUS_FAILED);
                    isSuccess = false;
                    ((whParcelUploadService) context).manageQueue();

                }

                @Override
                public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {

                    Log.d("SystemTime_onSuccess", new SimpleDateFormat("hh:mm:ss").format(new Date(System.currentTimeMillis())));
                    Log.d("onSuccessResponse", new String(arg2));
//                    DIDbHelper.updatePODStatus(context, poddata.getId(), new String(arg2), PODDao.POD_STATUS_UPLOADED);
                    isSuccess = true;
                    ((whParcelUploadService) context).manageQueue();
//                    MainActivity.this.progress.setProgress(0);
//                    try {
//                        BTParser.getParser(context, null).parseUploadResponse(model.getId(), new String(arg2));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        BTChatHelper.getInstance(context).updateMessageStatus(model.getId(), MessageModel.STATUS_FAILED, model.getId());
//                    }

                }

            });
        } catch (Exception e) {
            e.printStackTrace();
//            DIDbHelper.updatePODStatus(context, poddata.getId(), null, PODDao.POD_STATUS_FAILED);
            isSuccess = false;
            ((whParcelUploadService) context).manageQueue();
//            BTChatHelper.getInstance(context).updateMessageStatus(model.getId(), MessageModel.STATUS_FAILED, model.getId());
        }


//            ServiceClient client = ServiceClient.getInstance(context, null, new ServiceClient.ServiceCallBack() {
//            @Override
//            public void performThisWhenServiceEnds(String message, Object response) {
//                try {
////                    if (type == MessageModel.MESSAGE_TYPE_VIDEO) {
//                    Log.i("Response", (String) response);
//                    BTParser.getParser(context, null).parseUploadResponse(model.getId(), (String) response);
////                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        })
//        ;
//        File sourceFile = new File(filepath);
//        if (!sourceFile.isFile()) {
//            Log.e("File", "Source File not exist :" + filepath + "");
//            throw new IOException();
//
//        } else {
//
//            RequestParams params = new RequestParams();
//            params.put("image", new File(filepath));
//            client.performSynchPostRequest(embeddedurl, params);
//        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Upload service", "Destroy Method Called");

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.i("Upload service", "Task REmoved Method Called");
//        BTChatHelper.getInstance(context).markUploadingFailed();
    }

}
