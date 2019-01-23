package com.inerun.courier.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by vineet on 10/3/2017.
 */

public class PickParcelDetailData implements Serializable {

    @SerializedName("lenght")
    private String parcel_length;
    @SerializedName("width")
    private String parcel_width;
    @SerializedName("height")
    private String parcel_height;
    @SerializedName("volumeWeight")
    private String parcel_volum_weight;
    @SerializedName("actualWeight")
    private String parcel_actual_weight;
    private String parcel_implication;
    @SerializedName("amount")
    private String parcel_price;
    @SerializedName("description")
    private String parcel_descrip;
    @SerializedName("special_instructions")
    private String parcel_special_ins;
    @SerializedName("assignDriverDate")
    private String parcel_assign_date;
    @SerializedName("created_on")
    private String parcel_created_on;
    @SerializedName("pickup_comment")
    private String parcel_pickup_comment;
    @SerializedName("pickup_transaction_id")
    private String parcel_card_transaction_id;




    public PickParcelDetailData(String parcel_length, String parcel_height, String parcel_width, String parcel_volum_weight, String parcel_actual_weight, String parcel_implication, String parcel_price, String parcel_descrip, String parcel_special_ins) {
        this.parcel_length = parcel_length;
        this.parcel_height = parcel_height;
        this.parcel_width = parcel_width;
        this.parcel_volum_weight = parcel_volum_weight;
        this.parcel_actual_weight = parcel_actual_weight;
        this.parcel_implication = parcel_implication;
        this.parcel_price = parcel_price;
        this.parcel_descrip = parcel_descrip;
        this.parcel_special_ins = parcel_special_ins;
    }

    public PickParcelDetailData(String parcel_length, String parcel_width, String parcel_height, String parcel_volum_weight, String parcel_actual_weight, String parcel_price, String parcel_descrip, String parcel_special_ins, String parcel_assign_date, String parcel_created_on, String parcel_pickup_comment) {
        this.parcel_length = parcel_length;
        this.parcel_width = parcel_width;
        this.parcel_height = parcel_height;
        this.parcel_volum_weight = parcel_volum_weight;
        this.parcel_actual_weight = parcel_actual_weight;
        this.parcel_price = parcel_price;
        this.parcel_descrip = parcel_descrip;
        this.parcel_special_ins = parcel_special_ins;
        this.parcel_assign_date = parcel_assign_date;
        this.parcel_created_on = parcel_created_on;
        this.parcel_pickup_comment = parcel_pickup_comment;
    }

    public PickParcelDetailData(String parcel_length, String parcel_width, String parcel_height, String parcel_volum_weight, String parcel_actual_weight, String parcel_price, String parcel_descrip, String parcel_special_ins, String parcel_assign_date, String parcel_created_on, String parcel_pickup_comment, String parcel_card_transaction_id) {
        this.parcel_length = parcel_length;
        this.parcel_width = parcel_width;
        this.parcel_height = parcel_height;
        this.parcel_volum_weight = parcel_volum_weight;
        this.parcel_actual_weight = parcel_actual_weight;
        this.parcel_price = parcel_price;
        this.parcel_descrip = parcel_descrip;
        this.parcel_special_ins = parcel_special_ins;
        this.parcel_assign_date = parcel_assign_date;
        this.parcel_created_on = parcel_created_on;
        this.parcel_pickup_comment = parcel_pickup_comment;
        this.parcel_card_transaction_id = parcel_card_transaction_id;

    }



    public String getParcel_length() {
        return parcel_length;
    }

    public void setParcel_length(String parcel_length) {
        this.parcel_length = parcel_length;
    }

    public String getParcel_height() {
        return parcel_height;
    }

    public void setParcel_height(String parcel_height) {
        this.parcel_height = parcel_height;
    }

    public String getParcel_width() {
        return parcel_width;
    }

    public void setParcel_width(String parcel_width) {
        this.parcel_width = parcel_width;
    }

    public String getParcel_volum_weight() {
        return parcel_volum_weight;
    }

    public void setParcel_volum_weight(String parcel_volum_weight) {
        this.parcel_volum_weight = parcel_volum_weight;
    }

    public String getParcel_actual_weight() {
        return parcel_actual_weight;
    }

    public void setParcel_actual_weight(String parcel_actual_weight) {
        this.parcel_actual_weight = parcel_actual_weight;
    }

    public String getParcel_implication() {
        return parcel_implication;
    }

    public void setParcel_implication(String parcel_implication) {
        this.parcel_implication = parcel_implication;
    }

    public String getParcel_price() {
        return parcel_price;
    }

    public void setParcel_price(String parcel_price) {
        this.parcel_price = parcel_price;
    }

    public String getParcel_descrip() {
        return parcel_descrip;
    }

    public void setParcel_descrip(String parcel_descrip) {
        this.parcel_descrip = parcel_descrip;
    }

    public String getParcel_special_ins() {
        return parcel_special_ins;
    }

    public void setParcel_special_ins(String parcel_special_ins) {
        this.parcel_special_ins = parcel_special_ins;
    }

    public String getParcel_assign_date() {
        return parcel_assign_date;
    }

    public void setParcel_assign_date(String parcel_assign_date) {
        this.parcel_assign_date = parcel_assign_date;
    }

    public String getParcel_created_on() {
        return parcel_created_on;
    }

    public void setParcel_created_on(String parcel_created_on) {
        this.parcel_created_on = parcel_created_on;
    }

    public String getParcel_pickup_comment() {
        return parcel_pickup_comment;
    }

    public void setParcel_pickup_comment(String parcel_pickup_comment) {
        this.parcel_pickup_comment = parcel_pickup_comment;
    }

    public String getParcel_card_transaction_id() {
        return parcel_card_transaction_id;
    }

    public void setParcel_card_transaction_id(String parcel_card_transaction_id) {
        this.parcel_card_transaction_id = parcel_card_transaction_id;
    }
}
