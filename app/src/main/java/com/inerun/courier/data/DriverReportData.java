package com.inerun.courier.data;

import java.io.Serializable;

/**
 * Created by vineet on 03/10/18.
 */

public class DriverReportData implements Serializable {


    private String total_delivery_count;
    private String delivered_count;
    private String delivery_pending_count;

    private String total_collection_count;
    private String collected_count;
    private String collection_pending_count;

    private String total_payment;
    private String payment_cash;
    private String payment_card;

    private String retrun_parcels;


    public DriverReportData(String total_delivery_count, String delivered_count, String delivery_pending_count, String total_collection_count, String collected_count, String collection_pending_count, String total_payment, String payment_cash, String payment_card, String retrun_parcels) {
        this.total_delivery_count = total_delivery_count;
        this.delivered_count = delivered_count;
        this.delivery_pending_count = delivery_pending_count;
        this.total_collection_count = total_collection_count;
        this.collected_count = collected_count;
        this.collection_pending_count = collection_pending_count;
        this.total_payment = total_payment;
        this.payment_cash = payment_cash;
        this.payment_card = payment_card;
        this.retrun_parcels = retrun_parcels;
    }

    public String getTotal_delivery_count() {
        return total_delivery_count;
    }

    public void setTotal_delivery_count(String total_delivery_count) {
        this.total_delivery_count = total_delivery_count;
    }

    public String getDelivered_count() {
        return delivered_count;
    }

    public void setDelivered_count(String delivered_count) {
        this.delivered_count = delivered_count;
    }

    public String getDelivery_pending_count() {
        return delivery_pending_count;
    }

    public void setDelivery_pending_count(String delivery_pending_count) {
        this.delivery_pending_count = delivery_pending_count;
    }

    public String getTotal_collection_count() {
        return total_collection_count;
    }

    public void setTotal_collection_count(String total_collection_count) {
        this.total_collection_count = total_collection_count;
    }

    public String getCollected_count() {
        return collected_count;
    }

    public void setCollected_count(String collected_count) {
        this.collected_count = collected_count;
    }

    public String getCollection_pending_count() {
        return collection_pending_count;
    }

    public void setCollection_pending_count(String collection_pending_count) {
        this.collection_pending_count = collection_pending_count;
    }

    public String getTotal_payment() {
        return total_payment;
    }

    public void setTotal_payment(String total_payment) {
        this.total_payment = total_payment;
    }

    public String getPayment_cash() {
        return payment_cash;
    }

    public void setPayment_cash(String payment_cash) {
        this.payment_cash = payment_cash;
    }

    public String getPayment_card() {
        return payment_card;
    }

    public void setPayment_card(String payment_card) {
        this.payment_card = payment_card;
    }

    public String getRetrun_parcels() {
        return retrun_parcels;
    }

    public void setRetrun_parcels(String retrun_parcels) {
        this.retrun_parcels = retrun_parcels;
    }
}
