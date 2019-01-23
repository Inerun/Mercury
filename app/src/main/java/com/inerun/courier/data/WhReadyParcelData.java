package com.inerun.courier.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by vineet on 2/20/2017.
 */

public class WhReadyParcelData implements Serializable {
    private boolean status;
    private int total;
    private int count;
    private ArrayList<RequestData> custRequestData;
    private ArrayList<CustomerExecutiveData> executivedata;

    public ArrayList<CustomerExecutiveData> getExecutivedata() {
        return executivedata;
    }

    public WhReadyParcelData(ArrayList<RequestData> custRequestData) {
        this.custRequestData = custRequestData;
    }

    public WhReadyParcelData() {
    }

    public boolean isStatus() {
        return status;
    }

    public int getTotal() {
        return total;
    }

    public int getCount() {
        return count;
    }

    public ArrayList<RequestData> getCustRequestData() {
        return custRequestData;
    }

    public void setCustRequestData(ArrayList<RequestData> custRequestData) {
        this.custRequestData = custRequestData;
    }

    public class RequestData implements Serializable{
        private String request_id;
        private ArrayList<ParcelListingData.ParcelData> parcelData;


        public String getRequest_id() {
            return request_id;
        }

        public ArrayList<ParcelListingData.ParcelData> getParcelData() {
            return parcelData;
        }
    }


}
