package com.inerun.courier.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by vineet on 12/2/2016.
 */

public class UpdatedPickupParcelData implements Serializable {

    private String barcode;
    private int pickup_status;
    private String pickup_comments;

    private String update_date;

    private ArrayList<ParcelStatus> statusdata;
    private String card_transaction_id;
    private String awb;

    public UpdatedPickupParcelData(String barcode, int pickup_status, String pickup_comments, String update_date, ArrayList<ParcelStatus> statusdata) {
        this.barcode = barcode;
        this.pickup_status = pickup_status;
        this.pickup_comments = pickup_comments;
        this.update_date = update_date;
        this.statusdata = statusdata;
    }

    public UpdatedPickupParcelData(String barcode, int pickup_status, String pickup_comments, String update_date, ArrayList<ParcelStatus> statusdata, String card_transaction_id, String awb) {
        this.barcode = barcode;
        this.pickup_status = pickup_status;
        this.pickup_comments = pickup_comments;
        this.update_date = update_date;
        this.statusdata = statusdata;
        this.card_transaction_id = card_transaction_id;
        this.awb = awb;
    }
}
