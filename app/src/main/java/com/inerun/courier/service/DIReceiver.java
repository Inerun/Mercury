package com.inerun.courier.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.inerun.courier.constant.UrlConstants;

/**
 * Created by vineet on 12/12/2016.
 */

public abstract class DIReceiver extends BroadcastReceiver {
    public static final int TYPE_POD_UPDATED = 111;
    public static final int TYPE_NETWORK_CHANGE = 112;
    public static final int TYPE_WAREHOUSE_PARCEL_DELIVERED = 113;
    public static final int TYPE_WH_POD_UPDATED = 114;
    public static final int TYPE_CUSTOMER_PARCEL_DELIVERED = 115;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("MArk","DIReceiver Called");
        int type = intent.getIntExtra(UrlConstants.KEY_TYPE, -1);
        boolean isSuccess = intent.getBooleanExtra(UrlConstants.KEY_DELIVERY_STATUS_FLAG, false);
        if(type == TYPE_POD_UPDATED){
            proccessDIReceiver(false);
        }else if(type == TYPE_WH_POD_UPDATED){
            proccessDIReceiver(true);
        }else if( type == TYPE_WAREHOUSE_PARCEL_DELIVERED){
            proccessDIReceiver(true);
        }else if( type == TYPE_CUSTOMER_PARCEL_DELIVERED){
            proccessCustParcelDelivered(isSuccess);
        }else if( type == TYPE_NETWORK_CHANGE){
            proccessNetworkChange();
        }
    }

    abstract public void proccessDIReceiver(boolean warehouse);
    abstract public void proccessCustParcelDelivered(boolean isSuccess);
    abstract public void proccessNetworkChange();
}
