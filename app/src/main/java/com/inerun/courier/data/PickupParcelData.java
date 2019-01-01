package com.inerun.courier.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import static com.inerun.courier.data.ParcelListingData.ParcelData.*;

/**
 * Created by vineet on 9/28/2017.
 */

public class PickupParcelData implements Serializable {

    private int column_id;
    @SerializedName("barcode")
    private String parcel_barcode;
    @SerializedName("customerid")
    private String customer_id;
    @SerializedName("personalfirstname")
    private String fname;
    @SerializedName("personallastname")
    private String lname;
    private String name;
    @SerializedName("personalemail")
    private String email;
    @SerializedName("personalphoneno")
    private String phone;
    @SerializedName("personallandline")
    private String landline;
    @SerializedName("personalext")
    private String ext;

    private int pickup_status;

    @SerializedName("pickup_address")
    private PickupAddress pickup_address;
    @SerializedName("delivery_address")
    private PickupAddress Delivery_address;
    @SerializedName("parcel_data")
    private PickParcelDetailData  pickParcelDetailData;
    public ArrayList<ParcelStatus> status;

    private boolean isSelected;
    private String vendor_name;
    private String awb;
    private Payment payment;
    private Services service;
    private Shipment shipment;

    public PickupParcelData(String parcel_barcode, String name, String phone, PickupAddress pickup_address, PickParcelDetailData pickParcelDetailData) {
        this.parcel_barcode = parcel_barcode;
        this.name = name;
        this.phone = phone;
        this.pickup_address = pickup_address;
        this.pickParcelDetailData = pickParcelDetailData;
    }

    public PickupParcelData(String parcel_barcode, String fname, String lname, String email, String phone, String landline, String ext, PickupAddress pickup_address, PickupAddress delivery_address, PickParcelDetailData pickParcelDetailData) {
        this.parcel_barcode = parcel_barcode;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.phone = phone;
        this.landline = landline;
        this.ext = ext;
        this.pickup_address = pickup_address;
        Delivery_address = delivery_address;
        this.pickParcelDetailData = pickParcelDetailData;
    }

    public PickupParcelData(int column_id,String parcel_barcode, String customer_id, String fname, String lname, String email, String phone, String landline, String ext, int pickup_status, PickupAddress pickup_address, PickupAddress delivery_address, PickParcelDetailData pickParcelDetailData) {
        this.column_id = column_id;
        this.parcel_barcode = parcel_barcode;
        this.customer_id = customer_id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.phone = phone;
        this.landline = landline;
        this.ext = ext;
        this.pickup_status = pickup_status;
        this.pickup_address = pickup_address;
        Delivery_address = delivery_address;
        this.pickParcelDetailData = pickParcelDetailData;
    }

    public PickupParcelData(int column_id, String parcel_barcode, String customer_id, String fname, String lname, String email, String phone, String landline, String ext, int pickup_status, PickupAddress pickup_address, PickupAddress delivery_address, PickParcelDetailData pickParcelDetailData, String vendor_name, String awb, Payment payment, Services service, Shipment shipment) {
        this.column_id = column_id;
        this.parcel_barcode = parcel_barcode;
        this.customer_id = customer_id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.phone = phone;
        this.landline = landline;
        this.ext = ext;
        this.pickup_status = pickup_status;
        this.pickup_address = pickup_address;
        Delivery_address = delivery_address;
        this.pickParcelDetailData = pickParcelDetailData;
        this.vendor_name = vendor_name;
        this.awb = awb;
        this.payment = payment;
        this.service = service;
        this.shipment = shipment;
    }

    public int getColumn_id() {
        return column_id;
    }

    public String getParcel_barcode() {
        return parcel_barcode;
    }

    public void setParcel_barcode(String parcel_barcode) {
        this.parcel_barcode = parcel_barcode;
    }


    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getName() {
        return fname+ " " +lname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public int getPickup_status() {
        return pickup_status;
    }

    public void setPickup_status(int pickup_status) {
        this.pickup_status = pickup_status;
    }

    public PickupAddress getPickup_address() {
        return pickup_address;
    }

    public void setPickup_address(PickupAddress pickup_address) {
        this.pickup_address = pickup_address;
    }

    public PickupAddress getDelivery_address() {
        return Delivery_address;
    }

    public void setDelivery_address(PickupAddress delivery_address) {
        Delivery_address = delivery_address;
    }

    public PickParcelDetailData getPickParcelDetailData() {
        return pickParcelDetailData;
    }

    public void setPickParcelDetailData(PickParcelDetailData pickParcelDetailData) {
        this.pickParcelDetailData = pickParcelDetailData;
    }

    public ArrayList<ParcelStatus> getStatus() {
        return status;
    }

    public void setStatus(ArrayList<ParcelStatus> status) {
        this.status = status;
    }

    public String getDeliveryAddress() {

        String add = "";

        if (pickup_address.getAddress1() != null && pickup_address.getAddress1().trim().length() > 0) {
            add = pickup_address.getAddress1() + ", ";
        }
        if (pickup_address.getAddress2() != null && pickup_address.getAddress2().trim().length() > 0) {
            add = add + pickup_address.getAddress2() + ", ";
        }
        if (pickup_address.getCity() != null && pickup_address.getCity().trim().length() > 0) {
            add = add + pickup_address.getCity()+ ", ";
        }
        if (pickup_address.getState() != null && pickup_address.getState().trim().length() > 0) {
            add = add + pickup_address.getState() + ", ";
        }
        if (pickup_address.getCountry() != null && pickup_address.getCountry().trim().length() > 0) {
            add = add + pickup_address.getCountry() + ", ";
        }
        if (pickup_address.getZipCode() != null && pickup_address.getZipCode().trim().length() > 0) {
            add = add + pickup_address.getZipCode() ;
        }


        return add;
    }

    public String getPickUpAddress() {

        String add = "";

        if (pickup_address.getAddress1() != null && pickup_address.getAddress1().trim().length() > 0) {
            add = pickup_address.getAddress1() + ", ";
        }
        if (pickup_address.getAddress2() != null && pickup_address.getAddress2().trim().length() > 0) {
            add = add + pickup_address.getAddress2() + ", ";
        }
        if (pickup_address.getCity() != null && pickup_address.getCity().trim().length() > 0) {
            add = add + pickup_address.getCity()+ ", ";
        }
        if (pickup_address.getState() != null && pickup_address.getState().trim().length() > 0) {
            add = add + pickup_address.getState() + ", ";
        }
        if (pickup_address.getCountry() != null && pickup_address.getCountry().trim().length() > 0) {
            add = add + pickup_address.getCountry() + ", ";
        }
        if (pickup_address.getZipCode() != null && pickup_address.getZipCode().trim().length() > 0) {
            add = add + pickup_address.getZipCode() ;
        }


        return add;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
    }

    public String getAwb() {
        return awb;
    }

    public void setAwb(String awb) {
        this.awb = awb;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Services getService() {
        return service;
    }

    public void setService(Services service) {
        this.service = service;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public boolean isReceived() {
        return pickup_status == PICKUP_RECEIVED;
    }
}
