package com.inerun.courier.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by vineet on 13/11/18.
 */

public class Shipment implements Serializable {
    private String total_peices;
    @SerializedName("vol_weight")
    private String weight;
    private String chargeable_weight;
    private String total_declaredvalue;
    private String gross_weight;
    private String holdforcollection;
    private String insurancerequired;
    private String specialinstructions;
    private String selectedservice;
    private String parceltype;
    private String parceltypetext;
    private String insuracevalue = "";
    private String surcharge_type;
    private String surcharge_name;

    private List<ShipmentDetail> shipment_details;
    private Services service;

    public Shipment() {
    }

    public Shipment(String total_peices, String weight, List<ShipmentDetail> shipment_details) {
        this.total_peices = total_peices;
        this.weight = weight;
        this.shipment_details = shipment_details;
    }

    public String getTotal_peices() {
        return total_peices;
    }

    public void setTotal_peices(String total_peices) {
        this.total_peices = total_peices;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public List<ShipmentDetail> getShipment_details() {
        return shipment_details;
    }

    public void setShipment_details(List<ShipmentDetail> shipment_details) {
        this.shipment_details = shipment_details;
    }


    public Shipment(String total_peices, String weight, List<ShipmentDetail> shipment_details, String chargeable_weight, String total_declaredvalue, String gross_weight, String holdforcollection, String insurancerequired, String specialinstructions, String selectedservice, String insuracevalue, String parceltype, String parceltypetext, String surcharge_type, String surcharge_name) {
        this.total_peices = total_peices;
        this.weight = weight;
        this.chargeable_weight = chargeable_weight;
        this.total_declaredvalue = total_declaredvalue;
        this.gross_weight = gross_weight;
        this.holdforcollection = holdforcollection;
        this.insurancerequired = insurancerequired;
        this.specialinstructions = specialinstructions;
        this.selectedservice = selectedservice;
        this.shipment_details = shipment_details;
        this.insuracevalue = insuracevalue;
        this.parceltype = parceltype;
        this.parceltypetext = parceltypetext;
        this.parceltypetext = parceltypetext;
        this.surcharge_type = surcharge_type;
        this.surcharge_name = surcharge_name;

    }


    public String getInsuracevalue() {
        return insuracevalue;
    }

    public void setInsuracevalue(String insuracevalue) {
        this.insuracevalue = insuracevalue;
    }

    public String getParceltypetext() {
        return parceltypetext;
    }

    public void setParceltypetext(String parceltypetext) {
        this.parceltypetext = parceltypetext;
    }

    public String getChargeable_weight() {
        return chargeable_weight;
    }

    public void setChargeable_weight(String chargeable_weight) {
        this.chargeable_weight = chargeable_weight;
    }

    public String getTotal_declaredvalue() {
        return total_declaredvalue;
    }

    public void setTotal_declaredvalue(String total_declaredvalue) {
        this.total_declaredvalue = total_declaredvalue;
    }

    public String getGross_weight() {
        return gross_weight;
    }

    public void setGross_weight(String gross_weight) {
        this.gross_weight = gross_weight;
    }

    public boolean isHoldforcollection() {

        return (holdforcollection != null && holdforcollection.equals("1"));

    }

    public String getHoldforcollection() {
        return holdforcollection;
    }

    public String getInsurancerequired() {
        return insurancerequired;
    }

    public void setHoldforcollection(String holdforcollection) {
        this.holdforcollection = holdforcollection;
    }

    public boolean isInsurancerequired() {
        return (insurancerequired != null && insurancerequired.equals("1"));
    }

    public void setInsurancerequired(String insurancerequired) {
        this.insurancerequired = insurancerequired;
    }

    public String getSpecialinstructions() {
        return specialinstructions;
    }

    public void setSpecialinstructions(String specialinstructions) {
        this.specialinstructions = specialinstructions;
    }

    public String getSelectedservice() {
        return selectedservice;
    }

    public void setSelectedservice(String selectedservice) {
        this.selectedservice = selectedservice;
    }

    public String getSurcharge_type() {
        return surcharge_type;
    }

    public void setSurcharge_type(String surcharge_type) {
        this.surcharge_type = surcharge_type;
    }

    public String getSurcharge_name() {
        return surcharge_name;
    }

    public void setSurcharge_name(String surcharge_name) {
        this.surcharge_name = surcharge_name;
    }

    public void setService(Services service) {
        this.service = service;
    }

    public Services getService() {
        return service;
    }

    public String getParceltype() {
        return parceltype;
    }

    public void setParceltype(String parceltype) {
        this.parceltype = parceltype;
    }

    public class ShipmentDetail implements Serializable {
        private String box_id;
        private String no_of_parcels;

        private String length;
        @SerializedName("weight")
        private String breath;
        private String height;
        private String vol_weight;
        private String gross_weight;
        private String type;
        private String chargeable_weight;
        private String declared_value;
        private String status;

        private String created_on;
        private String updated_on;

//        public ShipmentDetail(String box_id, String no_of_parcels, String length, String breath, String height, String vol_weight, String gross_weight, String created_on, String updated_on) {
//            this.box_id = box_id;
//            this.no_of_parcels = no_of_parcels;
//            this.length = length;
//            this.breath = breath;
//            this.height = height;
//            this.vol_weight = vol_weight;
//            this.gross_weight = gross_weight;
//            this.created_on = created_on;
//            this.updated_on = updated_on;
//        }

        public ShipmentDetail(String box_id, String no_of_parcels, String length, String breath, String height, String vol_weight, String gross_weight, String type, String chargeable_weight, String declared_value, String status, String created_on, String updated_on) {
            this.box_id = box_id;
            this.no_of_parcels = no_of_parcels;
            this.length = length;
            this.breath = breath;
            this.height = height;
            this.vol_weight = vol_weight;
            this.gross_weight = gross_weight;
            this.type = type;
            this.chargeable_weight = chargeable_weight;
            this.declared_value = declared_value;
            this.status = status;
            this.created_on = created_on;
            this.updated_on = updated_on;
        }


        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getChargeable_weight() {
            return chargeable_weight;
        }

        public void setChargeable_weight(String chargeable_weight) {
            this.chargeable_weight = chargeable_weight;
        }

        public String getDeclared_value() {
            return declared_value;
        }

        public void setDeclared_value(String declared_value) {
            this.declared_value = declared_value;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getBox_id() {
            return box_id;
        }

        public void setBox_id(String box_id) {
            this.box_id = box_id;
        }

        public String getNo_of_parcels() {
            return no_of_parcels;
        }

        public void setNo_of_parcels(String no_of_parcels) {
            this.no_of_parcels = no_of_parcels;
        }

        public String getLength() {
            return length;
        }

        public void setLength(String length) {
            this.length = length;
        }

        public String getBreath() {
            return breath;
        }

        public void setBreath(String breath) {
            this.breath = breath;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getVol_weight() {
            return vol_weight;
        }

        public void setVol_weight(String vol_weight) {
            this.vol_weight = vol_weight;
        }

        public String getGross_weight() {
            return gross_weight;
        }

        public void setGross_weight(String gross_weight) {
            this.gross_weight = gross_weight;
        }

        public String getCreated_on() {
            return created_on;
        }

        public void setCreated_on(String created_on) {
            this.created_on = created_on;
        }

        public String getUpdated_on() {
            return updated_on;
        }


        public void setUpdated_on(String updated_on) {
            this.updated_on = updated_on;
        }

        public String getDimension() {
            return getLength() + "x" + getBreath() + "x" + getHeight();
        }
    }


    public void reCalculateData() {
        float weight = 0.0f;
        float chargeable_weight = 0.0f;
        float total_declaredvalue = 0.0f;
        float gross_weight = 0.0f;

        for (ShipmentDetail shipment_detail : shipment_details) {
            weight = weight + Float.parseFloat(shipment_detail.getVol_weight());
            chargeable_weight = chargeable_weight + Float.parseFloat(shipment_detail.getChargeable_weight());
            total_declaredvalue = total_declaredvalue + Float.parseFloat(shipment_detail.getDeclared_value());
            gross_weight = gross_weight + Float.parseFloat(shipment_detail.getGross_weight());

        }
        setWeight("" + weight);
        setChargeable_weight("" + chargeable_weight);
        setTotal_declaredvalue("" + total_declaredvalue);
        setGross_weight("" + gross_weight);


    }

}
