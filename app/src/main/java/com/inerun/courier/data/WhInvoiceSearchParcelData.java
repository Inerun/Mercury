package com.inerun.courier.data;

import java.util.ArrayList;

/**
 * Created by vinay on 19/12/16.
 */

public class WhInvoiceSearchParcelData {

    private boolean status;
    private String message;
    private ArrayList<ParcelListingData.ParcelData> deliverydata;
    private ArrayList<CustomerExecutiveData> executivedata;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }


    public ArrayList<ParcelListingData.ParcelData> getDeliverydata() {
        return deliverydata;
    }

    public ArrayList<CustomerExecutiveData> getExecutivedata() {
        return executivedata;
    }
}
