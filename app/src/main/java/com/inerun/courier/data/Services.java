package com.inerun.courier.data;

import java.io.Serializable;

/**
 * Created by vineet on 13/11/18.
 */

public class Services implements Serializable {
    private String service_id;
    private String service_name;
    private String freight_charges;

    private String estimated_delivery_date;
    private String time;
    private String rate_currency;
    private String rate;
    private String special_rate_currency;
    private String special_rate;
    private boolean isDefaultCheck;

    public Services(String service_id) {
        this.service_id = service_id;
    }


    public Services(String service_id, String service_name, String freight_charges) {
        this.service_id = service_id;
        this.service_name = service_name;
        this.freight_charges = freight_charges;
    }

    public Services(String service_id, String service_name, String freight_charges, String estimated_delivery_date, String time, String rate_currency, String rate, String special_rate_currency, String special_rate, boolean isDefaultCheck) {
        this.service_id = service_id;
        this.service_name = service_name;
        this.freight_charges = freight_charges;
        this.estimated_delivery_date = estimated_delivery_date;
        this.time = time;
        this.rate_currency = rate_currency;
        this.rate = rate;
        this.special_rate_currency = special_rate_currency;
        this.special_rate = special_rate;
        this.isDefaultCheck = isDefaultCheck;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getFreight_charges() {
        return freight_charges;
    }

    public void setFreight_charges(String freight_charges) {
        this.freight_charges = freight_charges;
    }

    public String getEstimated_delivery_date() {
        return estimated_delivery_date;
    }

    public void setEstimated_delivery_date(String estimated_delivery_date) {
        this.estimated_delivery_date = estimated_delivery_date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRate_currency() {
        return rate_currency;
    }

    public void setRate_currency(String rate_currency) {
        this.rate_currency = rate_currency;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getSpecial_rate_currency() {
        return special_rate_currency;
    }

    public void setSpecial_rate_currency(String special_rate_currency) {
        this.special_rate_currency = special_rate_currency;
    }

    public String getSpecial_rate() {
        return special_rate;
    }

    public void setSpecial_rate(String special_rate) {
        this.special_rate = special_rate;
    }

    public boolean isDefaultCheck() {
        return isDefaultCheck;
    }

    public void setDefaultCheck(boolean defaultCheck) {
        isDefaultCheck = defaultCheck;
    }


    @Override
    public boolean equals(Object obj) {
        return getService_id().equals(((Services)obj).getService_id());
    }
}
