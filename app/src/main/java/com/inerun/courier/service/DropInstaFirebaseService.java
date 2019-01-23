package com.inerun.courier.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.inerun.courier.constant.Utils;

/**
 * Created by vinay on 20/02/17.
 */

public class DropInstaFirebaseService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("DropInstaFirebase", "GcmId token: " + refreshedToken);
        Utils.saveGcmId(DropInstaFirebaseService.this,refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
//        DrsendRegistrationToServer(refreshedToken);
    }
}
