package com.inerun.courier.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by vineet on 13/11/18.
 */

public class Payment implements Serializable {
    @SerializedName("payment_details")
    private int payment_type;
    private String card_transaction_id;

    private String payment_name;
    private boolean select;

    public Payment(int payment_type, String payment_name) {
        this.payment_type = payment_type;
        this.payment_name = payment_name;
    }

    public Payment(int payment_type) {
        this.payment_type = payment_type;
    }

    public int getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(int payment_type) {
        this.payment_type = payment_type;
    }

    public String getCard_transaction_id() {
        return card_transaction_id;
    }

    public void setCard_transaction_id(String card_transaction_id) {
        this.card_transaction_id = card_transaction_id;
    }

    public String getPayment_name() {
        return payment_name;
    }

    public void setPayment_name(String payment_name) {
        this.payment_name = payment_name;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
