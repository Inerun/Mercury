package com.inerun.courier.data;

import java.io.Serializable;

/**
 * Created by vineet on 2/14/2017.
 */

public class ParcelSearchData implements Serializable {

    private String parcel_no;
    private String parcel_name;
    private String parcel_email;
    private String parcel_phone;
    private String parcel_custid;
    private String parcel_invoice_no;

    public ParcelSearchData(String parcel_no, String parcel_name, String parcel_email, String parcel_phone, String parcel_custid, String parcel_invoice_no) {
        this.parcel_no = parcel_no;
        this.parcel_name = parcel_name;
        this.parcel_email = parcel_email;
        this.parcel_phone = parcel_phone;
        this.parcel_custid = parcel_custid;
        this.parcel_invoice_no = parcel_invoice_no;
    }

    public String getParcel_no() {
        return parcel_no;
    }

    public String getParcel_name() {
        return parcel_name;
    }

    public String getParcel_email() {
        return parcel_email;
    }

    public String getParcel_phone() {
        return parcel_phone;
    }

    public String getParcel_custid() {
        return parcel_custid;
    }

    public String getParcel_invoice_no() {
        return parcel_invoice_no;
    }
}
