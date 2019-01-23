package com.inerun.courier.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by vineet on 12/2/2016.
 */

public class UpdatedParcelData implements Serializable {

    private String barcode;
    private int deliverystatus;
    private String deliverycomments;
    private int payment_status;
    private String update_date;
    private String pod_name_on_server;
    private String receiver_name;
    private String national_id;
    private ArrayList<ParcelStatus> statusdata;
    private String awb;
    private String mercury_payment_type;

    public UpdatedParcelData(String barcode, int deliverystatus, String deliverycomments, int payment_status, String update_date, String pod_name_on_server, String receiver_name,ArrayList<ParcelStatus> statusdata) {
        this.barcode = barcode;
        this.deliverystatus = deliverystatus;
        this.deliverycomments = deliverycomments;
        this.payment_status = payment_status;
        this.update_date = update_date;
        this.pod_name_on_server = pod_name_on_server;
        this.receiver_name = receiver_name;
        this.statusdata = statusdata;
    }

    public UpdatedParcelData(String barcode, int deliverystatus, String deliverycomments, int payment_status, String update_date, String pod_name_on_server, String receiver_name,ArrayList<ParcelStatus> statusdata, String national_id) {
        this.barcode = barcode;
        this.deliverystatus = deliverystatus;
        this.deliverycomments = deliverycomments;
        this.payment_status = payment_status;
        this.update_date = update_date;
        this.pod_name_on_server = pod_name_on_server;
        this.receiver_name = receiver_name;
        this.national_id = national_id;
        this.statusdata = statusdata;
    }
    public UpdatedParcelData(String barcode, int deliverystatus, String deliverycomments, int payment_status, String update_date, String pod_name_on_server, String receiver_name) {
        this.barcode = barcode;
        this.deliverystatus = deliverystatus;
        this.deliverycomments = deliverycomments;
        this.payment_status = payment_status;
        this.update_date = update_date;
        this.pod_name_on_server = pod_name_on_server;
        this.receiver_name = receiver_name;
        this.statusdata = statusdata;
    }

    public UpdatedParcelData(String barcode, int deliverystatus, String deliverycomments, int payment_status, String update_date, String pod_name_on_server, String receiver_name,ArrayList<ParcelStatus> statusdata, String national_id, String awb, String mercury_payment_type) {
        this.barcode = barcode;
        this.deliverystatus = deliverystatus;
        this.deliverycomments = deliverycomments;
        this.payment_status = payment_status;
        this.update_date = update_date;
        this.pod_name_on_server = pod_name_on_server;
        this.receiver_name = receiver_name;
        this.national_id = national_id;
        this.statusdata = statusdata;
        this.awb = awb;
        this.mercury_payment_type = mercury_payment_type;
    }

}
